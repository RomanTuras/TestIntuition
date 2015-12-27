/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.com.spasetv.testintuitions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;
import ua.com.spasetv.testintuitions.helpers.RndHelper;
import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.OnExerciseFinishListener;
import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by Roman Turas on 13/12/2015.
 * Fragment Exercise One.
 */

public class FragExerciseOne extends Fragment
        implements StaticFields, View.OnTouchListener, Animation.AnimationListener {

    private MainActivity mainActivity;
    private RndHelper rndHelper;
    private DataBaseHelper dataBaseHelper;
    private ContentValues contentValues;
    private SQLiteDatabase database;
    private ProgressBar progressBarExOne;
    private ExTextView textExOneProgress;
    private ImageView imgExOneMoon, imgExOneSun, imgExOneQuestion;
    private Animation animScaleIn, animScaleOut, animScaleOutOffset,
            animScaleInOffset, animPause;
    private SoundPool soundPool;
    private Vibrator vibrator;
    private OnExerciseFinishListener onExerciseFinishListener;

    private int sndCorrect, sndWrong;
    private byte numberOfQuestion = 23;
    private byte totalCorrectAnswers = 0;
    private byte[] arrayCorrectAnswers;
    private final static byte MOON_BUTTON = 0;
    private final static byte SUN_BUTTON = 1;
    private boolean isOnTouchKeyOn = true;
    private boolean isLastQuestion = false;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.rndHelper = new RndHelper(ID_EXERCISE_ONE);
        this.arrayCorrectAnswers = rndHelper.getArrayAnswers();
        this.vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        this.contentValues = new ContentValues();
        this.dataBaseHelper = new DataBaseHelper(activity);

        try {
            onExerciseFinishListener = (OnExerciseFinishListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnExerciseFinishListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){

        float txtSize = new DisplayMetrics(getActivity().getWindowManager()).getSizeTextH2();
        float txtSizePrgrs = new DisplayMetrics(getActivity().getWindowManager()).getSizeTextH4();
        int widthImage = new DisplayMetrics(getActivity().getWindowManager()).getWidthImage();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        }else{
            createOldSoundPool();
        }
        sndCorrect = soundPool.load(getActivity(), R.raw.correct, PRIORITY);
        sndWrong = soundPool.load(getActivity(), R.raw.wrong, PRIORITY);

        View view = inflater.inflate(R.layout.fragment_exercise_one, null);

        animScaleIn = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_scalein);
        animScaleIn.setAnimationListener(this);
        animScaleOut = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_scaleout);
        animScaleOutOffset = AnimationUtils.loadAnimation(getActivity(),
                R.anim.anim_scaleout_offset);
        animScaleOutOffset.setAnimationListener(this);
        animScaleInOffset = AnimationUtils.loadAnimation(getActivity(),
                R.anim.anim_scalein_offset);
        animScaleInOffset.setAnimationListener(this);
        animPause = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_pause);
        animPause.setAnimationListener(this);

        ExTextView textExOneHead = (ExTextView) view.findViewById(R.id.textExOneHead);
        ExTextView textExOneOr = (ExTextView) view.findViewById(R.id.textExOneOr);
        textExOneHead.setTextSize(txtSize);
        textExOneOr.setTextSize(txtSize);

        imgExOneQuestion = (ImageView) view.findViewById(R.id.imgExOneQuestion);
        imgExOneQuestion.getLayoutParams().width = widthImage;
        imgExOneQuestion.getLayoutParams().height = widthImage;

        imgExOneMoon = (ImageView) view.findViewById(R.id.imgExOneMoon);
        imgExOneMoon.getLayoutParams().height = widthImage + widthImage/2;
        imgExOneMoon.getLayoutParams().height = widthImage + widthImage/2;
        imgExOneMoon.setOnTouchListener(this);

        imgExOneSun = (ImageView) view.findViewById(R.id.imgExOneSun);
        imgExOneSun.getLayoutParams().height = widthImage + widthImage/2;
        imgExOneSun.getLayoutParams().height = widthImage + widthImage/2;
        imgExOneSun.setOnTouchListener(this);

        textExOneProgress = (ExTextView) view.findViewById(R.id.textExOneProgress);
        textExOneProgress.setTextSize(txtSizePrgrs);
        progressBarExOne = (ProgressBar) view.findViewById(R.id.progressBarExOne);
        progressBarExOne.getLayoutParams().width = widthImage;
        progressBarExOne.getLayoutParams().height = widthImage;

        if(getActivity()!=null){ mainActivity = (MainActivity) getActivity(); }

        overrideActionBar();
        setProgressBar(numberOfQuestion);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        soundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, SRC_QUALITY);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(isOnTouchKeyOn) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (view == imgExOneMoon) checkAnswer(MOON_BUTTON);
                else if (view == imgExOneSun) checkAnswer(SUN_BUTTON);
            }
        }
        return false;
    }

    private void checkAnswer(int nameButton){
        if(++numberOfQuestion <= TOTAL_QUESTIONS_EX_ONE){
            Log.d("TG", "n = "+numberOfQuestion);
            if(arrayCorrectAnswers[numberOfQuestion-1] == nameButton){
                Log.d("TG", "Correct!");
                Log.d("TG", "arr= "+arrayCorrectAnswers[numberOfQuestion-1]+" btn="+nameButton);
                soundPool.play(sndCorrect,VOLUME,VOLUME,PRIORITY,LOOP,RATE);
                totalCorrectAnswers++;
                showCorrectImage(arrayCorrectAnswers[numberOfQuestion-1]);
            }else {
                Log.d("TG", "Wrong! ");
                Log.d("TG", "arr= "+arrayCorrectAnswers[numberOfQuestion-1]+" btn="+nameButton);
                soundPool.play(sndWrong,VOLUME,VOLUME,PRIORITY,LOOP,RATE);
                vibrator.vibrate(200);
                showCorrectImage(arrayCorrectAnswers[numberOfQuestion-1]);
            }
        }
        if(TOTAL_QUESTIONS_EX_ONE == numberOfQuestion){
            isOnTouchKeyOn = false;
            isLastQuestion = true;
            try {
                saveResultExercise();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("TG", "n = "+numberOfQuestion+" -end!");
            setProgressBar(numberOfQuestion);
            imgExOneMoon.startAnimation(animPause);
        }
    }

    private void showCorrectImage(int correctButton){
        isOnTouchKeyOn = false;
        imgExOneQuestion.startAnimation(animScaleOut);
        if(correctButton == MOON_BUTTON) {
            imgExOneQuestion.setImageResource(R.drawable.ic_brightness_2_black_48dp);
        }else if(correctButton == SUN_BUTTON){
            imgExOneQuestion.setImageResource(R.drawable.ic_brightness_5_black_48dp);
        }
        imgExOneQuestion.startAnimation(animScaleIn);
    }

    private void setProgressBar(int progress){
        int textProgress = progress == TOTAL_QUESTIONS_EX_ONE ? progress : progress+1;
        String score = textProgress+"/"+TOTAL_QUESTIONS_EX_ONE;
        progressBarExOne.setMax(TOTAL_QUESTIONS_EX_ONE);
        progressBarExOne.setProgress(progress);
        textExOneProgress.setText(score);
    }

    private void overrideActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_EXERCISE_ONE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mainActivity.overrideActionBar(MAIN_ACTIVITY);
    }

    @Override
    public void onAnimationStart(Animation animation) {    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(!isLastQuestion) {
            if (animation == animScaleIn) {
                imgExOneQuestion.startAnimation(animScaleOutOffset);
            }else if (animation == animScaleOutOffset) {
                imgExOneQuestion.setImageResource(R.drawable.ic_help_outline_black_24dp);
                imgExOneQuestion.startAnimation(animScaleInOffset);
            }else if (animation == animScaleInOffset) {
                isOnTouchKeyOn = true;
                setProgressBar(numberOfQuestion);
            }
        }else {
            if(animation == animPause) {
                Log.d("TG", "totalCorrectAnswers = " + totalCorrectAnswers);
                onExerciseFinishListener.onExerciseFinish(FRAGMENT_EXERCISE_ONE, totalCorrectAnswers);
            }
        }
        vibrator.cancel();
    }

    private void saveResultExercise() throws IOException{
        int resultPercent = (totalCorrectAnswers*100)/TOTAL_QUESTIONS_EX_ONE;
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy");
        Date d = new Date();
        String date = sdf.format(d);
        Log.d("TG", "date: "+sdf.format(d));
        database = dataBaseHelper.getReadableDatabase();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_RESULT, resultPercent);
        database.insert(TABLE_NAME_EX_ONE, null, contentValues);
        database.close();
        dataBaseHelper.close();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {    }
}

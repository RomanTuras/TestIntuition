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
import java.util.ArrayList;
import java.util.Date;

import ua.com.spasetv.testintuitions.google_services.Analytics;
import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;
import ua.com.spasetv.testintuitions.helpers.RndHelper;
import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.OnExerciseFinishListener;
import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by Roman Turas on 24/12/2015.
 * Fragment Exercise Three.
 */

public class FragExerciseThree extends Fragment
        implements StaticFields, View.OnTouchListener, Animation.AnimationListener {

    private MainActivity mainActivity;
    private RndHelper rndHelper;
    private DataBaseHelper dataBaseHelper;
    private ContentValues contentValues;
    private SQLiteDatabase database;
    private ProgressBar progressBarExOne;
    private ExTextView textExOneProgress;
    private ImageView imgQuestion;
    private Animation animScaleIn, animScaleOut, animScaleOutOffset,
            animScaleInOffset, animPause;
    private SoundPool soundPool;
    private Vibrator vibrator;
    private View view;
    private OnExerciseFinishListener onExerciseFinishListener;

    private int sndCorrect, sndWrong;
    private int widthImage;
    private byte numberOfQuestion = 0;
    private byte totalCorrectAnswers = 0;
    private byte[] arrayAnswers;
    private final static byte STAR_BUTTON = 0;
    private final static byte WAVE_BUTTON = 1;
    private final static byte EYE_BUTTON = 2;
    private final static byte CROSS_BUTTON = 3;
    private final static byte CIRCLE_BUTTON = 4;
    private boolean isOnTouchKeyOn = true;
    private boolean isLastQuestion = false;

    private ArrayList<ImageView> arrayButtons = new ArrayList<>(); // array of Buttons objects
    private int idButtons[] = { R.id.imgStar, R.id.imgWave,
            R.id.imgEye, R.id.imgCross, R.id.imgCircle }; //array id of Buttons

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.rndHelper = new RndHelper(ID_EXERCISE_THREE);
        this.arrayAnswers = rndHelper.getArrayAnswers();
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
        float txtSizeProgress = new DisplayMetrics(getActivity().getWindowManager()).getSizeTextH4();
        widthImage = new DisplayMetrics(getActivity().getWindowManager()).getWidthImage();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        }else{
            createOldSoundPool();
        }
        sndCorrect = soundPool.load(getActivity(), R.raw.correct, PRIORITY);
        sndWrong = soundPool.load(getActivity(), R.raw.wrong, PRIORITY);

        view = inflater.inflate(R.layout.fragment_exercise_three, null);

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

        ExTextView textExThreeHead = (ExTextView) view.findViewById(R.id.textExThreeHead);
        textExThreeHead.setTextSize(txtSize);

        imgQuestion = (ImageView) view.findViewById(R.id.imgQuestion);
        imgQuestion.getLayoutParams().width = widthImage;
        imgQuestion.getLayoutParams().height = widthImage;

        textExOneProgress = (ExTextView) view.findViewById(R.id.textExThreeProgress);
        textExOneProgress.setTextSize(txtSizeProgress);
        progressBarExOne = (ProgressBar) view.findViewById(R.id.progressBarExThree);
        progressBarExOne.getLayoutParams().width = widthImage;
        progressBarExOne.getLayoutParams().height = widthImage;

        mainActivity = (MainActivity) getActivity();

        overrideActionBar();
        setProgressBar(numberOfQuestion);
        initButton();

        new Analytics(getActivity()).sendAnalytics("Test Intuition","One from Five","Start", "nop");
        return view;
    }

    /** Find 9 buttons, save their in array, set size to buttons */
    private void initButton() {
        arrayButtons.clear();
        for(int i = 0; i < idButtons.length; i++)
        {
            arrayButtons.add((ImageView) view.findViewById(idButtons[i]));
            arrayButtons.get(i).getLayoutParams().width = widthImage;
            arrayButtons.get(i).getLayoutParams().height = widthImage;
        }
        setButtonsTouchListener();
    }

    private void setButtonsTouchListener(){
        for(ImageView imageView: arrayButtons) {imageView.setOnTouchListener(this);}
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
                int item = arrayButtons.indexOf(view);
                checkAnswer(item);
            }
        }
        return false;
    }

    private void checkAnswer(int item){
        if(++numberOfQuestion < TOTAL_QUESTIONS_EX_THREE){
            showCorrectImage(item);
        }else {
            isOnTouchKeyOn = false;
            isLastQuestion = true;
            try {
                saveResultExercise();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showCorrectImage(item);
            arrayButtons.get(0).startAnimation(animPause);
            setProgressBar(numberOfQuestion);
        }
    }

    private void showCorrectImage(int item){
        isOnTouchKeyOn = false;
        int correctAnswer = arrayAnswers[numberOfQuestion-1];
        if(correctAnswer == item){
            soundPool.play(sndCorrect,VOLUME,VOLUME,PRIORITY,LOOP,RATE);
            totalCorrectAnswers++;
        }else{
            soundPool.play(sndWrong,VOLUME,VOLUME,PRIORITY,LOOP,RATE);
            vibrator.vibrate(200);
        }
        imgQuestion.startAnimation(animScaleOut);
        if(correctAnswer == STAR_BUTTON) {
            imgQuestion.setImageResource(R.drawable.star_48dp);
        }else if(correctAnswer == WAVE_BUTTON){
            imgQuestion.setImageResource(R.drawable.water_48dp);
        }else if(correctAnswer == EYE_BUTTON){
            imgQuestion.setImageResource(R.drawable.eye_48dp);
        }else if(correctAnswer == CROSS_BUTTON){
            imgQuestion.setImageResource(R.drawable.cross_48dp);
        }else if(correctAnswer == CIRCLE_BUTTON){
            imgQuestion.setImageResource(R.drawable.circle_48dp);
        }
        imgQuestion.startAnimation(animScaleIn);
    }

    private void setProgressBar(int progress){
        int textProgress = progress == TOTAL_QUESTIONS_EX_THREE ? progress : progress+1;
        String score = textProgress+"/"+TOTAL_QUESTIONS_EX_THREE;
        progressBarExOne.setMax(TOTAL_QUESTIONS_EX_THREE);
        progressBarExOne.setProgress(progress);
        textExOneProgress.setText(score);
    }

    private void overrideActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_EXERCISE_THREE);
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
                imgQuestion.startAnimation(animScaleOutOffset);
            }else if (animation == animScaleOutOffset) {
                imgQuestion.setImageResource(R.drawable.ic_help_outline_black_24dp);
                imgQuestion.startAnimation(animScaleInOffset);
            }else if (animation == animScaleInOffset) {
                isOnTouchKeyOn = true;
                setProgressBar(numberOfQuestion);
            }
        }else {
            if(animation == animPause) {
                onExerciseFinishListener.onExerciseFinish(FRAGMENT_EXERCISE_THREE,
                        totalCorrectAnswers);
            }
        }
        vibrator.cancel();
    }

    private void saveResultExercise() throws IOException{
        int resultPercent = (totalCorrectAnswers*100)/TOTAL_QUESTIONS_EX_THREE;
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy");
        Date d = new Date();
        String date = sdf.format(d);
        database = dataBaseHelper.getReadableDatabase();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_RESULT, resultPercent);
        database.insert(TABLE_NAME_EX_THREE, null, contentValues);
        database.close();
        dataBaseHelper.close();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {    }
}

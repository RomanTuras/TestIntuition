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
import android.content.Context;
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
    private ProgressBar progressBarExOne;
    private ExTextView textExOneProgress;
    private ImageView imgExOneMoon, imgExOneSun, imgExOneQuestion;
    private Animation animScaleIn, animScaleOut, animScaleOutOffset,
            animScaleInOffset, animPause;
    private SoundPool soundPool;
    private Vibrator vibrator;
    private OnExerciseFinishListener onExerciseFinishListener;

    private int sndCorrect, sndWrong;
    private long[] patternVibrator = {0,100,100};
    private byte numberOfQuestion = 23;
    private byte totalCorrectAnswers = 0;
    private byte[] arrayCorrectAnswers;
    private final static byte MOON_BUTTON = 0;
    private final static byte SUN_BUTTON = 1;
    private boolean onTouchKey = true;
    private boolean lastQuestionsKey = false;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.rndHelper = new RndHelper(ID_EXERCISE_ONE);
        this.arrayCorrectAnswers = rndHelper.getArrayCorrectAnswers();
        this.vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        try {
            onExerciseFinishListener = (OnExerciseFinishListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnExerciseFinishListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){

        float textSize = new DisplayMetrics(getActivity().getWindowManager()).getSizeTitle();
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
        textExOneHead.setTextSize(textSize);
        textExOneOr.setTextSize(textSize);

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
        textExOneProgress.setTextSize(textSize);
        progressBarExOne = (ProgressBar) view.findViewById(R.id.progressBarExOne);
        progressBarExOne.getLayoutParams().width = widthImage;
        progressBarExOne.getLayoutParams().height = widthImage;

        mainActivity = (MainActivity) getActivity();

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
        if(onTouchKey) {
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
                vibrator.vibrate(patternVibrator, 0);

                showCorrectImage(arrayCorrectAnswers[numberOfQuestion-1]);
            }
        }
        if(TOTAL_QUESTIONS_EX_ONE == numberOfQuestion){
            onTouchKey = false;
            lastQuestionsKey = true;
            showCorrectImage(arrayCorrectAnswers[numberOfQuestion-1]);
            Log.d("TG", "n = "+numberOfQuestion+" -end!");
        }
    }

    private void showCorrectImage(int correctButton){
        onTouchKey = false;
        imgExOneQuestion.startAnimation(animScaleOut);
        if(correctButton == MOON_BUTTON) {
            imgExOneQuestion.setImageResource(R.drawable.ic_brightness_2_black_48dp);
        }else if(correctButton == SUN_BUTTON){
            imgExOneQuestion.setImageResource(R.drawable.ic_brightness_5_black_48dp);
        }
        imgExOneQuestion.startAnimation(animScaleIn);
        if(lastQuestionsKey) imgExOneQuestion.startAnimation(animPause);


    }

    private void setProgressBar(int progress){
        progress = ++progress;
        String score = progress+"/"+TOTAL_QUESTIONS_EX_ONE;
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
        if(!lastQuestionsKey) {
            if (animation == animScaleIn) {
                imgExOneQuestion.startAnimation(animScaleOutOffset);
                vibrator.cancel();
            }else if (animation == animScaleOutOffset) {
                imgExOneQuestion.setImageResource(R.drawable.ic_help_outline_black_24dp);
                imgExOneQuestion.startAnimation(animScaleInOffset);
            }else if (animation == animScaleInOffset) {
                onTouchKey = true;
                setProgressBar(numberOfQuestion);
            }
        }else {
            if(animation == animPause) {
                Log.d("TG", "totalCorrectAnswers = " + totalCorrectAnswers);
                onExerciseFinishListener.onExerciseFinish(FRAGMENT_EXERCISE_ONE);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {    }
}

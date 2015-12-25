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
import java.util.ArrayList;
import java.util.Date;

import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;
import ua.com.spasetv.testintuitions.helpers.RndHelper;
import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.OnExerciseFinishListener;
import ua.com.spasetv.testintuitions.tools.StaticFields;

public class FragExerciseTwo extends Fragment
        implements StaticFields, View.OnTouchListener, Animation.AnimationListener {

    private MainActivity mainActivity;
    private DataBaseHelper dataBaseHelper;
    private ContentValues contentValues;
    private SQLiteDatabase database;
    private ProgressBar progressBarExTwo;
    private ExTextView textExTwoProgress;
    private Animation animScaleIn, animScaleOut,
            animPauseBeforeOpenAll, animPauseAfterOpenAll;
    private SoundPool soundPool;
    private Vibrator vibrator;
    private OnExerciseFinishListener onExerciseFinishListener;
    private View view;

    private int sndCorrect, sndWrong;
    private int widthImage;
    private boolean isOnTouchKeyOn = true;
    private byte numberOpenButton;
    private int totalCorrectAnswers;
    private int numberRepeatExercise;

    private byte[] arrayAnswers;
    private ArrayList<ImageView> arrayButtons = new ArrayList<>(); // array of Buttons objects
    private int idButtons[] = { R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5,
                            R.id.img6,R.id.img7,R.id.img8,R.id.img9 }; //array id of Buttons


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
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
        float txtSize = new DisplayMetrics(getActivity().getWindowManager()).getSizeTask();
        float txtSizePrgrs = new DisplayMetrics(getActivity().getWindowManager()).getSizeSubTitle();
        widthImage = new DisplayMetrics(getActivity().getWindowManager()).getWidthImage();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        }else{
            createOldSoundPool();
        }
        sndCorrect = soundPool.load(getActivity(), R.raw.correct, PRIORITY);
        sndWrong = soundPool.load(getActivity(), R.raw.wrong, PRIORITY);
        view = inflater.inflate(R.layout.fragment_exercise_two, null);

        if(getActivity()!=null){
            mainActivity = (MainActivity) getActivity();
        }

        animScaleIn = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_scalein);
        animScaleIn.setAnimationListener(this);
        animScaleOut = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_scaleout);

        animPauseBeforeOpenAll = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_pause);
        animPauseBeforeOpenAll.setAnimationListener(this);

        animPauseAfterOpenAll = AnimationUtils.loadAnimation(getActivity(),
                R.anim.anim_pause_two_seconds);
        animPauseAfterOpenAll.setAnimationListener(this);

        ExTextView textExTwoHead = (ExTextView) view.findViewById(R.id.textExTwoHead);
        textExTwoHead.setTextSize(txtSize);

        textExTwoProgress = (ExTextView) view.findViewById(R.id.textExTwoProgress);
        textExTwoProgress.setTextSize(txtSizePrgrs);
        progressBarExTwo = (ProgressBar) view.findViewById(R.id.progressBarExTwo);
        progressBarExTwo.getLayoutParams().width = widthImage;
        progressBarExTwo.getLayoutParams().height = widthImage;

        mainActivity = (MainActivity) getActivity();

        restoreActionBar();
        initButton();
        initExercise();
        initExerciseBeforeRepeat();
        setProgressBar(0);

        return view;
    }

    private void initExercise(){
        numberRepeatExercise = 0;
        totalCorrectAnswers = 0;
    }

    private void initExerciseBeforeRepeat() {
        this.arrayAnswers = new RndHelper(ID_EXERCISE_TWO).getArrayAnswers();
        numberOpenButton = 0;
        printArrayCorrect();
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

    private void restoreActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_EXERCISE_TWO);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(isOnTouchKeyOn) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                int item = arrayButtons.indexOf(view);
                checkAnswer(item);
                Log.d("TG", "view id - "+ item);
                Log.d("TG", "array answers - "+ arrayAnswers[item]);

                view.setOnTouchListener(null); //unset touch listener on the button you pressed
                if(++numberOpenButton == CORRECT_ANSWERS_EX_TWO) {//four buttons is opened
                    if(++numberRepeatExercise < REPEAT_EX_TWO) {//and going to repeat
                        isOnTouchKeyOn = false;
                        Log.d("TG", "once again!");
                        arrayButtons.get(item).startAnimation(animPauseBeforeOpenAll);
                    }else {
                        Log.d("TG", "game over!");
                        isOnTouchKeyOn = false;
                        setProgressBar(numberRepeatExercise);
                        arrayButtons.get(item).startAnimation(animPauseBeforeOpenAll);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mainActivity.overrideActionBar(MAIN_ACTIVITY);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation == animPauseBeforeOpenAll) {
            showAllAnswers();
        }else if(animation == animPauseAfterOpenAll) {
            if(numberRepeatExercise < REPEAT_EX_TWO) {
                for (ImageView img : arrayButtons) {
                    img.startAnimation(animScaleOut);
                    img.setAlpha(0.6f);
                    img.setImageResource(R.drawable.ic_help_outline_black_24dp);
                    img.startAnimation(animScaleIn);
                }
                initExerciseBeforeRepeat();
                setProgressBar(numberRepeatExercise);
                setButtonsTouchListener(); //set touch listener to the all buttons
                isOnTouchKeyOn = true;
            }else {
                try {
                    saveResultExercise();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                onExerciseFinishListener.onExerciseFinish(FRAGMENT_EXERCISE_TWO);
                Log.d("TG", "totalCorrectAnswers - "+ totalCorrectAnswers);
                Log.d("TG", "skill - "+
                        (totalCorrectAnswers*100)/(CORRECT_ANSWERS_EX_TWO * REPEAT_EX_TWO));
            }
        }
    }

    /**Show all answers, count opened smiles, and do pause after that*/
    private void showAllAnswers() {
        soundPool.play(sndCorrect,VOLUME,VOLUME,PRIORITY,LOOP,RATE);
        for(int i = 0; i < arrayAnswers.length; i++) {
            arrayButtons.get(i).startAnimation(animScaleOut);
            if(arrayAnswers[i] == 1) {
                arrayButtons.get(i).setImageResource(R.drawable.smile_48dp);
            }
            else arrayButtons.get(i).setImageResource(R.drawable.no_smile_48dp);
            arrayButtons.get(i).startAnimation(animScaleIn);
        }
        arrayButtons.get(1).startAnimation(animPauseAfterOpenAll);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void checkAnswer(int item) {
        isOnTouchKeyOn = false;
        arrayButtons.get(item).startAnimation(animScaleOut);
        arrayButtons.get(item).setAlpha(1.0f);
        if(arrayAnswers[item] == 1) { //correct answer, show smiley
            soundPool.play(sndCorrect,VOLUME,VOLUME,PRIORITY,LOOP,RATE);
            arrayButtons.get(item).setImageResource(R.drawable.smile_48dp);
            totalCorrectAnswers++;
            Log.d("TG", "Correct!");
        }else { //incorrect answer
            soundPool.play(sndWrong,VOLUME,VOLUME,PRIORITY,LOOP,RATE);
            vibrator.vibrate(200);
            arrayButtons.get(item).setImageResource(R.drawable.no_smile_48dp);
            Log.d("TG", "Wrong! ");
        }
        arrayButtons.get(item).startAnimation(animScaleIn);
        isOnTouchKeyOn = true;
    }

    //TODO delete this method and links to him, before production
    private void printArrayCorrect() {
        for(byte i: arrayAnswers){Log.d("TG", " --> "+i);}
    }

    private void setProgressBar(int progress){
        int textProgress = progress == REPEAT_EX_TWO ? progress : progress+1;
        String score = textProgress+"/"+REPEAT_EX_TWO;
        progressBarExTwo.setMax(REPEAT_EX_TWO);
        progressBarExTwo.setProgress(progress);
        textExTwoProgress.setText(score);
    }

    private void saveResultExercise() throws IOException {
        int resultPercent = (totalCorrectAnswers*100)/(CORRECT_ANSWERS_EX_TWO * REPEAT_EX_TWO);
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy");
        Date d = new Date();
        String date = sdf.format(d);
        Log.d("TG", "date: " + sdf.format(d));
        database = dataBaseHelper.getReadableDatabase();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_RESULT, resultPercent);
        database.insert(TABLE_NAME_EX_TWO, null, contentValues);
        database.close();
        dataBaseHelper.close();
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
}

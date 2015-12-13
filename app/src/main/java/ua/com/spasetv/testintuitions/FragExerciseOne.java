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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ua.com.spasetv.testintuitions.helpers.RndHelper;
import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by Roman Turas on 13/12/2015.
 * Fragment Exercise One.
 */

public class FragExerciseOne extends Fragment
        implements StaticFields, View.OnTouchListener {

    private Activity activity;
    private MainActivity mainActivity;
    private ProgressBar progressBarExOne;
    private ExTextView textExOneProgress;
    private ImageView imgExOneMoon;
    private ImageView imgExOneSun;
    private final static int NUMBER_OF_QUESTIONS = 25;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){

        float textSize = new DisplayMetrics(getActivity().getWindowManager()).getSizeTitle();
        int widthImage = new DisplayMetrics(getActivity().getWindowManager()).getWidthImage();

        View view = inflater.inflate(R.layout.fragment_exercise_one, null);

        ExTextView textExOneHead = (ExTextView) view.findViewById(R.id.textExOneHead);
        ExTextView textExOneOr = (ExTextView) view.findViewById(R.id.textExOneOr);
        textExOneHead.setTextSize(textSize);
        textExOneOr.setTextSize(textSize);

        ImageView imgExOneQuestion = (ImageView) view.findViewById(R.id.imgExOneQuestion);
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
        setProgressBar(0);

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if(view == imgExOneMoon){
                Log.d("TG", "imgExOneMoon touch");
                RndHelper rndHelper = new RndHelper(1);
                rndHelper.getArrayCorrectAnswers();
            }else if(view == imgExOneSun){
                Log.d("TG", "imgExOneSun touch");
            }
        }
        return false;
    }

    private void setProgressBar(int progress){
        progress = ++progress;
        String score = progress+"/"+NUMBER_OF_QUESTIONS;
        progressBarExOne.setMax(NUMBER_OF_QUESTIONS);
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

}

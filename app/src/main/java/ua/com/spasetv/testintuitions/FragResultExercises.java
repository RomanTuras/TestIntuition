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
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import ua.com.spasetv.testintuitions.google_services.Ads;
import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;
import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by Roman Turas on 14/12/2015.
 * Fragment Results of the Exercise.
 *
 * Displays the results of exercise.
 * Parameter - idFragment - passed from bundle.
 * This is class must be polymorphic... we see..
 */

public class FragResultExercises extends Fragment
        implements StaticFields {

    Activity activity;
    private MainActivity mainActivity;
    private Ads ads;
    private Resources res;
    private DataBaseHelper dataBaseHelper;
    private ContentValues contentValues;
    private SQLiteDatabase database;
    private ProgressBar progressSummaryTotal, progressSummaryPercent,
            progressSummaryBest, progressSummarySkill;
    private ExTextView textSummaryHead, textSummaryYourAnswers, textProgressSummaryTotal,
           textProgressSummaryPercent, textProgressSummaryBest, textProgressSummarySkill;

    private byte correctAnswers;
    private int bestPercent;
    private int idFragment;


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
        this.res = getActivity().getResources();
        dataBaseHelper = new DataBaseHelper(activity);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            this.idFragment = bundle.getInt(ID_FRAGMENT);
            this.correctAnswers = bundle.getByte(CORRECT_ANSW);
        }
        Log.d("TG", "idFragment = "+idFragment);
//        ads = new Ads(getActivity());

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        float txtSizeH2 = new DisplayMetrics(getActivity().getWindowManager()).getSizeTextH2();
        float txtSizeH3 = new DisplayMetrics(getActivity().getWindowManager()).getSizeTextH3();
        float txtSizeH4 = new DisplayMetrics(getActivity().getWindowManager()).getSizeTextH4();
        float txtSizeH6 = new DisplayMetrics(getActivity().getWindowManager()).getSizeTextH6();
        int widthImage = new DisplayMetrics(getActivity().getWindowManager()).getWidthImage();
        int widthProgress = widthImage + (widthImage*10)/100;
        View view = inflater.inflate(R.layout.fragment_result_exercise, null);
        if(getActivity()!=null){ mainActivity = (MainActivity) getActivity(); }

        textSummaryHead = (ExTextView) view.findViewById(R.id.textSummaryHead);
        textSummaryYourAnswers = (ExTextView) view.findViewById(R.id.textSummaryYourAnswers);
        textProgressSummaryTotal = (ExTextView) view.findViewById(R.id.textProgressSummaryTotal);
        textProgressSummaryPercent = (ExTextView) view.findViewById(R.id.textProgressSummaryPercent);
        textProgressSummaryBest = (ExTextView) view.findViewById(R.id.textProgressSummaryBest);
        textProgressSummarySkill = (ExTextView) view.findViewById(R.id.textProgressSummarySkill);

        progressSummaryTotal = (ProgressBar) view.findViewById(R.id.progressSummaryTotal);
        progressSummaryPercent = (ProgressBar) view.findViewById(R.id.progressSummaryPercent);
        progressSummaryBest = (ProgressBar) view.findViewById(R.id.progressSummaryBest);
        progressSummarySkill = (ProgressBar) view.findViewById(R.id.progressSummarySkill);

        progressSummaryTotal.getLayoutParams().width = widthProgress;
        progressSummaryTotal.getLayoutParams().height = widthProgress;
        progressSummaryPercent.getLayoutParams().width = widthProgress;
        progressSummaryPercent.getLayoutParams().height = widthProgress;
        progressSummaryBest.getLayoutParams().width = widthProgress;
        progressSummaryBest.getLayoutParams().height = widthProgress;
        progressSummarySkill.getLayoutParams().width = widthProgress;
        progressSummarySkill.getLayoutParams().height = widthProgress;

        textSummaryHead.setTextSize(txtSizeH2);
        textSummaryYourAnswers.setTextSize(txtSizeH4);
        textProgressSummaryTotal.setTextSize(txtSizeH4);
        textProgressSummaryPercent.setTextSize(txtSizeH4);
        textProgressSummaryBest.setTextSize(txtSizeH6);
        textProgressSummarySkill.setTextSize(txtSizeH6);

        overrideActionBar();

        switch(idFragment){
            case FRAGMENT_EXERCISE_ONE: setExerciseOne();
                break;
            case FRAGMENT_EXERCISE_TWO: setExerciseTwo();
                break;
            case FRAGMENT_EXERCISE_THREE: setExerciseThree();
                break;
        }
        return view;
    }

    private void setExerciseOne() {
        textSummaryHead.setText(res.getString(R.string.textSummaryFirstEx));
        String txt = correctAnswers + "/" + TOTAL_QUESTIONS_EX_ONE;
        int max = TOTAL_QUESTIONS_EX_ONE;
        int percent = (correctAnswers*100)/(TOTAL_QUESTIONS_EX_ONE);
        setProgressTotal(txt, max);
        setProgressPercent(percent);
        setProgressBest(ID_EXERCISE_ONE);
    }

    private void setExerciseTwo() {
        textSummaryHead.setText(res.getString(R.string.textSummarySecondEx));
        String txt = correctAnswers+"/"+(CORRECT_ANSWERS_EX_TWO * REPEAT_EX_TWO);
        int max = CORRECT_ANSWERS_EX_TWO * REPEAT_EX_TWO;
        int percent = (correctAnswers*100)/(CORRECT_ANSWERS_EX_TWO * REPEAT_EX_TWO);
        setProgressTotal(txt, max);
        setProgressPercent(percent);
        setProgressBest(ID_EXERCISE_TWO);
    }

    private void setExerciseThree() {
        textSummaryHead.setText(res.getString(R.string.textSummaryThirdEx));
        String txt = correctAnswers + "/" + TOTAL_QUESTIONS_EX_THREE;
        int max = TOTAL_QUESTIONS_EX_THREE;
        int percent = (correctAnswers*100)/(TOTAL_QUESTIONS_EX_THREE);
        setProgressTotal(txt, max);
        setProgressPercent(percent);
        setProgressBest(ID_EXERCISE_THREE);
    }

    private void setProgressTotal(String txt, int max){
        progressSummaryTotal.setMax(max);
        progressSummaryTotal.setProgress(correctAnswers);
        textProgressSummaryTotal.setText(txt);
    }

    private void setProgressPercent(int percent){
        String txt = percent+"%";
        progressSummaryPercent.setMax(100);
        progressSummaryPercent.setProgress(percent);
        textProgressSummaryPercent.setText(txt);
    }

    private void setProgressBest(byte idExercise){
        Resources res = getActivity().getResources();
        String bestDate = getBestResult(idExercise);
        String percent = bestPercent + "%";
        String txt = res.getString(R.string.textSummaryBest) + "\n" + percent + "\n" + bestDate;
        progressSummaryBest.setMax(100);
        progressSummaryBest.setProgress(bestPercent);
        textProgressSummaryBest.setText(txt);
    }

    private void setProgressSkill(int percent){
        String txt = percent+"%";
        progressSummaryPercent.setMax(100);
        progressSummaryPercent.setProgress(percent);
        textProgressSummaryPercent.setText(txt);
    }

    private String getBestResult(byte idExercise) {
        database = dataBaseHelper.getWritableDatabase();
        String table = null;
        String bestDate = null;
        switch (idExercise) {
            case ID_EXERCISE_ONE:
                table = TABLE_NAME_EX_ONE;
                break;
            case ID_EXERCISE_TWO:
                table = TABLE_NAME_EX_TWO;
                break;
            case ID_EXERCISE_THREE:
                table = TABLE_NAME_EX_THREE;
                break;
        }

        String selectQuery ="SELECT * FROM " + table +
                " WHERE result=(SELECT MAX(result) FROM " + table + ") ORDER BY date DESC";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                    bestDate = cursor.getString(1);
                    bestPercent = cursor.getInt(2);
                    Log.d("TG", "tempDate = " + bestDate + "; res = "+bestPercent);
            }
        }
        cursor.close();
        return bestDate;
    }

    private void overrideActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_RESULT);
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

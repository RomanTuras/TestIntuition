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

package ua.com.spasetv.testintuitions.tools;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ua.com.spasetv.testintuitions.R;
import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;

/**
 * Created by Roman Turas on 10/12/2015.
 * Fill arrayList with data required to Cards from Main Screen
 */
public class InitCardViewItems implements StaticFields{
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private ArrayList<ListData> cardViewData;
    private SimpleDateFormat sdf;
    private Date objectDate;
    private String todayDate;
    private Calendar calendar;
    private Resources resources;
    private int[] skill = new int[3];

    public InitCardViewItems(Context context) {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
        sdf = new SimpleDateFormat("d.MM.yyyy");
        objectDate = new Date();
        todayDate = sdf.format(objectDate);
        calendar = Calendar.getInstance();
        resources = context.getResources();
        cardViewData = new ArrayList<>();
        initCardViewItems();
    }

    public void initCardViewItems() {
        initFirstCard();
        initCardsOfExercises();
        initAboutCard();
    }

    /** Fill a first card (Card About) */
    private void initFirstCard() {
        cardViewData.add(new ListData(resources.getString(R.string.titleAbout),
                resources.getString(R.string.descriptionAbout),
                resources.getString(R.string.textIndicatorAbout),
                null,
                R.drawable.ic_help_outline_black_24dp,
                0,
                0));
    }

    /** Fill the following three cards of Exercises
     * number of element in array == idExercise in StaticFields */
    private void initCardsOfExercises() {
        String[] titleExercises = resources.getStringArray(R.array.titleExercise);
        for (int i = 0; i < titleExercises.length; i++) {
            int imgExercise = i == 0 ? R.drawable.exercise_one_48dp :
                    (i == 1 ? R.drawable.exercise_two_48dp_gray : R.drawable.exercise_three_48dp);

            int imgIndicatorLevel = i == 0 ? R.drawable.indicator_easy :
                    (i == 1 ? R.drawable.indicator_normal : R.drawable.indicator_hard);

            String textLevelIndicator = i == 0 ? resources.getString(R.string.textIndicatorEasy) :
                    (i == 1 ? resources.getString(R.string.textIndicatorNormal) :
                            resources.getString(R.string.textIndicatorHard));

            String textIndicatorFrequency = getTextIndicatorFrequency(i);
            if (textIndicatorFrequency == null) {
                textIndicatorFrequency = resources.getString(R.string.textIndicatorNever);
            }
            int imgIndicatorFrequency = getImgIndicatorFrequency(textIndicatorFrequency);


            String textSkill = new Skill(context).getSkillMainCard(i);
            cardViewData.add(new ListData(titleExercises[i],
                    textSkill,
                    textIndicatorFrequency,
                    textLevelIndicator,
                    imgExercise,
                    imgIndicatorFrequency,
                    imgIndicatorLevel));
        }
    }

    /** Fill a last card (Statistic -> store) */
    private void initAboutCard() {
        cardViewData.add(new ListData(resources.getString(R.string.titleEnableStat),
                resources.getString(R.string.descriptionStat2),
                resources.getString(R.string.textIndicatorStore),
                null,
                R.drawable.ic_shopping_cart_black_36dp,
                0,
                0));
    }

    private int getImgIndicatorFrequency(String textIndicator) {
        if(textIndicator == null) return R.drawable.circle_indicator_blank;
        else if(textIndicator.equals(resources.getString(R.string.textIndicatorToday))){
            return R.drawable.circle_indicator_green;
        }else if(textIndicator.equals(resources.getString(R.string.textIndicatorYesterday))) {
            return R.drawable.circle_indicator_yellow;
        }else if(textIndicator.equals(resources.getString(R.string.textIndicatorDaysEgo))) {
            return R.drawable.circle_indicator_orange;
        }else if(textIndicator.equals(resources.getString(R.string.textIndicatorLongTime))) {
            return R.drawable.circle_indicator_red;
        }
        return R.drawable.circle_indicator_blank;
    }

    /** Check date of last exercise in each tables */
    private String getTextIndicatorFrequency(int i){
        String txtIndicatorFr = null;
        String date = null;
        database = dataBaseHelper.getWritableDatabase();
        String table = i == 0 ? TABLE_NAME_EX_ONE :
                (i == 1 ? TABLE_NAME_EX_TWO : TABLE_NAME_EX_THREE);
        Cursor cursor = database.query(table, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToLast()) {
                date = cursor.getString(1);
            }
        }
        if(date != null) txtIndicatorFr = determineDate(date);
        database.close();
        dataBaseHelper.close();
        return txtIndicatorFr;
    }


    /** Convert the difference in dates in to text string: 'today', 'yesterday'... */
    private String determineDate(String date) {
        String txtIndicatorFr = null;

        if(todayDate.equals(date)) {
            txtIndicatorFr = resources.getString(R.string.textIndicatorToday);
            Log.d("TG", "1) determineDate: date = "+date+", calendar = "+sdf.format(calendar.getTime()));
        }

        calendar.setTime(objectDate);
        calendar.add(Calendar.DATE, -1);
        String tmpDate = (sdf.format(calendar.getTime())).toString();
        if(date.equals(tmpDate)){
            txtIndicatorFr = resources.getString(R.string.textIndicatorYesterday);
            Log.d("TG", "2) determineDate: date = "+date+", calendar = "+sdf.format(calendar.getTime()));
        }

        calendar.add(Calendar.DATE, -1);
        tmpDate = (sdf.format(calendar.getTime())).toString();
        if(date.equals(tmpDate)){
            txtIndicatorFr = resources.getString(R.string.textIndicatorDaysEgo);
            Log.d("TG", "3) determineDate: date = "+date+", calendar = "+sdf.format(calendar.getTime()));

        }else if(txtIndicatorFr == null){
            txtIndicatorFr = resources.getString(R.string.textIndicatorLongTime);
            Log.d("TG", "4) determineDate: date = "+date+", calendar = "+sdf.format(calendar.getTime()));

        }

        return txtIndicatorFr;
    }

    public ArrayList<ListData> getArrayList() {
        return cardViewData;
    }
}

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

import ua.com.spasetv.testintuitions.R;
import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;

/**
 * Created by Roman Turas on 27/12/2015.
 * Calculate number of training days and return average value of skill to the last seven days
 * of exercises -> that is in separate formats (to MainScreen or to ResultScreen)
 */
public class Skill implements StaticFields{
    private Context context;
    private Resources resources;
    private int[] skillArray = new int[3];
    private final int DO_DAYS = 7; //number days of exercises before skill is available

    public Skill(Context context){
        this.context = context;
        this.resources = context.getResources();
    }

    /** Generate string of skill in format for main cards
     * Volume of 'doDays' may be from 0 to 7 */
    public String getSkillMainCard(int idExercise){
        int doDays = calculateSkill(idExercise);
        String textSkill = resources.getString(R.string.skill) + " " + skillArray[idExercise] +"% ";
        if(doDays > 1 && doDays < 8) {
            textSkill += resources.getString(R.string.skillBegin) + " " + doDays + " " +
                    resources.getString(R.string.skillDays);
        }else if(doDays == 1) {
            textSkill += resources.getString(R.string.skillBegin) + " " + doDays + " " +
                    resources.getString(R.string.skillDay);
        }
        return textSkill;
    }

    /** Generate string of skill in format for Fragment Result
     * Volume of 'doDays' may be from 0 to 7 */
    public String getSkillToResult(int idExercise){
        int doDays = calculateSkill(idExercise);
        String textSkill = resources.getString(R.string.skill) + "\n" + skillArray[idExercise] + "% ";
        if(doDays > 1 && doDays < 8) {
            textSkill += " " + resources.getString(R.string.textStill) + "\n" + doDays + " " +
                    resources.getString(R.string.skillDays);
        }else if(doDays == 1) {
            textSkill += " " + resources.getString(R.string.textStill) + "\n" + doDays + " " +
                    resources.getString(R.string.skillDay);
        }
        return textSkill;
    }

    public int getSkillPercent(int idExercise){
        return skillArray[idExercise];
    }

    /** Calculate number of training days, if days between 0 and 6 - return
     * how many more days by trainings before skill will be available,
     * if 7 and more -> return average value of skill to the last seven days of exercises */
    private int calculateSkill(int idExercise) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        int doDays = DO_DAYS;
        int skill = 0;
        int numberOfTraining = 0;
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String table = idExercise == 0 ? TABLE_NAME_EX_ONE :
                (idExercise == 1 ? TABLE_NAME_EX_TWO : TABLE_NAME_EX_THREE);

        Cursor cursor = database.query(table, null, null, null, null, null, null);
        if (cursor != null) {
            String tempDate = "";
            if (cursor.moveToLast()) {
                do {
                    if(!tempDate.equals(cursor.getString(1))) {
                        tempDate = cursor.getString(1);
                        skill += cursor.getInt(2);
                        numberOfTraining++;
                        if(--doDays == 0) {
                            cursor.close();
                            database.close();
                            dataBaseHelper.close();
                            this.skillArray[idExercise] = skill/numberOfTraining;
                            return doDays;
                        }
                    }
                } while (cursor.moveToPrevious());
            }
            cursor.close();
        }
        database.close();
        dataBaseHelper.close();
        return doDays;
    }
}

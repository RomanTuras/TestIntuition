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

/**
 * Created by Roman Turas on 26/11/2015.
 * Described static fields that is using from all class
 */
public interface StaticFields {

    /* Id of fragments and activity: */
    byte MAIN_ACTIVITY = 0;
    byte FRAGMENT_ABOUT = 1;
    byte FRAGMENT_EXERCISE_ONE = 2;
    byte FRAGMENT_EXERCISE_TWO = 3;
    byte FRAGMENT_EXERCISE_THREE = 4;
    byte FRAGMENT_STATISTIC = 5;
    byte FRAGMENT_RESULT_EXERCISE = 6;
    String ID_FRAGMENT = "ID_FRAGMENT";

    /* Items from Card View: */
    byte ITEM_ABOUT = 0;
    byte ITEM_EXERCISE_ONE = 1;
    byte ITEM_EXERCISE_TWO = 2;
    byte ITEM_EXERCISE_THREE = 3;
    byte ITEM_STATISTIC = 4;

    /* Tags for fragments */
    String TAG_ABOUT = "ABOUT";
    String TAG_EXERCISE_ONE = "ID_EXERCISE_ONE";
    String TAG_EXERCISE_TWO = "ID_EXERCISE_TWO";
    String TAG_EXERCISE_THREE = "ID_EXERCISE_THREE";
    String TAG_STATISTIC = "STATISTIC";
    String TAG_RESULT_EX = "RESULT_EXERCISE";

    /* id of Exercises */
    byte ID_EXERCISE_ONE = 1;
    byte ID_EXERCISE_TWO = 2;
    byte ID_EXERCISE_THREE = 3;

    /* Describe quantity of questions for exercises */
    byte TOTAL_QUESTIONS_EX_ONE = 25;
    byte TOTAL_QUESTIONS_EX_TWO = 9;
    byte CORRECT_QUESTIONS_EX_TWO = 5;
    byte TOTAL_QUESTIONS_EX_THREE = 20;

    /* Sound stream */
    int MAX_STREAM = 1;
    int SRC_QUALITY = 0;
    int PRIORITY = 0;
    float VOLUME = 1;
    int LOOP = 0;
    float RATE = 1;

    /* Data Base */
    String DB_FILE = "intuition.db";
    String DB_FOLDER = "Intuition";
    String OLD_FILE = "stat.sys";
    String OLD_FOLDER = "ti";
    /* Tables and columns of data base*/
    /* Exercise One */
    String TABLE_NAME_EX_ONE = "ex_one";
    String TABLE_NAME_EX_TWO = "ex_two";
    String TABLE_NAME_EX_THREE = "ex_three";
    String COLUMN_DATE = "date";
    String COLUMN_RESULT = "result";

    /* ADS, Analytics & debug key */
    /* TODO: set isDebugModeOn = FALSE , before add app to production */

    String AD_UNIT_ID = "ca-app-pub-748105251765XXXX/781875XXXX";
    String TRACK_ID = "UA-6647XXXX-2";
    String TEST_DEVICE = "77A1F05FE188A3F51A0FF103708EDEF9";
    boolean isDebugModeOn = true;

}

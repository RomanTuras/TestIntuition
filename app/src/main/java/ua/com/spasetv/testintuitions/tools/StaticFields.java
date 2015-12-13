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

    // Fragment and activity:
    int MAIN_ACTIVITY = 0;
    int FRAGMENT_ABOUT = 1;
    int FRAGMENT_EXERCISE_ONE = 2;
    int FRAGMENT_EXERCISE_TWO = 3;
    int FRAGMENT_EXERCISE_THREE = 4;
    int FRAGMENT_STATISTIC = 5;

    // Items from Card View:
    int ITEM_ABOUT = 0;
    int ITEM_EXERCISE_ONE = 1;
    int ITEM_EXERCISE_TWO = 2;
    int ITEM_EXERCISE_THREE = 3;
    int ITEM_STATISTIC = 4;

    // Tags for fragments
    String TAG_ABOUT = "ABOUT";
    String TAG_EXERCISE_ONE = "EXERCISE_ONE";
    String TAG_EXERCISE_TWO = "EXERCISE_TWO";
    String TAG_EXERCISE_THREE = "EXERCISE_THREE";
    String TAG_STATISTIC = "STATISTIC";

    // id Exercises
    int EXERCISE_ONE = 1;
    int EXERCISE_TWO = 2;
    int EXERCISE_THREE = 3;

    // Describe quantity of questions for exercises
    int TOTAL_QUESTIONS_EX_ONE = 25;
    int TOTAL_QUESTIONS_EX_TWO = 9;
    int CORRECT_QUESTIONS_EX_TWO = 5;
    int TOTAL_QUESTIONS_EX_THREE = 20;

}

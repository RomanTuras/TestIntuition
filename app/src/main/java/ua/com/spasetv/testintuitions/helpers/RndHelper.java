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

package ua.com.spasetv.testintuitions.helpers;

import java.util.Random;

import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by salden on 13/12/2015.
 * Set random numbers, depending what kind exercises called and return filled array
 *
 * @param int idExercise
 */
public class RndHelper implements StaticFields{
    private int[] arrayCorrectAnswers;
    private final Random random = new Random();

    public RndHelper(int idExercise) {
        switch (idExercise){
            case EXERCISE_ONE: setArrayExOne();
                break;
            case EXERCISE_TWO: setArrayExTwo();
                break;
            case EXERCISE_THREE: setArrayExThree();
                break;
        }
    }

    /** Init array on 25 elements. Filled random 0 and 1 */
    private void setArrayExOne() {
        arrayCorrectAnswers = new int[TOTAL_QUESTIONS_EX_ONE];
        for(int i=0; i<TOTAL_QUESTIONS_EX_ONE; i++) arrayCorrectAnswers[i] = random.nextInt(2);
    }

    /** Init array on 9 elements. Set 5 smiles - mark as (1), other - (0) */
    private void setArrayExTwo() {
        arrayCorrectAnswers = new int[TOTAL_QUESTIONS_EX_TWO];
        int c=0;
        while (c<CORRECT_QUESTIONS_EX_TWO){
            for(int i=0;i<TOTAL_QUESTIONS_EX_TWO;i++){
                int x = random.nextInt(9);
                if(arrayCorrectAnswers[x] == 0){
                    arrayCorrectAnswers[x] = 1;
                    if(++c > 4) break;
                }
            }
        }
    }

    /** Init array on 20 elements. Filled random numbers from 0 to 5 */
    private void setArrayExThree() {
        arrayCorrectAnswers = new int[TOTAL_QUESTIONS_EX_THREE];
        for(int i=0; i<TOTAL_QUESTIONS_EX_THREE; i++) arrayCorrectAnswers[i] = random.nextInt(6);
    }

    public int[] getArrayCorrectAnswers(){
        return this.arrayCorrectAnswers;
    }

}

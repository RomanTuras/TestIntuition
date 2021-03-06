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
 */
public class RndHelper implements StaticFields{
    private byte[] arrayAnswers;
    private final Random random = new Random();

    public RndHelper(int idExercise) {
        switch (idExercise){
            case ID_EXERCISE_ONE: setArrayExOne();
                break;
            case ID_EXERCISE_TWO: setArrayExTwo();
                break;
            case ID_EXERCISE_THREE: setArrayExThree();
                break;
            default:
                break;
        }
    }

    /** Init array on 25 elements. Filled random 0 and 1 */
    private void setArrayExOne() {
        arrayAnswers = new byte[TOTAL_QUESTIONS_EX_ONE];
        for(byte i=0; i<TOTAL_QUESTIONS_EX_ONE; i++) arrayAnswers[i] = (byte)random.nextInt(2);
    }

    /** Init array on 9 elements. Set 4 smiles - mark as (1), other - (0) */
    private void setArrayExTwo() {
        arrayAnswers = new byte[TOTAL_QUESTIONS_EX_TWO];
        byte c = 0;
        while (c < CORRECT_ANSWERS_EX_TWO){
            for(byte i = 0 ; i < TOTAL_QUESTIONS_EX_TWO ; i++){
                byte x = (byte) random.nextInt(TOTAL_QUESTIONS_EX_TWO);
                if(arrayAnswers[x] == 0){
                    arrayAnswers[x] = 1;
                    if(++c == CORRECT_ANSWERS_EX_TWO) break;
                }
            }
        }
    }

    /** Init array on 20 elements. Filled random numbers from 0 to 4 */
    private void setArrayExThree() {
        arrayAnswers = new byte[TOTAL_QUESTIONS_EX_THREE];
        for(byte i=0; i<TOTAL_QUESTIONS_EX_THREE; i++) arrayAnswers[i] = (byte)random.nextInt(5);
    }

    public byte[] getArrayAnswers(){
        return this.arrayAnswers;
    }

}

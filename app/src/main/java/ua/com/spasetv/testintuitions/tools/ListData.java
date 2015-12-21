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
 * Created by Roman Turas on 25/11/2015.
 * Container of information for fields in CardView on main screen
 */
public class ListData {
    private String cardTitle;
    private String cardSubTitle;
    private String cardIndicatorFrequency;
    private String cardIndicatorLevel;
    private int imgExercise;
    private int imgIndicatorFrequency;
    private int imgIndicatorLevel;

    public ListData(String cardTitle,
                    String cardSubTitle,
                    String cardIndicatorFrequency,
                    String cardIndicatorLevel,
                    int imgExercise,
                    int imgIndicatorFrequency,
                    int imgIndicatorLevel){

        this.cardTitle = cardTitle;
        this.cardSubTitle = cardSubTitle;
        this.cardIndicatorFrequency = cardIndicatorFrequency;
        this.cardIndicatorLevel = cardIndicatorLevel;
        this.imgExercise = imgExercise;
        this.imgIndicatorFrequency = imgIndicatorFrequency;
        this.imgIndicatorLevel = imgIndicatorLevel;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getCardSubTitle() {
        return cardSubTitle;
    }

    public String getCardIndicatorFrequency() {
        return cardIndicatorFrequency;
    }

    public int getImgExercise() {
        return imgExercise;
    }

    public String getCardIndicatorLevel() {
        return cardIndicatorLevel;
    }

    public int getImgIndicatorFrequency() {
        return imgIndicatorFrequency;
    }

    public int getImgIndicatorLevel() {
        return imgIndicatorLevel;
    }
}

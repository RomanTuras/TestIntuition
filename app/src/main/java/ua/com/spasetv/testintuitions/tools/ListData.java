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
 * Created by salden on 25/11/2015.
 * Container of information for fields in CardView on main screen
 */
public class ListData {
    private String cardTitle;
    private String cardAmountTimes;
    private String cardBestResult;
    private int idImg;

    public ListData(String cardTitle, String cardAmountTimes, String cardBestResult, int idImg){
        this.cardTitle = cardTitle;
        this.cardAmountTimes = cardAmountTimes;
        this.cardBestResult = cardBestResult;
        this.idImg = idImg;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getCardAmountTimes() {
        return cardAmountTimes;
    }

    public String getCardBestResult() {
        return cardBestResult;
    }

    public int getIdImg() {
        return idImg;
    }
}

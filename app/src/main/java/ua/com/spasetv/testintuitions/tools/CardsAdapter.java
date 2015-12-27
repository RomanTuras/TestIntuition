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
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ua.com.spasetv.testintuitions.R;

/**
 * Created by Roman Turas on 25/11/2015.
 * Class CardsAdapter for adding content to Main Activity
 * It working with CardView
 */
public class CardsAdapter implements StaticFields {
    private Context context;
    private ArrayList<ListData> objects;
    private ArrayList<CardView> cardHolders;
    private DisplayMetrics displayMetrics;

    public CardsAdapter(Context context, ArrayList<ListData> arrayList, WindowManager windowManager){
        this.context = context;
        this.objects = arrayList;
        cardHolders = new ArrayList<>();
        displayMetrics = new DisplayMetrics(windowManager);
    }

    public ArrayList setCardsOnLayout(LinearLayout cardsContainer){
        LayoutInflater inflater1 = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int padding = displayMetrics.getPadding();
//        float elevation = displayMetrics.getElevation();
        float sizeTitle = displayMetrics.getSizeTextH1();
        float sizeSubTitle = displayMetrics.getSizeTextH4();
        float widthDisplay = displayMetrics.getWidthDisplay();
//        float heightDisplay = displayMetrics.getHeightDisplay();
//        float dpiDisplay = displayMetrics.getDpiDisplay();
        int widthImage = displayMetrics.getWidthImage();
        int widthImageArrow = displayMetrics.getWidthImageArrow();
        int widthImageIndicatorLevel = displayMetrics.getWidthImageIndicatolLevel();
        int widthImageIndicatorFrequency = displayMetrics.getWidthImageIndicatorFrequency();
        CardView cardView;
        int id = 0;

        for(ListData listData: objects) {
            View view = inflater1.inflate(R.layout.card_view, cardsContainer, false);

            LinearLayout layout_images = (LinearLayout) view.findViewById(R.id.layout_images);
            layout_images.setPadding(0, padding*2, 0, padding*2);

            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_card);
//            frameLayout.setPadding(padding, padding/2, padding, padding/2);

            cardView = (CardView) view.findViewById(R.id.card);
            cardView.setContentPadding(padding, 0, 0, 0);
            cardView.setId(id);
            cardView.setCardBackgroundColor(getColorForCard(++id));
//            cardView.setUseCompatPadding(true);
//            if(Build.VERSION.SDK_INT < 21) cardView.setCardElevation(10.0f);
//            if(Build.VERSION.SDK_INT > 20) cardView.setElevation(10.f);

            /** Set card title */
            ExTextView textTitle = (ExTextView) view.findViewById(R.id.textTitle);
            textTitle.setText(listData.getCardTitle());
            textTitle.setTextSize(sizeTitle);

            /** Set card subtitle */
            ExTextView textSubTitle = (ExTextView) view.findViewById(R.id.textSubTitle);
            textSubTitle.setText(listData.getCardSubTitle());
            textSubTitle.setTextSize(sizeSubTitle);

            /** Set text for frequency indicator */
            ExTextView textFrequencyIndicator =
                    (ExTextView) view.findViewById(R.id.textIndicatorFrequency);
            if(listData.getCardIndicatorFrequency() != null) {
                textFrequencyIndicator.setText(listData.getCardIndicatorFrequency());
                textFrequencyIndicator.setTextSize(sizeSubTitle);
            }else textFrequencyIndicator.setVisibility(View.GONE);

            /** Set text for level indicator */
            ExTextView textLevelIndicator = (ExTextView) view.findViewById(R.id.textIndicatorLevel);
            if(listData.getCardIndicatorLevel() != null) {
                textLevelIndicator.setText(listData.getCardIndicatorLevel());
                textLevelIndicator.setTextSize(sizeSubTitle);
            }else textLevelIndicator.setVisibility(View.GONE);

            /** Set main image of exercise */
            ImageView imgExercise = (ImageView) view.findViewById(R.id.imgExercise);
            imgExercise.setImageResource(listData.getImgExercise());
            imgExercise.getLayoutParams().width = widthImage;
            imgExercise.getLayoutParams().height = widthImage;

            /** Set image Indicator Frequency */
            ImageView imgIndicatorFrequency =
                    (ImageView) view.findViewById(R.id.imgIndicatorFrequency);
            if(listData.getImgIndicatorFrequency() != 0) {
                imgIndicatorFrequency.setBackgroundResource(listData.getImgIndicatorFrequency());
                imgIndicatorFrequency.getLayoutParams().width = widthImageIndicatorFrequency;
                imgIndicatorFrequency.getLayoutParams().height = widthImageIndicatorFrequency;
            }else imgIndicatorFrequency.setVisibility(View.GONE);

            /** Set image Indicator Level */
            ImageView imgIndicatorLevel =
                    (ImageView) view.findViewById(R.id.imgIndicatorLevel);
            if(listData.getImgIndicatorLevel() != 0) {
                imgIndicatorLevel.setBackgroundResource(listData.getImgIndicatorLevel());
                imgIndicatorLevel.getLayoutParams().width = widthImageIndicatorLevel;
                imgIndicatorLevel.getLayoutParams().height = widthImageIndicatorLevel;
            }else imgIndicatorLevel.setVisibility(View.GONE);

            /** Set arrow image on right-side */
            ImageView imageViewArrow = (ImageView) view.findViewById(R.id.img_arrow);
            imageViewArrow.getLayoutParams().width = widthImageArrow;
            imageViewArrow.getLayoutParams().height = widthImageArrow;

            /** Add all view to card layout */
            cardsContainer.addView(frameLayout);
            cardHolders.add(cardView);
        }
        return cardHolders;
    }

    /**Set background color, different for each card*/
    private int getColorForCard(int id){
        int color = 0;
        switch (id){
            case 1: color = context.getResources().getColor(R.color.colorAboutBackground);
                break;
            case 2: color = context.getResources().getColor(R.color.colorExOneBackground);
                break;
            case 3: color = context.getResources().getColor(R.color.colorExTwoBackground);
                break;
            case 4: color = context.getResources().getColor(R.color.colorExThreeBackground);
                break;
            case 5: color = context.getResources().getColor(R.color.colorStatisticBackground);
                break;
        }
        return color;
    }

}
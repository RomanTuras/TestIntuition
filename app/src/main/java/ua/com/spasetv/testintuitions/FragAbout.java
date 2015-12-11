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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by Roman Turas on 11/12/2015.
 * Fragment About.
 * Show help about intuition.
 */

public class FragAbout extends Fragment
        implements StaticFields {

    private Activity activity;
    private View view;
    private MainActivity mainActivity;
    private ImageView imageView;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        this.view = inflater.inflate(R.layout.fragment_about, null);

        if(getActivity()!=null){
            mainActivity = (MainActivity) getActivity();
        }
        showTextHelp();
        restoreActionBar();

        return view;
    }

    private void restoreActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_ABOUT);
    }

    private void showTextHelp() {
        ExTextView textAboutOne = (ExTextView) view.findViewById(R.id.textAboutOne);
        ExTextView textAboutTwo = (ExTextView) view.findViewById(R.id.textAboutTwo);
        ExTextView textAboutThree = (ExTextView) view.findViewById(R.id.textAboutThree);

        float textSize = new DisplayMetrics(getActivity().getWindowManager()).getSizeTitle();
        textAboutOne.setTextSize(textSize);
        textAboutTwo.setTextSize(textSize);
        textAboutThree.setTextSize(textSize);

        imageView = (ImageView) view.findViewById(R.id.imgBannerAbout);
        setReducedImageSize();

        textAboutOne.setText(makeStringFromArray(R.array.textAboutOne));
        textAboutTwo.setText(makeStringFromArray(R.array.textAboutTwo));
        textAboutThree.setText(makeStringFromArray(R.array.textAboutThree));
    }

    /**Combines all rows of the array in one string*/
    private String makeStringFromArray(int idArray){
        String tmpString = "";
        String[] arrayTextAbout = getResources().getStringArray(idArray);
        for(String string: arrayTextAbout){
            tmpString+=string;
        }
        return tmpString;
    }

    /**Resize image depending screen size*/
    void setReducedImageSize(){
        float widthDisplay = new DisplayMetrics(getActivity().getWindowManager()).getWidthDisplay();
        float padding = new DisplayMetrics(getActivity().getWindowManager()).getPadding();
        int targetImageViewWidth = (int)(widthDisplay-padding*2);
        int targetImageViewHeight = targetImageViewWidth/2;

        imageView.getLayoutParams().width = targetImageViewWidth;
        imageView.getLayoutParams().height = targetImageViewHeight;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

}

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

import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Roman Turas on 09/12/2015.
 * Assigns values depending from display resolution.
 */
public class DisplayMetrics {

    WindowManager windowManager;
    private int widthImage;
    private int widthImageArrow;
    private int padding;
    private float elevation;
    private float widthDisplay;
    private float heightDisplay;
    private float dpiDisplay;
    private float sizeTitle;
    private float sizeSubTitle;

    public DisplayMetrics(WindowManager windowManager){
        this.windowManager = windowManager;

        Display display = windowManager.getDefaultDisplay();
        android.util.DisplayMetrics metrics = new android.util.DisplayMetrics();
        display.getMetrics(metrics);

        widthDisplay = metrics.widthPixels;
        heightDisplay = metrics.heightPixels;
        dpiDisplay = metrics.densityDpi;
        sizeTitle = (widthDisplay/20)*(160/dpiDisplay);
        sizeSubTitle = (widthDisplay/28)*(160/dpiDisplay);
        widthImage = (int)((widthDisplay/5)*(160/dpiDisplay));
        widthImageArrow = (int)((widthDisplay/15)*(160/dpiDisplay));
        padding = (int)((widthDisplay/48)*(160/dpiDisplay));
        elevation = (widthDisplay/24)*(160/dpiDisplay);
    }

    public int getPadding(){
        return this.padding;
    }

    public float getElevation(){
        return this.elevation;
    }

    public float getSizeTitle(){
        return this.sizeTitle;
    }

    public float getSizeSubTitle(){
        return this.sizeSubTitle;
    }

    public int getWidthImage(){
        return widthImage;
    }

    public int getWidthImageArrow(){
        return widthImageArrow;
    }

    public float getWidthDisplay(){
        return widthDisplay;
    }

    public float getHeightDisplay(){
        return heightDisplay;
    }

    public float getDpiDisplay(){
        return dpiDisplay;
    }
}

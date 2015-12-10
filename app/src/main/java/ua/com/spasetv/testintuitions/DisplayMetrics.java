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

import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by salden on 09/12/2015.
 * Assigns values to static variables depending from display resolution.
 */
public class DisplayMetrics {

    WindowManager windowManager;
    int widthImage;
    int widthImageArrow;
    int padding;
    float elevation;
    float width;
    float high;
    float dpi;
    float sizeTitle;
    float sizeSubTitle;


    public DisplayMetrics(WindowManager windowManager){

        this.windowManager = windowManager;

        Display display = windowManager.getDefaultDisplay();
        android.util.DisplayMetrics metrics = new android.util.DisplayMetrics();
        display.getMetrics(metrics);

        width = metrics.widthPixels;
        high = metrics.heightPixels;
        dpi = metrics.densityDpi;
        sizeTitle = (width/20)*(160/dpi);
        sizeSubTitle = (width/28)*(160/dpi);
        widthImage = (int)((width/5)*(160/dpi));
        widthImageArrow = (int)((width/15)*(160/dpi));
        padding = (int)((width/48)*(160/dpi));
        elevation = (width/24)*(160/dpi);

        Log.d("TG", "sizeTitle " + sizeTitle + "  sizeSubTitle " + sizeSubTitle);
        Log.d("TG", "widthImage "+widthImage+"  widthImageArrow "+widthImageArrow);
        Log.d("TG", "elevation "+elevation);
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
}

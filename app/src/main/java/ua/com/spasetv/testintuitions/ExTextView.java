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

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by salden on 07/12/2015.
 * Overriding class TextView for adding TypeFace in each TextView in this activity
 */

public class ExTextView extends AppCompatTextView{

    String fontDroidSans = "fonts/DroidSans.ttf";
    Typeface CF;

    public ExTextView(Context context){
        super(context);
        CF = Typeface.createFromAsset(context.getAssets(), fontDroidSans);
        setTypeface(CF);
    }

    public ExTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        CF = Typeface.createFromAsset(context.getAssets(), fontDroidSans);
        setTypeface(CF);
    }

    public ExTextView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        CF = Typeface.createFromAsset(context.getAssets(), fontDroidSans);
        setTypeface(CF);
    }

    @Override
    public void setTypeface(android.graphics.Typeface tf, int style) {
        super.setTypeface(tf);
    }
}
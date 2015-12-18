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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.spasetv.testintuitions.google_services.Ads;
import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by Roman Turas on 14/12/2015.
 * Fragment Results of the Exercise.
 *
 * Displays the results of exercise.
 * Parameter - idFragment - passed from bundle.
 * This is class must be polymorphic... we see..
 */

public class FragResultExercise extends Fragment
        implements StaticFields {

    Activity activity;
    MainActivity mainActivity;
    private int idFragment;
    Ads ads;


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
        Bundle bundle = this.getArguments();
        if(bundle != null) idFragment = bundle.getInt(ID_FRAGMENT);
        Log.d("TG", "idFragment = "+idFragment);

//        ads = new Ads(getActivity());

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_result_exercise, null);

        if(getActivity()!=null){
            mainActivity = (MainActivity) getActivity();
        }

        restoreActionBar();
        return view;
    }

    private void restoreActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_STATISTIC);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mainActivity.overrideActionBar(MAIN_ACTIVITY);
    }

}

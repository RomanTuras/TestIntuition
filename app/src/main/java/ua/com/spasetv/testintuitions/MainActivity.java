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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ua.com.spasetv.testintuitions.tools.CardsAdapter;
import ua.com.spasetv.testintuitions.tools.InitCardViewItems;
import ua.com.spasetv.testintuitions.tools.StaticFields;


/**
 * Main class, start point of project.
 */

public class MainActivity extends AppCompatActivity
        implements StaticFields, View.OnClickListener{

    ArrayList<CardView> cardHolders;
    Fragment fragAbout, fragExerciseOne,
            fragExerciseTwo, fragExerciseThree, fragStatistic;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static LinearLayout cardsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_apps_white_18dp);

        SpannableString s = new SpannableString("qerqewtew");
        s.setSpan(new TypefaceSpan("fonts/DroidSans.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if(getSupportActionBar() != null) getSupportActionBar().setTitle(s);

        cardsContainer = (LinearLayout) findViewById(R.id.cards_container);
        CardsAdapter cardsAdapter = new CardsAdapter(this,
                new InitCardViewItems(this).getArrayList(), getWindowManager());
        /**
         * Salden:
         * IMPORTANT!!!! If set onClickListener added to cardView in ANOTHER class - this will
         * cause an ERRORS (Like back stack of fragment etc..)
         * Make cardView.setOnClickListener ONLY from MainActivity!!!!!!!!!
         */
        cardHolders = cardsAdapter.setCardsOnLayout(cardsContainer);
        for(CardView cardView: cardHolders){
            if(cardView!=null)
                cardView.setOnClickListener(this);
        }

    }


    /**Set new title and show back-arrow or app-icon to ActionBar depending from attached fragment*/
    public void overrideActionBar(int key){
        boolean enabledHomeArrow = true;
        String title = "";
        switch (key){
            case MAIN_ACTIVITY:
                enabledHomeArrow = false;
                title = getString(R.string.app_name);
                break;
            case FRAGMENT_ABOUT:
                title = getString(R.string.titleAbout);
                break;
            case FRAGMENT_EXERCISE_ONE:
                title = getString(R.string.titleExerciseOne);
                break;
            case FRAGMENT_EXERCISE_TWO:
                title = getString(R.string.titleExerciseTwo);
                break;
            case FRAGMENT_EXERCISE_THREE:
                title = getString(R.string.titleExerciseThree);
                break;
            case FRAGMENT_STATISTIC:
                title = getString(R.string.titleStatistic);
                break;
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabledHomeArrow);
            getSupportActionBar().setTitle(title);
            if(!enabledHomeArrow) toolbar.setNavigationIcon(R.drawable.ic_apps_white_18dp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            // some event
        }else if(id == android.R.id.home){
            /**If Home pressed from any fragment - return to the Main Screen*/
            if(getSupportActionBar().getTitle()!=
                    getString(R.string.app_name))
                transactionFragments();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        transactionFragments();
    }

    private void transactionFragments(){
        Fragment fragTmp = null;

        if(fragAbout != null && fragAbout.isAdded()){
            overrideActionBar(MAIN_ACTIVITY);
            fragTmp = fragAbout;
        }else if (fragExerciseOne != null && fragExerciseOne.isAdded()){
            overrideActionBar(MAIN_ACTIVITY);
            fragTmp = fragExerciseOne;
        }else if (fragExerciseTwo != null && fragExerciseTwo.isAdded()){
            overrideActionBar(MAIN_ACTIVITY);
            fragTmp = fragExerciseTwo;
        }else if (fragExerciseThree != null && fragExerciseThree.isAdded()){
            overrideActionBar(MAIN_ACTIVITY);
            fragTmp = fragExerciseThree;
        }else if (fragStatistic != null && fragStatistic.isAdded()){
            overrideActionBar(MAIN_ACTIVITY);
            fragTmp = fragStatistic;
        }

        if(fragTmp!=null){
            fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.remove(fragTmp).commit();
        }else finish();
    }

    /**Attach a necessary fragment to activity, depending from select card*/
    @Override
    public void onClick(View view){
        /**
         * Salden:
         * Attention! Use android.support.v4.app when working with Fragments
         * Check all imports (in each Classes)
         * Use "getSupportFragmentManager" instead "getFragmentManager"
         */
        fragmentManager = getSupportFragmentManager();

        switch (view.getId()){
            case ITEM_ABOUT:
                fragAbout = new FragAbout();
                if(!fragAbout.isAdded()){
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragAbout, "FRAG_ABOUT");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_ONE:
                fragExerciseOne = new FragExerciseOne();
                if(!fragExerciseOne.isAdded()){
                    Log.d("TG", "fragExerciseOne.isAdded");
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragExerciseOne, "FRAG_EXERCISE_ONE");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_TWO:
                fragExerciseTwo = new FragExerciseTwo();
                if(!fragExerciseTwo.isAdded()){
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragExerciseTwo, "FRAG_EXERCISE_TWO");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_THREE:
                fragExerciseThree = new FragExerciseThree();
                if(!fragExerciseThree.isAdded()){
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragExerciseThree, "FRAG_EXERCISE_THREE");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_STATISTIC:
                fragStatistic = new FragStatistic();
                if(!fragStatistic.isAdded()){
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragStatistic, "FRAG_STATISTIC");
                    fragmentTransaction.commit();
                }
                break;
        }
    }

}

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

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import ua.com.spasetv.testintuitions.google_services.Analytics;
import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;
import ua.com.spasetv.testintuitions.tools.CardsAdapter;
import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.InitCardViewItems;
import ua.com.spasetv.testintuitions.tools.OnExerciseFinishListener;
import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Main class, start point of project.
 */

public class MainActivity extends AppCompatActivity
        implements StaticFields, View.OnClickListener, OnExerciseFinishListener {

    private ArrayList<CardView> cardHolders;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;
    private LinearLayout cardsContainer, layoutWait;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardsContainer = (LinearLayout) findViewById(R.id.cards_container);

        layoutWait = (LinearLayout) findViewById(R.id.layoutWait);

        overrideActionBar(MAIN_ACTIVITY);

        try {
            initDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showPauseScreen();
        DisplayMetrics metrics = new DisplayMetrics(getWindowManager());
        String label = "h: " + metrics.getHeightDisplay() + " x w: " +
                metrics.getWidthDisplay() + " x d: " + metrics.getDpiDisplay();
        new Analytics(this).sendAnalytics("Test Intuition","Main Screen","Start app", label);
    }

    private void showPauseScreen() {
        layoutWait.setVisibility(View.VISIBLE);
    }

    private void hidePauseScreen() {
        layoutWait.setVisibility(View.GONE);
    }

    public void refreshMainScreen(){
        InitCardViewItems initCardViewItems = new InitCardViewItems(this);
        CardsAdapter cardsAdapter = new CardsAdapter(this, initCardViewItems.getArrayList(),
                getWindowManager());
        cardsContainer.removeAllViews();
        /**
         * Roman Turas:
         * IMPORTANT! If set onClickListener added to cardView in ANOTHER class - this will
         * cause an ERRORS (Like back stack of fragment etc..)
         * Make cardView.setOnClickListener ONLY from MainActivity!
         */
        cardHolders = cardsAdapter.setCardsOnLayout(cardsContainer);
        for(CardView cardView: cardHolders){
            if(cardView!=null) cardView.setOnClickListener(this);
        }
    }

    /** Make database (if not) search and move old result, delete old file (if it is)*/
    private void initDataBase() throws IOException {
        final MyHandler handler=new MyHandler(this);
        Thread t = new Thread(new Runnable() {
            public void run() {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                try {
                    dataBaseHelper.createDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                database.close();
                dataBaseHelper.close();
                handler.sendEmptyMessage(1);
            }
        });
        t.start();
    }

    /**Set new title and show back-arrow or app-icon to ActionBar depending from attached fragment*/
    public void overrideActionBar(byte key){
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
            case FRAGMENT_RESULT:
                title = getString(R.string.titleResults);
                break;
            case FRAGMENT_STATISTIC:
                title = getString(R.string.titleStatistic);
                break;
            default:
                enabledHomeArrow = false;
                title = getString(R.string.app_name);
                break;
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabledHomeArrow);
            getSupportActionBar().setTitle(title);

            if(!enabledHomeArrow) {
                toolbar.setNavigationIcon(R.drawable.ic_remove_red_eye_white_24dp);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == 0){
            // some event
        }else if(id == android.R.id.home){
            /**If Home pressed from any fragment - return to the Main Screen*/
            if(getSupportActionBar().getTitle()!=
                    getString(R.string.app_name))
                removeFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        removeFragment();
    }

    /** If fragment is attached ,remove him, override action bar, make visible main layout
     * If back pressed on main screen -> finish
     * When back pressed from Fragment Statistic -> refresh information for all cards */
    private void removeFragment(){
        String title = getString(R.string.app_name);
        if(fragment!=null && !getSupportActionBar().getTitle().equals(title)){
            fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.remove(fragment).commit();
            cardsContainer.setVisibility(View.VISIBLE);
        }else if(getSupportActionBar().getTitle().equals(title)) finish();
    }

    private void callFragmentResult(byte idFragment, byte totalCorrectAnswers) {
        if (fragment != null) {
            Bundle bundle = new Bundle();
            switch (idFragment){
                case FRAGMENT_EXERCISE_ONE:
                    fragment = new FragResultExercises();
                    bundle.putInt(ID_FRAGMENT, FRAGMENT_EXERCISE_ONE);
                    bundle.putByte(CORRECT_ANSW, totalCorrectAnswers);
                    refreshMainScreen();
                    break;
                case FRAGMENT_EXERCISE_TWO:
                    fragment = new FragResultExercises();
                    bundle.putInt(ID_FRAGMENT, FRAGMENT_EXERCISE_TWO);
                    bundle.putByte(CORRECT_ANSW, totalCorrectAnswers);
                    refreshMainScreen();
                    break;
                case FRAGMENT_EXERCISE_THREE:
                    fragment = new FragResultExercises();
                    bundle.putInt(ID_FRAGMENT, FRAGMENT_EXERCISE_THREE);
                    bundle.putByte(CORRECT_ANSW, totalCorrectAnswers);
                    refreshMainScreen();
                    break;
                default:
                    break;
            }
            fragment.setArguments(bundle);
            fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment).commit();
        }
    }

    /**Attach a necessary fragment to activity, depending from select card*/
    @Override
    public void onClick(View view){
        /**
         * Roman Turas:
         * Attention! Use android.support.v4.app when working with Fragments
         * Check all imports (in each Classes)
         * Use "getSupportFragmentManager" instead "getFragmentManager"
         */
        fragmentManager = getSupportFragmentManager();

        switch ((byte)view.getId()){
            case ITEM_ABOUT:
                fragment = new FragAbout();
                if(!fragment.isAdded()) addFragment(TAG_ABOUT);
                break;

            case ITEM_EXERCISE_ONE:
                fragment = new FragExerciseOne();
                if(!fragment.isAdded()) addFragment(TAG_EXERCISE_ONE);
                break;

            case ITEM_EXERCISE_TWO:
                fragment = new FragExerciseTwo();
                if(!fragment.isAdded()) addFragment(TAG_EXERCISE_TWO);
                break;

            case ITEM_EXERCISE_THREE:
                fragment = new FragExerciseThree();
                if(!fragment.isAdded()) addFragment(TAG_EXERCISE_THREE);
                break;

            case ITEM_STATISTIC:
                fragment = new FragStatistic();
                if(!fragment.isAdded()) addFragment(TAG_STATISTIC);
                break;
            default:
                break;
        }
    }

    private void addFragment(String tag){
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, tag);
        fragmentTransaction.commit();
        cardsContainer.setVisibility(View.GONE);
    }

    @Override
    public void onExerciseFinish(byte idFragment, byte totalCorrectAnswers) {
        switch (idFragment){
            case FRAGMENT_EXERCISE_ONE:
                callFragmentResult(FRAGMENT_EXERCISE_ONE, totalCorrectAnswers);
                break;
            case FRAGMENT_EXERCISE_TWO:
                callFragmentResult(FRAGMENT_EXERCISE_TWO, totalCorrectAnswers);
                break;
            case FRAGMENT_EXERCISE_THREE:
                callFragmentResult(FRAGMENT_EXERCISE_THREE, totalCorrectAnswers);
                break;
            default:
                break;
        }

    }


    public static class MyHandler extends Handler {
        WeakReference<MainActivity> wrActivity;

        public MyHandler(MainActivity activity) {
            wrActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = wrActivity.get();
            if (activity != null) {
                if(msg.what == 1) {
                    wrActivity.get().hidePauseScreen();
                    wrActivity.get().refreshMainScreen();
                }
            }

        }
    }
}

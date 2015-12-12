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

    private ArrayList<CardView> cardHolders;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;
    private LinearLayout cardsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overrideActionBar(MAIN_ACTIVITY);

        cardsContainer = (LinearLayout) findViewById(R.id.cards_container);
        CardsAdapter cardsAdapter = new CardsAdapter(this,
                new InitCardViewItems(this).getArrayList(), getWindowManager());
        /**
         * Roman Turas:
         * IMPORTANT! If set onClickListener added to cardView in ANOTHER class - this will
         * cause an ERRORS (Like back stack of fragment etc..)
         * Make cardView.setOnClickListener ONLY from MainActivity!
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

    /** If fragment is attached ,remove him, override action bar, make visible main layout
     * If back pressed on main screen -> finish */
    private void transactionFragments(){
        String title = getString(R.string.app_name);
        if(fragment!=null && !getSupportActionBar().getTitle().equals(title)){
            fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.remove(fragment).commit();
            cardsContainer.setVisibility(View.VISIBLE);
        }else if(getSupportActionBar().getTitle().equals(title)) finish();
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

        switch (view.getId()){
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
        }
    }

    private void addFragment(String tag){
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, tag);
        fragmentTransaction.commit();
        cardsContainer.setVisibility(View.GONE);
    }

}

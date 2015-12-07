package ua.com.spasetv.testintuitions;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements StaticFields, View.OnClickListener {

    ArrayList<ListData> arrayList;
    ArrayList<CardView> cardHolders;
    Fragment fragAbout, fragExerciseOne,
            fragExerciseTwo, fragExerciseThree, fragStatistic;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    Context context;
    public static LinearLayout cardsContainer;
    public static float sizeTitle;
    public static float sizeSubTitle;
    public static int widthImage;
    public static int widthImageArrow;
    public static float padding10;
    public static float padding5;
    public static float elevation;
    public static float width;
    public static float high;
    public static float dpi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = getBaseContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_apps_white_18dp);

        fragAbout = null;
        fragExerciseOne = null;
        fragExerciseTwo = null;
        fragExerciseThree = null;
        fragStatistic = null;

        initDisplay();
        initMainListItems();

        cardsContainer = (LinearLayout) findViewById(R.id.cards_container);
        CardsAdapter cardsAdapter = new CardsAdapter(this, arrayList);
        /**
         * Salden:
         * IMPORTANT!!!! If set onClickListener added to cardView in ANOTHER class - this will
         * cause an ERRORS (Like back stack of fragment e.t.c.)
         * Make cardView.setOnClickListener ONLY from MainActivity!!!!!!!!!
         */
        cardHolders = cardsAdapter.setCardsOnLayout(cardsContainer);
        for(CardView cardView: cardHolders){
            if(cardView!=null)
                cardView.setOnClickListener(this);
        }

    }

    public void initDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrix = new DisplayMetrics();
        display.getMetrics(metrix);
        width = metrix.widthPixels;
        high = metrix.heightPixels;
        dpi = metrix.densityDpi;
        sizeTitle = (width/20)*(160/dpi);
        sizeSubTitle = (width/28)*(160/dpi);
        widthImage = (int)((width/5)*(160/dpi));
        widthImageArrow = (int)((width/15)*(160/dpi));
        padding10 = (width/48)*(160/dpi);
        padding5 = padding10/2;
        elevation = (width/24)*(160/dpi); /// !!!!!!!!!!!!!
        Log.d("TG", "sizeTitle "+sizeTitle+"  sizeSubTitle "+sizeSubTitle);
        Log.d("TG", "widthImage "+widthImage+"  widthImageArrow "+widthImageArrow);
        Log.d("TG", "elevation "+elevation);
    }

    public void refreshActionBar(int key){
        switch (key){
            case MAIN_ACTIVITY:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toolbar.setNavigationIcon(R.drawable.ic_apps_white_18dp);
                getSupportActionBar().setTitle(R.string.app_name);
                break;
            case FRAGMENT_ABOUT:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.titleAbout);
                break;
            case FRAGMENT_EXERCISE_ONE:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.titleExerciseOne);
                break;
            case FRAGMENT_EXERCISE_TWO:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.titleExerciseTwo);
                break;
            case FRAGMENT_EXERCISE_THREE:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.titleExerciseThree);
                break;
            case FRAGMENT_STATISTIC:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.titleStatistic);
                break;
        }
    }

    public void initMainListItems(){
        arrayList = new ArrayList<>();
        Resources res = getResources();
        int amountTimes = 0;
        int bestResult = 0;
        String dateOfBestResult = "01.01.1953";
        String [] titleExercises = getResources().getStringArray(R.array.titleExercise);

        arrayList.add(new ListData(res.getString(R.string.titleAbout),
                res.getString(R.string.descriptionAbout), null, R.drawable.ic_help_outline_black_24dp));

        int i = 0;
        for (String titleEx: titleExercises) {
            int image = i==0?R.drawable.ic_grid_off_black_48dp:
                    (i==1?R.drawable.ic_blur_linear_black_48dp:R.drawable.ic_healing_black_48dp);
            arrayList.add(new ListData(titleEx,
                    res.getString(R.string.amount)+" "+amountTimes+" "+
                            res.getString(R.string.times),
                    res.getString(R.string.bestResult)+" "+bestResult+
                            res.getString(R.string.was)+" "+dateOfBestResult,
                    image));
            i++;
        }

        arrayList.add(new ListData(res.getString(R.string.titleEnableStat),
                res.getString(R.string.descriptionStat),
                res.getString(R.string.descriptionStat2),
                R.drawable.ic_shopping_cart_black_36dp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            fragmentManager = getSupportFragmentManager();
            fragAbout = new FragAbout();
            if(!fragAbout.isAdded()) {
                Log.d("TG", "fragAbout.isAdded == false");
                fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragAbout, "FRAG_ABOUT");
                fragmentTransaction.commit();
            }
            return true;
        }else if(id == android.R.id.home){
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

        if(fragAbout != null && fragAbout.isAdded()) {
            refreshActionBar(MAIN_ACTIVITY);
            fragTmp = fragAbout;
        }else if (fragExerciseOne != null && fragExerciseOne.isAdded()) {
            refreshActionBar(MAIN_ACTIVITY);
            fragTmp = fragExerciseOne;
        }else if (fragExerciseTwo != null && fragExerciseTwo.isAdded()) {
            refreshActionBar(MAIN_ACTIVITY);
            fragTmp = fragExerciseTwo;
        }else if (fragExerciseThree != null && fragExerciseThree.isAdded()) {
            refreshActionBar(MAIN_ACTIVITY);
            fragTmp = fragExerciseThree;
        }else if (fragStatistic != null && fragStatistic.isAdded()) {
            refreshActionBar(MAIN_ACTIVITY);
            fragTmp = fragStatistic;
        }


        Log.d("TG", "fragTmp ="+fragTmp);
        if(fragTmp!=null) {
            fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.remove(fragTmp).commit();
        }else finish();
    }


    @Override
    public void onClick(View view) {
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
                if(!fragAbout.isAdded()) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragAbout, "FRAG_ABOUT");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_ONE:
                fragExerciseOne = new FragExerciseOne();
                if(!fragExerciseOne.isAdded()) {
                    Log.d("TG", "fragExerciseOne.isAdded");
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragExerciseOne, "FRAG_EXERCISE_ONE");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_TWO:
                fragExerciseTwo = new FragExerciseTwo();
                if(!fragExerciseTwo.isAdded()) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragExerciseTwo, "FRAG_EXERCISE_TWO");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_THREE:
                fragExerciseThree = new FragExerciseThree();
                if(!fragExerciseThree.isAdded()) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragExerciseThree, "FRAG_EXERCISE_THREE");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_STATISTIC:
                fragStatistic = new FragStatistic();
                if(!fragStatistic.isAdded()) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragStatistic, "FRAG_STATISTIC");
                    fragmentTransaction.commit();
                }
                break;
        }
    }
}

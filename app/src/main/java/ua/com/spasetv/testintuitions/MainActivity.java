package ua.com.spasetv.testintuitions;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements StaticFields, View.OnClickListener {

    ArrayList<ListData> arrayList;
    Fragment fragAbout, fragExerciseOne,
            fragExerciseTwo, fragExerciseThree, fragStatistic;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static float sizeTitle;
    public static float sizeSubTitle;
    public static int widthImage;
    public static int widthImageArrow;
    public static float padding10;
    public static float padding5;
    public static float elevation;
    public static float width;
    public static float hight;
    public static float dpi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        LinearLayout cardsContainer = (LinearLayout) findViewById(R.id.cards_container);
        CardsAdapter cardsAdapter = new CardsAdapter(this, arrayList);
        cardsAdapter.setCardsOnLayout(cardsContainer);

    }

    public void initDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrix = new DisplayMetrics();
        display.getMetrics(metrix);
        width = metrix.widthPixels;
        hight = metrix.heightPixels;
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
                res.getString(R.string.descriptionAbout), null, R.drawable.ic_blur_linear_black_48dp));

        for (String titleEx: titleExercises) {
            arrayList.add(new ListData(titleEx,
                    res.getString(R.string.amount)+" "+amountTimes+" "+
                            res.getString(R.string.times),
                    res.getString(R.string.bestResult)+" "+bestResult+
                            res.getString(R.string.was)+" "+dateOfBestResult,
                    R.drawable.ic_blur_linear_black_48dp));
        }

        arrayList.add(new ListData(res.getString(R.string.titleEnableStat),
                res.getString(R.string.descriptionStat),
                res.getString(R.string.descriptionStat2),
                R.drawable.ic_blur_linear_black_48dp));
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
            return true;
        }else if(id == android.R.id.home){
            if(getSupportActionBar().getTitle()!=
                    getString(R.string.app_name))
                transactionFragments();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        fragmentManager = getFragmentManager();

        switch (position){
            case ITEM_ABOUT:
                fragAbout = new FragAbout();
                if(!fragAbout.isAdded()) {
                    Log.d("TG", "fragAbout.isAdded == false");
                    fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .add(R.id.container, fragAbout, "FRAG_ABOUT");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_ONE:
                fragExerciseOne = new FragExerciseOne();
                if(!fragExerciseOne.isAdded()) {
                    Log.d("TG", "fragExerciseOne.isAdded");
                    fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .add(R.id.container, fragExerciseOne, "FRAG_EXERCISE_ONE");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_TWO:
                fragExerciseTwo = new FragExerciseTwo();
                if(!fragExerciseTwo.isAdded()) {
                    Log.d("TG", "fragExerciseTwo.isAdded");
                    fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .add(R.id.container, fragExerciseTwo, "FRAG_EXERCISE_TWO");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_EXERCISE_THREE:
                fragExerciseThree = new FragExerciseThree();
                if(!fragExerciseThree.isAdded()) {
                    Log.d("TG", "fragExerciseThree.isAdded");
                    fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .add(R.id.container, fragExerciseThree, "FRAG_EXERCISE_THREE");
                    fragmentTransaction.commit();
                }
                break;

            case ITEM_STATISTIC:
                fragStatistic = new FragStatistic();
                if(!fragStatistic.isAdded()) {
                    Log.d("TG", "fragStatistic.isAdded");
                    fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .add(R.id.container, fragStatistic, "FRAG_STATISTIC");
                    fragmentTransaction.commit();
                }
                break;
        }
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
        Log.d("TG", "id = "+view.getId());
    }
}

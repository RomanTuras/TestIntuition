package ua.com.spasetv.testintuitions;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, StaticFields {

    ArrayList<ListData> arrayList;
    MyAdapter myAdapter;
    ListView mainListItem;
    Fragment fragAbout, fragExerciseOne;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_apps_white_18dp);

        fragAbout = null;
        fragExerciseOne = null;

        mainListItem = (ListView) findViewById(R.id.listView);
        initMainListItems();
        myAdapter = new MyAdapter(this, arrayList);
        mainListItem.setAdapter(myAdapter);
        mainListItem.setOnItemClickListener(this);
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
                res.getString(R.string.descriptionAbout), "", R.drawable.ic_blank));

        for (String titleEx: titleExercises) {
            arrayList.add(new ListData(titleEx,
                    res.getString(R.string.amount)+" "+amountTimes+" "+
                            res.getString(R.string.times),
                    res.getString(R.string.bestResult)+" "+bestResult+
                            res.getString(R.string.was)+" "+dateOfBestResult,
                    R.drawable.ic_blank));
        }

        arrayList.add(new ListData(res.getString(R.string.titleEnableStat),
                res.getString(R.string.descriptionStat),
                res.getString(R.string.descriptionStat2),
                R.drawable.ic_blank));
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

    @Override
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
                    Log.d("TG", "fragExerciseOne.isAdded == false");
                    fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .add(R.id.container, fragExerciseOne, "FRAG_EXERCISE_ONE");
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

        if(fragAbout != null)
            if (fragAbout.isAdded()) {
                refreshActionBar(MAIN_ACTIVITY);
                fragTmp = fragAbout;
            }else if(fragExerciseOne != null) {
                if (fragExerciseOne.isAdded()) {
                    refreshActionBar(MAIN_ACTIVITY);
                    fragTmp = fragExerciseOne;
                }
            }


        Log.d("TG", "fragTmp ="+fragTmp);
        if(fragTmp!=null) {
            fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.remove(fragTmp).commit();
        }else finish();
    }


}

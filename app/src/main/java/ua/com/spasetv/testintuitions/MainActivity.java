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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<ListData> arrayList;
    MyAdapter myAdapter;
    ListView mainListItem;
    Fragment fragAbout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static final int MAIN_ACTIVITY=0;
    public static final int FRAGMENT_ABOUT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshActionBar(MAIN_ACTIVITY);

        mainListItem = (ListView) findViewById(R.id.listView);
        initMainListItems();
        myAdapter = new MyAdapter(this, arrayList);
        mainListItem.setAdapter(myAdapter);
        mainListItem.setOnItemClickListener(this);
    }

    public void refreshActionBar(int key){
        switch (key){
            case MAIN_ACTIVITY:
                toolbar.setNavigationIcon(R.drawable.ic_apps_white_18dp);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case FRAGMENT_ABOUT:
                toolbar.setNavigationIcon(R.drawable.ic_apps_white_18dp);
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        fragmentManager = getFragmentManager();

        switch (position){
            case 0:
                fragAbout = new FragAbout();
                if(!fragAbout.isAdded()) {
                    fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .add(R.id.container, fragAbout, "FRAG_ABOUT");
                    fragmentTransaction.commit();
                }

                break;
        }
    }

    @Override
    public void onBackPressed(){
        Fragment fragTmp=null;
        if(fragAbout.isAdded()) {
            Log.d("TG", "Find Fragment " + fragAbout.getTag());
            fragTmp = fragAbout;
        }
            fragmentTransaction = fragmentManager
            .beginTransaction();
            fragmentTransaction.detach(fragTmp).commit();

    }


}

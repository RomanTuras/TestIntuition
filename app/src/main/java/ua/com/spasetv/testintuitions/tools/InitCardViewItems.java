package ua.com.spasetv.testintuitions.tools;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

import ua.com.spasetv.testintuitions.R;

/**
 * Created by salden on 10/12/2015.
 * Fill arrayList with data required to Cards from Main Screen
 */
public class InitCardViewItems {
    private Context context;

    private ArrayList<ListData> cardViewData;

    public InitCardViewItems(Context context) {
        this.context = context;
        initCardViewItems();
    }

    public void initCardViewItems(){
        cardViewData = new ArrayList<>();
        Resources resources = context.getResources();
        int amountTimes = 0;
        int bestResult = 0;
        String dateOfBestResult = "01.01.1953";
        String [] titleExercises = resources.getStringArray(R.array.titleExercise);

        cardViewData.add(new ListData(resources.getString(R.string.titleAbout),
                resources.getString(R.string.descriptionAbout), null,
                R.drawable.ic_help_outline_black_24dp));

        int i = 0;
        for (String titleEx: titleExercises){
            int image = i==0?R.drawable.ic_grid_off_black_48dp:
                    (i==1?R.drawable.ic_blur_linear_black_48dp:R.drawable.ic_healing_black_48dp);
            cardViewData.add(new ListData(titleEx,
                    resources.getString(R.string.amount) + " " + amountTimes + " " +
                            resources.getString(R.string.times),
                    resources.getString(R.string.bestResult) + " " + bestResult +
                            resources.getString(R.string.was)+" "+dateOfBestResult,
                    image));
            i++;
        }

        cardViewData.add(new ListData(resources.getString(R.string.titleEnableStat),
                resources.getString(R.string.descriptionStat),
                resources.getString(R.string.descriptionStat2),
                R.drawable.ic_shopping_cart_black_36dp));
    }

    public ArrayList<ListData> getArrayList() {
        return cardViewData;
    }
}

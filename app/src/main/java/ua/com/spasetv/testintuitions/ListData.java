package ua.com.spasetv.testintuitions;

/**
 * Created by salden on 25/11/2015.
 * Container for List View in main activity
 */
public class ListData {
    String title;
    String amountTimes;
    String bestResult;
    int idImg;

    public ListData(String title, String amountTimes, String bestResult, int idImg){
        this.title = title;
        this.amountTimes = amountTimes;
        this.bestResult = bestResult;
        this.idImg = idImg;
    }
}

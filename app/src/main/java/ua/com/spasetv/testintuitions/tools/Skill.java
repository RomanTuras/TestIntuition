package ua.com.spasetv.testintuitions.tools;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ua.com.spasetv.testintuitions.R;
import ua.com.spasetv.testintuitions.helpers.DataBaseHelper;

/**
 * Created by Roman Turas on 27/12/2015.
 */
public class Skill implements StaticFields{
    private Context context;
    private Resources resources;
    private int[] skillArray = new int[3];
    private final int DO_DAYS = 7; //number days of exercises before skill is available

    public Skill(Context context){
        this.context = context;
        this.resources = context.getResources();
    }

    /** Generate string of skill in format for main cards
     * Volume of 'doDays' may be from 0 to 7 */
    public String getSkillMainCard(int idExercise){
        int doDays = calculateSkill(idExercise);
        String textSkill = resources.getString(R.string.skill) + " " + skillArray[idExercise] + "% ";
        if(doDays > 1 && doDays < 8) {
            textSkill += resources.getString(R.string.skillBegin) + " " + doDays + " " +
                    resources.getString(R.string.skillDays);
        }else if(doDays == 1) {
            textSkill += resources.getString(R.string.skillBegin) + " " + doDays + " " +
                    resources.getString(R.string.skillDay);
        }
        return textSkill;
    }

    /** Calculate number of training days, if days between 0 and 6 - return
     * how many more days by trainings before skill will be available,
     * if 7 and more -> return average value of skill to the last seven days of exercises */
    private int calculateSkill(int i) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        int doDays = DO_DAYS;
        int skill = 0;
        int numberOfTraining = 0;
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String table = i == 0 ? TABLE_NAME_EX_ONE :
                (i == 1 ? TABLE_NAME_EX_TWO : TABLE_NAME_EX_THREE);

        Cursor cursor = database.query(table, null, null, null, null, null, null);
        if (cursor != null) {
            String tempDate = "";
            if (cursor.moveToLast()) {
                do {
                    if(!tempDate.equals(cursor.getString(1))) {
                        tempDate = cursor.getString(1);
                        skill += cursor.getInt(2);
                        numberOfTraining++;
                        if(--doDays == 0) {
                            this.skillArray[i] = skill/numberOfTraining;
                            return doDays;
                        }
                    }
                } while (cursor.moveToPrevious());
            }
            cursor.close();
        }
        database.close();
        dataBaseHelper.close();
        return doDays;
    }
}

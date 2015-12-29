package ua.com.spasetv.testintuitions.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ua.com.spasetv.testintuitions.tools.StaticFields;

/**
 * Created by salden on 14/12/2015.
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper implements StaticFields{

    public SQLiteDatabase mDataBase;
    private Context mContext;
    private ContentValues mContentValues =new ContentValues();
    private static String mSdState = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static String mPath = (mSdState + "/" + DB_FOLDER + "/" + DB_FILE);
    private static int mVers=1;

    public DataBaseHelper(Context context) {
        super(context, mPath, null, mVers);
        mContext = context;
    }

    private void setTestDataExOne(){
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy");
        Date d = new Date();
        String todayDate = sdf.format(d);
        String anyDate[] = {"01.06.2011", "09.03.2015", "16.10.2015", "17.10.2015", "25.11.2015",
                            "26.11.2015", "19.12.2015", "20.12.2015"};
        int anyResult[] = {30, 35, 37, 34, 39, 41, 42, 39};
        mDataBase = this.getWritableDatabase();
        mContentValues.clear();
        for(int i=0; i<anyResult.length; i++) {
            mContentValues.put(COLUMN_DATE, anyDate[i]);
            mContentValues.put(COLUMN_RESULT, anyResult[i]);
            mDataBase.insert(TABLE_NAME_EX_ONE, null, mContentValues);
            mDataBase.insert(TABLE_NAME_EX_TWO, null, mContentValues);
            mDataBase.insert(TABLE_NAME_EX_THREE, null, mContentValues);
        }

        mContentValues.put(COLUMN_DATE, todayDate);
        mContentValues.put(COLUMN_RESULT, 45);
        mDataBase.insert(TABLE_NAME_EX_ONE, null, mContentValues);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, -1);

        mContentValues.put(COLUMN_DATE, sdf.format(calendar.getTime()));
        mContentValues.put(COLUMN_RESULT, 40);
        mDataBase.insert(TABLE_NAME_EX_TWO, null, mContentValues);

        calendar.add(Calendar.DATE, -1);
        mContentValues.put(COLUMN_DATE, sdf.format(calendar.getTime()));
        mContentValues.put(COLUMN_RESULT, 37);
        mDataBase.insert(TABLE_NAME_EX_THREE, null, mContentValues);

        mDataBase.close();
    }

    private void saveOldData(String oldData) {
        mDataBase = this.getWritableDatabase();
        mVers = mDataBase.getVersion();
        long rowId;
        Cursor cursor = mDataBase.query(TABLE_NAME_EX_TWO, null, null, null, null, null, null);
        cursor.moveToLast();
        
        String date = getDate(oldData);
        int bestResult = getResult(oldData);

        mContentValues.clear();
        mContentValues.put(COLUMN_DATE, date);
        mContentValues.put(COLUMN_RESULT, bestResult);

        rowId = mDataBase.insert(TABLE_NAME_EX_TWO, null, mContentValues);
        
        if(rowId == -1) {
            mVers++;
            mContentValues.clear();
            mContentValues.put(COLUMN_DATE, date);
            mContentValues.put(COLUMN_RESULT, bestResult);
            mDataBase.insert(TABLE_NAME_EX_TWO, null, mContentValues);
        }
        cursor.close();
        mDataBase.close();
    }

    private int getResult(String oldData) {
        String s="";
        for(int i=0;i<oldData.length();i++)
        {
            if(oldData.charAt(i)=='/'){
                for(int j=i+1;j<oldData.length();j++)
                {
                    s += oldData.charAt(j);
                    if(oldData.charAt(j+1) == '*') break;
                }
            }
        }
        return Integer.valueOf(s);
    }

    private String getDate(String oldData) {
        String s="";
        for(int i=0;i<oldData.length();i++) {
            s+=oldData.charAt(i);
            if(oldData.charAt(i+1) == '/') break;
        }
        return s;
    }

    /** Search previous version 'Intuition', return string with 'Best result', delete old file */
    private String findOldFile() throws IOException{
        String pathOldFile = mSdState + "/" + OLD_FOLDER + "/" + OLD_FILE;
        String oldData;
        File oldFile = new File(pathOldFile);
        if(oldFile.exists()){
            BufferedReader br=new BufferedReader(new FileReader(oldFile));
            oldData = br.readLine();
            br.close();
            if(oldData != null) oldFile.delete();
            return oldData;
        }else return null;
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            try {
                String oldData = findOldFile();
                if(oldData != null) saveOldData(oldData);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            setTestDataExOne();
        }
    }

    /** Checking availability of database file and return true if it is */
    private boolean checkDataBase() {
        File dbFile = mContext.getDatabasePath(mPath);
        return dbFile.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String mTableMain="create table main (_id integer primary key autoincrement, "
                + "date text, login text, pass text, transfer_to_server bit);";

        String mTableExOne="create table ex_one (_id integer primary key autoincrement, "
                + "date text, result integer);";

        String mTableExTwo="create table ex_two (_id integer primary key autoincrement, "
                + "date text, result integer);";

        String mTableExThree="create table ex_three (_id integer primary key autoincrement, "
                + "date text, result integer);";

        sqLiteDatabase.execSQL(mTableMain);
        sqLiteDatabase.execSQL(mTableExOne);
        sqLiteDatabase.execSQL(mTableExTwo);
        sqLiteDatabase.execSQL(mTableExThree);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

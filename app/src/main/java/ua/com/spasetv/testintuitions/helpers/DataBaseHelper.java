package ua.com.spasetv.testintuitions.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    private void saveOldData(String oldData) {
        Log.d("DataBaseHelper: ", " -  save data from old file");

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
            Log.d("DataBaseHelper: ", "Olf file -  "+oldFile.toString()+"  found!");
            BufferedReader br=new BufferedReader(new FileReader(oldFile));
            oldData = br.readLine();
            br.close();
            if(oldData != null) oldFile.delete();
            return oldData;
        }else return null;
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            Log.d("DataBaseHelper: ", "Database Not found - create default!");
        }

//        if(mSdState.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("DataBaseHelper: ", " -  MEDIA_MOUNTED");
            try {
                String oldData = findOldFile();
                if(oldData != null) saveOldData(oldData);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }

    }


    /** Checking availability of database file and return true if it is */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        File dbFile = mContext.getDatabasePath(mPath);
        try {
            checkDB = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),
                    null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("DataBaseHelper:",""+e);
        }

        if (checkDB != null) {
            checkDB.close();
            Log.d("DataBaseHelper: ", mPath + " -  Successfully found!");
        }
        return checkDB != null ? true : false;
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

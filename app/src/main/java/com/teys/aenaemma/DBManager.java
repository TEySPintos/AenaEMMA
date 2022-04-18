package com.teys.aenaemma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by cgo on 9/08/16.
 */
public class DBManager {

    private Context context;
    private SurveyDBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
    }

    public Questionnaire getQuestById(String iden){
        Questionnaire quest = null;

        openDB();

        Cursor c = db.query(QueContract.QueEntry.TABLE_NAME,
                null,
                QueContract.QueEntry.COLUMN_NAME_IDEN + " LIKE ?",
                new String[]{iden},
                null,
                null,
                null);
        if(c!= null){
            if(c.moveToNext()){
                quest = QueContract.toQuestionnaire(c);
            }
        }

        closeDB();

        return quest;
    }



    public ArrayList<Questionnaire> getAllQuests(){
        ArrayList<Questionnaire> allQuest = new ArrayList<Questionnaire>();

        openDB();

        Cursor c = db.query(QueContract.QueEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                QueContract.QueEntry.COLUMN_NAME_IDEN+" DESC");
        if(c!= null){
            while(c.moveToNext()){
                allQuest.add(QueContract.toQuestionnaire(c));
            }
        }

        closeDB();

        return allQuest;
    }

    public ArrayList<Questionnaire> getNotUploadedQuests(){
        ArrayList<Questionnaire> questList = new ArrayList<Questionnaire>();

        openDB();

        Cursor c = db.query(QueContract.QueEntry.TABLE_NAME,
                null,
                QueContract.QueEntry.COLUMN_NAME_UPLOADED + " = 0 ",
                null,
                null,
                null,
                null);
        if(c!= null){
            while(c.moveToNext()){
                questList.add(QueContract.toQuestionnaire(c));
            }
        }

        closeDB();

        return questList;
    }


    public int addQuest(Questionnaire quest){
        int iden = -1;
        openDB();

        iden = (int) db.insert(QueContract.QueEntry.TABLE_NAME, null, QueContract.toContentValues(quest));

        closeDB();

        return iden;
    }

    public void deleteQuest(int iden) {

        openDB();
        int isDeleted = db.delete(QueContract.QueEntry.TABLE_NAME, QueContract.QueEntry.COLUMN_NAME_IDEN+"="+String.valueOf(iden), null);
        closeDB();

        return;
    }

    public void updateAsSended(int queIden) {
        ContentValues cv = new ContentValues();
        cv.put(QueContract.QueEntry.COLUMN_NAME_UPLOADED,true);
        String[] whereArgs = {String.valueOf(queIden)};

        openDB();
        db.update(QueContract.QueEntry.TABLE_NAME, cv, QueContract.QueEntry.COLUMN_NAME_IDEN +" = ?", whereArgs);
        closeDB();

    }

    private void openDB(){
        if(dbHelper== null)
            dbHelper = new SurveyDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB(){
        dbHelper.close();
        db = null;
    }








}

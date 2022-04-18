package com.teys.aenaemma;

import android.app.Activity;

/**
 * Created by cgo on 3/10/16.
 */

public abstract class Form {
    public static String DATE_FORMAT_COMPLETE ="dd/MM/yyyy HH:mm";


    String airportCode;
    String airportName;
    Activity activity;
    DictionaryManager dm;


    public  Form(String airportName, Activity surveyAct, DictionaryManager dm){
        this.dm = dm;
        this.activity = surveyAct;
        this.airportName = airportName;
        this.airportCode = dm.getAssociatedCodeFor(airportName, DictionaryManager.SURVEY_AIRPORT);
    }

    public abstract int getLayoutId();

    public abstract void initFormView();

    public abstract int onNextPressed(int p);

    public abstract int onPreviousPressed(int actual, int anterior);

    public abstract boolean checkQuestion(int q);

    public abstract Questionnaire fillQuest(Questionnaire quest,boolean throwError) throws Exception;

}

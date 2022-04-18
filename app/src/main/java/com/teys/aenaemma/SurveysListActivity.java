package com.teys.aenaemma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class SurveysListActivity extends AppCompatActivity {

    public final static String EXTRA_CODENCUESTADOR = "com.teys.AenaEMMA.CODENCUESTADOR";
    public final static String EXTRA_AEROPUERTO = "com.teys.AenaEMMA.AEORPUERTO";
    public final static String EXTRA_IDIOMA = "com.teys.AenaEMMA.IDIOMA";
    public static final String EXTRA_START_TIME = "com.teys.AenaEMMA.STARTTIME" ;
    public static final String EXTRA_SURVEY_IDEN = "com.teys.AenaEMMA.SURVEYIDEN";

    private Spinner languageSpinner;
    private Spinner airportSpinner;

    private int nextSurveyIden;

    @Override
    protected void onStart() {
        super.onStart();
        //Update next survey iden (possible new survey)
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String codEncuestador = sharedPref.getString(getString(R.string.CodEncuestador_Preference),"");
        nextSurveyIden = sharedPref.getInt(getString(R.string.sharedPref_nextSurvey), 1);

        DBManager dbManager = new DBManager(this.getApplicationContext());
        ArrayList<Questionnaire> allQuests = dbManager.getAllQuests();

        SurveyItemAdapter itemAdapter = new SurveyItemAdapter(this, allQuests);
        ((ListView) findViewById(R.id.surveyList_list_quests)).setAdapter(itemAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_encuestas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init Spinners
        ArrayAdapter<CharSequence> adapterLanguage =
                ArrayAdapter.createFromResource(this,
                        R.array.languageStrings,
                        R.layout.selection_spinner_item);
        adapterLanguage.setDropDownViewResource(R.layout.selection_spinner_item);
        languageSpinner = (Spinner) findViewById(R.id.edit_idioma);
        languageSpinner.setAdapter(adapterLanguage);

        ArrayAdapter<CharSequence> adapterAirport =
                ArrayAdapter.createFromResource(this,
                        R.array.surveyAirportStrings,
                        R.layout.selection_spinner_item);
        adapterAirport.setDropDownViewResource(R.layout.selection_spinner_item);
        airportSpinner = (Spinner) findViewById(R.id.edit_aeropuerto);
        airportSpinner.setAdapter(adapterAirport);


        //Get CodEncuestador & nextIden from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String codEncuestador = sharedPref.getString(getString(R.string.CodEncuestador_Preference),"");
        if(!codEncuestador.isEmpty()){
            EditText etCodEnc = (EditText) findViewById(R.id.edit_codEncuestador);
            etCodEnc.setText(codEncuestador);
        }
        nextSurveyIden = sharedPref.getInt(getString(R.string.sharedPref_nextSurvey), 1);

        DBManager dbManager = new DBManager(this.getApplicationContext());
        ArrayList<Questionnaire> allQuests = dbManager.getAllQuests();

        ((Button) findViewById(R.id.button_upload)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadQuests();
//                Toast toast = Toast.makeText (SurveysListActivity.this, "No ha sido posible conectar con el servidor, por favor pruebe más tarde", Toast.LENGTH_LONG);
//                toast.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listado_encuestas, menu);
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


    /** Called when button newSurvey is pressed **/
    public void newSurvey(View view){
        //Create a new activity with a new survey form
        Intent intent = new Intent(this, SurveyActivity.class);


        if(allFieldsFilled()) {

            //Set start time
            Calendar currentTime = Calendar.getInstance();
            String timeInMilis = String.valueOf(currentTime.getTimeInMillis());
            intent.putExtra(EXTRA_START_TIME, timeInMilis);

            //Get input data from the user
            EditText editText = (EditText) findViewById(R.id.edit_codEncuestador);
            String codEncuestador = editText.getText().toString();
            intent.putExtra(EXTRA_CODENCUESTADOR, codEncuestador);

            String aeropuerto = airportSpinner.getSelectedItem().toString();
            intent.putExtra(EXTRA_AEROPUERTO, aeropuerto);

            String idioma = languageSpinner.getSelectedItem().toString();
            intent.putExtra(EXTRA_IDIOMA, idioma);

            intent.putExtra(EXTRA_SURVEY_IDEN, nextSurveyIden);

            //Save codEncuestador en preferences
            SharedPreferences sharedPref =
                    getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.CodEncuestador_Preference), codEncuestador);
            editor.commit();

            //Launch the new activity
            startActivity(intent);
        }

    }

    private boolean allFieldsFilled() {

        EditText etCodEnc = (EditText) findViewById(R.id.edit_codEncuestador);
        String codEnc = etCodEnc.getText().toString();
        if(codEnc == null || codEnc.isEmpty()){
            Toast toast = Toast.makeText (this, "No se ha indicado el código del encuestador", Toast.LENGTH_LONG);
            toast.show();
            etCodEnc.requestFocus();
            return false;
        }
        Spinner sAirport = (Spinner) findViewById(R.id.edit_aeropuerto);
        String selectedAirport = sAirport.getSelectedItem().toString();
        String[] airportStrings = getResources().getStringArray(R.array.surveyAirportStrings);
        if(selectedAirport.contentEquals(airportStrings[0])){
            Toast toast = Toast.makeText (this, "No se ha indicado el aeropuerto", Toast.LENGTH_LONG);
            toast.show();
            sAirport.requestFocus();
            return false;
        }

        return true;
    }

    private void uploadQuests(){

        DBManager dbManager = new DBManager(this.getApplicationContext());
        ArrayList<Questionnaire> quesToUpdate = dbManager.getNotUploadedQuests();

        if(quesToUpdate==null || quesToUpdate.isEmpty()){
            Toast toast = Toast.makeText (SurveysListActivity.this, "No hay encuestas pendientes de envío", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText (SurveysListActivity.this, "Se van a enviar "+quesToUpdate.size()+" encuestas", Toast.LENGTH_LONG);
            toast.show();

            if(quesToUpdate != null && !quesToUpdate.isEmpty()) {
                RestClient rClient = RestClient.getInstance(this.getApplicationContext());
                int updatedQues = rClient.sendQues(quesToUpdate);}
        }
    }

}

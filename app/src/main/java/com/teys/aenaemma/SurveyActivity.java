package com.teys.aenaemma;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.widget.RadioGroup.*;
import static com.teys.aenaemma.R.id.survey_form_container;

public class SurveyActivity extends AppCompatActivity {
    private static String DATE_FORMAT_SHORT = "dd/MM/yyyy";
    private static String DATE_FORMAT_TIME = "HH:mm";
    private static String DATE_FORMAT_COMPLETE ="dd/MM/yyyy HH:mm";

    private Questionnaire quest;
    private DictionaryManager dm;
    private Form form;

    private String language = null;
    private String codIdioma = null;
    private String airport = null;
    private String codAeropuerto = null;
    private String codEncuestador = null;
    private int idQuest;
    private Date startDate = null;

    private int question=1;
    private Button saveButton;
    private Button nextButton;
    private Button previousButton;

    ArrayList<String> questions = new ArrayList<String>();

    private Spinner p33spinner;
    private Spinner p14spinner;
    private Spinner p27spinner;
    private Spinner p30bitem1spinner;
    private Spinner p30bitem2spinner;
    private Spinner p30bitem3spinner;
    private Spinner p30bitem4spinner;
    private Spinner p30bitem5spinner;

    private AutoCompleteTextView p1autoComplete;
    private AutoCompleteTextView p2autoComplete;
    private AutoCompleteTextView p2localidadAutoComplete;
    private AutoCompleteTextView p2provinciaAutoComplete;
    private AutoCompleteTextView p3localidadAutoComplete;
    private AutoCompleteTextView p3airportAutoComplete;
    private AutoCompleteTextView p9airportAutoComplete;
    private AutoCompleteTextView p10CompanyAutoComplete;
    private AutoCompleteTextView p12companyAutoComplete;
    private AutoCompleteTextView p13airportAutoComplete;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questions.add("1");

        //Retrieve all the data from the intent
        Intent intent = getIntent();
        language = intent.getStringExtra(SurveysListActivity.EXTRA_IDIOMA);
        airport = intent.getStringExtra(SurveysListActivity.EXTRA_AEROPUERTO);
        codEncuestador = intent.getStringExtra(SurveysListActivity.EXTRA_CODENCUESTADOR);
        idQuest = intent.getIntExtra(SurveysListActivity.EXTRA_SURVEY_IDEN, 0);


        dm = new DictionaryManager(this);
        dm.setAirportSelection(airport);

        codIdioma = dm.getAssociatedCodeFor(language, DictionaryManager.LANGUAGE);
        codAeropuerto = dm.getAssociatedCodeFor(airport, DictionaryManager.SURVEY_AIRPORT);


        switch (codAeropuerto){
            case "SVQ":
            case "BIO":
            case "OVD":
            case "AGP":
            case "LEI":
            case "SDR":
                form = new FormModel1(airport, this, dm);
                break;
            case "PMI":
            case "IBZ":
            case "MAH":
            case "TFN":
            case "TFS":
            case "ACE":
            case "FUE":
            case "LPA":
                form = new FormModel2(airport, this, dm);
                break;
            case "MAD":
            case "BCN":
                form = new FormModel3(airport, this, dm);
                break;
            default:
                form = new FormModel1(airport, this, dm);
                break;
        }

        quest = new Questionnaire(String.valueOf(idQuest), codEncuestador, codIdioma, codAeropuerto);


        //Set activity language based on selection
        String[] langStrings = getResources().getStringArray(R.array.languageStrings);
        String localeCode = "";
        if (language.contains(langStrings[0])) {
            localeCode = "es";
        } else if (language.contains(langStrings[1])) {
            localeCode = "en";
        } else if (language.contains(langStrings[2])) {
            localeCode = "de";
        } else if (language.contains(langStrings[3])) {
            localeCode = "fr";
        } else if (language.contains(langStrings[4])) {
            localeCode = "it";
        } else if (language.contains(langStrings[5])) {
            localeCode = "pt";
        } else if (language.contains(langStrings[6])) {
            localeCode = "ca";
        } else if (language.contains(langStrings[7])) {
            localeCode = "eu";
        } else {
            localeCode = "en";
        }

        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        //Define layout
        setContentView(R.layout.activity_survey);
        LinearLayout formContainer = (LinearLayout) findViewById(R.id.survey_form_container);
        View.inflate(this, form.getLayoutId(), formContainer);

        form.initFormView();

        //Set CodEncuestador on the survey header
        TextView tvCodEnc = (TextView) findViewById(R.id.survey_text_codEncuestador);
        tvCodEnc.setText(codEncuestador);



        //Progressbar
        final TextView tvQ = (TextView) findViewById(R.id.survey_text_question);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.survey_progressbar);

        pb.setProgress(1);

        if (codAeropuerto.contentEquals("MAD")){
            tvQ.setText("1/42");
            pb.setMax(42);
        }else{
            tvQ.setText("1/36");
            pb.setMax(36);
        }




        //Header text
        Resources res = getResources();
        String text = res.getString(R.string.survey_text_encuestasAeropuerto);
        text = text + " " + airport;
        TextView tvAirportHeader = (TextView) findViewById(R.id.survey_text_airportHeader);
        tvAirportHeader.setText(text);
        TextView tvCueNumber = (TextView) findViewById(R.id.survey_text_numEncuesta);
        tvCueNumber.setText(String.valueOf(idQuest));


        //Set date and time
        String startTime = intent.getStringExtra(SurveysListActivity.EXTRA_START_TIME);
        if (startTime != null) {

            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeInMillis(Long.parseLong(startTime));
            startDate = currentTime.getTime();
            //Set Date Text
            SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT_SHORT);
            SimpleDateFormat sdfTime = new SimpleDateFormat(DATE_FORMAT_TIME);

            //int month = currentTime.get(Calendar.MONTH) + 1;
            //String auxString = currentTime.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf  (month) + "/" + currentTime.get(Calendar.YEAR);
            TextView textView = (TextView) findViewById(R.id.survey_edit_fecha);
            textView.setText(sdfDate.format(currentTime.getTime()));
            //Set Time Text

            textView = (TextView) findViewById(R.id.survey_edit_hora);
            //auxString = currentTime.get(Calendar.HOUR_OF_DAY) + ":" + currentTime.get(Calendar.MINUTE);
            textView.setText(sdfTime.format(currentTime.getTime()));
        }



        saveButton = (Button) findViewById(R.id.survey_button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

              //if(satisfyValidation()){

              //if(1==1){
                if(satisfyValidation()){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyActivity.this);
                alertDialogBuilder.setMessage("¿Estás seguro de que deseas guardar y salir?");

                alertDialogBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        saveQuestionnaire();
                        Toast.makeText(SurveyActivity.this,"El cuestionario se ha guardado",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

              }


            }
        });


        previousButton = (Button) findViewById(R.id.survey_button_previous);
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v){

                EditText etDummy = (EditText) findViewById(R.id.survey_edit_dummy);
                etDummy.requestFocus();

                String total="";
                if (codAeropuerto.contentEquals("MAD")){
                    total="42";
                    pb.setMax(42);
                }else{
                    total="36";
                    pb.setMax(36);
                }

                int actual = question;
                int anterior = Integer.parseInt(questions.get((questions.size()-2)));

                form.onPreviousPressed(actual, anterior);
                question = form.onPreviousPressed(actual, anterior);
                String preg = Integer.toString(question);
                pb.setProgress(question);
                tvQ.setText(preg + "/" + total);
                pb.setProgress(question);
                questions.remove(questions.size()-1);

            }
        });


        nextButton = (Button) findViewById(R.id.survey_button_next);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etDummy = (EditText) findViewById(R.id.survey_edit_dummy);
                etDummy.requestFocus();

                pb.setProgress(question);
                /*View view = SurveyActivity.this.getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/
                String total="";
                if (codAeropuerto.contentEquals("MAD")){
                    total="42";
                    pb.setMax(42);
                }else{
                    total="36";
                    pb.setMax(36);
                }

                if(form.onNextPressed(question)!=0) {

                    question = form.onNextPressed(question);
                    String preg = Integer.toString(question);
                    if(!questions.contains(preg)) {questions.add(preg);}
                    //Toast toast = Toast.makeText(SurveyActivity.this, "Anterior: " + questions.get(questions.size()-2) + " Actual: " + questions.get(questions.size()-1), Toast.LENGTH_LONG);
                    //toast.show();
                    tvQ.setText(preg + "/" + total);
                    pb.setProgress(question);
                }

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }





    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyActivity.this);
        alertDialogBuilder.setMessage("¿Estás seguro de que deseas salir sin guardar? Se perderá toda la información rellenada.");

        alertDialogBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    private void saveQuestionnaire() {

        //Header
        quest.setIden(idQuest);
        quest.setHini(startDate);
        quest.setHfin(Calendar.getInstance().getTime());

        Date fechaEncuesta = new Date(startDate.getTime());
        SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
        String dateText = ((EditText) findViewById(R.id.survey_edit_fecha)).getText().toString();
        String timeText = ((EditText) findViewById(R.id.survey_edit_hora)).getText().toString();
        try {
            fechaEncuesta = sdfDate.parse(dateText +" "+timeText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        quest.setFentrev(fechaEncuesta);
        quest.setHentrev(fechaEncuesta);

        String codCompVuelo = ((EditText) findViewById(R.id.survey_edit_codCompVuelo)).getText().toString();
        String numVuelo = ((EditText) findViewById(R.id.survey_edit_numVuelo)).getText().toString();
        quest.setNumvueca(codCompVuelo+numVuelo);
        EditText etGate = (EditText) findViewById(R.id.survey_edit_puertaEmbarque);
        quest.setPuerta(etGate.getText().toString());

        try {
            quest = form.fillQuest(quest, true);

            //Save questionnaire in JSON file
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
            String fileName = "EMMA_"+ this.codEncuestador + "_Enc_"+ sdf.format(startDate)+".json";

            JSONWriter jsonWriter = new JSONWriter();
            jsonWriter.writeNewFile(this, quest, fileName);

            //Save codEncuestador en preferences
            SharedPreferences sharedPref =
                    getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.sharedPref_nextSurvey), idQuest+1);
            editor.commit();


            SurveyDBHelper dbHelper = new SurveyDBHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.insert(QueContract.QueEntry.TABLE_NAME, null, QueContract.toContentValues(quest));

            //End current activity
            finish();

        } catch (Exception e){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyActivity.this);
            alertDialogBuilder.setMessage("Se ha producido un error al guardar la encuesta. "+
                    "Por favor pulse VISUALIZAR para volver al cuestionario y "+
                    "copie los datos introducidos en un cuestionario físico (papel) " +
                    "para darselos a su coordinador. Una vez haya copiado el cuestionario," +
                    "vuelva a pulsar guardar y cuando le aparezca este mensaje pulse CONTINUAR");

            alertDialogBuilder.setPositiveButton("Visualizar cuestionario", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    return;
                }
            });

            alertDialogBuilder.setNegativeButton("Continuar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        quest = form.fillQuest(quest, false);
                        //Save questionnaire in JSON file
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm");
                        String fileName = "EMMA_"+ codEncuestador + "_Enc_"+ sdf.format(startDate)+".json";

                        JSONWriter jsonWriter = new JSONWriter();
                        jsonWriter.writeNewFile(SurveyActivity.this, quest, fileName);

                        //Save codEncuestador en preferences
                        SharedPreferences sharedPref =
                                getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.sharedPref_nextSurvey), idQuest+1);
                        editor.commit();

                        SurveyDBHelper dbHelper = new SurveyDBHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        db.insert(QueContract.QueEntry.TABLE_NAME, null, QueContract.toContentValues(quest));

                        //End current activity
                        finish();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


    }


    private boolean satisfyValidation(){
        //only for debubing purposes
 //       if (BuildConfig.DEBUG) {
 //           return true;
 //       }

        if(form.checkQuestion(36)) {


            // numvuepa != null
            EditText etCodCompVuelo = (EditText) findViewById(R.id.survey_edit_codCompVuelo);
            String codComp = etCodCompVuelo.getText().toString();
            if (codComp == null || codComp.isEmpty()) {
                etCodCompVuelo.setError("El número de vuelo debe estar relleno");
                etCodCompVuelo.requestFocus();
                return false;
            }

            // numvuepa != null
            EditText etNumVuelo = (EditText) findViewById(R.id.survey_edit_numVuelo);
            String numVuelo = etNumVuelo.getText().toString();
            if (numVuelo == null || numVuelo.isEmpty()) {
                etNumVuelo.setError("El número de vuelo debe estar relleno");
                etNumVuelo.requestFocus();
                return false;
            }

            //puerta != null
            EditText etGate = (EditText) findViewById(R.id.survey_edit_puertaEmbarque);
            String puertaEmbarque = etGate.getText().toString();
            if (puertaEmbarque == null || puertaEmbarque.isEmpty()) {
                etGate.setError("La puerta de embarque debe estar cumplimentada");
                etGate.requestFocus();
                return false;
            }
        }else {
            return false;
        }


        return true;
    }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Survey Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.teys.aenaemma/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Survey Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.teys.aenaemma/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}

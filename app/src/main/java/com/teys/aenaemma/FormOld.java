package com.teys.aenaemma;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by cgo on 12/07/16.
 */
public class FormOld {
    private static String DATE_FORMAT_COMPLETE ="dd/MM/yyyy HH:mm";

    private final static int FORM_MODEL1= 1;
    private final static int FORM_MODEL2= 2;

    private String airportCode;
    private String airportName;
    private Activity activity;
    private DictionaryManager dm;
    private int formModelCode;

    public FormOld(String airportName, Activity surveyAct, DictionaryManager dm) {
        this.airportName = airportName;
        this.airportCode = dm.getAssociatedCodeFor(airportName,DictionaryManager.SURVEY_AIRPORT);
        this.activity = surveyAct;
        this.dm = dm;

        switch (airportCode){
            case "SVQ":
                formModelCode = FORM_MODEL1;
                break;
            case "PMI":
                formModelCode = FORM_MODEL2;
                break;
            case "IBZ":
                formModelCode = FORM_MODEL2;
                break;
            case "MAH":
                formModelCode = FORM_MODEL2;
                break;
            case "OVD":
                formModelCode = FORM_MODEL1;
                break;
            case "AGP":
                formModelCode = FORM_MODEL1;
                break;
            case "BIO":
                formModelCode = FORM_MODEL1;
                break;
            default:
                formModelCode = FORM_MODEL1;
                break;
        }
    }

    private int getLayoutId(){
        switch (formModelCode){
            case FORM_MODEL1:
                return R.layout.form_model1;
            case FORM_MODEL2:
                return R.layout.form_model2;
            default:
                return -1;
        }
    }

    public void initFormView(){
        LinearLayout formLayout = (LinearLayout) activity.findViewById(R.id.survey_form_container);
        View formView = View.inflate(activity, getLayoutId(),formLayout);
        switch (formModelCode){
            case FORM_MODEL1:
                initializeViewElementsModel1();
                break;
            case FORM_MODEL2:
                initializeViewElementsModel2();
                break;
        }

    }


    public boolean satisfyValidation(){
        switch (formModelCode){
            case FORM_MODEL1:
                return satisfyValidationModel1();
            case FORM_MODEL2:
                return satisfyValidationModel2();
            default:
                return false;
        }
    }

    private void initializeViewElementsModel1(){


        checkP26_28();




        //AutoComplete Text P1
        AutoCompleteTextView p1autoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P1);
        String[] countries = dm.getStringsArrayFor(DictionaryManager.COUNTRY);
        ArrayAdapter<String> p1adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
        p1autoComplete.setAdapter(p1adapter);

        p1autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry =((TextView) view).getText().toString();
                LinearLayout llp1otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P1_otros);
                String countryCode = dm.getAssociatedCodeFor(selectedCountry, DictionaryManager.COUNTRY);
                if(countryCode!= null && countryCode.contentEquals("999")){
                    llp1otros.setVisibility(View.VISIBLE);
                } else {
                    llp1otros.setVisibility(View.GONE);
                }
                ((TextView) activity.findViewById(R.id.survey_text_P1)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });

        //AutoComplete Text P2
        AutoCompleteTextView p2autoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2);
//      String[] countries = dm.getStringsArrayFor(DictionaryManager.COUNTRY);
        ArrayAdapter<String> p2adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
        p2autoComplete.setAdapter(p2adapter);


        p2autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout locationSpain = (LinearLayout) activity.findViewById(R.id.survey_layout_P2_locationSpain);
                LinearLayout otherCountry = (LinearLayout) activity.findViewById(R.id.survey_layout_P2_otherCountry);
                LinearLayout otherLiteral = (LinearLayout) activity.findViewById(R.id.survey_layout_P2_otros);

                RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_P3_option1);

                String selectedOption = ((TextView) view).getText().toString();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COUNTRY);

                if (selectedCode.contains("724")) {
                    locationSpain.setVisibility(View.VISIBLE);
                    otherCountry.setVisibility(View.GONE);
                    otherLiteral.setVisibility(View.GONE);
                    rbResidencia.setEnabled(true);
                } else if (selectedCode.contains("999")) {
                    locationSpain.setVisibility(GONE);
                    otherLiteral.setVisibility(VISIBLE);
                    otherCountry.setVisibility(GONE);
                    rbResidencia.setEnabled(false);
                } else if (dm.getAreaStringsResourceId(selectedOption) > 0) {
                    locationSpain.setVisibility(View.GONE);
                    otherLiteral.setVisibility(GONE);
                    otherCountry.setVisibility(View.VISIBLE);
                    rbResidencia.setEnabled(false);
                    setP2areaSpinner(selectedOption);
                } else {
                    locationSpain.setVisibility(View.GONE);
                    otherLiteral.setVisibility(GONE);
                    otherCountry.setVisibility(View.GONE);
                    rbResidencia.setEnabled(false);
                }
                //Change question text color as answered
                ((TextView) activity.findViewById(R.id.survey_text_P2)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });

        AutoCompleteTextView p2provinciaAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_prov);
        String[] provincias = activity.getResources().getStringArray(R.array.provinciaStrings);
        ArrayAdapter<String> p2provinciaadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, provincias);
        p2provinciaAutoComplete.setAdapter(p2provinciaadapter);

        p2provinciaAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProv = ((TextView) view).getText().toString();
                if(selectedProv!=null && !selectedProv.isEmpty()){
                    String codProvText = dm.getAssociatedCodeFor(selectedProv, DictionaryManager.PROVINCIA);
                    if(codProvText!= null)  {
                        AutoCompleteTextView p2localidadAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_loc);
                        String[] localidades = dm.getLocalidadesFor(codProvText);

                        ArrayAdapter<String> p2localidadadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, localidades);
                        p2localidadAutoComplete.setAdapter(p2localidadadapter);

                        p2localidadAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedLoc = ((TextView) view).getText().toString();
                                if(selectedLoc!=null && !selectedLoc.isEmpty()) {
                                    String codLocText = dm.getAssociatedCodeFor(selectedLoc, DictionaryManager.LOCALIDAD);
                                    if(codLocText!= null && codLocText.contentEquals("99999"))  {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral)).setVisibility(View.VISIBLE);
                                        ((EditText) activity.findViewById(R.id.survey_edit_P2_loc_lit)).requestFocus();
                                    } else {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral)).setVisibility(View.GONE);
                                    }
                                }

                            }
                        });

                        //Allow to select P3_option1 based on selected Province
                        ((RadioButton) activity.findViewById(R.id.survey_radio_P3_option1))
                                    .setEnabled(dm.isValidResidenceProv(codProvText));


                    }
                }
            }
        });




        //P3
        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
        rgP3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);

                if(rb.isChecked()){
                    LinearLayout localidadP3 = (LinearLayout) activity.findViewById(R.id.survey_layout_P3_localidad);
                    LinearLayout conexionP3 = (LinearLayout) activity.findViewById(R.id.survey_layout_P3_conexion);
                    RelativeLayout rlP4 = (RelativeLayout) activity.findViewById(R.id.survey_layout_P4);
                    LinearLayout llP5_7 = (LinearLayout) activity.findViewById(R.id.survey_block_P5_P7);

                    switch(checkedId){
                        case R.id.survey_radio_P3_option1:
                            localidadP3.setVisibility(INVISIBLE);
                            conexionP3.setVisibility(INVISIBLE);
                            rlP4.setVisibility(GONE);
                            llP5_7.setVisibility(VISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(false);
                            break;
                        case R.id.survey_radio_P3_option2:
                            loadP3Fields();
                            localidadP3.setVisibility(View.VISIBLE);
                            conexionP3.setVisibility(INVISIBLE);
                            rlP4.setVisibility(VISIBLE);
                            llP5_7.setVisibility(VISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(true);
                            break;
                        case R.id.survey_radio_P3_option3:
                            localidadP3.setVisibility(INVISIBLE);
                            conexionP3.setVisibility(VISIBLE);
                            rlP4.setVisibility(GONE);
                            llP5_7.setVisibility(GONE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(true);
                            break;
                        default:
                            localidadP3.setVisibility(INVISIBLE);
                            conexionP3.setVisibility(INVISIBLE);
                            rlP4.setVisibility(VISIBLE);
                            llP5_7.setVisibility(VISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(true);
                            break;
                    }
                    checkP26_28();

                    ((TextView) activity.findViewById(R.id.survey_text_P3)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        AutoCompleteTextView p3airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport);
        String[] airportsShort = dm.getStringsArrayFor(DictionaryManager.AIRPORT);
        ArrayAdapter<String> p3airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsShort);
        p3airportAutoComplete.setAdapter(p3airportadapter);

        p3airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selecedAirport =((TextView) view).getText().toString();
                LinearLayout llp3otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P3_otros);
                String airportCode = dm.getAssociatedCodeFor(selecedAirport, DictionaryManager.AIRPORT);
                if(airportCode!= null && airportCode.contentEquals("999")){
                    llp3otros.setVisibility(View.VISIBLE);
                } else {
                    llp3otros.setVisibility(View.GONE);
                }
            }
        });




        //P4
        RadioGroup rgP4 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P4);
        rgP4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    EditText etP4others = (EditText) activity.findViewById(R.id.survey_edit_P4_otros);
                    switch (checkedId){
                        case R.id.survey_radio_P4_option7:
                            etP4others.setVisibility(VISIBLE);
                            etP4others.requestFocus();
                            break;
                        default:
                            etP4others.setVisibility(INVISIBLE);
                            break;
                    }
                }
                ((TextView) activity.findViewById(R.id.survey_text_P4)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


        if(airportCode.contentEquals("AGP")){
            ((TableRow) activity.findViewById(R.id.survey_row_P5_cercanias)).setVisibility(VISIBLE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_P5_optionCerc)).setVisibility(VISIBLE);
        }

        //RadioGroup P5
        RadioGroup rgP5 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P5);
        rgP5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);

                if(rb.isChecked()){
                    RelativeLayout rlP6 = (RelativeLayout) activity.findViewById(R.id.survey_layout_P6);
                    EditText edP5_others = (EditText) activity.findViewById(R.id.survey_edit_P5_others);
                    switch (checkedId){
                        case R.id.survey_radio_P5_option3:
                        case R.id.survey_radio_P5_option4:
                            rlP6.setVisibility(VISIBLE);
                            edP5_others.setVisibility(INVISIBLE);
                            break;
                        case R.id.survey_radio_P5_option7:
                            rlP6.setVisibility(GONE);
                            edP5_others.setVisibility(VISIBLE);
                            break;
                        default:
                            rlP6.setVisibility(GONE);
                            edP5_others.setVisibility(INVISIBLE);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_P5)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //Radiogroup P6
        RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P6);
        rgP6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP6b = (LinearLayout) activity.findViewById(R.id.survey_layout_P6b);
                    switch(checkedId){
                        case R.id.survey_radio_P6_option6:
                            llP6b.setVisibility(VISIBLE);
                            break;
                        default:
                            llP6b.setVisibility(GONE);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_P6)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P7
        EditText etP7 = (EditText) activity.findViewById(R.id.survey_edit_P7);
        etP7.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP7 = (EditText) activity.findViewById(R.id.survey_edit_P7);
                String text = etP7.getText().toString();
                if(text != null && !text.isEmpty()){
                    //Change question text color as answered
                    TextView tvP7 = (TextView) activity.findViewById(R.id.survey_text_P7);
                    (tvP7).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


        //P8
        EditText etP8hora = (EditText) activity.findViewById(R.id.survey_edit_P8_hora);
        etP8hora.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP8hora = (EditText) activity.findViewById(R.id.survey_edit_P8_hora);
                String textHora = etP8hora.getText().toString();
                if(textHora != null && !textHora.isEmpty()){
                    int hora = Integer.parseInt(textHora);
                    if(hora<0 || hora>23){
                        etP8hora.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        Toast toast = Toast.makeText(activity, "El campo hora debe tener un valor comprendido entre 00 y 23",
                                Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        etP8hora.setBackgroundColor(Color.WHITE);
                    }

                    EditText etP8min = (EditText) activity.findViewById(R.id.survey_edit_P8_minutos);
                    String textMin = etP8min.getText().toString();
                    if(textMin != null && !textMin.isEmpty()) {
                        //Change question text color as answered
                        TextView tvP8 = (TextView) activity.findViewById(R.id.survey_text_P8);
                        (tvP8).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }

                checkValidTimeP8();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


        EditText etP8min = (EditText) activity.findViewById(R.id.survey_edit_P8_minutos);
        etP8min.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP8min = (EditText) activity.findViewById(R.id.survey_edit_P8_minutos);
                String textMin = etP8min.getText().toString();
                if(textMin != null && !textMin.isEmpty()) {
                    int min = Integer.parseInt(textMin);
                    if (min < 0 || min > 59) {
                        etP8min.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        Toast toast = Toast.makeText(activity, "El campo minutos debe tener un valor comprendido entre 00 y 59",
                                Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        etP8min.setBackgroundColor(Color.WHITE);
                    }

                    EditText etP8hora = (EditText) activity.findViewById(R.id.survey_edit_P8_hora);
                    String textHora = etP8hora.getText().toString();
                    if (textHora != null && !textHora.isEmpty()) {
                        //Change question text color as answered
                        TextView tvP8 = (TextView) activity.findViewById(R.id.survey_text_P8);
                        (tvP8).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }
                checkValidTimeP8();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


        //AutoComplete P9
        AutoCompleteTextView p9airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
//        String[] airports = getResources().getStringArray(R.array.airportStrings);
        ArrayAdapter<String> p9airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsShort);
        p9airportAutoComplete.setAdapter(p9airportadapter);

        p9airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Update question text in P11
                TextView tvSelected = (TextView) view;
                String airport = tvSelected.getText().toString();
                String P11text = activity.getResources().getString(R.string.survey_text_P11).toString();
                String P11textFormated = String.format(P11text,airport);
                TextView tvP11 = (TextView) activity.findViewById(R.id.survey_text_P11);
                tvP11.setText(P11textFormated);

                //Check for condition in flow filter of blok P26-28
                checkP26_28();

                //Show literal field if selected aiport = others
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT);
                LinearLayout llP9otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P9_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP9otros.setVisibility(VISIBLE);
                } else {
                    llP9otros.setVisibility(GONE);
                }
                ((TextView) activity.findViewById(R.id.survey_text_P9)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });







        //AutoComplete Text P10
        AutoCompleteTextView p10CompanyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P10_company);
        String[] companiesShort = dm.getStringsArrayFor(DictionaryManager.COMPANY);
        ArrayAdapter<String> p10adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesShort);
        p10CompanyAutoComplete.setAdapter(p10adapter);

        p10CompanyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) ((TextView) view).getText();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COMPANY);
                ((TextView) activity.findViewById(R.id.survey_text_P10_companyCode))
                        .setText(selectedCode+"-");

                //Show literal field if selected aiport = others
                LinearLayout llP10otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P10_otros);
                if(selectedCode!=null && selectedCode.contentEquals("999")){
                    llP10otros.setVisibility(VISIBLE);
                } else {
                    llP10otros.setVisibility(GONE);
                }
                ((TextView) activity.findViewById(R.id.survey_text_P10)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


        //P11
        RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P11);
        rgP11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP12 = (LinearLayout) activity.findViewById(R.id.survey_layout_P12);
                    LinearLayout llP13 = (LinearLayout) activity.findViewById(R.id.survey_layout_P13);
                    switch (checkedId){
                        case R.id.survey_radio_P11_option1:
                            llP12.setVisibility(GONE);
                            llP13.setVisibility(GONE);
                            break;
                        default:
                            llP12.setVisibility(VISIBLE);
                            llP13.setVisibility(VISIBLE);
                            break;
                    }
                }
                checkP26_28();
                ((TextView) activity.findViewById(R.id.survey_text_P11)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });

        //AutoComplete P12
        AutoCompleteTextView p12companyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P12);
        String[] companiesLong = dm.getStringsArrayFor(DictionaryManager.COMPANY_LONG);
        ArrayAdapter<String> p12companyadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesLong);
        p12companyAutoComplete.setAdapter(p12companyadapter);


        p12companyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Show literal field if selected aiport = others
                String company = ((TextView) view).getText().toString();
                String companyCode = dm.getAssociatedCodeFor(company, DictionaryManager.COMPANY_LONG);
                LinearLayout llP12otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P12_otros);
                if(companyCode!=null && companyCode.contentEquals("999")){
                    llP12otros.setVisibility(VISIBLE);
                } else {
                    llP12otros.setVisibility(GONE);
                }
                ((TextView) activity.findViewById(R.id.survey_text_P12)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


        //AutoComplete P13
        AutoCompleteTextView p13airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13);
        String[] airportsLong = dm.getStringsArrayFor(DictionaryManager.AIRPORT_LONG);
        ArrayAdapter<String> p13airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsLong);
        p13airportAutoComplete.setAdapter(p13airportadapter);

        p13airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Show literal field if selected aiport = others
                String airport = ((TextView) view).getText().toString();
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT_LONG);
                LinearLayout llP13otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P13_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP13otros.setVisibility(VISIBLE);
                } else {
                    llP13otros.setVisibility(GONE);
                }
                ((TextView) activity.findViewById(R.id.survey_text_P13)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


        //Spinner P14
        Spinner p14spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P14);
        ArrayAdapter<CharSequence> p14Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p14strings,
                        R.layout.selection_spinner_item);
        p14Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p14spinner.setAdapter(p14Adapter);

        p14spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String selection = tv.getText().toString();

                String code = dm.getAssociatedCodeFor(selection, DictionaryManager.TRAVEL_REASON);
                if(code!= null && !code.contentEquals("0")) {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

                checkP33Visibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //P15
        RadioGroup rgP15 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P15);
        rgP15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    TextView tvP15a = (TextView) activity.findViewById(R.id.survey_text_P15a);
                    TextView tvP15b = (TextView) activity.findViewById(R.id.survey_text_P15b);
                    LinearLayout llP15num = (LinearLayout) activity.findViewById(R.id.survey_layout_P15nums);
                    switch (checkedId){
                        case R.id.survey_radio_P15_option1:
                            tvP15a.setVisibility(VISIBLE);
                            tvP15b.setVisibility(GONE);
                            llP15num.setVisibility(VISIBLE);
                            break;
                        case R.id.survey_radio_P15_option2:
                            tvP15a.setVisibility(GONE);
                            tvP15b.setVisibility(VISIBLE);
                            llP15num.setVisibility(VISIBLE);
                            break;
                        default:
                            tvP15a.setVisibility(INVISIBLE);
                            tvP15b.setVisibility(INVISIBLE);
                            llP15num.setVisibility(INVISIBLE);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_P15)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P16
        RadioGroup rgP16 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16);
        rgP16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP17 = (LinearLayout) activity.findViewById(R.id.survey_layout_P17);
                    LinearLayout llP18 = (LinearLayout) activity.findViewById(R.id.survey_layout_P18);
                    LinearLayout llP24 = (LinearLayout) activity.findViewById(R.id.survey_layout_P24);
                    switch (checkedId){
                        case R.id.survey_radio_P16_option1:
                            llP17.setVisibility(GONE);
                            llP18.setVisibility(GONE);
                            llP24.setVisibility(GONE);
                            break;
                        default:
                            llP17.setVisibility(VISIBLE);
                            llP18.setVisibility(VISIBLE);
                            llP24.setVisibility(VISIBLE);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_P16)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        EditText etP16num = (EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas);
        etP16num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String numPersonas  = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas))
                        .getText().toString();
                if(numPersonas != null && !numPersonas.isEmpty()){
                    ((RadioButton) activity.findViewById(R.id.survey_radio_P16_option3)).setChecked(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P17
        EditText etP17 = (EditText) activity.findViewById(R.id.survey_edit_P17);
        etP17.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP17 = (EditText) activity.findViewById(R.id.survey_edit_P17);
                String num = etP17.getText().toString();
                if(num != null && !num.isEmpty()){

                    int numMenores = Integer.parseInt(num);

                    int numPersonas = 0;
                    RadioGroup rgP16 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16);
                    int selOpt = rgP16.getCheckedRadioButtonId();
                    if(selOpt != R.id.survey_radio_P16_option1){
                        String numP = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();
                        if(numP != null && !numP.isEmpty()){
                            numPersonas = Integer.parseInt(numP);
                        }
                    } else {
                        numPersonas = 1;
                    }

                    if(numMenores>numPersonas){
                        etP17.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        Toast toast = Toast.makeText(activity, "El número de menores en el grupo (P17) no puede ser superior al número total de personas (P16)",
                                Toast.LENGTH_LONG);
                        toast.show();
                    } else{
                        etP17.setBackgroundColor(Color.WHITE);
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P17)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P17)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P18
        RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P18);
        rgP18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P18)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //P19
        EditText etP19 = (EditText) activity.findViewById(R.id.survey_edit_P19_numDias);
        etP19.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP19 = (EditText) activity.findViewById(R.id.survey_edit_P19_numDias);
                String numDias = etP19.getText().toString();
                if(numDias != null && !numDias.isEmpty()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P19)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });




        //P20
        RadioGroup rgP20 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P20);
        rgP20.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P20)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });



        //P21
        RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P21);
        rgP21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP22 = (LinearLayout) activity.findViewById(R.id.survey_layout_P22);
                    switch (checkedId){
                        case R.id.survey_radio_P21_option1:
                            llP22.setVisibility(GONE);
                            break;
                        default:
                            llP22.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P21)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        EditText etP21num = (EditText) activity.findViewById(R.id.survey_edit_P21_numViajes);
        etP21num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String numViajes  = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes))
                        .getText().toString();
                if(numViajes != null && !numViajes.isEmpty()){
                    ((RadioButton) activity.findViewById(R.id.survey_radio_P21_option2)).setChecked(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P22
        EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_P22_numViajes);
        etP22.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_P22_numViajes);
                String numViajes = etP22.getText().toString();
                if(numViajes != null && !numViajes.isEmpty()){
                    int numViajesMismaRuta = Integer.parseInt(numViajes);

                    int numTotalViajes = 0;
                    RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P21);
                    int selOpt = rgP21.getCheckedRadioButtonId();
                    if(selOpt != R.id.survey_radio_P21_option1){
                        String num = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes)).getText().toString();
                        if(num != null && !num.isEmpty()){
                            numTotalViajes = Integer.parseInt(num);
                        }
                    }

                    if(numViajesMismaRuta>numTotalViajes){
                        etP22.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        Toast toast = Toast.makeText(activity, "El número de viajes en la misma ruta (P22) no puede ser superior al número total de viajes (P21)",
                                Toast.LENGTH_LONG);
                        toast.show();
                    } else{
                        etP22.setBackgroundColor(Color.WHITE);
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P22)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P22)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });




        //P23
        RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P23);
        rgP23.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP23_num = (LinearLayout) activity.findViewById(R.id.survey_layout_P23_num);
                    LinearLayout llP24 = (LinearLayout) activity.findViewById(R.id.survey_layout_P24);
                    switch (checkedId) {
                        case R.id.survey_radio_P23_option1:
                            llP23_num.setVisibility(VISIBLE);
                            if (((RadioButton) activity.findViewById(R.id.survey_radio_P16_option1)).isChecked()){
                                llP24.setVisibility(GONE);
                            }else{
                                llP24.setVisibility(VISIBLE);
                            }
                            break;
                        case R.id.survey_radio_P23_option2:
                            llP23_num.setVisibility(INVISIBLE);
                            llP24.setVisibility(GONE);
                            break;
                        default:
                            llP23_num.setVisibility(INVISIBLE);
                            llP24.setVisibility(VISIBLE);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_P23)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //P24
        EditText etP24 = (EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas);
        etP24.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP24 = (EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas);
                String num = etP24.getText().toString();
                if(num != null && !num.isEmpty()){
                    int numPers = Integer.parseInt(num);

                    //Error P27>P19
                    String textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();
                    int selectedOption = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16)).getCheckedRadioButtonId();
                    if(selectedOption == R.id.survey_radio_P16_option1){
                        textP16 = "1";
                    }

                    String textP24 = num;
                    boolean error = false;
                    if(textP16 != null && !textP16.isEmpty()){
                        if(textP24 != null && !textP24.isEmpty()){
                            int numP16 = Integer.parseInt(textP16);
                            int numP24 = Integer.parseInt(textP24);
                            if(numP24>numP16){
                                Toast toast = Toast.makeText(activity, "El número de personas con bultos (P24) no puede ser mayor que el número total de personas del grupo (P16)", Toast.LENGTH_LONG);
                                toast.show();
                                etP24.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                error = true;
                            } else {
                                etP24.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P24)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    etP24.setBackgroundColor(Color.WHITE);
                    ((TextView) activity.findViewById(R.id.survey_text_P24)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) { }
        });



        //P25
        RadioGroup rgP25 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P25);
        rgP25.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P25)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });




        //P26
        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26);
        rgP26.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P26)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //Spinner P27
        Spinner p27spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P27);
        ArrayAdapter<CharSequence> p27Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p27strings,
                        R.layout.selection_spinner_item);
        p27Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p27spinner.setAdapter(p27Adapter);

        p27spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String selection = tv.getText().toString();

                if(selection!= null && !selection.contentEquals("...")) {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    return;
                }

                ((TextView) activity.findViewById(R.id.survey_text_P27)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //P28
        RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P28);
        rgP28.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P28)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P29
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P29);
        rgP29.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    TextView tvP29a = (TextView) activity.findViewById(R.id.survey_text_P29a);
                    EditText etP29a = (EditText) activity.findViewById(R.id.survey_edit_P29a);
                    switch (checkedId){
                        case R.id.survey_radio_P29_option1:
                            tvP29a.setVisibility(VISIBLE);
                            etP29a.setVisibility(VISIBLE);
                            break;
                        default:
                            tvP29a.setVisibility(INVISIBLE);
                            etP29a.setVisibility(INVISIBLE);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_P29)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        //P30
        RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P30);
        rgP30.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP30a = (LinearLayout) activity.findViewById(R.id.survey_layout_P30a);
                    switch (checkedId){
                        case R.id.survey_radio_P30_option1:
                            llP30a.setVisibility(VISIBLE);
                            break;
                        default:
                            llP30a.setVisibility(GONE);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_P30)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //Spinners P30b
        Spinner p30bitem1spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P30b_item1);
        ArrayAdapter<CharSequence> p30bitem1Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem1Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem1spinner.setAdapter(p30bitem1Adapter);

        Spinner p30bitem2spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P30b_item2);
        ArrayAdapter<CharSequence> p30bitem2Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem2Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem2spinner.setAdapter(p30bitem2Adapter);

        Spinner p30bitem3spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P30b_item3);
        ArrayAdapter<CharSequence> p30bitem3Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem3Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem3spinner.setAdapter(p30bitem3Adapter);

        Spinner p30bitem4spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P30b_item4);
        ArrayAdapter<CharSequence> p30bitem4Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem4Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem4spinner.setAdapter(p30bitem4Adapter);

        Spinner p30bitem5spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P30b_item5);
        ArrayAdapter<CharSequence> p30bitem5Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem4Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem5spinner.setAdapter(p30bitem5Adapter);


        //P31
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P31);
        rgP31.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P32);
                    switch (checkedId){
                        case R.id.survey_radio_P31_option1:
                            rgP32.setVisibility(VISIBLE);
                            break;
                        default:
                            rgP32.setVisibility(GONE);
                            break;
                    }

                    checkP33Visibility();
                    ((TextView) activity.findViewById(R.id.survey_text_P31)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P32
        RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P32);
        rgP32.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P32)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //Spinnner P33
        Spinner p33spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P33);
        ArrayAdapter<CharSequence> p33Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p33strings,
                        R.layout.selection_spinner_item);
        p33Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p33spinner.setAdapter(p33Adapter);

        p33spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout llp33others = (LinearLayout) activity.findViewById(R.id.survey_layout_P33_others);

                TextView tvSelected = (TextView) view;
                String[] possibleValues = activity.getResources().getStringArray(R.array.p33strings);
                if(tvSelected.getText().toString().contentEquals(possibleValues[possibleValues.length-1])){
                    llp33others.setVisibility(VISIBLE);
                } else {
                    llp33others.setVisibility(GONE);
                }
                if(possibleValues!=null && !tvSelected.getText().toString().contentEquals(possibleValues[0])){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P33)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //P34
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P34);
        rgP34.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P34)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P35
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P35);
        rgP35.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P35)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P36
        RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P36);
        rgP36.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_P36)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


    }



    private void checkValidTimeP8() {
        String textHoraLlegada = ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).getText().toString();
        String textMinLlegada = ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos)).getText().toString();

        Calendar llegadaTime = Calendar.getInstance();
        if(textHoraLlegada!= null && !textHoraLlegada.isEmpty()){
            if(textMinLlegada!= null && !textMinLlegada.isEmpty()){

                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(textHoraLlegada));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(textMinLlegada));
                llegadaTime.set(Calendar.SECOND, 0);
            } else {
                ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos))
                        .setBackgroundColor(Color.WHITE);
                return;
            }
        } else {
            ((EditText) activity.findViewById(R.id.survey_edit_P8_hora))
                    .setBackgroundColor(Color.WHITE);
            return;
        }

        Date fechaEncuesta = Calendar.getInstance().getTime();
        SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
        String dateText = ((EditText) activity.findViewById(R.id.survey_edit_fecha)).getText().toString();
        String timeText = ((EditText) activity.findViewById(R.id.survey_edit_hora)).getText().toString();
        try {
            fechaEncuesta = sdfDate.parse(dateText +" "+timeText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(llegadaTime.getTime().getTime()>fechaEncuesta.getTime()){
            ((EditText) activity.findViewById(R.id.survey_edit_P8_hora))
                    .setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
            ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos))
                    .setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
            Toast toast = Toast.makeText(activity, "La hora de llegada no puede ser posterior a la hora de realización de la encuesta",
                    Toast.LENGTH_LONG);
            toast.show();
        } else {
            ((EditText) activity.findViewById(R.id.survey_edit_P8_hora))
                    .setBackgroundColor(Color.WHITE);
            ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos))
                    .setBackgroundColor(Color.WHITE);
        }

    }




    private void setP2areaSpinner(String selectedOption) {
        int resourceToLoad = dm.getAreaStringsResourceId(selectedOption);
        Spinner p2spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P2_area);
        if(resourceToLoad>0) {
            ArrayAdapter<CharSequence> p2Adapter =
                    ArrayAdapter.createFromResource(activity,
                            resourceToLoad,
                            R.layout.selection_spinner_item);
            p2Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
            p2spinner.setAdapter(p2Adapter);
        } else {
            p2spinner.setAdapter(null);
        }
        return;
    }


    private void loadP3Fields() {

        AutoCompleteTextView p3provinciaAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_prov);
        String[] provincias = dm.getStringsArrayFor(DictionaryManager.PROVINCIA_PROC);
        ArrayAdapter<String> p3provinciaadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, provincias);
        p3provinciaAutoComplete.setAdapter(p3provinciaadapter);

        p3provinciaAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProv = ((TextView) view).getText().toString();
                if(selectedProv!=null && !selectedProv.isEmpty()) {
                    String codProvText = dm.getAssociatedCodeFor(selectedProv, DictionaryManager.PROVINCIA_PROC);
                    if (codProvText != null) {
                        AutoCompleteTextView p3localidadAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_loc);
                        String[] localidades = dm.getLocalidadesProcFor(codProvText);
                        if (codProvText.contentEquals("99")) {


                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P3_provLiteral)).setVisibility(View.VISIBLE);
                            ((EditText) activity.findViewById(R.id.survey_edit_P3_prov_lit)).requestFocus();

                        } else {
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P3_provLiteral)).setVisibility(View.GONE);
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P3_locLiteral)).setVisibility(View.GONE);
                        }
                            ArrayAdapter<String> p3localidadadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, localidades);
                            p3localidadAutoComplete.setAdapter(p3localidadadapter);

                            p3localidadAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String selectedLoc = ((TextView) view).getText().toString();
                                    if (selectedLoc != null && !selectedLoc.isEmpty()) {
                                        String codLocText = dm.getAssociatedCodeFor(selectedLoc, DictionaryManager.LOCALIDAD_PROC);
                                        if (codLocText != null && codLocText.contentEquals("99999")) {
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P3_locLiteral)).setVisibility(View.VISIBLE);
                                            ((EditText) activity.findViewById(R.id.survey_edit_P3_loc_lit)).requestFocus();
                                        } else {
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P3_locLiteral)).setVisibility(View.GONE);
                                        }
                                    }
                                    //Update P8 question text including selected Localidad
                                    String P4text = activity.getResources().getString(R.string.survey_text_P4).toString();
                                    String P4textFormated = String.format(P4text, selectedLoc);
                                    TextView tvP4 = (TextView) activity.findViewById(R.id.survey_text_P4);
                                    tvP4.setText(P4textFormated);
                                }
                            });

                    }
                }
            }
        });


    }

    /*
     * check flow filters for showing block P26-P28
     */
    private void checkP26_28(){
        if(airportCode!=null && (airportCode.contentEquals("OVD") || airportCode.contentEquals("BIO"))){
            ((LinearLayout) activity.findViewById(R.id.survey_block_P26_P28)).setVisibility(GONE);
        } else {
            RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
            int selectedOptionP3 =rgP3.getCheckedRadioButtonId();

            RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P11);
            int selectedOptionP11 =rgP11.getCheckedRadioButtonId();

            AutoCompleteTextView actvP9 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
            String textP9 = actvP9.getText().toString();
            String selectedAirportCode = dm.getAssociatedCodeFor(textP9, DictionaryManager.AIRPORT);

            LinearLayout llP26_28 = (LinearLayout) activity.findViewById(R.id.survey_block_P26_P28);
            llP26_28.setVisibility(GONE);
            if( (selectedOptionP3<0) || (selectedOptionP3!=R.id.survey_radio_P3_option3)){
                if( (selectedOptionP11<0) || (selectedOptionP11==R.id.survey_radio_P11_option1)){
                    if((selectedAirportCode == null) || (selectedAirportCode.contentEquals("MAD")) ){
                        llP26_28.setVisibility(VISIBLE);
                    }
                }
            }
        }

    }


    private void checkP33Visibility(){
        LinearLayout llP33 = (LinearLayout) activity.findViewById(R.id.survey_layout_P33);
        llP33.setVisibility(VISIBLE);

        String selection = (String) ((Spinner) activity.findViewById(R.id.survey_spinner_P14)).getSelectedItem();
        String code = dm.getAssociatedCodeFor(selection,DictionaryManager.TRAVEL_REASON);
        int optionValue = 0;
        if(code != null && !code.isEmpty()) {
            // Not business passengers
            optionValue = Integer.parseInt(code);

        }

        int selOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P31)).getCheckedRadioButtonId();

        if (optionValue > 200 || (selOpt!=R.id.survey_radio_P31_option1 && selOpt >0)) {
            llP33.setVisibility(GONE);
        }

    }









    private void initializeViewElementsModel2(){

        //P1
        AutoCompleteTextView p1autoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P1);
        String[] countries = dm.getStringsArrayFor(DictionaryManager.COUNTRY);
        ArrayAdapter<String> p1adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
        p1autoComplete.setAdapter(p1adapter);

        p1autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry =((TextView) view).getText().toString();
                LinearLayout llp1otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P1_otros);
                String countryCode = dm.getAssociatedCodeFor(selectedCountry, DictionaryManager.COUNTRY);
                if(countryCode!= null && countryCode.contentEquals("999")){
                    llp1otros.setVisibility(View.VISIBLE);
                } else {
                    llp1otros.setVisibility(View.GONE);
                }

                ((TextView) activity.findViewById(R.id.survey_text_M2_P1)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

            }
        });


        //AutoComplete Text P2
        AutoCompleteTextView p2autoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
//      String[] countries = getResources().getStringArray(R.array.countryStrings);
        ArrayAdapter<String> p2adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
        p2autoComplete.setAdapter(p2adapter);


        p2autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout locationSpain = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P2_locationSpain);
                LinearLayout otherCountry = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P2_otherCountry);
                LinearLayout otherLiteral = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P2_otros);

                RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);

                String selectedOption = ((TextView) view).getText().toString();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COUNTRY);

                if (selectedCode.contains("724")) {
                    locationSpain.setVisibility(View.VISIBLE);
                    otherCountry.setVisibility(View.GONE);
                    otherLiteral.setVisibility(View.GONE);
                    rbResidencia.setEnabled(true);
                } else if (selectedCode.contains("999")) {
                    locationSpain.setVisibility(GONE);
                    otherLiteral.setVisibility(VISIBLE);
                    otherCountry.setVisibility(GONE);
                    rbResidencia.setEnabled(false);
                } else if (dm.getAreaStringsResourceId(selectedOption) > 0) {
                    locationSpain.setVisibility(View.GONE);
                    otherLiteral.setVisibility(GONE);
                    otherCountry.setVisibility(View.VISIBLE);
                    rbResidencia.setEnabled(false);
                    setM2P2areaSpinner(selectedOption);
                } else {
                    locationSpain.setVisibility(View.GONE);
                    otherLiteral.setVisibility(GONE);
                    otherCountry.setVisibility(View.GONE);
                    rbResidencia.setEnabled(false);
                }
                //Change question text color as answered
                ((TextView) activity.findViewById(R.id.survey_text_M2_P2)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });

        AutoCompleteTextView p2provinciaAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2_prov);
        String[] provincias = dm.getStringsArrayFor(DictionaryManager.PROVINCIA);
        ArrayAdapter<String> p2provinciaadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, provincias);
        p2provinciaAutoComplete.setAdapter(p2provinciaadapter);

        p2provinciaAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProv = ((TextView) view).getText().toString();
                if(selectedProv!=null && !selectedProv.isEmpty()){
                    String codProvText = dm.getAssociatedCodeFor(selectedProv, DictionaryManager.PROVINCIA);
                    if(codProvText!= null)  {
                        AutoCompleteTextView p2localidadAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2_loc);
                        String[] localidades = dm.getLocalidadesFor(codProvText);

                        ArrayAdapter<String> p2localidadadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, localidades);
                        p2localidadAutoComplete.setAdapter(p2localidadadapter);

                        p2localidadAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedLoc = ((TextView) view).getText().toString();
                                if(selectedLoc!=null && !selectedLoc.isEmpty()) {
                                    String codLocText = dm.getAssociatedCodeFor(selectedLoc, DictionaryManager.LOCALIDAD);
                                    if(codLocText!= null && codLocText.contentEquals("99999"))  {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P2_locLiteral)).setVisibility(View.VISIBLE);
                                        ((EditText) activity.findViewById(R.id.survey_edit_M2_P2_loc_lit)).requestFocus();
                                    } else {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P2_locLiteral)).setVisibility(View.GONE);
                                    }
                                }
                            }
                        });

                        //TODO esto es un parche para filtra las islas en "Viene de su residencia"
                        if(airportCode.contentEquals("PMI")){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            if(!codProvText.contentEquals("7")){
                                rbResidencia.setEnabled(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("IBZ")){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            if(!codProvText.contentEquals("7")){
                                rbResidencia.setEnabled(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("MAH")){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            if(!codProvText.contentEquals("7")){
                                rbResidencia.setEnabled(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        }

                    }
                }
            }
        });


        String[] airportsShort = dm.getStringsArrayFor(DictionaryManager.AIRPORT);

        if(airportCode.contentEquals("IBZ")  || airportCode.contentEquals("MAH")){
            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P3)).setVisibility(GONE);
            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P4)).setVisibility(GONE);
            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P5)).setVisibility(GONE);
            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P6)).setVisibility(GONE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option4)).setVisibility(VISIBLE);

            AutoCompleteTextView actvP7airport = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P7_airport);
            //String[] airportsShort = dm.getStringsArrayFor(DictionaryManager.AIRPORT);
            ArrayAdapter<String> p7airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsShort);
            actvP7airport.setAdapter(p7airportadapter);

            actvP7airport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Update question text in P11
                    TextView tvSelected = (TextView) view;
                    String airport = tvSelected.getText().toString();

                    //Show literal field if selected aiport = others
                    String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT);
                    LinearLayout llP7otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_otrosAeropuerto);
                    if (airportCode != null && airportCode.contentEquals("999")) {
                        llP7otros.setVisibility(VISIBLE);
                    } else {
                        llP7otros.setVisibility(GONE);
                    }
                }
            });

        } else {

            //P3
            RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P3);
            rgP3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                    if (rb.isChecked()) {
                        LinearLayout llP7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7);
                        RelativeLayout llP8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P8);
                        LinearLayout llP09_P11 = (LinearLayout) activity.findViewById(R.id.survey_block_M2_P09_P11);

                        LinearLayout llP4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P4);
                        LinearLayout llP5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P5);
                        LinearLayout llP6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P6);
                        switch (checkedId) {
                            case R.id.survey_radio_M2_P3_option1:
                                llP7.setVisibility(GONE);
                                llP8.setVisibility(GONE);
                                llP09_P11.setVisibility(GONE);
                                llP4.setVisibility(VISIBLE);
                                llP5.setVisibility(VISIBLE);
                                llP6.setVisibility(VISIBLE);
                                break;
                            case R.id.survey_radio_M2_P3_option2:
                                llP7.setVisibility(VISIBLE);
                                llP8.setVisibility(VISIBLE);
                                llP09_P11.setVisibility(VISIBLE);

                                llP4.setVisibility(GONE);
                                llP5.setVisibility(GONE);
                                llP6.setVisibility(GONE);
                                break;
                            default:
                                llP7.setVisibility(VISIBLE);
                                llP8.setVisibility(VISIBLE);
                                llP09_P11.setVisibility(VISIBLE);

                                llP4.setVisibility(VISIBLE);
                                llP5.setVisibility(VISIBLE);
                                llP6.setVisibility(VISIBLE);
                                break;
                        }

                        //Change text color as answered
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P3)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                    }
                }
            });


            //P4
            AutoCompleteTextView p4airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P4);
//            String[] airports = dm.getStringsArrayFor(DictionaryManager.AIRPORT);
            ArrayAdapter<String> p4airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsShort);
            p4airportAutoComplete.setAdapter(p4airportadapter);

            p4airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Update question text in P11
                    TextView tvSelected = (TextView) view;
                    String airport = tvSelected.getText().toString();

                    //Show literal field if selected aiport = others
                    String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT);
                    LinearLayout llP4otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P4_otros);
                    if (airportCode != null && airportCode.contentEquals("999")) {
                        llP4otros.setVisibility(VISIBLE);
                    } else {
                        llP4otros.setVisibility(GONE);
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P4)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            });


            //P5
            AutoCompleteTextView p5CompanyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P5);
            String[] companiesShort = dm.getStringsArrayFor(DictionaryManager.COMPANY);
            ArrayAdapter<String> p5adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesShort);
            p5CompanyAutoComplete.setAdapter(p5adapter);

            p5CompanyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedOption = (String) ((TextView) view).getText();
                    String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COMPANY);

                    //Show literal field if selected aiport = others
                    LinearLayout llP5otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P5_otros);
                    if (selectedCode != null && selectedCode.contentEquals("999")) {
                        llP5otros.setVisibility(VISIBLE);
                    } else {
                        llP5otros.setVisibility(GONE);
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P5)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            });


            //P6
            RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P6);
            rgP6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                    if (rb.isChecked()) {
                        //Change question text color as answered
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P6)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }
            });
        }


        //P7

        String P7text = activity.getResources().getString(R.string.survey_text_M2_P7).toString();
        String P7textFormated = String.format(P7text,this.airportName);
        TextView tvP7 = (TextView) activity.findViewById(R.id.survey_text_M2_P7);
        tvP7.setText(P7textFormated);

        RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P7);
        rgP7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);

                if(rb.isChecked()){
                    LinearLayout localidadP7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_localidad);
                    LinearLayout playaP7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_playa);
                    LinearLayout conexionP7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_conexion);
                    RelativeLayout rlP8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P8);
                    LinearLayout blP9_P11 = (LinearLayout) activity.findViewById(R.id.survey_block_M2_P09_P11);

                    switch(checkedId){
                        case R.id.survey_radio_M2_P7_option1:
                            localidadP7.setVisibility(INVISIBLE);
                            playaP7.setVisibility(INVISIBLE);
                            rlP8.setVisibility(GONE);
                            blP9_P11.setVisibility(VISIBLE);
                            break;
                        case R.id.survey_radio_M2_P7_option2_localidad:
                            loadM2P7Fields();
                            localidadP7.setVisibility(View.VISIBLE);
                            playaP7.setVisibility(INVISIBLE);
                            conexionP7.setVisibility(INVISIBLE);
                            rlP8.setVisibility(VISIBLE);
                            blP9_P11.setVisibility(VISIBLE);
                            break;
                        case R.id.survey_radio_M2_P7_option3:
                            localidadP7.setVisibility(INVISIBLE);
                            playaP7.setVisibility(VISIBLE);
                            conexionP7.setVisibility(INVISIBLE);
                            rlP8.setVisibility(VISIBLE);
                            blP9_P11.setVisibility(VISIBLE);
                            break;
                        case R.id.survey_radio_M2_P7_option4:
                            localidadP7.setVisibility(INVISIBLE);
                            playaP7.setVisibility(INVISIBLE);
                            conexionP7.setVisibility(VISIBLE);
                            rlP8.setVisibility(GONE);
                            blP9_P11.setVisibility(GONE);
                            break;
                        default:
                            localidadP7.setVisibility(INVISIBLE);
                            playaP7.setVisibility(INVISIBLE);
                            conexionP7.setVisibility(INVISIBLE);
                            rlP8.setVisibility(VISIBLE);
                            blP9_P11.setVisibility(VISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P7)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });






        //P8
        RadioGroup rgP8 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P8);
        rgP8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    EditText etP8others = (EditText) activity.findViewById(R.id.survey_edit_M2_P8_otros);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P8_option7:
                            etP8others.setVisibility(VISIBLE);
                            etP8others.requestFocus();
                            break;
                        default:
                            etP8others.setVisibility(INVISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P8)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //P9
        RadioGroup rgP9 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P9);
        rgP9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);

                if(rb.isChecked()){
                    RelativeLayout rlP10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P10);
                    EditText edP9_others = (EditText) activity.findViewById(R.id.survey_edit_M2_P9_others);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P9_option3:
                        case R.id.survey_radio_M2_P9_option4:
                            rlP10.setVisibility(VISIBLE);
                            edP9_others.setVisibility(INVISIBLE);
                            break;
                        case R.id.survey_radio_M2_P9_option7:
                            rlP10.setVisibility(GONE);
                            edP9_others.setVisibility(VISIBLE);
                            break;
                        default:
                            rlP10.setVisibility(GONE);
                            edP9_others.setVisibility(INVISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P9)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P10
        RadioGroup rgP10 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P10);
        rgP10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP10b = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P10b);
                    switch(checkedId){
                        case R.id.survey_radio_M2_P10_option6:
                            llP10b.setVisibility(VISIBLE);
                            break;
                        default:
                            llP10b.setVisibility(GONE);
                            break;
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P10)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P11
        EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M2_P11);
        etP11.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M2_P11);
                String text = etP11.getText().toString();
                if(text != null && !text.isEmpty()){
                    //Change question text color as answered
                    TextView tvP11 = (TextView) activity.findViewById(R.id.survey_text_M2_P11);
                    (tvP11).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });





        //P12
        EditText etP12hora = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora);
        etP12hora.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP12hora = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora);
                    String textHora = etP12hora.getText().toString();
                    if(textHora != null && !textHora.isEmpty()){
                        int hora = Integer.parseInt(textHora);
                        if(hora<0 || hora>23){
                            etP12hora.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            Toast toast = Toast.makeText(activity, "El campo hora debe tener un valor comprendido entre 00 y 23",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            etP12hora.setBackgroundColor(Color.WHITE);
                        }

                        EditText etP12min = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos);
                        String textMin = etP12min.getText().toString();
                        if(textMin != null && !textMin.isEmpty()) {
                            //Change question text color as answered
                            TextView tvP12 = (TextView) activity.findViewById(R.id.survey_text_M2_P12);
                            (tvP12).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                        }
                    }

                    checkValidTimeM2P12();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });





//                .setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                /* When focus is lost check that the text field
//                * has valid values.
//                */
//                if (!hasFocus) {
//                    EditText etP12hora = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora);
//                    String textHora = etP12hora.getText().toString();
//                    if(textHora != null && !textHora.isEmpty()){
//                        int hora = Integer.parseInt(textHora);
//                        if(hora<0 || hora>23){
//                            etP12hora.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
//                            Toast toast = Toast.makeText(activity, "El campo hora debe tener un valor comprendido entre 00 y 23",
//                                    Toast.LENGTH_LONG);
//                            toast.show();
//                        } else {
//                            etP12hora.setBackgroundColor(Color.WHITE);
//                        }
//
//                        EditText etP12min = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos);
//                        String textMin = etP12min.getText().toString();
//                        if(textMin != null && !textMin.isEmpty()) {
//                            //Change question text color as answered
//                            TextView tvP12 = (TextView) activity.findViewById(R.id.survey_text_M2_P12);
//                            (tvP12).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
//                        }
//                    }
//
//                    checkValidTimeM2P12();
//
//                }
//            }
//        });

        EditText etP12min = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos);
        etP12min.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP12min = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos);
                    String textMin = etP12min.getText().toString();
                    if(textMin != null && !textMin.isEmpty()) {
                        int min = Integer.parseInt(textMin);
                        if (min < 0 || min > 59) {
                            etP12min.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            Toast toast = Toast.makeText(activity, "El campo minutos debe tener un valor comprendido entre 00 y 59",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            etP12min.setBackgroundColor(Color.WHITE);
                        }

                        EditText etP12hora = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora);
                        String textHora = etP12hora.getText().toString();
                        if (textHora != null && !textHora.isEmpty()) {
                            //Change question text color as answered
                            TextView tvP12 = (TextView) activity.findViewById(R.id.survey_text_M2_P12);
                            (tvP12).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                        }
                    }
                //TODO check arrival time before quest time
                checkValidTimeM2P12();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


//                .setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                /* When focus is lost check that the text field
//                * has valid values.
//                */
//                if (!hasFocus) {
//                    EditText etP12min = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos);
//                    String textMin = etP12min.getText().toString();
//                    if(textMin != null && !textMin.isEmpty()){
//                        int min = Integer.parseInt(textMin);
//                        if(min < 0 || min > 59){
//                            etP12min.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
//                            Toast toast = Toast.makeText(activity, "El campo minutos debe tener un valor comprendido entre 00 y 59",
//                                    Toast.LENGTH_LONG);
//                            toast.show();
//                        } else {
//                            etP12min.setBackgroundColor(Color.WHITE);
//                        }
//
//                        EditText etP12hora = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora);
//                        String textHora = etP12hora.getText().toString();
//                        if(textHora != null && !textHora.isEmpty()) {
//                            //Change question text color as answered
//                            TextView tvP12 = (TextView) activity.findViewById(R.id.survey_text_M2_P12);
//                            (tvP12).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
//
//                            //TODO check arrival time before quest time
//                            checkValidTimeM2P12();
//                        }
//                    }
//                }
//            }
//        });



        //P13
        AutoCompleteTextView p13airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P13);
//        String[] airports = dm.getStringsArrayFor(DictionaryManager.AIRPORT);
        ArrayAdapter<String> p13airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsShort);
        p13airportAutoComplete.setAdapter(p13airportadapter);

        p13airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Update question text in P11
                TextView tvSelected = (TextView) view;
                String airport = tvSelected.getText().toString();
                String P15text = activity.getResources().getString(R.string.survey_text_M2_P15).toString();
                String P15textFormated = String.format(P15text,airport);
                TextView tvP15 = (TextView) activity.findViewById(R.id.survey_text_M2_P15);
                tvP15.setText(P15textFormated);

                //Show literal field if selected aiport = others
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT);
                LinearLayout llP13otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P13_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP13otros.setVisibility(VISIBLE);
                } else {
                    llP13otros.setVisibility(GONE);
                }

                //Check destination airport is different from origin
                AutoCompleteTextView p13airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P13);
                p13airportAutoComplete.setBackgroundColor(Color.WHITE);
                if(airportCode!= null && !airportCode.isEmpty()){
                    AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P4);
                    String airportOriginName =  actvP4.getText().toString();
                    String airportOriginCode =dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT);
                    if(airportOriginCode!= null && !airportOriginCode.isEmpty()){
                        if(airportCode.contentEquals(airportOriginCode)){
                            p13airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            Toast toast = Toast.makeText(activity, "El aeropuerto de destino (P13) no puede ser el mismo que el de origen (P4)",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }

                //Change question text color as answered
                ((TextView) activity.findViewById(R.id.survey_text_M2_P13)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });



        //P14
        AutoCompleteTextView p14CompanyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P14_company);
        String[] companiesShort = dm.getStringsArrayFor(DictionaryManager.COMPANY);
        ArrayAdapter<String> p14adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesShort);
        p14CompanyAutoComplete.setAdapter(p14adapter);

        p14CompanyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) ((TextView) view).getText();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COMPANY);
                ((TextView) activity.findViewById(R.id.survey_text_M2_P14_companyCode))
                        .setText(selectedCode+"-");

                //Show literal field if selected aiport = others
                LinearLayout llP10otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P14_otros);
                if(selectedCode!=null && selectedCode.contentEquals("999")){
                    llP10otros.setVisibility(VISIBLE);
                } else {
                    llP10otros.setVisibility(GONE);
                }

                String numVuelo = ((EditText) activity.findViewById(R.id.survey_edit_M2_P14)).getText().toString();
                if(numVuelo!= null && ! numVuelo.isEmpty()) {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        EditText etP14 = (EditText) activity.findViewById(R.id.survey_edit_M2_P14);
        etP14.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                * has valid values.
                */
                if (!hasFocus) {
                    EditText etP14 = (EditText) activity.findViewById(R.id.survey_edit_M2_P14);
                    String numVuelo = etP14.getText().toString();
                    if(numVuelo != null && !numVuelo.isEmpty()){

                        AutoCompleteTextView actvP14 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P14_company);
                        String company = actvP14.getText().toString();
                        if(company != null && !company.isEmpty()) {
                            //Change question text color as answered
                            ((TextView) activity.findViewById(R.id.survey_text_M2_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                        }
                    }
                }
            }
        });




        //P15
        RadioGroup rgP15 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P15);
        rgP15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP15b = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P15b);
                    LinearLayout llP16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P16);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P15_option1:
                            llP15b.setVisibility(GONE);
                            llP16.setVisibility(GONE);
                            break;
                        default:
                            llP15b.setVisibility(VISIBLE);
                            llP16.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P15)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //AutoComplete P15b
        AutoCompleteTextView p15bcompanyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P15b);
        String[] companiesLong = dm.getStringsArrayFor(DictionaryManager.COMPANY_LONG);
        ArrayAdapter<String> p15bcompanyadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesLong);
        p15bcompanyAutoComplete.setAdapter(p15bcompanyadapter);


        p15bcompanyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Show literal field if selected aiport = others
                String company = ((TextView) view).getText().toString();
                String companyCode = dm.getAssociatedCodeFor(company, DictionaryManager.COMPANY_LONG);
                LinearLayout llP15botros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P15b_otros);
                if(companyCode!=null && companyCode.contentEquals("999")){
                    llP15botros.setVisibility(VISIBLE);
                } else {
                    llP15botros.setVisibility(GONE);
                }


                //Change question text color as answered
                ((TextView) activity.findViewById(R.id.survey_text_M2_P15b)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


        //AutoComplete P16
        AutoCompleteTextView p16airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P16);
        String[] airportsLong = dm.getStringsArrayFor(DictionaryManager.AIRPORT_LONG);
        ArrayAdapter<String> p16airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsLong);
        p16airportAutoComplete.setAdapter(p16airportadapter);

        p16airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Show literal field if selected aiport = others
                String airport = ((TextView) view).getText().toString();
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT_LONG);
                LinearLayout llP16otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P16_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP16otros.setVisibility(VISIBLE);
                } else {
                    llP16otros.setVisibility(GONE);
                }

                //Check destination airport is different from origin
                AutoCompleteTextView p16airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P16);
                p16airportAutoComplete.setBackgroundColor(Color.WHITE);
                if(airportCode!= null && !airportCode.isEmpty()){
                    AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P4);
                    String airportOriginName =  actvP4.getText().toString();
                    String airportOriginCode =dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT_LONG);
                    if(airportOriginCode!= null && !airportOriginCode.isEmpty()){
                        if(airportCode.contentEquals(airportOriginCode)){
                            p16airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            Toast toast = Toast.makeText(activity, "El aeropuerto de finalización del viaje (P16) no puede ser el mismo que el de origen (P4)",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }

                //Change question text color as answered
                ((TextView) activity.findViewById(R.id.survey_text_M2_P16)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


        //P17
        Spinner p17spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P17);
        ArrayAdapter<CharSequence> p17Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p14strings,
                        R.layout.selection_spinner_item);
        p17Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p17spinner.setAdapter(p17Adapter);

        p17spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String selection = tv.getText().toString();

                String code = dm.getAssociatedCodeFor(selection,DictionaryManager.TRAVEL_REASON);
                if(code!= null && !code.contentEquals("0")) {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P17)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

                checkM2P33Visibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //P18
        RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P18);
        rgP18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    TextView tvP18a = (TextView) activity.findViewById(R.id.survey_text_M2_P18a);
                    TextView tvP18b = (TextView) activity.findViewById(R.id.survey_text_M2_P18b);
                    LinearLayout llP18num = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P18nums);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P18_option1:
                            tvP18a.setVisibility(VISIBLE);
                            tvP18b.setVisibility(GONE);
                            llP18num.setVisibility(VISIBLE);
                            break;
                        case R.id.survey_radio_M2_P18_option2:
                            tvP18a.setVisibility(GONE);
                            tvP18b.setVisibility(VISIBLE);
                            llP18num.setVisibility(VISIBLE);
                            break;
                        default:
                            tvP18a.setVisibility(INVISIBLE);
                            tvP18b.setVisibility(INVISIBLE);
                            llP18num.setVisibility(INVISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P18)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                    if(checkedId== R.id.survey_radio_M2_P18_option1){
                        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P3);
                        int selP3 = rgP3.getCheckedRadioButtonId();
                        RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P7);
                        int selP7 = rgP7.getCheckedRadioButtonId();

                        if(selP3!=R.id.survey_radio_M2_P3_option1 && (selP7==R.id.survey_radio_M2_P7_option2_localidad || selP7==R.id.survey_radio_M2_P7_option3)){
                            ((RadioButton) activity.findViewById(R.id.survey_radio_M2_P18_option1))
                                    .setTextColor(activity.getResources().getColor(R.color.aenaRed));
                            Toast toast = Toast.makeText(activity, "No es habitual que sea un viaje de ida si el pasajero no viene en conexión (P3) o no viene de su residencia. Verificar!",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            ((TextView) activity.findViewById(R.id.survey_text_M2_P18_error)).setVisibility(VISIBLE);
                        }
                    } else {
                        ((RadioButton) activity.findViewById(R.id.survey_radio_M2_P18_option1))
                                .setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P18_error)).setVisibility(GONE);
                    }
                }
            }
        });


        //P19
        RadioGroup rgP19 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19);
        rgP19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P20);
                    LinearLayout llP21 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P21);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P19_option1:
                            llP20.setVisibility(GONE);
                            llP21.setVisibility(GONE);
                            break;
                        default:
                            llP20.setVisibility(VISIBLE);
                            llP21.setVisibility(VISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P19)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        EditText etP19num = (EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas);
        etP19num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String numPersonas  = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas))
                        .getText().toString();
                if(numPersonas != null && !numPersonas.isEmpty()){
                    ((RadioButton) activity.findViewById(R.id.survey_radio_M2_P19_option3)).setChecked(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P20
        EditText etP20 = (EditText) activity.findViewById(R.id.survey_edit_M2_P20);
        etP20.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP20 = (EditText) activity.findViewById(R.id.survey_edit_M2_P20);
                    String num = etP20.getText().toString();
                    if(num != null && !num.isEmpty()){

                            int numMenores = Integer.parseInt(num);

                            int numPersonas = 0;
                            RadioGroup rgP19 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19);
                            int selOpt = rgP19.getCheckedRadioButtonId();
                            if(selOpt != R.id.survey_radio_M2_P19_option1){
                                String numP = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
                                if(numP != null && !numP.isEmpty()){
                                    numPersonas = Integer.parseInt(numP);
                                }
                            } else {
                                numPersonas = 1;
                            }

                            if(numMenores>numPersonas){
                                etP20.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                Toast toast = Toast.makeText(activity, "El número de menores en el grupo (P20) no puede ser superior al número total de personas (P19)",
                                        Toast.LENGTH_LONG);
                                toast.show();
                            } else{
                                etP20.setBackgroundColor(Color.WHITE);
                            }

                            //Change question text color as answered
                            ((TextView) activity.findViewById(R.id.survey_text_M2_P20)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    } else {
                        //Change question text color as answered
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P20)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                    }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P21
        RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P21);
        rgP21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P21)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //P22
        EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias);
        etP22.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                * has valid values.
                */
                if (!hasFocus) {
                    EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias);
                    String numDias = etP22.getText().toString();
                    if(numDias != null && !numDias.isEmpty()){

                        //Change question text color as answered
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P22)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }
            }
        });


        //P23
        RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P23);
        rgP23.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P23)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });



        //P24
        RadioGroup rgP24 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P24);
        rgP24.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP25 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P25);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P24_option1:
                            llP25.setVisibility(GONE);
                            break;
                        default:
                            llP25.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P24)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        EditText etP24num = (EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes);
        etP24num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String numViajes  = ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes))
                        .getText().toString();
                if(numViajes != null && !numViajes.isEmpty()){
                    ((RadioButton) activity.findViewById(R.id.survey_radio_M2_P24_option2)).setChecked(true);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P25
        EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes);
        etP25.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes);
                    String numViajes = etP25.getText().toString();
                    if(numViajes != null && !numViajes.isEmpty()){
                        int numViajesMismaRuta = Integer.parseInt(numViajes);

                        int numTotalViajes = 0;
                        RadioGroup rgP24 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P24);
                        int selOpt = rgP24.getCheckedRadioButtonId();
                        if(selOpt != R.id.survey_radio_M2_P24_option1){
                            String num = ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes)).getText().toString();
                            if(num != null && !num.isEmpty()){
                                numTotalViajes = Integer.parseInt(num);
                            }
                        }

                        if(numViajesMismaRuta>numTotalViajes){
                            etP25.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            Toast toast = Toast.makeText(activity, "El número de viajes en la misma ruta (P25) no puede ser superior al número total de viajes (P24)",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else{
                            etP25.setBackgroundColor(Color.WHITE);
                        }
                        //Change question text color as answered
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P25)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    } else {
                        //Change question text color as answered
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P25)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                    }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });




        //P26
        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P26);
        rgP26.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP26_num = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P26_num);
                    LinearLayout llP27 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P27);
                    EditText etP27 = (EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas);
                    int selectP19 = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19)).getCheckedRadioButtonId();
                    switch (checkedId){
                        case R.id.survey_radio_M2_P26_option1:
                            llP26_num.setVisibility(VISIBLE);
                            llP27.setVisibility(VISIBLE);
                            if(selectP19==R.id.survey_radio_M2_P19_option1){
                                llP27.setVisibility(GONE);
                                etP27.setText("1");
                            }
                            break;
                        case R.id.survey_radio_M2_P26_option2:
                            llP26_num.setVisibility(INVISIBLE);
                            llP27.setVisibility(GONE);
                            etP27.setText("");
                            break;
                        default:
                            llP26_num.setVisibility(INVISIBLE);
                            llP27.setVisibility(VISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P26)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P27
        EditText etP27 = (EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas);
        etP27.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                    EditText etP27 = (EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas);
                    String num = etP27.getText().toString();
                    if(num != null && !num.isEmpty()){
                        int numPers = Integer.parseInt(num);




                        //Error P27>P19
                        String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
                        int selectedOption = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19)).getCheckedRadioButtonId();
                        if(selectedOption == R.id.survey_radio_M2_P19_option1){
                            textP19 = "1";
                        }
                        String textP27 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas)).getText().toString();


                        boolean error = false;
                        if(textP19 != null && !textP19.isEmpty()){
                            if(textP27 != null && !textP27.isEmpty()){
                                int numP19 = Integer.parseInt(textP19);
                                int numP27 = Integer.parseInt(textP27);
                                if(numP27>numP19){
                                    Toast toast = Toast.makeText(activity, "El número de personas con bultos (P27) no puede ser mayor que el número total de personas del grupo (P19)", Toast.LENGTH_LONG);
                                    toast.show();
                                    etP27.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                    error = true;
                                } else {
                                    etP27.setBackgroundColor(Color.WHITE);
                                }
                            }
                        }

//                        if(!error) {
//                            //Warning P27>P26b
//                            int numBultos = 0;
//                            RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P26);
//                            int selOpt = rgP26.getCheckedRadioButtonId();
//                            if (selOpt == R.id.survey_radio_M2_P26_option1) {
//                                String numP = ((EditText) activity.findViewById(R.id.survey_edit_M2_P26b_numBultos)).getText().toString();
//                                if (numP != null && !numP.isEmpty()) {
//                                    numBultos = Integer.parseInt(numP);
//                                }
//                            }
//
//                            if (numPers > numBultos) {
//                                etP27.setBackgroundColor(activity.getResources().getColor(R.color.aenaOrange));
//                                Toast toast = Toast.makeText(activity, "El número de personas (P27) es superior al número total de bultos (P26). Verificar",
//                                        Toast.LENGTH_LONG);
//                                toast.show();
//                            } else {
//                                etP27.setBackgroundColor(Color.WHITE);
//                            }
//                        }


                        //Change question text color as answered
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P27)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    } else {
                        //Change question text color as answered
                        etP27.setBackgroundColor(Color.WHITE);
                        ((TextView) activity.findViewById(R.id.survey_text_M2_P27)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                    }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) { }
        });



        //P28
        RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P28);
        rgP28.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P28)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //P29
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P29);
        rgP29.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    TextView tvP29a = (TextView) activity.findViewById(R.id.survey_text_M2_P29a);
                    EditText etP29a = (EditText) activity.findViewById(R.id.survey_edit_M2_P29a);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P29_option1:
                            tvP29a.setVisibility(VISIBLE);
                            etP29a.setVisibility(VISIBLE);
                            break;
                        default:
                            tvP29a.setVisibility(INVISIBLE);
                            etP29a.setVisibility(INVISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P29)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        //P30
        RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P30);
        rgP30.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP30a = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P30a);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P30_option1:
                            llP30a.setVisibility(VISIBLE);
                            break;
                        default:
                            llP30a.setVisibility(GONE);
                            break;
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P30)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //Spinners P30b
        Spinner p30bitem1spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item1);
        ArrayAdapter<CharSequence> p30bitem1Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem1Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem1spinner.setAdapter(p30bitem1Adapter);

        Spinner p30bitem2spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item2);
        ArrayAdapter<CharSequence> p30bitem2Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem2Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem2spinner.setAdapter(p30bitem2Adapter);

        Spinner p30bitem3spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item3);
        ArrayAdapter<CharSequence> p30bitem3Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem3Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem3spinner.setAdapter(p30bitem3Adapter);

        Spinner p30bitem4spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item4);
        ArrayAdapter<CharSequence> p30bitem4Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem4Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem4spinner.setAdapter(p30bitem4Adapter);

        Spinner p30bitem5spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item5);
        ArrayAdapter<CharSequence> p30bitem5Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        p30bitem4Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p30bitem5spinner.setAdapter(p30bitem5Adapter);



        //P31
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P31);
        rgP31.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP33 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P33);
                    RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P32);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P31_option1:
                            rgP32.setVisibility(VISIBLE);;
                            break;
                        default:
                            rgP32.setVisibility(GONE);
                            break;
                    }
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P31)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    //Update P33
                    checkM2P33Visibility();
                }

            }
        });


        //P32
        RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P32);
        rgP32.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P32)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //Spinnner P33
        Spinner p33spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P33);
        ArrayAdapter<CharSequence> p33Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p33strings,
                        R.layout.selection_spinner_item);
        p33Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p33spinner.setAdapter(p33Adapter);

        p33spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout llp33others = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P33_others);

                TextView tvSelected = (TextView) view;
                String[] possibleValues = activity.getResources().getStringArray(R.array.p33strings);
                if(tvSelected.getText().toString().contentEquals(possibleValues[possibleValues.length-1])){
                    llp33others.setVisibility(VISIBLE);
                } else {
                    llp33others.setVisibility(GONE);
                }
                if(possibleValues!=null && !tvSelected.getText().toString().contentEquals(possibleValues[0])){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P33)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //P34
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P34);
        rgP34.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P34)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //P35
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P35);
        rgP35.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P35)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //P36
        RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P36);
        rgP36.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P36)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });



    }

    private void checkValidTimeM2P12() {
        String textHoraLlegada = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).getText().toString();
        String textMinLlegada = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos)).getText().toString();

        Calendar llegadaTime = Calendar.getInstance();
        if(textHoraLlegada!= null && !textHoraLlegada.isEmpty()){
            if(textMinLlegada!= null && !textMinLlegada.isEmpty()){

                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(textHoraLlegada));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(textMinLlegada));
                llegadaTime.set(Calendar.SECOND, 0);
            } else {
                ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos))
                        .setBackgroundColor(Color.WHITE);
                return;
            }
        } else {
            ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora))
                    .setBackgroundColor(Color.WHITE);
            return;
        }

        Date fechaEncuesta = Calendar.getInstance().getTime();
        SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
        String dateText = ((EditText) activity.findViewById(R.id.survey_edit_fecha)).getText().toString();
        String timeText = ((EditText) activity.findViewById(R.id.survey_edit_hora)).getText().toString();
        try {
            fechaEncuesta = sdfDate.parse(dateText +" "+timeText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(llegadaTime.getTime().getTime()>fechaEncuesta.getTime()){
            ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora))
                    .setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
            ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos))
                    .setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
            Toast toast = Toast.makeText(activity, "La hora de llegada no puede ser posterior a la hora de realización de la encuesta",
                    Toast.LENGTH_LONG);
            toast.show();
        } else {
            ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora))
                    .setBackgroundColor(Color.WHITE);
            ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos))
                    .setBackgroundColor(Color.WHITE);
        }

    }


    private void setM2P2areaSpinner(String selectedOption) {
        int resourceToLoad = dm.getAreaStringsResourceId(selectedOption);
        Spinner p2spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P2_area);
        if(resourceToLoad>0) {
            ArrayAdapter<CharSequence> p2Adapter =
                    ArrayAdapter.createFromResource(activity,
                            resourceToLoad,
                            R.layout.selection_spinner_item);
            p2Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
            p2spinner.setAdapter(p2Adapter);
        } else {
            p2spinner.setAdapter(null);
        }

        return;
    }



    private void loadM2P7Fields() {

        AutoCompleteTextView p7provinciaAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P7_prov);
        String[] provincias = dm.getStringsArrayFor(DictionaryManager.PROVINCIA_PROC);
        ArrayAdapter<String> p7provinciaadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, provincias);
        p7provinciaAutoComplete.setAdapter(p7provinciaadapter);

        p7provinciaAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProv = ((TextView) view).getText().toString();
                if(selectedProv!=null && !selectedProv.isEmpty()) {
                    String codProvText = dm.getAssociatedCodeFor(selectedProv, DictionaryManager.PROVINCIA_PROC);
                    if (codProvText != null) {
                        AutoCompleteTextView p7localidadAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P7_loc);
                        String[] localidades = dm.getLocalidadesProcFor(codProvText);
                        if (codProvText.contentEquals("99")) {
                            p7localidadAutoComplete.setText(localidades[0]);

                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_provLiteral)).setVisibility(View.VISIBLE);
                            ((EditText) activity.findViewById(R.id.survey_edit_M2_P7_prov_lit)).requestFocus();
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_locLiteral)).setVisibility(View.VISIBLE);
                        } else {
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_provLiteral)).setVisibility(View.GONE);
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_locLiteral)).setVisibility(View.GONE);

                            ArrayAdapter<String> p7localidadadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, localidades);
                            p7localidadAutoComplete.setAdapter(p7localidadadapter);

                            p7localidadAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String selectedLoc = ((TextView) view).getText().toString();
                                    if (selectedLoc != null && !selectedLoc.isEmpty()) {
                                        String codLocText = dm.getAssociatedCodeFor(selectedLoc, DictionaryManager.LOCALIDAD_PROC);
                                        if (codLocText != null && codLocText.contentEquals("99999")) {
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_locLiteral)).setVisibility(View.VISIBLE);
                                            ((EditText) activity.findViewById(R.id.survey_edit_M2_P7_loc_lit)).requestFocus();
                                        } else {
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_locLiteral)).setVisibility(View.GONE);
                                        }
                                    }
                                    //Update P8 question text including selected Localidad
                                    String P8text = activity.getResources().getString(R.string.survey_text_M2_P8).toString();
                                    String P8textFormated = String.format(P8text, selectedLoc);
                                    TextView tvP8 = (TextView) activity.findViewById(R.id.survey_text_M2_P8);
                                    tvP8.setText(P8textFormated);
                                }
                            });
                        }
                    }
                }
            }
        });


    }

    private void checkM2P33Visibility(){
        LinearLayout llP33 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P33);
        llP33.setVisibility(VISIBLE);

        String selection = (String) ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P17)).getSelectedItem();
        String code = dm.getAssociatedCodeFor(selection,DictionaryManager.TRAVEL_REASON);
        int optionValue = 0;
        if(code != null && !code.isEmpty()) {
            // Not business passengers
            optionValue = Integer.parseInt(code);

        }

        int selOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P31)).getCheckedRadioButtonId();

        if (optionValue > 200 || (selOpt!=R.id.survey_radio_M2_P31_option1 && selOpt >0)) {
            llP33.setVisibility(GONE);
        }

    }


    private boolean satisfyValidationModel1() {


        //P1 Nacionalidad != null
        AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P1);
        String p1Text = actvP1.getText().toString();
        if(p1Text==null || p1Text.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar el pais de nacionalidad (P1)", Toast.LENGTH_LONG);
            toast.show();
            actvP1.requestFocus();
            return false;
        }

        //P2 Pais de residencia != null
        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2);
        String p2Text = actvP2.getText().toString();
        if(p2Text==null || p2Text.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar el pais de residencia (P2)", Toast.LENGTH_LONG);
            toast.show();
            actvP2.requestFocus();
            return false;
        }


        //P2Localidad != null
//        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
//        p2Text = actvP2.getText().toString();
        String codCountry = dm.getAssociatedCodeFor(p2Text, DictionaryManager.COUNTRY);
        if(codCountry!=null && codCountry.contentEquals("724")){
            AutoCompleteTextView actvP2_loc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_loc);
            String codLoc = dm.getAssociatedCodeFor(actvP2_loc.getText().toString(), DictionaryManager.LOCALIDAD);
            if(codLoc == null || codLoc.isEmpty()) {
                Toast toast = Toast.makeText(activity, "Se debe indicar la LOCALIDAD de RESIDENCIA del pasajero (P2)", Toast.LENGTH_LONG);
                toast.show();
                actvP2_loc.requestFocus();
                return false;
            }
        }

        //P2Area != null
//        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
//        p2Text = actvP2.getText().toString();
//        String codCountry = dm.getAssociatedCodeFor(p2Text, DictionaryManager.COUNTRY);
        String[] areasStrings = dm.getAreasStringFor(p2Text);
        if(areasStrings!=null && areasStrings.length != 0){
            Spinner spP2_area = (Spinner) activity.findViewById(R.id.survey_spinner_P2_area);
            String codArea = dm.getAssociatedCodeForArea(spP2_area.getSelectedItem().toString(), codCountry);
            if(codArea == null || codArea.isEmpty() || codArea.contentEquals("0")){
                Toast toast = Toast.makeText(activity, "Se debe indicar el área o región de residencia del pasajero (P2)", Toast.LENGTH_LONG);
                toast.show();
                spP2_area.requestFocus();
                return false;
            }
        }



        //P3 != null
        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
        int checkedId = rgP3.getCheckedRadioButtonId();
        if(checkedId<=0){
            Toast toast = Toast.makeText(activity, "Se debe indicar desde donde se ha ACCEDIDO al aeropuerto (P3)", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        //P3 localidad != null
        if(checkedId==R.id.survey_radio_P3_option2){
            String textP7Localidad = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_loc))
                    .getText().toString();
            String codLocalidad = dm.getAssociatedCodeFor(textP7Localidad, DictionaryManager.LOCALIDAD_PROC);
            if(codLocalidad== null ||codLocalidad.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar la LOCALIDAD de donde PROVIENE el pasajero (P3)", Toast.LENGTH_LONG);
                toast.show();
                ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_loc)).requestFocus();
                return false;
            }
        }

        //P3 aeropuerto != null
        if(checkedId==R.id.survey_radio_P3_option3){
            String textP3Aerop = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport))
                    .getText().toString();
            String codAerop = dm.getAssociatedCodeFor(textP3Aerop, DictionaryManager.AIRPORT);
            if(codAerop== null ||codAerop.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el AEROPUERTO de donde PROVIENE el pasajero (P3)", Toast.LENGTH_LONG);
                toast.show();
                ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport)).requestFocus();
                return false;
            }

            //P3a aeropuerto origen != aeropuerto encuesta
            String selCode = codAerop;
            if (selCode != null && selCode.contentEquals(airportCode)) {
                Toast toast = Toast.makeText(activity, "El aeropuerto de origen no puede ser igual que el actual (P3a)", Toast.LENGTH_LONG);
                toast.show();
                ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport)).requestFocus();
                return false;
            }
        }




        if(checkedId==R.id.survey_radio_P3_option2  || checkedId==R.id.survey_radio_P3_option1){

            int checkId=0;

            if(checkedId==R.id.survey_radio_P3_option2) {
                //P4
                checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P4)).getCheckedRadioButtonId();
                if (checkId <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE ALOJAMIENTO utilizado durante la estancia (P4)", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            }

            //P5
            checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P5)).getCheckedRadioButtonId();
            if(checkId<=0){
                Toast toast = Toast.makeText(activity, "Se debe indicar el ÚLTIMO MODO DE TRANSPORTE utilizado para llegar al aeropuerto (P5)", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }

            if(checkId==R.id.survey_radio_P5_option3 || checkId==R.id.survey_radio_P5_option4){

                //P6
                checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P6)).getCheckedRadioButtonId();
                if(checkId<=0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar DONDE se ha dejado el vehículo (P6)", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }

                if(checkId==R.id.survey_radio_P5_option6){
                    String textP6b = ((EditText) activity.findViewById(R.id.survey_edit_P6b)).getText().toString();
                    if(textP6b == null || textP6b.isEmpty()){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el POR QUÉ se ha dejado el vehículo en un parking fuera (P6b)", Toast.LENGTH_LONG);
                        toast.show();
                        ((EditText) activity.findViewById(R.id.survey_edit_P6b)).requestFocus();
                        return false;
                    }
                }
            }


            //P7
            EditText etP7 = (EditText) activity.findViewById(R.id.survey_edit_P7);
            String textP7 = etP7.getText().toString();
            if(textP7==null || textP7.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el número de ACOMPAÑANTES (P7)", Toast.LENGTH_LONG);
                toast.show();
                etP7.requestFocus();
                return false;
            }


        }


        //P8 Hora llegada != null
        String P8hora = ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).getText().toString();
        String P8min = ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos)).getText().toString();


        if(P8hora== null || P8hora.isEmpty() || P8min== null || P8min.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar la HORA completa de LLEGADA al aeropuerto (P8)", Toast.LENGTH_LONG);
            toast.show();
            ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).requestFocus();
            return false;
        }

        String textHora = P8hora;
        if(textHora != null && !textHora.isEmpty()){
            int hora = Integer.parseInt(textHora);
            if(hora<0 || hora>23){
                Toast toast = Toast.makeText(activity, "El campo hora (P8) debe tener un valor comprendido entre 00 y 23",
                        Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).requestFocus();
                return false;
            }
        }

        String textMin = P8min;
        if(textMin != null && !textMin.isEmpty()){
            int min = Integer.parseInt(textMin);
            if(min<0 || min>59){
                Toast toast = Toast.makeText(activity, "El campo minutos (P8) debe tener un valor comprendido entre 00 y 59",
                        Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos)).requestFocus();
                return false;
            }
        }

        Calendar llegadaTime = Calendar.getInstance();
        String textHoraLlegada = P8hora;
        String textMinLlegada = P8min;
        if(textHoraLlegada!= null && !textHoraLlegada.isEmpty()){
            if(textMinLlegada!= null && !textMinLlegada.isEmpty()){

                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(textHoraLlegada));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(textMinLlegada));
                llegadaTime.set(Calendar.SECOND, 0);

                Date fechaEncuesta = Calendar.getInstance().getTime();
                SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
                String dateText = ((EditText) activity.findViewById(R.id.survey_edit_fecha)).getText().toString();
                String timeText = ((EditText) activity.findViewById(R.id.survey_edit_hora)).getText().toString();
                try {
                    fechaEncuesta = sdfDate.parse(dateText +" "+timeText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(llegadaTime.getTime().getTime()>fechaEncuesta.getTime()){
                    Toast toast = Toast.makeText(activity, "La hora de llegada (P8) no puede ser posterior a la hora de realización de la encuesta",
                            Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).requestFocus();
                    return false;
                }
            }
        }




        //P9 Aeropuerto destino != null

        AutoCompleteTextView actvAeropuertoDestino = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
        String selectedAirport = actvAeropuertoDestino.getText().toString();

        if(selectedAirport== null || selectedAirport.isEmpty()){
            Toast toast = Toast.makeText(activity, "El aeropuerto de destino (P9) debe estar cumplimentado", Toast.LENGTH_LONG);
            toast.show();
            actvAeropuertoDestino.requestFocus();
            return false;
        }


        if (selectedAirport != null) {
            String selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
            if (selCode != null && selCode.contentEquals(airportCode)) {
                Toast toast = Toast.makeText(activity, "El aeropuerto de destino no puede ser igual que el actual (P9)", Toast.LENGTH_LONG);
                toast.show();
                actvAeropuertoDestino.requestFocus();
                return false;
            }
        }


        //P10 Compañia y número de vuelo != null
        AutoCompleteTextView actvCompany = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P10_company);
        String selectedCompany = actvCompany.getText().toString();
        if(selectedCompany== null || selectedCompany.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo (P10)", Toast.LENGTH_LONG);
            toast.show();
            actvCompany.requestFocus();
            return false;
        }

        EditText etFlightNum = (EditText) activity.findViewById(R.id.survey_edit_P10);
        String number = etFlightNum.getText().toString();
        if(number==null || number.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo (P10)", Toast.LENGTH_LONG);
            toast.show();
            etFlightNum.requestFocus();
            return false;
        }


        //P11
        RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P11);
        int selId = rgP11.getCheckedRadioButtonId();
        if(selId<=0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si se va a hacer TRANSBORDO O FINALIZA el viaje (P11)", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }


        if(selId == R.id.survey_radio_P11_option2){
            //P12

            String textP12 = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P12)).getText().toString();
            String code =  dm.getAssociatedCodeFor(textP12, DictionaryManager.COMPANY_LONG);
            if(code == null || code.isEmpty() || code.contentEquals("0")){
                Toast toast = Toast.makeText(activity, "Se debe indicar la COMPAÑIA con la que continua el viaje (P12)", Toast.LENGTH_LONG);
                toast.show();
                ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P12)).requestFocus();
                return false;
            }

            String textP13 = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13)).getText().toString();
            code =  dm.getAssociatedCodeFor(textP13, DictionaryManager.AIRPORT_LONG);
            if(code == null || code.isEmpty() || code.contentEquals("0")){
                Toast toast = Toast.makeText(activity, "Se debe indicar el AEROPUERTO de FINALIZACIÓN del viaje (P13)", Toast.LENGTH_LONG);
                toast.show();
                ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13)).requestFocus();
                return false;
            }


        }


        //P13
        AutoCompleteTextView actvAeropuertoFinal = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13);
        selectedAirport = actvAeropuertoFinal.getText().toString();
        if (selectedAirport != null) {
            String selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
            if (selCode != null && selCode.contentEquals(airportCode)) {
                Toast toast = Toast.makeText(activity, "El aeropuerto de finalización del viaje no puede ser igual que el actual (P13)", Toast.LENGTH_LONG);
                toast.show();
                actvAeropuertoFinal.requestFocus();
                return false;
            }
        }


        //P14 Motivo viaje != null
        Spinner sP14 = (Spinner) activity.findViewById(R.id.survey_spinner_P14);
        String code = dm.getAssociatedCodeFor((String) sP14.getSelectedItem(), DictionaryManager.TRAVEL_REASON);

        if(code==null || code.isEmpty() || code.contentEquals("0")){
            Toast toast = Toast.makeText(activity, "Se debe indicar el PROPOSITO PRINCIPAL del viaje (P14)", Toast.LENGTH_LONG);
            toast.show();
            sP14.requestFocus();
            return false;
        }



        //P15 Viaje IDA o VUELTA != null
        RadioGroup rgP15 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P15);
        int selOpt = rgP15.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si el viaje es de IDA o VUELTA (P15)", Toast.LENGTH_LONG);
            toast.show();
            rgP15.requestFocus();
            return false;
        }

        //P15b Num dias
        EditText etP15b = (EditText) activity.findViewById(R.id.survey_edit_P15b);
        String textP15b = etP15b.getText().toString();
        if(textP15b==null || textP15b.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE DÍAS de duración del viaje (P15a)", Toast.LENGTH_LONG);
            toast.show();
            etP15b.requestFocus();
            return false;
        }


        //P16 Num personas != null
        RadioGroup rgP16 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16);
        selOpt = rgP16.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo (P16)", Toast.LENGTH_LONG);
            toast.show();
            rgP16.requestFocus();
            return false;
        }

        if(selOpt==R.id.survey_radio_P16_option3){
            //P16num != null
            String textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();
            if(textP16==null || textP16.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo (P16)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).requestFocus();
                return false;
            }

            //P17
            String textP17 = ((EditText) activity.findViewById(R.id.survey_edit_P17)).getText().toString();
            if(textP17==null || textP17.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE MENORES de 15 años (P17)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P17)).requestFocus();
                return false;
            }

            //P18 != null
            int selecOpt= ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P18)).getCheckedRadioButtonId();
            if(selecOpt<=0){
                Toast toast = Toast.makeText(activity, "Se debe indicar la RELACIÓN entre los compnentes del grupo (P18)", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }

        }



        // Num P17 < P16
        String textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();
        String textP17 = ((EditText) activity.findViewById(R.id.survey_edit_P17)).getText().toString();

        if(textP16 != null && !textP16.isEmpty()){
            if(textP17 != null && !textP17.isEmpty()){
                int numP16 = Integer.parseInt(textP16);
                int numP17 = Integer.parseInt(textP17);
                if(numP17>numP16){
                    Toast toast = Toast.makeText(activity, "El número de menores (P17) no puede ser mayor que el número total de personas del grupo (P16)", Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_P17)).requestFocus();
                    return false;
                }
            }
        }


        //P19 NumDias != null
        String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_P19_numDias)).getText().toString();
        if(textP19 == null || textP19.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el tiempo que hace que se RESERVÓ el billete (P19)", Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_P19_numDias)).requestFocus();
                    return false;
        }


        //P20 != null
        RadioGroup rgP20 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P20);
        selOpt = rgP20.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE TARIFA del billete (P20)", Toast.LENGTH_LONG);
            toast.show();
            rgP20.requestFocus();
            return false;
        }


        //P21 Num viajes avion != null
        RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P21);
        selOpt = rgP21.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión (P21)", Toast.LENGTH_LONG);
            toast.show();
            rgP21.requestFocus();
            return false;
        }

        if(selOpt==R.id.survey_radio_P21_option2){
            String textP21 = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes)).getText().toString();
            if(textP21==null | textP21.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión (P21)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes)).requestFocus();
                return false;
            }
        }

        //P22
        selOpt = rgP21.getCheckedRadioButtonId();
        if(selOpt==R.id.survey_radio_P21_option2){
            String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).getText().toString();
            if(textP22 == null || textP22.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar cuantas veces se ha realizado la MISMA RUTA COMPLETA (P22)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).requestFocus();
                return false;
            }
        }


        //P22 <= P21
        EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_P22_numViajes);
        String numViajes = etP22.getText().toString();
        if(numViajes != null && !numViajes.isEmpty()){
            int numViajesMismaRuta = Integer.parseInt(numViajes);

            int numTotalViajes = 0;
            selOpt = rgP21.getCheckedRadioButtonId();
            if(selOpt != R.id.survey_radio_P21_option1){
                String num = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes)).getText().toString();
                if(num != null && !num.isEmpty()){
                    numTotalViajes = Integer.parseInt(num);
                }
            }

            if(numViajesMismaRuta>numTotalViajes){
                Toast toast = Toast.makeText(activity, "El número de viajes en la misma ruta (P22) no puede ser superior al número total de viajes (P21)",
                        Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).requestFocus();
                return false;
            }
        }


        //P23 Facturación & Nº bultos != null
        RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P23);
        int selectedOption = rgP23.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si se ha FACTURADO equipaje (P23)", Toast.LENGTH_LONG);
            toast.show();
            rgP23.requestFocus();
            return false;

        } else {
            if(selectedOption== R.id.survey_radio_P23_option1){
                EditText etP23num =  (EditText) activity.findViewById(R.id.survey_edit_P23b_numBultos);
                String numBultos = etP23num.getText().toString();
                if(numBultos==null || numBultos.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE BULTOS (P23)", Toast.LENGTH_LONG);
                    toast.show();
                    etP23num.requestFocus();
                    return false;
                }



                //P24 != null
                if(((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16)).getCheckedRadioButtonId()==R.id.survey_radio_P16_option3) {
                    String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas)).getText().toString();
                    if (textP24 == null || textP24.isEmpty()) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar a cuantas PERSONAS pertenecen los BULTOS (P24)", Toast.LENGTH_LONG);
                        toast.show();
                        ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas)).requestFocus();
                        return false;
                    }
                }
            }
        }



        // Num P16 < P24
//      String textP16 = ((EditText) findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();

        selectedOption = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16)).getCheckedRadioButtonId();
        if(selectedOption == R.id.survey_radio_P16_option1){
            textP16 = "1";
        }
        String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas)).getText().toString();

        if(textP16 != null && !textP16.isEmpty()){
            if(textP24 != null && !textP24.isEmpty()){
                int numP16 = Integer.parseInt(textP16);
                int numP24 = Integer.parseInt(textP24);
                if(numP24>numP16){
                    Toast toast = Toast.makeText(activity, "El número de personas con bultos (P24) no puede ser mayor que el número total de personas del grupo (P16)", Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas)).requestFocus();
                    return false;
                }
            }
        }

        //P25 tarjeta embarque != null
        RadioGroup rgP25 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P25);
        selOpt = rgP25.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar donde se ha obtenido la TARJETA DE EMBARQUE (P25)", Toast.LENGTH_LONG);
            toast.show();
            rgP25.requestFocus();
            return false;
        }

        //P26-P28
        if(airportCode!=null && !airportCode.contentEquals("OVD") && !airportCode.contentEquals("BIO")){
            //P26 Mismo trayecto ave != null

            rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
            int selectedOptionP3 =rgP3.getCheckedRadioButtonId();

            rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P11);
            int selectedOptionP11 =rgP11.getCheckedRadioButtonId();

            AutoCompleteTextView actvP9 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
            String textP9 = actvP9.getText().toString();
            String selectedAirportCode = dm.getAssociatedCodeFor(textP9, DictionaryManager.AIRPORT);

            LinearLayout llP26_28 = (LinearLayout) activity.findViewById(R.id.survey_block_P26_P28);
            llP26_28.setVisibility(GONE);
            if( (selectedOptionP3<0) || (selectedOptionP3!=R.id.survey_radio_P3_option3)){
                if( (selectedOptionP11<0) || (selectedOptionP11==R.id.survey_radio_P11_option1)){
                    if((selectedAirportCode == null) || (selectedAirportCode.contentEquals("MAD")) ){



                        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26);
                        selOpt = rgP26.getCheckedRadioButtonId();
                        if(selOpt <= 0){
                            Toast toast = Toast.makeText(activity, "Se debe indicar si ha utilizado AVE en algun ocasión (P26)", Toast.LENGTH_LONG);
                            toast.show();
                            rgP26.requestFocus();
                            return false;
                        }


                        String textP27 = (String) ((Spinner) activity.findViewById(R.id.survey_spinner_P27)).getSelectedItem();

                        if(textP27==null || textP27.isEmpty() || textP27.contentEquals("...")){
                            Toast toast = Toast.makeText(activity, "Se debe indicar el MOTIVO por el que se ha elegido el avión (P27)", Toast.LENGTH_LONG);
                            toast.show();
                            ((Spinner) activity.findViewById(R.id.survey_spinner_P27)).requestFocus();
                            return false;
                        }


                        //P28 Opcion transporte != null
                        RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P28);
                        selOpt = rgP28.getCheckedRadioButtonId();
                        if(selOpt <= 0){
                            Toast toast = Toast.makeText(activity, "Se debe indicar qeu MEDIO DE TRANSPORTE se utilizaría (P28)", Toast.LENGTH_LONG);
                            toast.show();
                            rgP28.requestFocus();
                            return false;
                        }

                    }
                }
            }





        }


        //P29 Consumo restauración != null
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P29);
        selOpt = rgP29.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si ha CONSUMIDO algún producto en los servicios de restauración (P29)", Toast.LENGTH_LONG);
            toast.show();
            rgP29.requestFocus();
            return false;
        }

        if(selOpt==R.id.survey_radio_P29_option1){
            String textP29a = ((EditText) activity.findViewById(R.id.survey_edit_P29a)).getText().toString();
            if(textP29a==null || textP29a.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar cuanto se ha GASTADO en los servicios de RESTAURACIÓN (P29a)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P29a)).requestFocus();
                return false;
            }
        }

        //P30 Compra articulo != null
        RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P30);
        selOpt = rgP30.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si ha COMPRADO algún artículo en las tiendas (P30)", Toast.LENGTH_LONG);
            toast.show();
            rgP30.requestFocus();
            return false;
        }

        if(selOpt==R.id.survey_radio_P30_option1){
            String textP30a = ((EditText) activity.findViewById(R.id.survey_edit_P30a)).getText().toString();
            if(textP30a==null || textP30a.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar cuanto se ha GASTADO en las TIENDAS del aeropuerto (P30a)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_P30a)).requestFocus();
                return false;
            }
        }


        //P31 Situación laboral != null
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P31);
        selectedOption = rgP31.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar la SITUACIÓN LABORAL (P31)", Toast.LENGTH_LONG);
            toast.show();
            rgP31.requestFocus();
            return false;
        }

        if(selOpt==R.id.survey_radio_P31_option1){
            //P32
            int selectOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P32)).getCheckedRadioButtonId();
            if(selectOpt<=0){
                Toast toast = Toast.makeText(activity, "Se debe indicar el tipo de TRABAJO (P32)", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }


        //P33
        String selection = (String) ((Spinner) activity.findViewById(R.id.survey_spinner_P14)).getSelectedItem();
        code = dm.getAssociatedCodeFor(selection,DictionaryManager.TRAVEL_REASON);
        int optionValue = 0;
        if(code != null && !code.isEmpty()) {
            optionValue = Integer.parseInt(code);
        }

        selOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P31)).getCheckedRadioButtonId();

        if (optionValue < 200 && (selOpt==R.id.survey_radio_P31_option1 || selOpt <= 0)) {
            String selP33 =(String) ((Spinner) activity.findViewById(R.id.survey_spinner_P33)).getSelectedItem();
            code = dm.getAssociatedCodeFor(selP33, DictionaryManager.ACTIVITY);
            if(code == null || code.isEmpty() || code.contentEquals("0")){
                Toast toast = Toast.makeText(activity, "Se debe la ACTIVIDAD a la que se dedica la empresa (P33)", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }



        //P34 Estudios != null
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P34);
        selectedOption = rgP34.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NIVEL DE ESTUDIOS del entrevistado (P34)", Toast.LENGTH_LONG);
            toast.show();
            rgP34.requestFocus();
            return false;
        }

        //P35 Edad != null
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P35);
        selectedOption = rgP35.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar la EDAD del entrevistado (P35)", Toast.LENGTH_LONG);
            toast.show();
            rgP34.requestFocus();
            return false;
        }

        //P36 sexo != null
        RadioGroup rgSexo = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P36);
        if (rgSexo.getCheckedRadioButtonId() == -1) {
            Toast toast = Toast.makeText(activity, "Se debe seleccionar un SEXO del entrevistado (P36)", Toast.LENGTH_LONG);
            toast.show();
            rgSexo.requestFocus();
            return false;
        }


        //If all conditions are satisfied then return true
        return true;

    }

    private boolean satisfyValidationModel2(){

        //P1 Nacionalidad != null
        AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P1);
        String p1Text = actvP1.getText().toString();
        if(p1Text==null || p1Text.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar el pais de NACIONALIDAD (P1)", Toast.LENGTH_LONG);
            toast.show();
            actvP1.requestFocus();
            return false;
        }

        //P2 Pais de residencia != null
        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
        String p2Text = actvP2.getText().toString();
        if(p2Text==null || p2Text.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar el pais de RESIDENCIA (P2)", Toast.LENGTH_LONG);
            toast.show();
            actvP2.requestFocus();
            return false;
        }

        //P2Localidad != null
//        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
//        p2Text = actvP2.getText().toString();
        String codCountry = dm.getAssociatedCodeFor(p2Text, DictionaryManager.COUNTRY);
        if(codCountry!=null && codCountry.contentEquals("724")){
            AutoCompleteTextView actvP2_loc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2_loc);
            String codLoc = dm.getAssociatedCodeFor(actvP2_loc.getText().toString(), DictionaryManager.LOCALIDAD);
            if(codLoc == null || codLoc.isEmpty()) {
                Toast toast = Toast.makeText(activity, "Se debe indicar la LOCALIDAD de RESIDENCIA del pasajero (P2)", Toast.LENGTH_LONG);
                toast.show();
                actvP2_loc.requestFocus();
                return false;
            }
        }

        //P2Area != null
//        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
//        p2Text = actvP2.getText().toString();
//        String codCountry = dm.getAssociatedCodeFor(p2Text, DictionaryManager.COUNTRY);
        String[] areasStrings = dm.getAreasStringFor(p2Text);
        if(areasStrings!=null && areasStrings.length != 0){
            Spinner spP2_area = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P2_area);
            String codArea = dm.getAssociatedCodeForArea(spP2_area.getSelectedItem().toString(), codCountry);
            if(codArea == null || codArea.isEmpty() || codArea.contentEquals("0")){
                Toast toast = Toast.makeText(activity, "Se debe indicar el área o región de residencia del pasajero (P2)", Toast.LENGTH_LONG);
                toast.show();
                spP2_area.requestFocus();
                return false;
            }
        }



        int selOpt = -1;
        //P3 Conexion != null (solo aplica al cuestionario de PMI
        if(airportCode.contentEquals("PMI")) {
            RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P3);
            selOpt = rgP3.getCheckedRadioButtonId();
            if (selOpt <= 0) {
                Toast toast = Toast.makeText(activity, "Se debe indicar si el pasajero procede de CONEXIÓN (P3)", Toast.LENGTH_LONG);
                toast.show();
                rgP3.requestFocus();
                return false;
            }
        }




        //P4 Aeropuerto origen != aeropuerto encuesta
        AutoCompleteTextView actvAeropuertoOrigen = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P4);
        String selectedAirport = actvAeropuertoOrigen.getText().toString();
        if (selectedAirport != null) {
            String selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
            if (selCode != null && selCode.contentEquals(airportCode)) {
                Toast toast = Toast.makeText(activity, "El aeropuerto de origen no puede ser igual que el actual (P4)", Toast.LENGTH_LONG);
                toast.show();
                actvAeropuertoOrigen.requestFocus();
                return false;
            }
        }



        //P7 localidad != null
        RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P7);
        int checkedId = rgP7.getCheckedRadioButtonId();
        if(checkedId==R.id.survey_radio_M2_P7_option2_localidad){
            String textP7Localidad = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P7_loc))
                    .getText().toString();
            String codLocalidad = dm.getAssociatedCodeFor(textP7Localidad, DictionaryManager.LOCALIDAD_PROC);
            if(codLocalidad== null ||codLocalidad.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar la LOCALIDAD de donde PROVIENE el pasajero (P7)", Toast.LENGTH_LONG);
                toast.show();
                ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P7_loc)).requestFocus();
                return false;
            }
        }


        if(checkedId==R.id.survey_radio_M2_P7_option2_localidad || checkedId==R.id.survey_radio_M2_P7_option3 || checkedId==R.id.survey_radio_M2_P7_option1){

            int checkId=0;

            if(checkedId==R.id.survey_radio_M2_P7_option2_localidad || checkedId==R.id.survey_radio_M2_P7_option3) {
                //P8
                checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P8)).getCheckedRadioButtonId();
                if (checkId <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE ALOJAMIENTO utilizado durante la estancia (P8)", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            }

            //P9
            checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P9)).getCheckedRadioButtonId();
            if(checkId<=0){
                Toast toast = Toast.makeText(activity, "Se debe indicar el ÚLTIMO MODO DE TRANSPORTE utilizado para llegar al aeropuerto (P9)", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }

            if(checkId==R.id.survey_radio_M2_P9_option3 || checkId==R.id.survey_radio_M2_P9_option4){

                //P10
                checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P10)).getCheckedRadioButtonId();
                if(checkId<=0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar DONDE se ha dejado el vehículo (P10)", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            }


            //P11
            EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M2_P11);
            String textP11 = etP11.getText().toString();
            if(textP11==null || textP11.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el número de ACOMPAÑANTES (P11)", Toast.LENGTH_LONG);
                toast.show();
                etP11.requestFocus();
                return false;
            }


        }




        //P12 Hora llegada != null
        String P12hora = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).getText().toString();
        String P12min = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos)).getText().toString();


        if(P12hora== null || P12hora.isEmpty() || P12min== null || P12min.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar la HORA completa de LLEGADA al aeropuerto (P12)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).requestFocus();
                return false;
        }

        String textHora = P12hora;
        if(textHora != null && !textHora.isEmpty()){
            int hora = Integer.parseInt(textHora);
            if(hora<0 || hora>23){
                Toast toast = Toast.makeText(activity, "El campo hora (P12) debe tener un valor comprendido entre 00 y 23",
                        Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).requestFocus();
                return false;
            }
        }

        String textMin = P12min;
        if(textMin != null && !textMin.isEmpty()){
            int min = Integer.parseInt(textMin);
            if(min<0 || min>59){
                Toast toast = Toast.makeText(activity, "El campo minutos (P12) debe tener un valor comprendido entre 00 y 59",
                        Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos)).requestFocus();
                return false;
            }
        }

        Calendar llegadaTime = Calendar.getInstance();
        String textHoraLlegada = P12hora;
        String textMinLlegada = P12min;
        if(textHoraLlegada!= null && !textHoraLlegada.isEmpty()){
            if(textMinLlegada!= null && !textMinLlegada.isEmpty()){

                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(textHoraLlegada));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(textMinLlegada));
                llegadaTime.set(Calendar.SECOND, 0);

                Date fechaEncuesta = Calendar.getInstance().getTime();
                SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
                String dateText = ((EditText) activity.findViewById(R.id.survey_edit_fecha)).getText().toString();
                String timeText = ((EditText) activity.findViewById(R.id.survey_edit_hora)).getText().toString();
                try {
                    fechaEncuesta = sdfDate.parse(dateText +" "+timeText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(llegadaTime.getTime().getTime()>fechaEncuesta.getTime()){
                    Toast toast = Toast.makeText(activity, "La hora de llegada (P12) no puede ser posterior a la hora de realización de la encuesta",
                            Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).requestFocus();
                    return false;
                }
            }
        }





        //P13 not null & != aeropuerto encuesta
        AutoCompleteTextView actvAeropuertoDestino = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P13);
        selectedAirport = actvAeropuertoDestino.getText().toString();

        if(selectedAirport== null || selectedAirport.isEmpty()){
            Toast toast = Toast.makeText(activity, "El aeropuerto de destino (P13) debe estar cumplimentado", Toast.LENGTH_LONG);
            toast.show();
            actvAeropuertoDestino.requestFocus();
            return false;
        }

        if (selectedAirport != null) {
            String selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
            if (selCode != null && selCode.contentEquals(airportCode)) {
                Toast toast = Toast.makeText(activity, "El aeropuerto de destino no puede ser igual que el actual (P13)", Toast.LENGTH_LONG);
                toast.show();
                actvAeropuertoDestino.requestFocus();
                return false;
            }
        }




        //P14 Compañia y número de vuelo != null
        AutoCompleteTextView actvCompany = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P14_company);
        String selectedCompany = actvCompany.getText().toString();
        if(selectedCompany== null || selectedCompany.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo (P14)", Toast.LENGTH_LONG);
            toast.show();
            actvCompany.requestFocus();
            return false;
        }

        EditText etFlightNum = (EditText) activity.findViewById(R.id.survey_edit_M2_P14);
        String number = etFlightNum.getText().toString();
        if(number==null || number.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo (P14)", Toast.LENGTH_LONG);
            toast.show();
            etFlightNum.requestFocus();
            return false;
        }


        //P16 Aeropuerto finalización != aeropuerto encuesta
        AutoCompleteTextView actvAeropuertoFinal = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P16);
        selectedAirport = actvAeropuertoFinal.getText().toString();
        if (selectedAirport != null) {
            String selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
            if (selCode != null && selCode.contentEquals(airportCode)) {
                Toast toast = Toast.makeText(activity, "El aeropuerto de finalización del viaje no puede ser igual que el actual (P16)", Toast.LENGTH_LONG);
                toast.show();
                actvAeropuertoFinal.requestFocus();
                return false;
            }
        }



        //P17 Motivo viaje != null
        Spinner sP17 = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P17);
        if(sP17.getSelectedItem()==null){
            Toast toast = Toast.makeText(activity, "Se debe indicar el PROPOSITO PRINCIPAL del viaje (P17)", Toast.LENGTH_LONG);
            toast.show();
            sP17.requestFocus();
            return false;
        }

        //P18 Viaje IDA o VUELTA != null
        RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P18);
        selOpt = rgP18.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si el viaje es de IDA o VUELTA (P18)", Toast.LENGTH_LONG);
            toast.show();
            rgP18.requestFocus();
            return false;
        }

        //P18 Num dias
        EditText etP18 = (EditText) activity.findViewById(R.id.survey_edit_M2_P18b);
        String textP18 = etP18.getText().toString();
        if(textP18==null || textP18.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE DÍAS de duración del viaje (P18a)", Toast.LENGTH_LONG);
            toast.show();
            etP18.requestFocus();
            return false;
        }


        //P19 Num personas != null
        RadioGroup rgP19 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19);
        selOpt = rgP19.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo (P19)", Toast.LENGTH_LONG);
            toast.show();
            rgP19.requestFocus();
            return false;
        }

        if(selOpt==R.id.survey_radio_M2_P19_option3){
            String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
            if(textP19==null || textP19.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo (P19)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).requestFocus();
                return false;
            }
        }


        // Num P19 < P20
        String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
        String textP20 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P20)).getText().toString();


        if(textP19 != null && !textP19.isEmpty()){
            if(textP20 != null && !textP20.isEmpty()){
                int numP19 = Integer.parseInt(textP19);
                int numP20 = Integer.parseInt(textP20);
                if(numP20>numP19){
                    Toast toast = Toast.makeText(activity, "El número de menores (P20) no puede ser mayor que el número total de personas del grupo (P19)", Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_M2_P20)).requestFocus();
                    return false;
                }
            }
        }


        //P21 != null
//        RadioGroup rgP19 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19);
        selOpt = rgP19.getCheckedRadioButtonId();
        if(selOpt==R.id.survey_radio_M2_P19_option3){
            int selP21 = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P21)).getCheckedRadioButtonId();
            if(selP21<=0){
                Toast toast = Toast.makeText(activity, "Se debe indicar la RELACIÓN existente entre los componentes del grupo (P21)", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }

        //P22 Tiempo reserva != null
        String P22numDias = ((EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias)).getText().toString();
        if(P22numDias== null || P22numDias.isEmpty()){
            Toast toast = Toast.makeText(activity, "Se debe indicar el TIEMPO que hace desde la reserva del billete (P22)", Toast.LENGTH_LONG);
            toast.show();
            ((EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias)).requestFocus();
            return false;
        }


        //P23 Tipo tarifa != null
        RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P23);
        selOpt = rgP23.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE TARIFA del billete (P23)", Toast.LENGTH_LONG);
            toast.show();
            rgP23.requestFocus();
            return false;
        }

        //P24 Num viajes avion != null
        RadioGroup rgP24 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P24);
        selOpt = rgP24.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión (P24)", Toast.LENGTH_LONG);
            toast.show();
            rgP24.requestFocus();
            return false;
        }

        if(selOpt==R.id.survey_radio_M2_P24_option2){
            String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes)).getText().toString();
            if(textP24==null | textP24.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión (P24)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes)).requestFocus();
                return false;
            }
        }

        //P25
        selOpt = rgP24.getCheckedRadioButtonId();
        if(selOpt==R.id.survey_radio_M2_P24_option2){
            String textP25 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes)).getText().toString();
            if(textP25 == null || textP25.isEmpty()){
                Toast toast = Toast.makeText(activity, "Se debe indicar cuantas veces se ha realizado la MISMA RUTA COMPLETA (P25)", Toast.LENGTH_LONG);
                toast.show();
                ((EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes)).requestFocus();
                return false;
            }
        }

        //P25 <= P24
        EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes);
        String numViajes = etP25.getText().toString();
        if(numViajes != null && !numViajes.isEmpty()){
            int numViajesMismaRuta = Integer.parseInt(numViajes);

            int numTotalViajes = 0;
            selOpt = rgP24.getCheckedRadioButtonId();
            if(selOpt != R.id.survey_radio_M2_P24_option1){
                String num = ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes)).getText().toString();
                if(num != null && !num.isEmpty()){
                    numTotalViajes = Integer.parseInt(num);
                }
            }

            if(numViajesMismaRuta>numTotalViajes){
                Toast toast = Toast.makeText(activity, "El número de viajes en la misma ruta (P25) no puede ser superior al número total de viajes (P24)",
                        Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }


        //P26 Facturación & Nº bultos != null
        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P26);
        int selectedOption = rgP26.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si se ha FACTURADO equipaje (P26)", Toast.LENGTH_LONG);
            toast.show();
            rgP26.requestFocus();
            return false;

        } else {
            if(selectedOption== R.id.survey_radio_M2_P26_option1){
                EditText etP26num =  (EditText) activity.findViewById(R.id.survey_edit_M2_P26b_numBultos);
                String numBultos = etP26num.getText().toString();
                if(numBultos==null || numBultos.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE BULTOS (P26)", Toast.LENGTH_LONG);
                    toast.show();
                    etP26num.requestFocus();
                    return false;
                }
            }

        }


        // Num P19 < P27
        textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
        selectedOption = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19)).getCheckedRadioButtonId();
        if(selectedOption == R.id.survey_radio_M2_P19_option1){
            textP19 = "1";
        }
        String textP27 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas)).getText().toString();



        if(textP19 != null && !textP19.isEmpty()){
            if(textP27 != null && !textP27.isEmpty()){
                int numP19 = Integer.parseInt(textP19);
                int numP27 = Integer.parseInt(textP27);
                if(numP27>numP19){
                    Toast toast = Toast.makeText(activity, "El número de personas con bultos (P27) no puede ser mayor que el número total de personas del grupo (P19)", Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas)).requestFocus();
                    return false;
                }
            }
        }


//        //Num P26 < P27
//        String textP26 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P26b_numBultos)).getText().toString();
//
//        if(textP26 != null && !textP26.isEmpty()){
//            if(textP27 != null && !textP27.isEmpty()){
//                int numP26 = Integer.parseInt(textP26);
//                int numP27 = Integer.parseInt(textP27);
//                if(numP27>numP26){
//                    Toast toast = Toast.makeText(activity, "El número de personas con bultos (P27) no puede ser mayor que el número total de bultos (P26)", Toast.LENGTH_LONG);
//                    toast.show();
//                    ((EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas)).requestFocus();
//                    return false;
//                }
//            }
//        }


        //P28 tarjeta embarque != null
        RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P28);
        selOpt = rgP28.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar donde se ha obtenido la TARJETA DE EMBARQUE (P28)", Toast.LENGTH_LONG);
            toast.show();
            rgP28.requestFocus();
            return false;
        }


        //P29 Consumo restauración != null
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P29);
        selOpt = rgP29.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si ha CONSUMIDO algún producto en los servicios de restauración (P29)", Toast.LENGTH_LONG);
            toast.show();
            rgP29.requestFocus();
            return false;
        }

        //P30 Compra articulo != null
        RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P30);
        selOpt = rgP30.getCheckedRadioButtonId();
        if(selOpt <= 0){
            Toast toast = Toast.makeText(activity, "Se debe indicar si ha COMPRADO algún artículo en las tiendas (P30)", Toast.LENGTH_LONG);
            toast.show();
            rgP30.requestFocus();
            return false;
        }


        //P31 Situación laboral != null
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P31);
        selectedOption = rgP31.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar la SITUACIÓN LABORAL (P31)", Toast.LENGTH_LONG);
            toast.show();
            rgP31.requestFocus();
            return false;
        }

        //P34 Estudios != null
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P34);
        selectedOption = rgP34.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar el NIVEL DE ESTUDIOS del entrevistado (P34)", Toast.LENGTH_LONG);
            toast.show();
            rgP34.requestFocus();
            return false;
        }

        //P35 Edad != null
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P35);
        selectedOption = rgP35.getCheckedRadioButtonId();
        if(selectedOption<0){
            Toast toast = Toast.makeText(activity, "Se debe indicar la EDAD del entrevistado (P35)", Toast.LENGTH_LONG);
            toast.show();
            rgP34.requestFocus();
            return false;
        }

        //P36 sexo != null
        RadioGroup rgSexo = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P36);
        if (rgSexo.getCheckedRadioButtonId() == -1) {
            Toast toast = Toast.makeText(activity, "Se debe seleccionar un SEXO del entrevistado", Toast.LENGTH_LONG);
            toast.show();
            rgSexo.requestFocus();
            return false;
        }


        //If all conditions are satisfied then return true
        return true;
    }



    public Questionnaire fillQuest(Questionnaire quest, boolean throwError) throws Exception{
           try {
               switch (formModelCode) {
                   case FORM_MODEL1:
                       return fillQuestModel1(quest);
                   case FORM_MODEL2:
                       return fillQuestModel2(quest, throwError);
                   default:
                       return null;
               }
           } catch (Exception e){
               throw new Exception(e.toString());
           }
    }


    private Questionnaire fillQuestModel1(Questionnaire quest) {

        //P1
        AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P1);
        String countryText = actvP1.getText().toString();
        String codCountry = dm.getAssociatedCodeFor(countryText, DictionaryManager.COUNTRY);
        if(codCountry != null)
            quest.setCdpaisna(Integer.parseInt(codCountry));
        else
            quest.setCdpaisna(-1);

        //P1 otros literal
        if(codCountry!= null && codCountry.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_P1_otros)).getText().toString();
            if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                quest.setCdpaisna_lit(otrosLiteral);
            } else {
                quest.setCdpaisna_lit(null);
            }
        }

        //P2
        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2);
        countryText = actvP2.getText().toString();
        codCountry = dm.getAssociatedCodeFor(countryText, DictionaryManager.COUNTRY);
        if(codCountry != null)
            quest.setCdpaisre(Integer.parseInt(codCountry));
        else
            quest.setCdpaisre(-1);

        //P2 Localidad-Area
        if(codCountry != null) {
            String codLoc = null;
            if (codCountry.contentEquals("724")){
                AutoCompleteTextView actvP2_loc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_loc);
                codLoc = dm.getAssociatedCodeFor(actvP2_loc.getText().toString(), DictionaryManager.LOCALIDAD);
                if(codLoc == null || codLoc.isEmpty()){
                    codLoc = null;
                } else if(codLoc.contentEquals("99999")){
                    String locLit = ((EditText) activity.findViewById(R.id.survey_edit_P2_loc_lit)).getText().toString();
                    String provText = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_prov)).getText().toString();
                    String codProv = dm.getAssociatedCodeFor(provText, DictionaryManager.PROVINCIA);
                    if(codProv != null && !codProv.contentEquals("99")){
                        locLit = locLit + ", " + provText;
                    } else {
                        locLit = locLit +", " + ((EditText) activity.findViewById(R.id.survey_edit_P2_prov_lit)).getText().toString();;
                    }
                    quest.setCdlocado_lit(locLit);
                }
            }else{
                Spinner sP2_area = (Spinner) activity.findViewById(R.id.survey_spinner_P2_area);
                if(sP2_area.getSelectedItem() != null) {
                    String P2_area = sP2_area.getSelectedItem().toString();
                    if (P2_area != null && !P2_area.isEmpty()) {
                        codLoc = dm.getAssociatedCodeForArea(P2_area, codCountry);
                    }
                }
            }
            quest.setCdlocado(codLoc);
        }

        //P2 otros literal
        if(codCountry!= null && codCountry.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_P2_otros)).getText().toString();
            if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                quest.setCdpaisre_lit(otrosLiteral);
            } else{
                quest.setCdpaisre_lit(null);
            }

        }



        //P3
        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
        int checkedId = rgP3.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P3_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P3_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P3_option3:
                    selectedCode = 9;
                    break;
            }
            quest.setVien_re(selectedCode);
            if(selectedCode==2){
                String textP3Localidad = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_loc))
                        .getText().toString();
                String codLocalidad = dm.getAssociatedCodeFor(textP3Localidad, DictionaryManager.LOCALIDAD);
                quest.setCdlocaco(Integer.parseInt(codLocalidad));
            }
            if(selectedCode==9){
                String textP3Aeropuerto = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport))
                        .getText().toString();
                String codAeropuerto = dm.getAssociatedCodeFor(textP3Aeropuerto, DictionaryManager.AIRPORT);
                quest.setCdiaptoo(codAeropuerto);

                //Save P3 others literal
                if(codAeropuerto!= null && codAeropuerto.contentEquals("999")){
                    String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_P3_otros)).getText().toString();
                    if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                        quest.setCdiaptoo_lit(otrosLiteral);
                    } else {
                        quest.setCdiaptoo_lit(null);
                    }
                }
            }
        }


        //P4
        RadioGroup rgP4 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P4);
        checkedId = rgP4.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P4_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P4_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P4_option3:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_P4_option4:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_P4_option5:
                    selectedCode = 8;
                    break;
                case R.id.survey_radio_P4_option6:
                    selectedCode = 10;
                    break;
                case R.id.survey_radio_P4_option7:
                    selectedCode = 9;
                    break;
            }
            quest.setCdalojin(selectedCode);
            if(selectedCode==9){
                EditText etP4otros = (EditText) activity.findViewById(R.id.survey_edit_P4_otros);
                quest.setCdalojin_lit(etP4otros.getText().toString());
            }
        }


        //P5
        RadioGroup rgP5 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P5);
        checkedId = rgP5.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P5_option1:
                    selectedCode = 11;
                    break;
                case R.id.survey_radio_P5_option2:
                    selectedCode = 25;
                    break;
                case R.id.survey_radio_P5_option3:
                    selectedCode = 22;
                    break;
                case R.id.survey_radio_P5_option4:
                    selectedCode = 23;
                    break;
                case R.id.survey_radio_P5_option5:
                    selectedCode = 35;
                    break;
                case R.id.survey_radio_P5_option6:
                    selectedCode = 31;
                    break;
                case R.id.survey_radio_P5_option7:
                    selectedCode = 91;
                    break;
                case R.id.survey_radio_P5_optionCerc:
                    selectedCode = 42;
                    break;
            }
            quest.setUltimodo(selectedCode);
            if(selectedCode==91){
                EditText etP5otros = (EditText) activity.findViewById(R.id.survey_edit_P5_others);
                quest.setUltimodo_lit(etP5otros.getText().toString());
            }
        }


        //P6
        RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P6);
        checkedId = rgP6.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P6_option1:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_P6_option2:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_P6_option3:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P6_option4:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P6_option5:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_P6_option6:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_P6_option7:
                    selectedCode = 9;
                    break;
            }
            quest.setSitiopark(selectedCode);
            if(selectedCode==4){
                EditText etP6b = (EditText) activity.findViewById(R.id.survey_edit_P6b);
                quest.setPqfuera(etP6b.getText().toString());
            }
        }

        //P7
        String p7text = ((EditText) activity.findViewById(R.id.survey_edit_P7)).getText().toString();
        if(p7text!= null && !p7text.isEmpty()){
            quest.setAcomptes(Integer.parseInt(p7text));
        } else {
            quest.setAcomptes(-1);
        }

        //P8
        String P8hora = ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).getText().toString();
        String P8min = ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos)).getText().toString();


        if(P8hora!= null && !P8hora.isEmpty()){
            if(P8min!= null && !P8min.isEmpty()){
                Calendar llegadaTime = Calendar.getInstance();
                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(P8hora));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(P8min));
                llegadaTime.set(Calendar.SECOND, 0);
                quest.setHllega(llegadaTime.getTime());
            }
        }


        //P9
        AutoCompleteTextView actvP9 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
        String P9text = actvP9.getText().toString();
        String p9code = dm.getAssociatedCodeFor(P9text, DictionaryManager.AIRPORT);
        quest.setCdiaptod(p9code);

        //P9 otros literal
        if(p9code!= null && p9code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_P9_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptod_lit(otrosLiteral);
            } else{
                quest.setCdiaptod_lit(null);
            }

        }

        //P10
        AutoCompleteTextView actvP10 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P10_company);
        String P10text = actvP10.getText().toString();
        String p10code = dm.getAssociatedCodeFor(P10text, DictionaryManager.COMPANY);
        quest.setNumvuepa_comp(p10code);
        EditText p10number = (EditText) activity.findViewById(R.id.survey_edit_P10);
        quest.setNumvuepa(p10code+p10number.getText().toString());

        //P10 otros literal
        if(p10code!= null && p10code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_P10_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setNumvuepa_lit(otrosLiteral);
            } else {
                quest.setNumvuepa_lit(null);
            }
        }


        //P11
        RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P11);
        checkedId = rgP11.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P11_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P11_option2:
                    selectedCode = 2;
                    break;

            }
            quest.setCdterm(selectedCode);
        }


        //P12
        AutoCompleteTextView actvP12 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P12);
        String companyCode = dm.getAssociatedCodeFor(actvP12.getText().toString(), DictionaryManager.COMPANY_LONG);
        quest.setCdociaar(companyCode);

        //P12 otros literal
        if(companyCode!= null && companyCode.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_P12_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdociaar_lit(otrosLiteral);
            } else {
                quest.setCdociaar_lit(null);
            }

        }

        //P13
        AutoCompleteTextView actvP13 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13);
        String airportText = actvP13.getText().toString();
        String airportCode = dm.getAssociatedCodeFor(airportText, DictionaryManager.AIRPORT_LONG);
        quest.setCdiaptof(airportCode);

        //P13 otros literal
        if(airportCode!= null && airportCode.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_P13_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptof_lit(otrosLiteral);
            } else {
                quest.setCdiaptof_lit(null);
            }

        }

        //P14
        Spinner sP14 = (Spinner) activity.findViewById(R.id.survey_spinner_P14);
        if(sP14.getSelectedItem()!=null) {
            String codReason = dm.getAssociatedCodeFor(sP14.getSelectedItem().toString(), DictionaryManager.TRAVEL_REASON);
            if (codReason != null) {
                quest.setCdmviaje(Integer.parseInt(codReason));
            }
        } else {
            quest.setCdmviaje(-1);
        }



        //P15
        RadioGroup rgP15 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P15);
        checkedId = rgP15.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = 0;
            switch (checkedId) {
                case R.id.survey_radio_P15_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P15_option2:
                    selectedCode = 2;
                    break;

            }
            quest.setCdidavue(selectedCode);
        }

        //P15a
        EditText etP15a = (EditText) activity.findViewById(R.id.survey_edit_P15b);
        String textP15a = etP15a.getText().toString();
        if(textP15a!= null && !textP15a.isEmpty()){
            quest.setTaus(Integer.parseInt(textP15a));
        } else {
            quest.setTaus(-1);
        }


        //P16
        RadioGroup rgP16 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16);
        checkedId = rgP16.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P16_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P16_option3:
                    selectedCode = 2;
                    break;
            }
            if(selectedCode ==  1){
                quest.setNpers(1);
            } else {
                String textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas))
                        .getText().toString();
                if(textP16!= null && !textP16.isEmpty()) {
                    quest.setNpers(Integer.parseInt(textP16));
                } else {
                    quest.setNpers(-1);
                }
            }
        } else {
            String textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas))
                    .getText().toString();
            if(textP16!= null && !textP16.isEmpty()) {
                quest.setNpers(Integer.parseInt(textP16));
            } else {
                quest.setNpers(-1);
            }
        }


        //P17
        String textP17 = ((EditText) activity.findViewById(R.id.survey_edit_P17)).getText().toString();
        if(textP17!=null && !textP17.isEmpty()){
            quest.setNninos(Integer.parseInt(textP17));
        } else {
            quest.setNninos(-1);
        }




        //P18
        RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P18);
        checkedId = rgP18.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P18_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P18_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P18_option3:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_P18_option4:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_P18_option5:
                    selectedCode = 9;
                    break;

            }
            quest.setRelacion(selectedCode);
        }


        //P19
        String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_P19_numDias)).getText().toString();
        if(textP19 != null && !textP19.isEmpty()){
            quest.setCdtreser(Integer.parseInt(textP19));
        } else {
            quest.setCdtreser(-1);
        }


        //P20
        RadioGroup rgP20 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P20);
        checkedId = rgP20.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P20_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P20_option2:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_P20_option3:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_P18_option4:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_P18_option5:
                    selectedCode = 9;
                    break;
            }
            quest.setCdbillet(selectedCode);
        }


        //P21
        RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P21);
        checkedId = rgP21.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P21_option1:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_P21_option2:
                    selectedCode = 2;
                    break;

            }
            if(selectedCode ==  1){
                quest.setNviaje(0);
            } else {
                String textP21 = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes))
                        .getText().toString();
                if(textP21!= null && !textP21.isEmpty()) {
                    quest.setNviaje(Integer.parseInt(textP21));
                } else {
                    quest.setNviaje(-1);
                }
            }
        } else {
            String textP21 = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes))
                    .getText().toString();
            if(textP21!= null && !textP21.isEmpty()) {
                quest.setNviaje(Integer.parseInt(textP21));
            } else {
                quest.setNviaje(-1);
            }
        }


        //P22
        String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).getText().toString();
        if(textP22 != null && !textP22.isEmpty()){
            quest.setVol12mes(Integer.parseInt(textP22));
        } else {
            quest.setVol12mes(-1);
        }


        //P23
        RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P23);
        checkedId = rgP23.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P23_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P23_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setP44factu(selectedCode);
        }

        //P23a
        String textP23a = ((EditText) activity.findViewById(R.id.survey_edit_P23b_numBultos)).getText().toString();
        if(textP23a!= null && !textP23a.isEmpty()){
            quest.setBulgrupo(Integer.parseInt(textP23a));
        } else {
            quest.setBulgrupo(-1);
        }



        //P24
        String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas)).getText().toString();
        if(textP24!= null && !textP24.isEmpty()){
            quest.setNperbul(Integer.parseInt(textP24));
        } else {
            quest.setNperbul(-1);
        }

        //P25
        RadioGroup rgP25 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P25);
        checkedId = rgP25.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P25_option1:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_P25_option2:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P25_option3:
                    selectedCode = 2;
                    break;
            }
            quest.setChekinb(selectedCode);
        }



        //P26
        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26);
        checkedId = rgP26.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P26_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P26_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setUsoave(selectedCode);
        }


        //P27
        String textP27 = ((Spinner) activity.findViewById(R.id.survey_spinner_P27)).getSelectedItem().toString();
        String codMotivo = dm.getAssociatedCodeFor(textP27, DictionaryManager.FLIGHT_REASON);
        if(codMotivo!=null){
            quest.setMotivoavion2(Integer.parseInt(codMotivo));
            if(codMotivo.contentEquals("91")){
                String P27otros = ((EditText) activity.findViewById(R.id.survey_edit_P27_especificar)).getText().toString();
                quest.setMotivoavion2_lit(P27otros);
            }
        } else {
            quest.setMotivoavion2(-1);
        }




        //P28
        RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P28);
        checkedId = rgP28.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P28_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P28_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P28_option3:
                    selectedCode = 3;
                    break;
            }
            quest.setPrefiere(selectedCode);
        }


        //P29
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P29);
        checkedId = rgP29.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P29_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P29_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setConsume(selectedCode);

        }


        //P29a
        String textP29gasto = ((EditText) activity.findViewById(R.id.survey_edit_P29a)).getText().toString();
        if(textP29gasto != null && !textP29gasto.isEmpty()){
            quest.setGas_cons(Integer.parseInt(textP29gasto));
        } else {
            quest.setGas_cons(-1);
        }


        //P30
        RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P30);
        checkedId = rgP30.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = 0;
            switch (checkedId) {
                case R.id.survey_radio_P30_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P30_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setComprart(selectedCode);
        }

        //P30a
        String textP30a = ((EditText) activity.findViewById(R.id.survey_edit_P30a)).getText().toString();
        if(textP30a != null && !textP30a.isEmpty()){
            quest.setGas_com(Integer.parseInt(textP30a));
        } else {
            quest.setGas_com(-1);
        }

        //P30b
        String selItem1 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item1)).getSelectedItem().toString();
        String codItem1 = dm.getAssociatedCodeFor(selItem1, DictionaryManager.ITEM_TYPE);
        if(codItem1 != null && !codItem1.isEmpty()) {
            quest.setProd1(Integer.parseInt(codItem1));
        } else {
            quest.setProd1(-1);
        }

        String selItem2 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item2)).getSelectedItem().toString();
        String codItem2 = dm.getAssociatedCodeFor(selItem2, DictionaryManager.ITEM_TYPE);
        if(codItem2 != null && !codItem2.isEmpty()) {
            quest.setProd2(Integer.parseInt(codItem2));
        } else {
            quest.setProd2(-1);
        }

        String selItem3 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item3)).getSelectedItem().toString();
        String codItem3 = dm.getAssociatedCodeFor(selItem3, DictionaryManager.ITEM_TYPE);
        if(codItem3 != null && !codItem3.isEmpty()) {
            quest.setProd3(Integer.parseInt(codItem3));
        } else {
            quest.setProd3(-1);
        }

        String selItem4 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item4)).getSelectedItem().toString();
        String codItem4 = dm.getAssociatedCodeFor(selItem4, DictionaryManager.ITEM_TYPE);
        if(codItem4 != null && !codItem4.isEmpty()) {
            quest.setProd4(Integer.parseInt(codItem4));
        } else {
            quest.setProd4(-1);
        }

        String selItem5 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item5)).getSelectedItem().toString();
        String codItem5 = dm.getAssociatedCodeFor(selItem5, DictionaryManager.ITEM_TYPE);
        if(codItem5 != null && !codItem5.isEmpty()) {
            quest.setProd5(Integer.parseInt(codItem5));
        } else {
            quest.setProd5(-1);
        }


        //P31
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P31);
        checkedId = rgP31.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P31_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P31_option2:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_P31_option3:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_P31_option4:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P31_option5:
                    selectedCode = 6;
                    break;

            }
            quest.setCdslab(selectedCode);
        }


        //P32
        RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P32);
        checkedId = rgP32.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P32_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P32_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P32_option3:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_P32_option4:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_P32_option5:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_P32_option6:
                    selectedCode = 9;
                    break;
            }
            quest.setCdsprof(selectedCode);
        }


        //P33
        String textP33 = ((Spinner) activity.findViewById(R.id.survey_spinner_P33)).getSelectedItem().toString();
        String codActivity = dm.getAssociatedCodeFor(textP33, DictionaryManager.ACTIVITY);
        if(codActivity!= null){
            quest.setActiv05(Integer.parseInt(codActivity));

            if(codActivity.contentEquals("99")){
                String textP33otros = ((EditText) activity.findViewById(R.id.survey_edit_P33_otraActividad))
                        .getText().toString();
                quest.setActiv05_lit(textP33otros);
            }
        } else {
            quest.setActiv05(-1);
        }


        //P34
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P34);
        checkedId = rgP34.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P34_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P34_option2:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_P34_option3:
                    selectedCode = 5;
                    break;
            }
            quest.setEstudios(selectedCode);
        }


        //P35
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P35);
        checkedId = rgP35.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P35_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P35_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_P35_option3:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_P35_option4:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_P35_option5:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_P35_option6:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_P35_option7:
                    selectedCode = 7;
                    break;
                case R.id.survey_radio_P35_option8:
                    selectedCode = 8;
                    break;
                case R.id.survey_radio_P35_option9:
                    selectedCode = 9;
                    break;
                case R.id.survey_radio_P35_option10:
                    selectedCode = 10;
                    break;
                case R.id.survey_radio_P35_option11:
                    selectedCode = 11;
                    break;
                case R.id.survey_radio_P35_option12:
                    selectedCode = 12;
                    break;
            }
            quest.setCdedad(selectedCode);
        }


        //P36
        RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P36);
        checkedId = rgP36.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P36_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_P36_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdsexo(selectedCode);
        }


        return quest;

    }

    private Questionnaire fillQuestModel2(Questionnaire quest,boolean throwError) throws Exception {

        try{


        //P1
        AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P1);
        String countryText = actvP1.getText().toString();
        String codCountry = dm.getAssociatedCodeFor(countryText, DictionaryManager.COUNTRY);
        if(codCountry != null)
            quest.setCdpaisna(Integer.parseInt(codCountry));
        else
            quest.setCdpaisna(-1);

        //P1 otros literal
        if(codCountry!= null && codCountry.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P1_otros)).getText().toString();
            if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                quest.setCdpaisna_lit(otrosLiteral);
            } else {
                quest.setCdpaisna_lit(null);
            }
        }

        //P2
        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
        countryText = actvP2.getText().toString();
        codCountry = dm.getAssociatedCodeFor(countryText, DictionaryManager.COUNTRY);
        if(codCountry != null)
            quest.setCdpaisre(Integer.parseInt(codCountry));
        else
            quest.setCdpaisre(-1);

        //P2 Localidad-Area
        if(codCountry != null) {
            String codLoc = null;
            if (codCountry.contentEquals("724")){
                AutoCompleteTextView actvP2_loc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2_loc);
                codLoc = dm.getAssociatedCodeFor(actvP2_loc.getText().toString(), DictionaryManager.LOCALIDAD);
                if(codLoc == null || codLoc.isEmpty()) {
                    codLoc = null;
                } else if(codLoc.contentEquals("99999")){
                        String locLit = ((EditText) activity.findViewById(R.id.survey_edit_M2_P2_loc_lit)).getText().toString();
                        String provText = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2_prov)).getText().toString();
                        String codProv = dm.getAssociatedCodeFor(provText, DictionaryManager.PROVINCIA);
                        if(codProv != null && !codProv.contentEquals("99")){
                            locLit = locLit + ", " + provText;
                        } else {
                            locLit = locLit +", " + ((EditText) activity.findViewById(R.id.survey_edit_M2_P2_prov_lit)).getText().toString();;
                        }
                        quest.setCdlocado_lit(locLit);
                }
            }else{
                Spinner sP2_area = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P2_area);
                if(sP2_area.getSelectedItem() != null) {
                    String P2_area = sP2_area.getSelectedItem().toString();
                    if (P2_area != null && !P2_area.isEmpty()) {
                        codLoc = dm.getAssociatedCodeForArea(P2_area, codCountry);
                    }
                }
            }
            quest.setCdlocado(codLoc);
        }

        //P2 otros literal
        if(codCountry!= null && codCountry.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P2_otros)).getText().toString();
            if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                quest.setCdpaisre_lit(otrosLiteral);
            } else{
                quest.setCdpaisre_lit(null);
            }

        }


        //P3
        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P3);
        int checkedId = rgP3.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P3_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P3_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdcambio(selectedCode);
        }

        //P4
        AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P4);
        String P4text = actvP4.getText().toString();
        String p4code = dm.getAssociatedCodeFor(P4text, DictionaryManager.AIRPORT);
        quest.setCdiaptoo(p4code);

        //P4 otros literal
        if(p4code!= null && p4code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P4_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptoo_lit(otrosLiteral);
            } else{
                quest.setCdiaptoo_lit(null);
            }
        }


        //P5
        AutoCompleteTextView actvP5 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P5);
        String P5text = actvP5.getText().toString();
        String p5code = dm.getAssociatedCodeFor(P5text, DictionaryManager.COMPANY);

        quest.setCiaantes(p5code);

        //P5 otros literal
        if(p5code!= null && p5code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P5_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCiaantes_lit(otrosLiteral);
            } else {
                quest.setCiaantes_lit(null);
            }
        }


        //P6
        RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P6);
        checkedId = rgP6.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P6_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P6_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setConexfac(selectedCode);
        }



        //P7
        RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P7);
        checkedId = rgP7.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P7_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P7_option2_localidad:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M2_P7_option3:
                    selectedCode = 9;
                    break;
                case R.id.survey_radio_M2_P7_option4:
                    selectedCode = 99000;
                    break;
            }
            quest.setVien_re(selectedCode);
            if(selectedCode==2){
                String textP7Localidad = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P7_loc))
                        .getText().toString();
                String codLocalidad = dm.getAssociatedCodeFor(textP7Localidad, DictionaryManager.LOCALIDAD_PROC);
                if(codLocalidad== null ||codLocalidad.isEmpty()){
                    codLocalidad = "-1";
                } else if(codLocalidad.contentEquals("99999")){
                    String locLit = ((EditText) activity.findViewById(R.id.survey_edit_M2_P7_loc_lit)).getText().toString();
                    String provText = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P7_prov)).getText().toString();
                    String codProv = dm.getAssociatedCodeFor(provText, DictionaryManager.PROVINCIA_PROC);
                    if(codProv != null && !codProv.contentEquals("99")){
                        locLit = locLit + ", " + provText;
                    } else {
                        locLit = locLit +", " + ((EditText) activity.findViewById(R.id.survey_edit_M2_P7_prov_lit)).getText().toString();;
                    }
                    quest.setCdlocaco_lit(locLit);
                }
                quest.setCdlocaco(Integer.parseInt(codLocalidad));

            }
            if(selectedCode==9){
                String textP7Playa = ((EditText) activity.findViewById(R.id.survey_autoComplete_M2_P7_playa))
                        .getText().toString();
                quest.setCdiaptoo(textP7Playa);
            }
            if(selectedCode==99000){
                String textP7aeropuerto = ((EditText) activity.findViewById(R.id.survey_autoComplete_M2_P7_airport))
                        .getText().toString();
                String airportCode = dm.getAssociatedCodeFor(textP7aeropuerto, DictionaryManager.AIRPORT);
                quest.setCdiaptoo(airportCode);

                //Save P3 others literal
                if(airportCode!= null && airportCode.contentEquals("999")){
                    String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P7_otrosAeropuerto)).getText().toString();
                    if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                        quest.setCdiaptoo_lit(otrosLiteral);
                    } else {
                        quest.setCdiaptoo_lit(null);
                    }
                }
            }
        }


        //TODO read and save P3-P7




        //P8
        RadioGroup rgP8 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P8);
        checkedId = rgP8.getCheckedRadioButtonId();
        if (checkedId  > 0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P8_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P8_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M2_P8_option3:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M2_P8_option4:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M2_P8_option5:
                    selectedCode = 8;
                    break;
                case R.id.survey_radio_M2_P8_option6:
                    selectedCode = 10;
                    break;
                case R.id.survey_radio_M2_P8_option7:
                    selectedCode = 9;
                    break;
            }
            quest.setCdalojin(selectedCode);
            if(selectedCode==9){
                EditText etP8otros = (EditText) activity.findViewById(R.id.survey_edit_M2_P8_otros);
                quest.setCdalojin_lit(etP8otros.getText().toString());
            }
        }


        //P9
        RadioGroup rgP9 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P9);
        checkedId = rgP9.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P9_option1:
                    selectedCode = 11;
                    break;
                case R.id.survey_radio_M2_P9_option2:
                    selectedCode = 25;
                    break;
                case R.id.survey_radio_M2_P9_option3:
                    selectedCode = 22;
                    break;
                case R.id.survey_radio_M2_P9_option4:
                    selectedCode = 23;
                    break;
                case R.id.survey_radio_M2_P9_option5:
                    selectedCode = 35;
                    break;
                case R.id.survey_radio_M2_P9_option6:
                    selectedCode = 31;
                    break;
                case R.id.survey_radio_M2_P9_option7:
                    selectedCode = 91;
                    break;
            }
            quest.setUltimodo(selectedCode);
            if(selectedCode==91){
                EditText etP9otros = (EditText) activity.findViewById(R.id.survey_edit_M2_P9_others);
                quest.setUltimodo_lit(etP9otros.getText().toString());
            }
        }



        //P10
        RadioGroup rgP10 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P10);
        checkedId = rgP10.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P10_option1:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M2_P10_option2:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M2_P10_option3:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P10_option4:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M2_P10_option5:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M2_P10_option6:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M2_P10_option7:
                    selectedCode = 9;
                    break;
            }
            quest.setSitiopark(selectedCode);
            if(selectedCode==4){
                EditText etP10b = (EditText) activity.findViewById(R.id.survey_edit_M2_P10b);
                quest.setPqfuera(etP10b.getText().toString());
            }
        }

        //P11
        String p11text = ((EditText) activity.findViewById(R.id.survey_edit_M2_P11)).getText().toString();
        if(p11text!= null && !p11text.isEmpty()){
            quest.setAcomptes(Integer.parseInt(p11text));
        } else {
            quest.setAcomptes(-1);
        }

        //P12
        String P12hora = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).getText().toString();
        String P12min = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos)).getText().toString();


        if(P12hora!= null && !P12hora.isEmpty()){
            if(P12min!= null && !P12min.isEmpty()){
                Calendar llegadaTime = Calendar.getInstance();
                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(P12hora));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(P12min));
                llegadaTime.set(Calendar.SECOND, 0);
                quest.setHllega(llegadaTime.getTime());
            }
        }


        //P13
        AutoCompleteTextView actvP13 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P13);
        String P13text = actvP13.getText().toString();
        String p13code = dm.getAssociatedCodeFor(P13text, DictionaryManager.AIRPORT);
        quest.setCdiaptod(p13code);

        //P13 otros literal
        if(p13code!= null && p13code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P13_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptod_lit(otrosLiteral);
            } else{
                quest.setCdiaptod_lit(null);
            }
        }

        //P14
        AutoCompleteTextView actvP14 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P14_company);
        String P14text = actvP14.getText().toString();
        String p14code = dm.getAssociatedCodeFor(P14text, DictionaryManager.COMPANY);
        quest.setNumvuepa_comp(p14code);
        EditText p14number = (EditText) activity.findViewById(R.id.survey_edit_M2_P14);
        quest.setNumvuepa(p14code+p14number.getText().toString());

        //P10 otros literal
        if(p14code!= null && p14code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P14_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setNumvuepa_lit(otrosLiteral);
            } else {
                quest.setNumvuepa_lit(null);
            }
        }


        //P15
        RadioGroup rgP15 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P15);
        checkedId = rgP15.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P15_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P15_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdterm(selectedCode);
        }


        //P15b
        AutoCompleteTextView actvP15b = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P15b);
        String companyCode = dm.getAssociatedCodeFor(actvP15b.getText().toString(), DictionaryManager.COMPANY_LONG);
        quest.setCdociaar(companyCode);

        //P15b otros literal
        if(companyCode!= null && companyCode.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P15b_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdociaar_lit(otrosLiteral);
            } else {
                quest.setCdociaar_lit(null);
            }
        }


        //P16
        AutoCompleteTextView actvP16 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P16);
        String airportCode = dm.getAssociatedCodeFor(actvP16.getText().toString(), DictionaryManager.AIRPORT_LONG);
        quest.setCdiaptof(airportCode);

        //P16 otros literal
        if(airportCode!= null && airportCode.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M2_P16_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptof_lit(otrosLiteral);
            } else {
                quest.setCdiaptof_lit(null);
            }
        }


        //P17
        Spinner sP17 = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P17);
        if(sP17.getSelectedItem()!=null) {
            String codReason = dm.getAssociatedCodeFor(sP17.getSelectedItem().toString(), DictionaryManager.TRAVEL_REASON);
            if (codReason != null) {
                quest.setCdmviaje(Integer.parseInt(codReason));
            }
        } else {
            quest.setCdmviaje(-1);
        }


        //P18
        RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P18);
        checkedId = rgP18.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = 0;
            switch (checkedId) {
                case R.id.survey_radio_M2_P18_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P18_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdidavue(selectedCode);
        }

        //P18a
        EditText etP18a = (EditText) activity.findViewById(R.id.survey_edit_M2_P18b);
        String textP18a = etP18a.getText().toString();
        if(textP18a!= null && !textP18a.isEmpty()){
            quest.setTaus(Integer.parseInt(textP18a));
        } else {
            quest.setTaus(-1);
        }


        //P19
        RadioGroup rgP19 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19);
        checkedId = rgP19.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P19_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P19_option3:
                    selectedCode = 2;
                    break;
            }
            if(selectedCode ==  1){
                quest.setNpers(1);
            } else {
                String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas))
                        .getText().toString();
                if(textP19!= null && !textP19.isEmpty()) {
                    quest.setNpers(Integer.parseInt(textP19));
                } else {
                    quest.setNpers(-1);
                }
            }
        } else {
            String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas))
                    .getText().toString();
            if(textP19!= null && !textP19.isEmpty()) {
                quest.setNpers(Integer.parseInt(textP19));
            } else {
                quest.setNpers(-1);
            }
        }


        //P20
        String textP20 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P20)).getText().toString();
        if(textP20!=null && !textP20.isEmpty()){
            quest.setNninos(Integer.parseInt(textP20));
        } else {
            quest.setNninos(-1);
        }




        //P21
        RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P21);
        checkedId = rgP21.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P21_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P21_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M2_P21_option3:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M2_P21_option4:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M2_P21_option5:
                    selectedCode = 9;
                    break;
            }
            quest.setRelacion(selectedCode);
        }


        //P22
        String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias)).getText().toString();
        if(textP22 != null && !textP22.isEmpty()){
            quest.setCdtreser(Integer.parseInt(textP22));
        } else {
            quest.setCdtreser(-1);
        }


        //P23
        RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P23);
        checkedId = rgP23.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P23_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P23_option2:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M2_P23_option3:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M2_P23_option4:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M2_P23_option5:
                    selectedCode = 9;
                    break;
            }
            quest.setCdbillet(selectedCode);
        }



        //P24
        RadioGroup rgP24 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P24);
        checkedId = rgP24.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P24_option1:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_M2_P24_option2:
                    selectedCode = 2;
                    break;

            }
            if(selectedCode ==  1){
                quest.setNviaje(0);
            } else {
                String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes))
                        .getText().toString();
                if(textP24!= null && !textP24.isEmpty()) {
                    quest.setNviaje(Integer.parseInt(textP24));
                } else {
                    quest.setNviaje(-1);
                }
            }
        } else {
            String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes))
                    .getText().toString();
            if(textP24!= null && !textP24.isEmpty()) {
                quest.setNviaje(Integer.parseInt(textP24));
            } else {
                quest.setNviaje(-1);
            }
        }


        //P25
        String textP25 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes)).getText().toString();
        if(textP25 != null && !textP25.isEmpty()){
            quest.setVol12mes(Integer.parseInt(textP25));
        } else {
            quest.setVol12mes(-1);
        }


        //P26
        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P26);
        checkedId = rgP26.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P26_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P26_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setP44factu(selectedCode);
        }

        //P26a
        String textP26a = ((EditText) activity.findViewById(R.id.survey_edit_M2_P26b_numBultos)).getText().toString();
        if(textP26a!= null && !textP26a.isEmpty()){
            quest.setBulgrupo(Integer.parseInt(textP26a));
        } else {
            quest.setBulgrupo(-1);
        }


        //P27
        String textP27 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas)).getText().toString();
        if(textP27!= null && !textP27.isEmpty()){
            quest.setNperbul(Integer.parseInt(textP27));
        } else {
            quest.setNperbul(-1);
        }

        //P28
        RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P28);
        checkedId = rgP28.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P28_option1:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_M2_P28_option2:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P28_option3:
                    selectedCode = 2;
                    break;
            }
            quest.setChekinb(selectedCode);
        }


        //P29
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P29);
        checkedId = rgP29.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P29_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P29_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setConsume(selectedCode);

        }


        //P29a
        String textP29gasto = ((EditText) activity.findViewById(R.id.survey_edit_M2_P29a)).getText().toString();
        if(textP29gasto != null && !textP29gasto.isEmpty()){
            quest.setGas_cons(Integer.parseInt(textP29gasto));
        } else {
            quest.setGas_cons(-1);
        }


        //P30
        RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P30);
        checkedId = rgP30.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = 0;
            switch (checkedId) {
                case R.id.survey_radio_M2_P30_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P30_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setComprart(selectedCode);
        }

        //P30a
        String textP30a = ((EditText) activity.findViewById(R.id.survey_edit_M2_P30a)).getText().toString();
        if(textP30a != null && !textP30a.isEmpty()){
            quest.setGas_com(Integer.parseInt(textP30a));
        } else {
            quest.setGas_com(-1);
        }

        //P30b
        String selItem1 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item1)).getSelectedItem().toString();
        String codItem1 = dm.getAssociatedCodeFor(selItem1, DictionaryManager.ITEM_TYPE);
        if(codItem1 != null && !codItem1.isEmpty()) {
            quest.setProd1(Integer.parseInt(codItem1));
        } else {
            quest.setProd1(-1);
        }

        String selItem2 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item2)).getSelectedItem().toString();
        String codItem2 = dm.getAssociatedCodeFor(selItem2, DictionaryManager.ITEM_TYPE);
        if(codItem2 != null && !codItem2.isEmpty()) {
            quest.setProd2(Integer.parseInt(codItem2));
        } else {
            quest.setProd2(-1);
        }

        String selItem3 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item3)).getSelectedItem().toString();
        String codItem3 = dm.getAssociatedCodeFor(selItem3, DictionaryManager.ITEM_TYPE);
        if(codItem3 != null && !codItem3.isEmpty()) {
            quest.setProd3(Integer.parseInt(codItem3));
        } else {
            quest.setProd3(-1);
        }

        String selItem4 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item4)).getSelectedItem().toString();
        String codItem4 = dm.getAssociatedCodeFor(selItem4, DictionaryManager.ITEM_TYPE);
        if(codItem4 != null && !codItem4.isEmpty()) {
            quest.setProd4(Integer.parseInt(codItem4));
        } else {
            quest.setProd4(-1);
        }

        String selItem5 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item5)).getSelectedItem().toString();
        String codItem5 = dm.getAssociatedCodeFor(selItem5, DictionaryManager.ITEM_TYPE);
        if(codItem5 != null && !codItem5.isEmpty()) {
            quest.setProd5(Integer.parseInt(codItem5));
        } else {
            quest.setProd5(-1);
        }



        //P31
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P31);
        checkedId = rgP31.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P31_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P31_option2:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M2_P31_option3:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M2_P31_option4:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M2_P31_option5:
                    selectedCode = 6;
                    break;

            }
            quest.setCdslab(selectedCode);
        }


        //P32
        RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P32);
        checkedId = rgP32.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P32_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P32_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M2_P32_option3:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M2_P32_option4:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M2_P32_option5:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_M2_P32_option6:
                    selectedCode = 9;
                    break;
            }
            quest.setCdsprof(selectedCode);
        }


        //P33
        String textP33 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P33)).getSelectedItem().toString();
        String codActivity = dm.getAssociatedCodeFor(textP33, DictionaryManager.ACTIVITY);
        if(codActivity!= null){
            quest.setActiv05(Integer.parseInt(codActivity));

            if(codActivity.contentEquals("99")){
                String textP33otros = ((EditText) activity.findViewById(R.id.survey_edit_M2_P33_otraActividad))
                        .getText().toString();
                quest.setActiv05_lit(textP33otros);
            }
        } else {
            quest.setActiv05(-1);
        }


        //P34
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P34);
        checkedId = rgP34.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P34_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P34_option2:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M2_P34_option3:
                    selectedCode = 5;
                    break;
            }
            quest.setEstudios(selectedCode);
        }


        //P35
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P35);
        checkedId = rgP35.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P35_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P35_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M2_P35_option3:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M2_P35_option4:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M2_P35_option5:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M2_P35_option6:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M2_P35_option7:
                    selectedCode = 7;
                    break;
                case R.id.survey_radio_M2_P35_option8:
                    selectedCode = 8;
                    break;
                case R.id.survey_radio_M2_P35_option9:
                    selectedCode = 9;
                    break;
                case R.id.survey_radio_M2_P35_option10:
                    selectedCode = 10;
                    break;
                case R.id.survey_radio_M2_P35_option11:
                    selectedCode = 11;
                    break;
                case R.id.survey_radio_M2_P35_option12:
                    selectedCode = 12;
                    break;
            }
            quest.setCdedad(selectedCode);
        }


        //P36
        RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P36);
        checkedId = rgP36.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M2_P36_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M2_P36_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdsexo(selectedCode);
        }

            //Return the questionnaire filled
            return quest;


        } catch (Exception e){
            if(throwError){
                throw new Exception(e.toString());

            } else {
                //Return the questionnaire filled
                return quest;
            }
        }

    }







}

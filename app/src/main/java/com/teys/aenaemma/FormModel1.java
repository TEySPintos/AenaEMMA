package com.teys.aenaemma;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by cgo on 3/10/16.
 */

public class FormModel1 extends Form {

    public FormModel1(String airportName, Activity surveyAct, DictionaryManager dm) {
        super(airportName, surveyAct, dm);
    }

    @Override
    public int getLayoutId() {
        return R.layout.form_model1;
    }

    @Override
    public void initFormView() {

        //AutoComplete Text P1
        AutoCompleteTextView p1autoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P1);
        p1autoComplete.requestFocus();
        String[] countries = dm.getStringsArrayFor(DictionaryManager.COUNTRY);
        ArrayAdapter<String> p1adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
        p1autoComplete.setAdapter(p1adapter);


        p1autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry =((TextView) view).getText().toString();
                LinearLayout llp1otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P1_otros);
                EditText etP1otros = (EditText) activity.findViewById(R.id.survey_edit_P1_otros);
                String countryCode = dm.getAssociatedCodeFor(selectedCountry, DictionaryManager.COUNTRY);
                if(countryCode!= null && countryCode.contentEquals("999")){
                    llp1otros.setVisibility(View.VISIBLE);
                    etP1otros.requestFocus();
                } else {
                    llp1otros.setVisibility(View.GONE);
                    etP1otros.setText("");
                }
                //((TextView) activity.findViewById(R.id.survey_text_P1)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                EditText etP2otros = (EditText) activity.findViewById(R.id.survey_edit_P2_otros);
                LinearLayout provLiteral = (LinearLayout) activity.findViewById(R.id.survey_layout_P2_provLiteral);
                LinearLayout locLiteral = (LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral);


                RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_P3_option1);

                String selectedOption = ((TextView) view).getText().toString();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COUNTRY);

                if (selectedCode.contains("724")) {
                    locationSpain.setVisibility(View.VISIBLE);
                    otherCountry.setVisibility(View.GONE);
                    otherLiteral.setVisibility(View.GONE);
                    provLiteral.setVisibility(GONE);
                    locLiteral.setVisibility(GONE);
                    etP2otros.setText("");
                    rbResidencia.setEnabled(true);
                } else if (selectedCode.contains("999")) {
                    locationSpain.setVisibility(GONE);
                    provLiteral.setVisibility(GONE);
                    locLiteral.setVisibility(GONE);
                    otherLiteral.setVisibility(VISIBLE);
                    etP2otros.requestFocus();
                    otherCountry.setVisibility(GONE);
                    rbResidencia.setEnabled(false);
                    rbResidencia.setChecked(false);
                } else if (dm.getAreaStringsResourceId(selectedOption) > 0) {
                    locationSpain.setVisibility(View.GONE);
                    provLiteral.setVisibility(GONE);
                    locLiteral.setVisibility(GONE);
                    otherLiteral.setVisibility(GONE);
                    etP2otros.setText("");
;                   otherCountry.setVisibility(View.VISIBLE);
                    rbResidencia.setEnabled(false);
                    rbResidencia.setChecked(false);
                    setP2areaSpinner(selectedOption);
                } else {
                    locationSpain.setVisibility(View.GONE);
                    provLiteral.setVisibility(GONE);
                    locLiteral.setVisibility(GONE);
                    otherLiteral.setVisibility(GONE);
                    etP2otros.setText("");
                    otherCountry.setVisibility(View.GONE);
                    rbResidencia.setEnabled(false);
                    rbResidencia.setChecked(false);
                }
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
                    RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_P3_option1);

                    //TODO esto es un parche para filtra en "Viene de su residencia"

                    if (!codProvText.contentEquals("41") && !codProvText.contentEquals("11") && !codProvText.contentEquals("21") && !codProvText.contentEquals("14") && !codProvText.contentEquals("23") && !codProvText.contentEquals("29") && !codProvText.contentEquals("18") && !codProvText.contentEquals("10") && !codProvText.contentEquals("6")) {
                        rbResidencia.setEnabled(false);
                        rbResidencia.setChecked(false);
                    } else {
                        rbResidencia.setEnabled(true);
                    }

                    if(codProvText!= null)  {
                        AutoCompleteTextView p2localidadAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_loc);
                        String[] localidades = dm.getLocalidadesFor(codProvText);

                        if (codProvText.contentEquals("99")) {


                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_provLiteral)).setVisibility(View.VISIBLE);
                            ((EditText) activity.findViewById(R.id.survey_edit_P2_prov_lit)).requestFocus();
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral)).setVisibility(View.VISIBLE);
                            AutoCompleteTextView locDesconocida = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_loc);

                            locDesconocida.setText("OTRAS");


                        } else {
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_provLiteral)).setVisibility(View.GONE);
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral)).setVisibility(View.GONE);
                        }

                        ArrayAdapter<String> p2localidadadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, localidades);
                        p2localidadAutoComplete.setAdapter(p2localidadadapter);

                        p2localidadAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedLoc = ((TextView) view).getText().toString();
                                if(selectedLoc!=null && !selectedLoc.isEmpty()) {
                                    String codLocText = dm.getAssociatedCodeFor(selectedLoc, DictionaryManager.LOCALIDAD);
                                    if(codLocText!= null && codLocText.contentEquals("99900"))  {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral)).setVisibility(View.VISIBLE);
                                        ((EditText) activity.findViewById(R.id.survey_edit_P2_loc_lit)).requestFocus();
                                    } else {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral)).setVisibility(View.GONE);
                                    }
                                }
                            }
                        });


                        //Allow to select P3_option1 based on selected Province
                        //((RadioButton) activity.findViewById(R.id.survey_radio_P3_option1))
                               // .setEnabled(dm.isValidResidenceProv(codProvText));


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
                    EditText etProvincia = (EditText) activity.findViewById(R.id.survey_autoComplete_P3_prov);
                    LinearLayout provLit = (LinearLayout) activity.findViewById(R.id.survey_layout_P3_provLiteral);
                    LinearLayout locLit = ((LinearLayout) activity.findViewById(R.id.survey_layout_P3_locLiteral));
                    EditText editProvLit = (EditText) activity.findViewById(R.id.survey_edit_P3_prov_lit);
                    EditText editLocLit = (EditText) activity.findViewById(R.id.survey_edit_P3_loc_lit);
                    AutoCompleteTextView autoProv = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_prov);
                    AutoCompleteTextView autoLoc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_loc);
                    AutoCompleteTextView autoAirport = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport);


                    switch(checkedId){
                        case R.id.survey_radio_P3_option1:
                            localidadP3.setVisibility(INVISIBLE);
                            autoProv.setText("");
                            autoLoc.setText("");
                            conexionP3.setVisibility(INVISIBLE);
                            autoAirport.setText("");
                            rlP4.setVisibility(GONE);
                            provLit.setVisibility(GONE);
                            locLit.setVisibility(GONE);
                            editProvLit.setText("");
                            editLocLit.setText("");
                            llP5_7.setVisibility(VISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(false);
                            break;
                        case R.id.survey_radio_P3_option2:
                            loadP3Fields();
                            localidadP3.setVisibility(View.VISIBLE);
                            etProvincia.requestFocus();
                            autoAirport.setText("");
                            conexionP3.setVisibility(INVISIBLE);
                            llP5_7.setVisibility(VISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(true);
                            break;
                        case R.id.survey_radio_P3_option3:
                            localidadP3.setVisibility(INVISIBLE);
                            autoProv.setText("");
                            autoLoc.setText("");
                            conexionP3.setVisibility(VISIBLE);
                            provLit.setVisibility(GONE);
                            locLit.setVisibility(GONE);
                            editProvLit.setText("");
                            editLocLit.setText("");
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(true);
                            break;
                        default:
                            localidadP3.setVisibility(INVISIBLE);
                            conexionP3.setVisibility(INVISIBLE);
                            llP5_7.setVisibility(VISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option2)).setEnabled(true);
                            break;
                    }
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
                EditText etP3otros = (EditText) activity.findViewById(R.id.survey_edit_P3_otros);
                String airportCode = dm.getAssociatedCodeFor(selecedAirport, DictionaryManager.AIRPORT);
                if(airportCode!= null && airportCode.contentEquals("999")){
                    llp3otros.setVisibility(View.VISIBLE);
                    etP3otros.requestFocus();
                } else {
                    llp3otros.setVisibility(View.GONE);
                    etP3otros.setText("");
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
                    EditText edP5_others = (EditText) activity.findViewById(R.id.survey_edit_P5_others);
                    switch (checkedId){
                        case R.id.survey_radio_P5_option3:
                        case R.id.survey_radio_P5_option4:
                            edP5_others.setVisibility(INVISIBLE);
                            edP5_others.setText("");
                            break;
                        case R.id.survey_radio_P5_option7:
                            edP5_others.setVisibility(VISIBLE);
                            edP5_others.requestFocus();
                            break;
                        default:
                            edP5_others.setVisibility(INVISIBLE);
                            edP5_others.setText("");
                            break;
                    }
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
                    EditText etP6b = (EditText) activity.findViewById(R.id.survey_edit_P6b);
                    switch(checkedId){
                        case R.id.survey_radio_P6_option6:
                            llP6b.setVisibility(VISIBLE);
                            etP6b.requestFocus();
                            break;
                        default:
                            llP6b.setVisibility(GONE);
                            etP6b.setText("");
                            break;
                    }
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
                    //(tvP7).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                        etP8hora.setError("El campo hora debe tener un valor comprendido entre 00 y 23");
                    } else {
                        etP8hora.setBackgroundColor(Color.WHITE);
                    }

                    EditText etP8min = (EditText) activity.findViewById(R.id.survey_edit_P8_minutos);
                    String textMin = etP8min.getText().toString();
                    if(textMin != null && !textMin.isEmpty()) {
                        //Change question text color as answered
                        TextView tvP8 = (TextView) activity.findViewById(R.id.survey_text_P8);
                        //(tvP8).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                        etP8min.setError("El campo minutos debe tener un valor comprendido entre 00 y 59");
                    } else {
                        etP8min.setBackgroundColor(Color.WHITE);
                    }

                    EditText etP8hora = (EditText) activity.findViewById(R.id.survey_edit_P8_hora);
                    String textHora = etP8hora.getText().toString();
                    if (textHora != null && !textHora.isEmpty()) {
                        //Change question text color as answered
                        TextView tvP8 = (TextView) activity.findViewById(R.id.survey_text_P8);
                        //(tvP8).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
        String[] airportsShor = dm.getStringsArrayFor(DictionaryManager.AIRPORT);
        ArrayAdapter<String> p9airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsShor);
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
                //checkP26_28();



                //Show literal field if selected aiport = others
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT);
                LinearLayout llP9otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P9_otros);
                EditText etP9otros = (EditText) activity.findViewById(R.id.survey_edit_P9_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP9otros.setVisibility(VISIBLE);
                    etP9otros.requestFocus();
                } else {
                    llP9otros.setVisibility(GONE);
                    etP9otros.setText("");
                }
                //((TextView) activity.findViewById(R.id.survey_text_P9)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                EditText etP10otros = (EditText) activity.findViewById(R.id.survey_edit_P10_otros);
                if(selectedCode!=null && selectedCode.contentEquals("999")){
                    llP10otros.setVisibility(VISIBLE);
                    etP10otros.requestFocus();
                } else {
                    llP10otros.setVisibility(GONE);
                    etP10otros.setText("");
                }
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
                            //llP12.setVisibility(GONE);
                            //llP13.setVisibility(GONE);
                            break;
                        default:
                            //llP12.setVisibility(VISIBLE);
                            //llP13.setVisibility(VISIBLE);
                            break;
                    }
                }
                //checkP26_28();
                //((TextView) activity.findViewById(R.id.survey_text_P11)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                EditText etP12Otros = (EditText) activity.findViewById(R.id.survey_edit_P12_otros);
                String company = ((TextView) view).getText().toString();
                String companyCode = dm.getAssociatedCodeFor(company, DictionaryManager.COMPANY_LONG);
                LinearLayout llP12otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P12_otros);
                if(companyCode!=null && companyCode.contentEquals("999")){
                    llP12otros.setVisibility(VISIBLE);
                    etP12Otros.requestFocus();
                } else {
                    llP12otros.setVisibility(GONE);
                    etP12Otros.setText("");
                }
                //((TextView) activity.findViewById(R.id.survey_text_P12)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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

                /*TextView tvSelected = (TextView) view;
                String airport = tvSelected.getText().toString();
                String P27Btext = activity.getResources().getString(R.string.survey_text_P27B).toString();
                String P27BtextFormated = String.format(P27Btext,airport);
                TextView tvP27B = (TextView) activity.findViewById(R.id.survey_text_P27B);
                tvP27B.setText(P27BtextFormated);*/


                //Show literal field if selected aiport = others
                String airport = ((TextView) view).getText().toString();
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT_LONG);
                LinearLayout llP13otros = (LinearLayout) activity.findViewById(R.id.survey_layout_P13_otros);
                EditText etP13otros = (EditText) activity.findViewById(R.id.survey_edit_P13_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP13otros.setVisibility(VISIBLE);
                    etP13otros.requestFocus();
                } else {
                    llP13otros.setVisibility(GONE);
                    etP13otros.setText("");
                }
                //((TextView) activity.findViewById(R.id.survey_text_P13)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
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
                    EditText etP15b = (EditText) activity.findViewById(R.id.survey_edit_P15b);
                    switch (checkedId){
                        case R.id.survey_radio_P15_option1:
                            tvP15a.setVisibility(VISIBLE);
                            tvP15b.setVisibility(GONE);
                            llP15num.setVisibility(VISIBLE);
                            etP15b.requestFocus();
                            break;
                        case R.id.survey_radio_P15_option2:
                            tvP15a.setVisibility(GONE);
                            tvP15b.setVisibility(VISIBLE);
                            llP15num.setVisibility(VISIBLE);
                            etP15b.requestFocus();
                            break;
                        default:
                            tvP15a.setVisibility(INVISIBLE);
                            tvP15b.setVisibility(INVISIBLE);
                            llP15num.setVisibility(INVISIBLE);
                            break;
                    }

                    if(checkedId== R.id.survey_radio_P15_option1){


                        RadioButton rb3Op1 = (RadioButton) activity.findViewById(R.id.survey_radio_P3_option1);


                        if (!rb3Op1.isChecked()) {
                            ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option1))
                                    .setTextColor(activity.getResources().getColor(R.color.aenaRed));
                            ((TextView) activity.findViewById(R.id.survey_text_P15_error)).setVisibility(VISIBLE);

                        }
                    } else {
                        ((RadioButton) activity.findViewById(R.id.survey_radio_P15_option1))
                                .setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                        ((TextView) activity.findViewById(R.id.survey_text_P15_error)).setVisibility(GONE);
                    }
                }
            }
        });

        EditText etP15 = (EditText) activity.findViewById(R.id.survey_edit_P15b);
        etP15.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP15 = (EditText) activity.findViewById(R.id.survey_edit_P15b);
                String num = etP15.getText().toString();
                if(num != null && !num.isEmpty()){
                    int numDias = Integer.parseInt(num);






                    int spP14 = ((Spinner) activity.findViewById(R.id.survey_spinner_P14)).getSelectedItemPosition();

                    String textP14 = Integer.toString(spP14);
                    String textP15 = ((EditText) activity.findViewById(R.id.survey_edit_P15b)).getText().toString();

                    boolean error = false;
                    if(textP14 != null && !textP14.isEmpty()){
                        if(textP15 != null && !textP15.isEmpty()){
                            int numP14 = Integer.parseInt(textP14);
                            int numP15 = Integer.parseInt(textP15);

                            switch(numP14){
                                case 4:
                                    if(numDias<=90){
                                        etP15.setError("El propósito principal del viaje no es coherente con el número de días indicados");
                                        etP15.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP15.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                                case 18:
                                    if(numDias>7){
                                        etP15.setError("El propósito principal del viaje no es coherente con el número de días indicados");
                                        etP15.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP15.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                                case 19:
                                    if(numDias<=7){
                                        etP15.setError("El propósito principal del viaje no es coherente con el número de días indicados");
                                        etP15.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP15.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                            }

                        }
                    }



                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P15b)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    //etP15.setBackgroundColor(Color.WHITE);
                    //((TextView) activity.findViewById(R.id.survey_text_P15b)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) { }
        });



        //P16
        RadioGroup rgP16 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16);
        rgP16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()) {
                    LinearLayout llP17 = (LinearLayout) activity.findViewById(R.id.survey_layout_P17);
                    LinearLayout llP18 = (LinearLayout) activity.findViewById(R.id.survey_layout_P18);
                    LinearLayout llP24 = (LinearLayout) activity.findViewById(R.id.survey_layout_P24);
                    EditText etP24 = (EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas);
                    switch (checkedId) {
                        case R.id.survey_radio_P16_option1:
                            etP24.setText("1");
                            break;
                        default:
                            etP24.setText("");
                            break;
                    }
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
                        etP17.setError("El número de menores en el grupo no puede ser superior al número total de personas");
                    } else{
                        etP17.setBackgroundColor(Color.WHITE);
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P17)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P18)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                    //((TextView) activity.findViewById(R.id.survey_text_P19)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P20)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                            //llP22.setVisibility(GONE);
                            break;
                        default:
                            //llP22.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P21)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                        etP22.setError("El número de viajes en la misma ruta no puede ser superior al número total de viajes");
                    } else{
                        etP22.setBackgroundColor(Color.WHITE);
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P22)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    EditText etP23 = (EditText) activity.findViewById(R.id.survey_edit_P23b_numBultos);

                    switch (checkedId) {
                        case R.id.survey_radio_P23_option1:
                            llP23_num.setVisibility(VISIBLE);
                            etP23.requestFocus();
                            break;
                        case R.id.survey_radio_P23_option2:
                            llP23_num.setVisibility(INVISIBLE);
                            etP23.setText("");
                            break;
                        default:
                            llP23_num.setVisibility(INVISIBLE);

                            break;
                    }
                    //((TextView) activity.findViewById(R.id.survey_text_P23)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                                etP24.setError("El número de personas con bultos no puede ser mayor que el número total de personas del grupo");
                                etP24.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                error = true;
                            } else {
                                etP24.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P24)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P25)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P26)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P26B
        RadioGroup rgP26B = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26B);
        rgP26B.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP26Bnums = (LinearLayout) activity.findViewById(R.id.survey_layout_P26Bnums);
                    LinearLayout llP26Ba = (LinearLayout) activity.findViewById(R.id.survey_layout_P26Ba);
                    EditText etP15b = (EditText) activity.findViewById(R.id.survey_edit_P15b);
                    switch (checkedId){
                        case R.id.survey_radio_P26B_option1:
                            llP26Bnums.setVisibility(VISIBLE);
                            llP26Ba.setVisibility(VISIBLE);
                            break;
                        case R.id.survey_radio_P26B_option2:
                            llP26Bnums.setVisibility(GONE);
                            llP26Ba.setVisibility(GONE);
                            etP15b.requestFocus();
                            break;
                        default:
                            llP26Bnums.setVisibility(INVISIBLE);
                            llP26Ba.setVisibility(INVISIBLE);
                            break;
                    }
                }
            }
        });

        RadioGroup rgP26Bb = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26Bb);
        rgP26Bb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    EditText etP26otros = (EditText) activity.findViewById(R.id.survey_edit_P26_otros);
                    switch (checkedId){
                        case R.id.survey_radio_P26Bb_option4:
                            etP26otros.setVisibility(VISIBLE);
                            etP26otros.requestFocus();
                            break;
                        default:
                            etP26otros.setVisibility(GONE);
                            etP26otros.setText("");
                            break;
                    }
                }
            }
        });


        //Spinner P27
        final Spinner p27spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P27);
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

                    LinearLayout P27others = (LinearLayout) activity.findViewById(R.id.survey_layout_P27_others);
                    EditText P27motivoLit = (EditText) activity.findViewById(R.id.survey_edit_P27_especificar);

                    String P27motivo = p27spinner.getSelectedItem().toString();
                    if (selection.contains("91")) {
                        P27others.setVisibility(VISIBLE);
                        P27motivoLit.requestFocus();
                    }else{
                        P27others.setVisibility(GONE);
                        P27motivoLit.setText("");
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    return;
                }



                ((TextView) activity.findViewById(R.id.survey_text_P27)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Spinner P27B
        final Spinner p27Bspinner = (Spinner) activity.findViewById(R.id.survey_spinner_P27B);
        ArrayAdapter<CharSequence> p27BAdapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p27strings,
                        R.layout.selection_spinner_item);
        p27Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        p27Bspinner.setAdapter(p27BAdapter);

        p27Bspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String selection = tv.getText().toString();

                if(selection!= null && !selection.contentEquals("...")) {

                    LinearLayout P27Bothers = (LinearLayout) activity.findViewById(R.id.survey_layout_P27B_others);
                    EditText P27BmotivoLit = (EditText) activity.findViewById(R.id.survey_edit_P27B_especificar);

                    String P27Bmotivo = p27Bspinner.getSelectedItem().toString();
                    if (selection.contains("91")) {
                        P27Bothers.setVisibility(VISIBLE);
                        P27BmotivoLit.requestFocus();
                    }else{
                        P27Bothers.setVisibility(GONE);
                        P27BmotivoLit.setText("");
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    return;
                }



                ((TextView) activity.findViewById(R.id.survey_text_P27B)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P28)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                            etP29a.requestFocus();
                            break;
                        default:
                            tvP29a.setVisibility(INVISIBLE);
                            etP29a.setVisibility(INVISIBLE);
                            etP29a.setText("");
                            break;
                    }
                    //((TextView) activity.findViewById(R.id.survey_text_P29)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    EditText etP30a = (EditText) activity.findViewById(R.id.survey_edit_P30a);
                    switch (checkedId){
                        case R.id.survey_radio_P30_option1:
                            llP30a.setVisibility(VISIBLE);
                            etP30a.requestFocus();
                            break;
                        default:
                            llP30a.setVisibility(GONE);
                            break;
                    }
                    //((TextView) activity.findViewById(R.id.survey_text_P30)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                            //rgP32.setVisibility(VISIBLE);
                            break;
                        default:
                            //rgP32.setVisibility(GONE);
                            break;
                    }

                    //checkP33Visibility();
                    //((TextView) activity.findViewById(R.id.survey_text_P31)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P32)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                EditText etP33otraActividad = (EditText) activity.findViewById(R.id.survey_edit_P33_otraActividad);

                TextView tvSelected = (TextView) view;
                String[] possibleValues = activity.getResources().getStringArray(R.array.p33strings);
                if(tvSelected.getText().toString().contentEquals(possibleValues[possibleValues.length-1])){
                    llp33others.setVisibility(VISIBLE);
                    etP33otraActividad.requestFocus();
                } else {
                    llp33others.setVisibility(GONE);
                    etP33otraActividad.setText("");
                }
                if(possibleValues!=null && !tvSelected.getText().toString().contentEquals(possibleValues[0])){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_P33)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P34)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P35)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_P36)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
        final Spinner p2spinner = (Spinner) activity.findViewById(R.id.survey_spinner_P2_area);
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

        p2spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EditText P2regionLit = (EditText) activity.findViewById(R.id.survey_edit_P2_region_lit);
                String P2_area = p2spinner.getSelectedItem().toString();
                if (P2_area.contains("999")) {
                    P2regionLit.setVisibility(VISIBLE);
                    P2regionLit.requestFocus();
                }else{
                    P2regionLit.setVisibility(INVISIBLE);
                    P2regionLit.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //return;
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
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_P3_locLiteral)).setVisibility(View.VISIBLE);
                            AutoCompleteTextView locDesconocida = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_loc);

                            locDesconocida.setText("OTRAS");

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
                                    if (codLocText != null && codLocText.contentEquals("99900")) {
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
        if(airportCode!=null && (airportCode.contentEquals("OVD") || airportCode.contentEquals("BIO") || airportCode.contentEquals("LEI"))){
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

    public boolean checkQuestion(int check) {
        AutoCompleteTextView actvAeropuertoOrigen = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport);
        String selectedAirport = actvAeropuertoOrigen.getText().toString();
        String codCountry = "";
        int selOpt=-1;

        switch (check) {

            case 1:
                AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P1);
                String p1Text = actvP1.getText().toString();
                codCountry = dm.getAssociatedCodeFor(p1Text, DictionaryManager.COUNTRY);
                if (codCountry == null || codCountry.isEmpty()) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el país de NACIONALIDAD", Toast.LENGTH_LONG);
                    toast.show();
                    actvP1.requestFocus();
                    return false;
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P1_otros)).getVisibility() == VISIBLE) {
                    EditText etP1_otros = (EditText) activity.findViewById(R.id.survey_edit_P1_otros);
                    String txtP1_otros = etP1_otros.getText().toString();

                    if (txtP1_otros == null || txtP1_otros.isEmpty()) {
                        etP1_otros.setError("Se debe especificar otro país de nacionalidad");
                        etP1_otros.requestFocus();
                        return false;
                    }
                }

                break;
            case 2:
                //P2 Pais de residencia != null
                AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2);
                String p2Text = actvP2.getText().toString();
                codCountry = dm.getAssociatedCodeFor(p2Text, DictionaryManager.COUNTRY);
                if (codCountry == null || codCountry.isEmpty()) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el país de residencia", Toast.LENGTH_LONG);
                    toast.show();
                    actvP2.requestFocus();
                    return false;
                }


                //P2Localidad != null
//        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
//        p2Text = actvP2.getText().toString();
                codCountry = dm.getAssociatedCodeFor(p2Text, DictionaryManager.COUNTRY);
                if (codCountry != null && codCountry.contentEquals("724")) {
                    AutoCompleteTextView actvP2_loc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2_loc);
                    String codLoc = dm.getAssociatedCodeFor(actvP2_loc.getText().toString(), DictionaryManager.LOCALIDAD);
                    if (codLoc == null || codLoc.isEmpty()) {
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
                if (areasStrings != null && areasStrings.length != 0) {
                    Spinner spP2_area = (Spinner) activity.findViewById(R.id.survey_spinner_P2_area);
                    String codArea = dm.getAssociatedCodeForArea(spP2_area.getSelectedItem().toString(), codCountry);
                    if (codArea == null || codArea.isEmpty() || codArea.contentEquals("0")) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el área o región de residencia del pasajero (P2)", Toast.LENGTH_LONG);
                        toast.show();
                        spP2_area.requestFocus();
                        return false;
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P2_otros)).getVisibility() == VISIBLE) {
                    EditText etP2otros = (EditText) activity.findViewById(R.id.survey_edit_P2_otros);
                    String txtP2otros = etP2otros.getText().toString();
                    if (txtP2otros == null || txtP2otros.isEmpty()) {
                        etP2otros.setError("Se debe especificar otro país");
                        etP2otros.requestFocus();
                        return false;
                    }
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P2_provLiteral)).getVisibility() == VISIBLE) {
                    EditText etP2provLit = (EditText) activity.findViewById(R.id.survey_edit_P2_prov_lit);
                    String textP2provLit = etP2provLit.getText().toString();
                    if (textP2provLit == null || textP2provLit.isEmpty()) {
                        etP2provLit.setError("Se debe especificar otra provincia");
                        etP2provLit.requestFocus();
                        return false;
                    }
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P2_locLiteral)).getVisibility() == VISIBLE) {
                    EditText etP2locLit = (EditText) activity.findViewById(R.id.survey_edit_P2_loc_lit);
                    String textP2locLit = etP2locLit.getText().toString();
                    if (textP2locLit == null || textP2locLit.isEmpty()) {
                        etP2locLit.setError("Se debe especificar otra localidad");
                        etP2locLit.requestFocus();
                        return false;
                    }

                    if(((LinearLayout) activity.findViewById(R.id.survey_layout_P2_otherCountry)).getVisibility() == VISIBLE) {
                        if(((EditText) activity.findViewById(R.id.survey_edit_P2_region_lit)).getVisibility() == VISIBLE) {
                            EditText etP2regionLit = (EditText) activity.findViewById(R.id.survey_edit_P2_region_lit);
                            String textP2regionLit = etP2regionLit.getText().toString();
                            if (textP2regionLit == null || textP2regionLit.isEmpty()) {
                                etP2regionLit.setError("Se debe especificar otra región");
                                etP2regionLit.requestFocus();
                                return false;
                            }
                        }
                    }
                }

                break;
            case 3:
                //P3 != null
                RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
                int checkedId = rgP3.getCheckedRadioButtonId();
                if (checkedId <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar desde donde se ha ACCEDIDO al aeropuerto", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }

                //P3 localidad != null
                if (checkedId == R.id.survey_radio_P3_option2) {
                    AutoCompleteTextView actvP3localidad = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_loc);
                    String textP7Localidad = actvP3localidad.getText().toString();
                    String codLocalidad = dm.getAssociatedCodeFor(textP7Localidad, DictionaryManager.LOCALIDAD_PROC);
                    if (codLocalidad == null || codLocalidad.isEmpty()) {
                        actvP3localidad.setError("Se debe indicar la localidad de donde proviene el pasajero");
                        actvP3localidad.requestFocus();
                        return false;
                    }
                }

                //P3 aeropuerto != null
                if (checkedId == R.id.survey_radio_P3_option3) {
                    AutoCompleteTextView actvP3aerop = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport);
                    String textP3Aerop = actvP3aerop.getText().toString();
                    String codAerop = dm.getAssociatedCodeFor(textP3Aerop, DictionaryManager.AIRPORT);
                    if (codAerop == null || codAerop.isEmpty()) {
                        actvP3aerop.setError("Se debe indicar el aeropuerto de donde proviene el pasajero");
                        actvP3aerop.requestFocus();
                        return false;
                    }

                    //P3a aeropuerto origen != aeropuerto encuesta
                    String selCode = codAerop;
                    if (selCode != null && selCode.contentEquals(airportCode)) {
                        Toast toast = Toast.makeText(activity, "El aeropuerto de origen no puede ser igual que el actual", Toast.LENGTH_LONG);
                        toast.show();
                        ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport)).requestFocus();
                        return false;
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P3_otros)).getVisibility() == VISIBLE) {
                        EditText etP3otros = (EditText) activity.findViewById(R.id.survey_edit_P3_otros);
                        String txtP3otros = etP3otros.getText().toString();
                        if (txtP3otros == null || txtP3otros.isEmpty()) {
                            etP3otros.setError("Se debe especificar otro aeropuerto");
                            etP3otros.requestFocus();
                            return false;
                        }
                    }
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P3_provLiteral)).getVisibility() == VISIBLE) {
                    EditText etP3provLit = (EditText) activity.findViewById(R.id.survey_edit_P3_prov_lit);
                    String textP3provLit = etP3provLit.getText().toString();
                    if (textP3provLit == null || textP3provLit.isEmpty()) {
                        etP3provLit.setError("Se debe especificar otra provincia");
                        etP3provLit.requestFocus();
                        return false;
                    }
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P3_locLiteral)).getVisibility() == VISIBLE) {
                    EditText etP3locLit = (EditText) activity.findViewById(R.id.survey_edit_P3_loc_lit);
                    String textP3locLit = etP3locLit.getText().toString();
                    if (textP3locLit == null || textP3locLit.isEmpty()) {
                        etP3locLit.setError("Se debe especificar otra localidad");
                        etP3locLit.requestFocus();
                        return false;
                    }
                }

                break;
            case 4:
                //P4
                if (((RelativeLayout) activity.findViewById(R.id.survey_layout_P4)).getVisibility() == VISIBLE) {
                    selOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P4)).getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE ALOJAMIENTO utilizado durante la estancia", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }

                    if (((EditText) activity.findViewById(R.id.survey_edit_P4_otros)).getVisibility() == VISIBLE) {
                        EditText etP4otros = (EditText) activity.findViewById(R.id.survey_edit_P4_otros);
                        String txtP4otros = etP4otros.getText().toString();
                        if (txtP4otros == null || txtP4otros.isEmpty()) {
                            etP4otros.setError("Se debe especificar otro tipo de alojamiento");
                            etP4otros.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 5:
                //P5
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P5)).getVisibility() == VISIBLE) {
                    RadioGroup rgP5 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P5);
                    selOpt = rgP5.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el ÚLTIMO MODO DE TRANSPORTE", Toast.LENGTH_LONG);
                        toast.show();
                        rgP5.requestFocus();
                        return false;
                    }

                    if (((EditText) activity.findViewById(R.id.survey_edit_P5_others)).getVisibility() == VISIBLE) {
                        EditText etP5otros = (EditText) activity.findViewById(R.id.survey_edit_P5_others);
                        String txtP5otros = etP5otros.getText().toString();
                        if (txtP5otros == null || txtP5otros.isEmpty()) {
                            etP5otros.setError("Se debe especificar otro modo de transporte");
                            etP5otros.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 6:
                //P6
                if (((RelativeLayout) activity.findViewById(R.id.survey_layout_P6)).getVisibility() == VISIBLE) {
                    selOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P6)).getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar DÓNDE se ha dejado el vehículo", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }

                    if (selOpt == R.id.survey_radio_P5_option6) {
                        String textP6b = ((EditText) activity.findViewById(R.id.survey_edit_P6b)).getText().toString();
                        if (textP6b == null || textP6b.isEmpty()) {
                            Toast toast = Toast.makeText(activity, "Se debe indicar el POR QUÉ se ha dejado el vehículo en un parking fuera", Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_P6b)).requestFocus();
                            return false;
                        }
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P6b)).getVisibility() == VISIBLE) {
                        EditText etP6b = (EditText) activity.findViewById(R.id.survey_edit_P6b);
                        String txtP6b = etP6b.getText().toString();
                        if (txtP6b == null || txtP6b.isEmpty()) {
                            etP6b.setError("Se debe especificar un motivo");
                            etP6b.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 7:
                //P7
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P7)).getVisibility() == VISIBLE) {
                    EditText etP7 = (EditText) activity.findViewById(R.id.survey_edit_P7);
                    String textP7 = etP7.getText().toString();
                    if (textP7 == null || textP7.isEmpty()) {
                        etP7.setError("Se debe indicar el número de acompañantes");
                        etP7.requestFocus();
                        return false;
                    }
                }

                break;
            case 8:
                //P8 Hora llegada != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P8)).getVisibility() == VISIBLE) {
                    EditText etP8hora = (EditText) activity.findViewById(R.id.survey_edit_P8_hora);
                    EditText etP8min = (EditText) activity.findViewById(R.id.survey_edit_P8_minutos);
                    String P8hora = ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).getText().toString();
                    String P8min = ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos)).getText().toString();

                    if (P8hora == null || P8hora.isEmpty()) {
                        etP8hora.setError("Se debe indicar la hora completa de llegada al aeropuerto");
                        etP8hora.requestFocus();
                        return false;
                    }

                    if (P8min == null || P8min.isEmpty()) {
                        etP8min.setError("Se debe indicar la hora completa de llegada al aeropuerto");
                        etP8min.requestFocus();
                        return false;
                    }

                    String textHora = P8hora;
                    if (textHora != null && !textHora.isEmpty()) {
                        int hora = Integer.parseInt(textHora);
                        if (hora < 0 || hora > 23) {
                            Toast toast = Toast.makeText(activity, "El campo hora debe tener un valor comprendido entre 00 y 23",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).requestFocus();
                            return false;
                        }
                    }

                    String textMin = P8min;
                    if (textMin != null && !textMin.isEmpty()) {
                        int min = Integer.parseInt(textMin);
                        if (min < 0 || min > 59) {
                            Toast toast = Toast.makeText(activity, "El campo minutos debe tener un valor comprendido entre 00 y 59",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_P8_minutos)).requestFocus();
                            return false;
                        }
                    }

                    Calendar llegadaTime = Calendar.getInstance();
                    String textHoraLlegada = P8hora;
                    String textMinLlegada = P8min;
                    if (textHoraLlegada != null && !textHoraLlegada.isEmpty()) {
                        if (textMinLlegada != null && !textMinLlegada.isEmpty()) {

                            llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(textHoraLlegada));
                            llegadaTime.set(Calendar.MINUTE, Integer.parseInt(textMinLlegada));
                            llegadaTime.set(Calendar.SECOND, 0);

                            Date fechaEncuesta = Calendar.getInstance().getTime();
                            SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
                            String dateText = ((EditText) activity.findViewById(R.id.survey_edit_fecha)).getText().toString();
                            String timeText = ((EditText) activity.findViewById(R.id.survey_edit_hora)).getText().toString();
                            try {
                                fechaEncuesta = sdfDate.parse(dateText + " " + timeText);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (llegadaTime.getTime().getTime() > fechaEncuesta.getTime()) {
                                Toast toast = Toast.makeText(activity, "La hora de llegada no puede ser posterior a la hora de realización de la encuesta",
                                        Toast.LENGTH_LONG);
                                toast.show();
                                ((EditText) activity.findViewById(R.id.survey_edit_P8_hora)).requestFocus();
                                return false;
                            }
                        }
                    }
                }

                break;
            case 9:
                //P9 Aeropuerto destino != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P9)).getVisibility() == VISIBLE) {
                    AutoCompleteTextView actvAeropuertoDestino = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
                    String p9Text = actvAeropuertoDestino.getText().toString();
                    selectedAirport = dm.getAssociatedCodeFor(p9Text, DictionaryManager.AIRPORT);;
                    if (selectedAirport == null || selectedAirport.isEmpty()) {
                        Toast toast = Toast.makeText(activity, "El aeropuerto de destino debe estar cumplimentado", Toast.LENGTH_LONG);
                        toast.show();
                        actvAeropuertoDestino.requestFocus();
                        return false;
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P9_otros)).getVisibility() == VISIBLE) {
                        EditText etP9otros = (EditText) activity.findViewById(R.id.survey_edit_P9_otros);
                        String txtP9otros = etP9otros.getText().toString();
                        if (txtP9otros == null || txtP9otros.isEmpty()) {
                            etP9otros.setError("Se debe especificar otro aeropuerto");
                            etP9otros.requestFocus();
                            return false;
                        }
                    }
                    return checkCoherence(9);
                }

                break;
            case 10:
                //P10 Compañia y número de vuelo != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P10)).getVisibility() == VISIBLE) {
                    AutoCompleteTextView actvCompany = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P10_company);
                    String p10Text = actvCompany.getText().toString();
                    String selectedCompany = dm.getAssociatedCodeFor(p10Text, DictionaryManager.COMPANY);;
                    if (selectedCompany == null || selectedCompany.isEmpty()) {
                        actvCompany.setError("Se debe indicar la compañía aérea");
                        actvCompany.requestFocus();
                        return false;
                    }

                    EditText etFlightNum = (EditText) activity.findViewById(R.id.survey_edit_P10);
                    String number = etFlightNum.getText().toString();
                    if (number == null || number.isEmpty()) {
                        etFlightNum.setError("Se debe indicar el número de vuelo");
                        etFlightNum.requestFocus();
                        return false;
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P10_otros)).getVisibility() == VISIBLE) {
                        EditText etP10otros = (EditText) activity.findViewById(R.id.survey_edit_P10_otros);
                        String textP10otros = etP10otros.getText().toString();
                        if (textP10otros == null || textP10otros.isEmpty()) {
                            etP10otros.setError("Se debe indicar otra compañía");
                            etP10otros.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 11:
                //P11
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P11)).getVisibility() == VISIBLE) {
                    RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P11);
                    selOpt = rgP11.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si se va a hacer TRANSBORDO O FINALIZA el viaje", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                break;
            case 12:
                //P12
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P12)).getVisibility() == VISIBLE) {
                    String textP12 = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P12)).getText().toString();
                    String code = dm.getAssociatedCodeFor(textP12, DictionaryManager.COMPANY_LONG);
                    if (code == null || code.isEmpty() || code.contentEquals("0")) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la COMPAÑIA con la que continua el viaje", Toast.LENGTH_LONG);
                        toast.show();
                        ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P12)).requestFocus();
                        return false;
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P12_otros)).getVisibility() == VISIBLE) {
                        EditText etP12otros = (EditText) activity.findViewById(R.id.survey_edit_P12_otros);
                        String textP12otros = etP12otros.getText().toString();
                        if (textP12otros == null || textP12otros.isEmpty()) {
                            etP12otros.setError("Se debe indicar otra compañía");
                            etP12otros.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 13:
                //P13
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P13)).getVisibility() == VISIBLE) {
                    String textP13 = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13)).getText().toString();
                    String code = dm.getAssociatedCodeFor(textP13, DictionaryManager.AIRPORT_LONG);
                    if (code == null || code.isEmpty() || code.contentEquals("0")) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el AEROPUERTO en el que finalizará su viaje", Toast.LENGTH_LONG);
                        toast.show();
                        ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13)).requestFocus();
                        return false;
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P13_otros)).getVisibility() == VISIBLE) {
                        EditText etP13otros = (EditText) activity.findViewById(R.id.survey_edit_P13_otros);
                        String txtP13otros = etP13otros.getText().toString();
                        if (txtP13otros == null || txtP13otros.isEmpty()) {
                            etP13otros.setError("Se debe especificar otro aeropuerto");
                            etP13otros.requestFocus();
                            return false;
                        }
                    }

                    return checkCoherence(13);
                }

                break;
            case 14:
                //P14 Motivo viaje != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P14)).getVisibility() == VISIBLE) {
                    Spinner sP14 = (Spinner) activity.findViewById(R.id.survey_spinner_P14);
                    String code = dm.getAssociatedCodeFor((String) sP14.getSelectedItem(), DictionaryManager.TRAVEL_REASON);
                    if (code == null || code.isEmpty() || code.contentEquals("0")) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el PROPOSITO PRINCIPAL del viaje", Toast.LENGTH_LONG);
                        toast.show();
                        sP14.requestFocus();
                        return false;
                    }
                }

                break;
            case 15:
                //P15 Viaje IDA o VUELTA != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P15)).getVisibility() == VISIBLE) {
                    RadioGroup rgP15 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P15);
                    selOpt = rgP15.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si el viaje es de IDA o VUELTA", Toast.LENGTH_LONG);
                        toast.show();
                        rgP15.requestFocus();
                        return false;
                    }

                    //P15b Num dias
                    EditText etP15b = (EditText) activity.findViewById(R.id.survey_edit_P15b);
                    String textP15b = etP15b.getText().toString();
                    if (textP15b == null || textP15b.isEmpty()) {
                        etP15b.setError("Se debe indicar el número de días");
                        etP15b.requestFocus();
                        return false;
                    }


                    EditText etP15 = (EditText) activity.findViewById(R.id.survey_edit_P15b);
                    String numP15 = etP15.getText().toString();

                    int numDias = Integer.parseInt(numP15);

                    int spP14b = ((Spinner) activity.findViewById(R.id.survey_spinner_P14)).getSelectedItemPosition();

                    String textP14 = Integer.toString(spP14b);
                    String textP15 = ((EditText) activity.findViewById(R.id.survey_edit_P15b)).getText().toString();

                    boolean error = false;
                    if(textP14 != null && !textP14.isEmpty()){
                        if(textP15 != null && !textP15.isEmpty()){
                            int numP14 = Integer.parseInt(textP14);
                            //int numP21 = Integer.parseInt(textP21);

                            switch(numP14){
                                case 4:
                                    if(numDias<=90){
                                        LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_P14);
                                        etP15.setError("El propósito principal del viaje no es coherente con el número de días indicados");
                                        p14.setVisibility(VISIBLE);
                                        return false;
                                    }
                                    break;
                                case 18:
                                    if(numDias>7) {
                                        LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_P14);
                                        etP15.setError("El propósito principal del viaje no es coherente con el número de días indicados");
                                        p14.setVisibility(VISIBLE);
                                        return false;
                                    }
                                    break;
                                case 19:
                                    if(numDias<=7) {
                                        LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_P14);
                                        etP15.setError("El propósito principal del viaje no es coherente con el número de días indicados");
                                        p14.setVisibility(VISIBLE);
                                        return false;
                                    }
                                    break;
                            }

                        }
                    }
                }

                break;
            case 16:
                //P16 Num personas != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P16)).getVisibility() == VISIBLE) {
                    RadioGroup rgP16 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16);
                    selOpt = rgP16.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo", Toast.LENGTH_LONG);
                        toast.show();
                        rgP16.requestFocus();
                        return false;
                    }

                    if (selOpt == R.id.survey_radio_P16_option3) {
                        //P16num != null
                        EditText etP16numPersonas = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas));
                        String textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();
                        if (textP16 == null || textP16.isEmpty()) {
                            etP16numPersonas.setError("Se debe indicar el número de personas en el grupo");
                            etP16numPersonas.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 17:
                //P17
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P17)).getVisibility() == VISIBLE) {
                    EditText etP17 = ((EditText) activity.findViewById(R.id.survey_edit_P17));
                    String textP17 = etP17.getText().toString();
                    if (textP17 == null || textP17.isEmpty()) {
                        etP17.setError("Se debe indicar el número de menores de 15 años");
                        etP17.requestFocus();
                        return false;
                    }
                    return checkCoherence(17);
                }

                break;
            case 18:
                //P18 != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P18)).getVisibility() == VISIBLE) {
                    int selecOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P18)).getCheckedRadioButtonId();
                    if (selecOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la RELACIÓN entre los compnentes del grupo", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                break;
            case 19:
                //P19 NumDias != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P19)).getVisibility() == VISIBLE) {
                    EditText etP19numDias = ((EditText) activity.findViewById(R.id.survey_edit_P19_numDias));
                    String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_P19_numDias)).getText().toString();
                    if (textP19 == null || textP19.isEmpty()) {
                        etP19numDias.setError("Se debe indicar el tiempo que hace que se reservó el billete");
                        etP19numDias.requestFocus();
                        return false;
                    }
                }

                break;
            case 20:
                //P20 != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P20)).getVisibility() == VISIBLE) {
                    RadioGroup rgP20 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P20);
                    selOpt = rgP20.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE TARIFA del billete", Toast.LENGTH_LONG);
                        toast.show();
                        rgP20.requestFocus();
                        return false;
                    }
                }

                break;
            case 21:
                //P21 Num viajes avion != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P21)).getVisibility() == VISIBLE) {
                    RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P21);
                    EditText etP21numViajes = (EditText) activity.findViewById(R.id.survey_edit_P21_numViajes);
                    selOpt = rgP21.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión", Toast.LENGTH_LONG);
                        toast.show();
                        rgP21.requestFocus();
                        return false;
                    }

                    if (selOpt == R.id.survey_radio_P21_option2) {
                        String textP21 = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes)).getText().toString();
                        if (textP21 == null | textP21.isEmpty()) {
                            etP21numViajes.setError("Se debe indicar el número de viajes");
                            etP21numViajes.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 22:
                //P22
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P22)).getVisibility() == VISIBLE) {
                    EditText etP22numViajes = ((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes));
                    String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).getText().toString();
                    if (textP22 == null || textP22.isEmpty()) {
                        etP22numViajes.setError("Se debe indicar cuántas veces se ha realizado la misma ruta completa");
                        etP22numViajes.requestFocus();
                        return false;
                    }

                    return checkCoherence(22);
                }

                break;
            case 23:
                //P23 Facturación & Nº bultos != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P23)).getVisibility() == VISIBLE) {
                    RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P23);
                    int selectedOption = rgP23.getCheckedRadioButtonId();
                    if (selectedOption < 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si se ha FACTURADO equipaje", Toast.LENGTH_LONG);
                        toast.show();
                        rgP23.requestFocus();
                        return false;

                    } else {
                        if (selectedOption == R.id.survey_radio_P23_option1) {
                            EditText etP23numBultos = (EditText) activity.findViewById(R.id.survey_edit_P23b_numBultos);
                            String numBultos = etP23numBultos.getText().toString();
                            if (numBultos == null || numBultos.isEmpty()) {
                                etP23numBultos.setError("Se debe indicar el número de bultos");
                                etP23numBultos.requestFocus();
                                return false;
                            }
                        }
                    }

                    return checkCoherence(23);
                }

                break;
            case 24:
                //P24 != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P24)).getVisibility() == VISIBLE) {
                    String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas)).getText().toString();
                    EditText etP24numPersonas = ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas));
                    if (textP24 == null || textP24.isEmpty()) {
                        etP24numPersonas.setError("Se debe indicar a cuántas personas pertenecen los bultos");
                        etP24numPersonas.requestFocus();
                        return false;
                    }

                    if (textP24.contentEquals("0")) {
                        etP24numPersonas.setError("El número de personas a las que pertenecen los bultos debe ser distinto de cero");
                        etP24numPersonas.requestFocus();
                        return false;
                    }

                    return checkCoherence(24);
                }

                break;
            case 25:
                //P25 tarjeta embarque != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P25)).getVisibility()==VISIBLE) {
                    RadioGroup rgP25 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P25);
                    selOpt = rgP25.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar donde se ha obtenido la TARJETA DE EMBARQUE", Toast.LENGTH_LONG);
                        toast.show();
                        rgP25.requestFocus();
                        return false;
                    }
                }

                break;
            case 26:
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P26)).getVisibility()==VISIBLE) {
                    RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26);
                    selOpt = rgP26.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha utilizado AVE en algun ocasión", Toast.LENGTH_LONG);
                        toast.show();
                        rgP26.requestFocus();
                        return false;
                    }
                }

                break;/*
            case 26:
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P26B)).getVisibility()==VISIBLE) {
                    RadioGroup rgP26B = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26B);
                    RadioGroup rgP26Bb = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26Bb);
                    selOpt = rgP26B.getCheckedRadioButtonId();
                    int selOptB = rgP26Bb.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha utilizado AVE en algun ocasión", Toast.LENGTH_LONG);
                        toast.show();
                        rgP26B.requestFocus();
                        return false;
                    }
                    if(((LinearLayout) activity.findViewById(R.id.survey_layout_P26Bnums)).getVisibility()==VISIBLE) {
                        EditText etP26Bnums = (EditText) activity.findViewById(R.id.survey_edit_P26Bb);
                        String txtP26Bnums  = etP26Bnums .getText().toString();

                        if (txtP26Bnums  == null || txtP26Bnums .isEmpty()) {
                            etP26Bnums.setError("Se debe especificar el número de veces");
                            etP26Bnums.requestFocus();
                            return false;
                        }
                    }

                    if(((LinearLayout) activity.findViewById(R.id.survey_layout_P26Ba)).getVisibility()==VISIBLE) {
                        if (selOptB <= 0) {
                            Toast toast = Toast.makeText(activity, "Se debe indicar un modo de transporte", Toast.LENGTH_LONG);
                            toast.show();
                            return false;
                        }
                    }

                    if(((EditText) activity.findViewById(R.id.survey_edit_P26_otros)).getVisibility()==VISIBLE) {
                        EditText etP26Botros = (EditText) activity.findViewById(R.id.survey_edit_P26_otros);
                        String txtP26Botros  = etP26Botros .getText().toString();

                        if (txtP26Botros  == null || txtP26Botros .isEmpty()) {
                            etP26Botros.setError("Se debe especificar otro modo");
                            etP26Botros.requestFocus();
                            return false;
                        }
                    }
                }

                break;*/
            case 27:
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P27)).getVisibility()==VISIBLE) {
                    String textP27 = (String) ((Spinner) activity.findViewById(R.id.survey_spinner_P27)).getSelectedItem();
                    if (textP27 == null || textP27.isEmpty() || textP27.contentEquals("...")) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el MOTIVO por el que se ha elegido el avión", Toast.LENGTH_LONG);
                        toast.show();
                        ((Spinner) activity.findViewById(R.id.survey_spinner_P27)).requestFocus();
                        return false;
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P27_others)).getVisibility() == VISIBLE) {
                        EditText etP27otros = (EditText) activity.findViewById(R.id.survey_edit_P27_especificar);
                        String txtP27otros = etP27otros.getText().toString();

                        if (txtP27otros == null || txtP27otros.isEmpty()) {
                            etP27otros.setError("Se debe especificar otro motivo");
                            etP27otros.requestFocus();
                            return false;
                        }
                    }

                }

                break;/*
            case 27:
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P27B)).getVisibility()==VISIBLE) {
                    String textP27B = (String) ((Spinner) activity.findViewById(R.id.survey_spinner_P27B)).getSelectedItem();
                    if (textP27B == null || textP27B.isEmpty() || textP27B.contentEquals("...")) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el MOTIVO", Toast.LENGTH_LONG);
                        toast.show();
                        ((Spinner) activity.findViewById(R.id.survey_spinner_P27B)).requestFocus();
                        return false;
                    }

                    if (((LinearLayout) activity.findViewById(R.id.survey_layout_P27B_others)).getVisibility() == VISIBLE) {
                        EditText etP27Botros = (EditText) activity.findViewById(R.id.survey_edit_P27B_especificar);
                        String txtP27Botros = etP27Botros.getText().toString();

                        if (txtP27Botros == null || txtP27Botros.isEmpty()) {
                            etP27Botros.setError("Se debe especificar otro motivo");
                            etP27Botros.requestFocus();
                            return false;
                        }
                    }

                }

                break;
                */
            case 28:
                //P28 Opcion transporte != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P28)).getVisibility()==VISIBLE) {
                    RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P28);
                    selOpt = rgP28.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar que MEDIO DE TRANSPORTE se utilizaría", Toast.LENGTH_LONG);
                        toast.show();
                        rgP28.requestFocus();
                        return false;
                    }
                }

                break;
            case 29:
                //P29 Consumo restauración != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P29)).getVisibility()==VISIBLE) {
                    RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P29);
                    selOpt = rgP29.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha CONSUMIDO algún producto en los servicios de RESTAURACIÓN", Toast.LENGTH_LONG);
                        toast.show();
                        rgP29.requestFocus();
                        return false;
                    }

                    if (selOpt == R.id.survey_radio_P29_option1) {
                        String textP29a = ((EditText) activity.findViewById(R.id.survey_edit_P29a)).getText().toString();
                        if (textP29a == null || textP29a.isEmpty()) {
                            Toast toast = Toast.makeText(activity, "Se debe indicar cuánto se ha GASTADO en los servicios de RESTAURACIÓN", Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_P29a)).requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 30:
                //P30 Compra articulo != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P30)).getVisibility()==VISIBLE) {
                    int spin1 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item1)).getSelectedItemPosition();
                    int spin2 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item2)).getSelectedItemPosition();
                    int spin3 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item3)).getSelectedItemPosition();
                    int spin4 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item4)).getSelectedItemPosition();
                    int spin5 = ((Spinner) activity.findViewById(R.id.survey_spinner_P30b_item5)).getSelectedItemPosition();

                    RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P30);
                    selOpt = rgP30.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha COMPRADO algún artículo en las tiendas", Toast.LENGTH_LONG);
                        toast.show();
                        rgP30.requestFocus();
                        return false;
                    }

                    if (selOpt == R.id.survey_radio_P30_option1) {
                        String textP30a = ((EditText) activity.findViewById(R.id.survey_edit_P30a)).getText().toString();
                        if (textP30a == null || textP30a.isEmpty()) {
                            Toast toast = Toast.makeText(activity, "Se debe indicar cuánto se ha GASTADO en las TIENDAS del aeropuerto", Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_P30a)).requestFocus();
                            return false;
                        }
                        if (spin1==0 && spin2==0 && spin3==0 && spin4==0 && spin5==0) {
                            Toast toast = Toast.makeText(activity, "Se debe especificar al menos un artículo", Toast.LENGTH_LONG);
                            toast.show();
                            return false;
                        }
                    }
                }

                break;
            case 31:
                //P31 Situación laboral != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P31)).getVisibility()==VISIBLE) {
                    RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P31);
                    selOpt = rgP31.getCheckedRadioButtonId();
                    if (selOpt < 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la SITUACIÓN LABORAL", Toast.LENGTH_LONG);
                        toast.show();
                        rgP31.requestFocus();
                        return false;
                    }
                }

                break;
            case 32:
                //P32
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P32)).getVisibility()==VISIBLE) {
                    RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P32);
                    selOpt = rgP32.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el tipo de TRABAJO", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                break;
            case 33:
                //P33 Actividad empresa != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P33)).getVisibility()==VISIBLE) {
                    int spinP33 = ((Spinner) activity.findViewById(R.id.survey_spinner_P33)).getSelectedItemPosition();
                    if (spinP33 == 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la ACTIVIDAD de la empresa u organización", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_P33_others)).getVisibility() == VISIBLE) {
                    EditText etP33_otraActividad = (EditText) activity.findViewById(R.id.survey_edit_P33_otraActividad);
                    String textP33_otraActividad = etP33_otraActividad.getText().toString();
                    if (textP33_otraActividad == null || textP33_otraActividad.isEmpty()) {
                        etP33_otraActividad.setError("Se debe especificar otra actividad");
                        etP33_otraActividad.requestFocus();
                        return false;
                    }
                }

                break;
            case 34:
                //P34 Estudios != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P34)).getVisibility()==VISIBLE) {
                    RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P34);
                    selOpt = rgP34.getCheckedRadioButtonId();
                    if (selOpt < 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NIVEL DE ESTUDIOS del entrevistado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP34.requestFocus();
                        return false;
                    }
                }

                break;
            case 35:
                //P35 Edad != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P35)).getVisibility()==VISIBLE) {
                    RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P35);
                    selOpt = rgP35.getCheckedRadioButtonId();
                    if (selOpt < 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la EDAD del entrevistado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP35.requestFocus();
                        return false;
                    }
                }

                break;
            case 36:
                //P36 sexo != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_P36)).getVisibility()==VISIBLE) {
                    RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P36);
                    selOpt = rgP36.getCheckedRadioButtonId();
                    if (selOpt < 0) {
                        Toast toast = Toast.makeText(activity, "Se debe seleccionar un SEXO del entrevistado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP36.requestFocus();
                        return false;
                    }
                }

               break;
        }
        return true;
    }

    public boolean checkCoherence(int check) {

        int selOpt = -1;
        String airportOriginName="";
        String airportOriginCode="";
        String selectedAirport="";

        switch (check) {
            case 9:
                AutoCompleteTextView P9airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
                selectedAirport = P9airportAutoComplete.getText().toString();
                airportCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
                AutoCompleteTextView actvP3 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport);
                airportOriginName = actvP3.getText().toString();
                airportOriginCode = dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT);
                P9airportAutoComplete.setBackgroundColor(Color.WHITE);
                if (airportCode != null && !airportCode.isEmpty()) {

                    if (airportOriginCode != null && !airportOriginCode.isEmpty()) {
                        if (airportCode.contentEquals(airportOriginCode)) {
                            View view = activity.getCurrentFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                            builder.setTitle("Se ha producido una incoherencia");
                            builder.setMessage("Para continuar con el cuestionario es necesario modificar al menos una de las respuestas.");
                            //builder.setPositiveButton("Aceptar", null);

                            P9airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            P9airportAutoComplete.setError("El aeropuerto de destino no puede ser el mismo que el de origen");

                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9)).requestFocus();
                                }

                            });
                            builder.create();
                            builder.show();
                            showQuestion(3);
                            return false;
                        }
                    }
                }

                break;
            case 13:
                LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_P3);
                LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_P9);

                AutoCompleteTextView actvAeropuertoFinal = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13);
                selectedAirport = actvAeropuertoFinal.getText().toString();
                AutoCompleteTextView P13airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13);
                P13airportAutoComplete.setBackgroundColor(Color.WHITE);
                airportCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
                if (airportCode != null && !airportCode.isEmpty()) {
                    actvP3 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P3_airport);
                    airportOriginName = actvP3.getText().toString();
                    airportOriginCode = dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT);
                    if (airportOriginCode != null && !airportOriginCode.isEmpty()) {
                        if (airportCode.contentEquals(airportOriginCode)) {
                            View view = activity.getCurrentFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                            builder.setTitle("Se ha producido una incoherencia");
                            builder.setMessage("Para continuar con el cuestionario es necesario modificar al menos una de las respuestas.");
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13)).requestFocus();
                                }
                            });
                            P13airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            P13airportAutoComplete.setError("El aeropuerto de finalización del viaje no puede ser el mismo que el de origen.");
                            builder.create();
                            builder.show();
                            p3.setVisibility(VISIBLE);
                            return false;
                        } else {
                            p3.setVisibility(GONE);
                        }
                    }
                }

                AutoCompleteTextView actvP9 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
                String airportOriginNameP9 = actvP9.getText().toString();
                String airportOriginCodeP9 = dm.getAssociatedCodeFor(airportOriginNameP9, DictionaryManager.AIRPORT);

                airportCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
                if (airportCode != null && !airportCode.isEmpty()) {


                    if (airportOriginCodeP9 != null && !airportOriginCodeP9.isEmpty()) {
                        if (airportCode.contentEquals(airportOriginCodeP9)) {
                            View view = activity.getCurrentFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                            builder.setTitle("Se ha producido una incoherencia");
                            builder.setMessage("Para continuar con el cuestionario es necesario modificar al menos una de las respuestas.");
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13)).requestFocus();
                                }
                            });
                            P13airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            P13airportAutoComplete.setError("El aeropuerto de finalización del viaje no puede ser el mismo que el de origen.");
                            builder.create();
                            builder.show();
                            p9.setVisibility(VISIBLE);
                            return false;
                        } else {
                            p9.setVisibility(GONE);
                        }
                    }
                }

                break;
            case 17:
                // Num P17 < P16
                String textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();
                String textP17 = ((EditText) activity.findViewById(R.id.survey_edit_P17)).getText().toString();

                if (textP16 != null && !textP16.isEmpty()) {
                    if (textP17 != null && !textP17.isEmpty()) {
                        int numP16 = Integer.parseInt(textP16);
                        int numP17 = Integer.parseInt(textP17);
                        if (numP17 > numP16) {
                            View view = activity.getCurrentFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                            builder.setTitle("Se ha producido una incoherencia");
                            //builder.setMessage("El número de menores no puede ser mayor que el número total de personas en el grupo. Es necesario modificar al menos una de las dos respuestas.");
                            builder.setMessage("Para continuar con el cuestionario es necesario modificar al menos una de las respuestas.");
                            //builder.setPositiveButton("Aceptar", null);
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).requestFocus();
                                }
                            });
                            builder.create();
                            builder.show();
                            //((EditText) activity.findViewById(R.id.survey_edit_P17)).requestFocus();
                            showQuestion(16);
                            return false;
                        }
                    }
                }

                break;
            case 22:
                //P22 <= P21
                EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_P22_numViajes);
                String numViajes = etP22.getText().toString();
                RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P21);
                if (numViajes != null && !numViajes.isEmpty()) {
                    int numViajesMismaRuta = Integer.parseInt(numViajes);

                    int numTotalViajes = 0;
                    selOpt = rgP21.getCheckedRadioButtonId();
                    if (selOpt != R.id.survey_radio_P21_option1) {
                        String num = ((EditText) activity.findViewById(R.id.survey_edit_P21_numViajes)).getText().toString();
                        if (num != null && !num.isEmpty()) {
                            numTotalViajes = Integer.parseInt(num);
                        }
                    }

                    if (numViajesMismaRuta > numTotalViajes) {
                        View view = activity.getCurrentFocus();
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                        builder.setTitle("Se ha producido una incoherencia");
                        builder.setMessage("Para continuar con el cuestionario es necesario modificar al menos una de las respuestas.");
                        //builder.setPositiveButton("Aceptar", null);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                ((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).requestFocus();
                            }
                        });
                        builder.create();
                        builder.show();
                        //((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).requestFocus();
                        //((EditText) activity.findViewById(R.id.survey_edit_P22_numViajes)).clearFocus();
                        showQuestion(21);
                        return false;
                    }
                }

                break;
            case 23:
                final EditText etP23 = (EditText) activity.findViewById(R.id.survey_edit_P23b_numBultos);
                String textP23 = etP23.getText().toString();
                final RadioButton rbP3opt2 = (RadioButton) activity.findViewById(R.id.survey_radio_P23_option2);

                if (textP23.contentEquals("0")) {

                    View view = activity.getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                    builder.setTitle("Se ha producido una incoherencia");
                    //builder.setMessage("El número de menores no puede ser mayor que el número total de personas en el grupo. Es necesario modificar al menos una de las dos respuestas.");
                    builder.setMessage("Para continuar con el cuestionario es necesario modificar esta respuesta. ¿El número de bultos es igual a cero?");
                    //builder.setPositiveButton("Aceptar", null);
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            etP23.setText("");
                            rbP3opt2.setChecked(true);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            etP23.setText("");
                            etP23.requestFocus();
                        }
                    });
                    builder.create();
                    builder.show();
                    //((EditText) activity.findViewById(R.id.survey_edit_P17)).requestFocus();
                    return false;
                }
                   break;
            case 24:
                // Num P16 < P24
                textP16 = ((EditText) activity.findViewById(R.id.survey_edit_P16_numPersonas)).getText().toString();
                EditText etP24 =((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas));

                selOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16)).getCheckedRadioButtonId();
                if(selOpt == R.id.survey_radio_P16_option1){
                    textP16 = "1";
                }
                String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas)).getText().toString();

                if(textP16 != null && !textP16.isEmpty()){
                    if(textP24 != null && !textP24.isEmpty()){
                        int numP16 = Integer.parseInt(textP16);
                        int numP24 = Integer.parseInt(textP24);
                        if(numP24>numP16){
                            View view = activity.getCurrentFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                            builder.setTitle("Se ha producido una incoherencia");
                            //builder.setMessage("El número de personas con bultos no puede ser mayor que el número total de personas del grupo. Es necesario modificar al menos una de las dos respuestas.");
                            builder.setMessage("Para continuar con el cuestionario es necesario modificar al menos una de las respuestas.");
                            builder.setPositiveButton("Aceptar", null);
                            builder.create();
                            builder.show();
                            //etP24.requestFocus();
                            showQuestion(16);
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }

    public int showNextQuestion(int show) {

        int checkedId;
        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P31);
        RadioGroup rgP16 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P16);


        switch (show) {
            case 1:

                Button anterior = (Button) activity.findViewById(R.id.survey_button_previous);
                anterior.setVisibility(VISIBLE);

                show = showQuestion(2);

                break;
            case 2:

                show = showQuestion(3);

                break;
            case 3:

                rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P3);
                checkedId = rgP3.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P3_option1:
                            show = showQuestion(5);
                            break;
                        case R.id.survey_radio_P3_option3:
                            show = showQuestion(8);
                            break;
                        default:
                            show = showQuestion(4);
                            break;
                    }
                }

                break;
            case 4:

                show = showQuestion(5);

                break;
            case 5:

                RadioGroup rgP5 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P5);
                checkedId = rgP5.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P5_option3:
                            show = showQuestion(6);
                            break;
                        case R.id.survey_radio_P5_option4:
                            show = showQuestion(6);
                            break;
                        default:
                            show = showQuestion(7);
                            break;
                    }
                }

                break;
            case 6:

                show = showQuestion(7);

                break;
            case 7:

                show = showQuestion(8);

                break;
            case 8:

                show = showQuestion(9);

                break;
            case 9:

                show = showQuestion(10);

                break;
            case 10:

                show = showQuestion(11);

                break;
            case 11:

                RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P11);
                checkedId = rgP11.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P11_option1:
                            show = showQuestion(14);
                            break;
                        default:
                            show = showQuestion(12);
                            break;
                    }
                }

                break;
            case 12:

                show = showQuestion(13);

                break;
            case 13:

                show = showQuestion(14);

                break;
            case 14:

                show = showQuestion(15);

                break;
            case 15:

                show = showQuestion(16);

                break;
            case 16:

                checkedId = rgP16.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P16_option1:
                            show = showQuestion(19);
                            break;
                        default:
                            show = showQuestion(17);
                            break;
                    }
                }

                break;
            case 17:

                show = showQuestion(18);

                break;
            case 18:

                show = showQuestion(19);

                break;
            case 19:

                show = showQuestion(20);

                break;
            case 20:

                show = showQuestion(21);

                break;
            case 21:

                RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P21);
                checkedId = rgP21.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P21_option1:
                            show = showQuestion(23);
                            break;
                        default:
                            show = showQuestion(22);
                            break;
                    }
                }

                break;
            case 22:

                show = showQuestion(23);

                break;
            case 23:

                RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P23);
                checkedId = rgP23.getCheckedRadioButtonId();
                int checkedIdP16 = rgP16.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P23_option2:
                            show = showQuestion(25);
                            break;
                        default:
                            if(checkedIdP16>0) {
                                switch (checkedIdP16) {
                                    case R.id.survey_radio_P16_option1:
                                        show = showQuestion(25);
                                        break;
                                    default:
                                        show = showQuestion(24);
                                        break;
                                }
                            }
                            break;
                    }
                }

                break;
            case 24:

                show = showQuestion(25);

                break;
            case 25:
                AutoCompleteTextView airportP9AutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
                AutoCompleteTextView airportP13AutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13);
                String airportP9 = airportP9AutoComplete.getText().toString();
                String airportP13 = airportP13AutoComplete.getText().toString();
                String codP9 = dm.getAssociatedCodeFor(airportP9, DictionaryManager.AIRPORT);
                String codP13 = dm.getAssociatedCodeFor(airportP13, DictionaryManager.AIRPORT_LONG);

                int checkedIdP3 = rgP3.getCheckedRadioButtonId();

                    //if (checkedIdP3 !=  R.id.survey_radio_P3_option3 && ( codP9.equals("MAD")||codP13.equals("MAD") )){
                    if ((checkedIdP3 !=  R.id.survey_radio_P3_option3) && (codP9.equals("MAD"))){
                        show = showQuestion(26);

                    }else{
                        show = showQuestion(29);

                    }

                break;
            case 26:

                RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26);
                checkedId = rgP26.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P26_option1:
                            show = showQuestion(27);
                            break;
                        default:
                            show = showQuestion(29);
                            break;
                    }
                }

                break;
            case 27:

                show = showQuestion(28);

                break;
            case 28:

                show = showQuestion(29);

                break;
            case 29:

                show = showQuestion(30);

                break;
            case 30:

                show = showQuestion(31);

                break;
            case 31:

                checkedId = rgP31.getCheckedRadioButtonId();
                if(checkedId>0) {
                    switch (checkedId) {
                        case R.id.survey_radio_P31_option1:
                            show = showQuestion(32);
                            break;
                        default:
                            show = showQuestion(34);
                            break;
                    }
                }

                break;
            case 32:

                Spinner sP14 = (Spinner) activity.findViewById(R.id.survey_spinner_P14);
                String codReason = dm.getAssociatedCodeFor(sP14.getSelectedItem().toString(), DictionaryManager.TRAVEL_REASON);
                int codigo = Integer.parseInt(codReason);

                if (codigo>100 && codigo<107) {
                    checkedId = rgP31.getCheckedRadioButtonId();
                    if(checkedId>0) {
                        switch (checkedId) {
                            case R.id.survey_radio_P31_option1:
                                show = showQuestion(33);
                                break;
                            default:
                                show = showQuestion(34);
                                break;
                        }
                    }

                }else{
                    show = showQuestion(34);
                }

                break;
            case 33:

                show = showQuestion(34);

                break;
            case 34:

                show = showQuestion(35);

                break;
            case 35:

                show = showQuestion(36);

                Button siguiente = (Button) activity.findViewById(R.id.survey_button_next);
                Button guardar = (Button) activity.findViewById(R.id.survey_button_save);
                siguiente.setVisibility(GONE);
                guardar.setVisibility(VISIBLE);

                break;

        }
        return show;
    }


    @Override
    public int onNextPressed(int p) {

        if(checkQuestion(p)) {hideQuestions();
            return showNextQuestion(p);
        } else {return 0;}

 /*VALID

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

 VALID*/


    }

    public int showQuestion(int show) {

        switch(show) {
            case 1:

                LinearLayout p1 = (LinearLayout) activity.findViewById(R.id.survey_layout_P1);
                Button previo = (Button) activity.findViewById(R.id.survey_button_previous);
                previo.setVisibility(GONE);
                p1.setVisibility(VISIBLE);
                show = 1;

                break;
            case 2:

                LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_P2);
                AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P2);
                p2.setVisibility(VISIBLE);
                actvP2.requestFocus();
                show = 2;

                break;
            case 3:

                LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_P3);
                p3.setVisibility(VISIBLE);
                show = 3;

                break;
            case 4:

                RelativeLayout p4 = (RelativeLayout) activity.findViewById(R.id.survey_layout_P4);
                p4.setVisibility(VISIBLE);
                show = 4;

                break;
            case 5:

                LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_P5);
                p5.setVisibility(VISIBLE);
                show = 5;

                break;
            case 6:

                RelativeLayout p6 = (RelativeLayout) activity.findViewById(R.id.survey_layout_P6);
                p6.setVisibility(VISIBLE);
                show = 6;

                break;
            case 7:

                EditText etP7 = (EditText) activity.findViewById(R.id.survey_edit_P7);
                LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_P7);
                p7.setVisibility(VISIBLE);
                etP7.requestFocus();
                show = 7;

                break;
            case 8:

                EditText etP8 = (EditText) activity.findViewById(R.id.survey_edit_P8_hora);
                LinearLayout p8 = (LinearLayout) activity.findViewById(R.id.survey_layout_P8);
                p8.setVisibility(VISIBLE);
                etP8.requestFocus();
                show = 8;

                break;
            case 9:

                AutoCompleteTextView actvP9 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P9);
                LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_P9);
                p9.setVisibility(VISIBLE);
                actvP9.requestFocus();
                show = 9;

                break;
            case 10:

                AutoCompleteTextView actvP10 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P10_company);
                LinearLayout p10 = (LinearLayout) activity.findViewById(R.id.survey_layout_P10);
                p10.setVisibility(VISIBLE);
                actvP10.requestFocus();
                show = 10;

                break;
            case 11:

                LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_P11);
                p11.setVisibility(VISIBLE);
                show = 11;

                break;
            case 12:

                AutoCompleteTextView actvP12 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P12);
                LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_P12);
                p12.setVisibility(VISIBLE);
                actvP12.requestFocus();
                show = 12;

                break;
            case 13:

                AutoCompleteTextView actvP13 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P13);
                LinearLayout p13 = (LinearLayout) activity.findViewById(R.id.survey_layout_P13);
                p13.setVisibility(VISIBLE);
                actvP13.requestFocus();
                show = 13;

                break;
            case 14:

                LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_P14);
                p14.setVisibility(VISIBLE);
                show = 14;

                break;
            case 15:

                LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_P15);
                p15.setVisibility(VISIBLE);
                show = 15;

                break;
            case 16:

                LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_P16);
                p16.setVisibility(VISIBLE);
                show = 16;

                break;
            case 17:

                EditText etP17 = (EditText) activity.findViewById(R.id.survey_edit_P17);
                LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_P17);
                p17.setVisibility(VISIBLE);
                etP17.requestFocus();
                show = 17;

                break;
            case 18:

                LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_P18);
                p18.setVisibility(VISIBLE);
                show = 18;

                break;
            case 19:

                EditText etP19 = (EditText) activity.findViewById(R.id.survey_edit_P19_numDias);
                LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_P19);
                p19.setVisibility(VISIBLE);
                etP19.requestFocus();
                show = 19;

                break;
            case 20:

                LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_P20);
                p20.setVisibility(VISIBLE);
                show = 20;

                break;
            case 21:

                LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_P21);
                p21.setVisibility(VISIBLE);
                show = 21;

                break;
            case 22:

                EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_P22_numViajes);
                LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_P22);
                p22.setVisibility(VISIBLE);
                etP22.requestFocus();
                show = 22;

                break;
            case 23:

                LinearLayout p23 = (LinearLayout) activity.findViewById(R.id.survey_layout_P23);
                p23.setVisibility(VISIBLE);
                show = 23;

                break;
            case 24:

                EditText etP24 = (EditText) activity.findViewById(R.id.survey_edit_P24_numPersonas);
                LinearLayout p24 = (LinearLayout) activity.findViewById(R.id.survey_layout_P24);
                p24.setVisibility(VISIBLE);
                etP24.requestFocus();
                show = 24;

                break;
            case 25:

                LinearLayout pb25 = (LinearLayout) activity.findViewById(R.id.survey_layout_P25);
                pb25.setVisibility(VISIBLE);
                show = 25;

                break;
            case 26:

                LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_P26);
                p26.setVisibility(VISIBLE);
                show = 26;

                break;
            case 27:

                LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_P27);
                p27.setVisibility(VISIBLE);
                show = 27;

                break;
            case 28:

                LinearLayout p28 = (LinearLayout) activity.findViewById(R.id.survey_layout_P28);
                p28.setVisibility(VISIBLE);
                show = 28;

                break;
            case 29:

                LinearLayout p29 = (LinearLayout) activity.findViewById(R.id.survey_layout_P29);
                p29.setVisibility(VISIBLE);
                show = 29;

                break;
            case 30:

                LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_P30);
                p30.setVisibility(VISIBLE);
                show = 30;

                break;
            case 31:

                LinearLayout p31 = (LinearLayout) activity.findViewById(R.id.survey_layout_P31);
                p31.setVisibility(VISIBLE);
                show = 31;

                break;
            case 32:

                LinearLayout p32 = (LinearLayout) activity.findViewById(R.id.survey_layout_P32);
                p32.setVisibility(VISIBLE);
                show = 32;

                break;
            case 33:

                LinearLayout p33 = (LinearLayout) activity.findViewById(R.id.survey_layout_P33);
                p33.setVisibility(VISIBLE);
                show = 33;

                break;
            case 34:

                LinearLayout p34 = (LinearLayout) activity.findViewById(R.id.survey_layout_P34);
                p34.setVisibility(VISIBLE);
                show = 34;

                break;
            case 35:

                Button guardar = (Button) activity.findViewById(R.id.survey_button_save);
                guardar.setVisibility(GONE);
                Button siguiente = (Button) activity.findViewById(R.id.survey_button_next);
                siguiente.setVisibility(VISIBLE);

                LinearLayout p35 = (LinearLayout) activity.findViewById(R.id.survey_layout_P35);
                p35.setVisibility(VISIBLE);
                show = 35;

                break;
            case 36:

                LinearLayout p36 = (LinearLayout) activity.findViewById(R.id.survey_layout_P36);
                p36.setVisibility(VISIBLE);
                show = 36;

                break;
        }
        return show;
    }

    public void hideQuestions() {

        LinearLayout p1 = (LinearLayout) activity.findViewById(R.id.survey_layout_P1);
        p1.setVisibility(GONE);

        LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_P2);
        p2.setVisibility(GONE);

        LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_P3);
        p3.setVisibility(GONE);

        RelativeLayout p4 = (RelativeLayout) activity.findViewById(R.id.survey_layout_P4);
        p4.setVisibility(GONE);

        LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_P5);
        p5.setVisibility(GONE);

        RelativeLayout p6 = (RelativeLayout) activity.findViewById(R.id.survey_layout_P6);
        p6.setVisibility(GONE);

        LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_P7);
        p7.setVisibility(GONE);

        LinearLayout p8 = (LinearLayout) activity.findViewById(R.id.survey_layout_P8);
        p8.setVisibility(GONE);

        LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_P9);
        p9.setVisibility(GONE);

        LinearLayout p10 = (LinearLayout) activity.findViewById(R.id.survey_layout_P10);
        p10.setVisibility(GONE);

        LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_P11);
        p11.setVisibility(GONE);

        LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_P12);
        p12.setVisibility(GONE);

        LinearLayout p13 = (LinearLayout) activity.findViewById(R.id.survey_layout_P13);
        p13.setVisibility(GONE);

        LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_P14);
        p14.setVisibility(GONE);

        LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_P15);
        p15.setVisibility(GONE);

        LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_P16);
        p16.setVisibility(GONE);

        LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_P17);
        p17.setVisibility(GONE);

        LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_P18);
        p18.setVisibility(GONE);

        LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_P19);
        p19.setVisibility(GONE);

        LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_P20);
        p20.setVisibility(GONE);

        LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_P21);
        p21.setVisibility(GONE);

        LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_P22);
        p22.setVisibility(GONE);

        LinearLayout p23 = (LinearLayout) activity.findViewById(R.id.survey_layout_P23);
        p23.setVisibility(GONE);

        LinearLayout p24 = (LinearLayout) activity.findViewById(R.id.survey_layout_P24);
        p24.setVisibility(GONE);

        LinearLayout p25 = (LinearLayout) activity.findViewById(R.id.survey_layout_P25);
        p25.setVisibility(GONE);

       LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_P26);
        p26.setVisibility(GONE);

        LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_P27);
        p27.setVisibility(GONE);
 /*
        LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_P26B);
        p26.setVisibility(GONE);

        LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_P27B);
        p27.setVisibility(GONE);
*/
        LinearLayout p28 = (LinearLayout) activity.findViewById(R.id.survey_layout_P28);
        p28.setVisibility(GONE);

        LinearLayout p29 = (LinearLayout) activity.findViewById(R.id.survey_layout_P29);
        p29.setVisibility(GONE);

        LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_P30);
        p30.setVisibility(GONE);

        LinearLayout p31 = (LinearLayout) activity.findViewById(R.id.survey_layout_P31);
        p31.setVisibility(GONE);

        LinearLayout p32 = (LinearLayout) activity.findViewById(R.id.survey_layout_P32);
        p32.setVisibility(GONE);

        LinearLayout p33 = (LinearLayout) activity.findViewById(R.id.survey_layout_P33);
        p33.setVisibility(GONE);

        LinearLayout p34 = (LinearLayout) activity.findViewById(R.id.survey_layout_P34);
        p34.setVisibility(GONE);

        LinearLayout p35 = (LinearLayout) activity.findViewById(R.id.survey_layout_P35);
        p35.setVisibility(GONE);

        LinearLayout p36 = (LinearLayout) activity.findViewById(R.id.survey_layout_P36);
        p36.setVisibility(GONE);
    }


    @Override
    public int onPreviousPressed(int actual, int anterior) {

        hideQuestions();

        return showQuestion(anterior);
    }


    @Override
    public Questionnaire fillQuest(Questionnaire quest,boolean throwError) throws Exception{
        //P1
        AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_P1);
        String countryText = actvP1.getText().toString();
        String codCountry = dm.getAssociatedCodeFor(countryText, DictionaryManager.COUNTRY);
        String countryLiteral = ((EditText) activity.findViewById(R.id.survey_autoComplete_P1)).getText().toString();
        if(codCountry != null) {
            quest.setCdpaisna(Integer.parseInt(codCountry));
        }else {
            quest.setCdpaisna(-1);
            quest.setCdpaisna_lit(countryLiteral);
        }

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
                } else if(codLoc.contentEquals("99900")){
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
                    selectedCode = 7;
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
                case R.id.survey_radio_P5_optionUber:
                    selectedCode = 24;
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
                    selectedCode = 1;
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


        //P26B
        RadioGroup rgP26B = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26B);
        RadioGroup rgP26Bb = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_P26Bb);
        checkedId = rgP26B.getCheckedRadioButtonId();
        int checkedIdP26Bb = rgP26Bb.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_P26B_option1:
                    selectedCode = 1;
                    if(checkedIdP26Bb>0) {
                        int selectedCodeP26Bb = -1;
                        switch (checkedIdP26Bb) {
                            case R.id.survey_radio_P26Bb_option1:
                                selectedCodeP26Bb = 1;

                                break;
                            case R.id.survey_radio_P26Bb_option2:
                                selectedCodeP26Bb = 2;
                                break;
                            case R.id.survey_radio_P26Bb_option3:
                                selectedCodeP26Bb = 3;
                                break;
                            case R.id.survey_radio_P26Bb_option4:
                                selectedCodeP26Bb = 4;
                                break;
                        }
                        EditText etP26Bb = (EditText) activity.findViewById(R.id.survey_edit_P26Bb);
                        quest.setVecesAerop(Integer.parseInt(etP26Bb.getText().toString()));

                        quest.setModoAerop(selectedCodeP26Bb);

                        if(selectedCodeP26Bb==4){
                            EditText etP26Bbotros = (EditText) activity.findViewById(R.id.survey_edit_P26_otros);
                            quest.setModoAerop_lit(etP26Bbotros.getText().toString());
                        }
                    }
                    break;
                case R.id.survey_radio_P26B_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setUsoAerop(selectedCode);
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


        //P27B
        String textP27B = ((Spinner) activity.findViewById(R.id.survey_spinner_P27B)).getSelectedItem().toString();
        codMotivo = dm.getAssociatedCodeFor(textP27B, DictionaryManager.FLIGHT_REASON);
        if(codMotivo!=null){
            quest.setMotivoAerop(Integer.parseInt(codMotivo));
            if(codMotivo.contentEquals("91")){
                String P27Botros = ((EditText) activity.findViewById(R.id.survey_edit_P27B_especificar)).getText().toString();
                quest.setMotivoAerop_lit(P27Botros);
            }
        } else {
            quest.setMotivoAerop(-1);
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
}

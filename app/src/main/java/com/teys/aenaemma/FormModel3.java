package com.teys.aenaemma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.Layout;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.checked;
import static android.R.attr.content;
import static android.R.attr.textColor;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by cgo on 19/10/16.
 */

public class FormModel3 extends Form {

    public FormModel3(String airportName, Activity surveyAct, DictionaryManager dm) {
        super(airportName, surveyAct, dm);
    }

    @Override
    public int getLayoutId() {
        return R.layout.form_model3;
    }

    @Override
    public void initFormView() {


        if(airportCode.contentEquals("BCN")){
            //P6b
            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6b)).setVisibility(VISIBLE);

            //P10 option crucero
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P10_option6)).setVisibility(VISIBLE);

            //P30b
            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30b)).setVisibility(VISIBLE);


            //P34, P35, P36
            ((LinearLayout) activity.findViewById(R.id.survey_block_M3_P34_P36)).setVisibility(GONE);

            //P12
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12_option6)).setVisibility(GONE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12_option7)).setVisibility(GONE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12_option8)).setVisibility(GONE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12_optionFake)).setVisibility(INVISIBLE);

            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12_option6b)).setVisibility(VISIBLE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12_option7b)).setVisibility(VISIBLE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12_option8b)).setVisibility(VISIBLE);


            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12b_option6)).setVisibility(GONE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12b_option7)).setVisibility(GONE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12b_option8)).setVisibility(GONE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12b_optionFake)).setVisibility(INVISIBLE);

            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12b_option6b)).setVisibility(VISIBLE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12b_option7b)).setVisibility(VISIBLE);
            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P12b_option8b)).setVisibility(VISIBLE);


            ((TextView) activity.findViewById(R.id.survey_text_M3_P12_busPublico)).setText("");
            ((TextView) activity.findViewById(R.id.survey_text_M3_P12_busUrbano)).setText(R.string.survey_text_M3_P12_publicbus);
            ((TextView) activity.findViewById(R.id.survey_text_M3_P12_busInterurbano)).setText(R.string.survey_text_M3_P12_bustransitrenfe);
            ((TextView) activity.findViewById(R.id.survey_text_M3_P12_busLarga)).setText(R.string.survey_text_M3_P12_bustransitterm);
            ((TextView) activity.findViewById(R.id.survey_text_M3_P12_busLanzadera)).setText("");
            ((TextView) activity.findViewById(R.id.survey_text_M3_P12_tren)).setText(R.string.survey_text_M3_P12_cercanias);

        }

        //P1
        AutoCompleteTextView p1autoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P1);
        String[] countries = dm.getStringsArrayFor(DictionaryManager.COUNTRY);
        ArrayAdapter<String> p1adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
        p1autoComplete.setAdapter(p1adapter);

        p1autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry =((TextView) view).getText().toString();
                LinearLayout llp1otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P1_otros);
                String countryCode = dm.getAssociatedCodeFor(selectedCountry, DictionaryManager.COUNTRY);
                if(countryCode!= null && countryCode.contentEquals("999")){
                    llp1otros.setVisibility(VISIBLE);
                } else {
                    llp1otros.setVisibility(GONE);
                }

                //((TextView) activity.findViewById(R.id.survey_text_M3_P1)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

            }
        });


        //AutoComplete Text P2
        AutoCompleteTextView p2autoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2);
//      String[] countries = getResources().getStringArray(R.array.countryStrings);
        ArrayAdapter<String> p2adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
        p2autoComplete.setAdapter(p2adapter);


        p2autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout locationSpain = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_locationSpain);
                LinearLayout otherCountry = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_otherCountry);
                LinearLayout otherLiteral = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_otros);
                LinearLayout provLiteral = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_provLiteral);
                LinearLayout locLiteral = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_locLiteral);

                RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M3_P9_option1);

                String selectedOption = ((TextView) view).getText().toString();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COUNTRY);

                if (selectedCode.contains("724")) {
                    locationSpain.setVisibility(VISIBLE);
                    otherCountry.setVisibility(GONE);
                    otherLiteral.setVisibility(GONE);
                    provLiteral.setVisibility(GONE);
                    locLiteral.setVisibility(GONE);
                    rbResidencia.setEnabled(true);
                } else if (selectedCode.contains("999")) {
                    locationSpain.setVisibility(GONE);
                    provLiteral.setVisibility(GONE);
                    locLiteral.setVisibility(GONE);
                    otherLiteral.setVisibility(VISIBLE);
                    otherCountry.setVisibility(GONE);
                    rbResidencia.setEnabled(false);
                } else if (dm.getAreaStringsResourceId(selectedOption) > 0) {
                    locationSpain.setVisibility(GONE);
                    provLiteral.setVisibility(GONE);
                    otherLiteral.setVisibility(GONE);
                    otherCountry.setVisibility(VISIBLE);
                    rbResidencia.setEnabled(false);
                    setM3P2areaSpinner(selectedOption);
                } else {
                    locationSpain.setVisibility(GONE);
                    provLiteral.setVisibility(GONE);
                    locLiteral.setVisibility(GONE);
                    otherLiteral.setVisibility(GONE);
                    otherCountry.setVisibility(GONE);
                    rbResidencia.setEnabled(false);
                }
                //Change question text color as answered
                //((TextView) activity.findViewById(R.id.survey_text_M3_P2)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });

        AutoCompleteTextView p2provinciaAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2_prov);
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
                        AutoCompleteTextView p2localidadAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2_loc);
                        String[] localidades = dm.getLocalidadesFor(codProvText);


                        RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M3_P9_option1);

                        //TODO esto es un parche para filtra en "Viene de su residencia"

                        if (!codProvText.contentEquals("41") && !codProvText.contentEquals("11") && !codProvText.contentEquals("21") && !codProvText.contentEquals("14") && !codProvText.contentEquals("23") && !codProvText.contentEquals("29") && !codProvText.contentEquals("18") && !codProvText.contentEquals("10") && !codProvText.contentEquals("6")) {
                            rbResidencia.setEnabled(false);
                            rbResidencia.setChecked(false);
                        } else {
                            rbResidencia.setEnabled(true);
                        }


                        if (codProvText.contentEquals("99")) {


                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_provLiteral)).setVisibility(View.VISIBLE);
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P2_prov_lit)).requestFocus();
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_locLiteral)).setVisibility(View.VISIBLE);
                            AutoCompleteTextView locDesconocida = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2_loc);

                            locDesconocida.setText("OTRAS");


                        } else {
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_provLiteral)).setVisibility(View.GONE);
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_locLiteral)).setVisibility(View.GONE);
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
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_locLiteral)).setVisibility(VISIBLE);
                                        ((EditText) activity.findViewById(R.id.survey_edit_M3_P2_loc_lit)).requestFocus();
                                    } else {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_locLiteral)).setVisibility(GONE);
                                    }

                                    if(codLocText!=null && ((airportCode.contentEquals("MAD") && codLocText.contentEquals("28079")) || (airportCode.contentEquals("BCN") && codLocText.contentEquals("8019")))){
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_distrito)).setVisibility(VISIBLE);
                                        setM3distritoSpinner(R.id.survey_spinner_M3_P2_distrito, R.id.survey_layout_M3_P2_disLiteral);
                                    } else {
                                        ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_distrito)).setVisibility(GONE);
                                    }


                                }
                            }
                        });


                    }
                }
            }
        });




        //P3
        /*RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P3);
        rgP3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if (rb.isChecked()) {

                    LinearLayout llP09_P11 = (LinearLayout) activity.findViewById(R.id.survey_block_M3_P09_P14);

                    LinearLayout llP4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4);
                    LinearLayout llP5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P5);
                    LinearLayout llP6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6);
                    LinearLayout llP7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P7);
                    RelativeLayout llP8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P8);
                    switch (checkedId) {
                        case R.id.survey_radio_M3_P3_option1:
                            llP4.setVisibility(VISIBLE);
                            llP5.setVisibility(VISIBLE);
                            llP6.setVisibility(VISIBLE);
                            llP7.setVisibility(VISIBLE);
                            llP8.setVisibility(VISIBLE);
                            llP09_P11.setVisibility(GONE);
                            if(airportCode.contentEquals("BCN")){
                                ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6b)).setVisibility(VISIBLE);
                            }
                            break;
                        case R.id.survey_radio_M3_P3_option2:
                            llP4.setVisibility(GONE);
                            llP5.setVisibility(GONE);
                            llP6.setVisibility(GONE);
                            llP7.setVisibility(GONE);
                            llP8.setVisibility(GONE);
                            llP09_P11.setVisibility(VISIBLE);
                            if(airportCode.contentEquals("BCN")){
                                ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6b)).setVisibility(GONE);
                            }
                            break;
                        default:
                            llP7.setVisibility(VISIBLE);
                            llP8.setVisibility(VISIBLE);
                            llP09_P11.setVisibility(VISIBLE);

                            llP4.setVisibility(VISIBLE);
                            llP5.setVisibility(VISIBLE);
                            llP6.setVisibility(VISIBLE);
                            if(airportCode.contentEquals("BCN")){
                                ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6b)).setVisibility(VISIBLE);
                            }
                            break;
                    }

                    //Change text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P3)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });*/

        //P4
        AutoCompleteTextView p4airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
        String[] airportsShort = dm.getStringsArrayFor(DictionaryManager.AIRPORT);
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
                LinearLayout llP4otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4_otros);
                EditText etP4Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P4_otros);
                if (airportCode != null && airportCode.contentEquals("999")) {
                    llP4otros.setVisibility(VISIBLE);
                    etP4Otros.requestFocus();
                } else {
                    llP4otros.setVisibility(GONE);
                    etP4Otros.setText("");
                }

                //Change question text color as answered
                //((TextView) activity.findViewById(R.id.survey_text_M3_P4)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });



        //P5
        AutoCompleteTextView p5CompanyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P5);
        String[] companiesShort = dm.getStringsArrayFor(DictionaryManager.COMPANY);
        ArrayAdapter<String> p5adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesShort);
        p5CompanyAutoComplete.setAdapter(p5adapter);

        p5CompanyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) ((TextView) view).getText();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COMPANY);

                //Show literal field if selected aiport = others
                LinearLayout llP5otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P5_otros);
                EditText etP5Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P5_otros);
                if (selectedCode != null && selectedCode.contentEquals("999")) {
                    llP5otros.setVisibility(VISIBLE);
                    etP5Otros.requestFocus();
                } else {
                    llP5otros.setVisibility(GONE);
                    etP5Otros.setText("");
                }

                //Change question text color as answered
                //((TextView) activity.findViewById(R.id.survey_text_M3_P5)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


/*        //P6
        RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P6);
        rgP6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if (rb.isChecked()) {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M3_P6)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

*/

        //P7
        String P7textFormated = String.format(activity.getResources().getString(R.string.survey_text_M3_P7).toString(), airportName);
        ((TextView) activity.findViewById(R.id.survey_text_M3_P7))
                .setText(P7textFormated);

/*
        RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P7);
        rgP7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    RelativeLayout llP8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P8);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P7_option2:
                            llP8.setVisibility(GONE);
                            break;
                        default:
                            llP8.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M3_P7)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });
*/

        //P8
        RadioGroup rgP8 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P8);
        rgP8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    EditText etP8others = (EditText) activity.findViewById(R.id.survey_edit_M3_P8_otros);
                    LinearLayout blP09P14 = (LinearLayout) activity.findViewById(R.id.survey_block_M3_P09_P14);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P8_option1:
                            etP8others.setVisibility(GONE);
                            etP8others.setText("");
                            //blP09P14.setVisibility(GONE);
                            break;
                        case R.id.survey_radio_M3_P8_option4:
                           // blP09P14.setVisibility(VISIBLE);
                            etP8others.setVisibility(VISIBLE);
                            etP8others.requestFocus();
                            break;
                        default:
                            etP8others.setVisibility(GONE);
                            etP8others.setText("");
                            //blP09P14.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P8)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //P9

        String P9text = activity.getResources().getString(R.string.survey_text_M3_P9).toString();
        String P9textFormated = String.format(P9text, airportName);
        TextView tvP9 = (TextView) activity.findViewById(R.id.survey_text_M3_P9);
        tvP9.setText(P9textFormated);

        RadioGroup rgP9 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P9);
        rgP9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);

                if(rb.isChecked()){
                    LinearLayout localidadP9 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_localidad);
                    RelativeLayout rlP10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P10);

                    LinearLayout provLit = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_provLiteral);
                    LinearLayout locLit = ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_locLiteral));
                    EditText editProvLit = (EditText) activity.findViewById(R.id.survey_edit_M3_P9_prov_lit);
                    EditText editLocLit = (EditText) activity.findViewById(R.id.survey_edit_M3_P9_loc_lit);
                    AutoCompleteTextView autoProv = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_prov);
                    AutoCompleteTextView autoLoc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_loc);
                    EditText editDisList = (EditText) activity.findViewById(R.id.survey_edit_M3_P9_dis_lit);
                    LinearLayout disLit =  (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_disLiteral);
                    LinearLayout distrito = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_distrito);
                    Spinner spDistrito = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P9_distrito);


                    switch(checkedId){
                        case R.id.survey_radio_M3_P9_option1:
                            localidadP9.setVisibility(View.INVISIBLE);
                            autoProv.setText("");
                            autoLoc.setText("");
                            provLit.setVisibility(GONE);
                            locLit.setVisibility(GONE);
                            editProvLit.setText("");
                            editLocLit.setText("");
                            distrito.setVisibility(GONE);
                            spDistrito.setSelection(0);
                            disLit.setVisibility(GONE);
                            editDisList.setText("");
                            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P21_option2)).setEnabled(false);
                            break;
                        case R.id.survey_radio_M3_P9_option2_localidad:
                            loadM3P9Fields();
                            localidadP9.setVisibility(View.VISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P21_option2)).setEnabled(true);
                            break;
                        default:
                            localidadP9.setVisibility(View.INVISIBLE);
                            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P21_option2)).setEnabled(true);
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P9)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //P10
        RadioGroup rgP10 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P10);
        rgP10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    EditText etP10others = (EditText) activity.findViewById(R.id.survey_edit_M3_P10_otros);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P10_option7:
                            etP10others.setVisibility(VISIBLE);
                            etP10others.requestFocus();
                            break;
                        default:
                            etP10others.setVisibility(View.INVISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P10)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //P11
        RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P11);
        rgP11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    String textP12 = activity.getResources().getString(R.string.survey_text_M3_P12);
                    RadioGroup rgP12b = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12b);
                    TextView tvP12b = (TextView) activity.findViewById(R.id.survey_text_M3_P12b_firstmode);
                    switch(checkedId){
                        case R.id.survey_radio_M3_P11_option1:
                            rgP12b.setVisibility(GONE);
                            tvP12b.setVisibility(GONE);
                            break;
                        default:
                            rgP12b.setVisibility(VISIBLE);
                            tvP12b.setVisibility(VISIBLE);
                            textP12 = activity.getResources().getString(R.string.survey_text_M3_P12b);
                            break;
                    }
                    ((TextView) activity.findViewById(R.id.survey_text_M3_P12)).setText(textP12);

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P11)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M3_P11b_numModos);
        etP11.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M3_P11b_numModos);
                String text = etP11.getText().toString();
                if(text != null && !text.isEmpty()){
                    //Change question text color as answered
                    ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P11_option3)).setChecked(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });




        //P12
        RadioGroup rgP12 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12);
        rgP12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);

                if(rb.isChecked()){

                    RelativeLayout rlP13 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P13);
                    EditText edP12_others = (EditText) activity.findViewById(R.id.survey_edit_M3_P12_others);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P12_option3:
                        case R.id.survey_radio_M3_P12_option4:
                            //rlP13.setVisibility(VISIBLE);
                            edP12_others.setVisibility(INVISIBLE);
                            break;
                        case R.id.survey_radio_M3_P12_option11:
                            rlP13.setVisibility(GONE);
                            edP12_others.setVisibility(VISIBLE);
                            break;
                        case R.id.survey_radio_M3_P12_optionFake:
                            Toast toast = Toast.makeText(activity, "Valor no permitido! Se debe indicar el medio de transporte para ACCEDER al aeropuerto",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            rlP13.setVisibility(GONE);
                            edP12_others.setVisibility(INVISIBLE);
                            break;
                        default:
                            rlP13.setVisibility(GONE);
                            edP12_others.setVisibility(INVISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P12)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //P13
        RadioGroup rgP13 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P13);
        rgP13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP13b = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P13b);
                    switch(checkedId){
                        case R.id.survey_radio_M3_P13_option6:
                            llP13b.setVisibility(VISIBLE);
                            break;
                        default:
                            llP13b.setVisibility(GONE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P13)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //P14
        EditText etP14 = (EditText) activity.findViewById(R.id.survey_edit_M3_P14);
        etP14.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP14 = (EditText) activity.findViewById(R.id.survey_edit_M3_P14);
                String text = etP14.getText().toString();
                if(text != null && !text.isEmpty()){
                    //Change question text color as answered
                    //TextView tvP14 = (TextView) activity.findViewById(R.id.survey_text_M3_P14);
                    //(tvP14).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


        //P15
        EditText etP15hora = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora);
        etP15hora.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP15hora = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora);
                String textHora = etP15hora.getText().toString();
                if(textHora != null && !textHora.isEmpty()){
                    int hora = Integer.parseInt(textHora);
                    if(hora<0 || hora>23){
                        etP15hora.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        etP15hora.setError("El campo hora debe tener un valor comprendido entre 00 y 23");

                    } else {
                        etP15hora.setBackgroundColor(Color.WHITE);
                    }

                    EditText etP15min = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos);
                    String textMin = etP15min.getText().toString();
                    if(textMin != null && !textMin.isEmpty()) {
                        //Change question text color as answered
                        //TextView tvP15 = (TextView) activity.findViewById(R.id.survey_text_M3_P15);
                        //(tvP15).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }

                checkValidTimeM3P15();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


        EditText etP15min = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos);
        etP15min.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                checkValidTimeM3P15();

                EditText etP15min = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos);
                String textMin = etP15min.getText().toString();
                if(textMin != null && !textMin.isEmpty()) {
                    int min = Integer.parseInt(textMin);
                    if (min < 0 || min > 59) {
                        etP15min.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        etP15min.setError("El campo minutos debe tener un valor comprendido entre 00 y 59");
                    } else {
                        //etP15min.setBackgroundColor(Color.WHITE);
                    }

                    EditText etP15hora = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora);
                    String textHora = etP15hora.getText().toString();
                    if (textHora != null && !textHora.isEmpty()) {
                        //Change question text color as answered
                        //TextView tvP15 = (TextView) activity.findViewById(R.id.survey_text_M3_P15);
                        //(tvP15).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }
                //TODO check arrival time before quest time

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });




        //P16
        AutoCompleteTextView P16airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
//        String[] airports = dm.getStringsArrayFor(DictionaryManager.AIRPORT);
        ArrayAdapter<String> P16airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsShort);
        P16airportAutoComplete.setAdapter(P16airportadapter);

        P16airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Update question text in P11
                TextView tvSelected = (TextView) view;
                String airport = tvSelected.getText().toString();
                String P18text = activity.getResources().getString(R.string.survey_text_M3_P18).toString();
                String P18textFormated = String.format(P18text,airport);
                TextView tvP18 = (TextView) activity.findViewById(R.id.survey_text_M3_P18);
                tvP18.setText(P18textFormated);

                //Show literal field if selected aiport = others
                EditText etP16Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P16_otros);
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT);
                LinearLayout llP16otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P16_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP16otros.setVisibility(VISIBLE);
                    etP16Otros.requestFocus();
                } else {
                    llP16otros.setVisibility(GONE);
                    etP16Otros.setText("");
                }

                //Check destination airport is different from origin
                AutoCompleteTextView P16airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
                P16airportAutoComplete.setBackgroundColor(Color.WHITE);
                if(airportCode!= null && !airportCode.isEmpty()){
                    AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
                    String airportOriginName =  actvP4.getText().toString();
                    String airportOriginCode =dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT);
                    if(airportOriginCode!= null && !airportOriginCode.isEmpty()){
                        if(airportCode.contentEquals(airportOriginCode)){
                            P16airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            P16airportAutoComplete.setError("El aeropuerto de destino no puede ser el mismo que el de origen");
                        }
                    }
                }

                //Change question text color as answered
                //((TextView) activity.findViewById(R.id.survey_text_M3_P16)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });


        //P17
        AutoCompleteTextView P17CompanyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P17_company);
        //companiesShort = dm.getStringsArrayFor(DictionaryManager.COMPANY);
        ArrayAdapter<String> P17adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesShort);
        P17CompanyAutoComplete.setAdapter(P17adapter);

        P17CompanyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) ((TextView) view).getText();
                String selectedCode = dm.getAssociatedCodeFor(selectedOption, DictionaryManager.COMPANY);
                ((TextView) activity.findViewById(R.id.survey_text_M3_P17_companyCode))
                        .setText(selectedCode+"-");

                //Show literal field if selected aiport = others
                LinearLayout llP10otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P17_otros);
                if(selectedCode!=null && selectedCode.contentEquals("999")){
                    llP10otros.setVisibility(VISIBLE);
                } else {
                    llP10otros.setVisibility(GONE);
                }

                String numVuelo = ((EditText) activity.findViewById(R.id.survey_edit_M3_P17)).getText().toString();
                if(numVuelo!= null && ! numVuelo.isEmpty()) {
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P17)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });





        EditText etP17 = (EditText) activity.findViewById(R.id.survey_edit_M3_P17);
        etP17.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP17 = (EditText) activity.findViewById(R.id.survey_edit_M3_P17);
                String numVuelo = etP17.getText().toString();
                if(numVuelo != null && !numVuelo.isEmpty()){

                    AutoCompleteTextView actvP17 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P17_company);
                    String company = actvP17.getText().toString();
                    if(company != null && !company.isEmpty()) {
                        //Change question text color as answered
                        //((TextView) activity.findViewById(R.id.survey_text_M3_P17)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });






        //P18
        RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P18);
        rgP18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP18b = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18b);
                    LinearLayout llP16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19);
                    AutoCompleteTextView actvP18b = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P18b);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P18_option1:
                            llP18b.setVisibility(GONE);
                            actvP18b.setText("");
                            //llP16.setVisibility(GONE);
                            break;
                        default:
                            llP18b.setVisibility(VISIBLE);
                            actvP18b.requestFocus();
                            //llP16.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P18)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });



        //AutoComplete P18b
        AutoCompleteTextView P18bcompanyAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P18b);
        String[] companiesLong = dm.getStringsArrayFor(DictionaryManager.COMPANY_LONG);
        ArrayAdapter<String> P18bcompanyadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, companiesLong);
        P18bcompanyAutoComplete.setAdapter(P18bcompanyadapter);


        P18bcompanyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Show literal field if selected aiport = others
                String company = ((TextView) view).getText().toString();
                String companyCode = dm.getAssociatedCodeFor(company, DictionaryManager.COMPANY_LONG);
                LinearLayout llP18botros = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18b_otros);
                if(companyCode!=null && companyCode.contentEquals("999")){
                    llP18botros.setVisibility(VISIBLE);
                } else {
                    llP18botros.setVisibility(GONE);
                }


                //Change question text color as answered
                //((TextView) activity.findViewById(R.id.survey_text_M3_P18b)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });





        //AutoComplete P19
        AutoCompleteTextView P19airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P19);
        String[] airportsLong = dm.getStringsArrayFor(DictionaryManager.AIRPORT_LONG);
        ArrayAdapter<String> P19airportadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, airportsLong);
        P19airportAutoComplete.setAdapter(P19airportadapter);

        P19airportAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Show literal field if selected aiport = others
                String airport = ((TextView) view).getText().toString();
                String airportCode = dm.getAssociatedCodeFor(airport, DictionaryManager.AIRPORT_LONG);
                LinearLayout llP19otros = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19_otros);
                if(airportCode!=null && airportCode.contentEquals("999")){
                    llP19otros.setVisibility(VISIBLE);
                } else {
                    llP19otros.setVisibility(GONE);
                }

                //Check destination airport is different from origin
                AutoCompleteTextView P19airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P19);
                P19airportAutoComplete.setBackgroundColor(Color.WHITE);
                if(airportCode!= null && !airportCode.isEmpty()){
                    AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
                    String airportOriginName = actvP4.getText().toString();
                    String airportOriginCode = dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT_LONG);
                    if(airportOriginCode!= null && !airportOriginCode.isEmpty()){
                        if(airportCode.contentEquals(airportOriginCode)){
                            P19airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            Toast toast = Toast.makeText(activity, "El aeropuerto de finalización del viaje (P19) no puede ser el mismo que el de origen (P4)",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }

                //AutoCompleteTextView P19airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P19);
                P19airportAutoComplete.setBackgroundColor(Color.WHITE);
                if(airportCode!= null && !airportCode.isEmpty()){
                    AutoCompleteTextView actvP16 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
                    String airportOriginName = actvP16.getText().toString();
                    String airportOriginCode = dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT);
                    if(airportOriginCode!= null && !airportOriginCode.isEmpty()){
                        if(airportCode.contentEquals(airportOriginCode)){
                            P19airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            P19airportAutoComplete.setError("El aeropuerto de finalización del viaje no puede ser el mismo que el de origen");
                            P19airportAutoComplete.requestFocus();
                        }
                    }
                }

                //Change question text color as answered
                //((TextView) activity.findViewById(R.id.survey_text_M3_P19)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
            }
        });




        //P20
        Spinner P20spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P20);
        ArrayAdapter<CharSequence> P20Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p14strings,
                        R.layout.selection_spinner_item);
        P20Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        P20spinner.setAdapter(P20Adapter);

        P20spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String selection = tv.getText().toString();

                String code = dm.getAssociatedCodeFor(selection,DictionaryManager.TRAVEL_REASON);
                if(code!= null && !code.contentEquals("0")) {
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P20)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

  //EJG              checkM3P39Visibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //P21
        RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P21);
        rgP21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText etP21 = (EditText) activity.findViewById(R.id.survey_edit_M3_P21b);
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if (rb.isChecked()) {
                    TextView tvP21a = (TextView) activity.findViewById(R.id.survey_text_M3_P21a);
                    TextView tvP21b = (TextView) activity.findViewById(R.id.survey_text_M3_P21b);
                    LinearLayout llP21num = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P21nums);
                    switch (checkedId) {
                        case R.id.survey_radio_M3_P21_option1:
                            tvP21a.setVisibility(VISIBLE);
                            tvP21b.setVisibility(GONE);
                            llP21num.setVisibility(VISIBLE);
                            etP21.requestFocus();
                            break;
                        case R.id.survey_radio_M3_P21_option2:
                            tvP21a.setVisibility(GONE);
                            tvP21b.setVisibility(VISIBLE);
                            llP21num.setVisibility(VISIBLE);
                            etP21.requestFocus();
                            break;
                        default:
                            tvP21a.setVisibility(View.INVISIBLE);
                            tvP21b.setVisibility(View.INVISIBLE);
                            llP21num.setVisibility(View.INVISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P21)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                    if (checkedId == R.id.survey_radio_M3_P21_option1) {
                        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P3);
                        int selP3 = rgP3.getCheckedRadioButtonId();
                        RadioGroup rgP9 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P9);
                        int selP9 = rgP9.getCheckedRadioButtonId();

                        if ((selP3 != R.id.survey_radio_M3_P3_option1) && (selP9 == R.id.survey_radio_M3_P9_option2_localidad)) {
                            ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P21_option1))
                                    .setTextColor(activity.getResources().getColor(R.color.aenaRed));
                            ((TextView) activity.findViewById(R.id.survey_text_M3_P21_error)).setVisibility(VISIBLE);
                        }
                    } else {
                        ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P21_option1))
                                .setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                        ((TextView) activity.findViewById(R.id.survey_text_M3_P21_error)).setVisibility(GONE);
                    }
                }
            }
        });


        EditText etP21 = (EditText) activity.findViewById(R.id.survey_edit_M3_P21b);
        etP21.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP21 = (EditText) activity.findViewById(R.id.survey_edit_M3_P21b);
                String num = etP21.getText().toString();
                if(num != null && !num.isEmpty()){
                    int numDias = Integer.parseInt(num);






                    int spP20 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P20)).getSelectedItemPosition();

                    String textP20 = Integer.toString(spP20);
                    String textP21 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P21b)).getText().toString();

                    boolean error = false;
                    if(textP20 != null && !textP20.isEmpty()){
                        if(textP21 != null && !textP21.isEmpty()){
                            int numP20 = Integer.parseInt(textP20);
                            int numP21 = Integer.parseInt(textP21);

                            switch(numP20){
                                case 4:
                                    if(numDias<=90){
                                        etP21.setError("Es necesario revisar el propósito principal del viaje porque no es coherente con el número de días indicados");
                                        etP21.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP21.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                                case 18:
                                    if(numDias>7){
                                        etP21.setError("Es necesario revisar el Propósito principal del viaje porque no es coherente con el número de días indicados");
                                        etP21.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP21.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                                case 19:
                                    if(numDias<=7){
                                        etP21.setError("Es necesario revisar el Propósito principal del viaje porque no es coherente con el número de días indicados");
                                        etP21.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP21.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                            }

                        }
                    }



                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_edit_M3_P21b)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    etP21.setBackgroundColor(Color.WHITE);
                    ((TextView) activity.findViewById(R.id.survey_edit_M3_P21b)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) { }
        });




        //P22
        RadioGroup rgP22 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22);
        rgP22.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP23 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P23);
                    LinearLayout llP24 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P24);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P22_option1:
                            //llP23.setVisibility(GONE);
                            //llP24.setVisibility(GONE);
                            break;
                        default:
                            //llP23.setVisibility(VISIBLE);
                            //llP24.setVisibility(VISIBLE);
                            break;
                    }

                    //Update P29 selection to recalculate P30 visibility
                    int checkP29 = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P29)).getCheckedRadioButtonId();
                    if(checkP29>0){
                        ((RadioButton) activity.findViewById(checkP29)).setChecked(true);
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P22)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        EditText etP22num = (EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas);
        etP22num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String numPersonas  = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas))
                        .getText().toString();
                if(numPersonas != null && !numPersonas.isEmpty()){
                    ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P22_option3)).setChecked(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P23
        EditText etP23 = (EditText) activity.findViewById(R.id.survey_edit_M3_P23);
        etP23.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP23 = (EditText) activity.findViewById(R.id.survey_edit_M3_P23);
                String num = etP23.getText().toString();
                if(num != null && !num.isEmpty()){

                    int numMenores = Integer.parseInt(num);

                    int numPersonas = 0;
                    RadioGroup rgP22 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22);
                    int selOpt = rgP22.getCheckedRadioButtonId();
                    if(selOpt != R.id.survey_radio_M3_P22_option1){
                        String numP = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).getText().toString();
                        if(numP != null && !numP.isEmpty()){
                            numPersonas = Integer.parseInt(numP);
                        }
                    } else {
                        numPersonas = 1;
                    }

                    if(numMenores>numPersonas){
                        etP23.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        etP23.setError("El número de menores en el grupo no puede ser superior al número total de personas");
                    } else{
                        etP23.setBackgroundColor(Color.WHITE);
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P23)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M3_P23)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //P24
        RadioGroup rgP24 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P24);
        rgP24.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P24)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });



        //P25
        EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M3_P25_numDias);
        etP25.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M3_P25_numDias);
                String numDias = etP25.getText().toString();
                if(numDias != null && !numDias.isEmpty()){

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P25)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });







        //P26
        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P26);
        rgP26.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P26)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });




        //P27
        RadioGroup rgP27 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P27);
        rgP27.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP28 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P28);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P27_option1:
                            //llP28.setVisibility(GONE);
                            break;
                        default:
                            //llP28.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P27)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        EditText etP27num = (EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes);
        etP27num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String numViajes  = ((EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes))
                        .getText().toString();
                if(numViajes != null && !numViajes.isEmpty()){
                    ((RadioButton) activity.findViewById(R.id.survey_radio_M3_P27_option2)).setChecked(true);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });




        //P28
        EditText etP28 = (EditText) activity.findViewById(R.id.survey_edit_M3_P28_numViajes);
        etP28.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP28 = (EditText) activity.findViewById(R.id.survey_edit_M3_P28_numViajes);
                String numViajes = etP28.getText().toString();
                if(numViajes != null && !numViajes.isEmpty()){
                    int numViajesMismaRuta = Integer.parseInt(numViajes);

                    int numTotalViajes = 0;
                    RadioGroup rgP27 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P27);
                    int selOpt = rgP27.getCheckedRadioButtonId();
                    if(selOpt != R.id.survey_radio_M3_P27_option1){
                        String num = ((EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes)).getText().toString();
                        if(num != null && !num.isEmpty()){
                            numTotalViajes = Integer.parseInt(num);
                        }
                    }

                    if(numViajesMismaRuta>numTotalViajes){
                        etP28.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                        etP28.setError("El número de viajes en la misma ruta no puede ser superior al número total de viajes");
                    } else{
                        etP28.setBackgroundColor(Color.WHITE);
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P28)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    ((TextView) activity.findViewById(R.id.survey_text_M3_P28)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });




        //P29
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P29);
        rgP29.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP29_num = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P29_num);
                    LinearLayout llP30 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30);
                    EditText etP29 = (EditText) activity.findViewById(R.id.survey_edit_M3_P29b_numBultos);
                    EditText etP30 = (EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas);
                    int selectP22 = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22)).getCheckedRadioButtonId();
                    switch (checkedId){
                        case R.id.survey_radio_M3_P29_option1:
                            llP29_num.setVisibility(VISIBLE);
                            etP29.requestFocus();
                            //llP30.setVisibility(VISIBLE);
                            if(selectP22==R.id.survey_radio_M3_P22_option1){
                                //llP30.setVisibility(GONE);
                                etP30.setText("1");
                            }
                            /*if(airportCode.contentEquals("BCN")){
                                ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30b)).setVisibility(VISIBLE);
                            }*/
                            break;
                        case R.id.survey_radio_M3_P29_option2:
                            llP29_num.setVisibility(View.INVISIBLE);
                            //llP30.setVisibility(GONE);
                            etP29.setText("");
                            /*if(airportCode.contentEquals("BCN")){
                                ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30b)).setVisibility(GONE);
                            }*/
                            break;
                        default:
                            llP29_num.setVisibility(View.INVISIBLE);
                            //llP30.setVisibility(VISIBLE);
                            /*if(airportCode.contentEquals("BCN")){
                                ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6b)).setVisibility(VISIBLE);
                            }*/
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P29)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });





        //P30
        EditText etP30 = (EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas);
        etP30.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP30 = (EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas);
                String num = etP30.getText().toString();
                if(num != null && !num.isEmpty()){
                    int numPers = Integer.parseInt(num);




                    //Error P30>P19
                    String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).getText().toString();
                    int selectedOption = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22)).getCheckedRadioButtonId();
                    if(selectedOption == R.id.survey_radio_M3_P22_option1){
                        textP19 = "1";
                    }
                    String textP30 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas)).getText().toString();


                    boolean error = false;
                    if(textP19 != null && !textP19.isEmpty()){
                        if(textP30 != null && !textP30.isEmpty()){
                            int numP19 = Integer.parseInt(textP19);
                            int numP30 = Integer.parseInt(textP30);
                            if(numP30>numP19){
                                etP30.setError("El número de personas con bultos no puede ser mayor que el número total de personas del grupo");
                                etP30.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                error = true;
                            } else {
                                etP30.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }

//                        if(!error) {
//                            //Warning P30>P29b
//                            int numBultos = 0;
//                            RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P29);
//                            int selOpt = rgP29.getCheckedRadioButtonId();
//                            if (selOpt == R.id.survey_radio_M3_P29_option1) {
//                                String numP = ((EditText) activity.findViewById(R.id.survey_edit_M3_P29b_numBultos)).getText().toString();
//                                if (numP != null && !numP.isEmpty()) {
//                                    numBultos = Integer.parseInt(numP);
//                                }
//                            }
//
//                            if (numPers > numBultos) {
//                                etP30.setBackgroundColor(activity.getResources().getColor(R.color.aenaOrange));
//                                Toast toast = Toast.makeText(activity, "El número de personas (P30) es superior al número total de bultos (P29). Verificar",
//                                        Toast.LENGTH_LONG);
//                                toast.show();
//                            } else {
//                                etP30.setBackgroundColor(Color.WHITE);
//                            }
//                        }


                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P30)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    etP30.setBackgroundColor(Color.WHITE);
                    ((TextView) activity.findViewById(R.id.survey_text_M3_P30)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) { }
        });



        //P31
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P31);
        rgP31.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P31)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });




        //P32
        RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P32);
        rgP32.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    TextView tvP32a = (TextView) activity.findViewById(R.id.survey_text_M3_P32a);
                    EditText etP32a = (EditText) activity.findViewById(R.id.survey_edit_M3_P32a);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P32_option1:
                            tvP32a.setVisibility(VISIBLE);
                            etP32a.setVisibility(VISIBLE);
                            etP32a.requestFocus();
                            break;
                        default:
                            tvP32a.setVisibility(View.INVISIBLE);
                            etP32a.setVisibility(View.INVISIBLE);
                            etP32a.setText("");
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P32)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });

        //P33
        RadioGroup rgP33 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P33);
        rgP33.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    LinearLayout llP33a = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P33a);
                    EditText etP33a = (EditText) activity.findViewById(R.id.survey_edit_M3_P33a);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P33_option1:
                            llP33a.setVisibility(VISIBLE);
                            etP33a.requestFocus();
                            break;
                        default:
                            llP33a.setVisibility(GONE);
                            etP33a.setText("");
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P33)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }
            }
        });


        //Spinners P33b
        Spinner P33bitem1spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item1);
        ArrayAdapter<CharSequence> P33bitem1Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        P33bitem1Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        P33bitem1spinner.setAdapter(P33bitem1Adapter);

        Spinner P33biteM3spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_iteM3);
        ArrayAdapter<CharSequence> P33biteM3Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        P33biteM3Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        P33biteM3spinner.setAdapter(P33biteM3Adapter);

        Spinner P33bitem3spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item3);
        ArrayAdapter<CharSequence> P33bitem3Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        P33bitem3Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        P33bitem3spinner.setAdapter(P33bitem3Adapter);

        Spinner P33bitem4spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item4);
        ArrayAdapter<CharSequence> P33bitem4Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        P33bitem4Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        P33bitem4spinner.setAdapter(P33bitem4Adapter);

        Spinner P33bitem5spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item5);
        ArrayAdapter<CharSequence> P33bitem5Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p30bstrings,
                        R.layout.selection_spinner_item);
        P33bitem4Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        P33bitem5spinner.setAdapter(P33bitem5Adapter);


        //P34
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P34);
        rgP34.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){

                    RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P35);
                    RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P36);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P34_option2:
                            //rgP35.setVisibility(GONE);
                           // rgP36.setVisibility(GONE);
                            break;
                        default:
                            //rgP35.setVisibility(VISIBLE);
                            //rgP36.setVisibility(VISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P34)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

            }
        });


        //P35
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P35);
        rgP35.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P36);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P35_option1:
                            //rgP36.setVisibility(GONE);
                            break;
                        default:
                            //rgP36.setVisibility(VISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P35)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

            }
        });



        //P36
        RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P36);
        rgP36.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P36)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

            }
        });


        //P37
        RadioGroup rgP37 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P37);
        rgP37.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    RadioGroup rgP38 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P38);
                    switch (checkedId){
                        case R.id.survey_radio_M3_P37_option1:
                            //rgP38.setVisibility(VISIBLE);;
                            break;
                        default:
                            //rgP38.setVisibility(GONE);
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P37)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    //Update P39
                    //checkM3P39Visibility();
                }

            }
        });




        //P38
        RadioGroup rgP38 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P38);
        rgP38.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P38)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //Spinnner P39
        Spinner P39spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P39);
        ArrayAdapter<CharSequence> P39Adapter =
                ArrayAdapter.createFromResource(activity,
                        R.array.p33strings,
                        R.layout.selection_spinner_item);
        P39Adapter.setDropDownViewResource(R.layout.selection_spinner_item);
        P39spinner.setAdapter(P39Adapter);

        P39spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout llP39others = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P39_others);

                TextView tvSelected = (TextView) view;
                String[] possibleValues = activity.getResources().getStringArray(R.array.p33strings);
                if(tvSelected.getText().toString().contentEquals(possibleValues[possibleValues.length-1])){
                    llP39others.setVisibility(VISIBLE);
                } else {
                    llP39others.setVisibility(GONE);
                }
                if(possibleValues!=null && !tvSelected.getText().toString().contentEquals(possibleValues[0])){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P39)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //P40
        RadioGroup rgP40 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P40);
        rgP40.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P40)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //P41
        RadioGroup rgP41 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P41);
        rgP41.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P41)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });


        //P42
        RadioGroup rgP42 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P42);
        rgP42.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                if(rb.isChecked()){
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M3_P42)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

                }
            }
        });



        //End of InitForm
    }



    private void setM3P2areaSpinner(String selectedOption) {
        int resourceToLoad = dm.getAreaStringsResourceId(selectedOption);
        final Spinner p2spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P2_area);
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
                EditText P2regionLit = (EditText) activity.findViewById(R.id.survey_edit_M3_P2_region_lit);
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


    private void setM3distritoSpinner(int spinnerResourceId, final int layoutLiteralId) {
        int resourceToLoad = dm.getStringsResourceIdForDistrito();
        Spinner p2spinner = (Spinner) activity.findViewById(spinnerResourceId);
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

        p2spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout llothers = (LinearLayout) activity.findViewById(layoutLiteralId);

                TextView tvSelected = (TextView) view;
                String selectCode = dm.getAssociatedCodeFor(tvSelected.getText().toString(), DictionaryManager.DISTRITO);
                if(selectCode!= null && selectCode.contentEquals("99")){
                    llothers.setVisibility(VISIBLE);
                } else {
                    llothers.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return;
    }



    private void loadM3P9Fields() {

        AutoCompleteTextView P9provinciaAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_prov);
        String[] provincias = dm.getStringsArrayFor(DictionaryManager.PROVINCIA_PROC);
        ArrayAdapter<String> P9provinciaadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, provincias);
        P9provinciaAutoComplete.setAdapter(P9provinciaadapter);

        P9provinciaAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProv = ((TextView) view).getText().toString();
                if(selectedProv!=null && !selectedProv.isEmpty()) {
                    String codProvText = dm.getAssociatedCodeFor(selectedProv, DictionaryManager.PROVINCIA_PROC);
                    if (codProvText != null) {
                        AutoCompleteTextView P9localidadAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_loc);
                        String[] localidades = dm.getLocalidadesProcFor(codProvText);
                        if (codProvText.contentEquals("99")) {
                            P9localidadAutoComplete.setText(localidades[0]);

                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_provLiteral)).setVisibility(View.VISIBLE);
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P9_prov_lit)).requestFocus();
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_locLiteral)).setVisibility(View.VISIBLE);
                            AutoCompleteTextView locDesconocida = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_loc);

                            locDesconocida.setText("OTRAS");
                        } else {
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_provLiteral)).setVisibility(View.GONE);
                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_locLiteral)).setVisibility(View.GONE);

                            ArrayAdapter<String> P9localidadadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, localidades);
                            P9localidadAutoComplete.setAdapter(P9localidadadapter);

                            P9localidadAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String selectedLoc = ((TextView) view).getText().toString();
                                    if (selectedLoc != null && !selectedLoc.isEmpty()) {
                                        String codLocText = dm.getAssociatedCodeFor(selectedLoc, DictionaryManager.LOCALIDAD_PROC);
                                        if (codLocText != null && codLocText.contentEquals("99900")) {
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_locLiteral)).setVisibility(View.VISIBLE);
                                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P9_loc_lit)).requestFocus();
                                        } else {
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_locLiteral)).setVisibility(View.GONE);
                                        }



                                        if(codLocText!=null && ((airportCode.contentEquals("MAD") && codLocText.contentEquals("28079")) || (airportCode.contentEquals("BCN") && codLocText.contentEquals("8019")))){
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_distrito)).setVisibility(VISIBLE);
                                            setM3distritoSpinner(R.id.survey_spinner_M3_P9_distrito, R.id.survey_layout_M3_P9_disLiteral);
                                        } else {
                                            ((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_distrito)).setVisibility(GONE);
                                        }
                                    }




                                    //Update P10 question text including selected Localidad
                                    String P10text = activity.getResources().getString(R.string.survey_text_M3_P10).toString();
                                    String P10textFormated = String.format(P10text, selectedLoc);
                                    TextView tvP10 = (TextView) activity.findViewById(R.id.survey_text_M3_P10);
                                    tvP10.setText(P10textFormated);
                                }
                            });
                        }
                    }
                }
            }
        });


    }



    private void checkValidTimeM3P15() {
        String textHoraLlegada = ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora)).getText().toString();
        String textMinLlegada = ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos)).getText().toString();

        Calendar llegadaTime = Calendar.getInstance();
        if(textHoraLlegada!= null && !textHoraLlegada.isEmpty()){
            if(textMinLlegada!= null && !textMinLlegada.isEmpty()){

                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(textHoraLlegada));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(textMinLlegada));
                llegadaTime.set(Calendar.SECOND, 0);
            } else {
                ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos))
                        .setBackgroundColor(Color.WHITE);
                return;
            }
        } else {
            ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora))
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
            ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora))
                    .setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
            ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos))
                    .setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
            Toast toast = Toast.makeText(activity, "La hora de llegada no puede ser posterior a la hora de realización de la encuesta",
                    Toast.LENGTH_LONG);
            toast.show();
        } else {
            ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora))
                    .setBackgroundColor(Color.WHITE);
            ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos))
                    .setBackgroundColor(Color.WHITE);
        }

    }



    private void checkM3P39Visibility(){
        LinearLayout llP39 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P39);
        llP39.setVisibility(VISIBLE);

        String selection = (String) ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P20)).getSelectedItem();
        String code = dm.getAssociatedCodeFor(selection,DictionaryManager.TRAVEL_REASON);
        int optionValue = 0;
        if(code != null && !code.isEmpty()) {
            // Not business passengers
            optionValue = Integer.parseInt(code);

        }

        int selOpt = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P37)).getCheckedRadioButtonId();

        if (optionValue > 200 || (selOpt!=R.id.survey_radio_M3_P37_option1 && selOpt >0)) {
            llP39.setVisibility(GONE);
        }

    }



    public boolean checkQuestion(int check) {

        AutoCompleteTextView actvAeropuertoOrigen = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
        String selectedAirport = actvAeropuertoOrigen.getText().toString();
        String selCode ="";
        int selOpt=-1;
        RadioGroup rgP22 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22);
        RadioGroup rgP27 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P27);
        LinearLayout p4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4);
        LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P16);


        switch (check) {



            case 1:
                AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P1);
                String p1Text = actvP1.getText().toString();

                if (p1Text == null || p1Text.isEmpty()) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el país de NACIONALIDAD", Toast.LENGTH_LONG);
                    toast.show();
                    actvP1.requestFocus();
                    return false;
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P1_otros)).getVisibility()==VISIBLE) {
                    EditText etP1_otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P1_otros);
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
                AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2);
                String p2Text = actvP2.getText().toString();
                if(p2Text==null || p2Text.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el país de RESIDENCIA", Toast.LENGTH_LONG);
                    toast.show();
                    actvP2.requestFocus();
                    return false;
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_otros)).getVisibility()==VISIBLE) {
                    EditText etP2_otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P2_otros);
                    String txtP2_otros = etP2_otros.getText().toString();

                    if (txtP2_otros == null || txtP2_otros.isEmpty()) {
                        etP2_otros.setError("Se debe especificar otro país de residencia");
                        etP2_otros.requestFocus();
                        return false;
                    }
                }

                //P2Localidad != null
                String codCountry = dm.getAssociatedCodeFor(p2Text, DictionaryManager.COUNTRY);
                if(codCountry!=null && codCountry.contentEquals("724")){
                    AutoCompleteTextView actvP2_loc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2_loc);
                    String codLoc = dm.getAssociatedCodeFor(actvP2_loc.getText().toString(), DictionaryManager.LOCALIDAD);
                    if(codLoc == null || codLoc.isEmpty()) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la LOCALIDAD de RESIDENCIA del pasajero", Toast.LENGTH_LONG);
                        toast.show();
                        actvP2_loc.requestFocus();
                        return false;
                    }
                }


                //P2Area != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_otherCountry)).getVisibility()==VISIBLE) {
                    int spinP2_area = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P2_area)).getSelectedItemPosition();

                    if (spinP2_area == 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el ÁREA o REGIÓN de RESIDENCIA del pasajero", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }


                //P2Distrito != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_distrito)).getVisibility()==VISIBLE) {
                    int spinP2_distrito = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P2_distrito)).getSelectedItemPosition();
                    String spinP2_dist= Integer.toString(spinP2_distrito);
                    Toast toas = Toast.makeText(activity, spinP2_dist, Toast.LENGTH_LONG);
                    toas.show();
                    if (spinP2_distrito == 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el DISTRITO de RESIDENCIA del pasajero", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }

                }
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_otherCountry)).getVisibility()==VISIBLE) {
                    if (((EditText) activity.findViewById(R.id.survey_edit_M3_P2_region_lit)).getVisibility() == VISIBLE) {
                        EditText etP2_regionLit = (EditText) activity.findViewById(R.id.survey_edit_M3_P2_region_lit);
                        String textP2_regionLit = etP2_regionLit.getText().toString();
                        if (textP2_regionLit == null || textP2_regionLit.isEmpty()) {
                            etP2_regionLit.setError("Se debe especificar otra área o región");
                            etP2_regionLit.requestFocus();
                            return false;
                        }
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2_disLiteral)).getVisibility() == VISIBLE) {
                    EditText etP2_disLit = (EditText) activity.findViewById(R.id.survey_edit_M3_P2_dis_lit);
                    String textP2disLit = etP2_disLit.getText().toString();
                    if (textP2disLit == null || textP2disLit.isEmpty()) {
                        etP2_disLit.setError("Se debe especificar la calle, hotel o similar");
                        etP2_disLit.requestFocus();
                        return false;
                    }
                }

                break;

            case 3:
                //P3 Conexion != null
                RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P3);
                selOpt = rgP3.getCheckedRadioButtonId();
                if (selOpt <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar si el pasajero procede de CONEXIÓN", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
                break;

            case 4:
                //P4
                selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
                if (selCode == null) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el aeropuerto de origen", Toast.LENGTH_LONG);
                    toast.show();
                    actvAeropuertoOrigen.requestFocus();
                    return false;
                }

                //P4 Aeropuerto origen != aeropuerto encuesta
                if (selectedAirport != null && !selectedAirport.isEmpty()) {
                    if (selCode != null && selCode.contentEquals(airportCode)) {
                        Toast toast = Toast.makeText(activity, "El aeropuerto de origen no puede ser igual que el actual", Toast.LENGTH_LONG);
                        toast.show();
                        actvAeropuertoOrigen.requestFocus();
                        return false;
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4_otros)).getVisibility() == VISIBLE) {
                EditText etP4Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P4_otros);
                    String textP4Otros = etP4Otros.getText().toString();
                    if (textP4Otros == null || textP4Otros.isEmpty()) {
                        etP4Otros.setError("Se debe especificar el nombre de otro aeropuerto");
                        etP4Otros.requestFocus();
                        return false;
                    }
                }
                break;

            case 5:
                //P5
                AutoCompleteTextView actv = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P5);
                String selected = actv.getText().toString();

                if (selected == null || selected.isEmpty()) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar la COMPAÑÍA AÉREA con la que ha volado", Toast.LENGTH_LONG);
                    toast.show();
                    actv.requestFocus();
                    return false;
                } else {
                    selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
                    if (selCode == null ) {
                        Toast toast = Toast.makeText(activity, "El campo de COMPAÑÍA AÉREA con la que ha volado no es válido", Toast.LENGTH_LONG);
                        toast.show();
                        actv.requestFocus();
                        return false;
                    }
                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P5_otros)).getVisibility() == VISIBLE) {
                    EditText etP5Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P5_otros);
                    String textP5Otros = etP5Otros.getText().toString();
                    if (textP5Otros == null || textP5Otros.isEmpty()) {
                        etP5Otros.setError("Se debe especificar el nombre de otra compañía aérea");
                        etP5Otros.requestFocus();
                        return false;
                    }
                }
                break;

            case 6:
                //P6
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6)).getVisibility()==VISIBLE) {
                    RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P6);
                    selOpt = rgP6.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha tenido que RECOGER EQUIPAJE en la conexión", Toast.LENGTH_LONG);
                        toast.show();
                        rgP6.requestFocus();
                        return false;
                    }
                }
                break;

            case 7:
                //P7 Noche != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P7)).getVisibility()==VISIBLE) {
                    RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P7);
                    selOpt = rgP7.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si se ha hecho NOCHE durante la conexión", Toast.LENGTH_LONG);
                        toast.show();
                        rgP7.requestFocus();
                        return false;
                    }
                }
                break;

            case 8:
                //P8
                if(((RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P8)).getVisibility()==VISIBLE) {
                    RadioGroup rgP8 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P8);
                    selOpt = rgP8.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar dónde se ha ALOJADO en la noche que ha pasado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP8.requestFocus();
                        return false;
                    }
                    EditText etP8 = (EditText) activity.findViewById(R.id.survey_edit_M3_P8_otros);
                    String textP8 = etP8.getText().toString();


                    selOpt = rgP8.getCheckedRadioButtonId();
                    if(selOpt==R.id.survey_radio_M3_P8_option4) {
                        if (textP8 == null || textP8.isEmpty()) {
                            etP8.setError("Se debe especificar otro tipo de alojamiento");
                            etP8.requestFocus();
                            return false;
                        }
                    }
                }
                break;

            case 9:
                //P9 localidad != null
                    RadioGroup rgP9 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P9);
                    int checkedId = rgP9.getCheckedRadioButtonId();
                    if (checkedId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar de donde PROCEDE el pasajero", Toast.LENGTH_LONG);
                        toast.show();
                        rgP9.requestFocus();
                        return false;
                    }


                    if (checkedId == R.id.survey_radio_M3_P9_option2_localidad) {
                        String textP9Localidad = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_loc))
                                .getText().toString();
                        String codLocalidad = dm.getAssociatedCodeFor(textP9Localidad, DictionaryManager.LOCALIDAD_PROC);
                        if (codLocalidad == null || codLocalidad.isEmpty()) {
                            Toast toast = Toast.makeText(activity, "Se debe indicar la LOCALIDAD de donde PROVIENE el pasajero", Toast.LENGTH_LONG);
                            toast.show();
                            ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_loc)).requestFocus();
                            return false;
                        }
                    }

                //P9Distrito != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9_distrito)).getVisibility()==VISIBLE) {
                    int spinP9_distrito = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P9_distrito)).getSelectedItemPosition();

                    if (spinP9_distrito == 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el DISTRITO de donde PROVIENE el pasajero", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                break;

            case 10:
                //P10
                if (((RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P10)).getVisibility() == VISIBLE) {
                    int checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P10)).getCheckedRadioButtonId();
                    if (checkId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE ALOJAMIENTO utilizado durante la estancia", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                    EditText etP10 = (EditText) activity.findViewById(R.id.survey_edit_M3_P10_otros);
                    String textP10 = etP10.getText().toString();

                    RadioGroup rgP10 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P10);
                    selOpt = rgP10.getCheckedRadioButtonId();
                    if(selOpt==R.id.survey_radio_M3_P10_option7) {
                        if (textP10 == null || textP10.isEmpty()) {
                            etP10.setError("Se debe especificar otro tipo de alojamiento");
                            etP10.requestFocus();
                            return false;
                        }
                    }
                }
                break;

            case 11:
                //P11
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P11)).getVisibility() == VISIBLE) {
                    RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P11);
                    selOpt = rgP11.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar cuantos MODOS DE TRANSPORTE se han utilizado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP11.requestFocus();
                        return false;
                    }
                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P11)).getVisibility() == VISIBLE) {
                    EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M3_P11b_numModos);
                    String textP11 = etP11.getText().toString();
                    //if (textP11 == null || textP11.isEmpty()) {
                    if(selOpt==R.id.survey_radio_M3_P11_option3 && (textP11 == null || textP11.isEmpty())){
                        etP11.setError("Se debe especificar el número de modos de transporte válido");
                        etP11.requestFocus();
                        return false;
                    }
                }
                break;

            case 12:
                //P12
                if (((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12b)).getVisibility() == VISIBLE) {
                    int checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12b)).getCheckedRadioButtonId();
                    if (checkId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el PRIMER MODO DE TRANSPORTE utilizado para llegar al aeropuerto", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P12)).getVisibility() == VISIBLE) {
                    int checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12)).getCheckedRadioButtonId();
                    if (checkId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el ÚLTIMO MODO DE TRANSPORTE utilizado para llegar al aeropuerto", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    } else if(checkId == R.id.survey_radio_M3_P12_optionFake){
                        Toast toast = Toast.makeText(activity, "El ÚLTIMO MODO DE TRANSPORTE utilizado para llegar al aeropuerto no puede ser Bus lanzadera", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }

                }
                break;

            case 13:
                //P13
                if (((RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P13)).getVisibility() == VISIBLE) {
                    int checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P13)).getCheckedRadioButtonId();
                    if (checkId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar DÓNDE ha dejado el vehículo", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P13b)).getVisibility() == VISIBLE) {
                    EditText etP13b_motivo = (EditText) activity.findViewById(R.id.survey_edit_M3_P13b);
                    String textP13b_motivo = etP13b_motivo.getText().toString();
                    if (textP13b_motivo == null || textP13b_motivo.isEmpty()) {
                        etP13b_motivo.setError("Se debe especificar un motivo");
                        etP13b_motivo.requestFocus();
                        return false;
                    }
                }
                break;

            case 14:
                //P14
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P14)).getVisibility() == VISIBLE) {
                    EditText etP14 = (EditText) activity.findViewById(R.id.survey_edit_M3_P14);
                    String textP14 = etP14.getText().toString();
                    if (textP14 == null || textP14.isEmpty()) {
                        etP14.setError("Se debe indicar el número de acompañantes");
                        etP14.requestFocus();
                        return false;
                    }
                }
                break;

            case 15:
                //P15 Hora llegada != null
                EditText etP15_hora = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora);
                EditText etP15_min = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos);
                String P15hora = ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora)).getText().toString();
                String P15min = ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos)).getText().toString();


                if(P15hora== null || P15hora.isEmpty() || P15min== null || P15min.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar la HORA completa de LLEGADA al aeropuerto", Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora)).requestFocus();
                    return false;
                }

                String textHora = P15hora;
                if(textHora != null && !textHora.isEmpty()){
                    int hora = Integer.parseInt(textHora);
                    if(hora<0 || hora>23){
                        etP15_hora.setError("El campo hora debe tener un valor comprendido entre 00 y 23");
                        ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora)).requestFocus();
                        return false;
                    }
                }

                String textMin = P15min;
                if(textMin != null && !textMin.isEmpty()){
                    int min = Integer.parseInt(textMin);
                    if(min<0 || min>59){
                        etP15_min.setError("El campo minutos debe tener un valor comprendido entre 00 y 59");
                        ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos)).requestFocus();
                        return false;
                    }
                }

                Calendar llegadaTime = Calendar.getInstance();
                String textHoraLlegada = P15hora;
                String textMinLlegada = P15min;
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
                            Toast toast = Toast.makeText(activity, "La hora de llegada no puede ser posterior a la hora de realización de la encuesta",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora)).requestFocus();
                            return false;
                        }
                    }
                }
                break;
            case 16:
                //P16 not null & != aeropuerto encuesta
                AutoCompleteTextView actvAeropuertoDestino = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
                selectedAirport = actvAeropuertoDestino.getText().toString();

                if(selectedAirport== null || selectedAirport.isEmpty()){
                    Toast toast = Toast.makeText(activity, "El aeropuerto de destino debe estar cumplimentado", Toast.LENGTH_LONG);
                    toast.show();
                    actvAeropuertoDestino.requestFocus();
                    return false;
                }

                /*if (selectedAirport != null) {
                    selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
                    if (selCode != null && selCode.contentEquals(airportCode)) {
                        Toast toast = Toast.makeText(activity, "El aeropuerto de destino no puede ser igual que el actual", Toast.LENGTH_LONG);
                        toast.show();
                        actvAeropuertoDestino.requestFocus();
                        return false;
                    }
                }*/
                //Check destination airport is different from origin


                airportCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
                AutoCompleteTextView P16airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
                P16airportAutoComplete.setBackgroundColor(Color.WHITE);
                if(airportCode!= null && !airportCode.isEmpty()){
                    AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
                    String airportOriginName =  actvP4.getText().toString();
                    String airportOriginCode = dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT);
                    if(airportOriginCode!= null && !airportOriginCode.isEmpty()){
                        if(airportCode.contentEquals(airportOriginCode)){
                            P16airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                            P16airportAutoComplete.setError("El aeropuerto de destino no puede ser el mismo que el de origen\n\nEs necesario modificar al menos una de las respuestas");
                            p4.setVisibility(VISIBLE);
                            return false;
                        }
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P16_otros)).getVisibility() == VISIBLE) {
                    EditText etP16Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P16_otros);
                    String textP16Otros = etP16Otros.getText().toString();
                    if (textP16Otros == null || textP16Otros.isEmpty()) {
                        etP16Otros.setError("Se debe especificar el nombre de otro aeropuerto");
                        etP16Otros.requestFocus();
                        return false;
                    }
                }

                break;

            case 17:
                //P17 Compañia y número de vuelo != null
                AutoCompleteTextView actvCompany = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P17_company);
                String selectedCompany = actvCompany.getText().toString();
                if(selectedCompany== null || selectedCompany.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo", Toast.LENGTH_LONG);
                    toast.show();
                    actvCompany.requestFocus();
                    return false;
                }

                EditText etFlightNum = (EditText) activity.findViewById(R.id.survey_edit_M3_P17);
                String number = etFlightNum.getText().toString();
                if(number==null || number.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo", Toast.LENGTH_LONG);
                    toast.show();
                    etFlightNum.requestFocus();
                    return false;
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P17_otros)).getVisibility() == VISIBLE) {
                    EditText etP17Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P17_otros);
                    String textP17Otros = etP17Otros.getText().toString();
                    if (textP17Otros == null || textP17Otros.isEmpty()) {
                        etP17Otros.setError("Se debe especificar el nombre de otra compañía aérea");
                        etP17Otros.requestFocus();
                        return false;
                    }
                }
                break;

            case 18:
                //P18
                RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P18);
                selOpt = rgP18.getCheckedRadioButtonId();
                if (selOpt <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar si el se viaja para hacer TRASBORDO", Toast.LENGTH_LONG);
                    toast.show();
                    rgP18.requestFocus();
                    return false;
                }


                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18b)).getVisibility()==VISIBLE) {
                    AutoCompleteTextView actvP18b = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P18b);
                    selected = actvP18b.getText().toString();
                    if (selected == null || selected.isEmpty()) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la compañía aérea con la que se continua el viaje", Toast.LENGTH_LONG);
                        toast.show();
                        actvP18b.requestFocus();
                        return false;
                    }
                }

                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18b_otros)).getVisibility() == VISIBLE) {
                    EditText etP18bOtros = (EditText) activity.findViewById(R.id.survey_edit_M3_P18b_otros);
                    String textP18bOtros = etP18bOtros.getText().toString();
                    if (textP18bOtros == null || textP18bOtros.isEmpty()) {
                        etP18bOtros.setError("Se debe especificar el nombre de otra compañía aérea");
                        etP18bOtros.requestFocus();
                        return false;
                    }
                }
                break;
            case 19:
                //P19
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19)).getVisibility()==VISIBLE) {
                    AutoCompleteTextView actvAeropuertoFinal = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P19);
                    selectedAirport = actvAeropuertoFinal.getText().toString();
                    if (selectedAirport == null || selectedAirport.isEmpty() || selectedAirport.contentEquals("")) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el aeropuerto de finalización del viaje", Toast.LENGTH_LONG);
                        toast.show();
                        actvAeropuertoFinal.requestFocus();
                        return false;
                    } else {
                        String code = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
                        if(code == null || code.isEmpty()){
                            Toast toast = Toast.makeText(activity, "El valor indicado en el campo AEROPUERTO DE FINALIZACIÓN no es válido", Toast.LENGTH_LONG);
                            toast.show();
                            actvAeropuertoFinal.requestFocus();
                            return false;
                        }
                    }


                    //P19 Aeropuerto finalización != aeropuerto encuesta
                    /*if (selectedAirport != null) {
                        selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
                        if (selCode != null && selCode.contentEquals(airportCode)) {
                            Toast toast = Toast.makeText(activity, "El aeropuerto de finalización del viaje no puede ser igual que el actual", Toast.LENGTH_LONG);
                            toast.show();
                            actvAeropuertoFinal.requestFocus();
                            return false;
                        }
                    }*/
                    //p16.setVisibility(GONE);
                    //Check destination airport is different from origin
                    AutoCompleteTextView P19airportAutoComplete = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P19);
                    P19airportAutoComplete.setBackgroundColor(Color.WHITE);
                    airportCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
                    if(airportCode!= null && !airportCode.isEmpty()){
                        AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
                        String airportOriginName =  actvP4.getText().toString();
                        String airportOriginCode =dm.getAssociatedCodeFor(airportOriginName, DictionaryManager.AIRPORT);
                        if(airportOriginCode!= null && !airportOriginCode.isEmpty()){
                            if(airportCode.contentEquals(airportOriginCode)){
                                P19airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                P19airportAutoComplete.setError("El aeropuerto de finalización del viaje no puede ser el mismo que el de origen.\n\nEs necesario modificar al menos una de las respuestas");

                                p4.setVisibility(VISIBLE);
                                return false;
                            }else{
                                p4.setVisibility(GONE);
                            }
                        }
                    }



                    AutoCompleteTextView actvP16 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
                    String airportOriginNameP16 = actvP16.getText().toString();
                    String airportOriginCodeP16 = dm.getAssociatedCodeFor(airportOriginNameP16, DictionaryManager.AIRPORT);

                    airportCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
                    if(airportCode!= null && !airportCode.isEmpty()){


                        if(airportOriginCodeP16!= null && !airportOriginCodeP16.isEmpty()){
                            if(airportCode.contentEquals(airportOriginCodeP16)){
                                P19airportAutoComplete.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                P19airportAutoComplete.setError("El aeropuerto de finalización del viaje no puede ser el mismo que el de origen.\n\nEs necesario modificar al menos una de las respuestas");
                                P19airportAutoComplete.requestFocus();
                                p16.setVisibility(VISIBLE);
                                return false;
                            }else{
                                p16.setVisibility(GONE);
                            }
                        }
                    }

                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19_otros)).getVisibility() == VISIBLE) {
                    EditText etP19Otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P19_otros);
                    String textP19Otros = etP19Otros.getText().toString();
                    if (textP19Otros == null || textP19Otros.isEmpty()) {
                        etP19Otros.setError("Se debe especificar el nombre de otro aeropuerto");
                        etP19Otros.requestFocus();
                        return false;
                    }
                }
                break;

            case 20:
                //P20 Motivo viaje != null
                //if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20)).getVisibility()==VISIBLE) {
                int spinP20 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P20)).getSelectedItemPosition();

                //}
                if (spinP20==0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el PROPÓSITO PRINCIPAL del viaje", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
                break;

            case 21:
                //P21 Viaje IDA o VUELTA != null
                EditText etP21 = (EditText) activity.findViewById(R.id.survey_edit_M3_P21b);
                RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P21);
                selOpt = rgP21.getCheckedRadioButtonId();
                if(selOpt <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar si el viaje es de IDA o VUELTA", Toast.LENGTH_LONG);
                    toast.show();
                    rgP21.requestFocus();
                    return false;
                }

                //P21 Num dias

                String textP21 = etP21.getText().toString();
                if(textP21==null || textP21.isEmpty()){
                    etP21.setError("Se debe indicar el número de días de duración del viaje");
                    etP21.requestFocus();
                    return false;
                }



            String numP21 = etP21.getText().toString();

                int numDias = Integer.parseInt(numP21);




                int spP20b = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P20)).getSelectedItemPosition();

                String textP20 = Integer.toString(spP20b);
                textP21 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P21b)).getText().toString();

                boolean error = false;
                if(textP20 != null && !textP20.isEmpty()){
                    if(textP21 != null && !textP21.isEmpty()){
                        int numP20 = Integer.parseInt(textP20);
                        //int numP21 = Integer.parseInt(textP21);

                        switch(numP20){
                            case 4:
                                if(numDias<=90){
                                    LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20);
                                    etP21.setError("Es necesario revisar el propósito principal del viaje porque no es coherente con el número de días indicados");
                                    p20.setVisibility(VISIBLE);
                                    return false;
                                }
                                break;
                            case 18:
                                if(numDias>7) {
                                    LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20);
                                    etP21.setError("Es necesario revisar el propósito principal del viaje porque no es coherente con el número de días indicados");
                                    p20.setVisibility(VISIBLE);
                                    return false;
                                }
                                break;
                            case 19:
                                if(numDias<=7) {
                                    LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20);
                                    etP21.setError("Es necesario revisar el propósito principal del viaje porque no es coherente con el número de días indicados");
                                    p20.setVisibility(VISIBLE);
                                    return false;
                                }
                                break;
                        }

                    }
                }

                break;

            case 22:
                //P22 Num personas != null
                EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas);
                selOpt = rgP22.getCheckedRadioButtonId();
                if(selOpt <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo", Toast.LENGTH_LONG);
                    toast.show();
                    rgP22.requestFocus();
                    return false;
                }

                if(selOpt==R.id.survey_radio_M3_P22_option3){
                    String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).getText().toString();
                    if(textP22==null || textP22.isEmpty()){
                        etP22.setError("Se debe indicar el número de personas en el grupo");
                        ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).requestFocus();
                        return false;
                    }
                }
                break;

            case 23:
                // Num P22 < P23

                EditText etP23 = (EditText) activity.findViewById(R.id.survey_edit_M3_P23);
                String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).getText().toString();
                String textP23 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P23)).getText().toString();
                if(textP22 != null && !textP22.isEmpty()){
                    if(textP23 != null && !textP23.isEmpty()){
                        int numP22 = Integer.parseInt(textP22);
                        int numP23 = Integer.parseInt(textP23);
                        if(numP23>numP22){
                            LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P22);
                            etP23.setError("El número de menores no puede ser mayor que el número total de personas del grupo\n\nEs necesario modificar al menos una de las respuestas");
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P23)).requestFocus();
                            p22.setVisibility(VISIBLE);

                            return false;
                        }
                    }
                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P22)).getVisibility() == VISIBLE) {

                    selOpt = rgP22.getCheckedRadioButtonId();
                    if (selOpt == R.id.survey_radio_M3_P22_option3) {
                        textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).getText().toString();
                        if (textP22 == null || textP22.isEmpty()) {
                            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo (P22)", Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).requestFocus();
                            return false;
                        }
                    }
                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P23)).getVisibility() == VISIBLE) {
                        textP23 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P23)).getText().toString();
                        if (textP23 == null || textP23.isEmpty()) {
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P23)).setError("Se debe indicar el número de menores de 15 años que hay en el grupo");
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P23)).requestFocus();
                            return false;
                        }
                }
                break;

            case 24:
                //P24 != null
                    int selP24 = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P24)).getCheckedRadioButtonId();
                    if(selP24<=0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar la RELACIÓN existente entre los componentes del grupo", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                break;

            case 25:
                //P25 Tiempo reserva != null
                EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M3_P25_numDias);
                String P25numDias = ((EditText) activity.findViewById(R.id.survey_edit_M3_P25_numDias)).getText().toString();
                if(P25numDias== null || P25numDias.isEmpty()){
                    etP25.setError("Se debe indicar el tiempo que hace desde la reserva del billete");
                    ((EditText) activity.findViewById(R.id.survey_edit_M3_P25_numDias)).requestFocus();
                    return false;
                }
                break;
            case 26:
                //P26 Tipo tarifa != null
                RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P26);
                selOpt = rgP26.getCheckedRadioButtonId();
                if(selOpt <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE TARIFA del billete", Toast.LENGTH_LONG);
                    toast.show();
                    rgP26.requestFocus();
                    return false;
                }
                break;

            case 27:
                //P27 Num viajes avion != null
                EditText etP27 = (EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes);
                selOpt = rgP27.getCheckedRadioButtonId();
                if(selOpt <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión", Toast.LENGTH_LONG);
                    toast.show();
                    rgP27.requestFocus();
                    return false;
                }

                if(selOpt==R.id.survey_radio_M3_P27_option2){
                    String textP27 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes)).getText().toString();
                    if(textP27==null | textP27.isEmpty()){
                        etP27.setError("Se debe indicar el número de viajes de avión");
                        ((EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes)).requestFocus();
                        return false;
                    }
                }
                break;

            case 28:
                //P28 <= P27
                EditText etP28 = (EditText) activity.findViewById(R.id.survey_edit_M3_P28_numViajes);
                String numViajes = etP28.getText().toString();
                if(numViajes != null && !numViajes.isEmpty()){
                    int numViajesMismaRuta = Integer.parseInt(numViajes);

                    int numTotalViajes = 0;
                    selOpt = rgP27.getCheckedRadioButtonId();
                    if(selOpt != R.id.survey_radio_M3_P27_option1){
                        String num = ((EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes)).getText().toString();
                        if(num != null && !num.isEmpty()){
                            numTotalViajes = Integer.parseInt(num);
                        }
                    }

                    if(numViajesMismaRuta>numTotalViajes){
                        LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P27);
                        etP28.setError("El número de viajes en la misma ruta no puede ser superior al número total de viajes\n\nEs necesario modificar al menos una de las respuestas");
                        p27.setVisibility(VISIBLE);
                        return false;
                    }
                }
                break;
            case 29:
                //P29 Facturación & Nº bultos != null
                RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P29);
                int selectedOption = rgP29.getCheckedRadioButtonId();
                if(selectedOption<0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar si se ha FACTURADO equipaje", Toast.LENGTH_LONG);
                    toast.show();
                    rgP29.requestFocus();
                    return false;

                } else {
                    if(selectedOption== R.id.survey_radio_M3_P29_option1){
                        EditText etP29num =  (EditText) activity.findViewById(R.id.survey_edit_M3_P29b_numBultos);
                        String numBultos = etP29num.getText().toString();
                        if(numBultos==null || numBultos.isEmpty()){
                            etP29num.setError("Se debe indicar el número de bultos");
                            etP29num.requestFocus();
                            return false;
                        }
                    }

                }
                break;

            case 30:
                //P30
                EditText etP30 = (EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas);
                textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).getText().toString();
                selectedOption = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22)).getCheckedRadioButtonId();
                if(selectedOption == R.id.survey_radio_M3_P22_option1){
                    textP22 = "1";
                }
                String textP30 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas)).getText().toString();

                if(textP22 != null && !textP22.isEmpty()){
                    if(textP30 != null && !textP30.isEmpty()){
                        int numP22 = Integer.parseInt(textP22);
                        int numP30 = Integer.parseInt(textP30);
                        if(numP30>numP22){
                            LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P22);
                            etP30.setError("El número de personas con bultos no puede ser mayor que el número total de personas del grupo\n\nEs necesario modificar al menos una de las respuestas");
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas)).requestFocus();
                            p22.setVisibility(VISIBLE);
                            return false;
                        }
                    }
                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P22)).getVisibility() == VISIBLE) {

                    selOpt = rgP22.getCheckedRadioButtonId();
                    if (selOpt == R.id.survey_radio_M3_P22_option3) {
                        textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).getText().toString();
                        if (textP22 == null || textP22.isEmpty()) {
                            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo (P22)", Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas)).requestFocus();
                            return false;
                        }
                    }
                }
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30)).getVisibility() == VISIBLE) {
                    textP30 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas)).getText().toString();
                    if (textP30 == null || textP30.isEmpty()) {
                        etP30.setError("Se debe indicar el número de personas a las que pertenecen los bultos");
                        ((EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas)).requestFocus();
                        return false;
                    }
                }

                break;
            case 300:
                //P30b
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30b)).getVisibility()==VISIBLE) {
                    RadioGroup rgP30b = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P30b);
                    selOpt = rgP30b.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha utilizado los MOSTRADORES de AUTO-FACTURACIÓN del equipaje", Toast.LENGTH_LONG);
                        toast.show();
                        rgP30b.requestFocus();
                        return false;
                    }
                }
                break;

            case 31:
                //P31 tarjeta embarque != null
                RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P31);
                selOpt = rgP31.getCheckedRadioButtonId();
                if(selOpt <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar dónde se ha obtenido la TARJETA DE EMBARQUE", Toast.LENGTH_LONG);
                    toast.show();
                    rgP31.requestFocus();
                    return false;
                }
                break;

            case 32:
                //P32 Consumo restauración != null
                RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P32);
                selOpt = rgP32.getCheckedRadioButtonId();
                if(selOpt <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar si ha CONSUMIDO algún producto en los servicios de restauración", Toast.LENGTH_LONG);
                    toast.show();
                    rgP32.requestFocus();
                    return false;
                } else if( selOpt ==R.id.survey_radio_M3_P32_option1){
                    TextView etP32a =(TextView) activity.findViewById(R.id.survey_edit_M3_P32a);
                    String value = ((TextView) activity.findViewById(R.id.survey_edit_M3_P32a)).getText().toString();
                    if(value==null || value.isEmpty() || value.contentEquals("")){
                        etP32a.setError("Se debe indicar el importe gastado en los servicios de restauración");
                        ((TextView) activity.findViewById(R.id.survey_edit_M3_P32a)).requestFocus();
                        return false;
                    }
                }
                break;

            case 33:
                /*//P33 Compra articulo != null
                RadioGroup rgP33 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P33);
                selOpt = rgP33.getCheckedRadioButtonId();
                if(selOpt <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar si ha COMPRADO algún artículo en las tiendas", Toast.LENGTH_LONG);
                    toast.show();
                    rgP33.requestFocus();
                    return false;
                } else if( selOpt ==R.id.survey_radio_M3_P33_option1){
                    String value = ((TextView) activity.findViewById(R.id.survey_edit_M3_P33a)).getText().toString();
                    if(value==null || value.isEmpty() || value.contentEquals("")){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el importe que se ha COMPRADO en las tiendas", Toast.LENGTH_LONG);
                        toast.show();
                        ((TextView) activity.findViewById(R.id.survey_edit_M3_P33a)).requestFocus();
                    }
                }*/
                //P33 Compra articulo != null
                int spin1 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item1)).getSelectedItemPosition();
                int spin2 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_iteM3)).getSelectedItemPosition();
                int spin3 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item3)).getSelectedItemPosition();
                int spin4 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item4)).getSelectedItemPosition();
                int spin5 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item5)).getSelectedItemPosition();

                RadioGroup rgP33 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P33);
                selectedOption = rgP33.getCheckedRadioButtonId();
                if(selectedOption <= 0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar si ha COMPRADO algún artículo en las tiendas", Toast.LENGTH_LONG);
                    toast.show();
                    rgP33.requestFocus();
                    return false;
                }else {
                    if (selectedOption == R.id.survey_radio_M3_P33_option1) {
                        EditText etP33a = (EditText) activity.findViewById(R.id.survey_edit_M3_P33a);
                        String cant33a = etP33a.getText().toString();
                        if (cant33a == null || cant33a.isEmpty()) {
                            etP33a.setError("Se debe indicar la cantidad gastada en las tiendas del aeropuerto");
                            etP33a.requestFocus();
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

            case 34:
                //P34 ConoceWifi != null
                RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P34);
                int selOptP34 = rgP34.getCheckedRadioButtonId();
                if (selOptP34 <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar si CONOCE la existencia de WIFI gratuito", Toast.LENGTH_LONG);
                    toast.show();
                    rgP34.requestFocus();
                    return false;
                }
                break;

            case 35:
                RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P35);
                int selOptP35 = rgP35.getCheckedRadioButtonId();
                if (selOptP35 <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar si HA UTILIZADO WIFI gratuito", Toast.LENGTH_LONG);
                    toast.show();
                    rgP35.requestFocus();
                    return false;
                }
                break;

            case 36:
                //P36 ConoceWifi != null
                RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P36);
                int selOptP36 = rgP36.getCheckedRadioButtonId();
                if (selOptP36 <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el MOTIVO de por que no utilizado WIFI", Toast.LENGTH_LONG);
                    toast.show();
                    rgP36.requestFocus();
                    return false;
                }
                break;

            case 37:
                //P37 Situación laboral != null
                RadioGroup rgP37 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P37);
                int selOptP37 = rgP37.getCheckedRadioButtonId();
                if(selOptP37<=0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar la SITUACIÓN LABORAL", Toast.LENGTH_LONG);
                    toast.show();
                    rgP37.requestFocus();
                    return false;
                }
                break;

            case 38:
                //P38 TipoTrabajo != null
                RadioGroup rgP38 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P38);
                int selOptP38 = rgP38.getCheckedRadioButtonId();
                if (selOptP38  <= 0) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE TRABAJO del entrevistado", Toast.LENGTH_LONG);
                    toast.show();
                    rgP38.requestFocus();
                    return false;
                }
                break;

            case 39:
                //P39 Actividad empresa != null
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P39)).getVisibility()==VISIBLE) {
                    int spinP39 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P39)).getSelectedItemPosition();

                    if (spinP39 == 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la ACTIVIDAD de la empresa u organización", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M3_P39_others)).getVisibility() == VISIBLE) {
                    EditText etP39_otraActividad = (EditText) activity.findViewById(R.id.survey_edit_M3_P39_otraActividad);
                    String textP39_otraActividad = etP39_otraActividad.getText().toString();
                    if (textP39_otraActividad == null || textP39_otraActividad.isEmpty()) {
                        etP39_otraActividad.setError("Se debe especificar otra actividad");
                        etP39_otraActividad.requestFocus();
                        return false;
                    }
                }
                break;

            case 40:
                //P40 Estudios != null
                RadioGroup rgP40 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P40);
                int selOptP40 = rgP40.getCheckedRadioButtonId();
                if(selOptP40<0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar el NIVEL DE ESTUDIOS del entrevistado", Toast.LENGTH_LONG);
                    toast.show();
                    rgP40.requestFocus();
                    return false;
                }
                break;

            case 41:
                //P41 Edad != null
                RadioGroup rgP41 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P41);
                int selOptP41 = rgP41.getCheckedRadioButtonId();
                if(selOptP41<0){
                    Toast toast = Toast.makeText(activity, "Se debe indicar la EDAD del entrevistado", Toast.LENGTH_LONG);
                    toast.show();
                    rgP41.requestFocus();
                    return false;
                }
                break;

            case 42:
                //P42 sexo != null
                RadioGroup rgSexo = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P42);
                if (rgSexo.getCheckedRadioButtonId() == -1) {
                    Toast toast = Toast.makeText(activity, "Se debe seleccionar el SEXO del entrevistado", Toast.LENGTH_LONG);
                    toast.show();
                    rgSexo.requestFocus();
                    return false;
                }
                break;

        }
        return true;
    }

    public int showNextQuestion(int show) {

        RelativeLayout p8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P8);
        LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9);
        RelativeLayout p10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P10);
        LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P11);
        LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P15);
        LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19);
        LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20);

        LinearLayout p25 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P25);
        LinearLayout p29 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P29);
        LinearLayout p31 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P31);
        LinearLayout p37 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P37);
        LinearLayout p40 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P40);

        RadioGroup rgP8 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P8);
        RadioGroup rgP22 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22);

        EditText etP15Hora = (EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora);
        EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M3_P25_numDias);


        switch (show){
            case 1:

                LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2);
                AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2);
                p2.setVisibility(VISIBLE);
                actvP2.requestFocus();
                show = 2;
                Button previo = (Button) activity.findViewById(R.id.survey_button_previous);
                previo.setVisibility(VISIBLE);

                break;
            case 2:

                LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P3);
                p3.setVisibility(VISIBLE);

                show = 3;

                break;
            case 3:
                LinearLayout p4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4);
                AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
                actvP4.requestFocus();
                RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P3);
                int checkedId = rgP3.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P3_option1:
                            p4.setVisibility(VISIBLE);
                            actvP4.requestFocus();
                            show = 4;
                            break;
                        case R.id.survey_radio_M3_P3_option2:
                            p9.setVisibility(VISIBLE);
                            show = 9;
                            break;
                    }
                }
                break;

            case 4:

                LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P5);
                AutoCompleteTextView actvP5 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P5);
                p5.setVisibility(VISIBLE);
                actvP5.requestFocus();
                show = 5;

                break;
            case 5:

                LinearLayout p6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6);
                p6.setVisibility(VISIBLE);
                show = 6;

                break;
            case 6:
                LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P7);
                p7.setVisibility(VISIBLE);
                show = 7;

                break;
            case 7:
                RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P7);
                checkedId = rgP7.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P7_option1:
                            p8.setVisibility(VISIBLE);
                            show = 8;
                            break;
                        case R.id.survey_radio_M3_P7_option2:
                            p15.setVisibility(VISIBLE);
                            etP15Hora.requestFocus();
                            show = 15;
                            break;
                    }
                }
                break;
            case 8:

                checkedId = rgP8.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P8_option1:
                            p15.setVisibility(VISIBLE);
                            etP15Hora.requestFocus();
                            show = 15;
                            break;
                        default:
                            p9.setVisibility(VISIBLE);
                            show = 9;
                            break;
                    }
                }
                break;
            case 9:

                RadioGroup rgP9 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P9);
                checkedId = rgP9.getCheckedRadioButtonId();
                int checkedIdP8 = rgP8.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P9_option1:
                            p11.setVisibility(VISIBLE);
                            show = 11;
                            break;
                        case R.id.survey_radio_M3_P9_option2_localidad:
                            if(checkedIdP8<0) {
                                p10.setVisibility(VISIBLE);
                                show = 10;
                            } else {
                                p11.setVisibility(VISIBLE);
                                show = 11;
                            }
                            break;
                    }
                }
                break;
            case 10:

                p11.setVisibility(VISIBLE);
                show = 11;

                break;
            case 11:

                LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P12);
                p12.setVisibility(VISIBLE);
                show = 12;

                break;
            case 12:
                RelativeLayout p13 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P13);

                RadioGroup rgP12 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12);
                checkedId = rgP12.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P12_option3:
                            p13.setVisibility(VISIBLE);
                            show = 13;
                            break;
                        case R.id.survey_radio_M3_P12_option4:
                            p13.setVisibility(VISIBLE);
                            show = 13;
                            break;
                        default:
                            p15.setVisibility(VISIBLE);
                            etP15Hora.requestFocus();
                            show = 15;
                            break;
                    }
                }
                break;
            case 13:

                LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P14);
                p14.setVisibility(VISIBLE);
                EditText etP14 = (EditText) activity.findViewById(R.id.survey_edit_M3_P14);
                etP14.requestFocus();
                show = 14;

                break;
            case 14:

                p15.setVisibility(VISIBLE);
                etP15Hora.requestFocus();
                show = 15;

                break;
            case 15:

                AutoCompleteTextView actvP16 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
                LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P16);
                p16.setVisibility(VISIBLE);
                actvP16.requestFocus();
                show = 16;

                break;
            case 16:

                AutoCompleteTextView actvP17 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P17_company);
                LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P17);
                p17.setVisibility(VISIBLE);
                actvP17.requestFocus();
                show = 17;

                break;
            case 17:

                LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18);
                p18.setVisibility(VISIBLE);
                show = 18;

                break;
            case 18:
                AutoCompleteTextView actvP19 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P19);
                RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P18);
                checkedId = rgP18.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P18_option1:
                            p20.setVisibility(VISIBLE);
                            show = 20;
                            break;
                        case R.id.survey_radio_M3_P18_option2:
                            p19.setVisibility(VISIBLE);
                            actvP19.requestFocus();
                            show = 19;
                            break;
                    }
                }
                break;
            case 19:

                p20.setVisibility(VISIBLE);
                show = 20;

                break;
            case 20:

                LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P21);
                p21.setVisibility(VISIBLE);
                show = 21;

                break;
            case 21:

                LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P22);
                p22.setVisibility(VISIBLE);
                show = 22;

                break;
            case 22:
                LinearLayout p23 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P23);
                EditText etP23 = (EditText) activity.findViewById(R.id.survey_edit_M3_P23);

                checkedId = rgP22.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P22_option1:
                            p25.setVisibility(VISIBLE);
                            etP25.requestFocus();
                            show = 25;
                            break;
                        default:
                            p23.setVisibility(VISIBLE);
                            etP23.requestFocus();
                            show = 23;
                            break;
                    }
                }
                break;
            case 23:

                LinearLayout p24 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P24);
                p24.setVisibility(VISIBLE);
                show = 24;

                break;
            case 24:

                p25.setVisibility(VISIBLE);
                etP25.requestFocus();
                show = 25;

                break;
            case 25:

                LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P26);
                p26.setVisibility(VISIBLE);
                show = 26;

                break;
            case 26:

                LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P27);
                p27.setVisibility(VISIBLE);
                show = 27;

                break;
            case 27:

                EditText etP28 = (EditText) activity.findViewById(R.id.survey_edit_M3_P28_numViajes);
                LinearLayout p28 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P28);
                RadioGroup rgP27 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P27);
                checkedId = rgP27.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P27_option1:
                            p29.setVisibility(VISIBLE);
                            show = 29;
                            break;
                        default:
                            p28.setVisibility(VISIBLE);
                            etP28.requestFocus();
                            show = 28;
                            break;
                    }
                }
                break;
            case 28:

                p29.setVisibility(VISIBLE);
                show = 29;

                break;
            case 29:

                LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30);
                RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P29);
                checkedId = rgP29.getCheckedRadioButtonId();
                int checkedIdP22 = rgP22.getCheckedRadioButtonId();
                if(checkedId>0) {

                        if((checkedId==R.id.survey_radio_M3_P29_option1) && (checkedIdP22!=R.id.survey_radio_M3_P22_option1)){
                            p30.setVisibility(VISIBLE);
                            show = 30;
                        }else{
                            p31.setVisibility(VISIBLE);
                            show = 31;
                        }
                }
                break;
            case 30:
                LinearLayout p30b = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30b);
                p30b.setVisibility(VISIBLE);
                show = 300;

                break;
            case 300:
                p31.setVisibility(VISIBLE);
                show = 31;

                break;
            case 31:

                LinearLayout p32 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P32);
                p32.setVisibility(VISIBLE);
                show = 32;

                break;
            case 32:

                LinearLayout p33 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P33);
                p33.setVisibility(VISIBLE);
                show = 33;

                break;
            case 33:

                p37.setVisibility(VISIBLE);
                show = 37;

                break;
            case 34:
                LinearLayout p35 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P35);

                RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P34);
                checkedId = rgP34.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P34_option1:
                            p35.setVisibility(VISIBLE);
                            show = 35;
                            break;
                        case R.id.survey_radio_M3_P34_option2:
                            p37.setVisibility(VISIBLE);
                            show = 37;
                            break;
                    }
                }
                break;
            case 35:
                LinearLayout p36 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P36);

                RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P35);
                checkedId = rgP35.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P35_option1:
                            p37.setVisibility(VISIBLE);
                            show = 37;
                            break;
                        case R.id.survey_radio_M3_P35_option2:
                            p36.setVisibility(VISIBLE);
                            show = 36;
                            break;
                    }
                }
                break;
            case 36:

                p37.setVisibility(VISIBLE);
                show = 37;

                break;
            case 37:
                LinearLayout p38 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P38);

                RadioGroup rgP37 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P37);
                checkedId = rgP37.getCheckedRadioButtonId();
                if(checkedId>0) {

                    switch (checkedId) {
                        case R.id.survey_radio_M3_P37_option1:
                            p38.setVisibility(VISIBLE);
                            show = 38;
                            break;
                        default:
                            p40.setVisibility(VISIBLE);
                            show = 40;
                            break;
                    }
                }
                break;
            case 38:
                Spinner sP20 = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P20);
                String codReason = dm.getAssociatedCodeFor(sP20.getSelectedItem().toString(), DictionaryManager.TRAVEL_REASON);
                int codigo = Integer.parseInt(codReason);

                if (codigo>100 && codigo<107) {
                    LinearLayout p39 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P39);
                    p39.setVisibility(VISIBLE);
                    show = 39;
                }else{
                    p40.setVisibility(VISIBLE);
                    show = 40;
                }
                break;
            case 39:

                p40.setVisibility(VISIBLE);
                show = 40;

                break;
            case 40:

                LinearLayout p41 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P41);
                p41.setVisibility(VISIBLE);
                show = 41;

                break;
            case 41:

                LinearLayout p42 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P42);
                p42.setVisibility(VISIBLE);
                show = 42;
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

        LinearLayout p4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4);
        LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P16);
        LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20);
        LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P22);
        LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P27);

        switch (p) {
            case 1:
                LinearLayout p1 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P1);

                if (checkQuestion(1) == true) {
                    p1.setVisibility(GONE);
                    p = showNextQuestion(1);
                }

                break;

            case 2:
                LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2);

                if (checkQuestion(2) == true) {
                    p2.setVisibility(GONE);
                    p = showNextQuestion(2);
                }

                break;

            case 3:
                LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P3);

                if (checkQuestion(3) == true) {
                    p3.setVisibility(GONE);
                    p = showNextQuestion(3);
                }

                break;

            case 4:

                if (checkQuestion(4) == true) {
                    p4.setVisibility(GONE);
                    p = showNextQuestion(4);
                }

                break;


            case 5:
                LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P5);

                if (checkQuestion(5) == true) {
                    p5.setVisibility(GONE);
                    p = showNextQuestion(5);
                }

                break;

            case 6:
                LinearLayout p6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6);

                if (checkQuestion(6) == true) {
                    p6.setVisibility(GONE);
                    p = showNextQuestion(6);
                }

                break;

            case 7:
                LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P7);

                if (checkQuestion(7) == true) {
                    p7.setVisibility(GONE);
                    p = showNextQuestion(7);
                }

                break;

            case 8:
                RelativeLayout p8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P8);

                if (checkQuestion(8) == true) {
                    p8.setVisibility(GONE);
                    p = showNextQuestion(8);
                }

                break;
            case 9:
                LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9);

                if (checkQuestion(9) == true) {
                    p9.setVisibility(GONE);
                    p = showNextQuestion(9);
                }

                break;
            case 10:
                RelativeLayout p10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P10);

                if (checkQuestion(10) == true) {
                    p10.setVisibility(GONE);
                    p = showNextQuestion(10);
                }

                break;
            case 11:
                LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P11);

                if (checkQuestion(11) == true) {
                    p11.setVisibility(GONE);
                    p = showNextQuestion(11);
                }

                break;
            case 12:
                LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P12);

                if (checkQuestion(12) == true) {
                    p12.setVisibility(GONE);
                    p = showNextQuestion(12);
                }

                break;
            case 13:
                RelativeLayout p13 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P13);

                if (checkQuestion(13) == true) {
                    p13.setVisibility(GONE);
                    p = showNextQuestion(13);
                }

                break;
            case 14:
                LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P14);

                if (checkQuestion(14) == true) {
                    p14.setVisibility(GONE);
                    p = showNextQuestion(14);
                }

                break;
            case 15:
                LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P15);

                if (checkQuestion(15) == true) {
                    p15.setVisibility(GONE);
                    p = showNextQuestion(15);
                }

                break;
            case 16:


                if (checkQuestion(16) == true) {
                    p4.setVisibility(GONE);
                    p16.setVisibility(GONE);
                    p = showNextQuestion(16);
                }

                break;
            case 17:
                LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P17);

                if (checkQuestion(17) == true) {
                    p17.setVisibility(GONE);
                    p = showNextQuestion(17);
                }

                break;
            case 18:
                LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18);
                LinearLayout p18b = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18b);

                if (checkQuestion(18) == true) {
                    p18.setVisibility(GONE);
                    p18b.setVisibility(GONE);
                    p = showNextQuestion(18);
                }

                break;
            case 19:
                LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19);

                if (checkQuestion(19) == true) {
                    p4.setVisibility(GONE);
                    p16.setVisibility(GONE);
                    p19.setVisibility(GONE);
                    p = showNextQuestion(19);
                }

                break;
            case 20:

                if (checkQuestion(20) == true) {
                    p20.setVisibility(GONE);
                    p = showNextQuestion(20);
                }

                break;
            case 21:
                LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P21);

                if (checkQuestion(21) == true) {
                    p20.setVisibility(GONE);
                    p21.setVisibility(GONE);
                    p = showNextQuestion(21);
                }

                break;
            case 22:

                if (checkQuestion(22) == true) {
                    p22.setVisibility(GONE);
                    p = showNextQuestion(22);
                }

                break;
            case 23:
                LinearLayout p23 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P23);

                if (checkQuestion(23) == true) {
                    p22.setVisibility(GONE);
                    p23.setVisibility(GONE);
                    p = showNextQuestion(23);
                }

                break;
            case 24:
                LinearLayout p24 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P24);

                if (checkQuestion(24) == true) {
                    p24.setVisibility(GONE);
                    p = showNextQuestion(24);
                }

                break;
            case 25:
                LinearLayout p25 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P25);

                if (checkQuestion(25) == true) {
                    p25.setVisibility(GONE);
                    p = showNextQuestion(25);
                }

                break;
            case 26:
                LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P26);

                if (checkQuestion(26) == true) {
                    p26.setVisibility(GONE);
                    p = showNextQuestion(26);
                }

                break;
            case 27:

                if (checkQuestion(27) == true) {
                    p27.setVisibility(GONE);
                    p = showNextQuestion(27);
                }

                break;
            case 28:
                LinearLayout p28 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P28);

                if (checkQuestion(28) == true) {
                    p27.setVisibility(GONE);
                    p28.setVisibility(GONE);
                    p = showNextQuestion(28);
                }

                break;
            case 29:
                LinearLayout p29 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P29);

                if (checkQuestion(29) == true) {
                    p29.setVisibility(GONE);
                    p = showNextQuestion(29);
                }

                break;
            case 30:
                LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30);
                //LinearLayout p30b = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30b);

                if (checkQuestion(30) == true) {
                    p22.setVisibility(GONE);
                    p30.setVisibility(GONE);
                    //p30b.setVisibility(GONE);
                    p = showNextQuestion(30);
                }

                break;
            case 300:
                //LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30);
                LinearLayout p30b = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30b);

                if (checkQuestion(300) == true) {
                    p22.setVisibility(GONE);
                    //p30.setVisibility(GONE);
                    p30b.setVisibility(GONE);
                    p = showNextQuestion(300);
                }

                break;
            case 31:
                LinearLayout p31 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P31);

                if (checkQuestion(31) == true) {
                    p31.setVisibility(GONE);
                    p = showNextQuestion(31);
                }

                break;
            case 32:
                LinearLayout p32 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P32);

                if (checkQuestion(32) == true) {
                    p32.setVisibility(GONE);
                    p = showNextQuestion(32);
                }

                break;
            case 33:
                LinearLayout p33 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P33);

                if (checkQuestion(33) == true) {
                    p33.setVisibility(GONE);
                    p = showNextQuestion(33);
                }

                break;
            case 34:
                LinearLayout p34 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P34);

                if (checkQuestion(34) == true) {
                    p34.setVisibility(GONE);
                    p = showNextQuestion(34);
                }

                break;
            case 35:
                LinearLayout p35 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P35);

                if (checkQuestion(35) == true) {
                    p35.setVisibility(GONE);
                    p = showNextQuestion(35);
                }

                break;
            case 36:
                LinearLayout p36 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P36);

                if (checkQuestion(36) == true) {
                    p36.setVisibility(GONE);
                    p = showNextQuestion(36);
                }

                break;
            case 37:
                LinearLayout p37 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P37);

                if (checkQuestion(37) == true) {
                    p37.setVisibility(GONE);
                    p = showNextQuestion(37);
                }

                break;
            case 38:
                LinearLayout p38 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P38);

                if (checkQuestion(38) == true) {
                    p38.setVisibility(GONE);
                    p = showNextQuestion(38);
                }

                break;
            case 39:
                LinearLayout p39 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P39);

                if (checkQuestion(39) == true) {
                    p39.setVisibility(GONE);
                    p = showNextQuestion(39);
                }

                break;
            case 40:
                LinearLayout p40 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P40);

                if (checkQuestion(40) == true) {
                    p40.setVisibility(GONE);
                    p = showNextQuestion(40);
                }

                break;
            case 41:
                LinearLayout p41 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P41);

                if (checkQuestion(41) == true) {
                    p41.setVisibility(GONE);
                    p = showNextQuestion(41);
                }

                break;
            case 42:
                LinearLayout p42 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P42);

                if (checkQuestion(42) == true) {
                    p42.setVisibility(GONE);
                    p = showNextQuestion(42);
                }

                break;

            //End of satistyValidation: Everything OK

        }
        return p;
    }

    public int showQuestion(int show) {
        LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2);
        LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P3);
        //questions.remove(questions.size()-1);
        //questions.get(questions.size()-1);

        switch(show) {
            case 1:

                LinearLayout p1 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P1);
                Button previo = (Button) activity.findViewById(R.id.survey_button_previous);
                previo.setVisibility(GONE);
                p1.setVisibility(VISIBLE);
                show = 1;

                break;
            case 2:

                p2.setVisibility(VISIBLE);
                show = 2;

                break;
            case 3:

                p3.setVisibility(VISIBLE);
                show = 3;

                break;
            case 4:

                LinearLayout p4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4);
                p4.setVisibility(VISIBLE);
                show = 4;

                break;
            case 5:

                LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P5);
                p5.setVisibility(VISIBLE);
                show = 5;

                break;
            case 6:

                LinearLayout p6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6);
                p6.setVisibility(VISIBLE);
                show = 6;

                break;
            case 7:

                LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P7);
                p7.setVisibility(VISIBLE);
                show = 7;

                break;
            case 8:

                RelativeLayout p8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P8);
                p8.setVisibility(VISIBLE);
                show = 8;

                break;
            case 9:

                LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9);
                p9.setVisibility(VISIBLE);
                show = 9;

                break;
            case 10:

                RelativeLayout p10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P10);
                p10.setVisibility(VISIBLE);
                show = 10;

                break;
            case 11:

                LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P11);
                p11.setVisibility(VISIBLE);
                show = 11;

                break;
            case 12:

                LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P12);
                p12.setVisibility(VISIBLE);
                show = 12;

                break;
            case 13:

                RelativeLayout p13 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P13);
                p13.setVisibility(VISIBLE);
                show = 13;

                break;
            case 14:

                LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P14);
                p14.setVisibility(VISIBLE);
                show = 14;

                break;
            case 15:

                LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P15);
                p15.setVisibility(VISIBLE);
                show = 15;

                break;
            case 16:

                LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P16);
                p16.setVisibility(VISIBLE);
                show = 16;

                break;
            case 17:

                LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P17);
                p17.setVisibility(VISIBLE);
                show = 17;

                break;
            case 18:

                LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18);
                p18.setVisibility(VISIBLE);
                show = 18;

                break;
            case 19:

                LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19);
                p19.setVisibility(VISIBLE);
                show = 19;

                break;
            case 20:

                LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20);
                p20.setVisibility(VISIBLE);
                show = 20;

                break;
            case 21:

                LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P21);
                p21.setVisibility(VISIBLE);
                show = 21;

                break;
        }
        return show;
    }

    @Override
    public int onPreviousPressed(int actual, int anterior) {

        LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P2);
        LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P3);
        //questions.remove(questions.size()-1);
        //questions.get(questions.size()-1);

        switch(actual) {
            case 2:

                Button previo = (Button) activity.findViewById(R.id.survey_button_previous);
                previo.setVisibility(GONE);
                p2.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 3:

                p3.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 4:

                LinearLayout p4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P4);
                p4.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 5 :

                LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P5);
                p5.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 6 :

                LinearLayout p6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P6);
                p6.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 7:

                LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P7);
                p7.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 8:

                RelativeLayout p8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P8);
                p8.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 9:

                LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P9);
                p9.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 10:

                RelativeLayout p10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P10);
                p10.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 11:

                LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P11);
                p11.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 12:

                LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P12);
                p12.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 13:

                RelativeLayout p13 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M3_P13);
                p13.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 14:

                LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P14);
                p14.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 15:

                LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P15);
                p15.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 16:

                LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P16);
                p16.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 17:

                LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P17);
                p17.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 18:

                LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P18);
                p18.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 19:

                LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P19);
                p19.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 20:

                LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P20);
                p20.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 21:

                LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P21);
                p21.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 22:

                LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P22);
                p22.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 23:

                LinearLayout p23 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P23);
                p23.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 24:

                LinearLayout p24 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P24);
                p24.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 25:

                LinearLayout p25 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P25);
                p25.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 26:

                LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P26);
                p26.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 27:

                LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P27);
                p27.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 28:

                LinearLayout p28 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P28);
                p28.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 29:

                LinearLayout p29 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P29);
                p29.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 30:

                LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P30);
                p30.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 31:

                LinearLayout p31 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P31);
                p31.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
            case 32:

                LinearLayout p32 = (LinearLayout) activity.findViewById(R.id.survey_layout_M3_P32);
                p32.setVisibility(GONE);
                actual = showQuestion(anterior);

                break;
        }
        return actual;
    }




    @Override
    public Questionnaire fillQuest(Questionnaire quest, boolean throwError) throws Exception {


        //P1
        AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P1);
        String countryText = actvP1.getText().toString();
        String codCountry = dm.getAssociatedCodeFor(countryText, DictionaryManager.COUNTRY);
        if(codCountry != null)
            quest.setCdpaisna(Integer.parseInt(codCountry));
        else
            quest.setCdpaisna(-1);

        //P1 otros literal
        if(codCountry!= null && codCountry.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P1_otros)).getText().toString();
            if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                quest.setCdpaisna_lit(otrosLiteral);
            } else {
                quest.setCdpaisna_lit(null);
            }
        }

        //P2
        AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2);
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
                AutoCompleteTextView actvP2_loc = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2_loc);
                codLoc = dm.getAssociatedCodeFor(actvP2_loc.getText().toString(), DictionaryManager.LOCALIDAD);
                if(codLoc == null || codLoc.isEmpty()) {
                    codLoc = null;
                } else if(codLoc.contentEquals("99999")){
                    String locLit = ((EditText) activity.findViewById(R.id.survey_edit_M3_P2_loc_lit)).getText().toString();
                    String provText = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P2_prov)).getText().toString();
                    String codProv = dm.getAssociatedCodeFor(provText, DictionaryManager.PROVINCIA);
                    if(codProv != null && !codProv.contentEquals("99")){
                        locLit = locLit + ", " + provText;
                    } else {
                        locLit = locLit +", " + ((EditText) activity.findViewById(R.id.survey_edit_M3_P2_prov_lit)).getText().toString();;
                    }
                    quest.setCdlocado_lit(locLit);
                }
            }else{
                Spinner sP2_area = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P2_area);
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
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P2_otros)).getText().toString();
            if(otrosLiteral!= null && !otrosLiteral.isEmpty()){
                quest.setCdpaisre_lit(otrosLiteral);
            } else{
                quest.setCdpaisre_lit(null);
            }

        }

        //P2 Ditrito
        Spinner sP2_dist = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P2_distrito);
        if(sP2_dist.getSelectedItem() != null) {
            String P2_dist = sP2_dist.getSelectedItem().toString();
            if (P2_dist != null && !P2_dist.isEmpty()) {
                String codDist = dm.getAssociatedCodeFor(P2_dist, DictionaryManager.DISTRITO);
                if(codDist!=null){
                    quest.setDistres(Integer.parseInt(codDist));

                    if(codDist.contentEquals("99")){
                        quest.setDistres_lit(((EditText) activity.findViewById(R.id.survey_edit_M3_P2_dis_lit)).getText().toString());
                    }
                }
            }
        }


        //P3
        RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P3);
        int checkedId = rgP3.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P3_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P3_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdcambio(selectedCode);
        }


        //P4
        AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P4);
        String P4text = actvP4.getText().toString();
        String p4code = dm.getAssociatedCodeFor(P4text, DictionaryManager.AIRPORT);
        quest.setCdiaptoo(p4code);

        //P4 otros literal
        if(p4code!= null && p4code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P4_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptoo_lit(otrosLiteral);
            } else{
                quest.setCdiaptoo_lit(null);
            }
        }


        //P5
        AutoCompleteTextView actvP5 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P5);
        String P5text = actvP5.getText().toString();
        String p5code = dm.getAssociatedCodeFor(P5text, DictionaryManager.COMPANY);

        quest.setCiaantes(p5code);

        //P5 otros literal
        if(p5code!= null && p5code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P5_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCiaantes_lit(otrosLiteral);
            } else {
                quest.setCiaantes_lit(null);
            }
        }


        //P6
        RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P6);
        checkedId = rgP6.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P6_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P6_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setConexfac(selectedCode);
        }

        //P6b
        RadioGroup rgP6b = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P6b);
        checkedId = rgP6b.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P6b_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P6b_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setConextour(selectedCode);
        }


        //P7
        RadioGroup rgP7 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P7);
        checkedId = rgP7.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P7_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P7_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdsinope(selectedCode);
        }


        //P8
        RadioGroup rgP8 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P8);
        checkedId = rgP8.getCheckedRadioButtonId();
        if (checkedId  > 0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P8_option1:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_M3_P8_option2:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P8_option3:
                    selectedCode = 7;
                    break;
                case R.id.survey_radio_M3_P8_option4:
                    selectedCode = 9;
                    break;

            }
            quest.setCdalojen(selectedCode);
            //if(selectedCode==9){
            //    EditText etP8otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P8_otros);
                //quest.setCdalojin_lit(etP8otros.getText().toString());
            //}
        }


        //P9
        RadioGroup rgP9 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P9);
        checkedId = rgP9.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P9_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P9_option2_localidad:
                    selectedCode = 2;
                    break;
            }
            quest.setVien_re(selectedCode);
            if(selectedCode==2){
                String textP9Localidad = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_loc))
                        .getText().toString();
                String codLocalidad = dm.getAssociatedCodeFor(textP9Localidad, DictionaryManager.LOCALIDAD_PROC);
                if(codLocalidad== null ||codLocalidad.isEmpty()){
                    codLocalidad = "-1";
                } else if(codLocalidad.contentEquals("99999")){
                    String locLit = ((EditText) activity.findViewById(R.id.survey_edit_M3_P9_loc_lit)).getText().toString();
                    String provText = ((AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P9_prov)).getText().toString();
                    String codProv = dm.getAssociatedCodeFor(provText, DictionaryManager.PROVINCIA_PROC);
                    if(codProv != null && !codProv.contentEquals("99")){
                        locLit = locLit + ", " + provText;
                    } else {
                        locLit = locLit +", " + ((EditText) activity.findViewById(R.id.survey_edit_M3_P9_prov_lit)).getText().toString();;
                    }
                    quest.setCdlocaco_lit(locLit);
                }
                quest.setCdlocaco(Integer.parseInt(codLocalidad));
                //P9 Ditrito
                Spinner sP9_dist = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P9_distrito);
                if(sP9_dist.getSelectedItem() != null) {
                    String P9_dist = sP9_dist.getSelectedItem().toString();
                    if (P9_dist != null && !P9_dist.isEmpty()) {
                        String codDist = dm.getAssociatedCodeFor(P9_dist, DictionaryManager.DISTRITO);
                        if(codDist!=null){
                            quest.setP14a(Integer.parseInt(codDist));

                            if(codDist.contentEquals("99")){
                                quest.setP14a_lit(((EditText) activity.findViewById(R.id.survey_edit_M3_P9_dis_lit)).getText().toString());
                            }
                        }
                    }
                }


            }
        }



        //P10
        RadioGroup rgP10 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P10);
        checkedId = rgP10.getCheckedRadioButtonId();
        if (checkedId  > 0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P10_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P10_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M3_P10_option3:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M3_P10_option4:
                    selectedCode = 7;
                    break;
                case R.id.survey_radio_M3_P10_option5:
                    selectedCode = 8;
                    break;
                case R.id.survey_radio_M3_P10_option6:
                    selectedCode = 10;
                    break;
                case R.id.survey_radio_M3_P10_option7:
                    selectedCode = 9;
                    break;
            }
            quest.setCdalojin(selectedCode);
            if(selectedCode==9){
                EditText etP10otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P10_otros);
                quest.setCdalojin_lit(etP10otros.getText().toString());
            }
        }


        //P11
        RadioGroup rgP11 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P11);
        checkedId = rgP11.getCheckedRadioButtonId();
        if (checkedId  > 0) {
            int nmodos = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P11_option1:
                    nmodos = 1;
                    break;
                case R.id.survey_radio_M3_P11_option2:
                    nmodos = 2;
                    break;
                case R.id.survey_radio_M3_P11_option3:
                    String modosTxt = ((EditText) activity.findViewById(R.id.survey_edit_M3_P11b_numModos)).getText().toString();
                    nmodos = Integer.parseInt(modosTxt);
                    break;

            }
            quest.setNmodos(nmodos);
        }



        //P12
        RadioGroup rgP12 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12);
        checkedId = rgP12.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P12_option1:
                    selectedCode = 11;
                    break;
                case R.id.survey_radio_M3_P12_option2:
                    selectedCode = 25;
                    break;
                case R.id.survey_radio_M3_P12_option3:
                    selectedCode = 22;
                    break;
                case R.id.survey_radio_M3_P12_option4:
                    selectedCode = 23;
                    break;
                case R.id.survey_radio_M3_P12_option5:
                    selectedCode = 35;
                    break;
                case R.id.survey_radio_M3_P12_option6:
                    selectedCode = 37;
                    break;
                case R.id.survey_radio_M3_P12_option7:
                    selectedCode = 39;
                    break;
                case R.id.survey_radio_M3_P12_option8:
                    selectedCode = 38;
                    break;
                case R.id.survey_radio_M3_P12_option6b:
                    selectedCode = 31;
                    break;
                case R.id.survey_radio_M3_P12_option7b:
                    selectedCode = 40;
                    break;
                case R.id.survey_radio_M3_P12_option8b:
                    selectedCode = 30;
                    break;
                case R.id.survey_radio_M3_P12_option9:
                    selectedCode = 42;
                    break;
                case R.id.survey_radio_M3_P12_option10:
                    selectedCode = 43;
                    break;
                case R.id.survey_radio_M3_P12_option11:
                    selectedCode = 91;
                    break;
            }
            quest.setUltimodo(selectedCode);
            if(selectedCode==91){
                EditText etP9otros = (EditText) activity.findViewById(R.id.survey_edit_M3_P12_others);
                quest.setUltimodo_lit(etP9otros.getText().toString());
            }
        }


        //P12b
        RadioGroup rgP12b = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P12b);
        checkedId = rgP12b.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P12b_option1:
                    selectedCode = 11;
                    break;
                case R.id.survey_radio_M3_P12b_option2:
                    selectedCode = 25;
                    break;
                case R.id.survey_radio_M3_P12b_option3:
                    selectedCode = 22;
                    break;
                case R.id.survey_radio_M3_P12b_option4:
                    selectedCode = 23;
                    break;
                case R.id.survey_radio_M3_P12b_option5:
                    selectedCode = 35;
                    break;
                case R.id.survey_radio_M3_P12b_option6:
                    selectedCode = 37;
                    break;
                case R.id.survey_radio_M3_P12b_option7:
                    selectedCode = 39;
                    break;
                case R.id.survey_radio_M3_P12b_option8:
                    selectedCode = 38;
                    break;
                case R.id.survey_radio_M3_P12b_option6b:
                    selectedCode = 31;
                    break;
                case R.id.survey_radio_M3_P12b_option7b:
                    selectedCode = 40;
                    break;
                case R.id.survey_radio_M3_P12b_option8b:
                    selectedCode = 30;
                    break;
                case R.id.survey_radio_M3_P12b_option9:
                    selectedCode = 42;
                    break;
                case R.id.survey_radio_M3_P12b_option10:
                    selectedCode = 43;
                    break;
                case R.id.survey_radio_M3_P12b_option11:
                    selectedCode = 91;
                    break;
            }
            quest.setModo1(selectedCode);
        }


        //P13
        RadioGroup rgP13 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P13);
        checkedId = rgP13.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P13_option1:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M3_P13_option2:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M3_P13_option3:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P13_option4:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M3_P13_option5:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M3_P13_option6:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M3_P13_option7:
                    selectedCode = 9;
                    break;
            }
            quest.setSitiopark(selectedCode);
            if(selectedCode==4){
                EditText etP13b = (EditText) activity.findViewById(R.id.survey_edit_M3_P13b);
                quest.setPqfuera(etP13b.getText().toString());
            }
        }



        //P14
        String P14text = ((EditText) activity.findViewById(R.id.survey_edit_M3_P14)).getText().toString();
        if(P14text!= null && !P14text.isEmpty()){
            quest.setAcomptes(Integer.parseInt(P14text));
        } else {
            quest.setAcomptes(-1);
        }



        //P15
        String P15hora = ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_hora)).getText().toString();
        String P15min = ((EditText) activity.findViewById(R.id.survey_edit_M3_P15_minutos)).getText().toString();


        if(P15hora!= null && !P15hora.isEmpty()){
            if(P15min!= null && !P15min.isEmpty()){
                Calendar llegadaTime = Calendar.getInstance();
                llegadaTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(P15hora));
                llegadaTime.set(Calendar.MINUTE, Integer.parseInt(P15min));
                llegadaTime.set(Calendar.SECOND, 0);
                quest.setHllega(llegadaTime.getTime());
            }
        }



        //P16
        AutoCompleteTextView actvP16 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P16);
        String P16text = actvP16.getText().toString();
        String P16code = dm.getAssociatedCodeFor(P16text, DictionaryManager.AIRPORT);
        quest.setCdiaptod(P16code);

        //P16 otros literal
        if(P16code!= null && P16code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P16_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptod_lit(otrosLiteral);
            } else{
                quest.setCdiaptod_lit(null);
            }
        }




        //P17
        AutoCompleteTextView actvP17 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P17_company);
        String P17text = actvP17.getText().toString();
        String P17code = dm.getAssociatedCodeFor(P17text, DictionaryManager.COMPANY);
        quest.setNumvuepa_comp(P17code);
        EditText P17number = (EditText) activity.findViewById(R.id.survey_edit_M3_P17);
        quest.setNumvuepa(P17code+P17number.getText().toString());

        //P10 otros literal
        if(P17code!= null && P17code.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P17_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setNumvuepa_lit(otrosLiteral);
            } else {
                quest.setNumvuepa_lit(null);
            }
        }




        //P18
        RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P18);
        checkedId = rgP18.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P18_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P18_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdterm(selectedCode);
        }


        //P18b
        AutoCompleteTextView actvP18b = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P18b);
        String companyCode = dm.getAssociatedCodeFor(actvP18b.getText().toString(), DictionaryManager.COMPANY_LONG);
        quest.setCdociaar(companyCode);

        //P18b otros literal
        if(companyCode!= null && companyCode.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P18b_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdociaar_lit(otrosLiteral);
            } else {
                quest.setCdociaar_lit(null);
            }
        }





        //P19
        AutoCompleteTextView actvP19 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M3_P19);
        String airportCode = dm.getAssociatedCodeFor(actvP19.getText().toString(), DictionaryManager.AIRPORT_LONG);
        quest.setCdiaptof(airportCode);

        //P19 otros literal
        if(airportCode!= null && airportCode.contentEquals("999")){
            String otrosLiteral = ((EditText) activity.findViewById(R.id.survey_edit_M3_P19_otros)).getText().toString();
            if(otrosLiteral != null && !otrosLiteral.isEmpty()){
                quest.setCdiaptof_lit(otrosLiteral);
            } else {
                quest.setCdiaptof_lit(null);
            }
        }




        //P20
        Spinner sP20 = (Spinner) activity.findViewById(R.id.survey_spinner_M3_P20);
        if(sP20.getSelectedItem()!=null) {
            String codReason = dm.getAssociatedCodeFor(sP20.getSelectedItem().toString(), DictionaryManager.TRAVEL_REASON);
            if (codReason != null) {
                quest.setCdmviaje(Integer.parseInt(codReason));
            }
        } else {
            quest.setCdmviaje(-1);
        }



        //P21
        RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P21);
        checkedId = rgP21.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = 0;
            switch (checkedId) {
                case R.id.survey_radio_M3_P21_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P21_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdidavue(selectedCode);
        }

        //P21a
        EditText etP21a = (EditText) activity.findViewById(R.id.survey_edit_M3_P21b);
        String textP21a = etP21a.getText().toString();
        if(textP21a!= null && !textP21a.isEmpty()){
            quest.setTaus(Integer.parseInt(textP21a));
        } else {
            quest.setTaus(-1);
        }




        //P22
        RadioGroup rgP22 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P22);
        checkedId = rgP22.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P22_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P22_option3:
                    selectedCode = 2;
                    break;
            }
            if(selectedCode ==  1){
                quest.setNpers(1);
            } else {
                String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas))
                        .getText().toString();
                if(textP22!= null && !textP22.isEmpty()) {
                    quest.setNpers(Integer.parseInt(textP22));
                } else {
                    quest.setNpers(-1);
                }
            }
        } else {
            String textP22 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P22_numPersonas))
                    .getText().toString();
            if(textP22!= null && !textP22.isEmpty()) {
                quest.setNpers(Integer.parseInt(textP22));
            } else {
                quest.setNpers(-1);
            }
        }



        //P23
        String textP23 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P23)).getText().toString();
        if(textP23!=null && !textP23.isEmpty()){
            quest.setNninos(Integer.parseInt(textP23));
        } else {
            quest.setNninos(-1);
        }



        //P24
        RadioGroup rgP24 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P24);
        checkedId = rgP24.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P24_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P24_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M3_P24_option3:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M3_P24_option4:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M3_P24_option5:
                    selectedCode = 9;
                    break;
            }
            quest.setRelacion(selectedCode);
        }




        //P25
        String textP25 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P25_numDias)).getText().toString();
        if(textP25 != null && !textP25.isEmpty()){
            quest.setCdtreser(Integer.parseInt(textP25));
        } else {
            quest.setCdtreser(-1);
        }




        //P26
        RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P26);
        checkedId = rgP26.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P26_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P26_option2:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M3_P26_option3:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M3_P26_option4:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M3_P26_option5:
                    selectedCode = 9;
                    break;
            }
            quest.setCdbillet(selectedCode);
        }



        //P27
        RadioGroup rgP27 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P27);
        checkedId = rgP27.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P27_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P27_option2:
                    selectedCode = 2;
                    break;

            }
            if(selectedCode ==  1){
                quest.setNviaje(0);
            } else {
                String textP27 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes))
                        .getText().toString();
                if(textP27!= null && !textP27.isEmpty()) {
                    quest.setNviaje(Integer.parseInt(textP27));
                } else {
                    quest.setNviaje(-1);
                }
            }
        } else {
            String textP27 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P27_numViajes))
                    .getText().toString();
            if(textP27!= null && !textP27.isEmpty()) {
                quest.setNviaje(Integer.parseInt(textP27));
            } else {
                quest.setNviaje(-1);
            }
        }




        //P28
        String textP28 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P28_numViajes)).getText().toString();
        if(textP28 != null && !textP28.isEmpty()){
            quest.setVol12mes(Integer.parseInt(textP28));
        } else {
            quest.setVol12mes(-1);
        }




        //P29
        RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P29);
        checkedId = rgP29.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P29_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P29_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setP44factu(selectedCode);
        }

        //P29a
        String textP29a = ((EditText) activity.findViewById(R.id.survey_edit_M3_P29b_numBultos)).getText().toString();
        if(textP29a!= null && !textP29a.isEmpty()){
            quest.setBulgrupo(Integer.parseInt(textP29a));
        } else {
            quest.setBulgrupo(-1);
        }





        //P30
        String textP30 = ((EditText) activity.findViewById(R.id.survey_edit_M3_P30_numPersonas)).getText().toString();
        if(textP30!= null && !textP30.isEmpty()){
            quest.setNperbul(Integer.parseInt(textP30));
        } else {
            quest.setNperbul(-1);
        }



        //P31
        RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P31);
        checkedId = rgP31.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P31_option1:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_M3_P31_option2:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P31_option3:
                    selectedCode = 2;
                    break;
            }
            quest.setChekinb(selectedCode);
        }


        //P6b
        RadioGroup rgP30b = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P30b);
        checkedId = rgP30b.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P30b_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P30b_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setDropoff(selectedCode);
        }


        //P32
        RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P32);
        checkedId = rgP32.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P32_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P32_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setConsume(selectedCode);

        }


        //P32a
        String textP32gasto = ((EditText) activity.findViewById(R.id.survey_edit_M3_P32a)).getText().toString();
        if(textP32gasto != null && !textP32gasto.isEmpty()){
            quest.setGas_cons(Integer.parseInt(textP32gasto));
        } else {
            quest.setGas_cons(-1);
        }


        //P33
        RadioGroup rgP33 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P33);
        checkedId = rgP33.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = 0;
            switch (checkedId) {
                case R.id.survey_radio_M3_P33_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P33_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setComprart(selectedCode);
        }

        //P33a
        String textP33a = ((EditText) activity.findViewById(R.id.survey_edit_M3_P33a)).getText().toString();
        if(textP33a != null && !textP33a.isEmpty()){
            quest.setGas_com(Integer.parseInt(textP33a));
        } else {
            quest.setGas_com(-1);
        }

        //P33b
        String selItem1 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item1)).getSelectedItem().toString();
        String codItem1 = dm.getAssociatedCodeFor(selItem1, DictionaryManager.ITEM_TYPE);
        if(codItem1 != null && !codItem1.isEmpty()) {
            quest.setProd1(Integer.parseInt(codItem1));
        } else {
            quest.setProd1(-1);
        }

        String selIteM3 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_iteM3)).getSelectedItem().toString();
        String codIteM3 = dm.getAssociatedCodeFor(selIteM3, DictionaryManager.ITEM_TYPE);
        if(codIteM3 != null && !codIteM3.isEmpty()) {
            quest.setProd2(Integer.parseInt(codIteM3));
        } else {
            quest.setProd2(-1);
        }

        String selItem3 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item3)).getSelectedItem().toString();
        String codItem3 = dm.getAssociatedCodeFor(selItem3, DictionaryManager.ITEM_TYPE);
        if(codItem3 != null && !codItem3.isEmpty()) {
            quest.setProd3(Integer.parseInt(codItem3));
        } else {
            quest.setProd3(-1);
        }

        String selItem4 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item4)).getSelectedItem().toString();
        String codItem4 = dm.getAssociatedCodeFor(selItem4, DictionaryManager.ITEM_TYPE);
        if(codItem4 != null && !codItem4.isEmpty()) {
            quest.setProd4(Integer.parseInt(codItem4));
        } else {
            quest.setProd4(-1);
        }

        String selItem5 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P33b_item5)).getSelectedItem().toString();
        String codItem5 = dm.getAssociatedCodeFor(selItem5, DictionaryManager.ITEM_TYPE);
        if(codItem5 != null && !codItem5.isEmpty()) {
            quest.setProd5(Integer.parseInt(codItem5));
        } else {
            quest.setProd5(-1);
        }



        //P34
        RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P34);
        checkedId = rgP34.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P34_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P34_option2:
                    selectedCode = 2;
                    break;

            }
            quest.setConoceWifi(selectedCode);
        }



        //P35
        RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P35);
        checkedId = rgP35.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P35_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P35_option2:
                    selectedCode = 2;
                    break;

            }
            quest.setUsadoWifi(selectedCode);
        }


        //P36
        RadioGroup rgP36 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P36);
        checkedId = rgP36.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P36_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P36_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M3_P36_option3:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M3_P36_option4:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M3_P36_option5:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M3_P36_option6:
                    selectedCode = 9;
                    break;


            }
            quest.setMotivoWifi(selectedCode);
        }



        //P37
        RadioGroup rgP37 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P37);
        checkedId = rgP37.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P37_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P37_option2:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M3_P37_option3:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M3_P37_option4:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M3_P37_option5:
                    selectedCode = 6;
                    break;

            }
            quest.setCdslab(selectedCode);
        }


        //P38
        RadioGroup rgP38 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P38);
        checkedId = rgP38.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P38_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P38_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M3_P38_option3:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M3_P38_option4:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M3_P38_option5:
                    selectedCode = 0;
                    break;
                case R.id.survey_radio_M3_P38_option6:
                    selectedCode = 9;
                    break;
            }
            quest.setCdsprof(selectedCode);
        }


        //P39
        String textP39 = ((Spinner) activity.findViewById(R.id.survey_spinner_M3_P39)).getSelectedItem().toString();
        String codActivity = dm.getAssociatedCodeFor(textP39, DictionaryManager.ACTIVITY);
        if(codActivity!= null){
            quest.setActiv05(Integer.parseInt(codActivity));

            if(codActivity.contentEquals("99")){
                String textP39otros = ((EditText) activity.findViewById(R.id.survey_edit_M3_P39_otraActividad))
                        .getText().toString();
                quest.setActiv05_lit(textP39otros);
            }
        } else {
            quest.setActiv05(-1);
        }


        //P40
        RadioGroup rgP40 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P40);
        checkedId = rgP40.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P40_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P40_option2:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M3_P40_option3:
                    selectedCode = 5;
                    break;
            }
            quest.setEstudios(selectedCode);
        }


        //P41
        RadioGroup rgP41 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P41);
        checkedId = rgP41.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P41_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P41_option2:
                    selectedCode = 2;
                    break;
                case R.id.survey_radio_M3_P41_option3:
                    selectedCode = 3;
                    break;
                case R.id.survey_radio_M3_P41_option4:
                    selectedCode = 4;
                    break;
                case R.id.survey_radio_M3_P41_option5:
                    selectedCode = 5;
                    break;
                case R.id.survey_radio_M3_P41_option6:
                    selectedCode = 6;
                    break;
                case R.id.survey_radio_M3_P41_option7:
                    selectedCode = 7;
                    break;
                case R.id.survey_radio_M3_P41_option8:
                    selectedCode = 8;
                    break;
                case R.id.survey_radio_M3_P41_option9:
                    selectedCode = 9;
                    break;
                case R.id.survey_radio_M3_P41_option10:
                    selectedCode = 10;
                    break;
                case R.id.survey_radio_M3_P41_option11:
                    selectedCode = 11;
                    break;
                case R.id.survey_radio_M3_P41_option12:
                    selectedCode = 12;
                    break;
            }
            quest.setCdedad(selectedCode);
        }


        //P42
        RadioGroup rgP42 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M3_P42);
        checkedId = rgP42.getCheckedRadioButtonId();
        if(checkedId>0) {
            int selectedCode = -1;
            switch (checkedId) {
                case R.id.survey_radio_M3_P42_option1:
                    selectedCode = 1;
                    break;
                case R.id.survey_radio_M3_P42_option2:
                    selectedCode = 2;
                    break;
            }
            quest.setCdsexo(selectedCode);
        }



        return quest;
    }
}

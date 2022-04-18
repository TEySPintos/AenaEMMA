package com.teys.aenaemma;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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



import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by cgo on 3/10/16.
 */

public class FormModel2 extends Form {

    public FormModel2(String airportName, Activity surveyAct, DictionaryManager dm) {
        super(airportName, surveyAct, dm);
    }

    @Override
    public int getLayoutId() {
        return R.layout.form_model2;
    }

    @Override
    public void initFormView() {


        //Cambiar texto Provincia por Isla
        TextView txtIsla1 = (TextView)activity.findViewById(R.id.survey_radio_M2_P7_option2_localidad);
        TextView txtIsla2 = (TextView)activity.findViewById(R.id.survey_layout_M2_P7_provincia);
        if(airportCode.equals("FUE") || airportCode.equals("ACE") || airportCode.equals("LPA")) {
            txtIsla1.setText(R.string.survey_radio_M2_P7_option2_island);
            txtIsla2.setText(R.string.survey_radio_M2_P7_option2_island);
        }

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

                //((TextView) activity.findViewById(R.id.survey_text_M2_P1)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("IBZ")){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            if(!codProvText.contentEquals("7")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("MAH")){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            if(!codProvText.contentEquals("7")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        }
                        else if(airportCode.contentEquals("TFN") ){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            if(!codProvText.contentEquals("38")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("TFS") ){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            if(!codProvText.contentEquals("38")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("ACE") ){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            //TODO change code
                            if(!codProvText.contentEquals("70")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("FUE") ){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            //TODO change code
                            if(!codProvText.contentEquals("71")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("PMA") ){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            //TODO change code
                            if(!codProvText.contentEquals("35")  && !codProvText.contentEquals("38")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        } else if(airportCode.contentEquals("LPA") ){
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            //TODO change code
                            if(!codProvText.contentEquals("72")){
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        }else if(airportCode.contentEquals("LEI") ) {
                            RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P7_option1);
                            //TODO change code
                            if (!codProvText.contentEquals("72")) {
                                rbResidencia.setEnabled(false);
                                rbResidencia.setChecked(false);
                            } else {
                                rbResidencia.setEnabled(true);
                            }
                        }
                    }
                }
            }
        });


        String[] airportsShort = dm.getStringsArrayFor(DictionaryManager.AIRPORT);

        if(airportCode.contentEquals("IBZ")  || airportCode.contentEquals("MAH") ||  airportCode.contentEquals("TFS") ||  airportCode.contentEquals("FUE") ||  airportCode.contentEquals("ACE")){
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
                                /*llP7.setVisibility(GONE);
                                llP8.setVisibility(GONE);
                                llP09_P11.setVisibility(GONE);
                                llP4.setVisibility(VISIBLE);
                                llP5.setVisibility(VISIBLE);
                                llP6.setVisibility(VISIBLE);*/
                                break;
                            case R.id.survey_radio_M2_P3_option2:
                                /*llP7.setVisibility(VISIBLE);
                                llP8.setVisibility(VISIBLE);
                                llP09_P11.setVisibility(VISIBLE);

                                llP4.setVisibility(GONE);
                                llP5.setVisibility(GONE);
                                llP6.setVisibility(GONE);*/
                                break;
                            default:
                                /*llP7.setVisibility(VISIBLE);
                                llP8.setVisibility(VISIBLE);
                                llP09_P11.setVisibility(VISIBLE);

                                llP4.setVisibility(VISIBLE);
                                llP5.setVisibility(VISIBLE);
                                llP6.setVisibility(VISIBLE);*/
                                break;
                        }

                        //Change text color as answered
                        //((TextView) activity.findViewById(R.id.survey_text_M2_P3)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                    EditText etP4Otros = (EditText) activity.findViewById(R.id.survey_edit_M2_P4_otros);
                    if (airportCode != null && airportCode.contentEquals("999")) {
                        llP4otros.setVisibility(VISIBLE);
                        etP4Otros.requestFocus();
                    } else {
                        llP4otros.setVisibility(GONE);
                        etP4Otros.setText("");
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P4)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    EditText etP5Otros = (EditText) activity.findViewById(R.id.survey_edit_M2_P5_otros);
                    if (selectedCode != null && selectedCode.contentEquals("999")) {
                        llP5otros.setVisibility(VISIBLE);
                        etP5Otros.requestFocus();
                    } else {
                        llP5otros.setVisibility(GONE);
                        etP5Otros.setText("");
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P5)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                        //((TextView) activity.findViewById(R.id.survey_text_M2_P6)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                    }
                }
            });
        }


        //P7

        String P7text = activity.getResources().getString(R.string.survey_text_M2_P7).toString();
        String P7textFormated = String.format(P7text, airportName);
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
                    LinearLayout provLiteralP7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_provLiteral);
                    LinearLayout locLiteralP7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7_locLiteral);
                    RelativeLayout rlP8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P8);
                    LinearLayout blP9_P11 = (LinearLayout) activity.findViewById(R.id.survey_block_M2_P09_P11);
                    RadioButton rbResidencia = (RadioButton) activity.findViewById(R.id.survey_radio_M2_P18_option2);

                    switch(checkedId){
                        case R.id.survey_radio_M2_P7_option1:
                            localidadP7.setVisibility(INVISIBLE);
                            playaP7.setVisibility(INVISIBLE);
                            rlP8.setVisibility(GONE);
                            blP9_P11.setVisibility(VISIBLE);
                            provLiteralP7.setVisibility(GONE);
                            locLiteralP7.setVisibility(GONE);
                            rbResidencia.setEnabled(false);
                            break;
                        case R.id.survey_radio_M2_P7_option2_localidad:
                            loadM2P7Fields();
                            localidadP7.setVisibility(View.VISIBLE);
                            playaP7.setVisibility(INVISIBLE);
                            conexionP7.setVisibility(INVISIBLE);
                            rlP8.setVisibility(VISIBLE);
                            blP9_P11.setVisibility(VISIBLE);
                            rbResidencia.setEnabled(true);
                            break;
                        case R.id.survey_radio_M2_P7_option3:
                            localidadP7.setVisibility(INVISIBLE);
                            playaP7.setVisibility(VISIBLE);
                            conexionP7.setVisibility(INVISIBLE);
                            rlP8.setVisibility(VISIBLE);
                            blP9_P11.setVisibility(VISIBLE);
                            provLiteralP7.setVisibility(GONE);
                            locLiteralP7.setVisibility(GONE);
                            rbResidencia.setEnabled(true);
                            break;
                        case R.id.survey_radio_M2_P7_option4:
                            localidadP7.setVisibility(INVISIBLE);
                            playaP7.setVisibility(INVISIBLE);
                            conexionP7.setVisibility(VISIBLE);
                            rlP8.setVisibility(GONE);
                            blP9_P11.setVisibility(GONE);
                            provLiteralP7.setVisibility(GONE);
                            locLiteralP7.setVisibility(GONE);
                            rbResidencia.setEnabled(true);
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P7)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P8)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //RelativeLayout rlP10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P10);
                    EditText edP9_others = (EditText) activity.findViewById(R.id.survey_edit_M2_P9_others);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P9_option3:
                        case R.id.survey_radio_M2_P9_option4:
                            //rlP10.setVisibility(VISIBLE);
                            edP9_others.setVisibility(INVISIBLE);
                            break;
                        case R.id.survey_radio_M2_P9_option7:
                            //rlP10.setVisibility(GONE);
                            edP9_others.setVisibility(VISIBLE);
                            break;
                        default:
                            //rlP10.setVisibility(GONE);
                            edP9_others.setVisibility(INVISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P9)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P10)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //TextView tvP11 = (TextView) activity.findViewById(R.id.survey_text_M2_P11);
                   // (tvP11).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                        //TextView tvP12 = (TextView) activity.findViewById(R.id.survey_text_M2_P12);
                        //(tvP12).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                        //TextView tvP12 = (TextView) activity.findViewById(R.id.survey_text_M2_P12);
                        //(tvP12).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                //((TextView) activity.findViewById(R.id.survey_text_M2_P13)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                            //((TextView) activity.findViewById(R.id.survey_text_M2_P14)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                AutoCompleteTextView actvP15b = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P15b);
                if(rb.isChecked()){
                    LinearLayout llP15b = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P15b);
                    LinearLayout llP16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P16);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P15_option1:
                            llP15b.setVisibility(GONE);
                            //llP16.setVisibility(GONE);
                            break;
                        default:
                            llP15b.setVisibility(VISIBLE);
                            actvP15b.requestFocus();
                            //llP16.setVisibility(VISIBLE);
                            break;
                    }

                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P15)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                //((TextView) activity.findViewById(R.id.survey_text_M2_P15b)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                //((TextView) activity.findViewById(R.id.survey_text_M2_P16)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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

                //checkM2P33Visibility();
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
                    EditText et18b = (EditText) activity.findViewById(R.id.survey_edit_M2_P18b);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P18_option1:
                            tvP18a.setVisibility(VISIBLE);
                            tvP18b.setVisibility(GONE);
                            llP18num.setVisibility(VISIBLE);
                            et18b.requestFocus();
                            break;
                        case R.id.survey_radio_M2_P18_option2:
                            tvP18a.setVisibility(GONE);
                            tvP18b.setVisibility(VISIBLE);
                            llP18num.setVisibility(VISIBLE);
                            et18b.requestFocus();
                            break;
                        default:
                            tvP18a.setVisibility(INVISIBLE);
                            tvP18b.setVisibility(INVISIBLE);
                            llP18num.setVisibility(INVISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P18)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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

        EditText etP18 = (EditText) activity.findViewById(R.id.survey_edit_M2_P18b);
        etP18.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                EditText etP18 = (EditText) activity.findViewById(R.id.survey_edit_M2_P18b);
                String num = etP18.getText().toString();
                if(num != null && !num.isEmpty()){
                    int numDias = Integer.parseInt(num);




                    //Error P27>P19
                    String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
                    int selectedOption = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19)).getCheckedRadioButtonId();


                    int spP17 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P17)).getSelectedItemPosition();

                    String textP17= Integer.toString(spP17);
                    String textP18 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P18b)).getText().toString();

                    boolean error = false;
                    if(textP17 != null && !textP17.isEmpty()){
                        if(textP18 != null && !textP18.isEmpty()){
                            int numP17 = Integer.parseInt(textP17);
                            int numP18 = Integer.parseInt(textP18);

                            switch(numP17){
                                case 4:
                                    if(numDias<=90){
                                        Toast toast = Toast.makeText(activity, "Es necesario revisar el Propósito principal del viaje (P17) porque no es coherente con el número de días indicados", Toast.LENGTH_LONG);
                                        toast.show();
                                        etP18.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP18.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                                case 18:
                                    if(numDias>7){
                                        Toast toast = Toast.makeText(activity, "Es necesario revisar el Propósito principal del viaje (P17) porque no es coherente con el número de días indicados", Toast.LENGTH_LONG);
                                        toast.show();
                                        etP18.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP18.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                                case 19:
                                    if(numDias<=7){
                                        Toast toast = Toast.makeText(activity, "Es necesario revisar el Propósito principal del viaje (P17) porque no es coherente con el número de días indicados", Toast.LENGTH_LONG);
                                        toast.show();
                                        etP18.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                        error = true;
                                    } else {
                                        etP18.setBackgroundColor(Color.WHITE);
                                    }
                                    break;
                            }

                            /*if(numP27>numP19){
                                Toast toast = Toast.makeText(activity, "El número de personas con bultos (P27) no puede ser mayor que el número total de personas del grupo (P19)", Toast.LENGTH_LONG);
                                toast.show();
                                etP18.setBackgroundColor(activity.getResources().getColor(R.color.aenaRed));
                                error = true;
                            } else {
                                etP18.setBackgroundColor(Color.WHITE);
                            }*/
                        }
                    }



                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P18b)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
                } else {
                    //Change question text color as answered
                    etP18.setBackgroundColor(Color.WHITE);
                    ((TextView) activity.findViewById(R.id.survey_text_M2_P18b)).setTextColor(activity.getResources().getColor(R.color.aenaBlue));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) { }
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
                            //llP20.setVisibility(GONE);
                            //llP21.setVisibility(GONE);
                            break;
                        default:
                            //llP20.setVisibility(VISIBLE);
                            //llP21.setVisibility(VISIBLE);
                            break;
                    }
                    //Change question text color as answered
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P19)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P20)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P21)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                        //((TextView) activity.findViewById(R.id.survey_text_M2_P22)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P23)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P24)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P25)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                            etP29a.requestFocus();
                            break;
                        default:
                            tvP29a.setVisibility(INVISIBLE);
                            etP29a.setVisibility(INVISIBLE);
                            etP29a.setText("");
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
                Spinner sp1 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item1));
                Spinner sp2 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item2));
                Spinner sp3 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item3));
                Spinner sp4 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item4));
                Spinner sp5 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item5));

                RadioButton rb = (RadioButton) activity.findViewById(checkedId);
                EditText etP30a = (EditText) activity.findViewById(R.id.survey_edit_M2_P30a);
                if(rb.isChecked()){
                    LinearLayout llP30a = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P30a);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P30_option1:
                            llP30a.setVisibility(VISIBLE);
                            etP30a.requestFocus();
                            break;
                        default:
                            llP30a.setVisibility(GONE);
                            etP30a.setText("");
                            sp1.setSelection(0);
                            sp2.setSelection(0);
                            sp3.setSelection(0);
                            sp4.setSelection(0);
                            sp5.setSelection(0);
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
                    //RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P32);
                    switch (checkedId){
                        case R.id.survey_radio_M2_P31_option1:
                            //rgP32.setVisibility(VISIBLE);;
                            break;
                        default:
                            //rgP32.setVisibility(GONE);
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P32)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P33)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));
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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P34)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                    //((TextView) activity.findViewById(R.id.survey_text_M2_P35)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
                    // ((TextView) activity.findViewById(R.id.survey_text_M2_P36)).setTextColor(activity.getResources().getColor(R.color.aenaGreen));

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
        final Spinner p2spinner = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P2_area);
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
                EditText P2regionLit = (EditText) activity.findViewById(R.id.survey_edit_M2_P2_region_lit);
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

    public boolean checkQuestion(int check) {

        AutoCompleteTextView actvAeropuertoOrigen = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P4);
        String selectedAirport = actvAeropuertoOrigen.getText().toString();
        String selCode ="";
        int selOpt=-1;

        switch (check){

            case 1:
                //P1 Nacionalidad != null
                AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P1);
                String p1Text = actvP1.getText().toString();
                if(p1Text==null || p1Text.isEmpty()){
                    //Toast toast = Toast.makeText(activity, "Se debe indicar el país de NACIONALIDAD", Toast.LENGTH_LONG);
                    //toast.setGravity(Gravity.CENTER_VERTICAL, 0, -50);
                    //toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 0, 0);
                    //toast.show();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                    builder.setTitle("Incoherencia");
                    builder.setMessage("El número de menores no puede ser superior al número total de personas en el grupo. Es necesario modificar al menos una de las dos respuestas.");
                    builder.setPositiveButton("Aceptar", null);
                    builder.create();
                    builder.show();
                    actvP1.requestFocus();
                    return false;
                }

                break;
            case 3:
                //P3 Conexion != null (solo aplica al cuestionario de PMI
                if(airportCode.contentEquals("PMI") || airportCode.contentEquals("TFN") || airportCode.contentEquals("PMA")) {
                    RadioGroup rgP3 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P3);
                    selOpt = rgP3.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si el pasajero procede de CONEXIÓN", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                        rgP3.requestFocus();
                        return false;
                    }
                }

                break;
            case 4:
                //P4
                selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
                if (selCode == null) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar el aeropuerto de origen", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    actvAeropuertoOrigen.requestFocus();
                    return false;
                }

                //P4 Aeropuerto origen != aeropuerto encuesta
                if (selectedAirport != null && !selectedAirport.isEmpty()) {
                    if (selCode != null && selCode.contentEquals(airportCode)) {
                        Toast toast = Toast.makeText(activity, "El aeropuerto de origen no puede ser igual que el actual", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                        actvAeropuertoOrigen.requestFocus();
                        return false;
                    }
                }

                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P4_otros)).getVisibility() == VISIBLE) {
                    EditText etP4Otros = (EditText) activity.findViewById(R.id.survey_edit_M2_P4_otros);
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
                AutoCompleteTextView actv = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P5);
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
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P5_otros)).getVisibility() == VISIBLE) {
                    EditText etP5Otros = (EditText) activity.findViewById(R.id.survey_edit_M2_P5_otros);
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
                if(((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P6)).getVisibility()==VISIBLE) {
                    RadioGroup rgP6 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P6);
                    selOpt = rgP6.getCheckedRadioButtonId();
                    if (selOpt <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha tenido que RECOGER EQUIPAJE en la conexión", Toast.LENGTH_LONG);
                        toast.show();
                        rgP6.requestFocus();
                        return false;
                    }
                }

                break;
            case 8:
                //P8
                if (((RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P8)).getVisibility() == VISIBLE) {
                    int checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P8)).getCheckedRadioButtonId();
                    if (checkId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE ALOJAMIENTO utilizado durante la estancia", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                    EditText etP8 = (EditText) activity.findViewById(R.id.survey_edit_M2_P8_otros);
                    String textP8 = etP8.getText().toString();

                    RadioGroup rgP8 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P8);
                    selOpt = rgP8.getCheckedRadioButtonId();
                    if(selOpt==R.id.survey_radio_M2_P8_option7) {
                        if (textP8 == null || textP8.isEmpty()) {
                            etP8.setError("Se debe especificar otro tipo de alojamiento");
                            etP8.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 9:
                //P9
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P9)).getVisibility() == VISIBLE) {
                    int checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P9)).getCheckedRadioButtonId();
                    if (checkId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el ÚLTIMO MODO DE TRANSPORTE utilizado para llegar al aeropuerto", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                break;
            case 10:
                //P10
                if (((RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P10)).getVisibility() == VISIBLE) {
                    int checkId = ((RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P10)).getCheckedRadioButtonId();
                    if (checkId <= 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar DÓNDE ha dejado el vehículo", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                break;
            case 11:
                //P11
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P11)).getVisibility() == VISIBLE) {
                    EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M2_P11);
                    String textP11 = etP11.getText().toString();
                    if (textP11 == null || textP11.isEmpty()) {
                        etP11.setError("Se debe indicar el número de acompañantes");
                        etP11.requestFocus();
                        return false;
                    }
                }

                break;
            case 12:
                //P12 Hora llegada != null
                String P12hora = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).getText().toString();
                String P12min = ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_minutos)).getText().toString();


                if(P12hora== null || P12hora.isEmpty() || P12min== null || P12min.isEmpty()){
                    Toast toast = Toast.makeText(activity, "Se debe indicar la HORA completa de LLEGADA al aeropuerto", Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).requestFocus();
                    return false;
                }

                String textHora = P12hora;
                if(textHora != null && !textHora.isEmpty()){
                    int hora = Integer.parseInt(textHora);
                    if(hora<0 || hora>23){
                        Toast toast = Toast.makeText(activity, "El campo hora debe tener un valor comprendido entre 00 y 23",
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
                        Toast toast = Toast.makeText(activity, "El campo minutos debe tener un valor comprendido entre 00 y 59",
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
                            Toast toast = Toast.makeText(activity, "La hora de llegada no puede ser posterior a la hora de realización de la encuesta",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora)).requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 13:
                //P13 not null & != aeropuerto encuesta
                AutoCompleteTextView actvAeropuertoDestino = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P13);
                selectedAirport = actvAeropuertoDestino.getText().toString();

                if(selectedAirport== null || selectedAirport.isEmpty()){
                    Toast toast = Toast.makeText(activity, "El aeropuerto de destino debe estar cumplimentado", Toast.LENGTH_LONG);
                    toast.show();
                    actvAeropuertoDestino.requestFocus();
                    return false;
                }

                if (selectedAirport != null) {
                    selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT);
                    if (selCode != null && selCode.contentEquals(airportCode)) {
                        Toast toast = Toast.makeText(activity, "El aeropuerto de destino no puede ser igual que el actual", Toast.LENGTH_LONG);
                        toast.show();
                        actvAeropuertoDestino.requestFocus();
                        return false;
                    }
                }

                break;
            case 14:
                //P14 Compañia y número de vuelo != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P14)).getVisibility() == VISIBLE) {
                    AutoCompleteTextView actvCompany = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P14_company);
                    String selectedCompany = actvCompany.getText().toString();
                    if (selectedCompany == null || selectedCompany.isEmpty()) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo", Toast.LENGTH_LONG);
                        toast.show();
                        actvCompany.requestFocus();
                        return false;
                    }

                    EditText etFlightNum = (EditText) activity.findViewById(R.id.survey_edit_M2_P14);
                    String number = etFlightNum.getText().toString();
                    if (number == null || number.isEmpty()) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la compañia aérea y el número de vuelo", Toast.LENGTH_LONG);
                        toast.show();
                        etFlightNum.requestFocus();
                        return false;
                    }
                }

                break;
            case 15:
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P15)).getVisibility() == VISIBLE) {

                }

                break;
            case 16:
                //P16 Aeropuerto finalización != aeropuerto encuesta
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P16)).getVisibility() == VISIBLE) {
                    AutoCompleteTextView actvAeropuertoFinal = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P16);
                    selectedAirport = actvAeropuertoFinal.getText().toString();
                    if (selectedAirport != null) {
                        selCode = dm.getAssociatedCodeFor(selectedAirport, DictionaryManager.AIRPORT_LONG);
                        if (selCode != null && selCode.contentEquals(airportCode)) {
                            Toast toast = Toast.makeText(activity, "El aeropuerto de finalización del viaje no puede ser igual que el actual", Toast.LENGTH_LONG);
                            toast.show();
                            actvAeropuertoFinal.requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 17:
                //P17 Motivo viaje != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P17)).getVisibility() == VISIBLE) {
                    Spinner sP17 = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P17);
                    if(sP17.getSelectedItem()==null){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el PROPOSITO PRINCIPAL del viaje", Toast.LENGTH_LONG);
                        toast.show();
                        sP17.requestFocus();
                        return false;
                    }
                }

                break;
            case 18:
                //P18 Viaje IDA o VUELTA != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P18)).getVisibility() == VISIBLE) {
                    RadioGroup rgP18 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P18);
                    selOpt = rgP18.getCheckedRadioButtonId();
                    if(selOpt <= 0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar si el viaje es de IDA o VUELTA", Toast.LENGTH_LONG);
                        toast.show();
                        rgP18.requestFocus();
                        return false;
                    }

                    //P18 Num dias
                    EditText etP18 = (EditText) activity.findViewById(R.id.survey_edit_M2_P18b);
                    String textP18 = etP18.getText().toString();
                    if(textP18==null || textP18.isEmpty()){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE DÍAS de duración del viaje", Toast.LENGTH_LONG);
                        toast.show();
                        etP18.requestFocus();
                        return false;
                    }
                }

                break;
            case 19:
                //P19 Num personas != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P19)).getVisibility() == VISIBLE) {
                    RadioGroup rgP19 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P19);
                    selOpt = rgP19.getCheckedRadioButtonId();
                    if(selOpt <= 0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo", Toast.LENGTH_LONG);
                        toast.show();
                        rgP19.requestFocus();
                        return false;
                    }

                    if(selOpt==R.id.survey_radio_M2_P19_option3){
                        String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
                        if(textP19==null || textP19.isEmpty()){
                            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE PERSONAS en el grupo", Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 20:
                //P20 Num menores
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P20)).getVisibility() == VISIBLE) {
                    EditText etP20 = (EditText) activity.findViewById(R.id.survey_edit_M2_P20);
                    String textP20 = etP20.getText().toString();
                    if (textP20 == null || textP20.isEmpty()) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE MENORES", Toast.LENGTH_LONG);
                        toast.show();
                        etP20.requestFocus();
                        return false;
                    }
                }

                break;
            case 21:
                //P21 != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P21)).getVisibility() == VISIBLE) {
                RadioGroup rgP21 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P21);
                selOpt = rgP21.getCheckedRadioButtonId();
                    if(selOpt<=0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar la RELACIÓN existente entre los componentes del grupo", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }
                }

                break;
            case 22:
                //P22 Tiempo reserva != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P22)).getVisibility() == VISIBLE) {
                    String P22numDias = ((EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias)).getText().toString();
                    if(P22numDias== null || P22numDias.isEmpty()){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el TIEMPO que hace desde la reserva del billete", Toast.LENGTH_LONG);
                        toast.show();
                        ((EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias)).requestFocus();
                        return false;
                    }
                }

                break;
            case 23:
                //P23 Tipo tarifa != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P23)).getVisibility() == VISIBLE) {
                    RadioGroup rgP23 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P23);
                    selOpt = rgP23.getCheckedRadioButtonId();
                    if(selOpt <= 0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE TARIFA del billete", Toast.LENGTH_LONG);
                        toast.show();
                        rgP23.requestFocus();
                        return false;
                    }
                }

                break;
            case 24:
                //P24 Num viajes avion != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P24)).getVisibility() == VISIBLE) {
                    RadioGroup rgP24 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P24);
                    selOpt = rgP24.getCheckedRadioButtonId();
                    if(selOpt <= 0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión", Toast.LENGTH_LONG);
                        toast.show();
                        rgP24.requestFocus();
                        return false;
                    }

                    if(selOpt==R.id.survey_radio_M2_P24_option2){
                        String textP24 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes)).getText().toString();
                        if(textP24==null | textP24.isEmpty()){
                            Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE VIAJES de avión", Toast.LENGTH_LONG);
                            toast.show();
                            ((EditText) activity.findViewById(R.id.survey_edit_M2_P24_numViajes)).requestFocus();
                            return false;
                        }
                    }
                }

                break;
            case 25:
                //P25
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P25)).getVisibility() == VISIBLE) {
                    String textP25 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes)).getText().toString();
                    if(textP25 == null || textP25.isEmpty()){
                        Toast toast = Toast.makeText(activity, "Se debe indicar cuantas veces se ha realizado la MISMA RUTA COMPLETA", Toast.LENGTH_LONG);
                        toast.show();
                        ((EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes)).requestFocus();
                        return false;
                    }

                }

                break;
            case 26:
                //P26 Facturación & Nº bultos != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P26)).getVisibility() == VISIBLE) {
                    RadioGroup rgP26 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P26);
                    int selectedOption = rgP26.getCheckedRadioButtonId();
                    if(selectedOption<0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar si se ha FACTURADO equipaje", Toast.LENGTH_LONG);
                        toast.show();
                        rgP26.requestFocus();
                        return false;

                    } else {
                        if(selectedOption== R.id.survey_radio_M2_P26_option1){
                            EditText etP26num =  (EditText) activity.findViewById(R.id.survey_edit_M2_P26b_numBultos);
                            String numBultos = etP26num.getText().toString();
                            if(numBultos==null || numBultos.isEmpty()){
                                Toast toast = Toast.makeText(activity, "Se debe indicar el NÚMERO DE BULTOS", Toast.LENGTH_LONG);
                                toast.show();
                                etP26num.requestFocus();
                                return false;
                            }
                        }

                    }
                }

                break;
            case 27:
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P27)).getVisibility() == VISIBLE) {

                }

                break;
            case 28:
                //P28 tarjeta embarque != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P28)).getVisibility() == VISIBLE) {
                    RadioGroup rgP28 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P28);
                    selOpt = rgP28.getCheckedRadioButtonId();
                    if(selOpt <= 0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar donde se ha obtenido la TARJETA DE EMBARQUE", Toast.LENGTH_LONG);
                        toast.show();
                        rgP28.requestFocus();
                        return false;
                    }
                }
                break;
            case 29:
                //P29 Consumo restauración != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P29)).getVisibility() == VISIBLE) {
                    RadioGroup rgP29 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P29);
                    selOpt = rgP29.getCheckedRadioButtonId();
                    if(selOpt < 0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar si ha CONSUMIDO algún producto en los servicios de restauración", Toast.LENGTH_LONG);
                        toast.show();
                        rgP29.requestFocus();
                        return false;
                    } else {
                        if (selOpt == R.id.survey_radio_M2_P29_option1) {
                            EditText etP29a = (EditText) activity.findViewById(R.id.survey_edit_M2_P29a);
                            String cant29a = etP29a.getText().toString();
                            if (cant29a == null || cant29a.isEmpty()) {
                                Toast toast = Toast.makeText(activity, "Se debe indicar una cantidad", Toast.LENGTH_LONG);
                                toast.show();
                                etP29a.requestFocus();
                                return false;
                            }
                        }
                    }
                }
                break;
            case 30:
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P30)).getVisibility() == VISIBLE) {

                }
                break;

            case 31:
                //P31 Situación laboral != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P30)).getVisibility() == VISIBLE) {
                    RadioGroup rgP31 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P31);
                    int selectedOptionP31 = rgP31.getCheckedRadioButtonId();
                    if (selectedOptionP31 < 0) {
                        Toast toast = Toast.makeText(activity, "Se debe indicar la SITUACIÓN LABORAL", Toast.LENGTH_LONG);
                        toast.show();
                        rgP31.requestFocus();
                        return false;
                    }
                }

                break;
            case 32:
                //P32 TipoTrabajo != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P32)).getVisibility() == VISIBLE) {
                    RadioGroup rgP32 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P32);
                    int selectedOption = rgP32.getCheckedRadioButtonId();
                    if(selectedOption<0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el TIPO DE TRABAJO del entrevistado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP32.requestFocus();
                        return false;
                    }
                }

                break;
            case 33:
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P33)).getVisibility() == VISIBLE) {

                }

                break;
            case 34:
                //P34 Estudios != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P34)).getVisibility() == VISIBLE) {
                    RadioGroup rgP34 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P34);
                    int selectedOption = rgP34.getCheckedRadioButtonId();
                    if(selectedOption<0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar el NIVEL DE ESTUDIOS del entrevistado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP34.requestFocus();
                        return false;
                    }
                }

                break;
            case 35:
                //P35 Edad != null
                if (((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P35)).getVisibility() == VISIBLE) {
                    RadioGroup rgP35 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P35);
                    int selectedOption = rgP35.getCheckedRadioButtonId();
                    if(selectedOption<0){
                        Toast toast = Toast.makeText(activity, "Se debe indicar la EDAD del entrevistado", Toast.LENGTH_LONG);
                        toast.show();
                        rgP35.requestFocus();
                        return false;
                    }
                }

                break;
        }

        return true;
    }


    @Override
    public int onNextPressed(int p) {
        if(checkQuestion(p)) {
            hideQuestions();
        return showNextQuestion(p);
        } else {
            return 0;
        }
/*VALID


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





        }


































        // Num P19 < P20
        String textP19 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P19_numPersonas)).getText().toString();
        textP20 = ((EditText) activity.findViewById(R.id.survey_edit_M2_P20)).getText().toString();


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








        //P30 Compra articulo != null
        int spin1 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item1)).getSelectedItemPosition();
        int spin2 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item2)).getSelectedItemPosition();
        int spin3 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item3)).getSelectedItemPosition();
        int spin4 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item4)).getSelectedItemPosition();
        int spin5 = ((Spinner) activity.findViewById(R.id.survey_spinner_M2_P30b_item5)).getSelectedItemPosition();
        LinearLayout pl30a = ((LinearLayout) activity.findViewById(R.id.survey_layout_M2_P30a));
        RadioGroup rgP30 = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P30);

        //selOpt = rgP30.getCheckedRadioButtonId();
        selectedOption = rgP30.getCheckedRadioButtonId();
        if(selectedOption <= 0){
           Toast toast = Toast.makeText(activity, "Se debe indicar si ha COMPRADO algún artículo en las tiendas (P30)", Toast.LENGTH_LONG);
            toast.show();
            rgP30.requestFocus();
            return false;

        /*}else if (spin1==0 && spin2==0 && spin3==0 && spin4==0 && spin5==0){
               if (pl30a.getVisibility() == View.VISIBLE) {
                   Toast toast = Toast.makeText(activity, "Se debe especificar al menos un artículo (P30b)", Toast.LENGTH_LONG);
                   toast.show();
                   return false;
            }*/
/*VALID
        } else {
            if (selectedOption == R.id.survey_radio_M2_P30_option1) {
                EditText etP30a = (EditText) activity.findViewById(R.id.survey_edit_M2_P30a);
                String cant30a = etP30a.getText().toString();
                if (cant30a == null || cant30a.isEmpty()) {
                    Toast toast = Toast.makeText(activity, "Se debe indicar una cantidad (P30a)", Toast.LENGTH_LONG);
                    toast.show();
                    etP30a.requestFocus();
                    return false;
                }
                if (spin1==0 && spin2==0 && spin3==0 && spin4==0 && spin5==0) {
                    Toast toast = Toast.makeText(activity, "Se debe especificar al menos un artículo (P30b)", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            }
        }











        //P36 sexo != null
        RadioGroup rgSexo = (RadioGroup) activity.findViewById(R.id.survey_radiogroup_M2_P36);
        if (rgSexo.getCheckedRadioButtonId() == -1) {
            Toast toast = Toast.makeText(activity, "Se debe seleccionar un SEXO del entrevistado", Toast.LENGTH_LONG);
            toast.show();
            rgSexo.requestFocus();
            return false;
        }

VALID*/
       //If all conditions are satisfied then return true



    }

    public int showNextQuestion(int show) {
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

                show = showQuestion(4);

                break;
            case 4:

                show = showQuestion(5);

                break;
            case 5:

                show = showQuestion(6);

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

                show = showQuestion(12);

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

                show = showQuestion(17);

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

                show = showQuestion(22);

                break;
            case 22:

                show = showQuestion(23);

                break;
            case 23:

                show = showQuestion(24);

                break;
            case 24:

                show = showQuestion(25);

                break;
            case 25:

                show = showQuestion(26);

                break;
            case 26:

                show = showQuestion(27);

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

                show = showQuestion(32);

                break;
            case 32:

                show = showQuestion(33);

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


    public int showQuestion(int show) {

        switch(show) {
            case 1:

                LinearLayout p1 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P1);
                Button previo = (Button) activity.findViewById(R.id.survey_button_previous);
                previo.setVisibility(GONE);
                p1.setVisibility(VISIBLE);
                show = 1;

                break;
            case 2:

                LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P2);
                AutoCompleteTextView actvP2 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P2);
                p2.setVisibility(VISIBLE);
                actvP2.requestFocus();
                show = 2;

                break;
            case 3:

                LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P3);
                p3.setVisibility(VISIBLE);
                show = 3;

                break;
            case 4:

                LinearLayout p4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P4);
                AutoCompleteTextView actvP4 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P4);
                p4.setVisibility(VISIBLE);
                actvP4.requestFocus();
                show = 4;

                break;
            case 5:

                LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P5);
                AutoCompleteTextView actvP5 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P5);
                p5.setVisibility(VISIBLE);
                actvP5.requestFocus();
                show = 5;

                break;
            case 6:

                LinearLayout p6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P6);
                p6.setVisibility(VISIBLE);
                show = 6;

                break;
            case 7:

                LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7);
                p7.setVisibility(VISIBLE);
                show = 7;

                break;
            case 8:

                RelativeLayout p8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P8);
                p8.setVisibility(VISIBLE);
                show = 8;

                break;
            case 9:

                LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P9);
                p9.setVisibility(VISIBLE);
                show = 9;

                break;
            case 10:

                RelativeLayout p10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P10);
                p10.setVisibility(VISIBLE);
                show = 10;

                break;
            case 11:

                EditText etP11 = (EditText) activity.findViewById(R.id.survey_edit_M2_P11);
                LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P11);
                p11.setVisibility(VISIBLE);
                etP11.requestFocus();
                show = 11;

                break;
            case 12:

                EditText etP12_hora = (EditText) activity.findViewById(R.id.survey_edit_M2_P12_hora);
                LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P12);
                p12.setVisibility(VISIBLE);
                etP12_hora.requestFocus();
                show = 12;

                break;
            case 13:

                AutoCompleteTextView actvP13 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P13);
                LinearLayout p13 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P13);
                p13.setVisibility(VISIBLE);
                actvP13.requestFocus();
                show = 13;

                break;
            case 14:

                AutoCompleteTextView actvP14 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P14_company);
                LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P14);
                p14.setVisibility(VISIBLE);
                actvP14.requestFocus();
                show = 14;

                break;
            case 15:

                LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P15);
                p15.setVisibility(VISIBLE);
                show = 15;

                break;
            case 16:

                AutoCompleteTextView actvP16 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P16);
                LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P16);
                p16.setVisibility(VISIBLE);
                actvP16.requestFocus();
                show = 16;

                break;
            case 17:

                LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P17);
                p17.setVisibility(VISIBLE);
                show = 17;

                break;
            case 18:

                LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P18);
                p18.setVisibility(VISIBLE);
                show = 18;

                break;
            case 19:

                LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P19);
                p19.setVisibility(VISIBLE);
                show = 19;

                break;
            case 20:

                EditText etP20 = (EditText) activity.findViewById(R.id.survey_edit_M2_P20);
                LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P20);
                p20.setVisibility(VISIBLE);
                etP20.requestFocus();
                show = 20;

                break;
            case 21:

                LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P21);
                p21.setVisibility(VISIBLE);
                show = 21;

                break;
            case 22:

                EditText etP22 = (EditText) activity.findViewById(R.id.survey_edit_M2_P22_numDias);
                LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P22);
                p22.setVisibility(VISIBLE);
                etP22.requestFocus();
                show = 22;

                break;
            case 23:

                LinearLayout p23 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P23);
                p23.setVisibility(VISIBLE);
                show = 23;

                break;
            case 24:

                LinearLayout p24 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P24);
                p24.setVisibility(VISIBLE);
                show = 24;

                break;
            case 25:

                EditText etP25 = (EditText) activity.findViewById(R.id.survey_edit_M2_P25_numViajes);
                LinearLayout pb25 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P25);
                pb25.setVisibility(VISIBLE);
                etP25.requestFocus();
                show = 25;

                break;
            case 26:

                LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P26);
                p26.setVisibility(VISIBLE);
                show = 26;

                break;
            case 27:

                EditText etP27 = (EditText) activity.findViewById(R.id.survey_edit_M2_P27_numPersonas);
                LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P27);
                p27.setVisibility(VISIBLE);
                etP27.requestFocus();
                show = 27;

                break;
            case 28:

                LinearLayout p28 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P28);
                p28.setVisibility(VISIBLE);
                show = 28;

                break;
            case 29:

                LinearLayout p29 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P29);
                p29.setVisibility(VISIBLE);
                show = 29;

                break;
            case 30:

                LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P30);
                p30.setVisibility(VISIBLE);
                show = 30;

                break;
            case 31:

                LinearLayout p31 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P31);
                p31.setVisibility(VISIBLE);
                show = 31;

                break;
            case 32:

                LinearLayout p32 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P32);
                p32.setVisibility(VISIBLE);
                show = 32;

                break;
            case 33:

                LinearLayout p33 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P33);
                p33.setVisibility(VISIBLE);
                show = 33;

                break;
            case 34:

                LinearLayout p34 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P34);
                p34.setVisibility(VISIBLE);
                show = 34;

                break;
            case 35:

                Button guardar = (Button) activity.findViewById(R.id.survey_button_save);
                Button siguiente = (Button) activity.findViewById(R.id.survey_button_next);
                guardar.setVisibility(GONE);
                siguiente.setVisibility(VISIBLE);

                LinearLayout p35 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P35);
                p35.setVisibility(VISIBLE);
                show = 35;

                break;
            case 36:

                LinearLayout p36 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P36);
                p36.setVisibility(VISIBLE);
                show = 36;

                break;
        }
        return show;
    }


    public void hideQuestions() {

        LinearLayout p1 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P1);
        p1.setVisibility(GONE);

        LinearLayout p2 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P2);
        p2.setVisibility(GONE);

        LinearLayout p3 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P3);
        p3.setVisibility(GONE);

        LinearLayout p4 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P4);
        p4.setVisibility(GONE);

        LinearLayout p5 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P5);
        p5.setVisibility(GONE);

        LinearLayout p6 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P6);
        p6.setVisibility(GONE);

        LinearLayout p7 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P7);
        p7.setVisibility(GONE);

        RelativeLayout p8 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P8);
        p8.setVisibility(GONE);

        LinearLayout p9 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P9);
        p9.setVisibility(GONE);

        RelativeLayout p10 = (RelativeLayout) activity.findViewById(R.id.survey_layout_M2_P10);
        p10.setVisibility(GONE);

        LinearLayout p11 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P11);
        p11.setVisibility(GONE);

        LinearLayout p12 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P12);
        p12.setVisibility(GONE);

        LinearLayout p13 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P13);
        p13.setVisibility(GONE);

        LinearLayout p14 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P14);
        p14.setVisibility(GONE);

        LinearLayout p15 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P15);
        p15.setVisibility(GONE);

        LinearLayout p15b = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P15b);
        p15b.setVisibility(GONE);

        LinearLayout p16 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P16);
        p16.setVisibility(GONE);

        LinearLayout p17 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P17);
        p17.setVisibility(GONE);

        LinearLayout p18 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P18);
        p18.setVisibility(GONE);

        LinearLayout p19 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P19);
        p19.setVisibility(GONE);

        LinearLayout p20 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P20);
        p20.setVisibility(GONE);

        LinearLayout p21 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P21);
        p21.setVisibility(GONE);

        LinearLayout p22 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P22);
        p22.setVisibility(GONE);

        LinearLayout p23 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P23);
        p23.setVisibility(GONE);

        LinearLayout p24 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P24);
        p24.setVisibility(GONE);

        LinearLayout pb25 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P25);
        pb25.setVisibility(GONE);

        LinearLayout p26 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P26);
        p26.setVisibility(GONE);

        LinearLayout p27 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P27);
        p27.setVisibility(GONE);

        LinearLayout p28 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P28);
        p28.setVisibility(GONE);

        LinearLayout p29 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P29);
        p29.setVisibility(GONE);

        LinearLayout p30 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P30);
        p30.setVisibility(GONE);

        LinearLayout p31 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P31);
        p31.setVisibility(GONE);

        LinearLayout p32 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P32);
        p32.setVisibility(GONE);

        LinearLayout p33 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P33);
        p33.setVisibility(GONE);

        LinearLayout p34 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P34);
        p34.setVisibility(GONE);

        LinearLayout p35 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P35);
        p35.setVisibility(GONE);

        LinearLayout p36 = (LinearLayout) activity.findViewById(R.id.survey_layout_M2_P36);
        p36.setVisibility(GONE);
    }

    @Override
    public int onPreviousPressed(int actual, int anterior) {

        switch(actual) {
            case 2:

                Button previo = (Button) activity.findViewById(R.id.survey_button_previous);
                previo.setVisibility(GONE);

                hideQuestions();
                actual = showQuestion(anterior);

                break;
            case 36:

                Button siguiente = (Button) activity.findViewById(R.id.survey_button_next);
                siguiente.setVisibility(VISIBLE);
                Button guardar = (Button) activity.findViewById(R.id.survey_button_save);
                guardar.setVisibility(GONE);

                hideQuestions();
                actual = showQuestion(anterior);

                break;
            default:

                hideQuestions();
                actual = showQuestion(anterior);

                break;
        }
        return actual;
    }

    @Override
    public Questionnaire fillQuest(Questionnaire quest,boolean throwError) throws Exception {
        try{

            //P1
            AutoCompleteTextView actvP1 = (AutoCompleteTextView) activity.findViewById(R.id.survey_autoComplete_M2_P1);
            String countryText = actvP1.getText().toString();
            String codCountry = dm.getAssociatedCodeFor(countryText, DictionaryManager.COUNTRY);
            String countryLiteral = ((EditText) activity.findViewById(R.id.survey_autoComplete_M2_P1)).getText().toString();
            if(codCountry != null) {
                quest.setCdpaisna(Integer.parseInt(codCountry));
            }else {
                quest.setCdpaisna(-1);
                quest.setCdpaisna_lit(countryLiteral);
            }

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

            //P2 aerea desconocida literal
            Spinner sP2_area = (Spinner) activity.findViewById(R.id.survey_spinner_M2_P2_area);
           // if(sP2_area.getSelectedItem()!= null && sP2_area.getSelectedItem().contentEquals("999")){
                String areaDescLit = ((EditText) activity.findViewById(R.id.survey_edit_M2_P2_region_lit)).getText().toString();
                if(areaDescLit!= null && !areaDescLit.isEmpty()){
                    quest.setCdlocado_reg(areaDescLit);
                } else{
                    quest.setCdlocado_reg(null);
                }

            //}

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
                    quest.setPlaya(textP7Playa);
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
                        selectedCode = 7;
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

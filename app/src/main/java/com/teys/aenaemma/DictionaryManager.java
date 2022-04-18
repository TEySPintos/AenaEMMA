package com.teys.aenaemma;

import android.app.Activity;

import org.w3c.dom.NamedNodeMap;

import java.util.ArrayList;

/**
 * Created by cgo on 20/06/16.
 */
public class DictionaryManager {

    public static final int SURVEY_AIRPORT = 1001;
    public static final int COUNTRY = 1002;
    public static final int LOCALIDAD = 1003;
    public static final int AIRPORT = 1004;
    public static final int COMPANY = 1005;
    public static final int TRAVEL_REASON = 1006;
    public static final int FLIGHT_REASON = 1007;
    public static final int ITEM_TYPE = 1008;
    public static final int ACTIVITY = 1009;
    public static final int LANGUAGE = 1010;
    public static final int PROVINCIA = 1011;
    public static final int COMPANY_LONG = 1012;
    public static final int AIRPORT_LONG = 1013;
    public static final int PROVINCIA_PROC = 1014;
    public static final int LOCALIDAD_PROC = 1015;
    public static final int PROVINCIA_RES = 1016;
    public static final int DISTRITO = 1017;

    private Activity activity;
    private String airportCode="";

    public DictionaryManager(Activity activity) {
        this.activity = activity;
    }

    public void setAirportSelection(String airportName){
        if(airportName!= null){
            String[] airportStrings = activity.getResources().getStringArray(R.array.surveyAirportStrings);
            String[] airportCodes = activity.getResources().getStringArray(R.array.surveyAirportCodes);
            airportCode = getCode(airportName,airportStrings,airportCodes);
            if(airportCode==null){
                airportCode="";
            }
        }
    }



    public static String getCode (String option, String[] stringsArray, String[] codesArray){
        String code = null;
        if (stringsArray.length != codesArray.length){
            return null;
        }

        for (int i = 0; i < stringsArray.length; i++) {
            if(option.contentEquals(stringsArray[i])){
                code = codesArray[i];
            }
        }

        return code;
    }

    public static int getValue (String option, String[] stringsArray, int[] codesArray){
        int code = -1;
        if (stringsArray.length != codesArray.length){
            return -99;
        }

        for (int i = 0; i < stringsArray.length; i++) {
            if(option.contentEquals(stringsArray[i])){
                code = codesArray[i];
            }
        }

        return code;
    }

    public String getAssociatedCodeFor(String option, int dictionaryID){
        String resultCode = null;
        if(option == null || option.isEmpty()){
            return null;
        }
        int stringsResourceId = 0, codesResourceId = 0;
        boolean isCodeInteger = false;

        switch (dictionaryID){
            case SURVEY_AIRPORT:
                stringsResourceId = R.array.surveyAirportStrings;
                codesResourceId = R.array.surveyAirportCodes;
                isCodeInteger = false;
                break;
            case COUNTRY:
                stringsResourceId = getStringsResourceIdForCountry();
                codesResourceId = getCodesResourceIdForCountry();
                isCodeInteger =true;
                break;
            case LOCALIDAD:
                stringsResourceId = getStringsResourceIdForLocalidad();
                codesResourceId = getCodesResourceIdForLocalidad();
                isCodeInteger = true;
                break;
            case LOCALIDAD_PROC:
                stringsResourceId = getStringsResourceIdForLocalidadProc();
                codesResourceId = getCodesResourceIdForLocalidadProc();
                isCodeInteger = true;
                break;
            case AIRPORT:
                stringsResourceId = getStringsResourceIdForAirport();
                codesResourceId = getCodesResourceIdForAirport();
                isCodeInteger = false;
                break;
            case AIRPORT_LONG:
                stringsResourceId = getStringsResourceIdForAirportLong();
                codesResourceId = getCodesResourceIdForAirportLong();
                isCodeInteger = false;
                break;
            case COMPANY:
                stringsResourceId = getStringsResourceIdForCompany();
                codesResourceId = getCodesResourceIdForCompany();
                isCodeInteger = false;
                break;
            case COMPANY_LONG:
                stringsResourceId = getStringsResourceIdForCompanyLong();
                codesResourceId = getCodesResourceIdForCompanyLong();
                isCodeInteger = false;
                break;
            case PROVINCIA:
                stringsResourceId = getStringsResourceIdForProvincia();
                codesResourceId = getCodesResourceIdForProvincia();
                isCodeInteger = true;
                break;
            case PROVINCIA_PROC:
                stringsResourceId = getStringsResourceIdForProvinciaProc();
                codesResourceId = getCodesResourceIdForProvinciaProc();
                isCodeInteger = true;
                break;
            case DISTRITO:
                stringsResourceId = getStringsResourceIdForDistrito();
                codesResourceId = getCodesResourceIdForDistrito();
                isCodeInteger = true;
                break;
            case TRAVEL_REASON:
                stringsResourceId = R.array.p14strings;
                codesResourceId = R.array.p14values;
                isCodeInteger = true;
                break;
            case FLIGHT_REASON:
                stringsResourceId = R.array.p27strings;
                codesResourceId = R.array.p27values;
                isCodeInteger = true;
                break;
            case ITEM_TYPE:
                stringsResourceId = R.array.p30bstrings;
                codesResourceId = R.array.p30bvalues;
                isCodeInteger = true;
                break;
            case ACTIVITY:
                stringsResourceId = R.array.p33strings;
                codesResourceId = R.array.p33values;
                isCodeInteger = true;
                break;
            case LANGUAGE:
                stringsResourceId = R.array.languageStrings;
                codesResourceId = R.array.languageCode;
                isCodeInteger = false;
                break;

        }

        if(isCodeInteger) {
            String[] stringsArray = activity.getResources().getStringArray(stringsResourceId);
            int[] codesArray = activity.getResources().getIntArray(codesResourceId);
            int resultValue = getValue(option, stringsArray, codesArray);
            if (resultValue > 0) {
                resultCode = String.valueOf(resultValue);
            } else {
                resultCode = null;
            }
        } else {
            String[] stringsArray = activity.getResources().getStringArray(stringsResourceId);
            String[] codesArray = activity.getResources().getStringArray(codesResourceId);
            resultCode = getCode(option, stringsArray,codesArray);
        }


        return resultCode;
    }





    public String[] getStringsArrayFor(int dictionaryID){
        int resourceId=0;
        switch (dictionaryID) {
            case AIRPORT:
                resourceId = getStringsResourceIdForAirport();
                break;
            case AIRPORT_LONG:
                resourceId = getStringsResourceIdForAirportLong();
                break;
            case COMPANY:
                resourceId = getStringsResourceIdForCompany();
                break;
            case COMPANY_LONG:
                resourceId = getStringsResourceIdForCompanyLong();
                break;
            case COUNTRY:
                resourceId = getStringsResourceIdForCountry();
                break;
            case LOCALIDAD:
                resourceId = getStringsResourceIdForLocalidad();
                break;
            case LOCALIDAD_PROC:
                resourceId = getStringsResourceIdForLocalidadProc();
                break;
            case PROVINCIA:
                resourceId = getStringsResourceIdForProvincia();
                break;
            case PROVINCIA_PROC:
                resourceId = getStringsResourceIdForProvinciaProc();
                break;
        }
        return activity.getResources().getStringArray(resourceId);
    }




   private int getStringsResourceIdForAirport(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.airportsSVQStrings;
                break;
            case "PMI":
                resourceId = R.array.airportsPMIStrings;
                break;
            case "IBZ":
                resourceId = R.array.airportsIBZStrings;
                break;
            case "MAH":
                resourceId = R.array.airportsMAHStrings;
                break;
            case "OVD":
                resourceId = R.array.airportsOVDStrings;
                break;
            case "AGP":
                resourceId = R.array.airportsAGPStrings;
                break;
            case "BIO":
                resourceId = R.array.airportsBIOStrings;
                break;
            case "MAD":
                resourceId = R.array.airportsMADStrings;
                break;
            case "BCN":
                resourceId = R.array.airportsBCNStrings;
                break;
            case "TFN":
                resourceId = R.array.airportsTFNStrings;
                break;
            case "TFS":
                resourceId = R.array.airportsTFSStrings;
                break;
            case "ACE":
                resourceId = R.array.airportsACEStrings;
                break;
            case "FUE":
                resourceId = R.array.airportsFUEStrings;
                break;
            case "LPA":
                resourceId = R.array.airportsLPAStrings;
                break;
            case "LEI":
                resourceId = R.array.airportsLEIStrings;
                break;
            case "SDR":
                resourceId = R.array.airportsSDRStrings;
                break;
            case "REU":
                resourceId = R.array.airportsREUStrings;
                break;
            case "ALC":
                resourceId = R.array.airportsALCStrings;
                break;
            case "GRX":
                resourceId = R.array.airportsGRXStrings;
                break;
            default:
                resourceId = R.array.airportStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForAirport(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.airportsSVQCodes;
                break;
            case "PMI":
                resourceId = R.array.airportsPMICodes;
                break;
            case "IBZ":
                resourceId = R.array.airportsIBZCodes;
                break;
            case "MAH":
                resourceId = R.array.airportsMAHCodes;
                break;
            case "OVD":
                resourceId = R.array.airportsOVDCodes;
                break;
            case "AGP":
                resourceId = R.array.airportsAGPCodes;
                break;
            case "BIO":
                resourceId = R.array.airportsBIOCodes;
                break;
            case "MAD":
                resourceId = R.array.airportsMADCodes;
                break;
            case "BCN":
                resourceId = R.array.airportsBCNCodes;
                break;
            case "TFN":
                resourceId = R.array.airportsTFNCodes;
                break;
            case "TFS":
                resourceId = R.array.airportsTFSCodes;
                break;
            case "ACE":
                resourceId = R.array.airportsACECodes;
                break;
            case "FUE":
                resourceId = R.array.airportsFUECodes;
                break;
            case "LPA":
                resourceId = R.array.airportsLPACodes;
                break;
            case "LEI":
                resourceId = R.array.airportsLEICodes;
                break;
            case "SDR":
                resourceId = R.array.airportsSDRCodes;
                break;
            case "REU":
                resourceId = R.array.airportsREUCodes;
                break;
            case "ALC":
                resourceId = R.array.airportsALCCodes;
                break;
            case "GRX":
                resourceId = R.array.airportsGRXCodes;
                break;
            default:
                resourceId = R.array.airportCodes;
                break;
        }
        return resourceId;
    }


    private int getStringsResourceIdForAirportLong(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.airportsSVQStrings;
                break;
            case "PMI":
                resourceId = R.array.airportsPMIStringsLong;
                break;
            case "IBZ":
                resourceId = R.array.airportsIBZStringsLong;
                break;
            case "MAH":
                resourceId = R.array.airportsMAHStringsLong;
                break;
            case "OVD":
                resourceId = R.array.airportsOVDStringsLong;
                break;
            case "AGP":
                resourceId = R.array.airportsAGPStringsLong;
                break;
            case "BIO":
                resourceId = R.array.airportsBIOStringsLong;
                break;
            case "MAD":
                resourceId = R.array.airportsMADStringsLong;
                break;
            case "BCN":
                resourceId = R.array.airportsBCNStringsLong;
                break;
            case "TFN":
                resourceId = R.array.airportsTFNStringsLong;
                break;
            case "TFS":
                resourceId = R.array.airportsTFSStringsLong;
                break;
            case "ACE":
                resourceId = R.array.airportsACEStringsLong;
                break;
            case "FUE":
                resourceId = R.array.airportsFUEStringsLong;
                break;
            case "LPA":
                resourceId = R.array.airportsLPAStringsLong;
                break;
            case "LEI":
                resourceId = R.array.airportsLEIStringsLong;
                break;
            case "SDR":
                resourceId = R.array.airportsSDRStringsLong;
                break;
            case "REU":
                resourceId = R.array.airportsREUStringsLong;
                break;
            case "ALC":
                resourceId = R.array.airportsALCStringsLong;
                break;
            case "GRX":
                resourceId = R.array.airportsGRXStringsLong;
                break;
            default:
                resourceId = R.array.airportStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForAirportLong(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.airportsSVQCodes;
                break;
            case "PMI":
                resourceId = R.array.airportsPMICodesLong;
                break;
            case "IBZ":
                resourceId = R.array.airportsIBZCodesLong;
                break;
            case "MAH":
                resourceId = R.array.airportsMAHCodesLong;
                break;
            case "OVD":
                resourceId = R.array.airportsOVDCodesLong;
                break;
            case "AGP":
                resourceId = R.array.airportsAGPCodesLong;
                break;
            case "BIO":
                resourceId = R.array.airportsBIOCodesLong;
                break;
            case "MAD":
                resourceId = R.array.airportsMADCodesLong;
                break;
            case "BCN":
                resourceId = R.array.airportsBCNCodesLong;
                break;
            case "TFN":
                resourceId = R.array.airportsTFNCodesLong;
                break;
            case "TFS":
                resourceId = R.array.airportsTFSCodesLong;
                break;
            case "ACE":
                resourceId = R.array.airportsACECodesLong;
                break;
            case "FUE":
                resourceId = R.array.airportsFUECodesLong;
                break;
            case "LPA":
                resourceId = R.array.airportsLPACodesLong;
                break;
            case "LEI":
                resourceId = R.array.airportsLEICodesLong;
                break;
            case "SDR":
                resourceId = R.array.airportsSDRCodesLong;
                break;
            case "REU":
                resourceId = R.array.airportsREUCodesLong;
                break;
            case "ALC":
                resourceId = R.array.airportsALCCodesLong;
                break;
            case "GRX":
                resourceId = R.array.airportsGRXCodesLong;
                break;
            default:
                resourceId = R.array.airportCodes;
                break;
        }
        return resourceId;
    }

    private int getStringsResourceIdForCompany(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.companiesSVQStrings;
                break;
            case "PMI":
                resourceId = R.array.companiesPMIStrings;
                break;
            case "IBZ":
                resourceId = R.array.companiesIBZStrings;
                break;
            case "MAH":
                resourceId = R.array.companiesMAHStrings;
                break;
            case "OVD":
                resourceId = R.array.companiesOVDStrings;
                break;
            case "AGP":
                resourceId = R.array.companiesAGPStrings;
                break;
            case "BIO":
                resourceId = R.array.companiesBIOStrings;
                break;
            case "MAD":
                resourceId = R.array.companiesMADStrings;
                break;
            case "BCN":
                resourceId = R.array.companiesBCNStrings;
                break;
            case "TFN":
                resourceId = R.array.companiesTFNStrings;
                break;
            case "TFS":
                resourceId = R.array.companiesTFSStrings;
                break;
            case "ACE":
                resourceId = R.array.companiesACEStrings;
                break;
            case "FUE":
                resourceId = R.array.companiesFUEStrings;
                break;
            case "LPA":
                resourceId = R.array.companiesLPAStrings;
                break;
            case "LEI":
                resourceId = R.array.companiesLEIStrings;
                break;
            case "SDR":
                resourceId = R.array.companiesSDRStrings;
                break;
            case "REU":
                resourceId = R.array.companiesREUStrings;
                break;
            case "ALC":
                resourceId = R.array.companiesALCStrings;
                break;
            case "GRX":
                resourceId = R.array.companiesGRXStrings;
                break;
            default:
                resourceId = R.array.companyStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForCompany(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.companiesSVQCodes;
                break;
            case "PMI":
                resourceId = R.array.companiesPMICodes;
                break;
            case "IBZ":
                resourceId = R.array.companiesIBZCodes;
                break;
            case "MAH":
                resourceId = R.array.companiesMAHCodes;
                break;
            case "OVD":
                resourceId = R.array.companiesOVDCodes;
                break;
            case "AGP":
                resourceId = R.array.companiesAGPCodes;
                break;
            case "BIO":
                resourceId = R.array.companiesBIOCodes;
                break;
            case "MAD":
                resourceId = R.array.companiesMADCodes;
                break;
            case "BCN":
                resourceId = R.array.companiesBCNCodes;
                break;
            case "TFN":
                resourceId = R.array.companiesTFNCodes;
                break;
            case "TFS":
                resourceId = R.array.companiesTFSCodes;
                break;
            case "ACE":
                resourceId = R.array.companiesACECodes;
                break;
            case "FUE":
                resourceId = R.array.companiesFUECodes;
                break;
            case "LPA":
                resourceId = R.array.companiesLPACodes;
                break;
            case "LEI":
                resourceId = R.array.companiesLEICodes;
                break;
            case "SDR":
                resourceId = R.array.companiesSDRCodes;
                break;
            case "REU":
                resourceId = R.array.companiesREUCodes;
                break;
            case "ALC":
                resourceId = R.array.companiesALCCodes;
                break;
            case "GRX":
                resourceId = R.array.companiesGRXCodes;
                break;
            default:
                resourceId = R.array.companyCodes;
                break;
        }
        return resourceId;
    }

    private int getStringsResourceIdForCompanyLong(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.companiesSVQStrings;
                break;
            case "PMI":
                resourceId = R.array.companiesPMIStringsLong;
                break;
            case "IBZ":
                resourceId = R.array.companiesIBZStringsLong;
                break;
            case "MAH":
                resourceId = R.array.companiesMAHStringsLong;
                break;
            case "OVD":
                resourceId = R.array.companiesOVDStringsLong;
                break;
            case "AGP":
                resourceId = R.array.companiesAGPStringsLong;
                break;
            case "BIO":
                resourceId = R.array.companiesBIOStringsLong;
                break;
            case "MAD":
                resourceId = R.array.companiesMADStringsLong;
                break;
            case "BCN":
                resourceId = R.array.companiesBCNStringsLong;
                break;
            case "TFN":
                resourceId = R.array.companiesTFNStringsLong;
                break;
            case "TFS":
                resourceId = R.array.companiesTFSStringsLong;
                break;
            case "ACE":
                resourceId = R.array.companiesACEstringsLong;
                break;
            case "FUE":
                resourceId = R.array.companiesFUEstringsLong;
                break;
            case "LPA":
                resourceId = R.array.companiesLPAStringsLong;
                break;
            case "LEI":
                resourceId = R.array.companiesLEIStringsLong;
                break;
            case "SDR":
                resourceId = R.array.companiesSDRStringsLong;
                break;
            case "REU":
                resourceId = R.array.companiesREUStringsLong;
                break;
            case "ALC":
                resourceId = R.array.companiesALCStringsLong;
                break;
            case "GRX":
                resourceId = R.array.companiesGRXStringsLong;
                break;
            default:
                resourceId = R.array.companyStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForCompanyLong(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.companiesSVQCodes;
                break;
            case "PMI":
                resourceId = R.array.companiesPMICodesLong;
                break;
            case "IBZ":
                resourceId = R.array.companiesIBZCodesLong;
                break;
            case "MAH":
                resourceId = R.array.companiesMAHCodesLong;
                break;
            case "OVD":
                resourceId = R.array.companiesOVDCodesLong;
                break;
            case "AGP":
                resourceId = R.array.companiesAGPCodesLong;
                break;
            case "BIO":
                resourceId = R.array.companiesBIOCodesLong;
                break;
            case "MAD":
                resourceId = R.array.companiesMADCodesLong;
                break;
            case "BCN":
                resourceId = R.array.companiesBCNCodesLong;
                break;
            case "TFN":
                resourceId = R.array.companiesTFNCodesLong;
                break;
            case "TFS":
                resourceId = R.array.companiesTFSCodesLong;
                break;
            case "ACE":
                resourceId = R.array.companiesACECodesLong;
                break;
            case "FUE":
                resourceId = R.array.companiesFUECodesLong;
                break;
            case "LPA":
                resourceId = R.array.companiesLPACodesLong;
                break;
            case "LEI":
                resourceId = R.array.companiesLEICodesLong;
                break;
            case "SDR":
                resourceId = R.array.companiesSDRCodesLong;
                break;
            case "REU":
                resourceId = R.array.companiesREUCodesLong;
                break;
            case "ALC":
                resourceId = R.array.companiesALCCodesLong;
                break;
            case "GRX":
                resourceId = R.array.companiesGRXCodesLong;
                break;
            default:
                resourceId = R.array.companyCodes;
                break;
        }
        return resourceId;
    }

    private int getStringsResourceIdForLocalidad(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.localidadStrings;
                break;
            case "PMI":
                resourceId = R.array.localidadStrings;
                break;
            case "IBZ":
                resourceId = R.array.localidadStrings;
                break;
            case "MAH":
                resourceId = R.array.localidadStrings;
                break;
            case "OVD":
                resourceId = R.array.localidadStrings;
                break;
            default:
                resourceId = R.array.localidadStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForLocalidad(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.localidadValues;
                break;
            case "PMI":
                resourceId = R.array.localidadValues;
                break;
            case "IBZ":
                resourceId = R.array.localidadValues;
                break;
            case "MAH":
                resourceId = R.array.localidadValues;
                break;
            case "OVD":
                resourceId = R.array.localidadValues;
                break;
            default:
                resourceId = R.array.localidadValues;
                break;
        }
        return resourceId;
    }

    private int getProvCodesResourceIdForLocalidad(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.provCodeMunicipios;
                break;
            case "PMI":
                resourceId = R.array.provCodeMunicipios;
                break;
            case "IBZ":
                resourceId = R.array.provCodeMunicipios;
                break;
            case "MAH":
                resourceId = R.array.provCodeMunicipios;
                break;
            case "OVD":
                resourceId = R.array.provCodeMunicipios;
                break;
            default:
                resourceId = R.array.provCodeMunicipios;
                break;
        }
        return resourceId;
    }

    private int getStringsResourceIdForLocalidadProc(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.localidadSVQStrings;
                break;
            case "PMI":
                resourceId = R.array.localidadPMIStrings;
                break;
            case "IBZ":
                resourceId = R.array.localidadIBZStrings;
                break;
            case "MAH":
                resourceId = R.array.localidadIBZStrings;
                break;
            case "OVD":
                resourceId = R.array.localidadOVDStrings;
                break;
            case "AGP":
                resourceId = R.array.localidadAGPStrings;
                break;
            case "BIO":
                resourceId = R.array.localidadBIOStrings;
                break;
            case "TFN":
            case "TFS":
                resourceId = R.array.localidadTFStrings;
                break;
            case "ACE":
                resourceId = R.array.localidadACEStrings;
                break;
            case "FUE":
                resourceId = R.array.localidadFUEStrings;
                break;
            case "LPA":
                resourceId = R.array.localidadLPAStrings;
                break;
            default:
                resourceId = R.array.localidadStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForLocalidadProc(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.localidadSVQValues;
                break;
            case "PMI":
                resourceId = R.array.localidadPMIValues;
                break;
            case "IBZ":
                resourceId = R.array.localidadIBZValues;
                break;
            case "MAH":
                resourceId = R.array.localidadIBZValues;
                break;
            case "OVD":
                resourceId = R.array.localidadOVDValues;
                break;
            case "AGP":
                resourceId = R.array.localidadAGPValues;
                break;
            case "BIO":
                resourceId = R.array.localidadBIOValues;
                break;
            case "TFN":
            case "TFS":
                resourceId = R.array.localidadTFValues;
                break;
            case "ACE":
                resourceId = R.array.localidadACEValues;
                break;
            case "FUE":
                resourceId = R.array.localidadFUEValues;
                break;
            case "LPA":
                resourceId = R.array.localidadLPAValues;
                break;
            default:
                resourceId = R.array.localidadValues;
                break;
        }
        return resourceId;
    }

    private int getProvCodesResourceIdForLocalidadProc(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.provCodeMunicipiosSVQ;
                break;
            case "PMI":
                resourceId = R.array.provCodeMunicipiosPMI;
                break;
            case "IBZ":
                resourceId = R.array.provCodeMunicipiosIBZ;
                break;
            case "MAH":
                resourceId = R.array.provCodeMunicipiosIBZ;
                break;
            case "OVD":
                resourceId = R.array.provCodeMunicipiosOVD;
                break;
            case "AGP":
                resourceId = R.array.provCodeMunicipiosAGP;
                break;
            case "BIO":
                resourceId = R.array.provCodeMunicipiosBIO;
                break;
            case "TFN":
            case "TFS":
                resourceId = R.array.provCodeMunicipiosTF;
                break;
            case "ACE":
                resourceId = R.array.provCodeMunicipiosACE;
                break;
            case "FUE":
                resourceId = R.array.provCodeMunicipiosFUE;
                break;
            case "LPA":
                resourceId = R.array.provCodeMunicipiosLPA;
                break;
            default:
                resourceId = R.array.provCodeMunicipios;
                break;
        }
        return resourceId;
    }


    private int getStringsResourceIdForProvincia(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.provinciaStrings;
                break;
            case "PMI":
                resourceId = R.array.provinciaStrings;
                break;
            case "IBZ":
                resourceId = R.array.provinciaStrings;
                break;
            case "MAH":
                resourceId = R.array.provinciaStrings;
                break;
            default:
                resourceId = R.array.provinciaStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForProvincia(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.provinciaValues;
                break;
            case "PMI":
                resourceId = R.array.provinciaValues;
                break;
            case "IBZ":
                resourceId = R.array.provinciaValues;
                break;
            case "MAH":
                resourceId = R.array.provinciaValues;
                break;
            default:
                resourceId = R.array.provinciaValues;
                break;
        }
        return resourceId;
    }

    private int getStringsResourceIdForProvinciaProc(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.provinciaSVQStrings;
                break;
            case "PMI":
                resourceId = R.array.provinciaPMIStrings;
                break;
            case "IBZ":
                resourceId = R.array.provinciaProcIBZStrings;
                break;
            case "MAH":
                resourceId = R.array.provinciaProcMAHStrings;
                break;
            case "OVD":
                resourceId = R.array.provinciaProcOVDStrings;
                break;
            case "AGP":
                resourceId = R.array.provinciaProcAGPStrings;
                break;
            case "BIO":
                resourceId = R.array.provinciaProcBIOStrings;
                break;
            case "MAD":
                resourceId = R.array.provinciaProcMADStrings;
                break;
            case "BCN":
                resourceId = R.array.provinciaProcBCNStrings;
                break;
            case "TFN":
                resourceId = R.array.provinciaProcTFNStrings;
                break;
            case "TFS":
                resourceId = R.array.provinciaProcTFSStrings;
                break;
            case "ACE":
                resourceId = R.array.provinciaProcACEStrings;
                break;
            case "FUE":
                resourceId = R.array.provinciaProcFUEStrings;
                break;
            case "LPA":
                resourceId = R.array.provinciaProcLPAStrings;
                break;
            case "LEI":
                resourceId = R.array.provinciaProcLEIStrings;
                break;
            case "REU":
                resourceId = R.array.provinciaProcREUStrings;
                break;
            case "ALC":
                resourceId = R.array.provinciaProcALCStrings;
                break;
            case "GRX":
                resourceId = R.array.provinciaProcGRXStrings;
                break;
            default:
                resourceId = R.array.provinciaStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForProvinciaProc(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.provinciaSVQValues;
                break;
            case "PMI":
                resourceId = R.array.provinciaPMIValues;
                break;
            case "IBZ":
                resourceId = R.array.provinciaProcIBZValues;
                break;
            case "MAH":
                resourceId = R.array.provinciaProcMAHValues;
                break;
            case "OVD":
                resourceId = R.array.provinciaProcOVDValues;
                break;
            case "AGP":
                resourceId = R.array.provinciaProcAGPValues;
                break;
            case "BIO":
                resourceId = R.array.provinciaProcBIOValues;
                break;
            case "MAD":
                resourceId = R.array.provinciaProcMADValues;
                break;
            case "BCN":
                resourceId = R.array.provinciaProcBCNValues;
                break;
            case "TFN":
                resourceId = R.array.provinciaProcTFNValues;
                break;
            case "TFS":
                resourceId = R.array.provinciaProcTFSValues;
                break;
            case "ACE":
                resourceId = R.array.provinciaProcACEValues;
                break;
            case "FUE":
                resourceId = R.array.provinciaProcFUEValues;
                break;
            case "LPA":
                resourceId = R.array.provinciaProcLPAValues;
                break;
            case "LEI":
                resourceId = R.array.provinciaProcLEIValues;
                break;
            case "REU":
                resourceId = R.array.provinciaProcREUValues;
                break;
            case "ALC":
                resourceId = R.array.provinciaProcALCValues;
                break;
            case "GRX":
                resourceId = R.array.provinciaProcGRXValues;
                break;
            default:
                resourceId = R.array.provinciaValues;
                break;
        }
        return resourceId;
    }



    public int getStringsResourceIdForDistrito(){
        int resourceId;
        switch (airportCode){
            case "MAD":
                resourceId = R.array.distritosMADStrings;
                break;
            case "BCN":
                resourceId = R.array.distritosBCNStrings;
                break;
            default:
                resourceId = R.array.distritosMADStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForDistrito(){
        int resourceId;
        switch (airportCode){
            case "MAD":
                resourceId = R.array.distritosMADCodes;
                break;
            case "BCN":
                resourceId = R.array.distritosBCNCodes;
                break;
            default:
                resourceId = R.array.distritosMADCodes;
                break;
        }
        return resourceId;
    }


    private int getStringsResourceIdForCountry(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.countrySVQStrings;
                break;
            case "PMI":
                resourceId = R.array.countryPMIStrings;
                break;
            case "IBZ":
                //TODO load specific dictionary
                resourceId = R.array.countryPMIStrings;
                break;
            case "MAH":
                resourceId = R.array.countryPMIStrings;
                break;
            case "OVD":
                resourceId = R.array.countryPMIStrings;
                break;
            case "AGP":
                resourceId = R.array.countryPMIStrings;
                break;
            default:
                resourceId = R.array.countryStrings;
                break;
        }
        return resourceId;
    }

    private int getCodesResourceIdForCountry(){
        int resourceId;
        switch (airportCode){
            case "SVQ":
                resourceId = R.array.countrySVQValues;
                break;
            case "PMI":
                resourceId = R.array.countryPMIValues;
                break;
            case "IBZ":
                //TODO load specific dictionary
                resourceId = R.array.countryPMIValues;
                break;
            case "MAH":
                resourceId = R.array.countryPMIValues;
                break;
            case "OVD":
                resourceId = R.array.countryPMIValues;
                break;
            case "AGP":
                resourceId = R.array.countryPMIValues;
                break;
            default:
                resourceId = R.array.countryValues;
                break;
        }
        return resourceId;
    }






        public String getAssociatedCodeForArea(String areaTex, String countryCode){
        String areaCode = null;
        int resourceToLoad;
        int resoureCodes;

        int selectedCountryValue =Integer.parseInt(countryCode);
        switch (selectedCountryValue){
            case 276:
                resourceToLoad = R.array.germanyAreasStrings;
                resoureCodes = R.array.germanyAreasCodes;
                break;
            case 40:
                resourceToLoad = R.array.austriaAreasStrings;
                resoureCodes = R.array.austriaAreasCodes;
                break;
            case 208:
                resourceToLoad = R.array.denmarkAreasStrings;
                resoureCodes = R.array.denmarkAreasCodes;
                break;
            case 246:
                resourceToLoad = R.array.finlandAreasStrings;
                resoureCodes = R.array.finlandAreasCodes;
                break;
            case 250:
                resourceToLoad = R.array.franceAreasStrings;
                resoureCodes = R.array.franceAreasCodes;
                break;
            case 380:
                resourceToLoad = R.array.italyAreasStrings;
                resoureCodes = R.array.italyAreasCodes;
                break;
            case 578:
                resourceToLoad = R.array.norwayAreasStrings;
                resoureCodes = R.array.norwayAreasCodes;
                break;
            case 528:
                resourceToLoad = R.array.hollandAreasStrings;
                resoureCodes = R.array.hollandAreasCodes;
                break;
            case 620:
                resourceToLoad = R.array.portugalAreasStrings;
                resoureCodes = R.array.portugalAreasCodes;
                break;
            case 826:
                resourceToLoad = R.array.englandAreasStrings;
                resoureCodes = R.array.englandAreasCodes;
                break;
            case 752:
                resourceToLoad = R.array.swedenAreasStrings;
                resoureCodes = R.array.swedenAreasCodes;
                break;
            case 756:
                resourceToLoad = R.array.switzerlandAreasStrings;
                resoureCodes = R.array.switzerlandAreasCodes;
                break;
            case 32:
                resourceToLoad = R.array.argentinaAreasStrings;
                resoureCodes = R.array.argentinaAreasCodes;
                break;
            case 76:
                resourceToLoad = R.array.brasilAreasStrings;
                resoureCodes = R.array.brasilAreasCodes;
                break;
            case 124:
                resourceToLoad = R.array.canadaAreasStrings;
                resoureCodes = R.array.canadaAreasCodes;
                break;
            case 840:
                resourceToLoad = R.array.usaAreasStrings;
                resoureCodes = R.array.usaAreasCodes;
                break;
            case 504:
                resourceToLoad = R.array.morrocoAreasStrings;
                resoureCodes = R.array.morrocoAreasCodes;
                break;
            case 484:
                resourceToLoad = R.array.mexicoAreasStrings;
                resoureCodes = R.array.mexicoAreasCodes;
                break;
            default:
                resourceToLoad = 0;
                resoureCodes = 0;
                break;
        }

        if(resourceToLoad !=0) {
            String[] stringsArray = activity.getResources().getStringArray(resourceToLoad);
            String[] codesArray = activity.getResources().getStringArray(resoureCodes);
            areaCode = getCode(areaTex, stringsArray, codesArray);
        }

        if(areaCode.contentEquals("0")){
            return null;
        } else {
            return areaCode;
        }
    }


    public String[] getAreasStringFor(String country){
        String[] stringArray = null;

        String textCode = this.getAssociatedCodeFor(country, COUNTRY);
        if(textCode==null || textCode.isEmpty()){
            return null;
        }

        int selectedCountryValue = Integer.parseInt(textCode);

        int resourceToLoad = 0;
        switch (selectedCountryValue){
            case 276:
                resourceToLoad = R.array.germanyAreasStrings;
                break;
            case 40:
                resourceToLoad = R.array.austriaAreasStrings;
                break;
            case 208:
                resourceToLoad = R.array.denmarkAreasStrings;
                break;
            case 246:
                resourceToLoad = R.array.finlandAreasStrings;
                break;
            case 250:
                resourceToLoad = R.array.franceAreasStrings;
                break;
            case 380:
                resourceToLoad = R.array.italyAreasStrings;
                break;
            case 578:
                resourceToLoad = R.array.norwayAreasStrings;
                break;
            case 528:
                resourceToLoad = R.array.hollandAreasStrings;
                break;
            case 620:
                resourceToLoad = R.array.portugalAreasStrings;
                break;
            case 826:
                resourceToLoad = R.array.englandAreasStrings;
                break;
            case 752:
                resourceToLoad = R.array.swedenAreasStrings;
                break;
            case 756:
                resourceToLoad = R.array.switzerlandAreasStrings;
                break;
            case 32:
                resourceToLoad = R.array.argentinaAreasStrings;
                break;
            case 76:
                resourceToLoad = R.array.brasilAreasStrings;
                break;
            case 124:
                resourceToLoad = R.array.canadaAreasStrings;
                break;
            case 840:
                resourceToLoad = R.array.usaAreasStrings;
                break;
            case 504:
                resourceToLoad = R.array.morrocoAreasStrings;
                break;
            case 484:
                resourceToLoad = R.array.mexicoAreasStrings;
                break;
            default:
                resourceToLoad = 0;
                break;
        }

        if(resourceToLoad!=0) {
            stringArray = activity.getResources().getStringArray(resourceToLoad);
        }

        return stringArray;
    }

    public int getAreaStringsResourceId (String country){

        String textCode = this.getAssociatedCodeFor(country, COUNTRY);
        if(textCode==null || textCode.isEmpty()){
            return -1;
        }

        int selectedCountryValue = Integer.parseInt(textCode);

        int resourceToLoad = 0;
        switch (selectedCountryValue){
            case 276:
                resourceToLoad = R.array.germanyAreasStrings;
                break;
            case 40:
                resourceToLoad = R.array.austriaAreasStrings;
                break;
            case 208:
                resourceToLoad = R.array.denmarkAreasStrings;
                break;
            case 246:
                resourceToLoad = R.array.finlandAreasStrings;
                break;
            case 250:
                resourceToLoad = R.array.franceAreasStrings;
                break;
            case 380:
                resourceToLoad = R.array.italyAreasStrings;
                break;
            case 578:
                resourceToLoad = R.array.norwayAreasStrings;
                break;
            case 528:
                resourceToLoad = R.array.hollandAreasStrings;
                break;
            case 620:
                resourceToLoad = R.array.portugalAreasStrings;
                break;
            case 826:
                resourceToLoad = R.array.englandAreasStrings;
                break;
            case 752:
                resourceToLoad = R.array.swedenAreasStrings;
                break;
            case 756:
                resourceToLoad = R.array.switzerlandAreasStrings;
                break;
            case 32:
                resourceToLoad = R.array.argentinaAreasStrings;
                break;
            case 76:
                resourceToLoad = R.array.brasilAreasStrings;
                break;
            case 124:
                resourceToLoad = R.array.canadaAreasStrings;
                break;
            case 840:
                resourceToLoad = R.array.usaAreasStrings;
                break;
            case 504:
                resourceToLoad = R.array.morrocoAreasStrings;
                break;
            case 484:
                resourceToLoad = R.array.mexicoAreasStrings;
                break;
            default:
                resourceToLoad = -1;
                break;
        }

        return resourceToLoad;
    }





    public String[] getLocalidadesFor(String codProvincia){
        ArrayList<String> localidadesArray = null;
        if(codProvincia == null || codProvincia.isEmpty()){
            return null;
        }
        int code = Integer.parseInt(codProvincia);
        int[] provCodes = activity.getResources().getIntArray(getProvCodesResourceIdForLocalidad());
        String[] allLocalidades = this.getStringsArrayFor(LOCALIDAD);
        localidadesArray = new ArrayList<String>();

        for(int i=0; i<allLocalidades.length; i++){
           if(provCodes[i] == code) {
               localidadesArray.add(allLocalidades[i]);
           }
        }
        //Always add others
        localidadesArray.add(allLocalidades[allLocalidades.length-1]);

        String[] stringsArray = new String[localidadesArray.size()];
        stringsArray = localidadesArray.toArray(stringsArray);

        return stringsArray;

    }

    public String[] getLocalidadesProcFor(String codProvincia){
        ArrayList<String> localidadesArray = null;
        if(codProvincia == null || codProvincia.isEmpty()){
            return null;
        }
        int code = Integer.parseInt(codProvincia);
        int[] provCodes = activity.getResources().getIntArray(getProvCodesResourceIdForLocalidadProc());
        String[] allLocalidades = this.getStringsArrayFor(LOCALIDAD_PROC);
        localidadesArray = new ArrayList<String>();

        for(int i=0; i<allLocalidades.length; i++){
            if(provCodes[i] == code || provCodes[i] == 99) {
                localidadesArray.add(allLocalidades[i]);
            }
        }

        String[] stringsArray = new String[localidadesArray.size()];
        stringsArray = localidadesArray.toArray(stringsArray);

        return stringsArray;
     }

    public boolean isValidResidenceProv(String provCode){
        int resourceID = 0;
        switch (airportCode){
            case "OVD":
                resourceID = R.array.provResOVD;
                break;
            case "AGP":
                resourceID = R.array.provResAGP;
                break;
            case "BIO":
                resourceID = R.array.provResBIO;
                break;
        }
        if(resourceID == 0){
            return true;
        }

        String[] codesArray = activity.getResources().getStringArray(resourceID);
        for(String code : codesArray){
            if(code.contentEquals(provCode)){
                return true;
            }
        }

        return false;
    }

}




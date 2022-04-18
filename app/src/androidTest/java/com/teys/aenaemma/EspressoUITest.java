package com.teys.aenaemma;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoUITest {

    @Rule
    public ActivityTestRule<SurveysListActivity> mActivityTestRule = new ActivityTestRule<>(SurveysListActivity.class);

    @Test
    public void espressoUITest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_codEncuestador), isDisplayed()));
        appCompatEditText.perform(replaceText("99"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.edit_aeropuerto), isDisplayed()));
        appCompatSpinner.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withText("Madrid"), isDisplayed()));
        appCompatTextView.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_newSurvey), withText("Nueva encuesta"), isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P1), isDisplayed()));
        appCompatAutoCompleteTextView.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P1), isDisplayed()));
        appCompatAutoCompleteTextView2.perform(replaceText("ESTONIA"), closeSoftKeyboard());
*/
        ViewInteraction appCompatTextView2 = onView(
                allOf(withText("ESTONIA"), isDisplayed()));
        appCompatTextView2.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("ESTONIA"), isDisplayed()));
        appCompatTextView2.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

*/
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton2.perform(scrollTo(), click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P2), isDisplayed()));
        appCompatAutoCompleteTextView3.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        ViewInteraction appCompatAutoCompleteTextView4 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P2), isDisplayed()));
        appCompatAutoCompleteTextView4.perform(replaceText("ESTONIA"), closeSoftKeyboard());
*/
        ViewInteraction appCompatTextView4 = onView(
                allOf(withText("ESTONIA"), isDisplayed()));
        appCompatTextView4.perform(click());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("ESTONIA"), isDisplayed()));
        appCompatTextView3.perform(click());
*/
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton3.perform(scrollTo(), click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.survey_radio_M3_P3_option1), withText("Sí"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P3),
                                withParent(withId(R.id.survey_layout_M3_P3)))),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton4.perform(scrollTo(), click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView5 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P4), isDisplayed()));
        appCompatAutoCompleteTextView5.perform(replaceText("VLC . VALENCIA"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        ViewInteraction appCompatAutoCompleteTextView6 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P4), withText("VLC . VALENCIA"), isDisplayed()));
        appCompatAutoCompleteTextView6.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("VLC . VALENCIA"), isDisplayed()));
        appCompatTextView4.perform(click());
*/

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton5.perform(scrollTo(), click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView8 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P5), isDisplayed()));
        appCompatAutoCompleteTextView8.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView9 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P5), isDisplayed()));
        appCompatAutoCompleteTextView9.perform(replaceText("VLG . VUELING AIRLINES, S.A."), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





       /* ViewInteraction appCompatTextView5 = onView(
                allOf(withId(android.R.id.text1), withText("VLG . VUELING AIRLINES, S.A."), isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        //appCompatTextView5.perform(click());
        ViewInteraction appCompatTextView5 = onView(
                allOf(withText("VLG . VUELING AIRLINES, S.A."), isDisplayed()));
        appCompatTextView5.perform(click());


        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton6.perform(scrollTo(), click());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.survey_radio_M3_P6_option2), withText("No"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P6),
                                withParent(withId(R.id.survey_layout_M3_P6)))),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton7.perform(scrollTo(), click());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.survey_radio_M3_P7_option1), withText("Sí"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P7),
                                withParent(withId(R.id.survey_layout_M3_P7)))),
                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.survey_radio_M3_P8_option2), withText("Hotel o similar"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P8),
                                withParent(withId(R.id.survey_layout_M3_P8)))),
                        isDisplayed()));
        appCompatRadioButton4.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton5 = onView(
                allOf(withId(R.id.survey_radio_M3_P9_option2_localidad), withText("Provincia"),
                        withParent(withId(R.id.survey_radiogroup_M3_P9)),
                        isDisplayed()));
        appCompatRadioButton5.perform(click());

        ViewInteraction appCompatAutoCompleteTextView10 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P9_prov),
                        withParent(withId(R.id.survey_layout_M3_P9_localidad)),
                        isDisplayed()));
        appCompatAutoCompleteTextView10.perform(replaceText("MADRID"), closeSoftKeyboard());
/*
        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.text1), withText("MADRID"), isDisplayed()));
        appCompatTextView6.perform(click());
*/
        ViewInteraction appCompatAutoCompleteTextView11 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P9_loc),
                        withParent(withId(R.id.survey_layout_M3_P9_localidad)),
                        isDisplayed()));
        appCompatAutoCompleteTextView11.perform(replaceText("MAJADAHONDA"), closeSoftKeyboard());
/*
        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(android.R.id.text1), withText("MAJADAHONDA"), isDisplayed()));
        appCompatTextView7.perform(click());
*/
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton10.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton6 = onView(
                allOf(withId(R.id.survey_radio_M3_P11_option1), withText("Un modo"),
                        withParent(withId(R.id.survey_radiogroup_M3_P11)),
                        isDisplayed()));
        appCompatRadioButton6.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton11.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton7 = onView(
                allOf(withId(R.id.survey_radio_M3_P12_option2),
                        withParent(withId(R.id.survey_radiogroup_M3_P12)),
                        isDisplayed()));
        appCompatRadioButton7.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton12.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.survey_edit_M3_P15_hora),
                        withParent(withId(R.id.survey_layout_M3_P15)),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.survey_edit_M3_P15_minutos),
                        withParent(withId(R.id.survey_layout_M3_P15)),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("35"), closeSoftKeyboard());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton13.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView12 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P16), isDisplayed()));
        appCompatAutoCompleteTextView12.perform(replaceText("WAW . VARSOVIA /OKECIE"), closeSoftKeyboard());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton14.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView16 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P17_company), isDisplayed()));
        appCompatAutoCompleteTextView16.perform(replaceText("VLG . VUELING AIRLINES, S.A."), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.survey_edit_M3_P17), isDisplayed()));
        appCompatEditText4.perform(replaceText("1234"), closeSoftKeyboard());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton15.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton8 = onView(
                allOf(withId(R.id.survey_radio_M3_P18_option2), withText("Trasbordo"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P18),
                                withParent(withId(R.id.survey_layout_M3_P18)))),
                        isDisplayed()));
        appCompatRadioButton8.perform(click());

        ViewInteraction appCompatAutoCompleteTextView17 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P18b), isDisplayed()));
        appCompatAutoCompleteTextView17.perform(replaceText("ma"), closeSoftKeyboard());

        ViewInteraction appCompatTextView11 = onView(
                allOf(withId(android.R.id.text1), withText("MLT . MALETH AERO"), isDisplayed()));
        appCompatTextView11.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton16.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView18 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P19), isDisplayed()));
        appCompatAutoCompleteTextView18.perform(replaceText("ma"), closeSoftKeyboard());

        ViewInteraction appCompatTextView12 = onView(
                allOf(withId(android.R.id.text1), withText("LME . LE MANS / ARNAGE"), isDisplayed()));
        appCompatTextView12.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton17.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.survey_spinner_M3_P20),
                        withParent(withId(R.id.survey_layout_M3_P20)),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        ViewInteraction appCompatTextView13 = onView(
                allOf(withText("202 . VISITA A PARIENTES Y AMIGOS"), isDisplayed()));
        appCompatTextView13.perform(click());

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton18.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton9 = onView(
                allOf(withId(R.id.survey_radio_M3_P21_option2), withText("Vuelta"),
                        withParent(withId(R.id.survey_radiogroup_M3_P21)),
                        isDisplayed()));
        appCompatRadioButton9.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.survey_edit_M3_P21b),
                        withParent(withId(R.id.survey_layout_M3_P21nums)),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton19.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton10 = onView(
                allOf(withId(R.id.survey_radio_M3_P22_option3), withText("Nº personas"),
                        withParent(withId(R.id.survey_radiogroup_M3_P22)),
                        isDisplayed()));
        appCompatRadioButton10.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.survey_edit_M3_P22_numPersonas), isDisplayed()));
        appCompatEditText6.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton20.perform(scrollTo(), click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.survey_edit_M3_P23),
                        withParent(withId(R.id.survey_layout_M3_P23)),
                        isDisplayed()));
        appCompatEditText7.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.survey_edit_M3_P23),
                        withParent(withId(R.id.survey_layout_M3_P23)),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton21.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton11 = onView(
                allOf(withId(R.id.survey_radio_M3_P24_option2), withText("Parientes (abuelos, tíos, primos, etc.)"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P24),
                                withParent(withId(R.id.survey_layout_M3_P24)))),
                        isDisplayed()));
        appCompatRadioButton11.perform(click());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton22.perform(scrollTo(), click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.survey_edit_M3_P25_numDias), isDisplayed()));
        appCompatEditText9.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatButton23 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton23.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton12 = onView(
                allOf(withId(R.id.survey_radio_M3_P26_option2), withText("Combinado (paquete)"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P26),
                                withParent(withId(R.id.survey_layout_M3_P26)))),
                        isDisplayed()));
        appCompatRadioButton12.perform(click());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton24.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton13 = onView(
                allOf(withId(R.id.survey_radio_M3_P27_option2), withText("Nº de viajes"),
                        withParent(withId(R.id.survey_radiogroup_M3_P27)),
                        isDisplayed()));
        appCompatRadioButton13.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.survey_edit_M3_P27_numViajes), isDisplayed()));
        appCompatEditText10.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatButton25 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton25.perform(scrollTo(), click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.survey_edit_M3_P28_numViajes), isDisplayed()));
        appCompatEditText11.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatButton26 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton26.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton14 = onView(
                allOf(withId(R.id.survey_radio_M3_P29_option2), withText("No"),
                        withParent(withId(R.id.survey_radiogroup_M3_P29)),
                        isDisplayed()));
        appCompatRadioButton14.perform(click());

        ViewInteraction appCompatButton27 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton27.perform(scrollTo(), click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.survey_edit_M3_P30_numPersonas), isDisplayed()));
        appCompatEditText12.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton15 = onView(
                allOf(withId(R.id.survey_radio_M3_P30b_option2), withText("No"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P30b),
                                withParent(withId(R.id.survey_layout_M3_P30b)))),
                        isDisplayed()));
        appCompatRadioButton15.perform(click());

        ViewInteraction appCompatButton28 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton28.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton16 = onView(
                allOf(withId(R.id.survey_radio_M3_P31_option2), withText("Aeropuerto (máquinas auto check-in)"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P31),
                                withParent(withId(R.id.survey_layout_M3_P31)))),
                        isDisplayed()));
        appCompatRadioButton16.perform(click());

        ViewInteraction appCompatButton29 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton29.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton17 = onView(
                allOf(withId(R.id.survey_radio_M3_P32_option2), withText("No"),
                        withParent(withId(R.id.survey_radiogroup_M3_P32)),
                        isDisplayed()));
        appCompatRadioButton17.perform(click());

        ViewInteraction appCompatButton30 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton30.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton18 = onView(
                allOf(withId(R.id.survey_radio_M3_P33_option2), withText("No"),
                        withParent(withId(R.id.survey_radiogroup_M3_P33)),
                        isDisplayed()));
        appCompatRadioButton18.perform(click());

        ViewInteraction appCompatButton31 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton31.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton19 = onView(
                allOf(withId(R.id.survey_radio_M3_P37_option3), withText("Estudiante"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P37),
                                withParent(withId(R.id.survey_layout_M3_P37)))),
                        isDisplayed()));
        appCompatRadioButton19.perform(click());

        ViewInteraction appCompatRadioButton20 = onView(
                allOf(withId(R.id.survey_radio_M3_P37_option1), withText("Trabaja"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P37),
                                withParent(withId(R.id.survey_layout_M3_P37)))),
                        isDisplayed()));
        appCompatRadioButton20.perform(click());

        ViewInteraction appCompatButton32 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton32.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton21 = onView(
                allOf(withId(R.id.survey_radio_M3_P38_option1), withText("Empresario o profesional con empleados"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P38),
                                withParent(withId(R.id.survey_layout_M3_P38)))),
                        isDisplayed()));
        appCompatRadioButton21.perform(click());

        ViewInteraction appCompatButton33 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton33.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton22 = onView(
                allOf(withId(R.id.survey_radio_M3_P40_option1), withText("Primarios/Básicos/EGB"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P40),
                                withParent(withId(R.id.survey_layout_M3_P40)))),
                        isDisplayed()));
        appCompatRadioButton22.perform(click());

        ViewInteraction appCompatButton34 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton34.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton23 = onView(
                allOf(withId(R.id.survey_radio_M3_P41_option9), withText("65 a 69 años"),
                        withParent(withId(R.id.survey_radiogroup_M3_P41)),
                        isDisplayed()));
        appCompatRadioButton23.perform(click());

        ViewInteraction appCompatButton35 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton35.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton24 = onView(
                allOf(withId(R.id.survey_radio_M3_P42_option1), withText("Hombre"),
                        withParent(allOf(withId(R.id.survey_radiogroup_M3_P42),
                                withParent(withId(R.id.survey_layout_M3_P42)))),
                        isDisplayed()));
        appCompatRadioButton24.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.survey_edit_codCompVuelo), isDisplayed()));
        appCompatEditText13.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.survey_edit_codCompVuelo), isDisplayed()));
        appCompatEditText14.perform(replaceText("VLG"), closeSoftKeyboard());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.survey_edit_numVuelo), isDisplayed()));
        appCompatEditText15.perform(replaceText("1234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.survey_edit_puertaEmbarque), isDisplayed()));
        appCompatEditText16.perform(replaceText("99"), closeSoftKeyboard());

        ViewInteraction appCompatButton36 = onView(
                allOf(withId(R.id.survey_button_save), withText("Guardar"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton36.perform(scrollTo(), click());

        ViewInteraction appCompatButton37 = onView(
                allOf(withId(android.R.id.button1), withText("Si"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton37.perform(click());

    }

}

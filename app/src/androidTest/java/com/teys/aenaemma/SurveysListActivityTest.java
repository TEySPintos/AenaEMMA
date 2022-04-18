package com.teys.aenaemma;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SurveysListActivityTest {

    @Rule
    public ActivityTestRule<SurveysListActivity> mActivityTestRule = new ActivityTestRule<>(SurveysListActivity.class);

    @Test
    public void surveysListActivityTest() {
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
        appCompatEditText.perform(replaceText("4"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction view = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.view.View.class), isDisplayed()));
        view.check(matches(isDisplayed()));

        ViewInteraction view2 = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.view.View.class), isDisplayed()));
        view2.check(matches(isDisplayed()));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.edit_aeropuerto), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.edit_aeropuerto), isDisplayed()));
        appCompatSpinner2.perform(click());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withText("Madrid"), isDisplayed()));
        appCompatTextView.perform(click());

        try {
            Thread.sleep(10000);
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

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P1), isDisplayed()));
        appCompatAutoCompleteTextView2.perform(replaceText("ES"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("ESTONIA"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P2), isDisplayed()));
        appCompatAutoCompleteTextView3.perform(click());

        ViewInteraction appCompatAutoCompleteTextView4 = onView(
                allOf(withId(R.id.survey_autoComplete_M3_P2), isDisplayed()));
        appCompatAutoCompleteTextView4.perform(replaceText("es"), closeSoftKeyboard());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("ESTONIA"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.survey_button_next), withText("Siguiente"),
                        withParent(withId(R.id.survey_form_control1))));
        appCompatButton4.perform(scrollTo(), click());

    }

}

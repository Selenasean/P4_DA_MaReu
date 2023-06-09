package fr.selquicode.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;


import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.selquicode.mareu.ui.create.CreateMeetingActivity;


/**
 * Instrumented test for CreateMeetingActivity UI
 */
@RunWith(AndroidJUnit4.class)
public class CreateMeetingActivityTest {

    private static final String MEETING_SUBJECT = "Sujet test";
    private static final String PARTICIPANT_EMAIL = "test@lamzone.fr";
    private static final String INVALID_EMAIL="t.lam1//!";

    @Rule
    public ActivityScenarioRule<CreateMeetingActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(CreateMeetingActivity.class);

    @Before
    public void setup(){
        Intents.init();
    }

    @Test
    public void createButton_shouldBeDisabled_whenInputsAreEmpty(){
        // creation button is disabled
        onView(withId(R.id.btn_create)).check(matches(not(isEnabled())));
    }

    @Test
    public void onClicked_onButtonTo_addEmailParticipant_shouldDisplayAnError_ifEmailInvalid(){
        //WHEN
        onView(withId(R.id.participants_textInput)).perform(typeText(INVALID_EMAIL));
        onView(withId(R.id.add_email_participant)).perform(click());
        //THEN
        onView(withId(R.id.participants_lyt))
                .check(matches(hasDescendant(withText(R.string.error_email))));
    }

    @Test
    public void onClicked_onButtonTo_addEmailParticipant_shouldDisplayParticipantEmail_ifEmailValid(){
        //WHEN
        onView(withId(R.id.participants_textInput)).perform(typeText(PARTICIPANT_EMAIL));
        onView(withId(R.id.add_email_participant)).perform(click());
        //THEN
        onView(allOf(withText(PARTICIPANT_EMAIL), withParent(allOf(withId(R.id.chip_group),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))),
                isDisplayed()));
    }

    /**
     * FAILED
     */
    @Test
    public void allFieldsCompleted_shouldEnabledCreateButton_andCreate_aNewMeeting(){
        //WHEN filled inputs
        onView(withId(R.id.choose_roomTextV)).perform(click());
        onView(withText("Salle B")).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.datepicker)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 6, 18));
        onView(withId(android.R.id.button1)).perform(doubleClick());

        onView(withId(R.id.timepicker)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10, 30));
        onView(withId(android.R.id.button1)).perform(doubleClick());

        onView(withId(R.id.subject)).perform(click());
        onView(withId(R.id.subject)).perform(typeText(MEETING_SUBJECT));

        onView(withId(R.id.participants_textInput)).perform(click());
        onView(withId(R.id.participants_textInput)).perform(typeText(PARTICIPANT_EMAIL));
        onView(withId(R.id.add_email_participant)).perform(click());

        //THEN
        // button is enabled
        onView(withId(R.id.btn_create)).check(matches(isEnabled()));
        onView(withId(R.id.btn_create)).perform(scrollTo()).perform(click());
    }

    @Test
    public void onFilledEveryInputs_shouldDisplayChosenInfo(){
        onView(withId(R.id.choose_roomTextV)).perform(click());
        onView(withText("Salle D")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.choose_roomTextV)).check(matches(withText("Salle D")));

        onView(withId(R.id.datepicker)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 6, 18));
        onView(withId(android.R.id.button1)).perform(doubleClick());
        onView(withId(R.id.datepicker)).check(matches(withText("18/06/2023")));

        onView(withId(R.id.timepicker)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10, 30));
        onView(withId(android.R.id.button1)).perform(doubleClick());
        onView(withId(R.id.timepicker)).check(matches(withText("10:30")));

        onView(withId(R.id.subject)).perform(click());
        onView(withId(R.id.subject)).perform(typeText(MEETING_SUBJECT));
        onView(withId(R.id.subject)).check(matches(withText(MEETING_SUBJECT)));

        onView(withId(R.id.participants_textInput)).perform(click());
        onView(withId(R.id.participants_textInput)).perform(typeText(PARTICIPANT_EMAIL));
        onView(withId(R.id.add_email_participant)).perform(click());
    }

}

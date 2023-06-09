package fr.selquicode.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.Matchers.equalTo;
import static fr.selquicode.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static fr.selquicode.mareu.utils.RecyclerViewUtils.withRecyclerView;



import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import fr.selquicode.mareu.ui.create.CreateMeetingActivity;
import fr.selquicode.mareu.ui.list.MeetingsActivity;
import fr.selquicode.mareu.utils.DeleteViewAction;


/**
 * Instrumented test for MeetingsActivity UI
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsActivityTest {

    private static final int MEETINGS_LIST_COUNT = 4;

    @Rule
    public ActivityScenarioRule<MeetingsActivity> mActivityActivityScenarioRule =
            new ActivityScenarioRule<>(MeetingsActivity.class);

    @Before
    public void setup(){
        Intents.init();
    }

    @Test
    public void recycleView_shouldBeVisible(){
        onView(withId(R.id.list_meetings)).check(matches(isDisplayed()));
    }

    @Test
    public void onCLicked_deleteButton_shouldDeleteTheMeeting(){
        //GIVEN
        onView(withId(R.id.list_meetings)).check(matches(hasChildCount(MEETINGS_LIST_COUNT)));
        // WHEN perform a click from delete button on an item from the RecycleView list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        //THEN
        onView(withId(R.id.list_meetings)).check(withItemCount(MEETINGS_LIST_COUNT -1));
    }

    @Test
    public void createActivity_isLaunched_whenButtonIsClicked(){
        //WHEN
        onView(withId(R.id.fab_to_create)).perform(click());
        //THEN
        intended(hasComponent(CreateMeetingActivity.class.getName()));
    }

    @Test
    public void onClick_createMeeting_shouldCreateMeeting(){
        onView(withId(R.id.fab_to_create)).perform(click());

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
        onView(withId(R.id.subject)).perform(typeText("sujet test"));

        onView(withId(R.id.participants_textInput)).perform(click());
        onView(withId(R.id.participants_textInput)).perform(typeText("j@lamzone.fr"));
        onView(withId(R.id.add_email_participant)).perform(click());

        //THEN
        onView(withId(R.id.btn_create)).perform(scrollTo()).perform(click());
        onView(withId(R.id.list_meetings)).check(withItemCount(MEETINGS_LIST_COUNT +1));
    }

    @Test
    public void onClicked_filterByRoom_shouldFilterByRoom(){
        //WHEN
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.filter_room)).perform(click());
        onView(withText("Salle A")).perform(click());

        //THEN
       onView(withId(R.id.list_meetings)).check(withItemCount(1));
       //Verifiez que l'item contient "Salle A"
    }

    @Test
    public void onCLicked_filterByDate_shouldFilterByDate(){
        //WHEN
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.filter_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 6, 12));
        onView(withId(android.R.id.button1)).perform(doubleClick());

        //THEN
        onView(withId(R.id.list_meetings)).check(withItemCount(2));
        onView(withRecyclerView(R.id.list_meetings).atPositionOnView(0, R.id.date_meeting))
                .check(matches(withText("12/06/2023")));
        onView(withRecyclerView(R.id.list_meetings).atPositionOnView(1, R.id.date_meeting)).check(matches(withText("12/06/2023")));
    }

    @Test
    public void onClicked_filterByDate_andFilterByRoom_shouldFilterByDateThenByRoom(){
        //WHEN
        // filter by date
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.filter_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 6, 12));
        onView(withId(android.R.id.button1)).perform(doubleClick());

        //filter by room
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.filter_room)).perform(click());
        onView(withText("Salle C")).perform(click());

        //THEN
        onView(withId(R.id.list_meetings)).check(withItemCount(1));
        onView(withRecyclerView(R.id.list_meetings).atPositionOnView(0, R.id.place_meeting))
                .check(matches(withText("Salle C")));
        onView(withRecyclerView(R.id.list_meetings).atPositionOnView(0, R.id.date_meeting))
                .check(matches(withText("12/06/2023")));
    }

    @Test
    public void onClicked_reset_shouldResetAllFilters(){
        //WHEN
        // filter by date
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.filter_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 6, 12));
        onView(withId(android.R.id.button1)).perform(doubleClick());
        // filter by place
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.filter_room)).perform(click());
        onView(withText("Salle C")).perform(click());
        // reset
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.filter_return)).perform(click());

        //THEN
        onView(withId(R.id.list_meetings)).check(withItemCount(MEETINGS_LIST_COUNT));
    }
}
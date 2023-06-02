package fr.selquicode.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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

    private static int MEETINGS_LIST_COUNT = 4;

    @Rule
    public ActivityScenarioRule<MeetingsActivity> mActivityActivityScenarioRule =
            new ActivityScenarioRule<>(MeetingsActivity.class);

    @Test
    public void recycleView_shouldBeVisible(){
        onView(withId(R.id.list_meetings)).check(matches(isDisplayed()));
    }

    /**FAILED
     */
    @Test
    public void meeting_shouldBeDeleted_andDisappear_whenClicked(){
        //GIVEN
        onView(withId(R.id.list_meetings)).check(matches(hasChildCount(MEETINGS_LIST_COUNT)));
        // WHEN perform a click from delete button on an item from the RecycleView list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        //THEN
        onView(withId(R.id.list_meetings)).check(matches(hasChildCount(MEETINGS_LIST_COUNT - 1)));
    }

    @Test
    public void createActivity_isLaunched_whenButtonIsClicked(){
        //WHEN
        onView(withId(R.id.fab_to_create)).perform(click());
        //THEN
        intended(hasComponent(CreateMeetingActivity.class.getName()));
    }

    @Test
    public void onClicked_filterByRoom_shouldFilterByRoom(){}

    @Test
    public void onCLicked_filterByDate_shouldFilterByDate(){}

    @Test
    public void onClicked_filterByDate_andFilterByRoom_shouldFilterByDateThenByRoom(){}

    @Test
    public void onClicked_reset_shouldResetAllFilters(){}
}
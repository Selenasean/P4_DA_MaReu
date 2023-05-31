package fr.selquicode.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import fr.selquicode.mareu.ui.list.MeetingsActivity;

/**
 * Instrumented test for MeetingsActivity UI
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsActivityTest {

//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("fr.selquicode.mareu", appContext.getPackageName());
//    }
//

    @Rule
    public ActivityScenarioRule<MeetingsActivity> mActivityActivityScenarioRule =
            new ActivityScenarioRule<>(MeetingsActivity.class);

    @Test
    public void recycleView_shouldBeVisible(){
        onView(withId(R.id.list_meetings)).check(matches(isDisplayed()));
    }

    @Test
    public void meeting_shouldBeDeleted_andDisappear_whenClicked(){}

    @Test
    public void createActivity_isLaunched_whenButtonIsClicked(){}

    @Test
    public void onClicked_filterByRoom_shouldFilterByRoom(){}

    @Test
    public void onCLicked_filterByDate_shouldFilterByDate(){}

    @Test
    public void onClicked_filterByDate_andFilterByRoom_shouldFilterByDateThenByRoom(){}

    @Test
    public void onClicked_reset_shouldResetAllFilters(){}
}
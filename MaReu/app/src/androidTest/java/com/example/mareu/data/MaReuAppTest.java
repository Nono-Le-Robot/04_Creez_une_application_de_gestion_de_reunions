package com.example.mareu.data;

import android.view.View;
import android.widget.TimePicker;
import static org.hamcrest.CoreMatchers.allOf;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static org.hamcrest.core.IsNull.notNullValue;


import org.junit.runners.MethodSorters;

import com.example.mareu.MainActivity;
import com.example.mareu.R;
import com.example.mareu.data.MeetingViewModel;
import com.example.mareu.utils.DeleteViewAction;

// TODO: verifier que le btn supprimer supprime bien la reunion
// TODO:verifier que la recherche fonctionne bien ?

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MaReuAppTest {
    private MainActivity mActivity;

    //TODO: comment importer l'api ?
    private MeetingViewModel meetingViewModel;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void test01_displayHomePageAndItemCorrectly(){
        onView(ViewMatchers.withId(R.id.main_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(hasChildCount(4)));
    }

    @Test
    public void test02_displayAddMeetingPage(){
        onView(withId(R.id.btn_add_meeting)).perform(click());
        onView(withId(R.id.add_actvity)).check(matches(isDisplayed()));

        String meetingSubject = "testMeetingSubject";

        onView(withId(R.id.edit_text_subject)).perform(typeText(meetingSubject));

        onView(withId(R.id.set_start_hour_btn)).perform(click());
        //TODO : pourquoi probleme timepicker ?
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(13, 00));
        Espresso.onIdle();
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.set_end_hour_btn)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(15, 50));
        Espresso.onIdle();
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.btn_select_room)).perform(click());
        onView(withText("Room 1")).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.btn_add_participant)).perform(click());
        onView(withId(R.id.edit_text_email)).perform(typeText("test01@gmail.com"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.btn_add_participant)).perform(click());
        onView(withId(R.id.edit_text_email)).perform(typeText("test02@gmail.com"));
        onView(withId(android.R.id.button1)).perform(click());

        //TODO pourquoi ca ne marche pas avec le btn de la toolbar ?
        onView(allOf(withId(R.id.btn_save_meeting))).perform(click());

        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(hasChildCount(5)));
    }
    @Test
    public void test03_displayMeeting() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        String subjectToCompare = "testMeetingSubject";
        String placeToCompare = "Room 1";
        String meetingHoursToCompare = "16:00 - 16:50";
        String firstParticipantToCompare = "test01@gmail.com";
        String secondParticipantToCompare = "test02@gmail.com";

        onView(withText(subjectToCompare)).check(matches(isDisplayed()));
        onView(withText(placeToCompare)).check(matches(isDisplayed()));
        onView(withText(meetingHoursToCompare)).check(matches(isDisplayed()));
        onView(withText(firstParticipantToCompare)).check(matches(isDisplayed()));
        onView(withText(secondParticipantToCompare)).check(matches(isDisplayed()));
    }
    @Test
    public void test04_removeMeeting(){
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(
                        4,
                        new DeleteViewAction()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recycler_view)).check(matches(hasChildCount(4)));


    }
}


package com.example.myvacation;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.widget.EditText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

import com.example.myvacation.UI.MainActivity;
import com.example.myvacation.UI.VacationList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myvacation", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<VacationList> activityRule =
            new ActivityScenarioRule<>(VacationList.class);
    @Test
    public void testSearchViewInputFiltersRecyclerView() throws InterruptedException {



        // Type a query into the SearchView
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));
        onView(withId(R.id.searchView)).perform(click());
        closeSoftKeyboard();
        Thread.sleep(2500);
        onView(withId(androidx.appcompat.R.id.search_src_text)) // Target the internal EditText
                .perform(typeText("example"));
        Thread.sleep(1000);
        // Assert RecyclerView displays filtered results
        onView(withId(R.id.vacationlistrecyclerview))
                .check(matches(hasDescendant(withText("example"))));
    }

}
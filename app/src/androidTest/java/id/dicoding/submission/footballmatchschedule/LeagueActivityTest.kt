package id.dicoding.submission.footballmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.dicoding.submission.footballmatchschedule.R.id.league_list_rv
import id.dicoding.submission.footballmatchschedule.R.id.viewpager
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LeagueActivityTest {
    @Rule
    @JvmField var activityRule = IntentsTestRule(LeagueActivity::class.java)

    @Test
    fun testChooseLeagueGoToLeagueSchedule() {
        onView(withId(league_list_rv)).check(matches(isDisplayed()))
        onView(withId(league_list_rv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(league_list_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(viewpager)).check(matches(isDisplayed()))
    }

    @Test
    fun testIntentSentToLeagueSchedule() {
        onView(withId(league_list_rv)).check(matches(isDisplayed()))
        onView(withId(league_list_rv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(league_list_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        intended(allOf(
                toPackage("id.dicoding.submission.footballmatchschedule"),
                hasExtra("name_league", "English Premier League"),
                hasExtra("id_league", "4328")))
    }
}
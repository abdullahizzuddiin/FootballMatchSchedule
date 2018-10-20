package id.dicoding.submission.footballmatchschedule

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import id.dicoding.submission.footballmatchschedule.R.id.*
import id.dicoding.submission.footballmatchschedule.RecyclerViewItemCountAssertion.Companion.withItemCount
import id.dicoding.submission.footballmatchschedule.test.GlobalIdlingResources
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LeagueScheduleActivityTest {
    private lateinit var mActivity: LeagueScheduleActivity
    private val id_league_intent_param = "id_league"
    private val name_league_intent_param = "name_league"
    private val id_league = "4328"
    private val name_league = "English Premier League"

    @Rule
    @JvmField
    var activityRule = IntentsTestRule(LeagueScheduleActivity::class.java, true, false)

    @Before
    fun setUp() {
        val intent = Intent()
        intent.putExtra(id_league_intent_param, id_league)
        intent.putExtra(name_league_intent_param, name_league)
        activityRule.launchActivity(intent)

        IdlingRegistry.getInstance().register(GlobalIdlingResources.getIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(GlobalIdlingResources.getIdlingResource())
    }

    @Test
    fun testAddToFavorite() {
        val firstRecyclerViewIndex = 0
        onView(result(withId(match_list_rv), firstRecyclerViewIndex)).check(matches(ViewMatchers.isDisplayed()))
        onView(result(withId(match_list_rv), firstRecyclerViewIndex)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(result(withId(match_list_rv), firstRecyclerViewIndex)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Jadwal pertandingan dijadikan favorit"))
                .check(matches(isDisplayed()))

        pressBack()
        val favoriteRecyclerViewIndex = 2
        onView(withId(viewpager)).perform(swipeLeft())
        onView(withId(viewpager)).perform(swipeLeft())
        onView(result(withId(match_list_rv), favoriteRecyclerViewIndex)).check(withItemCount(1))
    }

    @Test
    fun testRemoveFromFavorite() {
        val recyclerViewIndex = 2

        onView(withId(viewpager)).perform(swipeLeft())
        onView(withId(viewpager)).perform(swipeLeft())
//        Thread.sleep(1000)
        onView(result(withId(match_list_rv), recyclerViewIndex)).check(matches(ViewMatchers.isDisplayed()))
        onView(result(withId(match_list_rv), recyclerViewIndex)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(result(withId(match_list_rv), recyclerViewIndex)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Jadwal pertandingan bukan lagi favorit"))
                .check(matches(isDisplayed()))

        pressBack()
        onView(result(withId(match_list_rv), recyclerViewIndex)).check(withItemCount(0))
    }

    @Test
    fun testSwipeViewPager() {
        val recyclerViewIndex = 1
        onView(withId(viewpager)).perform(swipeLeft())
        onView(result(withId(match_list_rv), recyclerViewIndex)).check(matches(ViewMatchers.isDisplayed()))
        onView(result(withId(match_list_rv), recyclerViewIndex)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(result(withId(match_list_rv), recyclerViewIndex)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

    }
}

/**
 * Custom Matcher
 * @source: https://stackoverflow.com/questions/50844791/using-espresso-to-test-similar-recyclerview-inside-viewpager?noredirect=1&lq=1
 */
fun <T> result(matcher: Matcher<T>, position: Int): Matcher<T> {
    return object : BaseMatcher<T>() {
        override fun describeTo(description: Description?) {}

        private var resultIndex = -1
        override fun matches(item: Any): Boolean {
            if (matcher.matches(item)) {
                resultIndex++
                return resultIndex == position
            }
            return false
        }
    }
}

/**
 * Custom Assert
 * @Source: https://stackoverflow.com/a/43207009/5722842
 */
class RecyclerViewItemCountAssertion(private val matcher: Matcher<Int>) : ViewAssertion {
    companion object {
        fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return withItemCount(`is`(expectedCount))
        }

        fun withItemCount(matcher: Matcher<Int>): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(matcher)
        }
    }

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter?.itemCount, matcher)
    }
}
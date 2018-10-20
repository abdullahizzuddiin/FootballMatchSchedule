package id.dicoding.submission.footballmatchschedule.test

import android.support.test.espresso.IdlingResource
import android.support.v4.view.ViewPager

/**
 * @source: https://stackoverflow.com/questions/31056918/wait-for-view-pager-animations-with-espresso/32763454#32763454
 */
class ViewPagerIdlingResource(viewPager: ViewPager, private val name: String) : IdlingResource {
    private var isIdle: Boolean = true
    private var mResourceCallback: IdlingResource.ResourceCallback? = null

    init {
        viewPager.addOnPageChangeListener(ViewPagerListener())
    }

    override fun getName(): String {
        return name
    }

    override fun isIdleNow(): Boolean {
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        mResourceCallback = callback
    }

    inner class ViewPagerListener : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageScrollStateChanged(state: Int) {
            isIdle = (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING)
            mResourceCallback?.let {
                if (isIdle) it.onTransitionToIdle()
            }
        }
    }
}
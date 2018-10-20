package id.dicoding.submission.footballmatchschedule.ui

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import id.dicoding.submission.footballmatchschedule.R.color.colorAccent
import id.dicoding.submission.footballmatchschedule.R.color.greyBackgroundColor
import id.dicoding.submission.footballmatchschedule.R.id.match_list_rv
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class LeagueScheduleFragmentUI : AnkoComponent<Fragment> {
    lateinit var mLeagueScheduleListRv: RecyclerView
    lateinit var mProgressBar: ProgressBar
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        mSwipeRefreshLayout = swipeRefreshLayout {
            backgroundColor = ContextCompat.getColor(context, greyBackgroundColor)
            setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
            )

            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                mLeagueScheduleListRv = recyclerView {
                    id = match_list_rv
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }

                mProgressBar = progressBar {
                }.lparams {
                    centerHorizontally()
                    topMargin = dip(16)
                }
            }
        }

        mSwipeRefreshLayout
    }
}
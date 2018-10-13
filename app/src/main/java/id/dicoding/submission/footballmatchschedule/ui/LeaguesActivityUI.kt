package id.dicoding.submission.footballmatchschedule.ui

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import id.dicoding.submission.footballmatchschedule.LeagueActivity
import id.dicoding.submission.footballmatchschedule.R.color.colorAccent
import id.dicoding.submission.footballmatchschedule.R.id.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class LeaguesActivityUI : AnkoComponent<LeagueActivity> {
    lateinit var mLeaguesListRv: RecyclerView

    lateinit var mProgressBar: ProgressBar

    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun createView(ui: AnkoContext<LeagueActivity>) = with(ui) {
        mSwipeRefreshLayout = swipeRefreshLayout {
            id = league_list_srl
            setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
            )

            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                mLeaguesListRv = recyclerView {
                    id = league_list_rv
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }

                mProgressBar = progressBar {
                    id = league_list_pb
                }.lparams {
                    centerHorizontally()
                    topMargin = dip(16)
                }
            }
        }
        mSwipeRefreshLayout
    }
}
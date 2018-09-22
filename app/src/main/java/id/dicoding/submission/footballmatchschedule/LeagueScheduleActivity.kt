package id.dicoding.submission.footballmatchschedule

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.ProgressBar
import id.dicoding.submission.footballmatchschedule.R.color.colorAccent
import id.dicoding.submission.footballmatchschedule.adapter.LeagueSchedulesListAdapter
import id.dicoding.submission.footballmatchschedule.model.League
import id.dicoding.submission.footballmatchschedule.model.Schedule
import id.dicoding.submission.footballmatchschedule.presenter.MatchPresenter
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import id.dicoding.submission.footballmatchschedule.view_operation.MatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class LeagueScheduleActivity : AppCompatActivity(), MatchView {
    private lateinit var mLeagueScheduleListRv : RecyclerView

    private lateinit var mProgressBar : ProgressBar

    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout
    private var mSchedules : MutableList<Schedule> = mutableListOf()
    private lateinit var mAdapter : LeagueSchedulesListAdapter

    private lateinit var mPresenter : MatchPresenter

    private lateinit var mIdLeague : String
    private lateinit var mNameLeague : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LeagueScheduleActivityUI().setContentView(this)
        receiveExtras()
        initView()
        initPresenter()
        initAdapter()
        setRefreshListener()
    }

    private fun receiveExtras() {
        val intent = intent
        mIdLeague = intent.getStringExtra(resources.getString(R.string.id_league_intent_param))
        mNameLeague = intent.getStringExtra(resources.getString(R.string.name_league_intent_param))
    }

    private fun initView() {
        mLeagueScheduleListRv = find(R.id.league_schedule_list_rv)
        mProgressBar = find(R.id.league_schedule_list_pb)
        mSwipeRefreshLayout = find(R.id.league_schedule_list_srl)
    }

    private fun initPresenter() {
        mPresenter = MatchPresenter(this)
        mPresenter.getMatchList(mIdLeague)
    }

    override fun showLoading() {
        mProgressBar.setVisible()
        mLeagueScheduleListRv.setInvisible()
    }


    private fun initAdapter() {
        mAdapter = LeagueSchedulesListAdapter(this, mSchedules, ::onScheduleClickListener)
        mLeagueScheduleListRv.adapter = mAdapter
    }

    private fun setRefreshListener() {
        mSwipeRefreshLayout.onRefresh {
            showLoading()
            mPresenter.getMatchList(mIdLeague)
        }
    }

    private fun onScheduleClickListener(schedule: Schedule) {

    }

    override fun hideLoading() {
        mProgressBar.setInvisible()
        mLeagueScheduleListRv.setVisible()
    }

    override fun showLeagueList(filteredData: List<Schedule>) {
        mSwipeRefreshLayout.isRefreshing = false
        mSchedules.clear()
        mSchedules.addAll(filteredData)
        mAdapter.notifyDataSetChanged()
    }
}

class LeagueScheduleActivityUI : AnkoComponent<LeagueScheduleActivity> {
    override fun createView(ui: AnkoContext<LeagueScheduleActivity>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            swipeRefreshLayout {
                id = R.id.league_schedule_list_srl
                setColorSchemeResources(
                        colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    recyclerView {
                        id = R.id.league_schedule_list_rv
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar{
                        id = R.id.league_schedule_list_pb
                    }.lparams{
                        centerHorizontally()
                        topMargin = dip(16)
                    }
                }
            }.lparams(width = matchParent, height = wrapContent) {
                leftPadding = dip(16)
                rightPadding = dip(16)
            }
        }
    }

}

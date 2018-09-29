package id.dicoding.submission.footballmatchschedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import id.dicoding.submission.footballmatchschedule.R.color.colorAccent
import id.dicoding.submission.footballmatchschedule.R.color.greyBackgroundColor
import id.dicoding.submission.footballmatchschedule.adapter.LeagueSchedulesListAdapter
import id.dicoding.submission.footballmatchschedule.model.Schedule
import id.dicoding.submission.footballmatchschedule.presenter.MatchPresenter
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import id.dicoding.submission.footballmatchschedule.view_operation.MatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class LeagueScheduleFragment : Fragment(), MatchView {
    private lateinit var mLeagueScheduleListRv : RecyclerView
    private lateinit var mProgressBar : ProgressBar
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout

    private lateinit var mPresenter : MatchPresenter
    private lateinit var mIdLeague: String
    private var mType : Int = 0

    private var mSchedules : MutableList<Schedule> = mutableListOf()
    private lateinit var mAdapter : LeagueSchedulesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = LeagueScheduleFragmentUI().createView(AnkoContext.create(ctx, this))
        receiveExtras()
        initView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPresenter()
        initAdapter()
        setRefreshListener()
    }

    private fun receiveExtras() {
        val bundle = arguments
        mIdLeague = bundle?.getString(resources.getString(R.string.id_league_intent_param), "") ?: ""
        mType = bundle?.getInt(resources.getString(R.string.schedule_type_intent_param)) ?: 0
    }

    private fun setRefreshListener() {
        mSwipeRefreshLayout.onRefresh {
            showLoading()
            mPresenter.getMatchList(mIdLeague, mType)
        }
    }

    private fun initAdapter() {
        mAdapter = LeagueSchedulesListAdapter(context!!, mSchedules, ::onScheduleClickListener)
        mLeagueScheduleListRv.adapter = mAdapter
    }

    private fun onScheduleClickListener(schedule: Schedule) {
        startActivity(intentFor<DetailMatchActivity>(
                resources.getString(R.string.schedule_intent_param) to schedule
        ))
    }

    private fun initPresenter() {
        mPresenter = MatchPresenter(this)
        mPresenter.getMatchList(mIdLeague, mType)
    }

    private fun initView(view : View) {
        mLeagueScheduleListRv = view.find(R.id.league_schedule_list_rv)
        mProgressBar = view.find(R.id.league_schedule_list_pb)
        mSwipeRefreshLayout = view.find(R.id.league_schedule_list_srl)
    }

    override fun showLoading() {
        mProgressBar.setVisible()
        mLeagueScheduleListRv.setInvisible()
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

class LeagueScheduleFragmentUI : AnkoComponent<LeagueScheduleFragment> {
    override fun createView(ui: AnkoContext<LeagueScheduleFragment>) = with(ui) {
        swipeRefreshLayout {
            id = R.id.league_schedule_list_srl
            backgroundColor = ContextCompat.getColor(context, greyBackgroundColor)
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
        }
    }
}
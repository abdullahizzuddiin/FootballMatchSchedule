package id.dicoding.submission.footballmatchschedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.dicoding.submission.footballmatchschedule.adapter.LeaguesListAdapter
import id.dicoding.submission.footballmatchschedule.model.League
import id.dicoding.submission.footballmatchschedule.presenter.LeaguePresenter
import id.dicoding.submission.footballmatchschedule.ui.LeaguesActivityUI
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import id.dicoding.submission.footballmatchschedule.view_operation.LeagueView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.onRefresh

class LeagueActivity : AppCompatActivity(), LeagueView {
    private val leagueActivityUI: LeaguesActivityUI = LeaguesActivityUI()
    private var leagues: MutableList<League> = mutableListOf()

    private lateinit var mAdapter: LeaguesListAdapter

    private lateinit var mPresenter: LeaguePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leagueActivityUI.setContentView(this)
        initPresenter()
        initAdapter()
        setRefreshListener()
    }

    private fun initAdapter() {
        mAdapter = LeaguesListAdapter(leagues, ::onLeagueClickListener)
        leagueActivityUI.mLeaguesListRv.adapter = mAdapter
    }

    private fun initPresenter() {
        mPresenter = LeaguePresenter(this)
        mPresenter.getLeagueList()
    }

    private fun setRefreshListener() {
        leagueActivityUI.mSwipeRefreshLayout.onRefresh {
            mPresenter.getLeagueList()
        }
    }

    private fun onLeagueClickListener(league: League) {
        startActivity(intentFor<LeagueScheduleActivity>(
                resources.getString(R.string.id_league_intent_param) to league.id,
                resources.getString(R.string.name_league_intent_param) to league.name
        ))
    }

    override fun showLoading() {
        leagueActivityUI.mProgressBar.setVisible()
        leagueActivityUI.mLeaguesListRv.setInvisible()
    }

    override fun hideLoading() {
        leagueActivityUI.mProgressBar.setInvisible()
        leagueActivityUI.mLeaguesListRv.setVisible()
    }

    override fun showLeagueList(data: List<League>) {
        leagueActivityUI.mSwipeRefreshLayout.isRefreshing = false
        leagues.clear()
        leagues.addAll(data)
        mAdapter.notifyDataSetChanged()
    }
}

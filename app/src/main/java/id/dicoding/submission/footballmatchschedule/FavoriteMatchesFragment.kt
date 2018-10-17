package id.dicoding.submission.footballmatchschedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.dicoding.submission.footballmatchschedule.adapter.MatchesListAdapter
import id.dicoding.submission.footballmatchschedule.model.Match
import id.dicoding.submission.footballmatchschedule.presenter.MatchPresenter
import id.dicoding.submission.footballmatchschedule.ui.LeagueScheduleFragmentUI
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import id.dicoding.submission.footballmatchschedule.view_operation.MatchView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteMatchesFragment : Fragment(), MatchView {
    private val leagueScheduleFragmentUI = LeagueScheduleFragmentUI()

    private lateinit var mPresenter: MatchPresenter
    private lateinit var mIdLeague: String

    private var mMatches: MutableList<Match> = mutableListOf()
    private lateinit var mAdapter: MatchesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = leagueScheduleFragmentUI.createView(AnkoContext.create(ctx, this))

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        receiveExtras()
        initAdapter()
        setRefreshListener()
        receiveExtras()
    }

    override fun onStart() {
        super.onStart()
        initPresenter()
    }

    private fun receiveExtras() {
        val bundle = arguments
        mIdLeague = bundle?.getString(resources.getString(R.string.id_league_intent_param), "") ?: ""
    }

    private fun setRefreshListener() {
        leagueScheduleFragmentUI.mSwipeRefreshLayout.onRefresh {
            showLoading()
            mPresenter.getFavoriteMatchList(ctx, mIdLeague)
        }
    }

    private fun initAdapter() {
        context?.let {
            mAdapter = MatchesListAdapter(it, mMatches, ::onScheduleClickListener)
            leagueScheduleFragmentUI.mLeagueScheduleListRv.adapter = mAdapter
        }
    }

    private fun onScheduleClickListener(idMatch: String) {
        startActivity(intentFor<DetailMatchActivity>(
                resources.getString(R.string.id_match_intent_param) to idMatch,
                resources.getString(R.string.is_favorite_intent_param) to true
        ))
    }

    private fun initPresenter() {
        mPresenter = MatchPresenter(this)
        mPresenter.getFavoriteMatchList(ctx, mIdLeague)
    }

    override fun showLoading() {
        leagueScheduleFragmentUI.mProgressBar.setVisible()
        leagueScheduleFragmentUI.mLeagueScheduleListRv.setInvisible()
    }

    override fun hideLoading() {
        leagueScheduleFragmentUI.mProgressBar.setInvisible()
        leagueScheduleFragmentUI.mLeagueScheduleListRv.setVisible()
    }

    override fun showMatchList(filteredData: List<Match>) {
        leagueScheduleFragmentUI.mSwipeRefreshLayout.isRefreshing = false
        mMatches.clear()
        mMatches.addAll(filteredData)
        mAdapter.notifyDataSetChanged()
    }

}
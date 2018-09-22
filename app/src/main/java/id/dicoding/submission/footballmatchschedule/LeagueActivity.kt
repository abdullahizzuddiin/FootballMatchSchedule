package id.dicoding.submission.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.ProgressBar
import id.dicoding.submission.footballmatchschedule.R.color.colorAccent
import id.dicoding.submission.footballmatchschedule.adapter.LeaguesListAdapter
import id.dicoding.submission.footballmatchschedule.model.League
import id.dicoding.submission.footballmatchschedule.presenter.LeaguePresenter
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import id.dicoding.submission.footballmatchschedule.view_operation.LeagueView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class LeagueActivity : AppCompatActivity(), LeagueView {
    private lateinit var mLeaguesListRv : RecyclerView

    private lateinit var mProgressBar : ProgressBar
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout
    private var leagues : MutableList<League> = mutableListOf()

    private lateinit var mAdapter : LeaguesListAdapter

    private lateinit var mPresenter : LeaguePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LeaguesActivityUI().setContentView(this)
        initView()
        initPresenter()
        initAdapter()
        setRefreshListener()
    }

    private fun initView() {
        mLeaguesListRv = find(R.id.league_list_rv)
        mProgressBar = find(R.id.league_list_pb)
        mSwipeRefreshLayout = find(R.id.league_list_srl)
    }

    private fun initAdapter() {
        mAdapter = LeaguesListAdapter(leagues, ::onLeagueClickListener)
        mLeaguesListRv.adapter = mAdapter
    }

    private fun initPresenter() {
        mPresenter = LeaguePresenter(this)
        mPresenter.getLeagueList()
    }

    private fun setRefreshListener() {
        mSwipeRefreshLayout.onRefresh {
            mPresenter.getLeagueList()
        }
    }

    private fun onLeagueClickListener(league : League) {
        startActivity(intentFor<LeagueScheduleActivity>(
                resources.getString(R.string.id_league_intent_param) to league.id,
                resources.getString(R.string.name_league_intent_param) to league.name
        ))
    }

    override fun showLoading() {
        mProgressBar.setVisible()
        mLeaguesListRv.setInvisible()
    }

    override fun hideLoading() {
        mProgressBar.setInvisible()
        mLeaguesListRv.setVisible()
    }

    override fun showLeagueList(data: List<League>) {
        mSwipeRefreshLayout.isRefreshing = false
        leagues.clear()
        leagues.addAll(data)
        mAdapter.notifyDataSetChanged()
    }
}

class LeaguesActivityUI : AnkoComponent<LeagueActivity> {
    override fun createView(ui: AnkoContext<LeagueActivity>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            swipeRefreshLayout {
                id = R.id.league_list_srl
                setColorSchemeResources(
                        colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    recyclerView {
                        id = R.id.league_list_rv
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar{
                        id = R.id.league_list_pb
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

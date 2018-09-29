package id.dicoding.submission.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import id.dicoding.submission.footballmatchschedule.model.Schedule
import id.dicoding.submission.footballmatchschedule.presenter.DetailMatchPresenter
import id.dicoding.submission.footballmatchschedule.utility.parseToIndonesianDate
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import kotlinx.android.synthetic.main.activity_detail_match.*

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var mSchedule : Schedule

    private lateinit var mPresenter : DetailMatchPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        receiveExtras()
        initView()
        initPresenter()
        requestTeamLogo()
    }

    private fun requestTeamLogo() {
        mSchedule.idHomeTeam.let {
            mPresenter.getDetailTeam(mSchedule.idHomeTeam!!, true)
        }
        mSchedule.idAwayTeam.let {
            mPresenter.getDetailTeam(mSchedule.idAwayTeam!!, false)
        }
    }

    private fun initPresenter() {
        mPresenter = DetailMatchPresenter(this)
    }

    private fun receiveExtras() {
        val bundle = intent.extras
        mSchedule = bundle?.getParcelable(resources.getString(R.string.schedule_intent_param)) as Schedule
    }

    private fun initView() {
        detail_date_tv.text = mSchedule.date?.parseToIndonesianDate()
        detail_home_team_name_tv.text = mSchedule.homeTeam
        detail_home_score_tv.text = mSchedule.homeScore?.toString()
        detail_home_shot_tv.text = mSchedule.homeShots?.toString()
        detail_home_goal_tv.text = mSchedule.homeGoalDetails
        detail_home_formation_tv.text = mSchedule.homeFormation
        detail_goalkeeper_home_name_tv.text = mSchedule.homeLineupGoalkeeper
        detail_defense_home_name_tv.text = mSchedule.homeLineupDefense
        detail_midfielder_home_name_tv.text = mSchedule.homeLineupMidfield
        detail_forward_home_name_tv.text = mSchedule.homeLineupForward
        detail_substitute_home_name_tv.text = mSchedule.homeLineupSubstitutes

        detail_away_team_name_tv.text = mSchedule.awayTeam
        detail_away_score_tv.text = mSchedule.awayScore?.toString()
        detail_away_shot_tv.text = mSchedule.awayShots?.toString()
        detail_away_goal_tv.text = mSchedule.awayGoalDetails
        detail_away_formation_tv.text = mSchedule.awayFormation
        detail_goalkeeper_away_name_tv.text = mSchedule.awayLineupGoalkeeper
        detail_defense_away_name_tv.text = mSchedule.awayLineupDefense
        detail_midfielder_away_name_tv.text = mSchedule.awayLineupMidfield
        detail_forward_away_name_tv.text = mSchedule.awayLineupForward
        detail_substitute_away_name_tv.text = mSchedule.awayLineupSubstitutes

    }

    override fun showHomeTeamLogo(url: String?) {
        Picasso.get().load(url).into(detail_home_team_logo_iv)
    }

    override fun showAwayTeamLogo(url: String?) {
        Picasso.get().load(url).into(detail_away_team_logo_iv)
    }
}

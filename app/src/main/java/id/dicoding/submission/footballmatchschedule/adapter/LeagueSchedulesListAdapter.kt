package id.dicoding.submission.footballmatchschedule.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.dicoding.submission.footballmatchschedule.R
import id.dicoding.submission.footballmatchschedule.model.Schedule
import id.dicoding.submission.footballmatchschedule.utility.parseToIndonesianDate
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_league_match.*
import org.jetbrains.anko.textColor

class LeagueSchedulesListAdapter(
        private val mContext : Context,
        private val mSchedules : List<Schedule>,
        private val mListener : (Schedule) -> Unit) :
        RecyclerView.Adapter<LeagueSchedulesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_league_match, parent, false))
    }

    override fun getItemCount(): Int = mSchedules.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mSchedules[position])
    }

    inner class ViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            itemView.setOnClickListener{
                mListener(mSchedules[adapterPosition])
            }
        }

        fun bind(schedule : Schedule) {
            match_date_tv.text = schedule.date?.parseToIndonesianDate()
            match_home_team_tv.text = schedule.homeTeam
            match_away_team_tv.text = schedule.awayTeam
            initScheduleScore(schedule)
        }

        private fun initScheduleScore(schedule: Schedule) {
            if (schedule.homeScore == null || schedule.awayScore == null) {
                hideScheduleScore()
                return
            }
            showScheduleScore()

            val homeScore = schedule.homeScore!!
            val awayScore = schedule.awayScore!!
            match_home_score_tv.text = homeScore.toString()
            match_away_score_tv.text = awayScore.toString()
            setScoreColor(homeScore, awayScore)
        }

        private fun hideScheduleScore() {
            match_home_score_tv.setInvisible()
            match_away_score_tv.setInvisible()
        }

        private fun showScheduleScore() {
            match_home_score_tv.setVisible()
            match_away_score_tv.setVisible()
        }

        private fun setScoreColor(homeScore : Int, awayScore : Int) {
            when {
                homeScore > awayScore -> {
                    match_home_score_tv.textColor = ContextCompat.getColor(mContext, R.color.greenWinTeamColor)
                    match_away_score_tv.textColor = ContextCompat.getColor(mContext, R.color.redLoseTeamColor)
                }
                awayScore > homeScore -> {
                    match_home_score_tv.textColor = ContextCompat.getColor(mContext, R.color.redLoseTeamColor)
                    match_away_score_tv.textColor = ContextCompat.getColor(mContext, R.color.greenWinTeamColor)
                }
                else -> {
                    match_home_score_tv.textColor = ContextCompat.getColor(mContext, R.color.yellowDrawTeamColor)
                    match_away_score_tv.textColor = ContextCompat.getColor(mContext, R.color.yellowDrawTeamColor)
                }
            }
        }
    }
}
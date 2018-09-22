package id.dicoding.submission.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.dicoding.submission.footballmatchschedule.R
import id.dicoding.submission.footballmatchschedule.model.Schedule
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_league_match.*

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
        viewHolder.setScoreColor(mSchedules[position])
    }

    inner class ViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            itemView.setOnClickListener{
                mListener(mSchedules[adapterPosition])
            }
        }

        fun bind(schedule : Schedule) {
            match_date_tv.text = schedule.date
            match_home_team_tv.text = schedule.homeTeam
            match_away_team_tv.text = schedule.awayTeam
            schedule.homeScore.let {
                match_home_score_tv.text = schedule.homeScore.toString()
            }
            schedule.awayScore.let {
                match_away_score_tv.text = schedule.awayScore.toString()
            }
        }

        fun setScoreColor(schedule: Schedule) {
//            check(schedule.homeScore != null)
//            check(schedule.awayScore != null)
//
//            when {
//                schedule.homeScore > schedule.awayScore -> {
//                    match_home_score_tv.textColor = mContext.resources.getColor(R.color.greenWinTeamColor)
//                    match_away_score_tv.textColor = mContext.resources.getColor(R.color.redLoseTeamColor)
//                }
//                schedule.awayScore > schedule.homeScore -> {
//                    match_home_score_tv.textColor = mContext.resources.getColor(R.color.redLoseTeamColor)
//                    match_away_score_tv.textColor = mContext.resources.getColor(R.color.greenWinTeamColor)
//                }
//                else -> {
//                    match_away_score_tv.textColor = mContext.resources.getColor(R.color.yellowDrawTeamColor)
//                    match_home_score_tv.textColor = mContext.resources.getColor(R.color.yellowDrawTeamColor)
//                }
//            }
        }
    }

}
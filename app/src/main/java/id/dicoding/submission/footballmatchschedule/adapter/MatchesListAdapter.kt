package id.dicoding.submission.footballmatchschedule.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.dicoding.submission.footballmatchschedule.R
import id.dicoding.submission.footballmatchschedule.model.Match
import id.dicoding.submission.footballmatchschedule.utility.parseToIndonesianDate
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_league_match.*
import org.jetbrains.anko.textColor

class MatchesListAdapter(
        private val mContext: Context,
        private val mMatches: List<Match>,
        private val mListener: (String) -> Unit) :
        RecyclerView.Adapter<MatchesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_league_match, parent, false))
    }

    override fun getItemCount(): Int = mMatches.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mMatches[position])
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            itemView.setOnClickListener {
                mMatches[adapterPosition].idEvent?.let {
                    mListener(it)
                }
            }
        }

        fun bind(match: Match) {
            with(match) {
                match_date_tv.text = this.date?.parseToIndonesianDate()
                match_home_team_tv.text = this.homeTeam
                match_away_team_tv.text = this.awayTeam
                initScheduleScore(this)
            }
        }

        private fun initScheduleScore(match: Match) {
            if (match.homeScore == null || match.awayScore == null) {
                hideScheduleScore()
                return
            }
            showScheduleScore()

            val homeScore = match.homeScore!!
            val awayScore = match.awayScore!!
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

        private fun setScoreColor(homeScore: Int, awayScore: Int) {
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
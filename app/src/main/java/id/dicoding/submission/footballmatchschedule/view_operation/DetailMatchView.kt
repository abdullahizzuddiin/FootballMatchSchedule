package id.dicoding.submission.footballmatchschedule.view_operation

import id.dicoding.submission.footballmatchschedule.model.League

interface DetailMatchView {
    fun showHomeTeamLogo(url : String?)
    fun showAwayTeamLogo(url : String?)
}
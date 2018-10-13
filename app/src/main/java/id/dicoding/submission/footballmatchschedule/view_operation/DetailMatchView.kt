package id.dicoding.submission.footballmatchschedule.view_operation

import id.dicoding.submission.footballmatchschedule.model.Match

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun updateMatch(data: Match)
    fun showHomeTeamLogo(url: String?)
    fun showAwayTeamLogo(url: String?)
}
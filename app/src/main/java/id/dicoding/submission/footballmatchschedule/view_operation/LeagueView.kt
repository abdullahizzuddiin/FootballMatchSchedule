package id.dicoding.submission.footballmatchschedule.view_operation

import id.dicoding.submission.footballmatchschedule.model.League

interface LeagueView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<League>)
}
package id.dicoding.submission.footballmatchschedule.view_operation

import id.dicoding.submission.footballmatchschedule.model.Schedule

interface MatchView {
    fun showLoading()

    fun hideLoading()

    fun showLeagueList(filteredData: List<Schedule>)

}
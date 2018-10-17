package id.dicoding.submission.footballmatchschedule.view_operation

import id.dicoding.submission.footballmatchschedule.model.Match

interface MatchView {
    fun showLoading()

    fun hideLoading()

    fun showMatchList(filteredData: List<Match>)

}
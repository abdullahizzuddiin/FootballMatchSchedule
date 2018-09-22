package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.SchedulesResponse
import id.dicoding.submission.footballmatchschedule.view_operation.MatchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(private val mMatchView: MatchView) {
    private val gson = Gson()
    private val request = ApiRepository()

    fun getMatchList(idLeague : String) {
        mMatchView.showLoading()
        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDBApi.getNextMatchesByLeagueId(idLeague)), SchedulesResponse::class.java)
            val filteredData = data.events

            uiThread {
                mMatchView.hideLoading()
                mMatchView.showLeagueList(filteredData)
            }
        }
    }
}
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

    fun getMatchList(idLeague : String, type: Int) {
        mMatchView.showLoading()
        doAsync {
            val url = if(type == 0) TheSportDBApi.getPrevMatchesByLeagueId(idLeague) else TheSportDBApi.getNextMatchesByLeagueId(idLeague)
            val data = gson.fromJson(request.doRequest(url), SchedulesResponse::class.java)

            uiThread {
                mMatchView.hideLoading()
                mMatchView.showLeagueList(data.events)
            }
        }
    }
}
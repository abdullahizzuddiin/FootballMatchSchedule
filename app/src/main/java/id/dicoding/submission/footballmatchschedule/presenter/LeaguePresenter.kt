package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.LeaguesResponse
import id.dicoding.submission.footballmatchschedule.view_operation.LeagueView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeaguePresenter(private val mLeagueView: LeagueView) {
    private val gson = Gson()
    private val request = ApiRepository()

    fun getLeagueList() {
        mLeagueView.showLoading()
        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDBApi.getLeagues()), LeaguesResponse::class.java)
            val filteredData = data.leagues.filter { it.sportType.equals("Soccer") }

            uiThread {
                mLeagueView.hideLoading()
                mLeagueView.showLeagueList(filteredData)
            }
        }
    }
}
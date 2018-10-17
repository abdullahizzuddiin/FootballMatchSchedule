package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.LeaguesResponse
import id.dicoding.submission.footballmatchschedule.ui.CoroutineContextProvider
import id.dicoding.submission.footballmatchschedule.view_operation.LeagueView
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeaguePresenter(
        private val mLeagueView: LeagueView,
        private val gson: Gson = Gson(),
        private val request: ApiRepository = ApiRepository(),
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        mLeagueView.showLoading()

        GlobalScope.async(context.main) {
            val data = bg {
                gson.fromJson(request.doRequest(TheSportDBApi.getLeagues()), LeaguesResponse::class.java)
            }

            val filteredData = data.await().leagues.filter { it.sportType.equals("Soccer") }
            mLeagueView.showLeagueList(filteredData)
            mLeagueView.hideLoading()
        }
    }
}
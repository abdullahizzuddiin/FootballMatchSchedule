package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.Match
import id.dicoding.submission.footballmatchschedule.model.MatchesResponse
import id.dicoding.submission.footballmatchschedule.model.TeamsResponse
import id.dicoding.submission.footballmatchschedule.ui.CoroutineContextProvider
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(
        private val mDetailMatchView: DetailMatchView,
        private val gson: Gson = Gson(),
        private val request: ApiRepository = ApiRepository(),
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailTeamLogo(idTeam: String, isHomeTeam: Boolean) {
        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDBApi.getTeamDetailByTeamId(idTeam)), TeamsResponse::class.java)

            uiThread {
                if (isHomeTeam) {
                    mDetailMatchView.showHomeTeamLogo(data.teams[0].logo)
                } else {
                    mDetailMatchView.showAwayTeamLogo(data.teams[0].logo)
                }
            }
        }
    }

    fun getDetailMatch(idMatch: String) {
        mDetailMatchView.showLoading()

        GlobalScope.async(context.main) {
            val data = bg {
                gson.fromJson(request.doRequest(TheSportDBApi.getMatchDetailByMatchId(idMatch)), MatchesResponse::class.java)
            }

            mDetailMatchView.updateMatch(data.await().events[0])
            mDetailMatchView.hideLoading()
        }
    }
}
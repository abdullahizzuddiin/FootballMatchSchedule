package id.dicoding.submission.footballmatchschedule.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.MatchesResponse
import id.dicoding.submission.footballmatchschedule.model.TeamsResponse
import id.dicoding.submission.footballmatchschedule.test.GlobalIdlingResources
import id.dicoding.submission.footballmatchschedule.ui.CoroutineContextProvider
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class DetailMatchPresenter(
        private val mDetailMatchView: DetailMatchView,
        private val gson: Gson = Gson(),
        private val request: ApiRepository = ApiRepository(),
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailTeamLogo(idTeam: String, isHomeTeam: Boolean) {
        GlobalIdlingResources.increment()
        GlobalScope.launch(context.main) {
            val data = bg {
                gson.fromJson(request.doRequest(TheSportDBApi.getTeamDetailByTeamId(idTeam)), TeamsResponse::class.java)
            }

            val logo = data.await().teams[0].logo
            GlobalIdlingResources.decrement()

            if (isHomeTeam) mDetailMatchView.showHomeTeamLogo(logo)
            else mDetailMatchView.showAwayTeamLogo(logo)
        }
    }

    fun getDetailMatch(idMatch: String) {
        GlobalIdlingResources.increment()
        mDetailMatchView.showLoading()
        GlobalScope.launch(context.main) {
            val data = bg {
                gson.fromJson(request.doRequest(TheSportDBApi.getMatchDetailByMatchId(idMatch)), MatchesResponse::class.java)
            }
            mDetailMatchView.updateMatch(data.await().events[0])
            mDetailMatchView.hideLoading()
            GlobalIdlingResources.decrement()
        }
    }
}
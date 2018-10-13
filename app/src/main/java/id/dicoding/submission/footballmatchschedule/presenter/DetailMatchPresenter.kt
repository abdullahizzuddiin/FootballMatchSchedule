package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.MatchesResponse
import id.dicoding.submission.footballmatchschedule.model.TeamsResponse
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(private val mDetailMatchView: DetailMatchView) {
    private val gson = Gson()
    private val request = ApiRepository()

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
        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDBApi.getMatchDetailByMatchId(idMatch)), MatchesResponse::class.java)

            uiThread {
                mDetailMatchView.hideLoading()
                mDetailMatchView.updateMatch(data.events[0])
            }
        }
    }
}
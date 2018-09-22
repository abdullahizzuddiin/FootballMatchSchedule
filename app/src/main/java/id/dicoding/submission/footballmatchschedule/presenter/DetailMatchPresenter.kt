package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.TeamsResponse
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(mDetailMatchView: DetailMatchView) {
    private val gson = Gson()
    private val request = ApiRepository()

    fun getDetailTeam(idTeam : String, isHomeTeam : Boolean) {
        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDBApi.getTeamDetailByTeamId(idTeam)), TeamsResponse::class.java)
            val filteredData = data.teams

            uiThread {

            }
        }
    }
}
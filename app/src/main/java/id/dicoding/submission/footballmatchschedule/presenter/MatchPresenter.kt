package id.dicoding.submission.footballmatchschedule.presenter

import android.content.Context
import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.db.database
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.TABLE_FAVORITE_MATCH
import id.dicoding.submission.footballmatchschedule.model.Match
import id.dicoding.submission.footballmatchschedule.model.MatchesResponse
import id.dicoding.submission.footballmatchschedule.view_operation.MatchView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(private val mMatchView: MatchView) {
    private val gson = Gson()
    private val request = ApiRepository()

    fun getMatchList(idLeague: String, type: String) {
        mMatchView.showLoading()
        doAsync {
            val url = getUrlByType(idLeague, type)
            val data = gson.fromJson(request.doRequest(url), MatchesResponse::class.java)

            uiThread {
                mMatchView.hideLoading()
                mMatchView.showLeagueList(data.events)
            }
        }
    }

    private fun getUrlByType(idLeague: String, type: String): String {
        return when (type) {
            "previous" -> TheSportDBApi.getPrevMatchesByLeagueId(idLeague)
            "next" -> TheSportDBApi.getNextMatchesByLeagueId(idLeague)
            else -> ""
        }
    }

    fun getFavoriteMatchList(context: Context, idLeague: String) {
        mMatchView.showLoading()
        context?.database?.use {
            val result = select(TABLE_FAVORITE_MATCH).whereArgs("(LEAGUE_ID = {idLeague})", "idLeague" to idLeague)
            val favorites = result.parseList(classParser<FavoriteMatch>())
            mMatchView.hideLoading()
            mMatchView.showLeagueList(Match.mapToMatches(favorites))
        }
    }
}
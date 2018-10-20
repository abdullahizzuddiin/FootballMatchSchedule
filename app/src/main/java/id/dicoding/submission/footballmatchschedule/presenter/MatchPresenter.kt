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
import id.dicoding.submission.footballmatchschedule.test.GlobalIdlingResources
import id.dicoding.submission.footballmatchschedule.ui.CoroutineContextProvider
import id.dicoding.submission.footballmatchschedule.view_operation.MatchView
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class MatchPresenter(
        private val mMatchView: MatchView,
        private val gson: Gson = Gson(),
        private val request: ApiRepository = ApiRepository(),
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchList(idLeague: String, type: String) {
        GlobalIdlingResources.increment()
        mMatchView.showLoading()
        GlobalScope.launch(context.main) {
            val url = getUrlByType(idLeague, type)
            val data = bg {
                gson.fromJson(request.doRequest(url), MatchesResponse::class.java)
            }

            mMatchView.showMatchList(data.await().events)
            mMatchView.hideLoading()
            GlobalIdlingResources.decrement()
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
        GlobalIdlingResources.increment()
        mMatchView.showLoading()
        context.database.use {
            val result = select(TABLE_FAVORITE_MATCH).whereArgs("(LEAGUE_ID = {idLeague})", "idLeague" to idLeague)
            val favorites = result.parseList(classParser<FavoriteMatch>())
            mMatchView.hideLoading()
            mMatchView.showMatchList(Match.mapToMatches(favorites))
            GlobalIdlingResources.decrement()
        }
    }
}
package id.dicoding.submission.footballmatchschedule.api

import id.dicoding.submission.footballmatchschedule.BuildConfig

const val ALL_LEAGUES_URL = "all_leagues.php"
const val NEXT_MATCHES_URL = "eventsnextleague.php"
const val PREV_MATCH_URL = "eventspastleague.php"
const val TEAM_DETAIL_URL = "lookupteam.php"
const val MATCH_DETAIL_URL = "lookupevent.php"
const val ID_PARAM = "id"

object TheSportDBApi {
    fun getLeagues(): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/$ALL_LEAGUES_URL"

//        return Uri.parse(BuildConfig.BASE_URL)
//                .buildUpon()
//                .appendPath(BuildConfig.VERSION_URL)
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath(ALL_LEAGUES_URL)
//                .toString()
    }

    fun getNextMatchesByLeagueId(idLeague: String): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/$NEXT_MATCHES_URL?$ID_PARAM=" + idLeague

//        return Uri.parse(BuildConfig.BASE_URL)
//                .buildUpon()
//                .appendPath(BuildConfig.VERSION_URL)
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath(NEXT_MATCHES_URL)
//                .appendQueryParameter(ID_PARAM, idLeague)
//                .toString()
    }

    fun getPrevMatchesByLeagueId(idLeague: String): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/$PREV_MATCH_URL?$ID_PARAM=" + idLeague

//        return Uri.parse(BuildConfig.BASE_URL)
//                .buildUpon()
//                .appendPath(BuildConfig.VERSION_URL)
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath(PREV_MATCH_URL)
//                .appendQueryParameter(ID_PARAM, idLeague)
//                .toString()
    }

    fun getTeamDetailByTeamId(teamId: String): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/$TEAM_DETAIL_URL?$ID_PARAM=" + teamId

//        return Uri.parse(BuildConfig.BASE_URL)
//                .buildUpon()
//                .appendPath(BuildConfig.VERSION_URL)
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath(TEAM_DETAIL_URL)
//                .appendQueryParameter(ID_PARAM, teamId)
//                .toString()
    }


    fun getMatchDetailByMatchId(matchId: String): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/$MATCH_DETAIL_URL?$ID_PARAM=" + matchId

//        return Uri.parse(BuildConfig.BASE_URL)
//                .buildUpon()
//                .appendPath(BuildConfig.VERSION_URL)
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath(MATCH_DETAIL_URL)
//                .appendQueryParameter(ID_PARAM, matchId)
//                .toString()
    }
}
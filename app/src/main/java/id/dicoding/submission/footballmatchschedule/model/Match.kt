package id.dicoding.submission.footballmatchschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
        @SerializedName("idEvent")
        var idEvent: String? = null,

        @SerializedName("idLeague")
        var idLeague: String? = null,

        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,

        @SerializedName("idHomeTeam")
        var idHomeTeam: String? = null,

        @SerializedName("intHomeScore")
        var homeScore: Int? = null,

        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,

        @SerializedName("idAwayTeam")
        var idAwayTeam: String? = null,

        @SerializedName("intAwayScore")
        var awayScore: Int? = null,

        @SerializedName("dateEvent")
        var date: String? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGoalDetails: String? = null,

        @SerializedName("strHomeRedCards")
        var homeRedCards: String? = null,

        @SerializedName("strHomeYellowCards")
        var homeYellowCards: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeLineupSubstitutes: String? = null,

        @SerializedName("strHomeFormation")
        var homeFormation: String? = null,

        @SerializedName("strAwayRedCards")
        var awayRedCards: String? = null,

        @SerializedName("strAwayYellowCards")
        var awayYellowCards: String? = null,

        @SerializedName("strAwayGoalDetails")
        var awayGoalDetails: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayLineupDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayLineupMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var awayLineupForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var awayLineupSubstitutes: String? = null,

        @SerializedName("strAwayFormation")
        var awayFormation: String? = null,

        @SerializedName("intHomeShots")
        var homeShots: Int? = null,

        @SerializedName("intAwayShots")
        var awayShots: Int? = null

) : Parcelable {
    companion object {
        fun mapToMatches(favoriteMatches: List<FavoriteMatch>): List<Match> {
            val matches = mutableListOf<Match>()
            favoriteMatches.forEach {
                matches.add(mapToMatch(it))
            }

            return matches
        }

        private fun mapToMatch(favoriteMatch: FavoriteMatch): Match {
            return Match(
                    idEvent = favoriteMatch.matchId,
                    idLeague = favoriteMatch.leagueId,
                    homeTeam = favoriteMatch.homeTeamName,
                    awayTeam = favoriteMatch.awayTeamName,
                    homeScore = favoriteMatch.homeTeamScore,
                    awayScore = favoriteMatch.awayTeamScore,
                    date = favoriteMatch.matchDate)
        }
    }
}
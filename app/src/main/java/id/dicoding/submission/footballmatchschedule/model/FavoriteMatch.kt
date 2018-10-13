package id.dicoding.submission.footballmatchschedule.model

data class FavoriteMatch(
        val id: Long?,
        val matchId: String?,
        val leagueId: String?,
        val homeTeamName: String?,
        val homeTeamScore: Int?,
        val awayTeamName: String?,
        val awayTeamScore: Int?,
        val matchDate: String?) {

    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val LEAGUE_ID: String = "LEAGUE_ID"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val HOME_TEAM_SCORE: String = "HOME_TEAM_SCORE"
        const val AWAY_TEAM_SCORE: String = "AWAY_TEAM_SCORE"
        const val MATCH_DATE: String = "MATCH_DATE"
    }
}
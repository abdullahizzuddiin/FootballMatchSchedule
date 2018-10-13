package id.dicoding.submission.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("idTeam")
        var id: String? = null,

        @SerializedName("strTeam")
        var name: String? = null,

        @SerializedName("strTeamBadge")
        var logo: String? = null
)
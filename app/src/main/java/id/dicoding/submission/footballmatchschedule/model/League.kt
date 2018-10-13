package id.dicoding.submission.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class League(
        @SerializedName("idLeague")
        var id: String? = null,

        @SerializedName("strLeague")
        var name: String? = null,

        @SerializedName("strSport")
        var sportType: String? = null
)

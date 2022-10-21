

package com.example.flixsterpart2

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class TvBaseResponse(
    @SerialName("results")
    val result: List<Tv>?
)

@Keep
@Serializable
data class Tv(
    @SerialName("name")
    val title: String?,
    @SerialName("vote_average")
    val vote: Double?,
    @SerialName("vote_count")
    val count: Int?,
    @SerialName("poster_path")
    val poster_path: String?,
    @SerialName("first_air_date")
    val release_date: String?,
    @SerialName("overview")
    val overview: String?

) : java.io.Serializable

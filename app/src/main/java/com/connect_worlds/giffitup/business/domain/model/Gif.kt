package com.connect_worlds.giffitup.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gif(
    val id: String,
    val title: String,
    val hasAudio: Boolean,
    val isTrending: Boolean,
    val mediaTypes: List<Media>
) : Parcelable {
}
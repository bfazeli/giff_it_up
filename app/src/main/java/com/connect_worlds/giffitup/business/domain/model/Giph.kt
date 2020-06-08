package com.connect_worlds.giffitup.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Giph(
    val id: String,
    val title: String,
    val hasAudio: Boolean,
    val mediaTypes: List<Media>
) : Parcelable {
}
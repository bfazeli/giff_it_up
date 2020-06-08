package com.connect_worlds.giffitup.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val largeGif: GifProperties,
    val mediumGif: GifProperties,
    val smallGif: GifProperties
) : Parcelable {
}

@Parcelize
data class GifProperties(
    val url: String
) : Parcelable {
}
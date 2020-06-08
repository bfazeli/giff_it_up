package com.connect_worlds.giffitup.framework.presentation.giphlist.state

import android.os.Parcelable
import com.connect_worlds.giffitup.business.domain.model.Gif
import com.connect_worlds.giffitup.business.domain.state.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GifListViewState(
    var gifList: ArrayList<Gif>? = null,
    var searchQuery: String? = null,
    var page: Int? = null,
    var isQueryExhausted: Boolean? = null,
    var layoutManagerState: Parcelable? = null,
    var numGifsInCache: Int? = null
): Parcelable, ViewState {
}
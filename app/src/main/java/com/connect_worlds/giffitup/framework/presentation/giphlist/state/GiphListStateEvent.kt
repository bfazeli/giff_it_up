package com.connect_worlds.giffitup.framework.presentation.giphlist.state

import com.connect_worlds.giffitup.business.domain.state.StateEvent

sealed class GiphListStateEvent : StateEvent {
    class SearchGiphsEvent(
        val clearLayoutManagerState: Boolean = true
    ) : GiphListStateEvent() {
        override fun errorInfo(): String = "Error getting list of Giphs."
        override fun eventName(): String = "SearchGiphsEvent"
        override fun shouldDisplayProgressBar(): Boolean = true
    }

    class GetNumGiphsInCacheEvent : GiphListStateEvent() {
        override fun errorInfo(): String = "Error getting the number of giphs from the cache."
        override fun eventName(): String = "GetNumGiphsInCacheEvent"
        override fun shouldDisplayProgressBar(): Boolean = true
    }
}
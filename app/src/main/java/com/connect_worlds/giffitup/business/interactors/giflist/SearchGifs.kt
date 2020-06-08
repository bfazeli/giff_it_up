package com.connect_worlds.giffitup.business.interactors.giflist

import com.connect_worlds.giffitup.business.data.cache.abstraction.GifCacheDataSource
import com.connect_worlds.giffitup.business.data.network.ApiResponseHandler
import com.connect_worlds.giffitup.business.data.network.abstraction.GifNetworkDataSource
import com.connect_worlds.giffitup.business.data.util.safeApiCall
import com.connect_worlds.giffitup.business.data.util.safeCacheCall
import com.connect_worlds.giffitup.business.domain.model.Gif
import com.connect_worlds.giffitup.business.domain.state.*
import com.connect_worlds.giffitup.framework.presentation.giphlist.state.GifListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchGifs(
    private val gifCacheDataSource: GifCacheDataSource,
    private val gifNetworkDataSource: GifNetworkDataSource
) {
    companion object {
        val SEARCH_GIFS_SUCCESS = "Successfully retrieved list of gifs."
        val SEARCH_GIFS_NO_MATCHING_RESULTS = "There are no gifs that match that query."
    }

    fun searchGifs(
        query: String,
        page: Int,
        stateEvent: StateEvent
    ): Flow<DataState<GifListViewState>?> = flow {
        val updatedPage = if (page <= 0) 1 else page
        val apiResult = safeApiCall(Dispatchers.IO) {
            gifNetworkDataSource.searchGifs(query, updatedPage)
        }

        val response = object : ApiResponseHandler<GifListViewState, List<Gif>>(
            apiResult,
            stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<Gif>): DataState<GifListViewState>? {
                var message: String? =
                    SEARCH_GIFS_SUCCESS
                var uiComponentType: UIComponentType? = UIComponentType.None()
                if (resultObj.isEmpty()) {
                    message =
                        SEARCH_GIFS_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }
                return DataState.data(
                    Response(
                        message = message,
                        uiComponentType = uiComponentType as UIComponentType,
                        messageType = MessageType.Success()
                    ),
                    GifListViewState(
                        gifList = ArrayList(resultObj)
                    ),
                    stateEvent = stateEvent
                )
            }
        }.getResult()

        emit(response)
    }

    private suspend fun insertGifsToCache(response: DataState<GifListViewState>?) =
        response?.data?.gifList?.let { gifs ->
            safeCacheCall(Dispatchers.IO) {
                gifCacheDataSource.insertGifs(gifs)
            }
        }
}
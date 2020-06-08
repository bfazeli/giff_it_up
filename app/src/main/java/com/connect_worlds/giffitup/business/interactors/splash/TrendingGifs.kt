package com.connect_worlds.giffitup.business.interactors.splash

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

class TrendingGifs(
    private val gifNetworkDataSource: GifNetworkDataSource,
    private val gifCacheDataSource: GifCacheDataSource
) {
    companion object {
        val SEARCH_GIFS_SUCCESS = "Successfully retrieved list of gifs."
        val SEARCH_GIFS_NO_MATCHING_RESULTS = "There are no gifs that match that query."
        var previousCachePage: Int = 1
        var nextApiPosition: String = "n/a"
    }

    private suspend fun grabDataFromNetwork(
        stateEvent: StateEvent
    ): DataState<GifListViewState>? {
        val apiResult = safeApiCall(Dispatchers.IO) {
            gifNetworkDataSource.getTrendingGifs(nextApiPosition)
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

        insertGifsToCache(response)

        return response
    }

    private suspend fun grabDataFromCache(
        page: Int,
        stateEvent: StateEvent
    ): DataState<GifListViewState>? {
        val cacheResult = safeApiCall(Dispatchers.IO) {
            gifCacheDataSource.getGifs(page)
        }

        val response = object : ApiResponseHandler<GifListViewState, List<Gif>>(
            cacheResult,
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

        return response
    }

    fun getGifs(
        pageRequest: Int,
        stateEvent: StateEvent
    ): Flow<DataState<GifListViewState>?> = flow {
        emit(
            if (pageRequest > previousCachePage) {
                grabDataFromNetwork(stateEvent)
            } else {
                grabDataFromCache(pageRequest, stateEvent)
            }
        )
    }

    private suspend fun insertGifsToCache(response: DataState<GifListViewState>?) =
        response?.data?.gifList?.let { gifs ->
            safeCacheCall(Dispatchers.IO) {
                gifCacheDataSource.insertGifs(gifs)
            }
        }
}
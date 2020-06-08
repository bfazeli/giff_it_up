package com.connect_worlds.giffitup.business.data.network.implementation

import com.connect_worlds.giffitup.business.data.network.abstraction.GifNetworkDataSource
import com.connect_worlds.giffitup.business.domain.model.Gif
import com.connect_worlds.giffitup.framework.datasource.network.abstraction.TenorGifService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifNetworkDataSourceImpl
@Inject
constructor(
    private val tenorGiphService: TenorGifService
): GifNetworkDataSource {
    override suspend fun searchGifs(query: String, page: Int): List<Gif> =
        tenorGiphService.searchGifs(query, page)

    override suspend fun getTrendingGifs(
        nextPosition: String
    ): List<Gif> =
        tenorGiphService.getTrendingGifs(nextPosition)
}
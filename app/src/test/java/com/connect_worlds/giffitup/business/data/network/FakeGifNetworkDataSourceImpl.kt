package com.connect_worlds.giffitup.business.data.network

import com.connect_worlds.giffitup.business.data.network.abstraction.GifNetworkDataSource
import com.connect_worlds.giffitup.business.domain.model.Gif

class FakeGifNetworkDataSourceImpl
constructor(
    private val gifsData: HashMap<String, Gif>
) : GifNetworkDataSource {
    override suspend fun searchGifs(query: String, page: Int): List<Gif> =
        gifsData.filter { it.value.title.contains(query, true) }.values.toList()

    override suspend fun getTrendingGifs(nextPosition: String): List<Gif> =
        gifsData.filter { it.value.isTrending }.values.toList()
}
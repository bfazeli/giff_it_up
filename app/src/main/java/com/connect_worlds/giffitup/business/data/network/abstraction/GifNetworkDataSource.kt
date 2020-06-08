package com.connect_worlds.giffitup.business.data.network.abstraction

import com.connect_worlds.giffitup.business.domain.model.Gif

interface GifNetworkDataSource {
    suspend fun searchGifs(query: String, page: Int = 0): List<Gif>

    suspend fun getTrendingGifs(
        nextPosition: String
    ): List<Gif>
}
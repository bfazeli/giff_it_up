package com.connect_worlds.giffitup.framework.datasource.network.abstraction

import com.connect_worlds.giffitup.business.domain.model.Gif

interface TenorGifService {
    suspend fun searchGifs(query: String, page: Int = 0): List<Gif>

    suspend fun getTrendingGifs(page: Int = 0): List<Gif>
}
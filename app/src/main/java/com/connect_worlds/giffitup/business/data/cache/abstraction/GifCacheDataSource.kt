package com.connect_worlds.giffitup.business.data.cache.abstraction

import com.connect_worlds.giffitup.business.domain.model.Gif

interface GifCacheDataSource {
    suspend fun searchGifs(
        query: String,
        page: Int
    ): List<Gif>

    suspend fun searchGifById(id: String): Gif?

    suspend fun getGifs(
        page: Int
    ): List<Gif>

    suspend fun insertGifs(gifs: List<Gif>): LongArray

    suspend fun getNumGifs(): Int
}
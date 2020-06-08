package com.connect_worlds.giffitup.framework.datasource.cache.abstraction

import com.connect_worlds.giffitup.business.domain.model.Gif

interface GifDaoService {
    suspend fun searchGifs(
        query: String,
        page: Int
    ): List<Gif>

    suspend fun getGifs(page: Int): List<Gif>

    suspend fun insertGifs(giphs: List<Gif>): LongArray

    suspend fun searchGifById(id: String): Gif?

    suspend fun getNumGifs(): Int
}
package com.connect_worlds.giffitup.business.data.cache.abstraction

import com.connect_worlds.giffitup.business.domain.model.Giph

interface GiphCacheDataSource {
    suspend fun searchGiphs(
        query: String
    ): List<Giph>

    suspend fun searchGiphById(id: String): Giph?
}
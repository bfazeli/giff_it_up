package com.connect_worlds.giffitup.business.data.cache.implementation

import com.connect_worlds.giffitup.business.data.cache.abstraction.GiphCacheDataSource
import com.connect_worlds.giffitup.business.domain.model.Giph
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphCacheDataSourceImpl
@Inject
constructor(
    private val giphDaoService: GiphDaoService
) : GiphCacheDataSource {
    override suspend fun searchGiphs(query: String): List<Giph> =
        giphDaoService.searchNotes(query)

    override suspend fun searchGiphById(id: String): Giph? =
        giphDaoService.searchGiphById(id)
}
package com.connect_worlds.giffitup.business.data.cache.implementation

import com.connect_worlds.giffitup.business.data.cache.abstraction.GifCacheDataSource
import com.connect_worlds.giffitup.business.domain.model.Gif
import com.connect_worlds.giffitup.framework.datasource.cache.abstraction.GifDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifCacheDataSourceImpl
@Inject
constructor(
    private val gifDaoService: GifDaoService
) : GifCacheDataSource {
    override suspend fun searchGifs(query: String, page: Int): List<Gif> =
        gifDaoService.searchGifs(query, page)

    override suspend fun getGifs(page: Int): List<Gif> =
        gifDaoService.getGifs(page)

    override suspend fun insertGifs(gifs: List<Gif>): LongArray =
        gifDaoService.insertGifs(gifs)

    override suspend fun searchGifById(id: String): Gif? =
        gifDaoService.searchGifById(id)

    override suspend fun getNumGifs(): Int =
        gifDaoService.getNumGifs()
}
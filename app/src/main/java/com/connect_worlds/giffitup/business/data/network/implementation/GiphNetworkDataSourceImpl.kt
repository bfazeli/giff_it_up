package com.connect_worlds.giffitup.business.data.network.implementation

import com.connect_worlds.giffitup.business.data.network.abstraction.GiphNetworkDataSource
import com.connect_worlds.giffitup.business.domain.model.Giph
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphNetworkDataSourceImpl
@Inject
constructor(
    private val tenorGiphService: TenorGifService
): GiphNetworkDataSource {
    override suspend fun searchGiphs(query: String): List<Giph> =
        tenorGiphService.searchGiphs(query)

    override suspend fun getTrendingGiphs(paginationInfo: Pair<Int, Int>): List<Giph> =
        tenorGiphService.getTrendingGiphs(paginationInfo)
}
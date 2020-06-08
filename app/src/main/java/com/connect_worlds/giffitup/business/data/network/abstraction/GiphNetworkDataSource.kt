package com.connect_worlds.giffitup.business.data.network.abstraction

import com.connect_worlds.giffitup.business.domain.model.Giph

interface GiphNetworkDataSource {
    suspend fun searchGiphs(query: String): List<Giph>

    suspend fun getTrendingGiphs(paginated: Pair<Int, Int> = Pair(0, 20)): List<Giph>
}
package com.connect_worlds.giffitup.di

import com.connect_worlds.giffitup.business.data.cache.FakeGifCacheDataSourceImpl
import com.connect_worlds.giffitup.business.data.cache.abstraction.GifCacheDataSource
import com.connect_worlds.giffitup.business.data.network.FakeGifNetworkDataSourceImpl
import com.connect_worlds.giffitup.business.data.network.abstraction.GifNetworkDataSource
import com.connect_worlds.giffitup.business.domain.model.Gif
import com.connect_worlds.giffitup.util.isUnitTest
import kotlin.collections.HashMap

class DependencyContainer {
    lateinit var gifNetworkDataSource: GifNetworkDataSource
    lateinit var gifCacheDataSource: GifCacheDataSource

    init {
        isUnitTest = true
    }

    lateinit var gifsData: HashMap<String, Gif>

    fun build() {
        gifNetworkDataSource = FakeGifNetworkDataSourceImpl(
            gifsData = gifsData
        )
        gifCacheDataSource = FakeGifCacheDataSourceImpl(
            gifsData = gifsData
        )
    }
}
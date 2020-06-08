package com.connect_worlds.giffitup.business.data.cache

import com.connect_worlds.giffitup.business.data.cache.abstraction.GifCacheDataSource
import com.connect_worlds.giffitup.business.domain.model.Gif
import com.connect_worlds.giffitup.framework.datasource.cache.database.GIF_PAGINATION_PAGE_SIZE
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

const val FORCE_SEARCH_GIFS_EXCEPTION = "FORCE_SEARCH_NOTES_EXCEPTION"

class FakeGifCacheDataSourceImpl
constructor(
    private val gifsData: HashMap<String, Gif>
): GifCacheDataSource{
    override suspend fun searchGifs(query: String, page: Int): List<Gif> {
        if(query.equals(FORCE_SEARCH_GIFS_EXCEPTION)){
            throw Exception("Something went wrong searching the cache for gifs.")
        }
        val results: ArrayList<Gif> = ArrayList()
        for(gif in gifsData.values){
            if(gif.title.contains(query, true)){
                results.add(gif)
            }
            if(results.size > (page * GIF_PAGINATION_PAGE_SIZE)){
                break
            }
        }
        return results
    }

    override suspend fun getGifs(page: Int): List<Gif> {
        val results: ArrayList<Gif> = ArrayList()
        for(gif in gifsData.values){
            if(gif.isTrending){
                results.add(gif)
            }
            if(results.size > (page * GIF_PAGINATION_PAGE_SIZE)){
                break
            }
        }
        return results
    }

    override suspend fun insertGifs(gifs: List<Gif>): LongArray {
        val results = LongArray(gifs.size)
        for((index,gif) in gifs.withIndex()){
            results[index] = 1
            gifsData[gif.id] = gif
        }
        return results
    }

    override suspend fun searchGifById(id: String): Gif? =
        gifsData[id]

    override suspend fun getNumGifs(): Int =
        gifsData.size
}
package com.dicoding.capstoneproject.core.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import com.dicoding.capstoneproject.core.data.local.room.MediaDao
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.dicoding.capstoneproject.core.utility.DataMapper
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mediaDao: MediaDao){

    fun getMediaList() : Flow<List<MediaItem>> = mediaDao.getAllMedia()

    fun getFavoriteMediaList() : Flow<List<MediaItem>> = mediaDao.getAllFavoriteMedia()

    fun getMediaDetail(type: String, id: String) : Flow<MediaItem> = mediaDao.getMedia(type, id)

    suspend fun insertMediaList(mediaList: List<MediaItem>) = mediaDao.insertList(mediaList)

    suspend fun insertMediaItem(mediaItem: MediaItem) = mediaDao.insertSingle(mediaItem)

    fun setFavoriteState(mediaItem: MediaItem, state: Boolean) {
        mediaItem.favorite = state
        mediaDao.update(mediaItem)
    }
}
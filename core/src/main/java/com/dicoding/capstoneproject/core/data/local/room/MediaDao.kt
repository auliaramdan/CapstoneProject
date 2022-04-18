package com.dicoding.capstoneproject.core.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingle(media: MediaItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(mediaList: List<MediaItem>)

    @Update
    fun update(media: MediaItem)

    @Query("DELETE FROM mediaitem")
    fun deleteAll()

    @Query("SELECT * FROM mediaitem")
    fun getAllMedia() : Flow<List<MediaItem>>

    @Query("SELECT * FROM mediaitem WHERE favorite == 1")
    fun getAllFavoriteMedia() : Flow<List<MediaItem>>

    @Query("SELECT * FROM mediaitem WHERE id == :id AND media_type == :type")
    fun getMedia(type: String, id: String) : Flow<MediaItem>
}
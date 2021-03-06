package com.dicoding.capstoneproject.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem

@Database(entities = [MediaItem::class], version = 1, exportSchema = false)
abstract class MediaDatabase: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}
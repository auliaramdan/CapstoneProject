package com.dicoding.capstoneproject.core.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MediaItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id: Int = 0,

    @ColumnInfo(name = "first_air_date")
    @field:SerializedName("first_air_date")
    var firstAirDate: String? = null,

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    var overview: String? = null,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "media_type")
    @field:SerializedName("media_type")
    var mediaType: String? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    var title: String? = null,

    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    var releaseDate: String? = null,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,

    @ColumnInfo(name = "last_fetched")
    var lastFetched: Long? = null
) : Parcelable

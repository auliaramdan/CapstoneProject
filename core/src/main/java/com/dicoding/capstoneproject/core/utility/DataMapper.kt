package com.dicoding.capstoneproject.core.utility

import android.util.Log
import androidx.paging.DataSource
import com.dicoding.capstoneproject.core.R
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType

object DataMapper {

    fun mapEntityListToDomainList(input: List<MediaItem>): List<MediaModel> =
            input.map {
                mapSingleEntityToSingleDomain(it)
            }

    fun mapSingleDomainToSingleEntity(input: MediaModel): MediaItem {

        val mediaItem = MediaItem(
            id = input.id,
            posterPath = input.imagePath,
            overview = input.description,
            favorite = input.favorite
        )

        when(input.mediaType) {
            MediaType.MOVIE -> {
                mediaItem.title = input.name
                mediaItem.releaseDate = input.firstReleasedDate
                mediaItem.mediaType = "movie"
            }
            MediaType.TV_SHOW -> {
                mediaItem.name = input.name
                mediaItem.firstAirDate = input.firstReleasedDate
                mediaItem.mediaType = "tv"
            }
        }

        return mediaItem
    }

    fun mapSingleEntityToSingleDomain(input: MediaItem): MediaModel {
        val model = MediaModel(
            mediaType = mapEntityTypeToMediaType(input.mediaType!!),
            id = input.id,
            imagePath = input.posterPath!!,
            description = input.overview!!,
            favorite = input.favorite
        )
        model.name = when(model.mediaType) {
            MediaType.MOVIE -> input.title!!
            MediaType.TV_SHOW -> input.name!!
            else -> ""
        }
        model.firstReleasedDate = when(model.mediaType) {
            MediaType.MOVIE -> input.releaseDate!!
            MediaType.TV_SHOW -> input.firstAirDate!!
            else -> ""
        }

        return model
    }

    private fun mapEntityTypeToMediaType(input: String): MediaType =
        MediaType.values().first { it.entityTypeName == input}
}
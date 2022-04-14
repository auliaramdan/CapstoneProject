package com.dicoding.capstoneproject.core.data.local.entity

import com.google.gson.annotations.SerializedName

data class MediaListResponse(

    @field:SerializedName("item_count")
	val itemCount: Int? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("items")
	val items: List<MediaItem>? = null,
    )

package com.dicoding.capstoneproject.core.data.remote

import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import com.dicoding.capstoneproject.core.data.local.entity.MediaListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(value = "list/{list_id}")
    suspend fun getMediaList(
        @Path("list_id") list_id: String) : MediaListResponse

    @GET(value = "{media_type}/{media_id}")
    suspend fun getMediaDetail(
        @Path("media_type") media_type: String, @Path("media_id") media_id: String) : MediaItem
}
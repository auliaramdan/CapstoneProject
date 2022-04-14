package com.dicoding.capstoneproject.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaModel(
    val id: Int = 0,
    var name: String = "",
    val imagePath: String = "",
    val description: String = "",
    var firstReleasedDate: String = "",
    val favorite: Boolean = false,
    val mediaType: MediaType?
) : Parcelable
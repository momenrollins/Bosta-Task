package com.momen.bostatask.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val albumId: Int, val id: Int, val thumbnailUrl: String, val title: String, val url: String
) : Parcelable {
    override fun toString(): String {
        return title
    }
}
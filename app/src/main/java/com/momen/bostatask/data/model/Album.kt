package com.momen.bostatask.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val id: Int,
    val userId: Int,
    val title: String
) : Parcelable {
    override fun toString(): String {
        return title
    }
}
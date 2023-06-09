package com.momen.bostatask.data.repository

import com.momen.bostatask.data.model.Album
import com.momen.bostatask.data.model.Photo
import com.momen.bostatask.data.model.User
import com.momen.bostatask.data.network.ApiService
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUser(userId: Int): User {
        return apiService.getUser(userId)
    }

    suspend fun getAlbumsByUser(userId: Int): List<Album> {
        return apiService.getAlbumsByUser(userId)
    }

    suspend fun getPhotosByAlbum(albumId: Int): List<Photo> {
        return apiService.getPhotosByAlbum(albumId)
    }
}

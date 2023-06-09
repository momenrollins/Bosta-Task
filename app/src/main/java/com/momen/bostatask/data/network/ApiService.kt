package com.momen.bostatask.data.network

import com.momen.bostatask.data.model.Album
import com.momen.bostatask.data.model.Photo
import com.momen.bostatask.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @GET("albums")
    suspend fun getAlbumsByUser(@Query("userId") albumId: Int): List<Album>

    @GET("albums/{albumId}/photos")
    suspend fun getPhotosByAlbum(@Path("albumId") albumId: Int): List<Photo>
}
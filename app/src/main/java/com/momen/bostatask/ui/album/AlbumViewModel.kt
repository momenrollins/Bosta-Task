package com.momen.bostatask.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momen.bostatask.data.model.Photo
import com.momen.bostatask.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchPhotos(albumId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val photosResult = repository.getPhotosByAlbum(albumId)
                _photos.value = photosResult
            } catch (e: Exception) {
                _photos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}







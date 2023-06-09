package com.momen.bostatask.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momen.bostatask.data.model.Album
import com.momen.bostatask.utils.GeneralStates
import com.momen.bostatask.data.model.User
import com.momen.bostatask.data.repository.DataRepository
import com.momen.bostatask.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    private val _fetchDataStatus = MutableLiveData<GeneralStates>()
    val fetchDataStatus: LiveData<GeneralStates> = _fetchDataStatus
    fun fetchData(userId: Int) {
        viewModelScope.launch {
            _fetchDataStatus.postValue(GeneralStates.Loading)
            try {
                // Check internet connection
                val hasInternet = NetworkUtils.hasInternetConnection()

                if (!hasInternet) {
                    _fetchDataStatus.postValue(GeneralStates.NoConnect)
                    return@launch
                }

                val userDeferred = async { repository.getUser(userId) }
                val albumsDeferred = async { repository.getAlbumsByUser(userId) }

                val userResult = userDeferred.await()
                val albumsResult = albumsDeferred.await()

                _user.postValue(userResult)
                _albums.postValue(albumsResult)
                _fetchDataStatus.postValue(GeneralStates.Success)
            } catch (e: Exception) {
                _fetchDataStatus.postValue(GeneralStates.Error(e.message))
            }
        }
    }
}
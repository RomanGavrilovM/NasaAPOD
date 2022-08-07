package com.example.nasaapod.ui.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.api.mars.LatestPhoto
import com.example.nasaapod.api.mars.MarsResponse
import com.example.nasaapod.domain.MarsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class MarsViewModel(val repository: MarsRepository) : ViewModel() {

    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    val _photos: MutableSharedFlow<MarsResponse> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val photos: Flow<MarsResponse> = _photos

    fun requestMars() {

        _loading.value = true
        viewModelScope.launch {
            try {
                val marsPhotos = repository.Mars()
                _photos.emit(marsPhotos)

            } catch (exc: IOException) {
                _error.emit("Error from flow mars")
            }
            _loading.emit(false)
        }


    }

    class MarsViewModelFactory(private val repository: MarsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MarsViewModel(repository) as T

    }

}
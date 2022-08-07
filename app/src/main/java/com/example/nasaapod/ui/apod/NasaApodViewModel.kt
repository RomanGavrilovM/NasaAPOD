package com.example.nasaapod.ui.apod

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.api.apod.ApodResponse
import com.example.nasaapod.domain.NasaApodRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.util.*

class NasaApodViewModel(val repository: NasaApodRepository) : ViewModel() {

    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    val _today: MutableSharedFlow<ApodResponse> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val today: Flow<ApodResponse> = _today

    val _oneDayAgo: MutableSharedFlow<ApodResponse> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val oneDayAgo: Flow<ApodResponse> = _oneDayAgo

    val _twoDaysAgo: MutableSharedFlow<ApodResponse> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val twoDaysAgo: Flow<ApodResponse> = _twoDaysAgo

    @RequiresApi(Build.VERSION_CODES.O)
    fun requestApod() {

        _loading.value = true

        viewModelScope.launch {
            try {

                val today = LocalDate.now().toString()
                val oneDayAgo = LocalDate.now().minusDays(1).toString()
                val twoDaysAgo = LocalDate.now().minusDays(2).toString()

                val todayResponse = repository.Apod(today)
                _today.emit(todayResponse)

                val oneDayAgoResponse = repository.Apod(oneDayAgo)
                _oneDayAgo.emit(oneDayAgoResponse)

                val twoDaysAgoyResponse = repository.Apod(twoDaysAgo)
                _twoDaysAgo.emit(twoDaysAgoyResponse)


            } catch (exc: IOException) {
                _error.emit("Error loading APOD API ")
            }

            _loading.emit(false)
        }
    }

    class NasaApodViewModelFactory(private val repository: NasaApodRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NasaApodViewModel(repository) as T

    }

}





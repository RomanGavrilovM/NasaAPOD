package com.example.nasaapod.ui.earth

import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.api.epic.EpicResponseDTO
import com.example.nasaapod.domain.EpicRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException


class EpicViewModel(val repository: EpicRepository) : ViewModel() {


    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    val _epicList: MutableSharedFlow<List<EpicResponseDTO?>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val epicList: Flow<List<EpicResponseDTO?>> = _epicList


    fun requestEpic() {
        _loading.value = true
        viewModelScope.launch() {
            try {
                val epic = repository.Epic()
                _epicList.emit(epic)


            } catch (exc: IOException) {
                _error.emit("Error loading NASA EPIC API")
            }

            _loading.emit(false)
        }

    }

    class EpicViewModelFactory(private val repository: EpicRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            EpicViewModel(repository) as T
    }

}




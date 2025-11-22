package com.mopo.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mopo.sample.data.DataItem
import com.mopo.sample.data.OfflineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: OfflineRepository) : ViewModel() {
    private val _uiState = MutableStateFlow("Loading...")
    val uiState: StateFlow<String> = _uiState

    fun saveItem(item: DataItem) {
        viewModelScope.launch {
            repo.insertItem(item)
            _uiState.value = "Saved locally"
            repo.enqueueSync()
        }
    }

    suspend fun syncNow() {
        repo.performImmediateSync()
    }
}

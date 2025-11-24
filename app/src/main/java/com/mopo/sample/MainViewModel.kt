import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mopo.sample.data.DataItem
import com.mopo.sample.data.OfflineRepository
import com.mopo.sample.network.ConnectivityObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val repo: OfflineRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow("Loading...")
    val uiState: StateFlow<String> = _uiState

    private val connectivityObserver = ConnectivityObserver(application)

    private val _isOnline = MutableStateFlow(false)
    val isOnline: StateFlow<Boolean> = _isOnline

    init {
        viewModelScope.launch {
            connectivityObserver.connectivityStatus.collect { status ->
                _isOnline.value = (status == ConnectivityObserver.Status.Available)
            }
        }
    }

    fun saveItem(item: DataItem) {
        viewModelScope.launch {
            repo.insertItem(item)
            _uiState.value = "Saved locally"
            repo.enqueueSync()
        }
    }

    fun uploadItem(title: String, description: String) {
        val item = DataItem(
            id = 0,
            title = title,
            description = description,
            synced = false
        )
        saveItem(item)
        viewModelScope.launch {
            val result = repo.uploadToApi(item)
            result.fold(
                onSuccess = { println("Uploaded: $it")
                            _uiState.value = "Success"},
                onFailure = { println("Upload failed: ${it.message}")
                            _uiState.value = "${it.message}"}
            )
        }
    }

    suspend fun syncNow() {
        repo.performImmediateSync()
    }
}

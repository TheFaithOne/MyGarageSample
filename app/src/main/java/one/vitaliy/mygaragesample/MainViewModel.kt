package one.vitaliy.mygaragesample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import one.vitaliy.mygaragesample.api.CarDomain
import one.vitaliy.mygaragesample.api.MyGarageApiClient
import one.vitaliy.mygaragesample.api.toDomain
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val apiClient: MyGarageApiClient
) : ViewModel() {

    private val isRefreshing = MutableStateFlow(false)
    private val carList = MutableStateFlow<List<CarDomain>>(emptyList())
    private val error = MutableStateFlow<Throwable?>(null)
    val uiState = combine(carList, isRefreshing, error) { cars, refreshing, error ->
        if (error == null) {
            MainUiState.Content(cars, refreshing)
        } else {
            MainUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = MainUiState.Loading,
    )

    init {
        getCarsList()
    }

    fun onRefresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            // Simulate some loading time
            delay(2.seconds)
            getCarsList()
            isRefreshing.value = false
        }
    }

    private fun getCarsList() = viewModelScope.launch {
        runCatching {
            apiClient.getMyGarageInfo().map { it.toDomain() }
        }.onSuccess {
            carList.value = it
        }.onFailure {
            error.value = it
        }
    }
}

internal sealed interface MainUiState {
    object Loading : MainUiState
    data class Content(val carList: List<CarDomain>, val isRefreshing: Boolean) : MainUiState
    data object Error : MainUiState
}
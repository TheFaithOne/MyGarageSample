package one.vitaliy.mygaragesample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.mygaragesample.api.CarDomain
import one.vitaliy.mygaragesample.api.MyGarageApiClient
import one.vitaliy.mygaragesample.api.toDomain
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val apiClient: MyGarageApiClient
) : ViewModel() {
    val carList = MutableStateFlow<List<CarDomain>>(emptyList())

    init {
        viewModelScope.launch {
            carList.value = apiClient.getMyGarageInfo().map { it.toDomain() }
        }
    }
}
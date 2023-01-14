package io.studio.thecrypto.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.studio.thecrypto.domain.RateRepository
import io.studio.thecrypto.presentation.UiState.Error
import io.studio.thecrypto.presentation.UiState.Loading
import io.studio.thecrypto.presentation.UiState.Success
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val rateRepository: RateRepository
) : ViewModel() {

    private var currentJob: Job? = null
    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchRate()
    }

    fun fetchRate() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _uiState.value = Loading
            delay(3000)
            val rate = rateRepository.fetchRate()
            if (rate != null) {
                _uiState.value = Success(rate)
            } else {
                _uiState.value = Error
            }
        }
    }
}
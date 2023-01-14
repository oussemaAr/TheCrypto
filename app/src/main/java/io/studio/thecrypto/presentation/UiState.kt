package io.studio.thecrypto.presentation

import io.studio.thecrypto.model.RateModel

sealed class UiState {
    object Loading : UiState()
    object Error : UiState()
    class Success(val rate: RateModel) : UiState()
}
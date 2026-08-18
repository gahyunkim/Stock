package com.example.stock.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stock.core.model.UiScreen
import com.example.stock.core.network.FakeSduiRepository
import com.example.stock.core.network.SduiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val screen: UiScreen) : HomeUiState
}

class HomeViewModel(
    private val repository: SduiRepository = FakeSduiRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            _uiState.value = HomeUiState.Success(repository.fetchScreen(screenId = "home"))
        }
    }
}

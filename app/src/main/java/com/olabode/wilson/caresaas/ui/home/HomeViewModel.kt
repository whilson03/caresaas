package com.olabode.wilson.caresaas.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olabode.wilson.caresaas.repository.MainRepository
import com.olabode.wilson.caresaas.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _homeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    init {
        fetchSignedInUserDetails()
        fetchTasks(shortCode = "FKRC", careHomeId = 2, assignee = 15)
    }

    private fun fetchTasks(shortCode: String, careHomeId: Int, assignee: Int) {
        _homeScreenState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            mainRepository.fetchTasks(shortCode, careHomeId, assignee).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenState.update {
                            it.copy(isLoading = false, tasks = result.data)
                        }
                    }

                    is Result.Error -> {
                        _homeScreenState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun fetchSignedInUserDetails() {
        viewModelScope.launch {
            mainRepository.fetchSignedInUser().collect { name ->
                _homeScreenState.update { it.copy(signedUserName = name) }
            }
        }
    }
}
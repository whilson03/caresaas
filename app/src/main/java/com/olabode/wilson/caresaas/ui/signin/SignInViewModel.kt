package com.olabode.wilson.caresaas.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olabode.wilson.caresaas.repository.MainRepository
import com.olabode.wilson.caresaas.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _signInState: MutableLiveData<SignInState> = MutableLiveData(SignInState())
    val signInState: LiveData<SignInState> = _signInState

    fun signIn(userName: String, password: String) {
        _signInState.value = _signInState.value?.copy(isLoading = true)

        viewModelScope.launch {
            mainRepository.signIn(userName, password).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _signInState.value = _signInState.value?.copy(
                            isLoading = false,
                            isSignInSuccessful = true
                        )
                    }

                    is Result.Error -> {
                        _signInState.value = _signInState.value?.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun onErrorShown() {
        _signInState.value = _signInState.value?.copy(errorMessage = null)
    }
}
package com.olabode.wilson.caresaas.ui.signin

data class SignInState(
    val isLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val errorMessage: String? = null
)

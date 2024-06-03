package com.olabode.wilson.caresaas.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.olabode.wilson.caresaas.R
import com.olabode.wilson.caresaas.databinding.FragmentSignInBinding
import com.olabode.wilson.caresaas.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            handleSignInClicked()
        }

        viewModel.signInState.observe(viewLifecycleOwner) { state ->
            state?.let {
                binding.progressBar.isVisible = state.isLoading
                binding.signInButton.isEnabled = !state.isLoading
                if (state.isSignInSuccessful) navigateToHomeFragment()
                state.errorMessage?.let { handleErrorMessage(it) }
            }
        }
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_navigation_home)
    }

    private fun handleSignInClicked() {
        val userName = binding.userNameTextField.text.toString()
        val password = binding.passwordTextField.text.toString()

        if (userName.isEmpty()) {
            context?.showToast(R.string.blank_username_prompt)
            return
        }

        if (password.isEmpty()) {
            context?.showToast(R.string.blank_password_prompt)
            return
        }
        viewModel.signIn(userName, password)
    }

    private fun handleErrorMessage(message: String) {
        context?.showToast(message)
        viewModel.onErrorShown()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
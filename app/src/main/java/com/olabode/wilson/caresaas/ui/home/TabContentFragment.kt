package com.olabode.wilson.caresaas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.asLiveData
import com.olabode.wilson.caresaas.R
import com.olabode.wilson.caresaas.databinding.FragmentTabContentBinding
import com.olabode.wilson.caresaas.ui.adapter.TasksAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabContentFragment : Fragment() {

    private var _binding: FragmentTabContentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TasksAdapter()
        binding.taskListRecyclerView.adapter = adapter

        viewModel.homeScreenState.asLiveData().observe(viewLifecycleOwner) { state ->
            state?.let {
                binding.progressBar.isVisible = it.isLoading
                adapter.submitList(it.tasks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
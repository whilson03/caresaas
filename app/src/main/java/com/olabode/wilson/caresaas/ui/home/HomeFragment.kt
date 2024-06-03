package com.olabode.wilson.caresaas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.asLiveData
import com.google.android.material.tabs.TabLayoutMediator
import com.olabode.wilson.caresaas.R
import com.olabode.wilson.caresaas.databinding.FragmentHomeBinding
import com.olabode.wilson.caresaas.ui.adapter.HomeViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

        viewModel.homeScreenState.asLiveData().observe(viewLifecycleOwner) { state ->
            state?.let {
                binding.greeting.text = getString(R.string.greeting, it.signedUserName)
            }
        }

        binding.clockInButton.setOnClickListener {
            binding.clockInButton.isVisible = false
            binding.moreOptions.isVisible = true
            binding.clockedIn.isVisible = true
            binding.greetingSubtitle.text = getString(R.string.clocked_in_subtitle)
        }

        binding.clockOutButton.setOnClickListener {
            binding.clockInButton.isVisible = true
            binding.moreOptions.isVisible = false
            binding.clockedIn.isVisible = false
            binding.greetingSubtitle.text = getString(R.string.clock_in_to_begin_your_task)
        }
    }

    private fun setupViewPager() {
        val fragments = List(2) { TabContentFragment() }
        val adapter = HomeViewPagerAdapter(fragments, requireActivity())
        val tabs = listOf(R.string.medication, R.string.activities)
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = getString(tabs[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
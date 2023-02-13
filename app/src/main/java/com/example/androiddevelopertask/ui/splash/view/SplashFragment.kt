package com.example.androiddevelopertask.ui.splash.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androiddevelopertask.databinding.FragmentSplashBinding
import com.example.androiddevelopertask.ui.splash.SplashViewModel
import com.example.androiddevelopertask.util.Constants
import com.example.androiddevelopertask.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigationEvent()

        lifecycleScope.launch {
            delay(1000)
            viewModel.checkForToken()
        }
    }

    private fun observeNavigationEvent() {
        viewModel.navigateToLoginEvent.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        })

        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
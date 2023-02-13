package com.example.androiddevelopertask.ui.home.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddevelopertask.R
import com.example.androiddevelopertask.databinding.FragmentHomeBinding
import com.example.androiddevelopertask.ui.home.HomeViewModel
import com.example.androiddevelopertask.util.Constants
import com.example.androiddevelopertask.util.EventObserver
import com.example.androiddevelopertask.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val mAdapter by lazy { ProductsAdapter(ProductsAdapter.ProductClickListener { product ->
        viewModel.navigateToDetails(product)
    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupMenu()
        setupRecyclerView()
        viewModel.getProducts()
        observeProducts()
        observeNavigationEvent()
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvProducts.adapter = mAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeProducts() {
        showShimmerEffect()
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.submitList(it.products) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    binding.apply {
                        tvError.visibility = View.VISIBLE
                        rvProducts.visibility = View.GONE
                    }
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun observeNavigationEvent() {
        viewModel.navigateToDetailsEvent.observe(viewLifecycleOwner, EventObserver{product ->
            product?.let {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it))
            }
        })
        viewModel.navigateToLoginEvent.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        })
    }

    private fun setupMenu() {
        setHasOptionsMenu(true)
        binding.tb.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_Logout -> {
                    viewModel.deleteToken(Constants.shared_Preference_Key)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }
    }


    private fun showShimmerEffect() {
        binding.shimmerViewContainer.startShimmer()
    }

    private fun hideShimmerEffect() {
        binding.apply {
            shimmerViewContainer.stopShimmer()
            shimmerViewContainer.hideShimmer()
            shimmerViewContainer.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
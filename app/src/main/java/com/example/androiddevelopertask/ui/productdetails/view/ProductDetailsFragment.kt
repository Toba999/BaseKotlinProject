package com.example.androiddevelopertask.ui.productdetails.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.androiddevelopertask.R
import com.example.androiddevelopertask.databinding.FragmentProductDetailsBinding
import com.example.androiddevelopertask.model.Product
import me.relex.circleindicator.CircleIndicator3

class ProductDetailsFragment : DialogFragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        dialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            it.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product = ProductDetailsFragmentArgs.fromBundle(requireArguments()).product

        binding.apply {
            vpImages.adapter = ViewPagesAdapter(product.images)
            vpImages.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            gradientOverlay.setViewPager(vpImages)

            tvTitle.text = product.title
            tvPrice.text = "Price: ${product.price}$"
            tvDesc.text = product.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
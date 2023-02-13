package com.example.androiddevelopertask.ui.login.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androiddevelopertask.R
import com.example.androiddevelopertask.databinding.FragmentLoginBinding
import com.example.androiddevelopertask.ui.login.LoginViewModel
import com.example.androiddevelopertask.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigationEvent()
        observeSignInButton()
        observeRegisterButton()
        observeErrorSnackBar()
    }

    private fun observeNavigationEvent() {
        viewModel.navigateToRegisterEvent.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        })

        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        })
    }

    private fun observeRegisterButton() {
        binding.tvRegister.setOnClickListener {
            viewModel.navigateToRegister()
        }
    }

    private fun observeSignInButton() {
        binding.btnSign.setOnClickListener {
            if (isValid()) {
                viewModel.validateUserInputRegex(
                    binding.etPhone.text.toString(),
                    binding.etPass.text.toString()
                )
            }
        }
    }

    private fun observeErrorSnackBar() {
        viewModel.showErrorSnackBarEvent.observe(viewLifecycleOwner, EventObserver{
            showErrorSnackBar(it)
        })
    }

    private fun isValid(): Boolean {
        binding.apply {
            return if (etPhone.isEmpty())
                showErrorToast("Please Enter the phone number")
            else if (etPass.isEmpty())
                showErrorToast("Please Enter the password")
            else
                true
        }
    }

    private fun EditText.isEmpty(): Boolean {
        return this.text.toString().isEmpty()
    }

    private fun showErrorToast(message:String) :Boolean{
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
        return false
    }

    private fun showErrorSnackBar(message: String){
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorSnackBarError))
        snackBar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
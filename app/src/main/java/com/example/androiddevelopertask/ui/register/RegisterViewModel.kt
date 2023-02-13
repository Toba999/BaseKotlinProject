package com.example.androiddevelopertask.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevelopertask.repo.ProductsRepo
import com.example.androiddevelopertask.util.Constants
import com.example.androiddevelopertask.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val productsRepo: ProductsRepo): ViewModel() {
    private val _navigateToHomeEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigateToHomeEvent: LiveData<Event<Boolean>> = _navigateToHomeEvent

    private val _showErrorSnackBarEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val showErrorSnackBarEvent: LiveData<Event<String>> = _showErrorSnackBarEvent

    private fun navigateToHome() {
        _navigateToHomeEvent.value = Event(true)
    }

    fun validateUserInputRegex(email: String, phone: String, password: String) {
        Log.d("RegisterView",email)
        val emailRegex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        val phoneRegexStr = Regex("^[-+]?\\d*\$")
        val passwordPattern = Regex("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=_])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$")

        if (!email.matches(emailRegex) ) {
            _showErrorSnackBarEvent.value = Event("Please enter valid email")
        } else if(phone.length != 11 || !phone.matches(phoneRegexStr) ) {
            _showErrorSnackBarEvent.value = Event("Please enter valid phone number")
        } else if (password.length < 8 || !password.matches(passwordPattern)) {
            _showErrorSnackBarEvent.value = Event("Please enter valid password")
        } else {
            productsRepo.addData(Constants.shared_Preference_Key, phone)
            navigateToHome()
        }
    }
}
package com.example.androiddevelopertask.ui.login

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
class LoginViewModel @Inject constructor(private val productsRepo: ProductsRepo) : ViewModel() {
    private val _navigateToRegisterEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigateToRegisterEvent: LiveData<Event<Boolean>> = _navigateToRegisterEvent

    private val _navigateToHomeEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigateToHomeEvent: LiveData<Event<Boolean>> = _navigateToHomeEvent

    private val _showErrorSnackBarEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val showErrorSnackBarEvent: LiveData<Event<String>> = _showErrorSnackBarEvent

    fun navigateToRegister() {
        _navigateToRegisterEvent.value = Event(true)
    }

    private fun navigateToHome() {
        _navigateToHomeEvent.value = Event(true)
    }

    fun validateUserInputRegex(phone: String, password: String) {
        val phoneRegexStr = Regex("^[-+]?\\d*\$")
        val passwordPattern = Regex("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$")

        if(phone.length != 11 || !phone.matches(phoneRegexStr) ) {
            _showErrorSnackBarEvent.value = Event("Please enter valid phone number")
        } else if (password.length < 8 || !password.matches(passwordPattern)) {
            _showErrorSnackBarEvent.value = Event("Please enter valid password")
        } else {
            productsRepo.addData(Constants.shared_Preference_Key, phone)
            navigateToHome()
        }
    }
}
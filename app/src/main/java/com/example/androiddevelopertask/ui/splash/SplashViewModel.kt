package com.example.androiddevelopertask.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevelopertask.repo.ProductsRepo
import com.example.androiddevelopertask.util.Constants
import com.example.androiddevelopertask.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val productsRepo: ProductsRepo): ViewModel() {

    private val _navigateToLoginEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigateToLoginEvent: LiveData<Event<Boolean>> = _navigateToLoginEvent

    private val _navigateToHomeEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigateToHomeEvent: LiveData<Event<Boolean>> = _navigateToHomeEvent

    private fun navigateToLogin() {
        _navigateToLoginEvent.value = Event(true)
    }

    private fun navigateToHome() {
        _navigateToHomeEvent.value = Event(true)
    }

    fun checkForToken(){
        if ( productsRepo.getData(Constants.shared_Preference_Key,"EmptyToken") == "EmptyToken"){
            navigateToLogin()
        }else{
            navigateToHome()
        }
    }

}
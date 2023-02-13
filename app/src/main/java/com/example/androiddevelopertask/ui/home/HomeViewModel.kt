package com.example.androiddevelopertask.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevelopertask.model.Product
import com.example.androiddevelopertask.model.ProductResponse
import com.example.androiddevelopertask.repo.ProductsRepo
import com.example.androiddevelopertask.util.Constants
import com.example.androiddevelopertask.util.Event
import com.example.androiddevelopertask.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val productsRepo: ProductsRepo): ViewModel() {
    private val _products: MutableLiveData<NetworkResult<ProductResponse>> = MutableLiveData()
    val products: LiveData<NetworkResult<ProductResponse>> = _products

    private val _navigateToDetailsEvent: MutableLiveData<Event<Product?>> = MutableLiveData()
    val navigateToDetailsEvent: LiveData<Event<Product?>> = _navigateToDetailsEvent

    private val _navigateToLoginEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigateToLoginEvent: LiveData<Event<Boolean>> = _navigateToLoginEvent

    fun getProducts() {
        _products.value = NetworkResult.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = productsRepo.getData(Constants.shared_Preference_Key,"empty")
                _products.postValue(NetworkResult.Success(productsRepo.getProductsFromRemote(token)))
            } catch (e: Exception) {
                Log.e("MealsViewModel", e.message.toString())
                _products.postValue(NetworkResult.Error(e.message.toString()))
            }
        }
    }

    fun navigateToDetails(product: Product) {
        _navigateToDetailsEvent.value = Event(product)
    }

    private fun navigateToLogin() {
        _navigateToLoginEvent.value = Event(true)
    }

    fun deleteToken(key: String) {
        productsRepo.deleteData(key)
        navigateToLogin()
    }
}
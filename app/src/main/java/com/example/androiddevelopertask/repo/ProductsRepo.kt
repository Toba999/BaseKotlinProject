package com.example.androiddevelopertask.repo

import com.example.androiddevelopertask.local.HelperSharedPreferences
import com.example.androiddevelopertask.model.ProductResponse
import com.example.androiddevelopertask.remote.ApiService

class ProductsRepo(private val apiService: ApiService, private val sharedPreferences: HelperSharedPreferences) {

    suspend fun getProductsFromRemote(token : String): ProductResponse = apiService.getProducts((token))

    fun addData(key: String, value: String) {
        sharedPreferences.addData(key, value)
    }

    fun getData(key: String, defaultValue: String) =
        sharedPreferences.getData(key, defaultValue)

    fun deleteData(key: String) = sharedPreferences.deleteData(key)
}
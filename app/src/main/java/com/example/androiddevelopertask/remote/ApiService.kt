package com.example.androiddevelopertask.remote

import com.example.androiddevelopertask.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("/products")
    suspend fun getProducts(@Header("Authorization") authorization: String): ProductResponse
}
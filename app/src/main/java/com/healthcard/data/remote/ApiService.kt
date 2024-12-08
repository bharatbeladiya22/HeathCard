package com.healthcard.data.remote

import com.healthcard.domain.model.ProblemsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("https://run.mocky.io/v3/b0182e26-25b2-4ef1-8796-2665c3ba5437") // Replace with actual API endpoint
    suspend fun getMedicines(): Response<ProblemsResponse>
}
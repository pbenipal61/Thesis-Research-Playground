package com.example.thesisresearchplayground.data.api

import com.example.thesisresearchplayground.data.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
}
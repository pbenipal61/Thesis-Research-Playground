package com.example.thesisresearchplayground.data.repository

import com.example.thesisresearchplayground.data.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}
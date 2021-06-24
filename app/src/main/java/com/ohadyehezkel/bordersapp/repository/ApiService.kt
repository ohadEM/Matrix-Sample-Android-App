package com.ohadyehezkel.bordersapp.repository

import com.ohadyehezkel.bordersapp.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET("all")
    fun getCountries(): Single<List<Country>>
}
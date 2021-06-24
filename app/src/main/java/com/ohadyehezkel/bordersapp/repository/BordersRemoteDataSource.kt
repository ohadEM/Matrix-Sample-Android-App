package com.ohadyehezkel.bordersapp.repository

import com.ohadyehezkel.bordersapp.model.Country
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BordersRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun getCountries(): Single<List<Country>> = apiService.getCountries()
}
package com.ohadyehezkel.bordersapp.repository

import com.ohadyehezkel.bordersapp.model.Country
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BordersRepository @Inject constructor(
    private val localDataSource: BordersLocalDataSource,
    private val remoteDataSource: BordersRemoteDataSource
) {
    fun getCountries(): Single<List<Country>> {
        return localDataSource.getCountries()
            .onErrorResumeNext {
                remoteDataSource.getCountries().doOnSuccess {
                    localDataSource.storeCountries(it)
                }
            }
    }
}
package com.ohadyehezkel.bordersapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ohadyehezkel.bordersapp.model.Country
import com.ohadyehezkel.bordersapp.repository.BordersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TableViewModelImpl @Inject constructor(
    private val bordersRepository: BordersRepository
) : TableViewModel() {
    override fun getItems() {
        bordersRepository
            .getCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                 itemsLiveData.postValue(it)
            }, {
            })
    }
}
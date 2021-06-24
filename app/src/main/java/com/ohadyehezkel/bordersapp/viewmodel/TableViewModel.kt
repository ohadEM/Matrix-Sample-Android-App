package com.ohadyehezkel.bordersapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ohadyehezkel.bordersapp.model.Country

abstract class TableViewModel : ViewModel() {
    val itemsLiveData = MutableLiveData<List<Country>>()
    abstract fun getItems()
}
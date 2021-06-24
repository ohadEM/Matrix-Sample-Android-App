package com.ohadyehezkel.bordersapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ohadyehezkel.bordersapp.model.Country
import com.ohadyehezkel.bordersapp.model.CountryView

abstract class ViewBordersViewModel : ViewModel() {
    val data = MutableLiveData<CountryView>()
    abstract fun getCountryDetails(countryCioc: String)
}
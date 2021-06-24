package com.ohadyehezkel.bordersapp.viewmodel

import com.ohadyehezkel.bordersapp.model.CountryView
import com.ohadyehezkel.bordersapp.repository.BordersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ViewBordersViewModelImpl @Inject constructor(
    private val bordersRepository: BordersRepository
) : ViewBordersViewModel() {
    override fun getCountryDetails(countryCioc: String) {
        bordersRepository
            .getCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val country = it.find { it.cioc == countryCioc } ?: return@subscribe
                val borders = it.filter { country.borders.contains(it.cioc) }
                data.postValue(
                    CountryView(
                        cioc = country.cioc,
                        nativeName = country.nativeName,
                        name = country.name,
                        area = country.area,
                        borders = borders
                    )
                )
            }, {
            })
    }
}
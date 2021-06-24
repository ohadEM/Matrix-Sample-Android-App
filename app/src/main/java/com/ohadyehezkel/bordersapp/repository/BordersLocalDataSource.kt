package com.ohadyehezkel.bordersapp.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ohadyehezkel.bordersapp.common.Constants
import com.ohadyehezkel.bordersapp.common.Constants.Companion.PREFS_FILE
import com.ohadyehezkel.bordersapp.model.Country
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BordersLocalDataSource @Inject constructor(
    @ApplicationContext val context: Context
) {
    init {
        resetCountries()
    }

    private fun resetCountries() {
        val sharedPref = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            remove(Constants.COUNTRIES)
            apply()
        }
    }

    fun getCountries(): Single<List<Country>> {
        val prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        return prefs.getString(Constants.COUNTRIES, null)?.let {
            val itemType = object : TypeToken<List<Country>>() {}.type
            return Single.just(Gson().fromJson(it, itemType))
        } ?: return Single.error(Throwable("Entry does not exist"))
    }

    fun storeCountries(list: List<Country>) {
        val sharedPref = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(Constants.COUNTRIES, Gson().toJson(list))
            apply()
        }
    }
}
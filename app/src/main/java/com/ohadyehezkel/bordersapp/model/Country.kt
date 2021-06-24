package com.ohadyehezkel.bordersapp.model

data class Country(val cioc: String, val nativeName: String, val name: String, val area: Double, val borders: List<String>): ListItem()
package com.ohadyehezkel.bordersapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ohadyehezkel.bordersapp.common.Constants
import com.ohadyehezkel.bordersapp.databinding.ActivityViewBordersBinding
import com.ohadyehezkel.bordersapp.viewmodel.ViewBordersViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewBordersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBordersBinding
    private val viewModel: ViewBordersViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBordersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cioc = intent?.getStringExtra(Constants.COUNTRY_CIOC) ?: return
        viewModel.getCountryDetails(cioc)
        viewModel.data.observe(this, {
            binding.countryTitle.text = it.name
            binding.countrySubTitle.text = it.nativeName
            binding.area.text = it.area.toString()
            binding.borders.text = it.borders.joinToString(
                separator = System.lineSeparator(),
                transform = { "${it.name} ${it.nativeName}" })
        })
    }
}
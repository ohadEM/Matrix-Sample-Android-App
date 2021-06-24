package com.ohadyehezkel.bordersapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ohadyehezkel.bordersapp.common.Constants
import com.ohadyehezkel.bordersapp.databinding.ActivityTableBinding
import com.ohadyehezkel.bordersapp.viewmodel.TableViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTableBinding
    private val viewModel: TableViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = TableAdapter()
        adapter.itemClicked = {
            val intent = Intent(this, ViewBordersActivity::class.java)
            intent.putExtra(Constants.COUNTRY_CIOC, it.cioc)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        viewModel.getItems()
        viewModel.itemsLiveData.observe(this, Observer {
            adapter.addItems(it)
        })
    }
}
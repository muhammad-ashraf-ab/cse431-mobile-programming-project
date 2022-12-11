package com.college.cse431_mobile_programming_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL)

        setSupportActionBar(binding.mainToolbar)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)

//        val searchView = menu?.findItem(R.id.search_bar)?.actionView as SearchView
//        searchView.setIconifiedByDefault(false)
//        searchView.queryHint = "Search restaurants..."
//        searchView.maxWidth = Int.MAX_VALUE

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            if (!this.findNavController(binding.navHostFragment.id).popBackStack())
                finish()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    fun updateToolbar(title: String) {
        binding.mainToolbar.title = title
    }
}
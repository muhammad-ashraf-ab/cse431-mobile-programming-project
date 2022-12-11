package com.college.cse431_mobile_programming_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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

        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNav.setupWithNavController(navController.navController)

        // TODO: Add image as profile icon
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

    fun configureBars(title: String, toolbarUpEnabled: Boolean, navVisibility: Int, toolbarVisibility: Boolean = true) {
        binding.mainToolbar.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(toolbarUpEnabled)
        binding.bottomNav.visibility = navVisibility

        if (!toolbarVisibility) supportActionBar!!.hide() else supportActionBar!!.show()
    }
}
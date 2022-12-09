package com.college.cse431_mobile_programming_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.college.cse431_mobile_programming_project.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL)
    }
}
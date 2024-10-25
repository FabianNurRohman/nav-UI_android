package com.dicoding.fundamentalsub1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.fundamentalsub1.R
import com.dicoding.fundamentalsub1.databinding.ActivityMainBinding
import com.dicoding.fundamentalsub1.ui.upcoming.ActiveEventsFragment
import com.dicoding.fundamentalsub1.ui.finished.FinishedEventsFragment
import com.dicoding.fundamentalsub1.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navigation_active_events -> {
                    replaceFragment(ActiveEventsFragment())
                    true
                }
                R.id.navigation_finished_events -> {
                    replaceFragment(FinishedEventsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

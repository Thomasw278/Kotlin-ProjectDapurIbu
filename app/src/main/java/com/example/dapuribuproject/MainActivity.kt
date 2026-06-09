package com.example.dapuribuproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dapuribuproject.Fragment.Home_Fragment
import com.example.dapuribuproject.Fragment.Profile_Fragment
import com.example.dapuribuproject.Fragment.Katalog_Fragment
import com.example.dapuribuproject.Fragment.ChatFragment
import com.example.dapuribuproject.Fragment.Home_Admin_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Ambil Intent dari Login Activity
        val isAdmin = intent.getBooleanExtra("isAdmin", false)
        val username = intent.getStringExtra("username") ?: "User"

        // Default fragment saat aplikasi dibuka
        if (savedInstanceState == null && isAdmin) {
            replaceFragment(Home_Admin_Fragment())
        } else {
            replaceFragment(Home_Fragment())
        }

        // Switch antar fragment
        val bottonNavigationView = findViewById<BottomNavigationView>(R.id.bottomnNavigationView)
        bottonNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    if(isAdmin) {
                        replaceFragment(Home_Admin_Fragment())
                    } else {
                        replaceFragment(Home_Fragment())
                    }
                    true
                }
                R.id.profile -> {
                    replaceFragment(Profile_Fragment())
                    true
                }
                R.id.settings -> {
                    replaceFragment(Katalog_Fragment())
                    true
                }
                R.id.cstalk -> {
                    replaceFragment(ChatFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragment)
            .commit()
    }
}

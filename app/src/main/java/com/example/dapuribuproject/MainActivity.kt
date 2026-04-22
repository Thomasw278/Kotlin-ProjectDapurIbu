package com.example.dapuribuproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dapuribuproject.fragment.Home_Fragment
import com.example.dapuribuproject.fragment.Profile_Fragment
import com.example.dapuribuproject.fragment.Katalog_Fragment
import com.example.dapuribuproject.fragment.ChatFragment
import com.example.dapuribuproject.fragment.Home_Admin_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Define User
    val isAdmin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Default fragment saat aplikasi dibuka
        if (savedInstanceState == null && isAdmin) {
            replaceFragment(Home_Admin_Fragment())
        } else {
            replaceFragment(Home_Fragment())
        }

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

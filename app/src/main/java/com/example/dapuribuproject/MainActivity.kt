package com.example.dapuribuproject


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dapuribuproject.R
import com.example.dapuribuproject.fragment.Admin
import com.example.dapuribuproject.fragment.Home
import com.example.dapuribuproject.fragment.Profile
import com.example.dapuribuproject.fragment.Katalog
import com.example.dapuribuproject.fragment.Customer
import com.example.dapuribuproject.fragment.Home_admin
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        //by default yang dijalankan pertama kali
        if (savedInstanceState == null) {
            replaceFragment(Home())
        }

        val bottonNavigationView = findViewById<BottomNavigationView>(R.id.bottomnNavigationView)

        bottonNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(Home_admin())
                    true
                }

                R.id.profile -> {
                    replaceFragment(Profile())
                    true
                }

                R.id.settings -> {
                    replaceFragment(Katalog())
                    true
                }

                R.id.cstalk -> {
                    replaceFragment(Admin())
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
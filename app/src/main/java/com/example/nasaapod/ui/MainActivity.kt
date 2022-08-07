package com.example.nasaapod.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nasaapod.R
import com.example.nasaapod.databinding.ActivityMainBinding
import com.example.nasaapod.ui.apod.NasaApodFragment
import com.example.nasaapod.ui.earth.EarthFargment
import com.example.nasaapod.ui.mars.MarsFragment
import com.example.nasaapod.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val KEY_THEME = "KEY_THEME"
    }
    var showSplash = true

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            showSplash
        }


        val theme = getPreferences(Context.MODE_PRIVATE).getInt(KEY_THEME, -1)
        setTheme(theme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            showSplash = false
        }, 2000)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, NasaApodFragment())
                .commit()

        }

       val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_nav_bar)

        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.apod_menu -> NasaApodFragment()
                R.id.earth_menu -> EarthFargment()
                R.id.mars_menu -> MarsFragment()
                R.id.settings_menu -> SettingsFragment()
                else -> null
            }?.also { fragment ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment,"EPIC")
                    .commit()
            }
            true
        }

    }

    fun setSplashScreen () {
        showSplash = false
    }

}





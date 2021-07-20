package com.aero51.moviedatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aero51.moviedatabase.ui.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

        }
    }

    companion object {
        const val KEY_PREF_EXAMPLE_SWITCH = "example_switch"
    }
}
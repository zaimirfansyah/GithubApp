package com.example.githubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubapp.R
import android.content.Context
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.tema.TemaManager
import com.example.githubapp.viewmodel.TemaViewModelFactory
import com.example.githubapp.viewmodel.TemaViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TemaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tema)

        val switchTheme: SwitchMaterial = findViewById(R.id.ubah_tema)
        val pref = TemaManager.getInstance(dataStore)

        val temaViewModel = ViewModelProvider(this, TemaViewModelFactory(pref))[TemaViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        temaViewModel.getThemeSettings().observe(this) { isLightModeActive: Boolean ->
            if (isLightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            temaViewModel.saveThemeSetting(isChecked)
        }
    }
}
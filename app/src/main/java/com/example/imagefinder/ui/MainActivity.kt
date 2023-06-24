package com.example.imagefinder.ui

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.imagefinder.R
import com.example.imagefinder.ui.fragments.BluetoothFragment
import com.example.imagefinder.ui.fragments.HomeFragment
import com.example.imagefinder.ui.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var blueToothAdapter: BluetoothAdapter

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    // Switch to Home Fragment
                    switchFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_search -> {
                    // Switch to Search Fragment
                    switchFragment(BluetoothFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_profile -> {
                    // Switch to Profile Fragment
                    switchFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ImageFinder_);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        grantBluetoothPermission()

        bottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // Set the default fragment when the activity is created
        switchFragment(HomeFragment())
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    private fun grantBluetoothPermission() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_DENIED
        ) {
            if (Build.VERSION.SDK_INT >= 31) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    100
                )
                return
            }
            val blueToothManager =
                this@MainActivity?.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
            blueToothAdapter = if (Build.VERSION.SDK_INT >= 31) {
                blueToothManager.adapter
            } else {
                BluetoothAdapter.getDefaultAdapter()
            }

            if (!blueToothAdapter.isEnabled) {
                blueToothAdapter.enable()
                Toast.makeText(this@MainActivity, "Bluetooth is ON", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
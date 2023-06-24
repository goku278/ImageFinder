package com.example.imagefinder.ui.fragments

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.imagefinder.R

class BluetoothFragment : Fragment() {


    private val bluetoothPermissionRequestCode = 1

    private lateinit var blueToothAdapter: BluetoothAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        grantBluetoothPermission()

        return inflater.inflate(R.layout.fragment_bluetooth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        grantBluetoothPermission()

        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(intent, 1)

    }


    private fun grantBluetoothPermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_DENIED
        ) {
            if (Build.VERSION.SDK_INT >= 31) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    100
                )
                return
            }
            val blueToothManager = context?.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
            blueToothAdapter = if (Build.VERSION.SDK_INT >= 31) {
                blueToothManager.adapter
            } else {
                BluetoothAdapter.getDefaultAdapter()
            }

            if (!blueToothAdapter.isEnabled) {
                blueToothAdapter.enable()
                Toast.makeText(context!!, "Bluetooth is ON", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
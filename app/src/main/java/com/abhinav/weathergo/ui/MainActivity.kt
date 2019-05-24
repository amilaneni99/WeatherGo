package com.abhinav.weathergo.ui

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.abhinav.weathergo.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.jar.Manifest

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val fusedLocationProviderClient: FusedLocationProviderClient by instance()
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController)

        requestLocationPermissions()

        if (hasLocationPermission()){
            bindLocationManager()
        }else{
            requestLocationPermissions()
        }

    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                bindLocationManager()
            else
                Toast.makeText(this,"Please set Location manually in Settings", Toast.LENGTH_LONG).show()
        }
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient,
            locationCallback
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}

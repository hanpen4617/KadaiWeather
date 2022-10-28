package com.example.kadaiweather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kadaiweather.databinding.ActivityMainBinding
import com.google.android.gms.location.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var long = 0.0
    private var lat = 0.0
    private lateinit var resultText: String



    //権限許可launch
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        //パーミッションの許可が出ているならば
        if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) &&
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
        ) {
            //現在地取得開始
            locationStart()
        } else {
            //トースト表示
            Toast.makeText(this, "現在地を取得できません", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //パーミッションが許可されているならば//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            //現在地取得開始
            locationStart()
        } else {
            //ランチャー起動
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    }

    fun locationStart() {
        lateinit var fusedLocationClient: FusedLocationProviderClient
        lateinit var locationCallback: LocationCallback
        lateinit var locationRequest: LocationRequest

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,10000).build()

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                for(location in p0.locations){
                    val text1 = binding.textView1
                    val text2 = binding.textView2
                    lat = location.latitude
                    long = location.longitude
                    text1.text = lat.toString()
                    text2.text = long.toString()
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                }
            }
        }

        //初期化
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //パーミッションが許可されているか//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        //現在地更新
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }
}



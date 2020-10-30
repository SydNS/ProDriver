@file:Suppress("DEPRECATION")

package com.example.prodriver.Views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.prodriver.R
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class CustomersMapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    private lateinit var mMap: GoogleMap
    var mlocationRequest: LocationRequest? = null
    var mgoogleApiClient: GoogleApiClient? = null
    var location: Location? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mAuth = FirebaseAuth.getInstance();

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
// 2
//            requestLocationPermissions()
            dialogBox("Grant App Access To Location")
            finish()

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {// for ActivityCompat#requestPermissions for more details.
            return
        }
        buildGoogleApiClient()
        mMap.isMyLocationEnabled = true
    }

    private fun buildGoogleApiClient() {
        mgoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mgoogleApiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {
        mlocationRequest = LocationRequest()
        mlocationRequest?.interval = 10000
        mlocationRequest?.fastestInterval = 10000
        mlocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mgoogleApiClient,
            mlocationRequest,
            this
        )
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(p0: Location?) {

        getCurrentLocation(p0!!)
        var userId = FirebaseAuth.getInstance().currentUser?.uid
        var dbRef = FirebaseDatabase.getInstance().getReference("Users").child("CustomersAvailable")
        var geoFire = GeoFire(dbRef)
        geoFire.setLocation(userId, GeoLocation(p0.latitude, p0.longitude))
    }

    override fun onStop() {
        super.onStop()
        var userId = FirebaseAuth.getInstance().currentUser?.uid
        var dbRef = FirebaseDatabase.getInstance().getReference("Users").child("DriversAvailable")
        var geoFire = GeoFire(dbRef)
        geoFire.removeLocation(userId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var userId = FirebaseAuth.getInstance().currentUser?.uid
        var dbRef = FirebaseDatabase.getInstance().getReference("Users").child("DriversAvailable")
        var geoFire = GeoFire(dbRef)
        geoFire.removeLocation(userId)
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
//            getCurrentLocation(location)
            val tempBundle = Bundle()
            onCreate(tempBundle)
        } else {
//            Log.e(error("sds"), "Location permission denied")}
            Toast.makeText(this, "Yo", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCurrentLocation(location: Location?) {
//        location=p0
        val currentUser: FirebaseUser? = mAuth?.currentUser
        var latLng = LatLng(location!!.latitude, location!!.longitude)
        var markerOptions= mMap.addMarker(
            MarkerOptions().position(latLng).title(currentUser?.email+" is here"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        markerOptions.isVisible=true
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0F))
        markerOptions.remove()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = mAuth?.currentUser
//        updateUI(currentUser)
        if(currentUser==null){
            val intent = Intent(
                this,
                DriverLoginRegisterActivity::class.java
            )
            startActivity(intent)
            finish()
        }

    }
    private fun dialogBox(messagePassedon: String) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Location is Key For this App")
        //set message for alert dialog
        builder.setMessage(messagePassedon)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
        }
        //performing cancel action
//        builder.setNeutralButton("Cancel"){ dialogInterface, which ->
//            Toast.makeText(
//                applicationContext,
//                "clicked cancel\n operation cancel",
//                Toast.LENGTH_LONG
//            ).show()
//        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}


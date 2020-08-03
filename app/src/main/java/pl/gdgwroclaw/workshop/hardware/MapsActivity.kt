package pl.gdgwroclaw.workshop.hardware

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initializeLocationServices()
    }

    private fun initializeLocationServices() {
        SettingsClient(this).checkLocationSettings(
            LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build()
        ).addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
            } catch (e: ResolvableApiException) {
                e.startResolutionForResult(this, 0) //TODO handle SendIntentException
            } catch (e: ApiException) {
                e.printStackTrace() //TODO handle on UI layer
            }
        }
    }

    private val locationRequest =
        LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(3000)

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

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val lastLocation = locationResult.lastLocation
                        val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                        mMap.addMarker(MarkerOptions().position(latLng))
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    }
                },
                null
            )
        }
    }
}
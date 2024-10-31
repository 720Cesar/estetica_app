package com.example.estetica

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.estetica.databinding.ActivityMapaGpsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private lateinit var binding: ActivityMapaGpsBinding
lateinit var mMap: GoogleMap
lateinit var fusedLocation: FusedLocationProviderClient

class mapaGps : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaGpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)

        //Permite verificar si el usuario dió el permiso del uso de su localización o no
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            //En caso de dar permiso, se obtienen los valores del GPS del celular para marcarlo en Google Maps
            ultimaLocalizacion()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        //Permite usar Google Maps
        mMap = googleMap

        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        //Se verifica si se da el permiso de usar la localización
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        } else {
            mMap.isMyLocationEnabled = true
            centerMapToCurrentLocation() //Añade un botón que permite centrar la cámara del mapa
            // Si tenemos permiso, obtenemos la ubicación actual y mostramos el marcador en el mapa
            fusedLocation.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    //Si la localización no es nula, entonces se muestra la localización actual
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(currentLocation).title("Mi ubicación"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                }
            }
        }
    }

    //La función permite centrar la cámara del mapa a la localicación actual
    private fun centerMapToCurrentLocation() {
        //Se añaden los verifica si se tienen los permisos
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
        //En caso de tener el permiso, entonces se establece una animación de cámara que muestra la localización actual
        fusedLocation.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLocation = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
            }
        }
    }

    //Función que permite tener la localización del celular
    private fun ultimaLocalizacion() {
        //Se verifica si se tienen los permisos del GPS
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

        fusedLocation.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                //Se guardan los valores de la localización actual para mostrar las coordenadas en el mapa
                val latitudActual = location.latitude
                val longitudActual = location.longitude
                val coordenadas1 = LatLng(latitudActual, longitudActual)

                //Se añaden las coordenadas de la sucursal para mostrarlas en el mapa
                val latitudP2 = 19.4370817
                val longitudP2 = -99.059107
                val coordenadas2 = LatLng(latitudP2, longitudP2)

                //Se añaden marcadores para identificar la localiación actual y el de la sucursal en el mapa
                mMap.addMarker(MarkerOptions().position(coordenadas1).title("MI POSICIÓN"))
                mMap.addMarker(MarkerOptions().position(coordenadas2).title("SUCURSAL"))

            }


        }
    }
}
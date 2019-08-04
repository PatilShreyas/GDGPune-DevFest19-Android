package com.gdg.pune.devfest19.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.util.Log
import com.gdg.pune.devfest19.R



class MapViewFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mapview, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        mMap = googleMap!!
        Log.d("test","In the onmapready")
        // Add a marker in India and move the camera
        val sydney = LatLng(18.526110, 73.844131)
        Log.d("latlang", sydney.toString())
        mMap.addMarker(
            MarkerOptions().position(sydney)
                .title("ShivajiNagar, Pune")
                .visible(true)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,19.0f))
        mMap.uiSettings.isZoomControlsEnabled = true
    }

}

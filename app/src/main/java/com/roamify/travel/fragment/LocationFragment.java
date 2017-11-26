package com.roamify.travel.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roamify.travel.R;
import com.roamify.travel.map.MapWrapperLayout;
import com.roamify.travel.map.TouchableWrapper;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, TouchableWrapper.UpdateMapAfterUserInterection {
    private MapWrapperLayout mRelativeLayoutMapWrapperLayout;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    Marker mMarker;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        mRelativeLayoutMapWrapperLayout = (MapWrapperLayout) view.findViewById(R.id.detail_map_relative_layout);
        initializeMap();
        return view;
    }

    private void initializeMap() {
        try {
            if (mapFragment == null) {
                mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fr_detail_map);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mapFragment = SupportMapFragment.newInstance();
                fragmentTransaction.replace(R.id.fr_detail_map, mapFragment).commit();
            }
            mapFragment.getMapAsync(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onMapLoaded() {
        zoomAndAnimateMap(13.0f, 28.567766, 77.287655, "Chaddar Lake");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (this.googleMap == null) {
            this.googleMap = googleMap;
            this.googleMap.setOnMapLoadedCallback(LocationFragment.this);
            this.googleMap.getUiSettings().setMapToolbarEnabled(false);
            this.googleMap.getUiSettings().setCompassEnabled(false);
            this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    void zoomAndAnimateMap(float mapZooming, double lat, double lng, String locationName) {
        LatLng currentPosition = new LatLng(lat, lng);
        final MarkerOptions markerOptions = new MarkerOptions();
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        try {
            mMarker = googleMap.addMarker(markerOptions
                    .position(currentPosition)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .alpha(0.7f));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        googleMap.setPadding(30, 350, 50, 20);

        builder.include(markerOptions.getPosition());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, mapZooming);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onUpdateMapAfterUserInterection() {

    }
}

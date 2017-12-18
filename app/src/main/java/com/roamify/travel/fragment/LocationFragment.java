package com.roamify.travel.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.roamify.travel.activity.ActivityPackageDetails;
import com.roamify.travel.map.MapWrapperLayout;
import com.roamify.travel.map.TouchableWrapper;
import com.roamify.travel.models.PackageDetailsModel;
import com.roamify.travel.utils.GeocodingLocation;
import com.roamify.travel.utils.Validations;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, TouchableWrapper.UpdateMapAfterUserInterection {
    private MapWrapperLayout mRelativeLayoutMapWrapperLayout;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    Marker mMarker;
    String address;
    LatLng latLng;

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
        PackageDetailsModel packageDetailsModel = ActivityPackageDetails.getInstance().packageDetailsModel;
        address = packageDetailsModel.getAddress();
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
        /*GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address,
                getActivity(), new GeocoderHandler());*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(Validations.isNotNullNotEmptyNotWhiteSpace(address)) {
                        latLng = getGeoCoordsFromAddress(getActivity(), address);
                        zoomAndAnimateMap(13.0f, latLng, address);
                    }
                } catch (Exception Ex) {
                    Ex.printStackTrace();
                }
            }
        }).start();
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

    public static LatLng getGeoCoordsFromAddress(Context c, String address) {
        Geocoder geocoder = new Geocoder(c);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                System.out.println(latitude);
                System.out.println(longitude);
                return new LatLng(latitude, longitude);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    void zoomAndAnimateMap(final float mapZooming, final LatLng latLng, final String locationName) {
        //LatLng currentPosition = new LatLng(lat, lng);
        final MarkerOptions markerOptions = new MarkerOptions();
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMarker = googleMap.addMarker(markerOptions
                            .position(latLng)
                            .title(locationName)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .alpha(0.7f));

                    googleMap.setPadding(30, 300, 50, 20);
                    builder.include(markerOptions.getPosition());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, mapZooming);
                    //googleMap.moveCamera(cameraUpdate);
                    googleMap.animateCamera(cameraUpdate);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpdateMapAfterUserInterection() {

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

            if (Validations.isNotNullNotEmptyNotWhiteSpace(locationAddress)) {
                try {
                    String latlng[] = locationAddress.split(",");
                    //zoomAndAnimateMap(13.0f, latlng[0], address);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}

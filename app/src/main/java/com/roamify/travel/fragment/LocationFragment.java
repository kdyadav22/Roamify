package com.roamify.travel.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.roamify.travel.utils.Validations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */

public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, TouchableWrapper.UpdateMapAfterUserInterection {
    private MapWrapperLayout mRelativeLayoutMapWrapperLayout;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker mMarker;
    private String address;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        mRelativeLayoutMapWrapperLayout = view.findViewById(R.id.detail_map_relative_layout);
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
                assert fragmentManager != null;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(Validations.isNotNullNotEmptyNotWhiteSpace(address)) {
                        /*latLng = getGeoCoordsFromAddress(getActivity(), address);
                        zoomAndAnimateMap(latLng, address);*/
                        getLatLongFromPlace(address);
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

    private static LatLng getGeoCoordsFromAddress(Context c, String address) {
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

    void zoomAndAnimateMap(final LatLng latLng, final String locationName) {
        //LatLng currentPosition = new LatLng(lat, lng);
        final MarkerOptions markerOptions = new MarkerOptions();
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(latLng!=null) {
                        mMarker = googleMap.addMarker(markerOptions
                                .position(latLng)
                                .title(locationName)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                .alpha(0.7f));

                        googleMap.setPadding(30, 200, 30, 20);
                        builder.include(markerOptions.getPosition());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 9.0f);
                        //googleMap.moveCamera(cameraUpdate);
                        googleMap.animateCamera(cameraUpdate);
                    }else
                    {
                        Toast.makeText(getActivity(), "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpdateMapAfterUserInterection() {

    }

    public void getLatLongFromPlace(final String place) {
        try {
            Geocoder selected_place_geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> address;
            address = selected_place_geocoder.getFromLocationName(place, 5);
            if (address == null) {
                Toast.makeText(getActivity(), "There is no any address", Toast.LENGTH_SHORT).show();
            } else {
                final Address location = address.get(0);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        zoomAndAnimateMap(new LatLng(location.getLatitude(), location.getLongitude()), place);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            fetchLatLongFromService fetch_latlng_from_service_abc = new fetchLatLongFromService(place.replaceAll("\\s+", ""));
            fetch_latlng_from_service_abc.execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class fetchLatLongFromService extends AsyncTask<Void, Void, StringBuilder> {
        String place;
        fetchLatLongFromService(String place) {
            super();
            this.place = place;
        }
        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
            this.cancel(true);
        }
        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                HttpURLConnection conn;
                StringBuilder jsonResults = new StringBuilder();
                String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="+ this.place + "&sensor=false";
                URL url = new URL(googleMapUrl);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                return jsonResults;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());
                JSONArray resultJsonArray = jsonObj.getJSONArray("results");

                // Extract the Place descriptions from the results
                // resultList = new ArrayList<String>(resultJsonArray.length());

                if(resultJsonArray.length()>0) {
                    JSONObject before_geometry_jsonObj = resultJsonArray.getJSONObject(0);
                    JSONObject geometry_jsonObj = before_geometry_jsonObj.getJSONObject("geometry");
                    JSONObject location_jsonObj = geometry_jsonObj.getJSONObject("location");

                    String lat_helper = location_jsonObj.getString("lat");
                    double lat = Double.valueOf(lat_helper);

                    String lng_helper = location_jsonObj.getString("lng");
                    double lng = Double.valueOf(lng_helper);

                    final LatLng point = new LatLng(lat, lng);
                    //noinspection ConstantConditions
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            zoomAndAnimateMap(point, place);
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Address is not correct", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
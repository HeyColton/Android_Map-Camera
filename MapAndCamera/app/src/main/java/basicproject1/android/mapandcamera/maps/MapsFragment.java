package basicproject1.android.mapandcamera.maps;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends SupportMapFragment {
    private static final String TAG = "MapsFragmentTag";
    public static final float MINIMUM_ZOOM = 2.0f;
    public static final float MAXIMUM_ZOOM = 21.0f;
    private GoogleApiClient mClient;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private double mLat;
    private double mLon;
    private float mZoom;
    private boolean isMyLocationSet;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }


    public void updateLocation(double lat, double lon) {
        mLat = lat;
        mLon = lon;
        updateDisplay();
    }

    public void updateToCurrentLocation() {
        Log.i(TAG, "Should find image...");
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i(TAG, "Got a fix: " + location);
                        updateLocation(location.getLatitude(), location.getLongitude());

                    }
                });

    }

    public void updateZoom(float zoom) {
        mZoom = zoom;
        updateDisplay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mZoom = MINIMUM_ZOOM;
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mUiSettings = mMap.getUiSettings();
                mUiSettings.setZoomControlsEnabled(true);
                mUiSettings.setCompassEnabled(true);
                mUiSettings.setMyLocationButtonEnabled(true);
                mMap.setMyLocationEnabled(true);
                mUiSettings.setScrollGesturesEnabled(true);
                mUiSettings.setZoomGesturesEnabled(true);
                mUiSettings.setTiltGesturesEnabled(true);
                mUiSettings.setRotateGesturesEnabled(true);
//                updateToCurrentLocation();
                setUpMap();

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();

    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        updateToCurrentLocation();
//    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    // SupportMapFragment has its own onCreateView method that is used to create and display a map
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_maps, container, false);
//    }
    private void setUpMap() {
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(provider);

//        //set map type
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Current"));
    }
    private void updateDisplay() {
        if(mMap != null) {
            LatLng location = new LatLng(mLat, mLon);
            MarkerOptions locationMarker = new MarkerOptions().position(location);

            mMap.clear();
            mMap.addMarker(locationMarker);

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, mZoom);
            mMap.animateCamera(update);
        }
    }

}

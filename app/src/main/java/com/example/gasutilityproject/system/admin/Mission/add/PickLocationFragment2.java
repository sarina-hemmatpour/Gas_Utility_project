package com.example.gasutilityproject.system.admin.Mission.add;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.carto.styles.AnimationStyle;
import com.carto.styles.AnimationStyleBuilder;
import com.carto.styles.AnimationType;
import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.uiTools.Custom.Button.ColoredButton;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.neshan.common.model.LatLng;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.internal.utils.BitmapUtils;
import org.neshan.mapsdk.model.Marker;

import java.text.DateFormat;
import java.util.Date;

public class PickLocationFragment2 extends BaseFragment {

    final int REQUEST_CODE = 123;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private UserType userType = UserType.MANAGER;

    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private String lastUpdateTime;
    private Boolean mRequestingLocationUpdates;

    private ColoredButton btnSelect;
    ImageView floatingActionButton;

    private Location userLocation;
    private Marker marker;

    private Marker markerChoose = null;
    private LatLng latLng = null;
    private MapView mapView;

    private OnLocationPickerListener listener;
    private boolean isEnable = true;

    public void setListener(OnLocationPickerListener listener) {
        this.listener = listener;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setDefaultLngLat(double lat, double lng, boolean isEnable) {
        latLng = new LatLng(lat, lng);
        this.isEnable = isEnable;
    }

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {

        mapView = new MapView(activity);
        if (latLng != null) {
            mapView.moveCamera(new LatLng(latLng.getLatitude(), latLng.getLongitude()), 0.25f);
            mapView.setZoom(15, 0.25f);
            createMarker(latLng);
        }
        parent.addView(mapView, Param.consParam(-1, -1, 0, 0, 0, 0));

        if (UserType.MANAGER == userType)
            mapView.setOnMapClickListener(latLng -> {
                this.latLng = latLng;

                if (markerChoose != null)
                    mapView.removeMarker(markerChoose);
                markerChoose = createMarker(latLng);
                mapView.addMarker(markerChoose);
            });

        btnSelect = new ColoredButton(activity);
        btnSelect.setId(Ids.BUTTON_VIEW_ID_5);
        btnSelect.setup(userType == UserType.MANAGER ? "انتخاب مکان" : "ثبت مکان من", Colors.white, Colors.hover, Colors.primaryBlue, Dimen.fontSize14, 0, true, onClickListener);
        parent.addView(btnSelect, Param.consParam(-1, -2, -1, 0, 0, 0, Theme.getAf(90), Dimen.m40, Dimen.m40, Dimen.m40));
        if (!isEnable)
            btnSelect.setVisibility(View.GONE);

        floatingActionButton = new ImageView(activity);
        floatingActionButton.setImageResource(R.drawable.baseline_my_location_24);
        floatingActionButton.setId(Ids.BUTTON_VIEW_ID_4);
        floatingActionButton.setOnClickListener(onClickListener);
        floatingActionButton.setColorFilter(Colors.primaryBlue);
        parent.addView(floatingActionButton, Param.consParam(Theme.getAf(120), Theme.getAf(120), -1, -1, 0, isEnable ? -btnSelect.getId() : 0, -1, -1, Dimen.m40, Dimen.m24));

        return parent;
    }

    @Override
    public void onStart() {
        super.onStart();
        initLocation();
        startReceivingLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        settingsClient = LocationServices.getSettingsClient(activity);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                onLocationChange();
            }
        };

        mRequestingLocationUpdates = false;

        if (locationRequest == null) {
            locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL_IN_MILLISECONDS).build();
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(locationRequest);
            locationSettingsRequest = builder.build();
        }
    }


    public void startReceivingLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, "android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, "android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
                mRequestingLocationUpdates = true;
                startLocationUpdates();
            } else {
                requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, REQUEST_CODE);
            }
        } else {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(activity, "android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, "android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
                mRequestingLocationUpdates = true;
                startLocationUpdates();
            }
        }
    }

    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        if (ContextCompat.checkSelfPermission(activity, "android.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(activity, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void addUserMarker(LatLng loc) {
        //remove existing marker from map
        if (marker != null) {
            mapView.removeMarker(marker);
        }
        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), org.neshan.mapsdk.R.drawable.ic_my_location)));

        MarkerStyle markSt = markStCr.buildStyle();

        // Creating user marker
        marker = new Marker(loc, markSt);

        // Adding user marker to map!
        mapView.addMarker(marker);
    }

    private void onLocationChange() {
        if (userLocation != null && userType == UserType.TECHNICIAN && isEnable) {
            latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            addUserMarker(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));
            mapView.moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), .5f);
        }
    }

    public void focusOnUserLocation(View view) {
        if (userLocation != null) {
            addUserMarker(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));
            mapView.moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
            mapView.setZoom(15, 0.25f);
        } else {
            startReceivingLocationUpdates();
        }
    }

    public void stopLocationUpdates() {
        // Removing location updates
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                    }
                });
    }


    private Marker createMarker(LatLng loc) {
        // Creating animation for marker. We should use an object of type AnimationStyleBuilder, set
        // all animation features on it and then call buildStyle() method that returns an object of type
        // AnimationStyle
        AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
        animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTHSTEP);
        animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
        animStBl.setPhaseInDuration(0.5f);
        animStBl.setPhaseOutDuration(0.5f);
        AnimationStyle animSt = animStBl.buildStyle();

        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
        // AnimationStyle object - that was created before - is used here
        markStCr.setAnimationStyle(animSt);
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating marker
        return new Marker(loc, markSt);
    }

    private View.OnClickListener onClickListener = v -> {
        if (btnSelect.getId() == v.getId()) {
            if (latLng == null) {
                if (userType == UserType.MANAGER)
                    Toast.makeText(activity, "لطفا یک نقطه را انتخاب کنید", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activity, "لطفا لوکیشن خود را روشن کنید و به برنامه دسترسی دهید", Toast.LENGTH_SHORT).show();
            } else {
                if (listener != null) {
                    Toast.makeText(activity, latLng.getLatitude() + " | " + latLng.getLongitude(), Toast.LENGTH_SHORT).show();
                    activity.popFragment(true);
                    listener.onPickedLocation(latLng.getLatitude(), latLng.getLongitude());
                }
            }
        } else if (v.getId() == floatingActionButton.getId()) {
            focusOnUserLocation(floatingActionButton);
        }
    };

    public interface OnLocationPickerListener {
        void onPickedLocation(double latitude, double longitude);
    }

    public enum UserType {
        MANAGER,
        TECHNICIAN
    }
}

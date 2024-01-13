package com.example.gasutilityproject.system.admin.Mission.add;
//import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Text;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.uiTools.Custom.Button.ColoredButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
//import com.mapbox.api.geocoding.v5.GeocodingCriteria;
//import com.mapbox.api.geocoding.v5.MapboxGeocoding;
//import com.mapbox.api.geocoding.v5.models.CarmenFeature;
//import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
//import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

import timber.log.Timber;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
//import com.mapbox.maps.plugin.annotation.annotations;
import com.mapbox.maps.ViewAnnotationOptions;
import com.mapbox.maps.plugin.annotation.Annotation;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.viewannotation.ViewAnnotationManager;
//import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager;
import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;

public class PickLocationFragment extends BaseFragment {

    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private PermissionsManager permissionsManager;
    private ColoredButton btnSelect;

    private ImageView hoveringMarker;
    private MapView mapView;
    ImageView floatingActionButton;
    private OnLocationPickerListener listener;

    public void setListener(OnLocationPickerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                Toast.makeText(activity, "Permission granted!", Toast.LENGTH_SHORT).show();
            }
        }
    });

    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
        @Override
        public void onIndicatorBearingChanged(double v) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
        }
    };

    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
        @Override
        public void onIndicatorPositionChanged(@NonNull Point point) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(20.0).build());
            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
        }
    };

    private final OnMoveListener onMoveListener = new OnMoveListener() {
        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
            getGestures(mapView).removeOnMoveListener(onMoveListener);
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
            return false;
        }

        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {

        }
    };

    @Override
    public ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        mapView = new MapView(activity);
        parent.addView(mapView, Param.consParam(-1, -1, 0, 0, 0, 0));
//        mapView.setCameraDistance(1f);
//        mapView.getMapboxMap().setCamera(CameraOptions.class.);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        mapView.getMapboxMap().loadStyleUri(
                Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
//                        addAnnotationToMap();
                        mapView.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(20.0).build());
                        LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
                        locationComponentPlugin.setEnabled(true);
                        LocationPuck2D locationPuck2D = new LocationPuck2D();
                        locationPuck2D.setBearingImage(AppCompatResources.getDrawable(activity, R.drawable.ic_map_marker));
                        locationComponentPlugin.setLocationPuck(locationPuck2D);
                        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                        getGestures(mapView).addOnMoveListener(onMoveListener);

                        floatingActionButton.setOnClickListener(view -> {
                            locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                            locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                            getGestures(mapView).addOnMoveListener(onMoveListener);
                            floatingActionButton.setVisibility(View.GONE);
                        });
                    }
                }
        );

        hoveringMarker = new ImageView(activity);
        hoveringMarker.setId(6545);
        parent.addView(hoveringMarker, Param.consParam(-2, -2, 0, 0, 0, 0));
//        addAnnotationToMap();

        btnSelect = new ColoredButton(activity);
        btnSelect.setId(Ids.BUTTON_VIEW_ID_5);
        btnSelect.setup("انتخاب مکان", Colors.white, Colors.hover, Colors.primaryBlue, Dimen.fontSize14, 0, true, onClickListener);
        parent.addView(btnSelect, Param.consParam(-1, -2, -1, 0, 0, 0, Theme.getAf(90), Dimen.m40, Dimen.m40, Dimen.m40));

        floatingActionButton = new ImageView(activity);
        floatingActionButton.setImageResource(R.drawable.baseline_my_location_24);
        floatingActionButton.setId(Ids.BUTTON_VIEW_ID_4);
        floatingActionButton.setColorFilter(Colors.primaryBlue);
        parent.addView(floatingActionButton, Param.consParam(Theme.getAf(120), Theme.getAf(120), -1, -1, 0, -btnSelect.getId(), -1, -1, Dimen.m40, Dimen.m24));
        floatingActionButton.setVisibility(View.GONE);

        return parent;
    }

    private View.OnClickListener onClickListener = v -> {
        listener.onPickedLocation(-1, -1);
//        mapView = null;
        activity.popFragment(true);
    };

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }

    interface OnLocationPickerListener {
        void onPickedLocation(long latitude, long longitude);
    }


}

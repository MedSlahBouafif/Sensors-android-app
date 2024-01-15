package tn.esprit.sensors.gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tn.esprit.sensors.R;
import tn.esprit.sensors.database.AppDatabase;
import tn.esprit.sensors.database.Route;
import tn.esprit.sensors.gps.Routes;

public class MapsActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        SensorEventListener {

    private Polyline routePolyline;
    private boolean isRouting = false;
    private List<GeoPoint> routePoints;
    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 4;
    private MapView mapView;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private Handler locationUpdateHandler;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private Button startButton;

    private long startTime;
    private TextView timerTextView;
    private long startTimeMillis;
    private long elapsedTimeMillis;
    private Handler timerHandler = new Handler();
    private int stepCount;

    private boolean isRouteActive = false;

    private static final int LOCATION_UPDATE_INTERVAL = 1000;

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setupMap();

        appDatabase = AppDatabase.getDatabase(getApplicationContext());
        routePoints = new ArrayList<>();

        locationUpdateHandler = new Handler();
        startLocationUpdates();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        MaterialToolbar materialToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(materialToolbar);

        Configuration.getInstance().setUserAgentValue(BuildConfig.LIBRARY_PACKAGE_NAME);

        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this::onStartButtonClick);

        checkPermissionsState();
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    private void checkPermissionsState() {
        if (checkLocationPermission()) {
        } else {
            requestLocationPermission();
        }
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                MULTIPLE_PERMISSION_REQUEST_CODE
        );
    }

    private void setupMap() {
        mapView = findViewById(R.id.mapview);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.getController().setZoom(15);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        MyLocationNewOverlay oMapLocationOverlay = new MyLocationNewOverlay(mapView);
        oMapLocationOverlay.enableFollowLocation();
        oMapLocationOverlay.enableMyLocation();
        oMapLocationOverlay.disableMyLocation();
        mapView.getOverlays().add(oMapLocationOverlay);

        CompassOverlay compassOverlay = new CompassOverlay(this, mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        mapView.setMapListener(new DelayedMapListener(new MapListener() {
            @Override
            public boolean onZoom(final ZoomEvent e) {
                updateLocationInfo();
                return true;
            }

            @Override
            public boolean onScroll(final ScrollEvent e) {
                updateLocationInfo();
                return true;
            }
        }, 1000));
    }

    private void updateLocationInfo() {
        if (mapView != null && mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            String latitudeStr = String.format(Locale.getDefault(), "%.7f", latitude);
            String longitudeStr = String.format(Locale.getDefault(), "%.7f", longitude);

            TextView latLongTv = findViewById(R.id.textView);
            latLongTv.setText(String.format(Locale.getDefault(), "%s, %s", latitudeStr, longitudeStr));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        setCenterInMyCurrentLocation();
        if (isRouting) {
            GeoPoint currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
            routePoints.add(currentLocation);
            routePolyline.setPoints(routePoints);
        }
    }
    private void startLocationUpdates() {
        locationUpdateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLocationInfo();
                locationUpdateHandler.postDelayed(this, LOCATION_UPDATE_INTERVAL);
            }
        }, LOCATION_UPDATE_INTERVAL);
        // Remove the following log statement
        // Log.d("MapsActivity", "Location updates started");
    }


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void setCenterInMyCurrentLocation() {
        if (mLastLocation != null && mapView != null) {
            mapView.getOverlays().clear();

            GeoPoint newLocation = new GeoPoint(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            Marker currentLocationMarker = new Marker(mapView);
            currentLocationMarker.setPosition(newLocation);
            mapView.getOverlays().add(currentLocationMarker);

            mapView.getController().setCenter(newLocation);

            for (Overlay overlay : mapView.getOverlays()) {
                // Log the types of overlays present
                Log.d("MapsActivity", "Overlay type: " + overlay.getClass().getSimpleName());
            }
        } else {
            Toast.makeText(this, "Getting current location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (checkLocationPermission()) {
            requestLocationUpdates();
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(LOCATION_UPDATE_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Handle connection suspension
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Handle connection failure
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.reconnect();
        }
        if (isRouteActive && stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        locationUpdateHandler.removeCallbacksAndMessages(null);

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        if (!isRouteActive && stepSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDetach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MULTIPLE_PERMISSION_REQUEST_CODE) {
            boolean somePermissionWasDenied = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    somePermissionWasDenied = true;
                }
            }
            if (somePermissionWasDenied) {
                Toast.makeText(this, "Can't load maps without all the permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                setupMap();
            }
        } else {
            Toast.makeText(this, "Can't load maps without all the permissions granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MapsActivity.this, Routes.class);
            startActivity(settingsIntent);
            return true;
        } else if (id == R.id.action_locate) {
            setCenterInMyCurrentLocation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveRouteToDatabase(String routeName, String startTime, String endTime) {
        Route route = new Route();
        route.setName(routeName);
        route.setStartTime(startTime);
        route.setEndTime(endTime);
        // Save route to database
    }

    private void onRouteCompleted(String routeName, String startTime, String endTime) {
        saveRouteToDatabase(routeName, startTime, endTime);
        // Additional actions after completing a route
    }

    private void startRoute() {
        isRouting = true;
        routePoints.clear();
        routePolyline = new Polyline();
        routePolyline.setColor(Color.BLUE);
        routePolyline.setWidth(5f);
        mapView.getOverlayManager().add(routePolyline);
        isRouteActive = true;
        startTime = System.currentTimeMillis();
        startTimer();
        startStepCounter();
        // Additional actions when starting a route
    }

    private void completeRoute() {
        isRouting = false;
        routePolyline = null;
        String routeName = "Route " + System.currentTimeMillis();
        String startTime = getFormattedTime(System.currentTimeMillis());
        String endTime = getFormattedTime(System.currentTimeMillis());
        isRouteActive = false;
        // Reset timer and step count
        startTimeMillis = 0;
        elapsedTimeMillis = 0;
        stepCount = 0;
        // Stop timer and step counter
        stopTimer();
        stopStepCounter();
        // Reset UI elements
        updateTimerText(elapsedTimeMillis);
        updateStepCount(stepCount);
        TextView timerTextView = findViewById(R.id.timerTextView);
        timerTextView.setText("Timer: 00:00:00");
        TextView stepCountTextView = findViewById(R.id.stepCountTextView);
        stepCountTextView.setText("Step Count: 0");
        // Save route to database
        new InsertRouteAsyncTask().execute(new Route(routeName, startTime, endTime));
    }

    private class InsertRouteAsyncTask extends AsyncTask<Route, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Route... routes) {
            try {
                appDatabase.routeDao().insertRoute(routes[0]);
                return true; // Database insertion successful
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Database insertion failed
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (isSuccess) {
                Toast.makeText(MapsActivity.this, "Route added to the database", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MapsActivity.this, "Failed to add route to the database", Toast.LENGTH_SHORT).show();
            }
            // Additional UI-related operations after the database operation
        }
    }

    private String getFormattedTime(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timeMillis));
    }

    private String formatElapsedTime(long elapsedMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date(elapsedMillis);
        return sdf.format(date);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRouteActive) {
                long currentTimeMillis = System.currentTimeMillis();
                elapsedTimeMillis = currentTimeMillis - startTimeMillis;
                updateTimerText(elapsedTimeMillis);
                timerHandler.postDelayed(this, 1000);
            }
        }
    };

    private void startTimer() {
        isRouteActive = true;
        startTimeMillis = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void stopTimer() {
        isRouteActive = false;
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void updateTimerText(long elapsedMillis) {
        String formattedTime = formatElapsedTime(elapsedMillis);
        TextView timerTextView = findViewById(R.id.timerTextView);
        timerTextView.setText(formattedTime);
    }

    private void updateStepCount(int stepCount) {
        TextView stepCountTextView = findViewById(R.id.stepCountTextView);
        stepCountTextView.setText(String.valueOf(stepCount));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int stepCount = (int) event.values[0];
            updateStepCount(stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    private void startStepCounter() {
        if (sensorManager == null || stepSensor == null) {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

            if (stepSensor != null) {
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(this, "Step counter sensor not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void stopStepCounter() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void onStartButtonClick(View view) {
        if (!isRouting) {
            startRoute();
        } else {
            completeRoute();
        }
        updateStartButtonUI();
    }
    private void updateStartButtonUI() {
        startButton.setText(isRouting ? "Stop" : "Start");
    }
}

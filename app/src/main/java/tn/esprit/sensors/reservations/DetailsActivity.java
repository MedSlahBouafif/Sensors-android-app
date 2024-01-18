package tn.esprit.sensors.reservations;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import tn.esprit.sensors.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get data from intent
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        int imageId = intent.getIntExtra("imageId", R.drawable.placeholder_image);
        String description = intent.getStringExtra("description");
        String location = intent.getStringExtra("location");
        String phone = intent.getStringExtra("phone");
        String time = intent.getStringExtra("time");
        String hum = intent.getStringExtra("hum");
        String temp = intent.getStringExtra("temp");
        String press = intent.getStringExtra("press");

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        Sensor ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        // Set data to views
        ImageView detailImageView = findViewById(R.id.detailImageView);
        detailImageView.setImageResource(imageId);

        TextView detailTextView = findViewById(R.id.detailTextView);
        detailTextView.setText(itemName);

        TextView detailDescriptionTextView = findViewById(R.id.detailDescriptionTextView);
        detailDescriptionTextView.setText(description);

        TextView detailLocationView = findViewById(R.id.locationLabel);
        detailLocationView.setText(location);

        TextView detailPhoneView = findViewById(R.id.phoneLabel);
        detailPhoneView.setText(phone);

        TextView detailTimeView = findViewById(R.id.clockLabel);
        detailTimeView.setText(time);

        // Initialize TextView elements for sensor data
        TableLayout comparisonTable = findViewById(R.id.comparisonTable);
        TableRow dataRow1 = (TableRow) comparisonTable.getChildAt(1);
        TextView temperatureData1 = (TextView) dataRow1.getChildAt(2);
        TextView temperatureData2 = (TextView) dataRow1.getChildAt(3);

        TableRow dataRow2 = (TableRow) comparisonTable.getChildAt(2);
        TextView pressureData1 = (TextView) dataRow2.getChildAt(2);
        TextView pressureData2 = (TextView) dataRow2.getChildAt(3);

        TableRow dataRow3 = (TableRow) comparisonTable.getChildAt(3);
        TextView humidityData1 = (TextView) dataRow3.getChildAt(2);
        TextView humidityData2 = (TextView) dataRow3.getChildAt(3);

        // Set static data
        temperatureData1.setText(temp + "°C");
        pressureData1.setText(press + "hPa");
        humidityData1.setText(hum + "%");

        Button reservationButton = findViewById(R.id.reservationButton);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to FinalReservationActivity
                Intent finalReservationIntent = new Intent(DetailsActivity.this, FinalReservationActivity.class);
                startActivity(finalReservationIntent);
            }
        });
        SensorEventListener humidityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float humidityValue = event.values[0];
                humidityData2.setText(String.format("%.2f%%", humidityValue));

                // Compare with static value and change text color
                if (Float.parseFloat(hum) < humidityValue) {
                    humidityData2.setTextColor(Color.RED);
                } else if (Float.parseFloat(hum) > humidityValue) {
                    humidityData2.setTextColor(Color.BLUE);
                } else {
                    humidityData2.setTextColor(Color.BLACK); // Set the default color
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Handle accuracy changes if needed
            }
        };

        SensorEventListener ambientTemperatureListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float temperatureValue = event.values[0];
                temperatureData2.setText(String.format("%.2f°C", temperatureValue));

                // Compare with static value and change text color
                if (Float.parseFloat(temp) < temperatureValue) {
                    temperatureData2.setTextColor(Color.RED);
                } else if (Float.parseFloat(temp) > temperatureValue) {
                    temperatureData2.setTextColor(Color.BLUE);
                } else {
                    temperatureData2.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Handle accuracy changes if needed
            }
        };

        SensorEventListener pressureListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float pressureValue = event.values[0];
                pressureData2.setText(String.format("%.2fhPa", pressureValue));

                // Compare with static value and change text color
                if (Float.parseFloat(press) < pressureValue) {
                    pressureData2.setTextColor(Color.RED);
                } else if (Float.parseFloat(press) > pressureValue) {
                    pressureData2.setTextColor(Color.BLUE);
                } else {
                    pressureData2.setTextColor(Color.BLACK); // Set the default color
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Handle accuracy changes if needed
            }
        };

        sensorManager.registerListener(humidityListener, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(ambientTemperatureListener, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(pressureListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }



}

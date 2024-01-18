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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import tn.esprit.sensors.R;

public class UtilsActivity extends AppCompatActivity {

    private TextView hum;
    private TextView temp;
    private TextView press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils);

        hum = findViewById(R.id.humid);
        temp = findViewById(R.id.temper);
        press = findViewById(R.id.presse);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        Sensor ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);


        SensorEventListener humidityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float humidityValue = event.values[0];
                hum.setText(String.format("%.2f%%", humidityValue));

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
                temp.setText(String.format("%.2f°C", temperatureValue));

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
                press.setText(String.format("%.2fhPa", pressureValue));

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
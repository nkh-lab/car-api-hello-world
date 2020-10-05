package com.example.carapihelloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.car.hardware.CarSensorEvent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.car.Car;
import android.car.hardware.CarSensorManager;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private class SpeedListener implements CarSensorManager.OnSensorChangedListener {
        public void onSensorChanged(CarSensorEvent var1) {
            Log.d (TAG, "onSpeedChanged: " + var1.floatValues[0]);
        }
    };

    private class GearListener implements CarSensorManager.OnSensorChangedListener {
        public void onSensorChanged(CarSensorEvent var1) {
            Log.d (TAG, "onGearChanged: " + var1.intValues[0]);
        }
    };

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private Car mCar;
    private CarSensorManager mCarSensorManager;

    SpeedListener mSpeedListener;
    GearListener mGearListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Car.PERMISSION_SPEED) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Car.PERMISSION_POWERTRAIN) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Car.PERMISSION_SPEED, Car.PERMISSION_POWERTRAIN}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            initCarApi();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            //all permissions have been granted
            if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED))
                initCarApi();
        }
    }

    private void initCarApi() {
        mCar = Car.createCar(this);
        mSpeedListener = new SpeedListener();
        mGearListener = new GearListener();

        mCarSensorManager = (CarSensorManager) mCar.getCarManager(Car.SENSOR_SERVICE);

        mCarSensorManager.registerListener(mSpeedListener, CarSensorManager.SENSOR_TYPE_CAR_SPEED, CarSensorManager.SENSOR_RATE_NORMAL);
        mCarSensorManager.registerListener(mGearListener, CarSensorManager.SENSOR_TYPE_GEAR, CarSensorManager.SENSOR_RATE_NORMAL);
    }
}
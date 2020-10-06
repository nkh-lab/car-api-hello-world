package com.example.carapihelloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarSensorManager;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.CarSensorEvent;
import android.car.hardware.property.CarPropertyManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private Car mCar;
    private CarSensorManager mCarSensorManager;
    private CarPropertyManager mCarPropertyManager;

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

        initCarSensorManager();
        initCarPropertyManager();
    }

    private void initCarSensorManager() {
        mCarSensorManager = (CarSensorManager) mCar.getCarManager(Car.SENSOR_SERVICE);

        mCarSensorManager.registerListener(new CarSensorManager.OnSensorChangedListener() {
            @Override
            public void onSensorChanged(CarSensorEvent var1) {
                Log.d(TAG, "CarSensorManager.onSpeedChanged: " + var1.floatValues[0]);
            }
        }, CarSensorManager.SENSOR_TYPE_CAR_SPEED, CarSensorManager.SENSOR_RATE_NORMAL);

        mCarSensorManager.registerListener(new CarSensorManager.OnSensorChangedListener() {
            @Override
            public void onSensorChanged(CarSensorEvent var1) {
                Log.d(TAG, "CarSensorManager.onGearChanged: " + var1.intValues[0]);
            }
        }, CarSensorManager.SENSOR_TYPE_GEAR, CarSensorManager.SENSOR_RATE_NORMAL);
    }

    private void initCarPropertyManager() {
        mCarPropertyManager = (CarPropertyManager) mCar.getCarManager(Car.PROPERTY_SERVICE);

        Log.d(TAG, "CarPropertyManager.CurrentGear: " + mCarPropertyManager.getIntProperty(VehiclePropertyIds.GEAR_SELECTION, 0));

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "CarPropertyManager.onGearChanged: " + carPropertyValue.getValue());
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.e(TAG, "CarPropertyManager.onGearChangedError");
            }
        }, VehiclePropertyIds.GEAR_SELECTION, CarPropertyManager.SENSOR_RATE_NORMAL);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "CarPropertyManager.onSpeedChanged: " + carPropertyValue.getValue());
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.e(TAG, "CarPropertyManager.onSpeedChangedError");
            }
        }, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL);
    }
}
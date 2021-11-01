package com.example.carapihelloworld;

import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CarApiHelloWorld";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private Car mCar;
    private CarPropertyManager mCarPropertyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request dangerous permissions only
        List<String> permissionList = new ArrayList<String>();

        if (checkSelfPermission(Car.PERMISSION_SPEED) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Car.PERMISSION_SPEED);
        }
        if (checkSelfPermission(Car.PERMISSION_ENERGY) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Car.PERMISSION_ENERGY);
        }

        if (!permissionList.isEmpty()) {
            requestPermissions(permissionList.toArray(new String[0]), REQUEST_CODE_ASK_PERMISSIONS);
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

        initCarPropertyManager();
    }

    private void initCarPropertyManager() {
        mCarPropertyManager = (CarPropertyManager) mCar.getCarManager(Car.PROPERTY_SERVICE);

        Log.d(TAG, "Test CarPropertyManager getters:");
        Log.d(TAG, "GEAR_SELECTION: getIntProperty(" + mCarPropertyManager.getIntProperty(VehiclePropertyIds.GEAR_SELECTION, 0) + ")");

        Log.d(TAG, "Test CarPropertyManager callbacks:");
        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "GEAR_SELECTION: onChangeEvent(" + carPropertyValue.getValue() + ")");
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.d(TAG, "GEAR_SELECTION: onErrorEvent(" + i + ", " + i1 + ")");
            }
        }, VehiclePropertyIds.GEAR_SELECTION, CarPropertyManager.SENSOR_RATE_NORMAL);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onChangeEvent(" + carPropertyValue.getValue() + ")");
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onErrorEvent(" + i + ", " + i1 + ")");
            }
        }, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "EV_BATTERY_LEVEL: onChangeEvent(" + carPropertyValue.getValue() + ")");
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.d(TAG, "EV_BATTERY_LEVEL: onErrorEvent(" + i + ", " + i1 + ")");
            }
        }, VehiclePropertyIds.EV_BATTERY_LEVEL, CarPropertyManager.SENSOR_RATE_ONCHANGE);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "FUEL_DOOR_OPEN: onChangeEvent(" + carPropertyValue.getValue() + ")");

                try {
                    // Should be signed to "android.car.permission.CONTROL_CAR_ENERGY_PORTS"
                    mCarPropertyManager.setBooleanProperty(VehiclePropertyIds.FUEL_DOOR_OPEN, 0, true);
                } catch (SecurityException | IllegalArgumentException e) {
                    Log.e(TAG, "FUEL_DOOR_OPEN: setBooleanProperty(), Exception: " + e.getMessage());
                }
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.d(TAG, "FUEL_DOOR_OPEN: onErrorEvent(" + i + ", " + i1 + ")");
            }
        }, VehiclePropertyIds.FUEL_DOOR_OPEN, CarPropertyManager.SENSOR_RATE_ONCHANGE);
    }
}

package com.example.carapihelloworld;

import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CarApiHelloWorld";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    VehiclePropertyView mGearPropertyView;
    VehiclePropertyView mSpeedPropertyView;
    VehiclePropertyView mBatteryLevelPropertyView;
    VehiclePropertyView mFuelDoorOpenPropertyView;

    private CarPropertyManager mCarPropertyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Request dangerous permissions only
        List<String> dangPermToRequest = checkDangerousPermissions();

        if (dangPermToRequest.isEmpty()) {
            initCarPropertyManager();
            initGUI();
        } else {
            requestDangerousPermissions(dangPermToRequest);
            // CB:
            // onRequestPermissionsResult()
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            //all permissions have been granted
            if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
                initCarPropertyManager();
                initGUI();
            }
        }
    }

    @NonNull
    private List<String> checkDangerousPermissions() {
        List<String> permissions = new ArrayList<String>();

        if (checkSelfPermission(Car.PERMISSION_SPEED) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Car.PERMISSION_SPEED);
        }
        if (checkSelfPermission(Car.PERMISSION_ENERGY) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Car.PERMISSION_ENERGY);
        }

        return permissions;
    }

    private void requestDangerousPermissions(@NonNull List<String> permissions) {
        requestPermissions(permissions.toArray(new String[0]), REQUEST_CODE_ASK_PERMISSIONS);
    }

    private void initGUI() {
        setContentView(R.layout.activity_main);

        mGearPropertyView = findViewById(R.id.gear_property_view);
        mGearPropertyView.setPropId(VehiclePropertyIds.GEAR_SELECTION)
                .setPropName(VehiclePropertyIds.toString(VehiclePropertyIds.GEAR_SELECTION));

        mSpeedPropertyView = findViewById(R.id.speed_property_view);
        mSpeedPropertyView.setPropId(VehiclePropertyIds.PERF_VEHICLE_SPEED)
                .setPropName(VehiclePropertyIds.toString(VehiclePropertyIds.PERF_VEHICLE_SPEED));

        mBatteryLevelPropertyView = findViewById(R.id.battery_level_property_view);
        mBatteryLevelPropertyView.setPropId(VehiclePropertyIds.EV_BATTERY_LEVEL)
                .setPropName(VehiclePropertyIds.toString(VehiclePropertyIds.EV_BATTERY_LEVEL));

        mFuelDoorOpenPropertyView = findViewById(R.id.fuel_door_property_view);
        mFuelDoorOpenPropertyView.setPropId(VehiclePropertyIds.FUEL_DOOR_OPEN)
                .setPropName(VehiclePropertyIds.toString(VehiclePropertyIds.FUEL_DOOR_OPEN))
                .enableSetValue(new VehiclePropertyView.SetValueCB() {
                    @Override
                    public void onSetValue(String value_to_set) {
                        Log.d(TAG, "onEdit(" + value_to_set + ")");

                        try {
                            mCarPropertyManager.setBooleanProperty(VehiclePropertyIds.FUEL_DOOR_OPEN, 0, Boolean.parseBoolean(value_to_set));
                        } catch (SecurityException | IllegalArgumentException e) {
                            Log.e(TAG, "FUEL_DOOR_OPEN: setBooleanProperty(), Exception: " + e.getMessage());
                        }
                    }
                });
    }

    private void initCarPropertyManager() {
        mCarPropertyManager = (CarPropertyManager) Car.createCar(this).getCarManager(Car.PROPERTY_SERVICE);

        Log.d(TAG, "Test CarPropertyManager getters:");
        Log.d(TAG, "GEAR_SELECTION: getIntProperty(" + mCarPropertyManager.getIntProperty(VehiclePropertyIds.GEAR_SELECTION, 0) + ")");

        Log.d(TAG, "Test CarPropertyManager callbacks:");
        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "GEAR_SELECTION: onChangeEvent(" + carPropertyValue.getValue() + ")");
                mGearPropertyView.setPropValue(String.valueOf(carPropertyValue.getValue()));
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "GEAR_SELECTION: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.GEAR_SELECTION, CarPropertyManager.SENSOR_RATE_NORMAL);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onChangeEvent(" + carPropertyValue.getValue() + ")");
                mSpeedPropertyView.setPropValue(String.valueOf(carPropertyValue.getValue()));
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "EV_BATTERY_LEVEL: onChangeEvent(" + carPropertyValue.getValue() + ")");
                mBatteryLevelPropertyView.setPropValue(String.valueOf(carPropertyValue.getValue()));
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "EV_BATTERY_LEVEL: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.EV_BATTERY_LEVEL, CarPropertyManager.SENSOR_RATE_ONCHANGE);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "FUEL_DOOR_OPEN: onChangeEvent(" + carPropertyValue.getValue() + ")");
                mFuelDoorOpenPropertyView.setPropValue(String.valueOf(carPropertyValue.getValue()));
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "FUEL_DOOR_OPEN: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.FUEL_DOOR_OPEN, CarPropertyManager.SENSOR_RATE_ONCHANGE);
    }
}

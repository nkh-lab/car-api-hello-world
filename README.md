# car-api-hello-world
Android automotive CAR API usage example.

Example based on `android.car.hardware.CarPropertyManager` usage for obtaining speed, gear and other properties which are belonged for all possible permission protection levels:
- normal
- dangerous
- signature|privileged

Project support both [gradle](app/build.gradle) and [AOSP (Android.bp)](Android.bp) builds. 

## CAR API usage
In this project for using CAR API the following steps were done:

1. Added `android.car` library (to [gradle](app/build.gradle) or to [Android.bp](Android.bp))

2. Added permissions to [AndroidManifest.xml](app/src/main/AndroidManifest.xml)

3. Requested `dangerous` permissions in runtime in `onCreate()`

4. Added listeners by `CarPropertyManager.registerCallback()` and used get API, e.g. `CarPropertyManager.getIntProperty()`.

## Emulator/AVD
Tested on next emulators:
- SDK build on `Polestar2` AVD 
- AOSP build on `aosp_car_x86-userdebug` (android-12.0.0_r3)

[Useful link of how to set up `Polestar2` on Android Studio.](https://stackoverflow.com/questions/57968790/android-automotive-emulator-no-system-images-installed-for-this-target/58159715#58159715)

## AOSP
Start `CarApiHelloWorldApp` app via `am`:
```
$ am start -n com.example.carapihelloworld/.MainActivity
```

## Logcat expected output
After compiling and running this project, the following `logcat` messages are expected.

1. Not signed app

`Polstar2` emulator:
```
2021-11-01 03:16:36.826 26840-26840/com.example.carapihelloworld D/CarApiHelloWorld: Test CarPropertyManager getters:
2021-11-01 03:16:36.827 26840-26840/com.example.carapihelloworld D/CarApiHelloWorld: GEAR_SELECTION: getIntProperty(1)
2021-11-01 03:16:36.827 26840-26840/com.example.carapihelloworld D/CarApiHelloWorld: Test CarPropertyManager callbacks:
2021-11-01 03:16:36.840 26840-26840/com.example.carapihelloworld D/CarApiHelloWorld: GEAR_SELECTION: onChangeEvent(1)
2021-11-01 03:16:36.840 26840-26840/com.example.carapihelloworld D/CarApiHelloWorld: PERF_VEHICLE_SPEED: onChangeEvent(0.0)
2021-11-01 03:16:36.840 26840-26840/com.example.carapihelloworld D/CarApiHelloWorld: EV_BATTERY_LEVEL: onChangeEvent(150000.0)
2021-11-01 03:16:36.840 26840-26840/com.example.carapihelloworld D/CarApiHelloWorld: FUEL_DOOR_OPEN: onChangeEvent(false)
2021-11-01 03:16:36.840 26840-26840/com.example.carapihelloworld E/CarApiHelloWorld: FUEL_DOOR_OPEN: setBooleanProperty(), Exception: permission is null
```
AOSP `aosp_car_x86-userdebug` emulator:
```
2021-11-02 13:13:55.258 2642-2642/com.example.carapihelloworld D/CarApiHelloWorld: Test CarPropertyManager getters:
2021-11-02 13:13:55.266 2642-2642/com.example.carapihelloworld D/CarApiHelloWorld: GEAR_SELECTION: getIntProperty(4)
2021-11-02 13:13:55.266 2642-2642/com.example.carapihelloworld D/CarApiHelloWorld: Test CarPropertyManager callbacks:
2021-11-02 13:13:55.274 2642-2642/com.example.carapihelloworld D/CarApiHelloWorld: GEAR_SELECTION: onChangeEvent(4)
2021-11-02 13:13:55.290 2642-2642/com.example.carapihelloworld D/CarApiHelloWorld: PERF_VEHICLE_SPEED: onChangeEvent(0.0)
2021-11-02 13:13:55.290 2642-2642/com.example.carapihelloworld D/CarApiHelloWorld: EV_BATTERY_LEVEL: onChangeEvent(150000.0)
2021-11-02 13:13:55.290 2642-2642/com.example.carapihelloworld D/CarApiHelloWorld: FUEL_DOOR_OPEN: onChangeEvent(false)
2021-11-02 13:13:55.293 2642-2642/com.example.carapihelloworld E/CarApiHelloWorld: FUEL_DOOR_OPEN: setBooleanProperty(), Exception: requires android.car.permission.CONTROL_CAR_ENERGY_PORTS
```
2. Signed app

AOSP `aosp_car_x86-userdebug` emulator:
```
11-01 16:12:26.157  3587  3587 D CarApiHelloWorld: Test CarPropertyManager getters:
11-01 16:12:26.162  3587  3587 D CarApiHelloWorld: GEAR_SELECTION: getIntProperty(4)
11-01 16:12:26.162  3587  3587 D CarApiHelloWorld: Test CarPropertyManager callbacks:
11-01 16:12:26.172  3587  3587 D CarApiHelloWorld: GEAR_SELECTION: onChangeEvent(4)
11-01 16:12:26.172  3587  3587 D CarApiHelloWorld: PERF_VEHICLE_SPEED: onChangeEvent(0.0)
11-01 16:12:26.172  3587  3587 D CarApiHelloWorld: EV_BATTERY_LEVEL: onChangeEvent(150000.0)
11-01 16:12:26.177  3587  3587 D CarApiHelloWorld: FUEL_DOOR_OPEN: onChangeEvent(false)
11-01 16:12:26.197  3587  3587 D CarApiHelloWorld: FUEL_DOOR_OPEN: onChangeEvent(true)
```
To change car speed, gear, location, etc. use `AVD Extended Controls`:

![](doc/screenshots/ExtendedControls-CarData.png)

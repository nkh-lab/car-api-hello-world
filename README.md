# car-api-hello-world
Android automotive CAR API usage example.

Example based on `android.car.hardware.CarPropertyManager` usage for obtaining speed, gear and other properties which are belonged for all possible permission protection levels:
- normal
- dangerous
- signature|privileged

Project support both [gradle](app/build.gradle) and [AOSP (Android.bp)](Android.bp) builds. 

![](doc/screenshots/MainView-SetDialogue.png)


## CAR API usage
To use the CAR API, the following steps were taken:

1. Add `android.car` library (to [gradle](app/build.gradle), to [Android.bp](Android.bp)).

2. Add permissions to [AndroidManifest.xml](app/src/main/AndroidManifest.xml).

3. Request `dangerous` permissions in runtime in `onCreate()` in [MainActivity.java](app/src/main/java/com/example/carapihelloworld/MainActivity.java).

4. Use `CarPropertyManager` API in [MainActivity.java](app/src/main/java/com/example/carapihelloworld/MainActivity.java):
    - listeners: `registerCallback()`
    - getters: `getIntProperty()`
    - setters: `setBooleanProperty`

## Emulator/AVD
Tested on emulators:
- `Polestar2` ([Useful link of how to set up `Polestar2` on Android Studio](https://stackoverflow.com/questions/57968790/android-automotive-emulator-no-system-images-installed-for-this-target/58159715#58159715))
- AOSP `aosp_car_x86-userdebug` (android-12.0.0_r3)

To change car speed, gear, location, etc. use `AVD Extended Controls`:

![](doc/screenshots/ExtendedControls-CarData.png)


## AOSP
Start `CarApiHelloWorldApp` app via ADB ActivityManager:
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

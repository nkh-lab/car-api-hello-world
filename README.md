# car-api-hello-world
Android automotive CAR API usage example.

Example based on `android.car.hardware.CarPropertyManager` usage for obtaining speed, gear and other properties.

## CAR API usage
In this project for using CAR API the following steps were done:

1. Added `android.car` library to [app/build.gradle](app/build.gradle):
```
android {
    ...
    useLibrary 'android.car'
}
```
2. Added permissions to [AndroidManifest.xml](app/src/main/AndroidManifest.xml)

3. Requested `dangerous` permissions in runtime in `onCreate()`

4. Added listeners by `CarPropertyManager.registerCallback()` and used get API, e.g. `CarPropertyManager.getIntProperty()`.

## Emulator/AVD
For runtime testing `Polestar2` AVD is used.

[Useful link of how to set up `Polestar2` on Android Studio.](https://stackoverflow.com/questions/57968790/android-automotive-emulator-no-system-images-installed-for-this-target/58159715#58159715)

## Logcat expected output
After compiling and running this project, the following logcat messages are expected:  
```
2021-11-01 00:14:43.602 22419-22419/com.example.carapihelloworld D/CarApiHelloWorld: Test CarPropertyManager getters:
2021-11-01 00:14:43.603 22419-22419/com.example.carapihelloworld D/CarApiHelloWorld: GEAR_SELECTION: getIntProperty(1)
2021-11-01 00:14:43.603 22419-22419/com.example.carapihelloworld D/CarApiHelloWorld: Test CarPropertyManager callbacks:
2021-11-01 00:14:43.611 22419-22419/com.example.carapihelloworld D/CarApiHelloWorld: GEAR_SELECTION: onChangeEvent(1)
2021-11-01 00:14:43.611 22419-22419/com.example.carapihelloworld D/CarApiHelloWorld: PERF_VEHICLE_SPEED: onChangeEvent(0.0)
2021-11-01 00:14:43.611 22419-22419/com.example.carapihelloworld D/CarApiHelloWorld: EV_BATTERY_LEVEL: onChangeEvent(150000.0)
```
To change car speed, gear, location, etc. use `AVD Extended Controls`:

![](doc/screenshots/ExtendedControls-CarData.png)

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
2021-10-31 23:19:07.580 13982-13982/com.example.carapihelloworld D/CarApiHelloWorld: CarPropertyManager.CurrentGear: 8
2021-10-31 23:19:07.598 13982-13982/com.example.carapihelloworld D/CarApiHelloWorld: CarPropertyManager.onGearChanged: 8
2021-10-31 23:19:07.598 13982-13982/com.example.carapihelloworld D/CarApiHelloWorld: CarPropertyManager.onSpeedChanged: 0.0
2021-10-31 23:19:07.598 13982-13982/com.example.carapihelloworld D/CarApiHelloWorld: CarPropertyManager.onEvBatteryLevelChanged: 150000.0
```
To change car speed, gear, location, etc. use `AVD Extended Controls`:

![](doc/screenshots/ExtendedControls-CarData.png)

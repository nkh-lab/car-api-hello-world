# car-api-hello-world
Android automotive CAR API usage example.
Example based on android.car.hardware.CarSensorManager(deprecated in API 29) and android.car.hardware.CarPropertyManager usage for obtaining speed and gear changing events.

## CAR API usage steps
1. Add android.car library to app/build.gradle:
```
android {
    ...
    useLibrary 'android.car'
}
```
2. Add permissions to manifest AndroidManifest.xml:
```
<uses-permission android:name="android.car.permission.CAR_SPEED" />
<uses-permission android:name="android.car.permission.CAR_POWERTRAIN" />
```
3. Add listeners to android.car.hardware.CarSensorManager in MainActivity.java

## Emulator/AVD
Polestar 2 API 28 ([how to setup link](https://stackoverflow.com/questions/57968790/android-automotive-emulator-no-system-images-installed-for-this-target/58159715#58159715))

## Logcat expected output
```
...
2020-10-02 17:04:06.673 22378-22378/com.example.carapihelloworld D/MainActivity: CarPropertyManager.CurrentGear: 4
2020-10-02 17:04:06.690 22378-22378/com.example.carapihelloworld D/MainActivity: CarSensorManager.onSpeedChanged: 6.7055836
2020-10-02 17:04:06.690 22378-22378/com.example.carapihelloworld D/MainActivity: CarSensorManager.onGearChanged: 1
2020-10-02 17:04:06.690 22378-22378/com.example.carapihelloworld D/MainActivity: CarPropertyManager.onGearChanged: 4
2020-10-02 17:04:06.690 22378-22378/com.example.carapihelloworld D/MainActivity: CarPropertyManager.onSpeedChanged: 6.7055836
2020-10-02 17:04:22.159 22378-22378/com.example.carapihelloworld D/MainActivity: CarPropertyManager.onSpeedChanged: 6.2585444
2020-10-02 17:04:39.531 22378-22378/com.example.carapihelloworld D/MainActivity: CarSensorManager.onGearChanged: 2
2020-10-02 17:04:55.746 22378-22378/com.example.carapihelloworld D/MainActivity: CarPropertyManager.onSpeedChanged: 3.0555558
2020-10-02 17:04:55.747 22378-22378/com.example.carapihelloworld D/MainActivity: CarSensorManager.onSpeedChanged: 3.0555558
...
```
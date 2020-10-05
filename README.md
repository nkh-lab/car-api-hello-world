# car-api-hello-world
CAR API usage example.
Example based on android.car.hardware.CarSensorManager usage for obtaining speed and gear changing events.

## Usage steps
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
2020-10-02 09:06:40.224 17090-17090/com.example.carapihelloworld D/MainActivity: onSpeedChanged: 1.388889
2020-10-02 09:06:40.224 17090-17090/com.example.carapihelloworld D/MainActivity: onGearChanged: 4
```
# Android Location Tracker
This is a library that helps track one's location in an Android app. It makes use of the Android Architecture Components.

## Installation
Add this to your project `build.gradle` file:
```gradle
allprojects {
 repositories {
    jcenter()
    maven { url 'https://maven.google.com' }
    maven { url "https://jitpack.io" }
 }
}
```
and in your `app/build.gradle`:
```gradle
dependencies {
    compile 'com.github.vince-nyanga:android-location-tracker:1.0.0-alpha6'
}
```


## Usage:
Add the following permissions to you Manifest:
```xml
 <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
```
## Tracking
### 1. Create Tracker
```java
 LocationTracker locationTracker = new LocationTracker.Builder(this)
                .setPriority(LocationTracker.PRIORITY_HIGH)
                .setDisplacement(150)
                .setInterval(60000)
                .build();
```
### 2. Start tracking
```java
locationTracker.start();
```
### 3. Observe
`LocationTracker` extends `LiveData` so you have to observe its changes inside a `LifeCycleOwner`.
```java
locationTracker.observe(this, new Observer<Resource<Location>>() {
            @Override
            public void onChanged(@Nullable Resource<Location> locationResource) {
               if (locationResource.status == Status.SUCCESS) {
                        double lat = locationResource.value.getLatitude();
                        double lon = locationResource.value.getLongitude();
                }
                if (locationResource.status == Status.ERROR) {
                        
                  String message = locationResource.message;
                  // handle error
                }
                if (locationResource.status == Status.LOADING) {

                      // update UI
                }
            }
            
        });
```
### 4. Stop tracking
If you no longer want to receive location updates call `locationTracker.stop()`

## Get last known location
If you want to get the last known location call `locationTracker.getLastKnownLocation()`.

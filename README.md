# Android Location Tracker
This is a library that helps track one's location in an Android app. It makes use of the Android Architecture Components.

## Installation
Add this to your project `build.gradle` file:
```gradle
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```
and:
```gradle
dependencies {
    compile 'com.github.vince-nyanga:android-location-tracker:1.0.0-alpha2'
}
```


## Usage:

### Create
```java
 LocationTracker locationTracker = new LocationTrackerBuilder(this)
                .setPriority(LocationTracker.PRIORITY_HIGH)
                .setDisplacement(150)
                .setInterval(60000)
                .build();
```

### Observe
`LocationTracker` extends `LiveData` so you have to observe its changes inside a `LifeCycleOwner`.
```java
locationTracker.observe(this, new Observer<Resource<Location>>() {
            @Override
            public void onChanged(@Nullable Resource<Location> locationResource) {
               if (locationResource.status == Status.SUCCESS) {
                        double lat = locationResource.value.getLatitude();
                        double lon = locationResource.value.getLongitude();
                    }
            }
        });
```

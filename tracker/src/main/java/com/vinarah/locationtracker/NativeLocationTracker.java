package com.vinarah.locationtracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.vinarah.locationtracker.vo.Resource;

/**
 * @author Vincent
 * @since 2017/08/14
 */

class NativeLocationTracker extends LocationTracker
        implements LocationListener {

    private final float minDistance;

    private final long minTime;



    private final LocationManager locationManager;

    NativeLocationTracker(Context context, long minTime, float minDistance){
        locationManager = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
        this.minDistance = minDistance;
        this.minTime = minTime;
    }


    @Override
    @SuppressWarnings({"MissingPermission"})
    public void start() {
        super.start();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,
                this);
    }

    @Override
    protected void disconnect() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        setValue(Resource.success(location));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        setValue(Resource.<Location>error(s + " disabled"));
    }
}

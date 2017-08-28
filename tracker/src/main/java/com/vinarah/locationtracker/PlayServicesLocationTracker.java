package com.vinarah.locationtracker;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.vinarah.locationtracker.vo.Resource;

import static com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

/**
 * @author Vincent
 * @since 2017/08/14
 */

class PlayServicesLocationTracker extends LocationTracker implements GoogleApiClient
        .OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {
    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;


    PlayServicesLocationTracker(@NonNull Context context, long interval, int displacement,
                                @Priority int priority) {

        googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API)
                .addOnConnectionFailedListener(this).addConnectionCallbacks(this).build();

        locationRequest = new LocationRequest().setInterval(interval).setSmallestDisplacement
                (displacement);
        switch (priority) {
            case PRIORITY_HIGH:
                locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);
                break;
            case PRIORITY_POWER_SAVER:
                locationRequest.setPriority(PRIORITY_BALANCED_POWER_ACCURACY);
                break;
            case PRIORITY_LOW_POWER:
                locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
                break;
            default:
                locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);
                break;
        }
    }

    @Override
    public void start(){
        super.start();
        googleApiClient.connect();
    }

    @Override
    protected void disconnect(){
        if(googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        setValue(Resource.<Location>error(connectionResult.getErrorMessage()));
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onConnected(@Nullable Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        setValue(Resource.success(location));
    }
}

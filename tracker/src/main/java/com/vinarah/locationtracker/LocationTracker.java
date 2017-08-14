package com.vinarah.locationtracker;

import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.IntDef;
import android.util.Log;

import com.vinarah.locationtracker.vo.Resource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Vincent
 * @since 2017/08/08
 */


public class LocationTracker extends LiveData<Resource<Location>> {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PRIORITY_HIGH, PRIORITY_POWER_SAVER, PRIORITY_LOW_POWER})
    public @interface Priority {
    }
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_POWER_SAVER = 2;
    public static final int PRIORITY_LOW_POWER = 3;

    private boolean tracking;

    public void start() {
        tracking = true;
        setValue(Resource.<Location>loading());
    }

    public void stop() {
        disconnect();
        tracking = false;
    }


    protected void disconnect(){

    }

    @Override
    protected void onActive() {
        Log.d("LocationTracker", "onActive: tracking = "+tracking);
        if(tracking){
            start();
        }
    }

    @Override
    protected void onInactive() {
        disconnect();
    }


}


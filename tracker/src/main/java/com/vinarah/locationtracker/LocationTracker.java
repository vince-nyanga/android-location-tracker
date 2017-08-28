package com.vinarah.locationtracker;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresPermission;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.vinarah.locationtracker.vo.Resource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Vincent
 * @since 2017/08/08
 */


@SuppressWarnings("WeakerAccess")
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
        if(tracking){
            start();
        }
    }

    @Override
    protected void onInactive() {
        disconnect();
    }



    @SuppressWarnings("unused")
    public static class Builder {
        private static final long DEFAULT_INTERVAL = 300000;

        private static final int DEFAULT_DISPLACEMENT = 500;

        private static final
        @LocationTracker.Priority
        int DEFAULT_PRIORITY = LocationTracker.PRIORITY_HIGH;

        private Context context;

        private long interval = DEFAULT_INTERVAL;

        private int displacement = DEFAULT_DISPLACEMENT;

        private
        @LocationTracker.Priority
        int priority = DEFAULT_PRIORITY;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Sets the location update interval. Default value is 5 minutes.
         */
        @SuppressWarnings("SameParameterValue")
        public Builder setInterval(long interval) {
            this.interval = interval;
            return this;
        }

        /**
         * Sets the minimum displacement in meters that should trigger location update. Default value
         * is 500 meters.
         *
         * @param displacement minimum displacement in meters
         */
        @SuppressWarnings("SameParameterValue")
        public Builder setDisplacement(int displacement) {
            this.displacement = displacement;
            return this;
        }

        /**
         * Sets the priority for the location request.
         * <p>
         * Priority can be:
         * <li>PRIORITY_HIGH for precise location updates</li>
         * <li>PRIORITY_POWER_SAVER for balancing between power and accuracy</li>
         * <li>PRIORITY_LOW_POWER for low power usage</li>
         *
         * @param priority Priority
         */
        public Builder setPriority(@LocationTracker.Priority int priority) {
            this.priority = priority;
            return this;
        }


        @SuppressLint("MissingPermission")
        @RequiresPermission(
                anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission" +
                        ".ACCESS_FINE_LOCATION"}
        )
        public LocationTracker build() {
            if(isGooglePlayServicesAvailable()) {
                return new PlayServicesLocationTracker(context, interval, displacement, priority);
            }else{
                return  new NativeLocationTracker(context,interval,displacement);
            }
        }

        private boolean isGooglePlayServicesAvailable(){
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            int result = googleAPI.isGooglePlayServicesAvailable(context);
            return result == ConnectionResult.SUCCESS;
        }
    }


}


package com.vinarah.locationtracker;

import android.content.Context;
import android.support.annotation.RequiresPermission;

@SuppressWarnings("unused")
public class LocationTrackerBuilder {

    private static final long DEFAULT_INTERVAL = 300000;

    private static final int DEFAULT_DISPLACEMENT = 100;

    private static final
    @LocationTracker.Priority
    int DEFAULT_PRIORITY = LocationTracker.PRIORITY_HIGH;

    private Context context;

    private long interval = DEFAULT_INTERVAL;

    private int displacement = DEFAULT_DISPLACEMENT;

    private
    @LocationTracker.Priority
    int priority = DEFAULT_PRIORITY;

    public LocationTrackerBuilder(Context context) {
        this.context = context;
    }

    public LocationTrackerBuilder setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    public LocationTrackerBuilder setDisplacement(int displacement) {
        this.displacement = displacement;
        return this;
    }

    public LocationTrackerBuilder setPriority(@LocationTracker.Priority int priority){
        this.priority  = priority;
        return this;
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission" +
                    ".ACCESS_FINE_LOCATION"}
    )
    public LocationTracker build() {
        return new LocationTracker(context, interval, displacement, priority);
    }


}
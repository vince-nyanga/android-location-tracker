package com.vinarah.locationtracker;

import android.content.Context;
import android.support.annotation.RequiresPermission;

@SuppressWarnings("unused")
public class LocationTrackerBuilder {

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

    public LocationTrackerBuilder(Context context) {
        this.context = context;
    }

    /**
     * Sets the location update interval. Default value is 5 minutes.
     */
    public LocationTrackerBuilder setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Sets the minimum displacement in meters that should trigger location update. Default value
     * is 500 meters.
     *
     * @param displacement
     * @return
     */
    public LocationTrackerBuilder setDisplacement(int displacement) {
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
     * @param priority
     * @return
     */
    public LocationTrackerBuilder setPriority(@LocationTracker.Priority int priority) {
        this.priority = priority;
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
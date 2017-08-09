package com.scadsoftware.locationtracker;

import android.Manifest;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scadsoftware.locationtracker.databinding.ActivityMainBinding;
import com.vinarah.locationtracker.LocationTracker;
import com.vinarah.locationtracker.LocationTrackerBuilder;
import com.vinarah.locationtracker.vo.Resource;
import com.vinarah.locationtracker.vo.Status;

public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry registry = new LifecycleRegistry(this);

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        subscribeToLocationChanges();
    }

    private void subscribeToLocationChanges() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        LocationTracker locationTracker = new LocationTrackerBuilder(this)
                .build();
        locationTracker.observe(this, new Observer<Resource<Location>>() {
            @Override
            public void onChanged(@Nullable Resource<Location> locationResource) {
                if (locationResource != null) {
                    binding.setResource(locationResource);
                    if (locationResource.status == Status.SUCCESS) {
                        binding.setLatitude("" + locationResource.value.getLatitude());
                        binding.setLongitude("" + locationResource.value.getLongitude());
                    }
                }
            }
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return registry;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                subscribeToLocationChanges();
            }
        }
    }
}

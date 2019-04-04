package ru.nutscoon.mapsproject.Screens;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ru.nutscoon.mapsproject.Models.OrganizationsOnMapData;
import ru.nutscoon.mapsproject.R;
import ru.nutscoon.mapsproject.ViewModels.MainViewModel;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);

        setupViewModel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewModel.ready();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng initPos = new LatLng(54.203946, 37.621329);
        CameraPosition cameraPosition = CameraPosition.builder().target(initPos).zoom(12f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setupViewModel(){
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getOrganizationsLiveData().observe(this, new Observer<List<OrganizationsOnMapData>>() {
            @Override
            public void onChanged(@Nullable List<OrganizationsOnMapData> organizationsOnMapData) {
                if(organizationsOnMapData != null){
                    addOrganizationsOnMap(organizationsOnMapData);
                }
            }
        });
    }

    private void addOrganizationsOnMap(List<OrganizationsOnMapData> organizations){
        for (OrganizationsOnMapData point : organizations) {
            MarkerOptions p = new MarkerOptions()
                    .position(new LatLng(point.getLat(), point.getLon()))
                    .icon(BitmapDescriptorFactory.fromResource(point.getImg()));
            mMap.addMarker(p);
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(i);
                return false;
            }
        });
    }
}

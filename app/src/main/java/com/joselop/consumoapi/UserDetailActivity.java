package com.joselop.consumoapi;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joselop.consumoapi.model.User;

public class UserDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // Obtiene el usuario desde el Intent
        user = getIntent().getParcelableExtra("user");
        if (user != null) {
            Log.d("UserDetailActivity", "Usuario recibido: " + user.getName().getFirst() + " " + user.getName().getLast());
        } else {
            Log.e("UserDetailActivity", "No se recibió ningún usuario.");
        }

        // Configura el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Obtén la ubicación del usuario
        LatLng userLocation = new LatLng(
                Double.parseDouble(user.getLocation().getCoordinates().getLatitude()),
                Double.parseDouble(user.getLocation().getCoordinates().getLongitude())
        );

        // Añade un marcador en la ubicación del usuario
        googleMap.addMarker(new MarkerOptions().position(userLocation).title(user.getLocation().getCity()));

        // Mueve la cámara a la ubicación del usuario
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10f));
    }
}

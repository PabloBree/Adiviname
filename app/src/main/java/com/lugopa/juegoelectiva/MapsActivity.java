package com.lugopa.juegoelectiva;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lugopa.juegoelectiva.Model.Puntaje;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private ArrayList<Puntaje> lista_puntajes_1 = new ArrayList<Puntaje>();
    private ArrayList<LatLng> listaLatLng = new ArrayList<LatLng>();
    private ArrayList<String> listaNombres = new ArrayList<String>();
    private SupportMapFragment supportMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
       supportMapFragment.getMapAsync(this);


        // System.out.println(listaNombres.get(2));
//        Toast.makeText(getApplicationContext(),listaNombres.get(0), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),listaNombres.get(1), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),listaNombres.get(2), Toast.LENGTH_SHORT).show();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void obtenerListaPuntajesBD(){
        DatabaseReference nodoUsuarios = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        nodoUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // OBTENEMOS LOS DATOS DEL PUNTAJE DE LA BASE DE DATOS
                    Double lat, lon;
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Puntaje p = ds.getValue(Puntaje.class);
                        lat = p.getLatitud();
                        lon = p.getLongitud();
                        LatLng latLng = new LatLng(lat, lon);
                        listaLatLng.add(latLng);
                        listaNombres.add(p.getNombre());
                    }
                    for(int i=0; i<listaLatLng.size(); i++){  // ESTE FOR PUEDE EXTRAERSE EN UNA FUNCION
                        mMap.addMarker(new MarkerOptions().position(listaLatLng.get(i)).title(listaNombres.get(i)));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(listaLatLng.get(i)));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR REC. FIREBASE ", "ERROR - No se pudieron recuperar los datos de Firebase");
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        obtenerListaPuntajesBD();

    }

}
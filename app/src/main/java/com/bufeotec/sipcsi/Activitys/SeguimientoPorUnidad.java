package com.bufeotec.sipcsi.Activitys;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class SeguimientoPorUnidad extends AppCompatActivity implements OnMapReadyCallback {

    static GoogleMap mMap;
    static String idvehiculo;
    static String tok;
    String placa;
    Double lat,lon;
    static DataConnection dc;
    static Context context;
    Vehiculos vehiculos;
    public static ArrayList<Vehiculos> arrayUbicacion ;
    boolean run = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimientoporunidad);
        context=this;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_Seguimiento_por_unidad);
        mapFragment.getMapAsync(this);
        idvehiculo= getIntent().getExtras().getString("id_vehiculo");
        lat= Double.valueOf(getIntent().getExtras().getString("lat"));
        lon= Double.valueOf(getIntent().getExtras().getString("long"));
        tok= getIntent().getExtras().getString("tok");
        placa= getIntent().getExtras().getString("placa");

        showToolbar(placa,true);
        ejecutarCadaTiempo();



    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng punto = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(punto).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(punto));

        vehiculos = new Vehiculos();
        vehiculos.setId_vehiculo(idvehiculo);
        vehiculos.setTok(tok);

        dc = new DataConnection(this,"ubicacionVehiculo",vehiculos,false);
        new SeguimientoPorUnidad.GetUbicacion().execute();
    }

    public static  class GetUbicacion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayUbicacion= dc.getListaUbicacion();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            CargarPuntosAMapa();
        }
    }


    public static void CargarPuntosAMapa() {

        mMap.clear();

        if (arrayUbicacion.size() > 0) {

            LatLng ultpos = null;
            for (int i = 0; i < arrayUbicacion.size(); i++) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng((Double.parseDouble(arrayUbicacion.get(i).getLatitud()))
                        , (Double.parseDouble(arrayUbicacion.get(i).getLongitud()))))
                        .title(arrayUbicacion.get(i).getPlaca());
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                Marker marcador_ = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng((Double.parseDouble(arrayUbicacion.get(i).getLatitud()))
                                , (Double.parseDouble(arrayUbicacion.get(i).getLongitud()))))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(arrayUbicacion.get(i).getPlaca()+ " - " +arrayUbicacion.get(i).getFecha())

                );
                marcador_.showInfoWindow();
                //  marcador.setTag(i);

                ultpos = new LatLng((Double.parseDouble(arrayUbicacion.get(i).getLatitud()))
                        , (Double.parseDouble(arrayUbicacion.get(i).getLongitud())));
            }

               VolverPosicion(ultpos);



        } else {
            Toast.makeText(context, "Lo sentimos, no tenemos puntos en estos momentos", Toast.LENGTH_SHORT).show();
        }
        //mMap.setOnMarkerClickListener(this);
    }

    private static  void VolverPosicion(LatLng miLatLng) {

        //     mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        CameraPosition camPos = new CameraPosition.Builder()
                .target(miLatLng)   //Centramos el mapa en Madrid
                .zoom(16)         //Establecemos el zoom en 16
                .bearing(45)      //Establecemos la orientación con el noreste arriba
                .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                .build();

        CameraUpdate miUbicacion = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(miUbicacion);

    }


    private   void ejecutarCadaTiempo(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vehiculos = new Vehiculos();
                vehiculos.setId_vehiculo(idvehiculo);
                vehiculos.setTok(tok);

                if (!run) {
                    Log.i("funciones", "fun uno");
                    dc = new DataConnection(this, "ubicacionVehiculo", vehiculos, false);
                    new SeguimientoPorUnidad.GetUbicacion().execute();

                }else{
                    handler.removeCallbacks(this);
                }
                //CargarPuntosAMapa();//llamamos nuestro metodo
                handler.postDelayed(this,4000);//se ejecutara cada 10 segundos
            }
        },10000);//empezara a ejecutarse después de 5 milisegundos
    }


    @Override
    protected void onStop() {
        run=true;
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

    }
}

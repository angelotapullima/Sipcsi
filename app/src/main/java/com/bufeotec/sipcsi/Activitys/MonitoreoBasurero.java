package com.bufeotec.sipcsi.Activitys;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.bufeotec.sipcsi.Models.Puntos;
import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MonitoreoBasurero extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MoniBasurero";
    DataConnection dc, dc2;
    GoogleMap mMap;
    boolean run = false;
    Marker marcador_;
    Vehiculos vehiculos;
    boolean valor = false;
    public ArrayList<Vehiculos> listaBasureros;
    public  ArrayList<Puntos> listPoints;
    Preferences pref;
    public  float v;
    public  double lat, lng,lati,longi;
    public LatLng startPosition;
    public  LatLng endPosition;
    public  boolean isFirstPosition = true;
    public Double startLatitude ,startLongitude;
    String idVehiculo,placa,tok;
    Context context;

    static ArrayList<LatLng> points = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo_basurero);
        context=this;
        idVehiculo=getIntent().getExtras().getString("id_vehiculo");
        //lati= Double.valueOf(getIntent().getExtras().getString("lat"));
        //longi= Double.valueOf(getIntent().getExtras().getString("long"));
        tok= getIntent().getExtras().getString("tok");
        placa= getIntent().getExtras().getString("placa");

        pref=new Preferences(context);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_basurero);
        mapFragment.getMapAsync(this);
        ejecutarCadaTiempo();
        showToolbar(placa,true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        vehiculos= new Vehiculos();
        vehiculos.setId_vehiculo(idVehiculo);
        vehiculos.setTok(pref.getNombrePref());
        //LatLng punto = new LatLng(lati, longi);
        //mMap.addMarker(new MarkerOptions().position(punto).title("Mi Posición"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(punto));

        dc2 = new DataConnection(this, "listarRutasBasureros",idVehiculo, false);
        new MonitoreoBasurero.GetPoints().execute();


        dc = new DataConnection(this, "ubicacionVehiculo", vehiculos, false);
        new MonitoreoBasurero.GetBasureros().execute();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        mMap.setMyLocationEnabled(true);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public  class GetBasureros extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaBasureros = dc.getListaUbicacion();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CargarPuntosAMapa();
        }
    }

    public  class GetPoints extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listPoints = dc2.getListaRutasBasureros();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CargarPointsAMapa();
        }
    }


    public void CargarPointsAMapa() {


        if (listPoints.size() > 0) {

            points = new ArrayList<>();
            for (int i = 0; i < listPoints.size(); i++) {

                double lat = Double.parseDouble(listPoints.get(i).getLat());
                double lng = Double.parseDouble(listPoints.get(i).getLongitud());
                LatLng position = new LatLng(lat, lng);

                points.add(position);


            }
            Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(points));
            polyline.setColor(Color.RED);


        }
    }


    private void startBikeAnimation(final LatLng start, final LatLng end) {

        Log.e(TAG, "startBikeAnimation called...");

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                Log.e(TAG, "Car Animation Started...");
                v = valueAnimator.getAnimatedFraction();
                lng = v * end.longitude + (1 - v)
                        * start.longitude;
                lat = v * end.latitude + (1 - v)
                        * start.latitude;

                LatLng newPos = new LatLng(lat, lng);
                marcador_.setPosition(newPos);
                marcador_.setAnchor(0.5f, 0.5f);
                marcador_.setRotation(getBearing(start, end));

                // todo : Shihab > i can delay here
                /*mMap.moveCamera(CameraUpdateFactory
                        .newCameraPosition
                                (new CameraPosition.Builder()
                                        .target(newPos)
                                        .zoom(15.5f)
                                        .build()));*/

                startPosition = marcador_.getPosition();

            }

        });
        valueAnimator.start();
    }

    @Override
    public void onResume() {
        ejecutarCadaTiempo();
        super.onResume();
    }


    public  void CargarPuntosAMapa() {

        String TAG = "mare";
        if (listaBasureros.size() > 0) {

            startLatitude = Double.parseDouble(listaBasureros.get(0).getLatitud());
            startLongitude = Double.parseDouble(listaBasureros.get(0).getLongitud());

            Log.e(TAG, " algo para saber"+startLatitude + "--" + startLongitude);

            if (isFirstPosition) {
                startPosition = new LatLng(startLatitude, startLongitude);

                marcador_ = mMap.addMarker(new MarkerOptions().position(startPosition).
                        flat(true)
                        .title(listaBasureros.get(0).getPlaca() + " " + listaBasureros.get(0).getFecha())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                marcador_.setAnchor(0.5f, 0.5f);

                mMap.moveCamera(CameraUpdateFactory
                        .newCameraPosition
                                (new CameraPosition.Builder()
                                        .target(startPosition)
                                        .zoom(17)
                                        .build()));

                isFirstPosition = false;

            } else {
                endPosition = new LatLng(startLatitude, startLongitude);

                Log.e(TAG, startPosition.latitude + "--" + endPosition.latitude + "--Check --" + startPosition.longitude + "--" + endPosition.longitude);

                if ((startPosition.latitude != endPosition.latitude) || (startPosition.longitude != endPosition.longitude)) {

                    Log.e(TAG, "distinta posición");
                    startBikeAnimation(startPosition, endPosition);

                } else {

                    //startBikeAnimation(startPosition, endPosition);
                    Log.e(TAG, "misma posición");
                }
            }
        }else{
            Log.e(TAG, " no sirves para nada mrd" );
        }



    }

    public static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }


    private  void VolverPosicion(LatLng miLatLng) {

        //     mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        CameraPosition camPos = new CameraPosition.Builder()
                .target(miLatLng)   //Centramos el mapa en Madrid
                .zoom(16)         //Establecemos el zoom en 16
                .bearing(45)      //Establecemos la orientación con el noreste arriba
                .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                .build();

        CameraUpdate miUbicacion = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(miUbicacion);
        marcador_.showInfoWindow();
        valor=true;
    }




    private void ejecutarCadaTiempo(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!run) {
                    Log.i("funciones", "Basurero");
                    dc = new DataConnection(this, "listarRutasBasureros", pref.getDistritoIdPref(), false);
                    new MonitoreoBasurero.GetBasureros().execute();

                }else{
                    handler.removeCallbacks(this);
                }
                //CargarPuntosAMapa();//llamamos nuestro metodo
                handler.postDelayed(this,7000);//se ejecutara cada 10 segundos
            }
        },5000);//empezara a ejecutarse después de 5 milisegundos
    }

    @Override
    public void onStop() {
        run=true;
        super.onStop();
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

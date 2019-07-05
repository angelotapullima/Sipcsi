package com.bufeotec.sipcsi.Fragments;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Models.Puntos;
import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import static io.fabric.sdk.android.Fabric.TAG;


public class MonitoreoBasureroFragment extends Fragment implements OnMapReadyCallback {

    Activity activity;
    Context context;
    DataConnection dc, dc2;
    GoogleMap mMap;
    boolean run = false;
    Marker marcador_;
    boolean valor = false;
    public ArrayList<Vehiculos> listaBasureros;
    public  ArrayList<Puntos> listPoints;
    Preferences pref;
    public  float v;
    public  double lat, lng;
    public  LatLng startPosition;
    public  LatLng endPosition;
    public  boolean isFirstPosition = true;
    public Double startLatitude ,startLongitude;




    private OnFragmentInteractionListener mListener;

    public MonitoreoBasureroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_basurero);
        mapFragment.getMapAsync(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitoreo_basurero, container, false);
        activity = getActivity();
        context = getContext();
        pref = new Preferences(context);
        ejecutarCadaTiempo();
        getActivity().setTitle("Monitoreo Basurero");
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        dc2 = new DataConnection(getActivity(), "listarPoints", false);
        new MonitoreoBasureroFragment.GetPoints().execute();


        dc = new DataConnection(getActivity(), "listarBasureros", pref.getDistritoIdPref(), false);
        new MonitoreoBasureroFragment.GetBasureros().execute();

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        mMap.setMyLocationEnabled(true);


    }

    public  class GetBasureros extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaBasureros = dc.getListaBasureros();
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
            listPoints = dc2.getPuntos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CargarPointsAMapa();
        }
    }

    static ArrayList<LatLng> points = null;

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






        //if (marcador_!= null){ marcador_.remove();     }


        /*int  i = 0 ;
        //if (listaBasureros.size() > 0) {


            LatLng ultpos = null;
            //for (int i = 0; i < listaBasureros.size(); i++) {


                startPosition = new LatLng(Double.parseDouble(listaBasureros.get(i).getLatitud()),Double.parseDouble(listaBasureros.get(i).getLongitud()));
                endPosition = new LatLng(Double.parseDouble(listaBasureros.get(i).getLatitud_antiguo()),Double.parseDouble(listaBasureros.get(i).getLongitud_antiguo()));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng((Double.parseDouble(listaBasureros.get(i).getLatitud()))
                        , (Double.parseDouble(listaBasureros.get(i).getLongitud()))))
                        .title(listaBasureros.get(i).getPlaca());
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                marcador_ = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(listaBasureros.get(i).getLatitud()),Double.parseDouble(listaBasureros.get(i).getLatitud())))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(listaBasureros.get(i).getPlaca() + " " + listaBasureros.get(i).getFecha())

                );








                ultpos = new LatLng((Double.parseDouble(listaBasureros.get(i).getLatitud()))
                        , (Double.parseDouble(listaBasureros.get(i).getLongitud())));

            //}
            marcador_.showInfoWindow();

            if (!valor){
                //VolverPosicion(ultpos);
            }


        //} else {
            //Toast.makeText(activity, "Lo sentimos, no tenemos Unidades de Basura en estos momentos", Toast.LENGTH_SHORT).show();
        //}
        //mMap.setOnMarkerClickListener(this);

        startBikeAnimation(startPosition,endPosition);*/
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
                    dc = new DataConnection(getActivity(), "listarBasureros", pref.getDistritoIdPref(), false);
                    new MonitoreoBasureroFragment.GetBasureros().execute();

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
}

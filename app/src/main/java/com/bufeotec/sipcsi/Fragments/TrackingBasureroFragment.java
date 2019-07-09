package com.bufeotec.sipcsi.Fragments;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Activitys.MonitoreoBasurero;
import com.bufeotec.sipcsi.Models.Puntos;
import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bufeotec.sipcsi.Fragments.SeguimientoATodos.stringRequest;
import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;


public class TrackingBasureroFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "Tracking Basurero";
    Activity activity;
    GoogleMap mMap;
    Vehiculos vehiculos;
    Context context;
    Button empezar_ruta;
    boolean run = false;
    Marker marcador_;
    boolean valor = false;
    public ArrayList<Vehiculos> listaBasureros;
    public  ArrayList<Puntos> listPoints;
    Preferences pref;
    public  float v;
    public  double lat, lng;
    public LatLng startPosition;
    public  LatLng endPosition;
    public  boolean isFirstPosition = true;
    public Double startLatitude ,startLongitude;
    static ArrayList<LatLng> points = null;

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public TrackingBasureroFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_tracking_basura);
        mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracking_basurero, container, false);
        activity=getActivity();
        context=getContext();
        pref = new Preferences(context);
        activity.setTitle("Monitoreo Basurero");
        empezar_ruta=view.findViewById(R.id.empezar_ruta);
        empezar_ruta.setOnClickListener(this);
        return  view;
    }



    DataConnection dc,dc2;

    @Override
    public void onClick(View v) {

        if (v.equals(empezar_ruta)){

            notificacionInicioDeRuta(pref.getVehiculoPref());
            if (empezar_ruta.getText().toString().equals("Terminar Ruta")){
                empezar_ruta.setBackgroundColor(Color.BLUE);
                empezar_ruta.setTextColor(Color.WHITE);
                empezar_ruta.setText("Empezar Ruta");
            }else{
                empezar_ruta.setBackgroundColor(Color.RED);
                empezar_ruta.setTextColor(Color.WHITE);
                empezar_ruta.setText("Terminar Ruta");
            }

        }
    }


    StringRequest stringRequest;
    private void notificacionInicioDeRuta(final String vehiculoID) {
        String url ="https://"+IP+"/index.php?c=Vehiculo&a=notificar_inicio_basurero&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("1")){

                } else {
                    //Toast.makeText(context,"la fruta ",Toast.LENGTH_SHORT).show();
                    Log.e("noti_inicio_ruta:",""+response);
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_vehiculo",vehiculoID);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        vehiculos= new Vehiculos();
        vehiculos.setId_vehiculo(pref.getVehiculoPref());
        vehiculos.setTok(pref.getNombrePref());

        dc2 = new DataConnection(getActivity(), "listarRutasBasureros",pref.getVehiculoPref(), false);
        new TrackingBasureroFragment.GetPoints().execute();

        dc = new DataConnection(getActivity(), "ubicacionVehiculo", vehiculos, false);
        new TrackingBasureroFragment.GetBasureros().execute();

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
            listaBasureros = dc.getListaUbicacion();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CargarPuntosAMapa();
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
                    dc = new DataConnection(this, "ubicacionVehiculo", pref.getVehiculoPref(), false);
                    new TrackingBasureroFragment.GetBasureros().execute();

                }else{
                    handler.removeCallbacks(this);
                }
                //CargarPuntosAMapa();//llamamos nuestro metodo
                handler.postDelayed(this,10000);//se ejecutara cada 10 segundos
            }
        },5000);//empezara a ejecutarse después de 5 milisegundos
    }

    @Override
    public void onStop() {
        run=true;
        super.onStop();
    }
}

package com.bufeotec.sipcsi.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
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

import com.android.volley.toolbox.StringRequest;
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

import java.util.ArrayList;


public class MonitoreoBasureroFragment extends Fragment implements OnMapReadyCallback {

    Activity activity;
    Context context;
    static DataConnection dc;
    static GoogleMap mMap;
    boolean run = false;
    static Marker marcador_;
    static boolean valor = false;
    public static ArrayList<Vehiculos> listaBasureros;
    Preferences pref;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MonitoreoBasureroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonitoreoBasureroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonitoreoBasureroFragment newInstance(String param1, String param2) {
        MonitoreoBasureroFragment fragment = new MonitoreoBasureroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_basurero);
        mapFragment.getMapAsync(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitoreo_basurero, container, false);
        activity = getActivity();
        context = getContext();
        pref=new Preferences(context);
        ejecutarCadaTiempo();
        getActivity().setTitle("Monitoreo Basurero");
        return  view;
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




        dc = new DataConnection(getActivity(),"listarBasureros",pref.getDistritoIdPref(),false);
        new MonitoreoBasureroFragment.GetBasureros().execute();

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        mMap.setMyLocationEnabled(true);

    }

    public static class GetBasureros extends AsyncTask<Void, Void, Void> {

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

    public static void CargarPuntosAMapa() {

        mMap.clear();

        if (listaBasureros.size() > 0) {

            LatLng ultpos = null;
            for (int i = 0; i < listaBasureros.size(); i++) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng((Double.parseDouble(listaBasureros.get(i).getLatitud()))
                        , (Double.parseDouble(listaBasureros.get(i).getLongitud()))))
                        .title(listaBasureros.get(i).getPlaca());
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                marcador_ = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng((Double.parseDouble(listaBasureros.get(i).getLatitud()))
                                , (Double.parseDouble(listaBasureros.get(i).getLongitud()))))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(listaBasureros.get(i).getPlaca() + " " + listaBasureros.get(i).getFecha())

                );
                //marcador_.showInfoWindow();

                //  marcador.setTag(i);

                ultpos = new LatLng((Double.parseDouble(listaBasureros.get(i).getLatitud()))
                        , (Double.parseDouble(listaBasureros.get(i).getLongitud())));
            }marcador_.showInfoWindow();

            if (!valor){
                VolverPosicion(ultpos);
            }


        } else {
            //Toast.makeText(activity, "Lo sentimos, no tenemos Unidades de Basura en estos momentos", Toast.LENGTH_SHORT).show();
        }
        //mMap.setOnMarkerClickListener(this);
    }

    private static void VolverPosicion(LatLng miLatLng) {

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
                handler.postDelayed(this,10000);//se ejecutara cada 10 segundos
            }
        },20000);//empezara a ejecutarse después de 5 milisegundos
    }

    @Override
    public void onStop() {
        run=true;
        super.onStop();
    }
}

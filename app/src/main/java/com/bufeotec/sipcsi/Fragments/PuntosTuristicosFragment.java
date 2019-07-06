package com.bufeotec.sipcsi.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bufeotec.sipcsi.Models.Puntos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bumptech.glide.Glide;
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

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;


public class PuntosTuristicosFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    Activity activity;
    Context context;
    GoogleMap mMap;
    DataConnection dc;
    Marker marcador_;
    ImageView Timagen;
    View bottomSheet;
    LinearLayout tapactionlayout;
    private BottomSheetBehavior mBottomSheetBehavior1;
    LinearLayout botomPuntos, botomTurismo,botomAlertas;
    TextView Tnombre ,Tdescripcion,Tdireccion;

    public ArrayList<Puntos> listaTuristicos;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PuntosTuristicosFragment() {
        // Required empty public constructor
    }


    public static PuntosTuristicosFragment newInstance(String param1, String param2) {
        PuntosTuristicosFragment fragment = new PuntosTuristicosFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_puntos_turisticos, container, false);
        activity = getActivity();
        context = getContext();
        getActivity().setTitle("Puntos Turísticos");


        View headerLayout1 = view.findViewById(R.id.bottomJsoft);
        tapactionlayout = (LinearLayout) view.findViewById(R.id.tap_action_layout);

        bottomSheet = view.findViewById(R.id.bottomJsoft);
        Tnombre=view.findViewById(R.id.Tnombre);
        Timagen=view.findViewById(R.id.Timagen);
        Tdescripcion=view.findViewById(R.id.Tdescripcion);
        Tdireccion=view.findViewById(R.id.Tdireccion);
        botomPuntos=view.findViewById(R.id.botomPuntos);
        botomTurismo=view.findViewById(R.id.botomTurismo);
        botomAlertas=view.findViewById(R.id.botomAlertas);

        botomTurismo.setVisibility(View.VISIBLE);
        botomAlertas.setVisibility(View.GONE);
        botomPuntos.setVisibility(View.GONE);

        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior1.setPeekHeight(80);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tapactionlayout.setVisibility(View.GONE);
                }
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tapactionlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        tapactionlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior1.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_puntos_turisticos);
        mapFragment.getMapAsync(this);



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




        dc = new DataConnection(getActivity(),"listarTuristicos",false);
        new PuntosTuristicosFragment.GetTuristicos().execute();

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        mMap.setMyLocationEnabled(true);

    }

    public  class GetTuristicos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaTuristicos = dc.getListturisticos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CargarPuntosAMapa();
        }
    }

    public void CargarPuntosAMapa() {

        mMap.clear();

        if (listaTuristicos.size() > 0) {

            LatLng ultpos = null;
            for (int i = 0; i < listaTuristicos.size(); i++) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng((Double.parseDouble(listaTuristicos.get(i).getLat()))
                        , (Double.parseDouble(listaTuristicos.get(i).getLongitud()))))
                        .title(listaTuristicos.get(i).getNombre());
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                marcador_ = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng((Double.parseDouble(listaTuristicos.get(i).getLat()))
                                , (Double.parseDouble(listaTuristicos.get(i).getLongitud()))))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(listaTuristicos.get(i).getNombre())

                );
                //marcador_.showInfoWindow();

                marcador_.setTag(listaTuristicos.get(i));

                ultpos = new LatLng((Double.parseDouble(listaTuristicos.get(i).getLat()))
                        , (Double.parseDouble(listaTuristicos.get(i).getLongitud())));
            }marcador_.showInfoWindow();


                VolverPosicion(ultpos);



        } else {
            Toast.makeText(activity, "Lo sentimos, no tenemos Puntos turisticos en estos momentos", Toast.LENGTH_SHORT).show();
        }
        mMap.setOnMarkerClickListener(this);
    }

    private void VolverPosicion(LatLng miLatLng) {

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
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marcador_.showInfoWindow();
        try {
            Integer clickCount = (Integer) marker.getTag();
            //  return true;

        } catch (Exception ex) {
            Puntos puntos ;
            puntos = (Puntos) marker.getTag();

            Tnombre.setText(puntos.getNombre());
            Tdescripcion.setText(puntos.getDescripcion());
            Tdireccion.setText(puntos.getDireccion());
            //UniversalImageLoader.setImage("http://"+IP+"/"+ puntos.getImagen(),Timagen,null);
            Glide.with(context)
                  .load("http://"+IP+"/"+ puntos.getImagen())//Config.URL_IMAGES_PUBLIC + url__img+".jpg")
                    //.crossFade()
                    //.centerCrop()
                    //.placeholder(R.drawable.placeholder)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(Timagen);

            //txtHo.setText(puntos.getHora());
            //, txtDireccion, txtHorario
            mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);

        }
        return false;
    }
}

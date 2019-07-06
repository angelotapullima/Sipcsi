package com.bufeotec.sipcsi.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.Puntos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.UniversalImageLoader;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.bufeotec.sipcsi.Services.FireBaseMessaging.TAG;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    // location last updated time


    Activity activity;
    Context context;
    GoogleMap mMap;
    String dirImagen ="http://www.guabba.com/accidentestransito/media/loader.gif";
    Double lts, los;
    Marker marcador_;
    Puntos obj;
    FrameLayout FrameGif;
    ImageView gif;
    public  ArrayList<Puntos> arrayPuntos ;
    View bottomSheet;
    CheckBox chAccidentes,chDelitos,chBasura,chVial,chCamaras;
    TextView tfinal,closeFiltro;
    Button filtro   ;
    String finicio,feinal;
    int dia,mes,ano;
    LinearLayout botomPuntos, botomTurismo,botomAlertas;
    LinearLayout tapactionlayout;
    private BottomSheetBehavior mBottomSheetBehavior1;

    boolean accidentes = true, delitos=true , basura=true , vial = true, camaras=true ;
    TextView txtNombre ,txtdes,txtfe ,txtHo,tinicio;



    static DataConnection dc;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapsFragment() {
        // Required empty public constructor
    }



    public static MapsFragment newInstance(String param1, String param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_maps, container, false);

        activity = getActivity();
        context = getContext();
        getActivity().setTitle("Mapa de incidencias");
        gif=view.findViewById(R.id.gif);
        universalImageLoader = new UniversalImageLoader(context);
        FrameGif=view.findViewById(R.id.FrameGif);







        View headerLayout1 = view.findViewById(R.id.bottomJsoft);
        tapactionlayout = (LinearLayout) view.findViewById(R.id.tap_action_layout);

        bottomSheet = view.findViewById(R.id.bottomJsoft);
        txtNombre=view.findViewById(R.id.txtNombre);
        txtdes=view.findViewById(R.id.txtdes);
        txtHo=view.findViewById(R.id.txtHo);
        txtfe=view.findViewById(R.id.txtfe);
        botomPuntos=view.findViewById(R.id.botomPuntos);
        botomTurismo=view.findViewById(R.id.botomTurismo);
        botomAlertas=view.findViewById(R.id.botomAlertas);

        botomTurismo.setVisibility(View.GONE);
        botomAlertas.setVisibility(View.GONE);
        botomPuntos.setVisibility(View.VISIBLE);
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior1.setPeekHeight(70);
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

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( activity,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String tokenNuevo = instanceIdResult.getToken();
                Log.d(TAG, "token: " + tokenNuevo);




            }
        });




        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.mapa,menu); // TU MENU
    }

    private void Filtro() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_filtro);
        chAccidentes=dialog.findViewById(R.id.chAccidentes);
        chDelitos=dialog.findViewById(R.id.chDelitos);
        chBasura=dialog.findViewById(R.id.chBasura);
        chVial=dialog.findViewById(R.id.chVial);
        chCamaras=dialog.findViewById(R.id.chCamaras);
        closeFiltro=dialog.findViewById(R.id.closeFiltro);
        tinicio=dialog.findViewById(R.id.tinicio);
        tfinal=dialog.findViewById(R.id.tfinal);
        filtro=dialog.findViewById(R.id.filtro);
        dialog.setCancelable(true);


        tfinal.setText(feinal);
        tinicio.setText(finicio);
        if (basura){
            chBasura.setChecked(true);
        }if (accidentes){
            chAccidentes.setChecked(true);
        }if (delitos){
            chDelitos.setChecked(true);
        }if (vial){
            chVial.setChecked(true);
        }if (camaras){
            chCamaras.setChecked(true);
        }


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);

        closeFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        tinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                showDatePickerDialog(tinicio);

            }
        });

        tfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                showDatePickerDialog(tfinal);

            }
        });
        filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                if (chAccidentes.isChecked()){
                    accidentes=true;
                }else{
                    accidentes=false;
                }

                if (chDelitos.isChecked()){
                    delitos=true;
                }else{
                    delitos=false;
                }

                if (chBasura.isChecked()){
                    basura=true;
                }else{
                    basura=false;
                }

                if (chVial.isChecked()){
                    vial=true;
                }else{
                    vial=false;
                }

                if (chCamaras.isChecked()){
                    camaras=true;
                }else{
                    camaras=false;
                }

                actualizar(delitos,basura,vial,camaras);
                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filtroMenu: // TU OPCION
                Filtro();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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


    UniversalImageLoader universalImageLoader;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        LocationServices.getFusedLocationProviderClient(context).getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                          @Override
                                          public void onSuccess(Location location) {
                                              if (location!= null){
                                              lts = location.getLatitude();
                                              los= location.getLongitude();
                                              LatLng Ubica = new LatLng(lts, los);
                                                 Marker mPosition = mMap.addMarker(new MarkerOptions()
                                                          .position(Ubica)
                                                          .draggable(true)
                                                          .icon(BitmapDescriptorFactory.fromResource(R.drawable.mar))
                                                          .title("Mi Posición"));

                                                  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                                          new LatLng(mPosition.getPosition().latitude,
                                                                  mPosition.getPosition().longitude), 15));
                                          }
                                          }

                                      });


        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        feinal=dateFormat.format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.MONTH, -1);


        finicio=dateFormat.format(calendar.getTime());


        obj= new Puntos();
        obj.setAccidentes(String.valueOf(accidentes));
        obj.setDelitos(String.valueOf(delitos));
        obj.setBasura(String.valueOf(basura));
        obj.setVial(String.valueOf(vial));
        obj.setCamaras(String.valueOf(camaras));
        obj.setFinicio(finicio);
        obj.setFeinal(feinal);


        FrameGif.setVisibility(View.VISIBLE);

        if(isGif(dirImagen)){
            UniversalImageLoader.setImage(dirImagen,gif,null);
            Glide.with(context).asGif().load(dirImagen).into(gif);
        }
        else{
            Glide.with(context).load(dirImagen).into(gif);

        }
        dc = new DataConnection(getActivity(),"mostrarPuntos",obj,false);
        new GetPuntos().execute();



        //puntos();
        //mClusterManager.cluster();

        mMap.setMyLocationEnabled(true);

    }



    private boolean isGif(String imagen) {
        String extension = "";
        int i = imagen.lastIndexOf('.');
        int p = Math.max(imagen.lastIndexOf('/'), imagen.lastIndexOf('\\'));
        if (i > p) {
            extension = imagen.substring(i+1);
        }
        return extension.trim().equalsIgnoreCase("gif");
    }




    public  class GetPuntos extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                FrameGif.setVisibility(View.VISIBLE);
                super.onPreExecute();


            }


            @Override
            protected Void doInBackground(Void... params) {




                arrayPuntos = dc.getPuntos();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //
                //
                //puntos();
                CargarPuntosAMapa();


            }
    }

    public void actualizar(boolean del, boolean bas , boolean via , boolean cam){
        obj= new Puntos();
        obj.setAccidentes(String.valueOf(accidentes));
        obj.setDelitos(String.valueOf(del));
        obj.setBasura(String.valueOf(bas));
        obj.setVial(String.valueOf(via));
        obj.setCamaras(String.valueOf(cam));
        obj.setFinicio(tinicio.getText().toString());
        obj.setFeinal(tfinal.getText().toString());


        dc = new DataConnection(getActivity(),"mostrarPuntos",obj,false);
        new GetPuntos().execute();

    }


    public void CargarPuntosAMapa() {

        FrameGif.setVisibility(View.GONE);
        mMap.clear();
        if (marcador_!=null){
            marcador_.remove();
        }



        if (arrayPuntos.size() > 0) {

            LatLng ultpos = null;
            for (int i = 0; i < arrayPuntos.size(); i++) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng((Double.parseDouble(arrayPuntos.get(i).getLat()))
                        , (Double.parseDouble(arrayPuntos.get(i).getLongitud()))))
                        .title(arrayPuntos.get(i).getNombre());

                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                if (arrayPuntos.get(i).getNombre().equals("Accidente")){
                    marcador_ = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng((Double.parseDouble(arrayPuntos.get(i).getLat()))
                                    , (Double.parseDouble(arrayPuntos.get(i).getLongitud()))))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title(arrayPuntos.get(i).getNombre())
                    );
                }if (arrayPuntos.get(i).getNombre().equals("Delito")){
                    marcador_ = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng((Double.parseDouble(arrayPuntos.get(i).getLat()))
                                    , (Double.parseDouble(arrayPuntos.get(i).getLongitud()))))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title(arrayPuntos.get(i).getNombre())
                    );
                }if (arrayPuntos.get(i).getNombre().equals("Punto de Basura")){
                    marcador_ = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng((Double.parseDouble(arrayPuntos.get(i).getLat()))
                                    , (Double.parseDouble(arrayPuntos.get(i).getLongitud()))))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .title(arrayPuntos.get(i).getNombre())
                    );
                }if (arrayPuntos.get(i).getNombre().equals("Zona Vial")){
                    marcador_ = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng((Double.parseDouble(arrayPuntos.get(i).getLat()))
                                    , (Double.parseDouble(arrayPuntos.get(i).getLongitud()))))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .title(arrayPuntos.get(i).getNombre())
                    );
                }if (arrayPuntos.get(i).getNombre().equals("Camara de Seguridad")){
                    marcador_ = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng((Double.parseDouble(arrayPuntos.get(i).getLat()))
                                    , (Double.parseDouble(arrayPuntos.get(i).getLongitud()))))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                            .title(arrayPuntos.get(i).getNombre())
                    );
                }

                marcador_.setTag(arrayPuntos.get(i));
                //  marcador.setTag(i);
                //mClusterManager

                ultpos = new LatLng((Double.parseDouble(arrayPuntos.get(i).getLat()))
                        , (Double.parseDouble(arrayPuntos.get(i).getLongitud())));
            }
            VolverPosicion(ultpos);

        } else {
            //Toast.makeText(activity, "Lo sentimos, no tenemos puntos en estos momentos", Toast.LENGTH_SHORT).show();
        }
        mMap.setOnMarkerClickListener(this);

    }

    public  void VolverPosicion(LatLng miLatLng) {

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

            txtNombre.setText(puntos.getNombre());
            txtdes.setText(puntos.getDescripcion());
            txtfe.setText(puntos.getFecha());
            txtHo.setText(puntos.getHora());
            //, txtDireccion, txtHorario
            mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);

        }
        return false;
    }




    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private void showDatePickerDialog(final TextView editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate =  year+ "-" + twoDigits(month+1) + "-" + twoDigits(day);
                editText.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }

    }



}

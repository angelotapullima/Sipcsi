package com.bufeotec.sipcsi.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Feed.Views.FeedFragment;
import com.bufeotec.sipcsi.Models.Accidentes;
import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;


public class ParteFragment extends Fragment implements View.OnClickListener,OnMapReadyCallback {

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    Button btnMapaApoyo,btn_parte;
    EditText Destinatario,Asunto,Direccion,nombresP1,nombreP2,nombreP3,dniP1,dniP2,dniP3,EdadP1,EdadP2,EdadP3,Descripcion;
    EditText TipoDelito;
    ImageView mas1,mas2;
    View view2,view3;
    TextView DireFrame,Completado,titulo,Fecha,Hora;
    LinearLayout layoutP2,layoutP3,layoutAccidente,layoutDelito;
    Activity activity;
    DataConnection dc2,dc;
    Context context;
    FrameLayout frameMapa;
    Spinner spn_causas,spn_vehiculo;
    Marker mPlus;
    Double corx,cory;
    String data;
    StringRequest stringRequest;
    public ArrayList<Accidentes> arrayArea;
    ArrayList<String> arrayarea;
    ArrayList<String> arrayIdv;
    public ArrayList<Vehiculos> arrayIdvehiculo;
    Preferences pref;

    Double latApoyo = Double.valueOf(0);
    Double longApoyo = Double.valueOf(0);
    GoogleMap mMap;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ParteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParteFragment newInstance(String param1, String param2) {
        ParteFragment fragment = new ParteFragment();
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
        View view = inflater.inflate(R.layout.fragment_parte, container, false);
        activity = getActivity();
        context = getContext();
        getActivity().setTitle("Registro de Parte");
        pref=new Preferences(context);
        data = getArguments().getString("dato");
        titulo=view.findViewById(R.id.titulo);
        btnMapaApoyo=view.findViewById(R.id.btnMapaApoyo);
        btn_parte=view.findViewById(R.id.btn_parte);
        Destinatario=view.findViewById(R.id.Destinatario);
        Asunto=view.findViewById(R.id.Asunto);
        Fecha=view.findViewById(R.id.Fecha);
        Hora=view.findViewById(R.id.Hora);
        Direccion=view.findViewById(R.id.calle);
        spn_vehiculo=view.findViewById(R.id.spn_vehiculo);
        nombresP1=view.findViewById(R.id.nombresP1);
        nombreP2=view.findViewById(R.id.nombresP2);
        nombreP3=view.findViewById(R.id.nombresP3);
        dniP1=view.findViewById(R.id.DniP1);
        dniP2=view.findViewById(R.id.DniP2);
        dniP3=view.findViewById(R.id.DniP3);
        EdadP1=view.findViewById(R.id.EdadP1);
        EdadP2=view.findViewById(R.id.EdadP2);
        EdadP3=view.findViewById(R.id.EdadP3);
        mas1=view.findViewById(R.id.mas1);
        mas2=view.findViewById(R.id.mas2);
        layoutP2=view.findViewById(R.id.layoutP2);
        layoutP3=view.findViewById(R.id.layoutP3);
        view2=view.findViewById(R.id.view2);
        view3=view.findViewById(R.id.view3);
        DireFrame=view.findViewById(R.id.DireFrame);
        Completado=view.findViewById(R.id.Completado);
        frameMapa=view.findViewById(R.id.frameMapa);
        Descripcion=view.findViewById(R.id.Descripcion);
        layoutAccidente=view.findViewById(R.id.layoutAccidente);
        layoutDelito=view.findViewById(R.id.layoutDelito);
        spn_causas= view.findViewById(R.id.spn_causas);
        TipoDelito=view.findViewById(R.id.TipoDelito);

        if (data.equals("apoyo")){
            titulo.setText("Parte Apoyo");

        }if (data.equals("delito")){
            titulo.setText("Parte Delito");
            layoutDelito.setVisibility(View.VISIBLE);

        }if (data.equals("accidente")){
            titulo.setText("Parte Accidente");
            layoutAccidente.setVisibility(View.VISIBLE);
        }



        Fecha.setOnClickListener(this);
        Hora.setOnClickListener(this);
        mas1.setOnClickListener(this);
        mas2.setOnClickListener(this);
        Completado.setOnClickListener(this);
        btnMapaApoyo.setOnClickListener(this);
        btn_parte.setOnClickListener(this);

        spn_causas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    // txt_equipoRetador.setText("");
                    //civ_fotoEquipoRetador.setImageResource(R.drawable.error);
                    //falta imagen
                }else {
                    //txt_equipoRetador.setText(arrayEquipo.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_nombre());
                    // Picasso.with(context).load("http://"+IP+"/"+ arrayEquipo.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_foto()).into(civ_fotoEquipoRetador);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_vehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    // txt_equipoRetador.setText("");
                    //civ_fotoEquipoRetador.setImageResource(R.drawable.error);
                    //falta imagen
                }else {
                    //txt_equipoRetador.setText(arrayEquipo.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_nombre());
                    // Picasso.with(context).load("http://"+IP+"/"+ arrayEquipo.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_foto()).into(civ_fotoEquipoRetador);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        dc2 = new DataConnection(activity,"listarCausas",false);
        new GetCausas().execute();

        dc = new DataConnection(getActivity(),"listarVehiculos",pref.getIdUsuarioPref(),false);
        new GetVehiculosId().execute();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_Apoyo);
        mapFragment.getMapAsync((OnMapReadyCallback) this);



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

    String validaciondireccion = "";
    @Override
    public void onClick(View v) {


        if (v.equals(btnMapaApoyo)){
            frameMapa.setVisibility(View.VISIBLE);

        }if (v.equals(btn_parte)){
            if (data.equals("apoyo")){
                Webapoyo();

            }if (data.equals("delito")){
                Webdelito();

            }if (data.equals("accidente")){
                Webaccidente();
            }

        }if (v.equals(Completado)){
            validaciondireccion = DireFrame.getText().toString();
            if (!validaciondireccion.equals("cargando...")){
                frameMapa.setVisibility(View.GONE);
            }else{
                Toast.makeText(context, "Esperar que cargue la dirección del mapa ", Toast.LENGTH_SHORT).show();
            }
        }if (v.equals(mas1)){
            layoutP2.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        }if (v.equals(mas2)){
            layoutP3.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
        }if(v.equals(Fecha)){
            showDatePickerDialog(Fecha);
        }if(v.equals(Hora)) {
            obtenerHora();
        }
    }



    private void obtenerHora(){
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog recogerHora = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                Hora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);

        recogerHora.show();




    }

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
                            latApoyo = location.getLatitude();
                            longApoyo= location.getLongitude();
                            LatLng Ubica = new LatLng(latApoyo, longApoyo);
                            Marker mPosition = mMap.addMarker(new MarkerOptions()
                                    .position(Ubica)
                                    .draggable(true)
                                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.mar))
                                    .title("Mi Posición"));

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mPosition.getPosition().latitude,
                                            mPosition.getPosition().longitude), 15));
                        }
                    }

                });

        dragmapa();
    }


    public void dragmapa(){
        final Geocoder geo = new Geocoder(getActivity(), Locale.getDefault());
        LatLng latLng = new LatLng(latApoyo,longApoyo);
        mPlus = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Mi Fin"));
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                mMap.clear();
                LatLng center = mMap.getCameraPosition().target;
                mPlus.remove();
                MarkerOptions m = new MarkerOptions();
                m.title("My FinPlus");
                m.position(center);
                mPlus = mMap.addMarker(m);

                try {
                    corx = mPlus.getPosition().latitude;
                    cory = mPlus.getPosition().longitude;
                    List<Address> dire = geo.getFromLocation(mPlus.getPosition().latitude,mPlus.getPosition().longitude,1);
                    Direccion.setText(dire.get(0).getAddressLine(0));
                    DireFrame.setText(dire.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public void ShowHome (){

        FeedFragment po=new FeedFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor,po)
                .addToBackStack("frag")
                .commit();
    }
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                ShowHome();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    public void Webapoyo(){

        String url ="https://"+IP+"/index.php?c=Apoyo&a=registrar_parte_apoyo&key_mobile=123456asdfgh";
        //String url ="https://www.guabba.com/motokapp/ubicacion.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.i("RESPUESTA: ",""+response);

                if (response.equals("1")){

                    //Toast.makeText(context,"Se registro correctamente ",Toast.LENGTH_SHORT).show();
                    showCustomDialog();

                }else{
                    Toast.makeText(activity, "la alerta no se registro correctamente ", Toast.LENGTH_SHORT).show();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();


                String destinatario = Destinatario.getText().toString();
                String asunto = Asunto.getText().toString();
                String fechahora= Fecha.getText().toString() + " " + Hora.getText().toString();
                String cashe= Direccion.getText().toString();
                String coor_x= String.valueOf( corx);
                String coor_y= String.valueOf(cory);
                String desc= Descripcion.getText().toString();
                String nom1= nombresP1.getText().toString();
                String nom2= nombreP2.getText().toString();
                String nom3= nombreP3.getText().toString();
                String dni1= dniP1.getText().toString();
                String dni2= dniP2.getText().toString();
                String dni3= dniP3.getText().toString();
                String edad1= EdadP1.getText().toString();
                String edad2= EdadP2.getText().toString();
                String edad3= EdadP3.getText().toString();



                parametros.put("destinatario", destinatario);
                parametros.put("asunto", asunto);
                parametros.put("fechaHora", fechahora);
                parametros.put("vehiculo", arrayIdvehiculo.get(spn_vehiculo.getSelectedItemPosition()-1).getId_vehiculo());
                parametros.put("calle_nombre", cashe);
                parametros.put("coor_x", coor_x);
                parametros.put("coor_y", coor_y);
                parametros.put("descripcion", desc);
                parametros.put("nombre1", nom1);
                parametros.put("dni1", dni1);
                parametros.put("edad1", edad1);
                parametros.put("nombre2", nom2);
                parametros.put("dni2", dni2);
                parametros.put("edad2", edad2);
                parametros.put("nombre3", nom3);
                parametros.put("dni3", dni3);
                parametros.put("edad3", edad3);
                parametros.put("distrito", pref.getDistritoIdPref());
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);

    }


    public void Webdelito(){

        String url ="https://"+IP+"/index.php?c=Robo&a=registrar_parte_delito&key_mobile=123456asdfgh";
        //String url ="https://www.guabba.com/motokapp/ubicacion.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context,"Se registro correctamente ",Toast.LENGTH_SHORT).show();

                if (response.equals("1")){

                    showCustomDialog();

                }else{
                    Toast.makeText(activity, "la alerta no se registro correctamente ", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();

                String destinatario = Destinatario.getText().toString();
                String asunto = Asunto.getText().toString();
                String fechahora= Fecha.getText().toString() + " " + Hora.getText().toString();
                //String vehiculo= Vehiculo.getText().toString();
                String cashe= Direccion.getText().toString();
                String desc= Descripcion.getText().toString();
                String coor_x= String.valueOf(corx);
                String coor_y= String.valueOf(cory);
                String nom1= nombresP1.getText().toString();
                String nom2= nombreP2.getText().toString();
                String nom3= nombreP3.getText().toString();
                String dni1= dniP1.getText().toString();
                String dni2= dniP2.getText().toString();
                String dni3= dniP3.getText().toString();
                String edad1= EdadP1.getText().toString();
                String edad2= EdadP2.getText().toString();
                String edad3= EdadP3.getText().toString();
                String Tips= TipoDelito.getText().toString();



                parametros.put("destinatario", destinatario);
                parametros.put("asunto", asunto);
                parametros.put("fechaHora", fechahora);
                parametros.put("vehiculo", arrayIdvehiculo.get(spn_vehiculo.getSelectedItemPosition()-1).getId_vehiculo());
                parametros.put("calle_nombre", cashe);
                parametros.put("coor_x", coor_x);
                parametros.put("coor_y", coor_y);
                parametros.put("descripcion", desc);
                parametros.put("nombre1", nom1);
                parametros.put("dni1", dni1);
                parametros.put("edad1", edad1);
                parametros.put("nombre2", nom2);
                parametros.put("dni2", dni2);
                parametros.put("edad2", edad2);
                parametros.put("nombre3", nom3);
                parametros.put("dni3", dni3);
                parametros.put("edad3", edad3);
                parametros.put("tipo", Tips);
                parametros.put("distrito", pref.getDistritoIdPref());

                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }


    public void Webaccidente(){

        String url ="https://"+IP+"/index.php?c=Accidente&a=registro_parte_accidente&key_mobile=123456asdfgh";
        //String url ="https://www.guabba.com/motokapp/ubicacion.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("webAccidente", "onResponse: " + response);

                if (response.equals("1")){
                    showCustomDialog();

                }else{
                    Toast.makeText(activity, "fasho", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();


                String destinatario = Destinatario.getText().toString();
                String asunto = Asunto.getText().toString();
                String fechahora= Fecha.getText().toString() + " " + Hora.getText().toString();
                //String vehiculo= Vehiculo.getText().toString();
                String desc= Descripcion.getText().toString();
                String cashe= Direccion.getText().toString();
                String coor_x= String.valueOf( corx);
                String coor_y= String.valueOf( cory);
                String nom1= nombresP1.getText().toString();
                String nom2= nombreP2.getText().toString();
                String nom3= nombreP3.getText().toString();
                String dni1= dniP1.getText().toString();
                String dni2= dniP2.getText().toString();
                String dni3= dniP3.getText().toString();
                String edad1= EdadP1.getText().toString();
                String edad2= EdadP2.getText().toString();
                String edad3= EdadP3.getText().toString();



                parametros.put("destinatario", destinatario);
                parametros.put("asunto", asunto);
                parametros.put("fechaHora", fechahora);
                parametros.put("vehiculo", arrayIdvehiculo.get(spn_vehiculo.getSelectedItemPosition()-1).getId_vehiculo());
                parametros.put("calle_nombre", cashe);
                parametros.put("coor_x", coor_x);
                parametros.put("coor_y", coor_y);
                parametros.put("descripcion", desc);
                parametros.put("nombre1", nom1);
                parametros.put("dni1", dni1);
                parametros.put("edad1", edad1);
                parametros.put("nombre2", nom2);
                parametros.put("dni2", dni2);
                parametros.put("edad2", edad2);
                parametros.put("nombre3", nom3);
                parametros.put("dni3", dni3);
                parametros.put("edad3", edad3);
                parametros.put("causa", "1");
                parametros.put("distrito", pref.getDistritoIdPref());
                parametros.put("fatal", "si");

                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }

    public class GetCausas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayarea =  new ArrayList<String>();
            arrayArea = dc2.getListaCausas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for (Accidentes obj :arrayArea){
                arrayarea.add(obj.getCausa());
            }
            //progressBar.setVisibility(ProgressBar.INVISIBLE);
            arrayarea.add(0,"Seleccione");
            ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(context,R.layout.spiner_item,arrayarea);
            adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_causas.setAdapter(adapEquipos);

        }
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

    public class GetVehiculosId extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayIdv = new ArrayList<String>();
            arrayIdvehiculo = dc.getListaVehiculos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            for (Vehiculos obj :arrayIdvehiculo){
                arrayIdv.add(obj.getPlaca());
            }
            //progressBar.setVisibility(ProgressBar.INVISIBLE);
            arrayIdv.add(0,"Seleccione");
            ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(context,R.layout.spiner_item,arrayIdv);
            adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_vehiculo.setAdapter(adapEquipos);


        }
    }
}

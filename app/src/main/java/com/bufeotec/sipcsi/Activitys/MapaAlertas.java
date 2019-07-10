package com.bufeotec.sipcsi.Activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.UniversalImageLoader;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;
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
import java.util.HashMap;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;

public class MapaAlertas extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, View.OnClickListener {


    public static final String ALARMA= "ALARMA";
    BroadcastReceiver BR;
    UniversalImageLoader universalImageLoader;
    static GoogleMap mMap;
    static Marker marcador;
    Marker mIni;
    Context context;
    static DataConnection dc,dc2;
    public static ArrayList<Vehiculos> listavehiculosalertas;
    StringRequest stringRequest;
    Double Alertalatitudes,Alertalongitudes;
    String idAler,tipo,tipo_delito,des,dire,fecA,estado;
    TextView tips, description,direc,fecha;
    Handler handler;
    ImageView fotoAlerta;
    boolean run = false;
    TextView txtNombre ,txtdes,txtfe ,txtHo;
    Button btnAtendida;

    LinearLayout botomPuntos, botomTurismo,botomAlertas;
    static boolean valor = false;


    View bottomSheet;
    LinearLayout tapactionlayout;
    private BottomSheetBehavior mBottomSheetBehavior1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_alertas);

        context=this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_SeguimientoAlertas);
        mapFragment.getMapAsync(this);

        showToolbar("ListAlertas" ,true);
        txtNombre=findViewById(R.id.txtNombre);
        txtdes=findViewById(R.id.txtdes);
        txtHo=findViewById(R.id.txtHo);
        txtfe=findViewById(R.id.txtfe);




        universalImageLoader = new UniversalImageLoader(context);

        View headerLayout1 = findViewById(R.id.bottomJsoft);
        tapactionlayout = (LinearLayout) findViewById(R.id.tap_action_layout);

        bottomSheet = findViewById(R.id.bottomJsoft);
        tips=findViewById(R.id.tipo);
        description=findViewById(R.id.description);
        direc=findViewById(R.id.direc);
        fecha=findViewById(R.id.fecha);
        btnAtendida=findViewById(R.id.btnAtendida);
        fotoAlerta=findViewById(R.id.fotoAlerta);
        botomPuntos=findViewById(R.id.botomPuntos);
        botomTurismo=findViewById(R.id.botomTurismo);
        botomAlertas=findViewById(R.id.botomAlertas);

        botomTurismo.setVisibility(View.GONE);
        botomAlertas.setVisibility(View.VISIBLE);
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


        idAler=getIntent().getExtras().getString("alertaid");
        Alertalatitudes=Double.valueOf(getIntent().getExtras().getString("Alertalatitudes"));
        Alertalongitudes=Double.valueOf(getIntent().getExtras().getString("Alertalongitudes"));
        tipo=getIntent().getExtras().getString("tipo");
        tipo_delito=getIntent().getExtras().getString("tipoDelito");
        des=getIntent().getExtras().getString("des");
        dire=getIntent().getExtras().getString("direccion");
        fecA=getIntent().getExtras().getString("fecha");
        estado=getIntent().getExtras().getString("estado");

        if (estado.equals("atendido")){
            btnAtendida.setVisibility(View.GONE);
        }

        if (tipo.equals("DELITO")){
            tips.setText(tipo_delito);
            tips.setBackgroundColor(Color.rgb(63,81,181));
        }if (tipo.equals("ZONAVIAL")){
            tips.setText(tipo);
            tips.setBackgroundColor(Color.rgb(229,142,53));

        }if (tipo.equals("ACCIDENTE")){
            tips.setBackgroundColor(Color.rgb(232,52,51));
            tips.setText(tipo);
        }
        btnAtendida.setOnClickListener(this);

        description.setText(des);
        direc.setText(dire);
        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                String tipo = intent.getStringExtra("tipo");
                String mensaje = intent.getStringExtra("titulo");
                String dir = intent.getStringExtra("direccion");
                String cont = intent.getStringExtra("conte");

                if (tipo.equals("finalizar")){

                    dialogoFinalizar(cont);
                }




            }
        };


        ejecutarCadaTiempo();
    }

    private void dialogoFinalizar(String con) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_finalizar);
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
                finish();
                //Intent i = new Intent(MapaAlertas.this, MainActivity.class);
                //startActivity(i);
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(BR, new IntentFilter(ALARMA));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BR);
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    protected void onStop() {

        run=true;
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(Alertalatitudes, Alertalongitudes);
        mIni = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(true)
                .title("ALERTA"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(mIni.getPosition().latitude,
                        mIni.getPosition().longitude), 16));



        dc = new DataConnection(this,"listarVehiculosAlerta",idAler,false);
        new MapaAlertas.GetVehiculosAlerta().execute();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAtendida)){
            mostrarDialogo();
        }
    }
    public void mostrarDialogo(){
        new AlertDialog.Builder(MapaAlertas.this).setTitle("Alarma Atendida ")
                .setMessage("La alarma fue atendida exitosamente?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        atenderEstado();
                    }
                })
                .setNegativeButton("CANCEL",null)
                .show();
    }

    public class GetVehiculosAlerta extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listavehiculosalertas= dc.getListaVehiculosAlerta();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CargarPuntosAMapa();
        }
    }

    boolean fotex = false;

    public void CargarPuntosAMapa() {



        if(marcador!=null){
            marcador.remove();
        }
        if (listavehiculosalertas.size() > 0) {
            fecha.setText(listavehiculosalertas.get(0).getFechora());

            LatLng ultpos = null;
            for (int i = 0; i < listavehiculosalertas.size(); i++) {

                if (!listavehiculosalertas.get(0).getFoto().equals("0")){

                    if (fotex){

                    }else{
                        String urlImagen = "https://www.guabba.com/accidentestransito/"+listavehiculosalertas.get(0).getFoto();
                        Log.e("imagen", "CargarPuntosAMapa: " + urlImagen );
                        UniversalImageLoader.setImage(urlImagen,fotoAlerta,null);
                        fotex= true;
                        //Glide.with(context).load(urlImagen).into(fotoAlerta);

                    }

                }else{
                    fotoAlerta.setVisibility(View.GONE);
                }
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng((Double.parseDouble(listavehiculosalertas.get(i).getLatitud()))
                        , (Double.parseDouble(listavehiculosalertas.get(i).getLongitud()))))
                        .title(listavehiculosalertas.get(i).getPlaca());
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                marcador= mMap.addMarker(new MarkerOptions()
                        .position(new LatLng((Double.parseDouble(listavehiculosalertas.get(i).getLatitud()))
                                , (Double.parseDouble(listavehiculosalertas.get(i).getLongitud()))))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .title(listavehiculosalertas.get(i).getPlaca() + " "+ listavehiculosalertas.get(i).getHacetiempo())

                );
                //marcador.setTag(listavehiculosalertas.get(i));
                //  marcador.setTag(i);

                ultpos = new LatLng((Double.parseDouble(listavehiculosalertas.get(i).getLatitud()))
                        , (Double.parseDouble(listavehiculosalertas.get(i).getLongitud())));
            }

            if (!valor){
                VolverPosicion(ultpos);
            }

        } else {
            Toast.makeText(MapaAlertas.this, "Lo sentimos, no tenemos Vehiculos activos en estos momentos", Toast.LENGTH_SHORT).show();
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
        valor=true;
    }

    private void ejecutarCadaTiempo(){
        handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!run){
                    Log.i("funciones", "func alert: ");
                    dc = new DataConnection(MapaAlertas.this,"listarVehiculosAlerta",idAler,false);
                    new MapaAlertas.GetVehiculosAlerta().execute();
                }else{
                    handler.removeCallbacks(this);
                }


                //CargarPuntosAMapa();//llamamos nuestro metodo
                handler.postDelayed(this,4000);//se ejecutara cada 10 segundos
            }
        },5000);//empezara a ejecutarse después de 5 milisegundos
    }



    public void atenderEstado() {


            String url ="https://"+IP+"/index.php?c=Alerta&a=finalizar_alerta&key_mobile=123456asdfgh";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(context,"la fruta ",Toast.LENGTH_SHORT).show();
                Log.i("atenderestado" +": ",""+response);
                Toast.makeText(context, "atencion exitosa", Toast.LENGTH_SHORT).show();
                finish();


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i( "erroMapa: ",""+error);

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_alerta",idAler);

                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            Integer clickCount = (Integer) marker.getTag();
            //  return true;

        } catch (Exception ex) {
            Vehiculos vehiculos;
            vehiculos = (Vehiculos) marker.getTag();

            txtNombre.setText(vehiculos.getPlaca());
            txtdes.setText(vehiculos.getMarca());
            txtfe.setText(vehiculos.getModelo());


        }
        return false;
    }



}

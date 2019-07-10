package com.bufeotec.sipcsi.Principal;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Activitys.DetalleAlarmas;
import com.bufeotec.sipcsi.Activitys.GPSinactivo;
import com.bufeotec.sipcsi.BuildConfig;
import com.bufeotec.sipcsi.Feed.Views.FeedFragment;
import com.bufeotec.sipcsi.Fragments.AboutFragment;
import com.bufeotec.sipcsi.Fragments.InformacionFragment;
import com.bufeotec.sipcsi.Fragments.ListCarrosBasurerosFragment;
import com.bufeotec.sipcsi.Fragments.TipsFragment;
import com.bufeotec.sipcsi.Fragments.ListAlertas;
import com.bufeotec.sipcsi.Fragments.ListMisAlertas;
import com.bufeotec.sipcsi.Fragments.ListMisAtenciones;
import com.bufeotec.sipcsi.Fragments.ListParteAccidenteFragment;
import com.bufeotec.sipcsi.Fragments.ListParteApoyoFragment;
import com.bufeotec.sipcsi.Fragments.ListParteDelitoFragment;
import com.bufeotec.sipcsi.Fragments.ListVehiculosFragment;
import com.bufeotec.sipcsi.Fragments.MapsFragment;
import com.bufeotec.sipcsi.Fragments.NumerosEmergenciaFragment;
import com.bufeotec.sipcsi.Fragments.ParteFragment;
import com.bufeotec.sipcsi.Fragments.PerfilFragment;
import com.bufeotec.sipcsi.Fragments.PrimerosAuxiliosFragment;
import com.bufeotec.sipcsi.Fragments.PuntosTuristicosFragment;
import com.bufeotec.sipcsi.Fragments.SeguimientoATodos;
import com.bufeotec.sipcsi.Fragments.TrackingBasureroFragment;
import com.bufeotec.sipcsi.MiFeed.Views.MyFeedFragment;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.util.HashMap;
import java.util.Map;
import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener ,
        NavigationView.OnNavigationItemSelectedListener,
        MapsFragment.OnFragmentInteractionListener,
        ListVehiculosFragment.OnFragmentInteractionListener,
        SeguimientoATodos.OnFragmentInteractionListener,
        ListAlertas.OnFragmentInteractionListener,
        ListMisAtenciones.OnFragmentInteractionListener,
        ListMisAlertas.OnFragmentInteractionListener,
        PerfilFragment.OnFragmentInteractionListener,
        NumerosEmergenciaFragment.OnFragmentInteractionListener,
        PrimerosAuxiliosFragment.OnFragmentInteractionListener,
        ParteFragment.OnFragmentInteractionListener,
        ListCarrosBasurerosFragment.OnFragmentInteractionListener,
        TipsFragment.OnFragmentInteractionListener,
        ListParteAccidenteFragment.OnFragmentInteractionListener,
        ListParteDelitoFragment.OnFragmentInteractionListener,
        ListParteApoyoFragment.OnFragmentInteractionListener,
        PuntosTuristicosFragment.OnFragmentInteractionListener,
        TrackingBasureroFragment.OnFragmentInteractionListener, View.OnClickListener,
        MyFeedFragment.OnFragmentInteractionListener,
        InformacionFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        FeedFragment.OnFragmentInteractionListener {


    public static final String ALARMA= "ALARMA";

    static Dialog dialog, dialog1 = null;
    String valor;
    Button btnver;
    TextView tituloNoti,direccion;
    Fragment fragment = null;
    //Inicializaos las Variables
    public static String rol_id,nombreuser,vehiculoid;
    static public String usuarioid, distrito;
    static  String  token = "";
    Toolbar toolbar;
    static String tokenNuevo = "";
    static StringRequest stringRequest;
    Context context;
    String usuario ,clave;
    TextView Nusuario,EmailU;
    private static final String TAG = "FirebaseToken";
    static FloatingActionMenu fab;
    FloatingActionButton menu_delito,menu_accidente,menu_zonavial;
    static SharedPreferences sharedPreferences;
    BroadcastReceiver BR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();
        setContentView(R.layout.activity_main);
        context = this;
        checkpermisos();
        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
        //inicializar las preferencias para guardar los datos del login
        sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);


        //Inicializar Firebase para obtener los tokens
        FirebaseApp.initializeApp(this);


        if(!sharedPreferences.getString("idusuario","").equals("") ){

            usuarioid = sharedPreferences.getString("idusuario","");
            rol_id = sharedPreferences.getString("rol","");
            token= sharedPreferences.getString ("token","");
            usuario= sharedPreferences.getString ("nickname","");
            clave= sharedPreferences.getString ("clave","");
            distrito= sharedPreferences.getString ("distrito_id","");
            nombreuser= sharedPreferences.getString ("nombre","");
            vehiculoid= sharedPreferences.getString ("vehiculo","");
        }else{
            usuarioid = getIntent().getExtras().getString("idusuario");
            rol_id = getIntent().getExtras().getString("rol");
            token=getIntent().getExtras().getString("token");
            usuario=getIntent().getExtras().getString("nickname");
            clave=getIntent().getExtras().getString("clave");
            distrito=getIntent().getExtras().getString("distrito_id");
            nombreuser=getIntent().getExtras().getString("nombre");
            vehiculoid=getIntent().getExtras().getString("vehiculo");
        }
        Log.e("rol" ,"id : " +rol_id);
        //intent´s que se reciben al hacer el login


        //guardamos los accesos para no volver a hacer session
        //guardarAcceso(usuario,clave,distrito,usuarioid);

        //instanciamos la barra lateral del navigationDrawer
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        Nusuario=header.findViewById(R.id.nombreMain);
        EmailU = header.findViewById(R.id.emailMain);

        Nusuario.setText(usuario);
        EmailU.setText(nombreuser);


        //Inicializamos El fragment Mapas De Incidencias para Iniciar la aplicación
        ShowHome();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setElevation(0);


        //inicializamos el Float Action Button
        fab=findViewById(R.id.multiple_Actions);
        menu_accidente=findViewById(R.id.menu_accidente);
        menu_delito=findViewById(R.id.menu_delito);
        menu_zonavial=findViewById(R.id.menu_zonavial);






        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (rol_id.equals("3")){
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(11).setVisible(false);
        }if (rol_id.equals("4")){
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(7).setVisible(false);
            navigationView.getMenu().getItem(8).setVisible(false);
            navigationView.getMenu().getItem(9).setVisible(false);
            navigationView.getMenu().getItem(11).setVisible(false);
        }if (rol_id.equals("5")){
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(7).setVisible(false);
            navigationView.getMenu().getItem(8).setVisible(false);
            navigationView.getMenu().getItem(9).setVisible(false);
        }



        //Obetnemos el token actual para verificar si es igual al que llega de la BD
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener
                ( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        tokenNuevo = instanceIdResult.getToken();
                        if (tokenNuevo.equals(token)) {
                            Log.d(TAG, "todo esta chevere 1  " + tokenNuevo);
                            Log.d(TAG, "todo esta chevere 2: " + token);
                            //al ser diferente llamamos al asyntask GetActualizar

                            new GetActualizar().execute();
                        } else {
                            Log.d(TAG, "diferente nuevo  " + tokenNuevo);
                            Log.d(TAG, "diferente antiguo: " + token);
                            new GetActualizar().execute();
                        }
                    }
                });

        menu_delito.setOnClickListener(this);
        menu_accidente.setOnClickListener(this);
        menu_zonavial.setOnClickListener(this);

        View v = getLayoutInflater().inflate(R.layout.dialog_notify,null);
        dialog1 = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog1.setContentView(v);

        View vi = getLayoutInflater().inflate(R.layout.bottomsheet_layout,null);
        dialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(vi);

        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                String tipo = intent.getStringExtra("tipo");
                String mensaje = intent.getStringExtra("titulo");
                String dir = intent.getStringExtra("direccion");
                String cont = intent.getStringExtra("conte");

                if (tipo.equals("alarmas")){

                    dialogoAlarma(mensaje,dir);
                }if (tipo.equals("finalizar")){

                    dialogoFinalizar(cont);
                }




            }
        };
        GPSActivo();
        despuesde10();
    }




    public void checkpermisos() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    public void ShowHome (){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new FeedFragment()).commit();
    }

    @Override
    public void onBackPressed() {

        //Intanciamos el DrawerLayout
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);

        //Si estamos con la barra lateral visible al presionae atras se cerrara
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    public void despuesde10(){
        Handler handler = new Handler();

        //Llamamos al método postDelayed
        handler.postDelayed(new Runnable() {
            public void run() {

                Log.i("funciones", " despuesde10 " );
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    mService.requestLocationUpdates();
                }
       //código que se ejecuta tras el "delay"
            }
        }, 10000); // 2 segundos de "delay"

    }
    public void GPSActivo (){

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent i =new Intent(MainActivity.this, GPSinactivo.class);
            startActivity(i);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //Transacciones entre fragments del navigation Drawer
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        int id = item.getItemId();


        if (id == R.id.pueblo) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new FeedFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();



            // Handle the camera action
        } else if (id == R.id.mapa_incidencias) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new MapsFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();



            // Handle the camera action
        } else if (id == R.id.seguimiento_todos) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new SeguimientoATodos();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        } else if (id == R.id.list_unidad) {


            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ListVehiculosFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();


        } else if (id == R.id.list_alertas) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ListAlertas();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();


        } else if (id == R.id.Misatenciones) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ListMisAtenciones();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();


        } else if (id == R.id.Misalertas) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ListMisAlertas();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();


        }
        else if (id == R.id.parte) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ListParteApoyoFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();


        }else if (id == R.id.parteD) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ListParteDelitoFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();
            //setTitle("Parte Delito ");
            /*Bundle data = new Bundle();
            data.putString("dato","delito");
            fragment.setArguments(data);*/

        }else if (id == R.id.parteA) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ListParteAccidenteFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();
            /*Bundle data = new Bundle();
            data.putString("dato","accidente");
            fragment.setArguments(data);*/


        }else if (id == R.id.Basurero) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new ListCarrosBasurerosFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }
        else if (id == R.id.tracking) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new TrackingBasureroFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }
        else if (id == R.id.puntoT) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new PuntosTuristicosFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }
        else if (id == R.id.perfil) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new PerfilFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }else if (id == R.id.Nemergencias) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new NumerosEmergenciaFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }else if (id == R.id.Pauxilios) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new PrimerosAuxiliosFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }

        else if (id == R.id.tips) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new TipsFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }
        else if (id == R.id.about) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new AboutFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onClick(View v) {
        Intent i = null;
        if(v.equals(menu_accidente)){
            valor = "ACCIDENTE";
            i = new Intent(MainActivity.this, DetalleAlarmas.class);
            i.putExtra("valor",valor);
        }if(v.equals(menu_delito)){
            //mService.requestLocationUpdates();
            valor = "DELITO";
            i = new Intent(MainActivity.this,DetalleAlarmas.class);
            i.putExtra("valor",valor);
        }if(v.equals(menu_zonavial)){
            valor = "ZONAVIAL";
            i = new Intent(MainActivity.this,DetalleAlarmas.class);
            i.putExtra("valor",valor);
        }
        startActivity(i);

    }


    //Asyntask para actualizat token en segundo plano
    private  class GetActualizar extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Actualizar();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

    }

    //función para actualizar el token
    private void Actualizar(){
        String url ="https://"+IP+"/index.php?c=Usuario&a=actualizar_token&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("actualizartoken: ",""+response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //enviamos los parametros id_usuario y el token nuevo asignado al telefono
                Map<String,String> parametros=new HashMap<>();
                parametros.put("idusuario",usuarioid);
                parametros.put("token",tokenNuevo);

                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);


    }

    //funcion para guardar los accesos obtenidos desde el login


    //funcion para elimainar las preferencias al cerrar Sesion
    public static void removerLogin(){
        sharedPreferences.edit().clear().apply();
    }




    public void dialogoAlarma(String titles , String direc){

        //dialog.setContentView(R.layout.dialog_notify);


        tituloNoti = dialog1.findViewById(R.id.tituloNoti);
        direccion = dialog1.findViewById(R.id.Direccion);
        btnver=dialog1.findViewById(R.id.btnver);
        //closeNotify=dialog.findViewById(R.id.closeNotify);

        tituloNoti.setText(titles);
        direccion.setText(direc);

        btnver.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListAlertas listAlertas = new ListAlertas();
                fragmentTransaction.replace(R.id.contenedor, listAlertas);
                fragmentTransaction.commit();
                fab.setVisibility(View.GONE);
            }
        });

        /*closeNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();

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
                ShowHome();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }



    //private static final String TAG = MainActivity.class.getSimpleName();

    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;

    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;


    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        if (!checkPermissions()) {
            requestPermissions();

        } else {
            //mService.requestLocationUpdates();
        }


        // Restore the state of the buttons when the activity (re)launches.
        setButtonsState(Utils.requestingLocationUpdates(this));

        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    public void destruirService (){
        mService.removeLocationUpdates();
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
        LocalBroadcastManager.getInstance(this).registerReceiver(BR, new IntentFilter(ALARMA));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BR);
        super.onPause();
    }


    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.drawer_layout1),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.
                setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.drawer_layout1),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            String lat = String.valueOf(location.getLatitude());
            String lon = String.valueOf(location.getLongitude());
            if(location!=null){
                sendInfo(lat,lon);

                //Toast.makeText(MainActivity.this, Utils.getLocationText(location),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
                    false));
        }
    }

    private void setButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            //mRequestLocationUpdatesButton.setEnabled(false);
            //mRemoveLocationUpdatesButton.setEnabled(true);
        } else {
            //mRequestLocationUpdatesButton.setEnabled(true);
            //mRemoveLocationUpdatesButton.setEnabled(false);
        }
    }

    private void sendInfo(final String lat, final String lng) {


        String url ="https://"+IP+"/index.php?c=Vehiculo&a=actualizar_ubicacion_vehiculo&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("Hoja sin plaga")){

                } else {
                    //Toast.makeText(ChoferDatosDeCarrera.this,"No se ha registrado ",Toast.LENGTH_SHORT).show();
                    Log.d("LServices: ",""+response);
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
                parametros.put("id",vehiculoid);
                parametros.put("lat",lat);
                parametros.put("long",lng);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }




}

package com.bufeotec.sipcsi.Activitys;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Principal.MainActivity;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.theartofdev.edmodo.cropper.CropImage;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class DetalleAlarmas extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    //Iniciamos las Variables
    EditText edt_des;
    Button btn_registro, btn_camara,btn_cancelar;
    Spinner tipo_delito;
    ImageView img_alarma;
    String valor,lat,lon,des,alertas_id;
    TextView textValor,direc;
    String respuesta;
    LinearLayout lDelito;
    String tipoDelito,direccion;
    private int REQUEST_CAMERA = 0, SELET_GALERRY = 9;
    String userChoosenTask;
    public Uri output, resultUriRecortada;
    public Context context;
    GoogleMap mMap;
    LinearLayout laFoto;
    Marker mIni;
    StringRequest stringRequest;
    String latitude,longitude;
    JSONObject json_data;
    CheckBox checkFoto;
    Preferences pref;
    String valorcodigo;
    String urlGuardar = "https://"+IP+"/index.php?c=Alerta&a=guardar_alerta_imagen&key_mobile=123456asdfgh";
    String tipoNotificacion = "";
    String validaciondireccion = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alarmas);

        //Instanciamos el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_alarma);
        mapFragment.getMapAsync(this);

        valor = getIntent().getExtras().getString("valor");

        //pedimos permisos para usar el la memoria interna y externa del teléfono
        checkpermisos();
        showToolbar("Detalle Alarmas" , true);
        context=this;

        pref=new Preferences(getApplicationContext());
        edt_des = findViewById(R.id.edt_des);
        btn_registro = findViewById(R.id.btn_registrar);
        btn_camara = findViewById(R.id.btn_Camara);
        btn_cancelar = findViewById(R.id.btn_cancelar);
        tipo_delito = findViewById(R.id.spn_tipo_delito);
        img_alarma = findViewById(R.id.img_alarma);
        lDelito = findViewById(R.id.lDelito);
        textValor = findViewById(R.id.textvalor);
        laFoto=findViewById(R.id.laFoto);
        checkFoto = findViewById(R.id.checkFoto);
        direc=findViewById(R.id.dire);

        //Recibimos el valor de la Actividad DetallesTips para mostrar o no el tipo de delito
        if (valor.equals("ACCIDENTE")) {
            lDelito.setVisibility(View.GONE);
        }
        if (valor.equals("ZONAVIAL")) {
            lDelito.setVisibility(View.GONE);
        }
        if (valor.equals("PUEBLOOPINA")) {
            lDelito.setVisibility(View.GONE);
        }

        textValor.setText(valor);

        btn_camara.setOnClickListener(this);
        btn_registro.setOnClickListener(this);
        btn_cancelar.setOnClickListener(this);


        //mostramos la opcion de subir foto si es que el check esta seleccionado
        checkFoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    laFoto.setVisibility(View.VISIBLE);
                }else{
                    laFoto.setVisibility(View.GONE);
                }
            }
        });


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


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //comprobamos si tenemos permisos para poder utilizar el boton de mi ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        dragMapa();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_camara)) {
            ClickCamara();

        }
        if (v.equals(btn_registro)) {

                enviar();

        }if (v.equals(btn_cancelar)) {
            finish();

        }
    }

    public void ClickCamara() {
        selectImage();

    }

    public void selectImage() {

        final CharSequence[] items = {"Camara", "Galería", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DetalleAlarmas.this);
        builder.setTitle("Seleccione :");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Camara")) {
                    userChoosenTask = "Camara";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File carpetas = new File(Environment.getExternalStorageDirectory() + "/Sipsi/", "ListAlertas");
                    carpetas.mkdirs();

                    //String aleatorio = MenuPrincipal.usuario_id+"_"+act_nombreMascota.getText().toString();
                    String nombre = "aleatorio.jpg";

                    File imagen = new File(carpetas, nombre);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        String authorities = getApplicationContext().getPackageName() + ".provider";
                        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, imagen);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    } else {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));

                    }
                    output = Uri.fromFile(imagen);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[item].equals("Galería")) {
                    userChoosenTask = "Galería";


                    Intent intentgaleria = new Intent(Intent.ACTION_PICK);
                    intentgaleria.setType("image/*");
                    if (intentgaleria.resolveActivity(getPackageManager()) != null) {


                        startActivityForResult(intentgaleria, SELET_GALERRY);
                    }
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA){

                CropImage.activity(output).start(this);
                //  CropImage.activity(output).setMaxCropResultSize(1080,566 ).setMinCropResultSize(800,400).start(this);
            }
            else if (requestCode == SELET_GALERRY) {

                Uri uri = result.getData();


                File f1,f2;
                f1 = new File(getRealPathFromUri(context,uri));
                String fname = f1.getName();


                f2= new File(Environment.getExternalStorageDirectory() + "/Sipsi/","Queja");
                f2.mkdirs();
                try {
                    FileUtils.copyFileToDirectory(f1,f2);
                    ContentValues values =new ContentValues();
                    values.put(MediaStore.Images.Media.DATE_TAKEN,System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE,"image/*");
                    values.put(MediaStore.MediaColumns.DATA,f2.toString()+"/"+fname);
                    getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }  finally {
                    //Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                }

                CropImage.activity(uri).start(this);

            }
        }if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult resultado = CropImage.getActivityResult(result);
            if (resultCode == RESULT_OK) {

                resultUriRecortada = resultado.getUri();

                img_alarma.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = resultado.getError();
                //Toast.makeText(getApplicationContext(),"Error"+error, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Error: Intente de nuevo", Toast.LENGTH_SHORT).show();

            }
        }

    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



    public void enviar (){
        validaciondireccion = direc.getText().toString();
        if( !validaciondireccion.equals("cargando...")){
            if (!(tipo_delito.getSelectedItem().toString().isEmpty())  ) {

                des = edt_des.getText().toString();
                direccion = direc.getText().toString();
                tipoDelito = tipo_delito.getSelectedItem().toString();


                if (valor.equals("ACCIDENTE")) {
                    tipoNotificacion = valor;
                }
                if (valor.equals("ZONAVIAL")) {
                    tipoNotificacion = valor;
                }
                if (valor.equals("DELITO")) {
                    tipoNotificacion = tipoDelito;
                }


                RequestParams params1 = new RequestParams();




                des = edt_des.getText().toString();
                direccion = direc.getText().toString();
                tipoDelito = tipo_delito.getSelectedItem().toString();


                params1.put("id_distrito", pref.getDistritoIdPref());
                params1.put("id_usuario", pref.getIdUsuarioPref());
                params1.put("tipo", valor);
                params1.put("tipo_delito", tipoDelito);
                params1.put("calle_nombre", direccion);
                params1.put("calle_x", lat);
                params1.put("calle_y", lon);
                params1.put("descripcion", des);


                AsyncHttpClient client = new AsyncHttpClient();
                final String finalTipoNotificacion = tipoNotificacion;
                client.post("http://"+IP+"/index.php?c=Alerta&a=guardar&key_mobile=123456asdfgh", params1, new AsyncHttpResponseHandler() {

                    ProgressDialog loading;

                    @Override
                    public void onStart() {
                        super.onStart();
                        loading = new ProgressDialog(DetalleAlarmas.this);
                        loading.setTitle("Sipsi");
                        loading.setMessage("Por favor espere...");
                        loading.setIndeterminate(false);
                        loading.setCancelable(false);
                        loading.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        loading.dismiss();

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        if (statusCode == 200) {
                            //Toast.makeText(getApplicationContext(), "valo"+ responseBody.toString(), Toast.LENGTH_SHORT).show();

                            enviarAlertas(finalTipoNotificacion , direccion);
                            String est = "atendido";
                            try {
                                respuesta = new String(responseBody, "UTF-8");
                                Log.i("delitope", "onSuccess: " + respuesta);


                                try {
                                    json_data = new JSONObject(respuesta);
                                    JSONArray resultJSON = json_data.getJSONArray("results");
                                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                                    valorcodigo = jsonNodev.optString("valor");
                                    alertas_id=jsonNodev.optString("id_alerta");
                                    Log.i("alertas", "alertas id: " + alertas_id);

                                    if (checkFoto.isChecked()){
                                        uploadMultipart(alertas_id);
                                    }
                                    if (valorcodigo.equals("1") || !alertas_id.isEmpty()){
                                        //enviarAlertas();
                                        Intent i = new Intent(DetalleAlarmas.this, MapaAlertas.class);
                                        i.putExtra("Alertalatitudes",lat);
                                        i.putExtra("Alertalongitudes",lon);
                                        i.putExtra("alertaid",alertas_id);
                                        i.putExtra("tipo",valor);
                                        i.putExtra("tipoDelito",tipo_delito.getSelectedItem().toString());
                                        i.putExtra("des",des);
                                        i.putExtra("direccion",direccion);
                                        i.putExtra("estado",est);
                                        startActivity(i);
                                        finish();
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Esperar que cargue la dirección de mapa", Toast.LENGTH_LONG).show();
        }

    }

    public void checkpermisos() {
        if ((ContextCompat.checkSelfPermission(DetalleAlarmas.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(DetalleAlarmas.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(DetalleAlarmas.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }


    public void dragMapa() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.getFusedLocationProviderClient(DetalleAlarmas.this).getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf((location.getLongitude()));
                    LatLng Ubica = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Ubica, 15));
                    final Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                    mIni = mMap.addMarker(new MarkerOptions()
                            .position(Ubica)
                            .draggable(true)
                            .title("Inicio"));
                    mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                        @Override
                        public void onCameraIdle() {
                            LatLng center = mMap.getCameraPosition().target;
                            mIni.remove();
                            MarkerOptions m = new MarkerOptions();
                            m.title("Inicio");
                            m.position(center);
                            mIni = mMap.addMarker(m);
                            lat = String.valueOf(mIni.getPosition().latitude);
                            lon = String.valueOf(mIni.getPosition().longitude);

                            try {
                                List<Address> dire = geo.getFromLocation(mIni.getPosition().latitude,
                                        mIni.getPosition().longitude,1);
                                direc.setText(dire.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            }
        });


    }



    public void enviarConFoto() {

        validaciondireccion = direc.getText().toString();
        if( !validaciondireccion.equals("cargando...")){

            if (!(tipo_delito.getSelectedItem().toString().isEmpty())) {



                RequestParams params1 = new RequestParams();


                try {
                    //String imagen = "0";
                    File imagen = new File(resultUriRecortada.getPath());
                    //params1.put("nombre", act_nombreUsuario.getText().toString());
                    //params1.put("sexo", spn_sexo.getSelectedItem().toString());
                    tipoDelito = tipo_delito.getSelectedItem().toString();
                    direccion = direc.getText().toString();
                    des = edt_des.getText().toString();

                    if (valor.equals("ACCIDENTE")) {
                        tipoNotificacion = valor;
                    }
                    if (valor.equals("ZONAVIAL")) {
                        tipoNotificacion = valor;
                    }
                    if (valor.equals("DELITO")) {
                        tipoNotificacion = tipoDelito;
                    }

                    params1.put("imagen", imagen);
                    params1.put("id_distrito", pref.getDistritoIdPref());
                    params1.put("id_usuario", pref.getIdUsuarioPref());
                    params1.put("tipo", valor);
                    params1.put("tipo_delito",tipoDelito );
                    params1.put("calle_nombre",direccion );
                    params1.put("calle_x", lat);
                    params1.put("calle_y", lon);
                    params1.put("descripcion", des);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                AsyncHttpClient client = new AsyncHttpClient();
                client.post("https://"+IP+"/index.php?c=Alerta&a=guardar&key_mobile=123456asdfgh", params1, new AsyncHttpResponseHandler() {

                    ProgressDialog loading;

                    @Override
                    public void onStart() {
                        super.onStart();
                        loading = new ProgressDialog(DetalleAlarmas.this);
                        loading.setTitle("Sipsi");
                        loading.setMessage("Por favor espere...");
                        loading.setIndeterminate(false);
                        loading.setCancelable(false);
                        loading.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        loading.dismiss();

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        if (statusCode == 200) {
                            //Toast.makeText(getApplicationContext(), "valo"+ responseBody.toString(), Toast.LENGTH_SHORT).show();

                            String est = "atendido";
                            try {
                                respuesta = new String(responseBody, "UTF-8");
                                Log.i("delitope", "onSuccess: " + respuesta);


                                try {
                                    json_data = new JSONObject(respuesta);
                                    JSONArray resultJSON = json_data.getJSONArray("results");
                                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                                    valorcodigo = jsonNodev.optString("valor");
                                    alertas_id=jsonNodev.optString("id_alerta");
                                    Log.i("alertas", "alertas id: " + alertas_id);

                                    if (valorcodigo.equals("1") || !alertas_id.isEmpty()){
                                        enviarAlertas(tipoNotificacion,direccion);
                                        Intent i = new Intent(DetalleAlarmas.this, MapaAlertas.class);
                                        i.putExtra("Alertalatitudes",lat);
                                        i.putExtra("Alertalongitudes",lon);
                                        i.putExtra("alertaid",alertas_id);
                                        i.putExtra("tipo",valor);
                                        i.putExtra("tipoDelito",tipo_delito.getSelectedItem().toString());
                                        i.putExtra("des",des);
                                        i.putExtra("direccion",direccion);
                                        i.putExtra("estado",est);
                                        startActivity(i);
                                        finish();
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Esperar que cargue la dirección de mapa", Toast.LENGTH_LONG).show();
        }


    }

    private void enviarAlertas(final String tipoN , final String direcN) {
        String url ="https://"+IP+"/index.php?c=Alerta&a=notificar_alerta&key_mobile=123456asdfgh";
        //String url ="http://www.guabba.com/accidentestransito/app/controllers/prueba.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("1")){

                } else {
                    //Toast.makeText(context,"la fruta ",Toast.LENGTH_SHORT).show();
                    Log.e("notificacion_alertas: ",""+response);
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();
                //String to = "egX-GaUDnsk:APA91bFJC_iJHfJ_2Zyf06Smpr2EVdlsNyy6KxvEi5KSYJBuzZV5hd0ATfPJ77zPV_akxIDZ5SfCswlng8TzEmxY38aX4a0I3a3RylA5vzsBN4LxhkHJVyABJfsCd3X4mINPH9Xua3cf";
                parametros.put("id_distrito",pref.getDistritoIdPref());
                parametros.put("tipo",tipoN);
                parametros.put("direccion",direcN);
                //parametros.put("to",to);

                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }


    public void uploadMultipart(String idAlertas) {


        String path = resultUriRecortada.getPath();

        //File path = new File(resultUriRecortada.getPath());

        try {
            String uploadId = UUID.randomUUID().toString();
            Log.e("subida", "uploadMultipart: " + path.toString() );
            PendingIntent clickIntent = PendingIntent.getActivity(context, 1,
                    new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, urlGuardar)
                    .addFileToUpload(path, "imagen") //Adding file
                    .addParameter("id_alerta", idAlertas) //Adding text parameter to the request
                    .setNotificationConfig(getNotificationConfig(uploadId,R.string.cargando))
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(context, exc.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("subida", "uploadMultipart: " + exc.toString() );
        }
    }

    protected UploadNotificationConfig getNotificationConfig(final String uploadId, @StringRes int title) {
        UploadNotificationConfig config = new UploadNotificationConfig();


        PendingIntent clickIntent = PendingIntent.getActivity(
                context, 1, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        config.setTitleForAllStatuses(getString(title))
                .setRingToneEnabled(false)
                .setClickIntentForAllStatuses(clickIntent)
                .setClearOnActionForAllStatuses(true);

        config.getProgress().message = "Subiendo " + UPLOADED_FILES + " de " + TOTAL_FILES
                + " a " + UPLOAD_RATE + " - " + PROGRESS;
        config.getProgress().iconResourceID = R.drawable.posible;
        config.getProgress().iconColorResourceID = Color.BLUE;

        config.getCompleted().message = "Subida completada exitosamente en " + ELAPSED_TIME;
        config.getCompleted().iconResourceID = R.drawable.posible;
        config.getCompleted().iconColorResourceID = Color.GREEN;

        config.getError().message = "Error al Cargar Imagen";
        config.getError().iconResourceID = R.drawable.posible;
        config.getError().iconColorResourceID = Color.RED;

        config.getCancelled().message = "\n" +
                "La carga ha sido cancelada";
        config.getCancelled().iconResourceID = R.drawable.posible;
        config.getCancelled().iconColorResourceID = Color.YELLOW;

        return config;
    }
}


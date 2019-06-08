package com.bufeotec.sipcsi.Activitys;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bufeotec.sipcsi.Adapter.AdaptadorListadoQuejas;
import com.bufeotec.sipcsi.Models.Areas;
import com.bufeotec.sipcsi.Models.Queja;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bufeotec.sipcsi.Principal.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;
import static com.bufeotec.sipcsi.Principal.MainActivity.usuarioid;

//import de.hdodenhof.circleimageview.CircleImageView;

public class PuebloOpina extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.btn_bottom_sheet)
    Button btnBottomSheet;

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    BottomSheetBehavior sheetBehavior;

    static AdaptadorListadoQuejas adaptadorListadoQuejas;
    public ArrayList<Queja> arrayqueja;
    public static ArrayList<Queja> arrayquejactual;
    public static ArrayList<Areas> arrayArea;
    ArrayList<String> arrayarea;
    DataConnection dc,dc2;
    static DataConnection dc1;
    static RecyclerView rcv_quejas;
    static ProgressBar progressBar;
    static CardView cdv_mensaje;
//    CircleImageView civ_fotoUsuario;
    TextView txt_nombreUsuario;
    LinearLayout ll_adjuntarFoto;
    EditText edt_descripcionQeuja;
    Button btn_Camara, btn_registrarQeuja;
    CheckBox cbx_adjuntarfoto;
    Spinner spn_areas;
    ImageView  img_fotoQueja;
    public Queja queja;
    static Activity activity;
    public Context context;
    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    public Uri output,resultUriRecortada;
    String userChoosenTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pueblo_opina);
        ButterKnife.bind(this);

      //  civ_fotoUsuario = findViewById(R.id.civ_fotoUsuario);
        rcv_quejas = findViewById(R.id.rcv_quejas);
        progressBar = findViewById(R.id.progressbar);
        cdv_mensaje = findViewById(R.id.cdv_mensaje);
        txt_nombreUsuario = findViewById(R.id.txt_nombreUsuario);
        edt_descripcionQeuja = findViewById(R.id.edt_descripcionQeuja);
        img_fotoQueja = findViewById(R.id.img_fotoQueja);
        spn_areas = findViewById(R.id.spn_areas);
        btn_registrarQeuja = findViewById(R.id.btn_registrarQeuja);
        btn_Camara = findViewById(R.id.btn_Camara);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        cbx_adjuntarfoto = findViewById(R.id.cbx_adjuntarfoto);
        ll_adjuntarFoto = findViewById(R.id.ll_adjuntarFoto);

        dc = new DataConnection(activity,"listarQuejas",new Queja(usuarioid),false);
        new GetQueja().execute();
        dc2 = new DataConnection(activity,"listarAreas",false);
        new GetAreas().execute();

        spn_areas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btn_registrarQeuja.setOnClickListener(this);
        btn_Camara.setOnClickListener(this);
        context = getApplicationContext();

        if((ContextCompat.checkSelfPermission(PuebloOpina.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(PuebloOpina.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(PuebloOpina.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

        cbx_adjuntarfoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ll_adjuntarFoto.setVisibility(View.VISIBLE);
                }else{
                    ll_adjuntarFoto.setVisibility(View.GONE);
                }
            }
        });


        setSupportActionBar(toolbar);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        btnBottomSheet.setText("Cerrar");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        btnBottomSheet.setText("Publicar una Queja");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    public class GetAreas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayarea =  new ArrayList<String>();
            arrayArea = dc2.getListaAreas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(context,"size"+arrayArea.size(),Toast.LENGTH_LONG).show();

            for (Areas obj :arrayArea){
                arrayarea.add(obj.getArea_nombre());
            }
            //progressBar.setVisibility(ProgressBar.INVISIBLE);
            arrayarea.add(0,"Seleccione");
            ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(context,R.layout.spiner_item,arrayarea);
            adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_areas.setAdapter(adapEquipos);

        }
    }
    public class GetQueja extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayqueja = dc.getListaQuejas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(ProgressBar.INVISIBLE);


            //Toast.makeText(getActivity(),"Z "+arrayempresas.size(),Toast.LENGTH_SHORT).show();


            GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_quejas.setLayoutManager(linearLayoutManager);

            adaptadorListadoQuejas = new AdaptadorListadoQuejas(context, arrayqueja, R.layout.rcv_item_list_quejas, new AdaptadorListadoQuejas.OnItemClickListener() {
                @Override
                public void onItemClick(Queja queja, final int position) {

                    //Toast.makeText(getActivity(),"ID "+empresas.getEmpresas_id(), Toast.LENGTH_SHORT).show();

                    /*Intent intent = new Intent(getContext(), DetalleNegocio.class);
                    intent.putExtra("id_empresa",empresas.getEmpresas_id());
                    startActivity(intent);*/

                }
            });
            rcv_quejas.setAdapter(adaptadorListadoQuejas);

            if (arrayqueja.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    public static class ActualizarQueja extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayquejactual= dc1.getListaQuejas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            GridLayoutManager linearLayoutManager = new GridLayoutManager(activity, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_quejas.setLayoutManager(linearLayoutManager);

            adaptadorListadoQuejas = new AdaptadorListadoQuejas(activity, arrayquejactual, R.layout.rcv_item_list_quejas, new AdaptadorListadoQuejas.OnItemClickListener() {
                @Override
                public void onItemClick(Queja queja, final int position) {

                   /* Intent intent = new Intent(context,DetalleNegocio.class);
                    intent.putExtra("id_empresa",empresas.getEmpresas_id());
                    context.startActivity(intent);*/
                }
            });

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            rcv_quejas.setAdapter(adaptadorListadoQuejas);


            if( arrayquejactual.size()>0){
                cdv_mensaje.setVisibility(View.INVISIBLE);
            }else{
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    public static void ActualizarQueja(){
        dc1 = new DataConnection(activity,"listarQuejas",false);
        new ActualizarQueja().execute();
    }
    /**
     * manually opening / closing bottom sheet on button click
     */
    @OnClick(R.id.btn_bottom_sheet)
    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            btnBottomSheet.setText("Cerrar");
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            btnBottomSheet.setText("Publicar una Queja");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_Camara:
                selectImage();
                break;

            case R.id.btn_registrarQeuja:
                if (cbx_adjuntarfoto.isChecked()) {
                    if (!(edt_descripcionQeuja.getText().toString().isEmpty())&!(spn_areas.getSelectedItem().toString().equals("Seleccione"))){
                        RequestParams params1 = new RequestParams();
                        try {
                            File imagen = new File(resultUriRecortada.getPath());
                            params1.put("usuario_id", usuarioid);
                            params1.put("distrito_id", "1");
                            params1.put("destino", arrayArea.get(spn_areas.getSelectedItemPosition()-1).getArea_nombre());
                            params1.put("queja", edt_descripcionQeuja.getText().toString());
                            params1.put("imagen", imagen);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post("http://" + IP + "/index.php?c=Pueblo&a=guardar&key_mobile=123456asdfgh", params1, new AsyncHttpResponseHandler() {

                            String respuesta = null;
                            ProgressDialog loading;

                            @Override
                            public void onStart() {
                                super.onStart();
                                loading = new ProgressDialog(PuebloOpina.this);
                                loading.setTitle("SIPSI");
                                loading.setMessage("Por favor espere...");
                                loading.setIndeterminate(false);
                                loading.setCancelable(false);
                                loading.show();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                if (statusCode == 200) {

                                    try {
                                        respuesta = new String(responseBody, "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                    if (respuesta.equals("1")) {
                                        finish();
                                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                                        // FragmentEquiposHijo.ActualizarEquipo();

                                    } else {
                                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFinish() {
                               // super.onFinish();
                                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                loading.dismiss();
                               // ActualizarQueja()

                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Cargue la foto..", Toast.LENGTH_LONG).show();
                    }
                }


                /////
                else {
                    if (!(edt_descripcionQeuja.getText().toString().isEmpty())&!(spn_areas.getSelectedItem().toString().equals("Seleccione"))) {

                        RequestParams params1 = new RequestParams();
                        String imagen = " ";
                        params1.put("usuario_id", usuarioid);
                        params1.put("distrito_id", "1");
                        params1.put("destino", arrayArea.get(spn_areas.getSelectedItemPosition()-1).getArea_nombre());
                        params1.put("queja", edt_descripcionQeuja.getText().toString());
                        params1.put("imagen", imagen);

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post("http://" + IP + "/index.php?c=Pueblo&a=guardar&key_mobile=123456asdfgh", params1, new AsyncHttpResponseHandler() {

                            String respuesta = null;
                            ProgressDialog loading;

                            @Override
                            public void onStart() {
                                super.onStart();
                                loading = new ProgressDialog(PuebloOpina.this);
                                loading.setTitle("SIPSI");
                                loading.setMessage("Por favor espere...");
                                loading.setIndeterminate(false);
                                loading.setCancelable(false);
                                loading.show();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                if (statusCode == 200) {

                                    try {
                                        respuesta = new String(responseBody, "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                    if (respuesta.equals("1")) {
                                        finish();
                                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                                        // FragmentEquiposHijo.ActualizarEquipo();

                                    } else {
                                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFinish() {
                                //super.onFinish();
                                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                loading.dismiss();
                                //ActualizarQueja();


                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                    }
                }



                break;


        }
    }


    @Override
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

                img_fotoQueja.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));

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

    private void selectImage() {
        final CharSequence[] items = { "Camara", "Galería","Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PuebloOpina.this);
        builder.setTitle("Seleccione :");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Camara")) {
                    userChoosenTask ="Camara";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File carpetas = new File(Environment.getExternalStorageDirectory() + "/Sipsi/","Queja");
                    carpetas.mkdirs();

                    String aleatorio = usuarioid+"_"+ edt_descripcionQeuja.getText().toString();
                    String nombre = aleatorio +".jpg";
                    File imagen = new File(carpetas,nombre);

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                    {

                        String authorities=getApplicationContext().getPackageName()+".provider";
                        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(),authorities,imagen);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    }else
                    {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));

                    }
                    output = Uri.fromFile(imagen);
                    startActivityForResult(intent,REQUEST_CAMERA);

                }else if (items[item].equals("Galería")){
                    userChoosenTask ="Galería";


                    Intent intentgaleria = new Intent(Intent.ACTION_PICK);
                    intentgaleria.setType("image/*");
                    if (intentgaleria.resolveActivity(getPackageManager())!=null){


                        startActivityForResult(intentgaleria,SELET_GALERRY);
                    }
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed () {
        finish();
    }
}

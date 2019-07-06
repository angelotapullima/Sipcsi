package com.bufeotec.sipcsi.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bufeotec.sipcsi.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    EditText act_nombreUsuario,act_emailUsuario,act_dniUsuario,act_UsuarioUsuario,act_apeUsuario;

    Spinner spn_rol,spn_distrito;
    EditText edt_clave,edt_confirmarClave;
    public Context context;
    Button btn_registrar;
    JSONObject json_data;
    String distrito,dis,Roles,rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        context=this;


        showToolbar("Registro" ,true);

        act_nombreUsuario = findViewById(R.id.act_nombreUsuario);
        act_emailUsuario = findViewById(R.id.act_emailUsuario);
        act_apeUsuario = findViewById(R.id.act_apeUsuario);
        act_dniUsuario = findViewById(R.id.act_dniUsuario);
        act_UsuarioUsuario = findViewById(R.id.act_UsuarioUsuario);
        spn_distrito = findViewById(R.id.spn_distrito);
        edt_clave = findViewById(R.id.edt_clave);
        edt_confirmarClave = findViewById(R.id.edt_confirmarClave);
        btn_registrar = findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(this);
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onClick(View v) {
        distrito = spn_distrito.getSelectedItem().toString();
        //Roles = spn_rol.getSelectedItem().toString();


        rol="4";


        if (distrito.equals("Iquitos")){
            dis="1";
        }if (distrito.equals("Punchana")){
            dis="2";
        }if (distrito.equals("Belen")){
            dis="3";
        }if (distrito.equals("San Juan Bautista")){
            dis="4";
        }

        if(v.equals(btn_registrar)){
            if( !(act_nombreUsuario.getText().toString().isEmpty()) &&
                    !(act_apeUsuario.getText().toString().isEmpty())&&
                    !(act_emailUsuario.getText().toString().isEmpty()) &&
                    !(act_dniUsuario.getText().toString().isEmpty())  &&
                    !(act_UsuarioUsuario.getText().toString().isEmpty())  &&
                    !(edt_clave.getText().toString().isEmpty()) &&
                    !(edt_confirmarClave.getText().toString().isEmpty())&&
                    !(spn_distrito.getSelectedItem().toString().isEmpty())){

                if(edt_clave.getText().toString().equals(edt_confirmarClave.getText().toString())){
                    RequestParams params1 = new RequestParams();


                    params1.put("nombre", act_nombreUsuario.getText().toString());
                    params1.put("apellido", act_apeUsuario.getText().toString());
                    //params1.put("email", act_emailUsuario.getText().toString());
                    params1.put("dni", act_dniUsuario.getText().toString());
                    params1.put("nickname", act_UsuarioUsuario.getText().toString());
                    params1.put("contrasenha", edt_clave.getText().toString());
                    params1.put("distrito", dis);
                    params1.put("rol",rol );




                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post("http://www.guabba.com/accidentestransito/index.php?c=Usuario&a=registrar_ws&key_mobile=123456asdfgh",
                            params1, new AsyncHttpResponseHandler() {
                        String respuesta = null;
                        ProgressDialog loading;

                        @Override
                        public void onStart() {
                            super.onStart();
                            loading = new ProgressDialog(Registro.this);
                            loading.setTitle("Sipsi");
                            loading.setMessage("Por favor espere...");
                            loading.setIndeterminate(false);
                            loading.setCancelable(false);
                            loading.show();
                        }



                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200){
                                try {
                                    respuesta = new String(responseBody, "UTF-8");
                                    Log.i("Registro", "onSuccess: " +respuesta);



                                    if (respuesta.equals("1")) {

                                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                                        finish();
                                        // FragmentEquiposHijo.ActualizarEquipo();

                                    } else {
                                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }




                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplicationContext(),"Error al registrar", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            loading.dismiss();

                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();


                }
            }
        }
    }
}

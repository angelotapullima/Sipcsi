package com.bufeotec.sipcsi.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Models.Usuario;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;

public class EditarPerfil extends AppCompatActivity {

    EditText perfil_nombre,perfil_apellido,perfil_usuario,perfil_distrito,perfil_email;
    LinearLayout contraseña;
    Button btnGuardar;
    DataConnection dc;
    String nombre,apellido,distrito,usuario,email,id,dni;
    Usuario user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        nombre = getIntent().getExtras().getString("nombre");
        apellido = getIntent().getExtras().getString("apellido");
        distrito = getIntent().getExtras().getString("distrito");
        usuario = getIntent().getExtras().getString("usuario");
        id = getIntent().getExtras().getString("id");
        dni = getIntent().getExtras().getString("dni");
        showToolbar("Editar Perfil", true);


        perfil_nombre=findViewById(R.id.perfil_nombre);
        perfil_apellido=findViewById(R.id.perfil_apellido);
        perfil_distrito=findViewById(R.id.perfil_distrito);
        perfil_usuario=findViewById(R.id.perfil_usuario);
        perfil_email=findViewById(R.id.perfil_email);



        perfil_nombre.setText(nombre);
        perfil_apellido.setText(apellido);
        perfil_usuario.setText(usuario);
        perfil_distrito.setText(distrito);


        btnGuardar=findViewById(R.id.btnGuardar);
        contraseña=findViewById(R.id.btnContra);




        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();

            }
        });

        contraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EditarPerfil.this,CambiarContrasena.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

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

    StringRequest stringRequest;
    private void enviar() {
        String url ="https://"+IP+"/index.php?c=Usuario&a=editar_perfil&key_mobile=123456asdfgh";
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
                parametros.put("usuario_nombre",perfil_nombre.getText().toString());
                parametros.put("usuario_apellido",perfil_apellido.getText().toString());
                parametros.put("distrito_id",perfil_distrito.getText().toString());
                parametros.put("usuario_nickname",perfil_usuario.getText().toString());
                parametros.put("usuario_id",id);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}

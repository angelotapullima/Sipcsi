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

import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.bufeotec.sipcsi.Principal.MainActivity;

import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;

public class CambiarContrasena extends AppCompatActivity {


    EditText contraNueva,contraAntigua,contraNuevaRepetida;

    Button btnContra;
    String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);



        contraNueva=findViewById(R.id.contraNueva);
        contraAntigua=findViewById(R.id.contraAntigua);
        contraNuevaRepetida=findViewById(R.id.contraNuevaRepetida);
        btnContra=findViewById(R.id.btnCambiarContra);

        showToolbar("Cambiar Contraseña",true);
        id=getIntent().getExtras().getString("id");


        btnContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (contraNueva.getText().toString().equals(contraNuevaRepetida.getText().toString())){
                    cambiar();
                }else{
                    Toast.makeText(CambiarContrasena.this, "Las Contraseñas no Coinciden", Toast.LENGTH_SHORT).show();
                }

                cambiar();

            }
        });
    }

    StringRequest stringRequest;
    private void cambiar() {
        String url ="https://"+IP+"/index.php?c=Usuario&a=editar_contrasenha&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.e("respuesta de contraseña", "onResponse: " + response.trim().toString() );
                if(response.trim().equalsIgnoreCase("1")){


                    Toast.makeText(CambiarContrasena.this, "Contraseña Modifcicada Satisfactoriamente", Toast.LENGTH_SHORT).show();
                    Intent i =  new Intent(CambiarContrasena.this, MainActivity.class);
                    startActivity(i);
                }else if(response.trim().equalsIgnoreCase("2")){
                    Toast.makeText(CambiarContrasena.this, "Hubo un Problema", Toast.LENGTH_SHORT).show();
                    contraAntigua.setText("");
                    contraNueva.setText("");
                    contraNuevaRepetida.setText("");
                }
                else {
                    //Toast.makeText(context,"la fruta ",Toast.LENGTH_SHORT).show();
                    Toast.makeText(CambiarContrasena.this, "La Contrasela Antigua no Coincide", Toast.LENGTH_SHORT).show();
                    contraAntigua.setText("");
                    contraNueva.setText("");
                    contraNuevaRepetida.setText("");


            }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);




                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_usuario",id);
                parametros.put("contrasenha_nueva",contraNueva.getText().toString());
                parametros.put("contrasenha_antigua",contraAntigua.getText().toString());

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
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
}

package com.bufeotec.sipcsi.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bufeotec.sipcsi.Models.Usuario;
import com.bufeotec.sipcsi.Principal.MainActivity;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.DataConnection;

public class Login extends AppCompatActivity {

    private static final String TAG ="" ;
    EditText edt_password;
    Button btn_login;
    TextView txt_resgistrate;
    public Usuario obj;
    DataConnection dc;
    FrameLayout frameGif;
    ImageView gif;
    AutoCompleteTextView act_usuario;
    String dirImagen ="http://www.guabba.com/accidentestransito/media/loader.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        act_usuario = findViewById(R.id.act_usuario);
        btn_login = findViewById(R.id.btn_login);
        edt_password = findViewById(R.id.edt_password);
        txt_resgistrate = findViewById(R.id.txt_resgistrate);
        frameGif=findViewById(R.id.FrameGif);
        gif=findViewById(R.id.gif);



        //entrar();


        txt_resgistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registro.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = act_usuario.getText().toString();
                String clave = edt_password.getText().toString();
                //frameGif.setVisibility(View.VISIBLE);
                if( !(usuario.isEmpty()) && !(clave.isEmpty())){

                    obj = new Usuario();
                    obj.setUsuario_nickname(usuario);
                    obj.setUsuario_contrasenha(clave);

                    dc = new DataConnection(Login.this,"loginUsuario",obj,true);
                    //guardarAcceso(usuario,clave);


                }else {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();

                    //frameGif.setVisibility(View.GONE);

                }


            }
        });
        checkpermisos();

    }

    public void checkpermisos() {
        if ((ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(Login.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
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







}

package com.bufeotec.sipcsi.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.bufeotec.sipcsi.Principal.MainActivity;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    public TimerTask task;
    private static final long TIEMPO_SPLASH_SCREEN = 2000;
    Preferences preferencesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferencesUser = new Preferences(getApplicationContext());

        loadingAndDisplayContent();

        //Especificamos la tarea que debe hacer
        task = new TimerTask() {
            @Override
            public void run() {
                //Obtnemos los datos del SharedPreferences, Si el usuario de logueo antes, nos manda directo al MenuPrincipal,caso contrario debe loguearse
                if(!preferencesUser.getIdUsuarioPref().equals("") ){
                    Intent iprincipal = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(iprincipal);
                    finish();
                }else{
                    Intent i1= new Intent(getApplicationContext(),Login.class);
                    startActivity(i1);
                    finish();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task,TIEMPO_SPLASH_SCREEN);
    }

    private void loadingAndDisplayContent() {
        final LinearLayout lyt_progress =  findViewById(R.id.lyt_progress);
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);
    }
}

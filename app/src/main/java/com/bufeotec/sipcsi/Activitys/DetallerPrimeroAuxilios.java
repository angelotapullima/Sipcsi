package com.bufeotec.sipcsi.Activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bufeotec.sipcsi.R;

public class DetallerPrimeroAuxilios extends AppCompatActivity {

    TextView Dtitulo,Ddes;
    String titulo , des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaller_primero_auxilios);

        showToolbar("Primeros Auxilios",true);

        titulo = getIntent().getExtras().getString("titulo");
        des = getIntent().getExtras().getString("des");


        Dtitulo=findViewById(R.id.Dtitulo);
        Ddes=findViewById(R.id.Ddes);

        Dtitulo.setText(titulo);
        Ddes.setText(des);
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

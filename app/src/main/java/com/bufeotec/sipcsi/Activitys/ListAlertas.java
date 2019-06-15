package com.bufeotec.sipcsi.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Adapter.AlertasAdapter;
import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;

public class ListAlertas extends AppCompatActivity {

    DataConnection dc;
    Activity activity;
    CardView cdv_mensaje;
    ProgressBar progressBar;
    RecyclerView rcv_alertas_activity;
    ArrayList<Alertas> listAlertas;
    String id_alerta,Alertalatitudes,Alertalongitudes,tipo,tipo_delito,des,direc,fec;
    AlertasAdapter alertadAdapter;
    Alertas alers;
    StringRequest stringRequest;
    Context context;
    Preferences pref;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listalertas);
        context = this;
        showToolbar("Lista De Alertas",true);
        pref=new Preferences(getApplicationContext());

        progressBar = findViewById(R.id.progressbar);
        rcv_alertas_activity = findViewById(R.id.rcv_alertas_activity);
        cdv_mensaje = findViewById(R.id.cdv_mensaje);
        cdv_mensaje.setVisibility(View.INVISIBLE);


        alers= new Alertas();
        alers.setDistrito_id(pref.getDistritoIdPref());
        alers.setUsuario_id(pref.getIdUsuarioPref());


        dc = new DataConnection(activity,"listarAlertas",alers,false);
        new GetAlertas().execute();
    }

    public class GetAlertas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listAlertas = dc.getListaAlertas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            recycler();


        }
    }

    private   void recycler() {



        progressBar.setVisibility(ProgressBar.INVISIBLE);
        rcv_alertas_activity.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcv_alertas_activity.setLayoutManager(layoutManager);

        alertadAdapter = new AlertasAdapter(activity, listAlertas, R.layout.rcv_item_alertas, new AlertasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Alertas alertas, int position) {

                id_alerta= alertas.getAlerta_id();
                Alertalatitudes= alertas.getCalle_x();
                Alertalongitudes= alertas.getCalle_y();
                tipo= alertas.getAlerta_tipo();
                tipo_delito= alertas.getAlerta_tipo_delito();
                des= alertas.getAlerta_descripcion();
                direc= alertas.getCalle_nombre();
                fec=alertas.getAlerta_fecha();

                atenderAlerta();






            }
        });

        rcv_alertas_activity.setAdapter(alertadAdapter);
        if( listAlertas.size()>0){
            cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            cdv_mensaje.setVisibility(View.VISIBLE);
        }
    }

    private void atenderAlerta() {
        //ProgressDialog loading = ProgressDialog.show(context, "Sipsi", "Por favor espere...", false, false);
        String url ="https://"+IP+"/index.php?c=Alerta&a=atender_alerta&key_mobile=123456asdfgh";
        //String url ="https://www.guabba.com/motokapp/ubicacion.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context,"Se registro correctamente ",Toast.LENGTH_SHORT).show();
                Log.i("atenderalertaActivity: ",""+response);
                String estado = "alertas";
                if (response.equals("1")){
                    Intent i =  new Intent(ListAlertas.this, MapaAlertas.class);
                    i.putExtra("alertaid",id_alerta);
                    i.putExtra("Alertalatitudes",Alertalatitudes);
                    i.putExtra("Alertalongitudes",Alertalongitudes);
                    i.putExtra("tipo",tipo);
                    i.putExtra("tipoDelito",tipo_delito);
                    i.putExtra("des",des);
                    i.putExtra("direccion",direc);
                    i.putExtra("fecha",fec);
                    i.putExtra("estado",estado);
                    startActivity(i);

                }else{
                    Toast.makeText(context, "la alerta no se atendio correctamente ", Toast.LENGTH_SHORT).show();
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


                parametros.put("id_usuario", id);
                parametros.put("id_alerta", id_alerta);
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent i = new Intent(ListAlertas.this,Login.class);
        startActivity(i);
        return false;
    }


}

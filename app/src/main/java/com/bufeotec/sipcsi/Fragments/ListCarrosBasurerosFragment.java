package com.bufeotec.sipcsi.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bufeotec.sipcsi.Activitys.MonitoreoBasurero;
import com.bufeotec.sipcsi.Adapter.ListBasurerosAdapter;
import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;


public class ListCarrosBasurerosFragment extends Fragment {



    Activity activity;
    Context context;
    DataConnection dc;
    CardView cdv_mensaje;
    ListBasurerosAdapter listBasurerosAdapter;
    ProgressBar progressBar;
    RecyclerView rcv_basureros;
    Preferences pref;
    public ArrayList<Vehiculos> arrayBasureros;



    private OnFragmentInteractionListener mListener;

    public ListCarrosBasurerosFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_carros_basureros, container, false);

        activity = getActivity();
        context = getContext();
        pref=new Preferences(context);
        getActivity().setTitle("Lista De Vehiculos");
        progressBar = view.findViewById(R.id.progressbar);
        rcv_basureros = view.findViewById(R.id.rcv_basureros);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);
        cdv_mensaje.setVisibility(View.INVISIBLE);


        dc = new DataConnection(getActivity(),"listarBasureros",pref.getIdUsuarioPref(),false);
        new GetBasureros().execute();

        return view;
    }



    public class GetBasureros extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayBasureros = dc.getListaBasureros();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            recycler();


        }
    }

    private void recycler() {



        progressBar.setVisibility(ProgressBar.INVISIBLE);
        rcv_basureros.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_basureros.setLayoutManager(layoutManager);

        listBasurerosAdapter = new ListBasurerosAdapter(activity, arrayBasureros, R.layout.rcv_item_basureros, new ListBasurerosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vehiculos vehiculos, int position) {

                String id = vehiculos.getId_vehiculo();
                String lat = vehiculos.getLatitud();
                String lon = vehiculos.getLongitud();
                String to = vehiculos.getTok();
                String placa = vehiculos.getPlaca();

                Intent i =  new Intent(activity, MonitoreoBasurero.class);

                i.putExtra("lat",lat);
                i.putExtra("long",lon);
                i.putExtra("id_vehiculo",id);
                i.putExtra("tok",to);
                i.putExtra("placa",placa);
                startActivity(i);



            }
        });

        rcv_basureros.setAdapter(listBasurerosAdapter);
        if( arrayBasureros.size()>0){
            cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            cdv_mensaje.setVisibility(View.VISIBLE);
        }
    }





    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

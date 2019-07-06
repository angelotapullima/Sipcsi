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

import com.bufeotec.sipcsi.Activitys.MapaAlertas;
import com.bufeotec.sipcsi.Adapter.AdapterMisAlertas.AdapterMiListaAlertas;
import com.bufeotec.sipcsi.Adapter.AdapterMisAlertas.AdapterMiListaAlertasPendientes;
import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;

public class ListMisAlertas extends Fragment {

    Activity activity;
    Context context;
    DataConnection dc;
    CardView cdv_mensaje;
    ProgressBar progressBar;
    ArrayList<Alertas> listAlertasHechas;
    AdapterMiListaAlertas adapterMiListaAlertas;
    AdapterMiListaAlertasPendientes adapterMiListaAlertasPendientes;
    RecyclerView rcv_misalertashechas_atendidas,rcv_misalertashechas_pendientes;
    String malid_alerta, malAlertalatitudes,malAlertalongitudes,maltipo,maltipo_delito,maldes,maldirec,malfec;
    ArrayList<Alertas> Arraypendientes  = new ArrayList<>();
    ArrayList<Alertas> Arrayatendidos= new ArrayList<>();
    Preferences pref;



    private OnFragmentInteractionListener mListener;

    public ListMisAlertas() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_mis_alertas, container, false);
        activity = getActivity();
        context = getContext();
        pref= new Preferences(context);
        getActivity().setTitle("Mis ListAlertas");
        progressBar = view.findViewById(R.id.progressbar);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);
        cdv_mensaje.setVisibility(View.INVISIBLE);
        rcv_misalertashechas_pendientes=view.findViewById(R.id.rcv_misalertashechas_pendientes);
        rcv_misalertashechas_atendidas=view.findViewById(R.id.rcv_misalertashechas_atendidas);

        dc = new DataConnection(getActivity(),"listarAlertasHechas",pref.getIdUsuarioPref(),false);
        new ListMisAlertas.GetMisAlertasHechas().execute();
        return view;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public class GetMisAlertasHechas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listAlertasHechas= dc.getListaAlertasHechas();
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


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_misalertashechas_atendidas.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        rcv_misalertashechas_pendientes.setLayoutManager(layoutManager1);

        for (Alertas obj:listAlertasHechas){
            if (obj.getAlerta_estado().equals("2")){
                //
                Arrayatendidos.add(obj);
            }
        }
        for (Alertas obj:listAlertasHechas){
            if (obj.getAlerta_estado().equals("1")){
                //
                Arraypendientes.add(obj);
            }
        }


        adapterMiListaAlertasPendientes = new AdapterMiListaAlertasPendientes(activity, Arraypendientes, R.layout.rcv_item_misalertashechas,
                new AdapterMiListaAlertasPendientes.OnItemClickListener() {
                    @Override
                    public void onnItemClick(Alertas alertas, int position) {

                        malid_alerta= alertas.getAlerta_id();
                        malAlertalatitudes= alertas.getCalle_x();
                        malAlertalongitudes= alertas.getCalle_y();
                        maltipo= alertas.getAlerta_tipo();
                        maltipo_delito= alertas.getAlerta_tipo_delito();
                        maldes= alertas.getAlerta_descripcion();
                        maldirec= alertas.getCalle_nombre();
                        malfec=alertas.getAlerta_fecha();

                        String estado = "pendiente";

                        Intent i = new Intent(getContext(), MapaAlertas.class);
                        i.putExtra("alertaid",malid_alerta);
                        i.putExtra("Alertalatitudes",malAlertalatitudes);
                        i.putExtra("Alertalongitudes",malAlertalongitudes);
                        i.putExtra("tipo",maltipo);
                        i.putExtra("tipoDelito",maltipo_delito);
                        i.putExtra("des",maldes);
                        i.putExtra("direccion",maldirec);
                        i.putExtra("fecha",malfec);
                        i.putExtra("estado",estado);
                        startActivity(i);

                    }
                });

        adapterMiListaAlertas = new AdapterMiListaAlertas(activity, Arrayatendidos, R.layout.rcv_item_misalertashechas,
                new AdapterMiListaAlertas.OnItemClickListener() {
            @Override
            public void onItemClick(Alertas alertas, int position) {

                malid_alerta= alertas.getAlerta_id();
                malAlertalatitudes= alertas.getCalle_x();
                malAlertalongitudes= alertas.getCalle_y();
                maltipo= alertas.getAlerta_tipo();
                maltipo_delito= alertas.getAlerta_tipo_delito();
                maldes= alertas.getAlerta_descripcion();
                maldirec= alertas.getCalle_nombre();
                malfec=alertas.getAlerta_fecha();

                String estado = "atendido";

                Intent i = new Intent(getContext(), MapaAlertas.class);
                i.putExtra("alertaid",malid_alerta);
                i.putExtra("Alertalatitudes",malAlertalatitudes);
                i.putExtra("Alertalongitudes",malAlertalongitudes);
                i.putExtra("tipo",maltipo);
                i.putExtra("tipoDelito",maltipo_delito);
                i.putExtra("des",maldes);
                i.putExtra("direccion",maldirec);
                i.putExtra("fecha",malfec);
                i.putExtra("estado",estado);
                startActivity(i);


            }
        });

        rcv_misalertashechas_atendidas.setAdapter(adapterMiListaAlertas);
        rcv_misalertashechas_pendientes.setAdapter(adapterMiListaAlertasPendientes);
        rcv_misalertashechas_pendientes.setNestedScrollingEnabled(false);
        rcv_misalertashechas_atendidas.setNestedScrollingEnabled(false);


        if( listAlertasHechas.size()>0){
            cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            cdv_mensaje.setVisibility(View.VISIBLE);
        }


    }
}

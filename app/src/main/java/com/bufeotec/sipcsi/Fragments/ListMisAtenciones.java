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
import com.bufeotec.sipcsi.Adapter.AdapterMisAtenciones.AdapterMisAtenciones;
import com.bufeotec.sipcsi.Adapter.AdapterMisAtenciones.AdapterMisAtencionesPendientes;
import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;


public class ListMisAtenciones extends Fragment {


    Activity activity;
    Context context;
    DataConnection dc;
    CardView cdv_mensaje;
    ProgressBar progressBar;
    ArrayList<Alertas> listAtenciones;
    Preferences pref;
    ArrayList<Alertas> Atencionespendientes  = new ArrayList<>();
    ArrayList<Alertas> Atencionesatendidas= new ArrayList<>();
    AdapterMisAtenciones adapterMisAtenciones;
    AdapterMisAtencionesPendientes adapterMisAtencionesPendientes;

    RecyclerView rcv_misAtenciones,rcv_misAtenciones_pendientes;
    String maid_alerta, maAlertalatitudes,maAlertalongitudes,matipo,matipo_delito,mades,madirec,mafec;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListMisAtenciones() {
        // Required empty public constructor
    }



    public static ListMisAtenciones newInstance(String param1, String param2) {
        ListMisAtenciones fragment = new ListMisAtenciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_atenciones, container, false);
        activity = getActivity();
        context = getContext();
        pref=new Preferences(context);
        getActivity().setTitle("Mis Atenciones");
        progressBar = view.findViewById(R.id.progressbar);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);
        cdv_mensaje.setVisibility(View.INVISIBLE);
        rcv_misAtenciones=view.findViewById(R.id.rcv_misatenciones);
        rcv_misAtenciones_pendientes=view.findViewById(R.id.rcv_misAtenciones_pendientes);

        dc = new DataConnection(getActivity(),"listarAtenciones",pref.getIdUsuarioPref(),false);
        new ListMisAtenciones.GetMisatenciones().execute();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class GetMisatenciones extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listAtenciones= dc.getListaAtenciones();
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
        rcv_misAtenciones.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_misAtenciones.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        rcv_misAtenciones_pendientes.setLayoutManager(layoutManager1);


        for (Alertas obj:listAtenciones){
            if (obj.getAlerta_estado().equals("2")){
                //
                Atencionesatendidas.add(obj);
            }
        }
        for (Alertas obj:listAtenciones){
            if (obj.getAlerta_estado().equals("1")){
                //
                Atencionespendientes.add(obj);
            }
        }
        adapterMisAtenciones = new AdapterMisAtenciones(activity, Atencionesatendidas, R.layout.rcv_item_misatenciones, new AdapterMisAtenciones.OnItemClickListener() {
            @Override
            public void onItemClick(Alertas alertas, int position) {

                maid_alerta= alertas.getAlerta_id();
                maAlertalatitudes= alertas.getCalle_x();
                maAlertalongitudes= alertas.getCalle_y();
                matipo= alertas.getAlerta_tipo();
                matipo_delito= alertas.getAlerta_tipo_delito();
                mades= alertas.getAlerta_descripcion();
                madirec= alertas.getCalle_nombre();
                mafec=alertas.getAlerta_fecha();
                String estado = "atendido";

                Intent i = new Intent(getContext(), MapaAlertas.class);
                i.putExtra("alertaid",maid_alerta);
                i.putExtra("Alertalatitudes",maAlertalatitudes);
                i.putExtra("Alertalongitudes",maAlertalongitudes);
                i.putExtra("tipo",matipo);
                i.putExtra("tipoDelito",matipo_delito);
                i.putExtra("des",mades);
                i.putExtra("direccion",madirec);
                i.putExtra("fecha",mafec);
                i.putExtra("estado",estado);
                startActivity(i);


            }
        });

        adapterMisAtencionesPendientes =  new AdapterMisAtencionesPendientes(activity, Atencionespendientes, R.layout.rcv_item_misatenciones, new AdapterMisAtencionesPendientes.OnItemClickListener() {
            @Override
            public void onnItemClick(Alertas alertas, int position) {

                maid_alerta= alertas.getAlerta_id();
                maAlertalatitudes= alertas.getCalle_x();
                maAlertalongitudes= alertas.getCalle_y();
                matipo= alertas.getAlerta_tipo();
                matipo_delito= alertas.getAlerta_tipo_delito();
                mades= alertas.getAlerta_descripcion();
                madirec= alertas.getCalle_nombre();
                mafec=alertas.getAlerta_fecha();
                String estado = "atendidofff";

                Intent i = new Intent(getContext(), MapaAlertas.class);
                i.putExtra("alertaid",maid_alerta);
                i.putExtra("Alertalatitudes",maAlertalatitudes);
                i.putExtra("Alertalongitudes",maAlertalongitudes);
                i.putExtra("tipo",matipo);
                i.putExtra("tipoDelito",matipo_delito);
                i.putExtra("des",mades);
                i.putExtra("direccion",madirec);
                i.putExtra("fecha",mafec);
                i.putExtra("estado",estado);
                startActivity(i);
            }
        });

        rcv_misAtenciones.setAdapter(adapterMisAtenciones);
        rcv_misAtenciones_pendientes.setAdapter(adapterMisAtencionesPendientes);

        rcv_misAtenciones_pendientes.setNestedScrollingEnabled(false);
        rcv_misAtenciones.setNestedScrollingEnabled(false);
        if( listAtenciones.size()>0){
            cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            cdv_mensaje.setVisibility(View.VISIBLE);
        }
    }
}

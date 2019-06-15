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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListMisAlertas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMisAlertas.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMisAlertas newInstance(String param1, String param2) {
        ListMisAlertas fragment = new ListMisAlertas();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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

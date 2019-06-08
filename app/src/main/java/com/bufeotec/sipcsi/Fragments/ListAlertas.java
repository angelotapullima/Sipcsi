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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Activitys.MapaAlertas;
import com.bufeotec.sipcsi.Adapter.AlertasAdapter;
import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.DataConnection;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;
import static com.bufeotec.sipcsi.Principal.MainActivity.distrito;
import static com.bufeotec.sipcsi.Principal.MainActivity.usuarioid;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListAlertas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListAlertas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAlertas extends Fragment {


    Activity activity;
    Context context;
    DataConnection dc;
    CardView cdv_mensaje;
    ProgressBar progressBar;
    RecyclerView rcv_alertas;
    ArrayList<Alertas> listAlertas;
    StringRequest stringRequest;
    String id_alerta,Alertalatitudes,Alertalongitudes,tipo,tipo_delito,des,direc,fec;
    AlertasAdapter alertadAdapter;
    Alertas alers;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListAlertas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAlertas.
     */
    // TODO: Rename and change types and number of parameters
    public static ListAlertas newInstance(String param1, String param2) {
        ListAlertas fragment = new ListAlertas();
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
        View view = inflater.inflate(R.layout.fragment_list_alertas, container, false);
        activity = getActivity();
        context = getContext();
        getActivity().setTitle("Lista De ListAlertas");


        progressBar = view.findViewById(R.id.progressbar);
        rcv_alertas = view.findViewById(R.id.rcv_alertas);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);
        cdv_mensaje.setVisibility(View.INVISIBLE);

        alers= new Alertas();
        alers.setDistrito_id(distrito);
        alers.setUsuario_id(usuarioid);


        dc = new DataConnection(getActivity(),"listarAlertas",alers,false);
        new GetAlertas().execute();

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
        rcv_alertas.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_alertas.setLayoutManager(layoutManager);

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

        rcv_alertas.setAdapter(alertadAdapter);
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
                Log.i("RESPUESTA: ",""+response);
                String estado = "alertas";
                if (response.equals("1")){
                    Intent i =  new Intent(activity, MapaAlertas.class);
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
                    Toast.makeText(context, "la alerta no se registro correctamente ", Toast.LENGTH_SHORT).show();
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


                parametros.put("id_usuario", usuarioid);
                parametros.put("id_alerta", id_alerta);
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void onStop() {

        super.onStop();
    }
}

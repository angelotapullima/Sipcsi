package com.bufeotec.sipcsi.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bufeotec.sipcsi.Adapter.AdapterListParteDelito;
import com.bufeotec.sipcsi.Models.Delito;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;


public class ListParteDelitoFragment extends Fragment implements View.OnClickListener {


    Activity activity;
    Context context;
    DataConnection dc;
    CardView cdv_mensaje;
    ProgressBar progressBar;
    ArrayList<Delito> listDelito;
    AdapterListParteDelito adapterListParteDelito;
    RecyclerView rcv_listdelito;
    Button btnAddDeli;
    Fragment fragment = null;
    Preferences pref;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListParteDelitoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListParteDelitoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListParteDelitoFragment newInstance(String param1, String param2) {
        ListParteDelitoFragment fragment = new ListParteDelitoFragment();
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
        View view =  inflater.inflate(R.layout.fragment_list_parte_delito, container, false);
        activity = getActivity();
        context = getContext();
        pref=new Preferences(context);
        getActivity().setTitle("Lista Partes Delito");
        rcv_listdelito=view.findViewById(R.id.rcv_listdelito);
        progressBar = view.findViewById(R.id.progressbar);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);
        btnAddDeli = view.findViewById(R.id.btnAddDeli);
        cdv_mensaje.setVisibility(View.INVISIBLE);

        btnAddDeli.setOnClickListener(this);


        dc = new DataConnection(getActivity(),"listarDelitos",pref.getIdUsuarioPref(),false);
        new ListParteDelitoFragment.GetListDelitos().execute();
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

    public void onClick(View v) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        if (v.equals(btnAddDeli)){
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ParteFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();
            Bundle data = new Bundle();
            data.putString("dato","delito");
            fragment.setArguments(data);
            //fab.setVisibility(View.GONE);
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



    public class GetListDelitos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listDelito= dc.getListaDelito();
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
        rcv_listdelito.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_listdelito.setLayoutManager(layoutManager);

        adapterListParteDelito = new AdapterListParteDelito(activity, listDelito, R.layout.rcv_list_delito, new AdapterListParteDelito.OnItemClickListener() {
            @Override
            public void onItemClick(Delito delito, int position) {


            }
        });

        rcv_listdelito.setAdapter(adapterListParteDelito);
        if( listDelito.size()>0){
            cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            cdv_mensaje.setVisibility(View.VISIBLE);
        }
    }
}

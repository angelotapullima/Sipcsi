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

import com.bufeotec.sipcsi.Adapter.AdapterListParteApoyo;
import com.bufeotec.sipcsi.Models.Apoyo;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;


public class ListParteApoyoFragment extends Fragment implements View.OnClickListener {


    Activity activity;
    Context context;
    Apoyo apoyo;
    DataConnection dc;
    CardView cdv_mensaje;
    ProgressBar progressBar;
    ArrayList<Apoyo> listApoyo;
    AdapterListParteApoyo adapterListParteApoyo;
    RecyclerView rcv_listapoyo;
    Button btnAddApoy;
    Fragment fragment = null;
    Preferences pref;





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListParteApoyoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListParteApoyoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListParteApoyoFragment newInstance(String param1, String param2) {
        ListParteApoyoFragment fragment = new ListParteApoyoFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_parte_apoyo, container, false);

        activity = getActivity();
        context = getContext();
        pref= new Preferences(context);
        getActivity().setTitle("Lista Partes Apoyo");
        rcv_listapoyo=view.findViewById(R.id.rcv_listapoyo);
        progressBar = view.findViewById(R.id.progressbar);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);
        btnAddApoy = view.findViewById(R.id.btnAddApoy);
        cdv_mensaje.setVisibility(View.INVISIBLE);


        btnAddApoy.setOnClickListener(this);


        dc = new DataConnection(getActivity(),"listarApoyo",pref.getIdUsuarioPref(),false);
        new ListParteApoyoFragment.GetListApoyo().execute();
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
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        if (v.equals(btnAddApoy)){
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ParteFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();
            Bundle data = new Bundle();
            data.putString("dato","apoyo");
            fragment.setArguments(data);
            //fab.setVisibility(View.GONE);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class GetListApoyo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listApoyo= dc.getListaApoyo();
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
        rcv_listapoyo.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_listapoyo.setLayoutManager(layoutManager);

        adapterListParteApoyo = new AdapterListParteApoyo(activity, listApoyo, R.layout.rcv_list_apoyo, new AdapterListParteApoyo.OnItemClickListener() {
            @Override
            public void onItemClick(Apoyo delito, int position) {


            }
        });

        rcv_listapoyo.setAdapter(adapterListParteApoyo);
        if( listApoyo.size()>0){
            cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            cdv_mensaje.setVisibility(View.VISIBLE);
        }
    }
}

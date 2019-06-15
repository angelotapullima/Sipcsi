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

import com.bufeotec.sipcsi.Adapter.AdapterListParteAccidente;
import com.bufeotec.sipcsi.Models.Accidentes;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;

public class ListParteAccidenteFragment extends Fragment implements View.OnClickListener {

    Activity activity;
    Context context;
    DataConnection dc;
    CardView cdv_mensaje;
    ProgressBar progressBar;
    ArrayList<Accidentes> listAccidentes;
    AdapterListParteAccidente adapterListParteAccidente;
    RecyclerView rcv_listaccidente;
    Button btnAddAcci;
    Fragment fragment = null;
    Preferences pref;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListParteAccidenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListParteAccidenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListParteAccidenteFragment newInstance(String param1, String param2) {
        ListParteAccidenteFragment fragment = new ListParteAccidenteFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_parte_accidente, container, false);

        activity = getActivity();
        context = getContext();
        pref=new Preferences(context);
        getActivity().setTitle("Lista Partes Accidentes");
        rcv_listaccidente=view.findViewById(R.id.rcv_listaccidente);
        progressBar = view.findViewById(R.id.progressbar);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);
        btnAddAcci=view.findViewById(R.id.btnAddAcci);
        cdv_mensaje.setVisibility(View.INVISIBLE);


        btnAddAcci.setOnClickListener(this);

        dc = new DataConnection(getActivity(),"listarAccidentes",pref.getIdUsuarioPref(),false);
        new ListParteAccidenteFragment.GetListAccidentes().execute();

        return  view;
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
        if (v.equals(btnAddAcci)){
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new ParteFragment();
            fragmentTransaction.replace(R.id.contenedor, fragment).addToBackStack("frag").commit();
            Bundle data = new Bundle();
            data.putString("dato","accidente");
            fragment.setArguments(data);
            //fab.setVisibility(View.GONE);
        }
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
    public class GetListAccidentes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listAccidentes= dc.getListaAccidentes();
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
        rcv_listaccidente.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_listaccidente.setLayoutManager(layoutManager);

        adapterListParteAccidente = new AdapterListParteAccidente(activity, listAccidentes, R.layout.rcv_list_accidentes, new AdapterListParteAccidente.OnItemClickListener() {
            @Override
            public void onItemClick(Accidentes alertas, int position) {


                String estado = "alertas";


            }
        });

        rcv_listaccidente.setAdapter(adapterListParteAccidente);
        if( listAccidentes.size()>0){
            cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            cdv_mensaje.setVisibility(View.VISIBLE);
        }
    }
}

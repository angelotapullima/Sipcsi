package com.bufeotec.sipcsi.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bufeotec.sipcsi.Activitys.Login;
import com.bufeotec.sipcsi.Models.Usuario;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;

import static com.bufeotec.sipcsi.Principal.MainActivity.removerLogin;


public class InformacionFragment extends Fragment implements View.OnClickListener {

    TextView Pemail,Pusuario,Prol,Pdni,Pdistrito,Pclave,Pcerrar,Peditar;
    Activity activity;
    Context context;
    String distrito;
    DataConnection dc;
    ArrayList<Usuario> arrayPerfil;
    Preferences pref;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InformacionFragment() {
        // Required empty public constructor
    }


    public static InformacionFragment newInstance(String param1, String param2) {
        InformacionFragment fragment = new InformacionFragment();
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
        View view = inflater.inflate(R.layout.fragment_informacion, container, false);
        activity=getActivity();
        context= getContext();
        pref= new Preferences(context);
        Pdni = view.findViewById(R.id.Pdni);
        Prol = view.findViewById(R.id.Prol);
        Pdistrito = view.findViewById(R.id.Pdistrito);
        Pusuario = view.findViewById(R.id.Pusuario);
        Pclave = view.findViewById(R.id.Pclave);
        Pcerrar = view.findViewById(R.id.Pcerrar);
        Peditar = view.findViewById(R.id.Peditar);


        dc = new DataConnection(getActivity(),"listarPerfil",pref.getIdUsuarioPref(),false);
        new InformacionFragment.Getperfilinfo().execute();


        Peditar.setOnClickListener(this);
        Pcerrar.setOnClickListener(this);

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

        if (v.equals(Pcerrar)){
            mostrarDialogo();
        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class Getperfilinfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayPerfil = dc.getListaperfil();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            Pdni.setText(arrayPerfil.get(0).getUsuario_dni());
            Prol.setText(arrayPerfil.get(0).getRol_id());
            Pusuario.setText(arrayPerfil.get(0).getUsuario_nickname());
            Pclave.setText(arrayPerfil.get(0).getUsuario_contrasenha());



            distrito=arrayPerfil.get(0).getDistrito_id();

            if (distrito.equals("1")){
                Pdistrito.setText("Iquitos");
            }if (distrito.equals("2")){

                Pdistrito.setText("Punchana");
            }if (distrito.equals("3")){

                Pdistrito.setText("Belén");
            }if (distrito.equals("4")){

                Pdistrito.setText("San Juan Bautista");
            }

        }
    }

    private void logOut(){
        removerLogin();
        String login = "0";
        Intent i = new Intent(getActivity(), Login.class);
        i.putExtra("login",login);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }



    public void mostrarDialogo(){
        new AlertDialog.Builder(getContext()).setTitle("Cerrar Session")
                .setMessage("Estas seguro de Cerrar Sesion?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();
                    }
                })
                .setNegativeButton("CANCEL",null)
                .show();
    }
}

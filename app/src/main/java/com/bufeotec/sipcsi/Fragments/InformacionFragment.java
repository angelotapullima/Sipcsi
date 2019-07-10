package com.bufeotec.sipcsi.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
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

import com.bufeotec.sipcsi.Activitys.EditarPerfil;
import com.bufeotec.sipcsi.Activitys.Login;
import com.bufeotec.sipcsi.Feed.Models.ModelFeed;
import com.bufeotec.sipcsi.Feed.Repository.FeedDao;
import com.bufeotec.sipcsi.MiFeed.Repository.MyFeedInfoDao;
import com.bufeotec.sipcsi.MiFeed.Repository.MyFeedRoomDBRepository;
import com.bufeotec.sipcsi.MiFeed.Repository.MyFeedRoomDataBase;
import com.bufeotec.sipcsi.MiFeed.ViewModels.MyFeedListViewModel;
import com.bufeotec.sipcsi.Models.Usuario;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;
import java.util.List;

import static com.bufeotec.sipcsi.Principal.MainActivity.removerLogin;


public class InformacionFragment extends Fragment implements View.OnClickListener {

    TextView Pusuario,Prol,Pdni,Pdistrito,Pcerrar,Peditar;
    Activity activity;
    Context context;
    String distrito;
    DataConnection dc;
    ArrayList<Usuario> arrayPerfil;
    Preferences pref;
    MyFeedListViewModel myFeedListViewModel;



    private OnFragmentInteractionListener mListener;

    public InformacionFragment() {

        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myFeedListViewModel = ViewModelProviders.of(getActivity()).get(MyFeedListViewModel.class);
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
        Pcerrar = view.findViewById(R.id.Pcerrar);
        Peditar = view.findViewById(R.id.Peditar);


        dc = new DataConnection(getActivity(),"listarPerfil",pref.getIdUsuarioPref(),false);
        new InformacionFragment.Getperfilinfo().execute();


        Peditar.setOnClickListener(this);
        Pcerrar.setOnClickListener(this);

        return  view;
    }




    @Override
    public void onClick(View v) {

        if (v.equals(Pcerrar)){
            mostrarDialogo();
        }if(v.equals(Peditar)){
            Intent i =  new Intent(context, EditarPerfil.class);
            i.putExtra("nombre",arrayPerfil.get(0).getUsuario_nombre());
            i.putExtra("apellido",arrayPerfil.get(0).getUsuario_apellido());
            i.putExtra("usuario",arrayPerfil.get(0).getUsuario_nickname());
            i.putExtra("distrito",arrayPerfil.get(0).getDistrito_nombre());
            i.putExtra("dni",arrayPerfil.get(0).getUsuario_dni());
            i.putExtra("id",arrayPerfil.get(0).getUsuario_id());

            startActivity(i);
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

        myFeedListViewModel.deleteAllPost();

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

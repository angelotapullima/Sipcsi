package com.bufeotec.sipcsi.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bufeotec.sipcsi.Adapter.AdaptadorListadoQuejas;
import com.bufeotec.sipcsi.Models.Areas;
import com.bufeotec.sipcsi.Models.Queja;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.WebServices.DataConnection;

import java.util.ArrayList;

import static com.bufeotec.sipcsi.Principal.MainActivity.usuarioid;


public class FeedFragment extends Fragment implements View.OnClickListener , SwipeRefreshLayout.OnRefreshListener {


    Context context;
    Activity activity;
    DataConnection dc;
    AdaptadorListadoQuejas adaptadorListadoQuejas;
    public ArrayList<Queja> arrayqueja;
    RecyclerView rcv_quejas;
    ProgressBar progressBar;
    CardView cdv_mensaje;
    SwipeRefreshLayout swipeRefreshLayout;


    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feed, container, false);
        context=getContext();
        activity=getActivity();

        rcv_quejas = view.findViewById(R.id.rcv_quejas);
        progressBar = view.findViewById(R.id.progressbar);
        cdv_mensaje = view.findViewById(R.id.cdv_mensaje);


        dc = new DataConnection(activity,"listarQuejas",new Queja(usuarioid),false);
        new FeedFragment.GetQueja().execute();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);


        return  view ;
    }

    @Override
    public void onRefresh() {

        dc = new DataConnection(activity,"listarQuejas",new Queja(usuarioid),false);
        new FeedFragment.GetQueja().execute();
    }


    public class GetQueja extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayqueja = dc.getListaQuejas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_quejas.setLayoutManager(linearLayoutManager);

            adaptadorListadoQuejas = new AdaptadorListadoQuejas(context, arrayqueja, R.layout.rcv_item_list_quejas, new AdaptadorListadoQuejas.OnItemClickListener() {
                @Override
                public void onItemClick(Queja queja, final int position) {

                    //Toast.makeText(getActivity(),"ID "+empresas.getEmpresas_id(), Toast.LENGTH_SHORT).show();

                    /*Intent intent = new Intent(getContext(), DetalleNegocio.class);
                    intent.putExtra("id_empresa",empresas.getEmpresas_id());
                    startActivity(intent);*/

                }
            });
            rcv_quejas.setAdapter(adaptadorListadoQuejas);
            swipeRefreshLayout.setRefreshing(false);

            if (arrayqueja.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

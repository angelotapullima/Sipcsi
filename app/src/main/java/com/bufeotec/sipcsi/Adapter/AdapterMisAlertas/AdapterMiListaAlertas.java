package com.bufeotec.sipcsi.Adapter.AdapterMisAlertas;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

public class AdapterMiListaAlertas extends RecyclerView.Adapter<AdapterMiListaAlertas.MisAlertasViewHolder> {

    private ArrayList<Alertas> array;
    private int layoutpadre;
    Context context;
    Alertas obj;
    private OnItemClickListener listener;


    public AdapterMiListaAlertas(Context context, ArrayList<Alertas> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterMiListaAlertas.MisAlertasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutpadre, parent, false);
        return new AdapterMiListaAlertas.MisAlertasViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterMiListaAlertas.MisAlertasViewHolder holder, int position) {


        //setAnimation(holder.itemView, position);
        obj = array.get(position);
        String delito = obj.getAlerta_tipo();
        if (delito.equals("DELITO")){
            holder.maltipo_delito.setText(obj.getAlerta_tipo_delito());
            holder.maltipo_delito.setBackgroundColor(Color.rgb(63,81,181));
            holder.view.setBackgroundColor(Color.rgb(63,81,181));

        }if (delito.equals("ACCIDENTE")){
            holder.maltipo_delito.setText(obj.getAlerta_tipo());
            holder.maltipo_delito.setBackgroundColor(Color.rgb(232,52,51));
            holder.view.setBackgroundColor(Color.rgb(232,52,51));

        }if (delito.equals("ZONAVIAL")){
            holder.maltipo_delito.setText(obj.getAlerta_tipo());
            holder.maltipo_delito.setBackgroundColor(Color.rgb(229,142,53));
            holder.view.setBackgroundColor(Color.rgb(229,142,53));
        }

        holder.maldireccionDelito.setText(obj.getCalle_nombre());
        holder.maldesDelito.setText(obj.getAlerta_descripcion());
        holder.maltxtfec.setText(obj.getAlerta_fecha());

        holder.bid(obj, listener);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MisAlertasViewHolder extends RecyclerView.ViewHolder {

        TextView maltipo_delito, maldesDelito, maldireccionDelito, maltxtfec;
        View view ;
        CardView misAlertas;


        public MisAlertasViewHolder(View itemView) {
            super(itemView);
            maltipo_delito = itemView.findViewById(R.id.maltipo_delito);
            maldesDelito = itemView.findViewById(R.id.maldesDelito);
            maltxtfec = itemView.findViewById(R.id.maltxtfec);
            maldireccionDelito = itemView.findViewById(R.id.maldireccionDelito);
            misAlertas = itemView.findViewById(R.id.misAlertas);
            view = itemView.findViewById(R.id.view);



        }


        public void bid(final Alertas alertas, final AdapterMiListaAlertas.OnItemClickListener listener) {
            misAlertas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(alertas,getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(Alertas alertas, int position);
    }
}

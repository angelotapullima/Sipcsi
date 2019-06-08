package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

public class AlertasAdapter  extends  RecyclerView.Adapter<AlertasAdapter.AlertasViewHolder>{
    private ArrayList<Alertas> array;
    private int layoutpadre;
    Context context;
    Alertas obj;
    private OnItemClickListener listener;


    public AlertasAdapter(Context context, ArrayList<Alertas> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlertasAdapter.AlertasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new AlertasAdapter.AlertasViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull AlertasAdapter.AlertasViewHolder holder, int position) {


        //setAnimation(holder.itemView, position);
        obj = array.get(position);
        String delito = obj.getAlerta_tipo();

        if (delito.equals("DELITO")){
            holder.tipo_delito.setText(obj.getAlerta_tipo_delito());
            holder.tipo_delito.setBackgroundColor(Color.rgb(63,81,181));
            holder.view2.setBackgroundColor(Color.rgb(63,81,181));


        }if (delito.equals("ACCIDENTE")){
            holder.tipo_delito.setText(obj.getAlerta_tipo());
            holder.tipo_delito.setBackgroundColor(Color.rgb(232,52,51));
            holder.view2.setBackgroundColor(Color.rgb(232,52,51));


        }if (delito.equals("ZONAVIAL")){
            holder.tipo_delito.setText(obj.getAlerta_tipo());
            holder.tipo_delito.setBackgroundColor(Color.rgb(229,142,53));
            holder.view2.setBackgroundColor(Color.rgb(229,142,53));

        }

        //Picasso.with(context).load("https://"+IP+"/"+ obj.getUsuario_foto()).into(holder.fotoChofer);

        holder.desDelito.setText(obj.getAlerta_descripcion());
        holder.direccionDelito.setText(obj.getCalle_nombre());
        holder.cant.setText(obj.getCantidad());
        holder.txtfec.setText(obj.getAlerta_fecha());
        holder.bid(obj,listener);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class AlertasViewHolder extends RecyclerView.ViewHolder {


        TextView tipo_delito,desDelito,direccionDelito,cant,txtfec;
        Button btnActuar;
        View view2;

        public AlertasViewHolder (View itemView) {
            super(itemView);

            tipo_delito=itemView.findViewById(R.id.tipo_delito);
            desDelito=itemView.findViewById(R.id.desDelito);
            txtfec=itemView.findViewById(R.id.txtfec);
            direccionDelito=itemView.findViewById(R.id.direccionDelito);
            btnActuar=itemView.findViewById(R.id.btnActuar);
            cant=itemView.findViewById(R.id.cant);
            view2=itemView.findViewById(R.id.view2);

        }



        public void bid(final Alertas alertas,final AlertasAdapter.OnItemClickListener listener){

            btnActuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(alertas,getAdapterPosition());
                }
            });
        }
    }
    public interface  OnItemClickListener{
        void onItemClick(Alertas alertas, int position);
    }
}
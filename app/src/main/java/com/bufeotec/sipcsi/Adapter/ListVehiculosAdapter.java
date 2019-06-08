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

import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

public class ListVehiculosAdapter  extends RecyclerView.Adapter<ListVehiculosAdapter.VehiculosViewHolder> {
    private ArrayList<Vehiculos> array;
    private int layoutpadre;
    Context context;
    Vehiculos obj;
    private  OnItemClickListener listener;


    public ListVehiculosAdapter(Context context, ArrayList<Vehiculos> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListVehiculosAdapter.VehiculosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new VehiculosViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListVehiculosAdapter.VehiculosViewHolder holder, int position) {


        //setAnimation(holder.itemView, position);
        obj = array.get(position);
        if (obj.getTipo().equals("Moto")){

            holder.txt_tipo.setText(obj.getTipo());
            holder.txt_tipo.setBackgroundColor(Color.rgb(63,81,181));
            holder.view4.setBackgroundColor(Color.rgb(63,81,181));

        }if (obj.getTipo().equals("Carro")){

            holder.txt_tipo.setText(obj.getTipo());
            holder.txt_tipo.setBackgroundColor(Color.rgb(232,52,51));
            holder.view4.setBackgroundColor(Color.rgb(232,52,51));
        }


        //Picasso.with(context).load("https://"+IP+"/"+ obj.getUsuario_foto()).into(holder.fotoChofer);
        holder.txt_usuario.setText(obj.getUsuario());
        holder.txt_placa.setText(obj.getPlaca());
        holder.bid(obj,listener);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }
    public class VehiculosViewHolder extends RecyclerView.ViewHolder {


        TextView txt_tipo,txt_placa,txt_usuario;
        Button btnSolicitar;
        View view4;

        public VehiculosViewHolder(View itemView) {
            super(itemView);

            txt_tipo=itemView.findViewById(R.id.txt_tipo);
            txt_placa=itemView.findViewById(R.id.txt_placa);
            txt_usuario=itemView.findViewById(R.id.txt_usuario);
            btnSolicitar=itemView.findViewById(R.id.btnSolicitar);
            view4=itemView.findViewById(R.id.view4);
        }
        public void bid(final Vehiculos vehiculos,final OnItemClickListener listener){

            btnSolicitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(vehiculos,getAdapterPosition());
                }
            });
        }
    }
    public interface  OnItemClickListener{
        void onItemClick(Vehiculos vehiculos, int position);
    }
}

package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
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




public class ListBasurerosAdapter  extends RecyclerView.Adapter<ListBasurerosAdapter.BasurerosViewHolder> {

    private ArrayList<Vehiculos> array;
    private int layoutpadre;
    Context context;
    Vehiculos obj;
    private OnItemClickListener listener;

    public ListBasurerosAdapter (Context context, ArrayList<Vehiculos> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListBasurerosAdapter.BasurerosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(layoutpadre,viewGroup,false);
        return new BasurerosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBasurerosAdapter.BasurerosViewHolder holder, int position) {

        obj = array.get(position);

        holder.basureros_ruta.setText(obj.getUsuario());
        holder.basureros_placa.setText(obj.getPlaca());
        holder.bid(obj,listener);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class BasurerosViewHolder extends  RecyclerView.ViewHolder{


        TextView basureros_ruta,basureros_placa;
        Button btnVerRuta;
        public BasurerosViewHolder (View itemview){
            super(itemview);

            basureros_ruta=itemView.findViewById(R.id.basureros_ruta);
            basureros_placa=itemView.findViewById(R.id.basureros_placa);
            btnVerRuta=itemView.findViewById(R.id.btnVerRuta);
        }

        public void bid(final Vehiculos vehiculos,final OnItemClickListener listener){

            btnVerRuta.setOnClickListener(new View.OnClickListener() {
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

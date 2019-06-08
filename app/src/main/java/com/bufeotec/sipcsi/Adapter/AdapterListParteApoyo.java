package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.Apoyo;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

public class AdapterListParteApoyo extends RecyclerView.Adapter<AdapterListParteApoyo.ApoyoViewHolder> {

    private ArrayList<Apoyo> array;
    private int layoutpadre;
    Context context;
    Apoyo obj;
    private OnItemClickListener listener;

    public AdapterListParteApoyo(Context context, ArrayList<Apoyo> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }



    @NonNull
    @Override
    public AdapterListParteApoyo.ApoyoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutpadre, parent, false);
        return new AdapterListParteApoyo.ApoyoViewHolder (view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterListParteApoyo.ApoyoViewHolder holder, int position) {


        //setAnimation(holder.itemView, position);
        obj = array.get(position);
        holder.ApoyoFecha.setText(obj.getFecha());
        holder.ApoyoAsunto.setText(obj.getAsunto());
        holder.ApoyoParte.setText(obj.getNumero_parte());
        holder.ApoyoDes.setText(obj.getDescripcion());
        holder.bid(obj, listener);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }




    public class ApoyoViewHolder extends RecyclerView.ViewHolder {


        TextView ApoyoFecha, ApoyoAsunto, ApoyoParte, ApoyoDes;
        LinearLayout itemApoyo;


        public ApoyoViewHolder(View itemView) {
            super(itemView);

            ApoyoFecha=itemView.findViewById(R.id.ApoyoFecha);
            ApoyoAsunto=itemView.findViewById(R.id.ApoyoAsunto);
            ApoyoParte=itemView.findViewById(R.id.ApoyoParte);
            ApoyoDes=itemView.findViewById(R.id.ApoyoDes);
            itemApoyo=itemView.findViewById(R.id.itemApoyo);



        }


        public void bid(final Apoyo apoyo, final AdapterListParteApoyo.OnItemClickListener listener) {

            itemApoyo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(apoyo, getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Apoyo apoyo, int position);
    }
}

package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.Info;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

public class AdaptadorPrimerosAuxilios extends RecyclerView.Adapter<AdaptadorPrimerosAuxilios.PrimerosAuxiliosViewHolder> {

    private ArrayList<Info> array;
    private int layoutpadre;
    Context context;
    Info obj;
    private  OnItemClickListener listener;

    public AdaptadorPrimerosAuxilios() {
    }

    public AdaptadorPrimerosAuxilios(Context context, ArrayList<Info> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    public class PrimerosAuxiliosViewHolder extends RecyclerView.ViewHolder{



        TextView PAtitle,PAcontenido;
        CardView cardPrimeros;


        public PrimerosAuxiliosViewHolder(View itemView) {
            super(itemView);

            PAtitle=itemView.findViewById(R.id.PAtitle);
            //PAcontenido=itemView.findViewById(R.id.PAcontenido);
            cardPrimeros=itemView.findViewById(R.id.cardPrimeros);



        }


        public void bid(final Info info ,final OnItemClickListener listener){
            cardPrimeros.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(info, getAdapterPosition());
                }
            });


        }
    }

    @Override
    public PrimerosAuxiliosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new PrimerosAuxiliosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PrimerosAuxiliosViewHolder holder, int position) {

        obj = array.get(position);


        holder.PAtitle.setText(array.get(position).getTitulo());
        //holder.PAcontenido.setText(array.get(position).getContenido());
         holder.bid(obj,listener);
    }



    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(Info info, int position);
    }

}
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

public class AdaptadorTips extends RecyclerView.Adapter<AdaptadorTips.TipsViewHolder> {

    private ArrayList<Info> array;
    private int layoutpadre;
    Context context;
    Info obj;
    private  OnItemClickListener listener;

    public AdaptadorTips() {
    }

    public AdaptadorTips(Context context, ArrayList<Info> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    public class TipsViewHolder extends RecyclerView.ViewHolder{



        TextView Ttitle,Tcontenido;
        CardView cardtips;

        public TipsViewHolder(View itemView) {
            super(itemView);

            Ttitle=itemView.findViewById(R.id.Ttitle);
            Tcontenido=itemView.findViewById(R.id.Tcontenido);
            cardtips=itemView.findViewById(R.id.cardtips);


        }


        public void bid(final Info info ,final OnItemClickListener listener){
            cardtips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(info, getAdapterPosition());
                }
            });

        }
    }

    @Override
    public TipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new TipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TipsViewHolder holder, int position) {

        obj = array.get(position);

        holder.Ttitle.setText(array.get(position).getTitulo());
        holder.Tcontenido.setText(array.get(position).getContenido());

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
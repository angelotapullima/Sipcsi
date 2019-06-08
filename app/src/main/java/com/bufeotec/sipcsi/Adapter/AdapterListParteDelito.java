package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.Delito;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

public class AdapterListParteDelito extends RecyclerView.Adapter<AdapterListParteDelito.DelitosViewHolder> {

    private ArrayList<Delito> array;
    private int layoutpadre;
    Context context;
    Delito obj;
    private OnItemClickListener listener;

    public AdapterListParteDelito(Context context, ArrayList<Delito> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }



    @NonNull
    @Override
    public AdapterListParteDelito.DelitosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutpadre, parent, false);
        return new AdapterListParteDelito.DelitosViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterListParteDelito.DelitosViewHolder holder, int position) {


        //setAnimation(holder.itemView, position);
        obj = array.get(position);
        holder.DelitoFecha.setText(obj.getFecha());
        holder.DelitoAsunto.setText(obj.getAsunto());
        holder.DelitoParte.setText(obj.getNumero_parte());
        holder.DelitoDes.setText(obj.getDescripcion());
        holder.bid(obj, listener);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }




    public class DelitosViewHolder extends RecyclerView.ViewHolder {


        TextView DelitoFecha, DelitoAsunto, DelitoParte, DelitoDes;
        LinearLayout itemDelito;


        public DelitosViewHolder(View itemView) {
            super(itemView);

            DelitoFecha=itemView.findViewById(R.id.DelitoFecha);
            DelitoAsunto=itemView.findViewById(R.id.DelitoAsunto);
            DelitoParte=itemView.findViewById(R.id.DelitoParte);
            DelitoDes=itemView.findViewById(R.id.DelitoDes);
            itemDelito=itemView.findViewById(R.id.itemDelito);



        }


        public void bid(final Delito delito, final AdapterListParteDelito.OnItemClickListener listener) {

            itemDelito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(delito, getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Delito delito, int position);
    }
}

package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.Accidentes;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

public class AdapterListParteAccidente extends RecyclerView.Adapter<AdapterListParteAccidente.AccidentesViewHolder> {

    private ArrayList<Accidentes> array;
    private int layoutpadre;
    Context context;
    Accidentes obj;
    private OnItemClickListener listener;

    public AdapterListParteAccidente(Context context, ArrayList<Accidentes> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }



    @NonNull
    @Override
    public AdapterListParteAccidente.AccidentesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutpadre, parent, false);
        return new AdapterListParteAccidente.AccidentesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterListParteAccidente.AccidentesViewHolder holder, int position) {


        //setAnimation(holder.itemView, position);
        obj = array.get(position);
        holder.AccidenteFecha.setText(obj.getFecha());
        holder.AccidenteAsunto.setText(obj.getAsunto());
        holder.AccidenteParte.setText(obj.getNumero_parte());
        holder.AccidenteDes.setText(obj.getDescripcion());
        holder.bid(obj, listener);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }




    public class AccidentesViewHolder extends RecyclerView.ViewHolder {


        TextView AccidenteFecha, AccidenteAsunto, AccidenteParte, AccidenteDes;
        LinearLayout itemAccidente;


        public AccidentesViewHolder(View itemView) {
            super(itemView);

            AccidenteFecha=itemView.findViewById(R.id.AccidenteFecha);
            AccidenteAsunto=itemView.findViewById(R.id.AccidenteAsunto);
            AccidenteParte=itemView.findViewById(R.id.AccidenteParte);
            AccidenteDes=itemView.findViewById(R.id.AccidenteDes);
            itemAccidente=itemView.findViewById(R.id.itemAccidente);



        }


        public void bid(final Accidentes accidentes, final AdapterListParteAccidente.OnItemClickListener listener) {

            itemAccidente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(accidentes, getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Accidentes accidentes, int position);
    }
}

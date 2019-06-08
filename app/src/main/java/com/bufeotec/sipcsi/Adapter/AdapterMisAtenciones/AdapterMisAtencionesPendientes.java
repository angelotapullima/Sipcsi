package com.bufeotec.sipcsi.Adapter.AdapterMisAtenciones;

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

public class AdapterMisAtencionesPendientes extends RecyclerView.Adapter<AdapterMisAtencionesPendientes.AtencionesViewHolder>{

    private ArrayList<Alertas> array;
    private int layoutpadre;
    Context context;
    Alertas obj;
    private OnItemClickListener listener;


    public AdapterMisAtencionesPendientes(Context context, ArrayList<Alertas> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterMisAtencionesPendientes.AtencionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new AdapterMisAtencionesPendientes.AtencionesViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull AdapterMisAtencionesPendientes.AtencionesViewHolder holder, int position) {


        //setAnimation(holder.itemView, position);
        obj = array.get(position);
        String delito = obj.getAlerta_tipo();
        String delito_tipo= obj.getAlerta_tipo_delito();
        if (delito.equals("DELITO")){
            holder.mistipo_delito.setText(obj.getAlerta_tipo_delito());
            holder.mistipo_delito.setBackgroundColor(Color.rgb(63,81,181));
            holder.view3.setBackgroundColor(Color.rgb(63,81,181));

        }if (delito.equals("ACCIDENTE")){
            holder.mistipo_delito.setText(obj.getAlerta_tipo());
            holder.mistipo_delito.setBackgroundColor(Color.rgb(232,52,51));
            holder.view3.setBackgroundColor(Color.rgb(232,52,51));

        }if (delito.equals("ZONAVIAL")){
            holder.mistipo_delito.setText(obj.getAlerta_tipo());
            holder.mistipo_delito.setBackgroundColor(Color.rgb(229,142,53));
            holder.view3.setBackgroundColor(Color.rgb(229,142,53));

        }



        holder.misdesDelito.setText(obj.getAlerta_descripcion());
        holder.misdireccionDelito.setText(obj.getCalle_nombre());
        holder.mistxtfec.setText(obj.getAlerta_fecha());
        if (obj.getAlerta_estado().equals("2")){
            holder.misbtnActuar.setText("ATENDIDO");
            holder.misbtnActuar.setBackgroundResource(R.drawable.verde);
        }else {
            holder.misbtnActuar.setText("PENDIENTE");
            holder.misbtnActuar.setBackgroundResource(R.drawable.rojo);
        }

        holder.bid(obj,listener);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class AtencionesViewHolder extends RecyclerView.ViewHolder {

        TextView mistipo_delito,misdesDelito,misdireccionDelito,mistxtfec;
        Button misbtnActuar;
        View view3;


        public AtencionesViewHolder (View itemView) {
            super(itemView);
            mistipo_delito=itemView.findViewById(R.id.Mistipo_delito);
            misdesDelito=itemView.findViewById(R.id.MisdesDelito);
            mistxtfec=itemView.findViewById(R.id.Mistxtfec);
            misdireccionDelito=itemView.findViewById(R.id.MisdireccionDelito);
            misbtnActuar=itemView.findViewById(R.id.MisbtnActuar);
            view3=itemView.findViewById(R.id.view3);

        }



        public void bid(final Alertas alertas,final AdapterMisAtencionesPendientes.OnItemClickListener listener){

            misbtnActuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onnItemClick(alertas,getAdapterPosition());
                }
            });
        }
    }
    public interface  OnItemClickListener{
        void onnItemClick(Alertas alertas, int position);
    }
}

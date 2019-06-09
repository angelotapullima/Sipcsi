package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Models.Queja;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.UniversalImageLoader;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;
import static com.bufeotec.sipcsi.Principal.MainActivity.usuarioid;

public class AdaptadorListadoQuejas extends RecyclerView.Adapter<AdaptadorListadoQuejas.QuejaViewHolder> {

    private ArrayList<Queja> array;
    private int layoutpadre;
    Context context;
    Queja obj;
    UniversalImageLoader universalImageLoader;
    SharedPreferences preferencesUser;
    StringRequest stringRequest;
    private  OnItemClickListener listener;
    String foto ;

    public AdaptadorListadoQuejas() {
    }

    public AdaptadorListadoQuejas(Context context, ArrayList<Queja> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = context.getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    public class QuejaViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_fotoQueja;
        TextView like;
        private ProgressBar prog_fotoPublicacion;
        private TextView txt_nombreUsuario, txt_fechaQueja,  txt_descripcionQueja,txt_destinoQueja,nlike;


        public QuejaViewHolder(View itemView) {
            super(itemView);

            img_fotoQueja=  itemView.findViewById(R.id.img_fotoQuejaItem);
            txt_destinoQueja=  itemView.findViewById(R.id.txt_destinoQueja);
            prog_fotoPublicacion = itemView.findViewById(R.id.prog_fotoPublicacion);
            like=  itemView.findViewById(R.id.like);
            nlike=  itemView.findViewById(R.id.nlike);
            txt_nombreUsuario=  itemView.findViewById(R.id.txt_nombreUsuario);
            txt_fechaQueja=  itemView.findViewById(R.id.txt_fechaQueja);
            txt_descripcionQueja=  itemView.findViewById(R.id.txt_descripcionQueja);

        }


        public void bid(final Queja queja,final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(queja,getAdapterPosition());
                }
            });

        }
    }

    @Override
    public QuejaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new QuejaViewHolder(view);
    }


    int posicionlocalc;
    @Override
    public void onBindViewHolder(final QuejaViewHolder holder, int position) {

        obj = array.get(position);

        foto = obj.getQueja_foto();
        ImageLoader.getInstance().init(universalImageLoader.getConfig());


        if (obj.getEstado_like().equals("0")){
            holder.like.setBackgroundResource(R.drawable.brazo_white);

        }else{
            holder.like.setBackgroundResource(R.drawable.brazo);
            //estado = false;

        }
        holder.txt_nombreUsuario.setText(obj.getUsuario_nombre());
        holder.txt_destinoQueja.setText(obj.getQueja_destino());
        holder.txt_fechaQueja.setText(obj.getQueja_fecha());
        holder.nlike.setText(obj.getCantidad());
        holder.txt_descripcionQueja.setText(obj.getQueja_descripcion());

        //UniversalImageLoader.setImage("http://"+IP+"/"+obj.getQueja_foto(),holder.img_fotoQueja,holder.prog_fotoPublicacion);
        if (foto.equals("0")){
            holder.img_fotoQueja.setVisibility(View.GONE);
        }else{

            UniversalImageLoader.setImage("http://"+IP+"/"+array.get(position).getQueja_foto(),holder.img_fotoQueja,holder.prog_fotoPublicacion);
        }
         holder.bid(obj,listener);
    }

    private void darlike(final String idlike) {
        String url ="https://"+IP+"/index.php?c=Pueblo&a=dar_like&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("darlike: ",""+response);
                if(response.equals("1")){



                } else {
                    //Toast.makeText(ChoferDatosDeCarrera.this,"No se ha registrado ",Toast.LENGTH_SHORT).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario_id",usuarioid);
                parametros.put("pueblo_id",idlike);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
        //new GetPuntos().execute();
    }

    private void dislike(final String iddislike) {
        String url ="https://"+IP+"/index.php?c=Pueblo&a=quitar_like&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("Hoja sin plaga")){

                } else {
                    //Toast.makeText(ChoferDatosDeCarrera.this,"No se ha registrado ",Toast.LENGTH_SHORT).show();
                    Log.d("dislike: ",""+response);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario_id",usuarioid);
                parametros.put("pueblo_id",iddislike);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(Queja queja, int position);
    }

}
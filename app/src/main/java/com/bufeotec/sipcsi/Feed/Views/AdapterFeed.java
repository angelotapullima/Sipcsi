package com.bufeotec.sipcsi.Feed.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*import com.andr.mvvm.R;
import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;
import com.andr.mvvm.RetrofitRoom.UniversalImageLoader;*/
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bufeotec.sipcsi.Feed.Models.ModelFeed;
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.Util.Preferences;
import com.bufeotec.sipcsi.Util.UniversalImageLoader;
import com.bufeotec.sipcsi.WebServices.VolleySingleton;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bufeotec.sipcsi.WebServices.DataConnection.IP;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.PostViewHolder> {

    UniversalImageLoader universalImageLoader;
    String foto ;
    StringRequest stringRequest;
    ImageButton imgbt_like_g;
    TextView nlike_g;
    int totalLikes;
    JSONObject json_data;
    String resultado;
    int posicionlocalc;
    ModelFeed current;
    Context ctx;
    Preferences preferencesUser;


    class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_fotoQueja;
        ImageButton like;
        RelativeLayout relfoto;
        private ProgressBar prog_fotoPublicacion;
        private TextView txt_nombreUsuario, txt_fechaQueja,  txt_descripcionQueja,txt_destinoQueja,nlike;

        private PostViewHolder(View itemView) {
            super(itemView);
            img_fotoQueja=  itemView.findViewById(R.id.img_fotoQuejaItem);
            relfoto=  itemView.findViewById(R.id.relfoto);
            txt_destinoQueja=  itemView.findViewById(R.id.txt_destinoQueja);
            prog_fotoPublicacion = itemView.findViewById(R.id.prog_fotoPublicacion);
            like=  itemView.findViewById(R.id.like);
            nlike=  itemView.findViewById(R.id.nlike);
            txt_nombreUsuario=  itemView.findViewById(R.id.txt_nombreUsuario);
            txt_fechaQueja=  itemView.findViewById(R.id.txt_fechaQueja);
            txt_descripcionQueja=  itemView.findViewById(R.id.txt_descripcionQueja);
        }
    }

    private final LayoutInflater mInflater;
    private List<ModelFeed> mUsers; // Cached copy of users


    AdapterFeed(Context context) {
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);}

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_quejas, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);
            /*holder.id.setText(""+current.getId());
            holder.title.setText(current.getDestino());
            holder.body.setText(current.getFoto());*/


            holder.setIsRecyclable(false);
            holder.like.setId(position);
            foto = current.getFoto();
            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            holder.txt_nombreUsuario.setText(current.getUsuario());
            holder.txt_destinoQueja.setText(current.getDestino());
            holder.txt_fechaQueja.setText(current.getFecha());
            holder.nlike.setText(current.getCant_likes());
            holder.txt_descripcionQueja.setText(current.getQueja());

            //UniversalImageLoader.setImage("http://"+IP+"/"+obj.getQueja_foto(),holder.img_fotoQueja,holder.prog_fotoPublicacion);
            if (foto.equals("0")){
                holder.img_fotoQueja.setVisibility(View.GONE);
                holder.relfoto.setVisibility(View.GONE);
            }else{

                UniversalImageLoader.setImage("http://www.guabba.com/accidentestransito/"+current.getFoto(),holder.img_fotoQueja,null);
            }


            if (current.getDio_like().equals("0")){
                holder.like.setBackgroundResource(R.drawable.brazo_white);

            }else{
                //holder.like.setBackgroundColor(Color.rgb(248,167,36));
                holder.like.setBackgroundResource(R.drawable.brazo);
                //estado = false;

            }


            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posicionlocalc = v.getId();
                    imgbt_like_g = (ImageButton) v;
                    nlike_g = holder.nlike;


                    if (mUsers.get(posicionlocalc).getDio_like().equals("0")){
                        darlike(mUsers.get(posicionlocalc).getId());
                        holder.like.setBackgroundResource(R.drawable.brazo);

                    }else{
                        dislike(mUsers.get(posicionlocalc).getId());
                        holder.like.setBackgroundResource(R.drawable.brazo_white);
                    }
                }
            });
        } else {
            // Covers the case of data not being ready yet.
           // holder.userNameView.setText("No Word");
        }
    }
    void setWords(List<ModelFeed> users){
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }else{
            return  0;
        }
    }

    private void darlike(final String idlike) {
        String url ="https://"+IP+"/index.php?c=Pueblo&a=dar_like&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("darlike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        nlike_g.setText(String.valueOf(totalLikes));
                        mUsers.get(posicionlocalc).setDio_like("1");
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }





                //Toast.makeText(ChoferDatosDeCarrera.this,"No se ha registrado ",Toast.LENGTH_SHORT).show();


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
                parametros.put("usuario_id",preferencesUser.getIdUsuarioPref());
                parametros.put("pueblo_id",idlike);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(ctx).addToRequestQueue(stringRequest);
    }

    private void dislike(final String iddislike) {
        String url ="https://"+IP+"/index.php?c=Pueblo&a=quitar_like&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dislike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        nlike_g.setText(String.valueOf(totalLikes));
                        mUsers.get(posicionlocalc).setDio_like("0");
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
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
                parametros.put("usuario_id",preferencesUser.getIdUsuarioPref());
                parametros.put("pueblo_id",iddislike);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(ctx).addToRequestQueue(stringRequest);
    }

}

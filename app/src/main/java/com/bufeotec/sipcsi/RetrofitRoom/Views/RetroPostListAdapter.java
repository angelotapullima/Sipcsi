package com.bufeotec.sipcsi.RetrofitRoom.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*import com.andr.mvvm.R;
import com.andr.mvvm.RetrofitRoom.Models.ResultModel;
import com.andr.mvvm.RetrofitRoom.UniversalImageLoader;*/
import com.bufeotec.sipcsi.R;
import com.bufeotec.sipcsi.RetrofitRoom.Models.ResultModel;
import com.bufeotec.sipcsi.Util.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RetroPostListAdapter extends RecyclerView.Adapter<RetroPostListAdapter.PostViewHolder> {

    UniversalImageLoader universalImageLoader;
    String foto ;

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
    private List<ResultModel> mUsers; // Cached copy of users


    RetroPostListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);}

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_quejas, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (mUsers != null) {
            ResultModel  current = mUsers.get(position);
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


            /*holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posicionlocalc = v.getId();
                    imgbt_like_g = (ImageButton) v;
                    nlike_g = holder.nlike;


                    if (array.get(posicionlocalc).getEstado_like().equals("0")){
                        darlike(array.get(posicionlocalc).getQueja_id());
                        holder.like.setBackgroundResource(R.drawable.brazo);
                    }else{
                        dislike(array.get(posicionlocalc).getQueja_id());
                        holder.like.setBackgroundResource(R.drawable.brazo_white);
                    }
                }
            });*/
        } else {
            // Covers the case of data not being ready yet.
           // holder.userNameView.setText("No Word");
        }
    }
    void setWords(List<ResultModel> users){
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
}

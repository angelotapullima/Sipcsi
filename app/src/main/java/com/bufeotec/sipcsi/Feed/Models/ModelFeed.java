package com.bufeotec.sipcsi.Feed.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "feed")
public class ModelFeed
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "usuario")
    @SerializedName("usuario")
    private String usuario;


    @ColumnInfo(name = "id_usuario")
    @SerializedName("id_usuario")
    private String id_usuario;


    @ColumnInfo(name = "destino")
    @SerializedName("destino")
    private String destino;


    @ColumnInfo(name = "queja")
    @SerializedName("queja")
    private String queja;


    @ColumnInfo(name = "foto")
    @SerializedName("foto")
    private String foto;


    @ColumnInfo(name = "fecha")
    @SerializedName("fecha")
    private String fecha;


    @ColumnInfo(name = "cant_likes")
    @SerializedName("cant_likes")
    private String cant_likes;


    @ColumnInfo(name = "dio_like")
    @SerializedName("dio_like")
    private String dio_like;


    public String getId() {
        return id;
    }

    public void setId( @NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario( String usuario) {
        this.usuario = usuario;
    }

    @NonNull
    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario( String id_usuario) {
        this.id_usuario = id_usuario;
    }

    @NonNull
    public String getDestino() {
        return destino;
    }

    public void setDestino( String destino) {
        this.destino = destino;
    }

    @NonNull
    public String getQueja() {
        return queja;
    }

    public void setQueja( String queja) {
        this.queja = queja;
    }

    @NonNull
    public String getFoto() {
        return foto;
    }

    public void setFoto( String foto) {
        this.foto = foto;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha( String fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public String getCant_likes() {
        return cant_likes;
    }

    public void setCant_likes( String cant_likes) {
        this.cant_likes = cant_likes;
    }

    @NonNull
    public String getDio_like() {
        return dio_like;
    }

    public void setDio_like( String dio_like) {
        this.dio_like = dio_like;
    }

    @Override
    public String toString() {
        return "ModelFeed{" +"id=" + id +
                ", usuario='" + usuario + '\'' +
                ", id_usuario='" + id_usuario + '\'' +
                ", destino='" + destino + '\'' +
                ", queja='" + queja + '\'' +
                ", foto='" + foto + '\'' +
                ", fecha='" + fecha + '\'' +
                ", cant_likes='" + cant_likes + '\'' +
                ", dio_like='" + dio_like + '\'' +
                '}';
    }

    /*@Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", body = "+body+", title = "+title+"]";
    }*/
}

/*
@Entity(tableName = "post_info")


return "ClassPojo [id = "+id+", body = "+body+", title = "+title+"]";

 public class ModelFeed implements Parcelable{
        @NonNull
        @ColumnInfo(name = "userId")
        @SerializedName("userId")
        @Expose
        private Integer userId;

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @Expose
        private Integer id;

        @NonNull
        @ColumnInfo(name = "title")
        @SerializedName("title")
        @Expose
        private String title;

        @NonNull
        @ColumnInfo(name = "body")
        @SerializedName("body")
        @Expose
        private String body;

        public final Creator<ModelFeed> CREATOR = new Creator<ModelFeed>() {

            @SuppressWarnings({
                    "unchecked"
            })
            public ModelFeed createFromParcel(Parcel in) {
                ModelFeed instance = new ModelFeed();

                instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));

                instance.title = ((String) in.readValue((String.class.getClassLoader())));

                instance.body = ((String) in.readValue((String.class.getClassLoader())));

                return instance;
            }

            public ModelFeed[] newArray(int size) {
                return (new ModelFeed[size]);
            }

        };



        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }



        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }



        public void writeToParcel(Parcel dest, int flags) {

            dest.writeValue(id);

            dest.writeValue(title);

            dest.writeValue(body);

        }

        public int describeContents() {
            return 0;
        }
    }

*/

package com.bufeotec.sipcsi.MiFeed.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("index.php?c=Pueblo&a=listar_mis_publicaciones_ws&key_mobile=123456asdfgh")
    Call<String> savePost(@Field("id") String id);
}

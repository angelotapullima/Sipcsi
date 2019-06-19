package com.bufeotec.sipcsi.RetrofitRoom.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("index.php?c=Pueblo&a=listar_ws&key_mobile=123456asdfgh")
    Call<String> savePost(@Field("id") String id);
}

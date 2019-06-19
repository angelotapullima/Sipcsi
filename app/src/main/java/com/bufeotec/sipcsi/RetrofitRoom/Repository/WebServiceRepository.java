package com.bufeotec.sipcsi.RetrofitRoom.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

//import com.andr.mvvm.RetrofitRoom.Models.ResultModel;

import com.bufeotec.sipcsi.RetrofitRoom.Models.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebServiceRepository {

    Application application;
    public  WebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<ResultModel> webserviceResponseList = new ArrayList<>();

 public LiveData<List<ResultModel>> providesWebService() {

     final MutableLiveData<List<ResultModel>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         APIService service = retrofit.create(APIService.class);
        //  response = service.makeRequest().execute().body();
         service.savePost("19").enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 PostRoomDBRepository postRoomDBRepository = new PostRoomDBRepository(application);
                 postRoomDBRepository.insertPosts(webserviceResponseList);
                 data.setValue(webserviceResponseList);

             }

             @Override
             public void onFailure(Call<String> call, Throwable t) {
                 Log.d("Repository","Failed:::");
             }
         });
     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<ResultModel> parseJson(String response) {

        List<ResultModel> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                ResultModel mMovieModel = new ResultModel();

                //mMovieModel.setId(object.getString("id"));
                mMovieModel.setId(jsonNode.getString("id"));
                mMovieModel.setUsuario(jsonNode.getString("usuario"));
                mMovieModel.setId_usuario(jsonNode.getString("id_usuario"));
                mMovieModel.setDestino(jsonNode.getString("destino"));
                mMovieModel.setQueja(jsonNode.getString("queja"));
                mMovieModel.setFoto(jsonNode.getString("foto"));
                mMovieModel.setFecha(jsonNode.getString("fecha"));
                mMovieModel.setCant_likes(jsonNode.getString("cant_likes"));
                mMovieModel.setDio_like(jsonNode.getString("dio_like"));

                apiResults.add(mMovieModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}

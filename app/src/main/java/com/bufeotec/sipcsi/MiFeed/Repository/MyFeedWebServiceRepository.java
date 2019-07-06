package com.bufeotec.sipcsi.MiFeed.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bufeotec.sipcsi.MiFeed.Models.ModelMyFeed;
import com.bufeotec.sipcsi.Util.Preferences;

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

import static com.bufeotec.sipcsi.Principal.MainActivity.usuarioid;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

public class MyFeedWebServiceRepository {

    Application application;
    Context context;
    public MyFeedWebServiceRepository(Application application ){
        this.application = application;
    }


    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }




    List<ModelMyFeed> webserviceResponseList = new ArrayList<>();

 public LiveData<List<ModelMyFeed>> providesWebService() {


     final MutableLiveData<List<ModelMyFeed>> data = new MutableLiveData<>();

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
         service.savePost(usuarioid).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 MyFeedRoomDBRepository myFeedRoomDBRepository = new MyFeedRoomDBRepository(application);
                 myFeedRoomDBRepository.insertPosts(webserviceResponseList);
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

     //  return retrofit.create(ModelFeed.class);
     return  data;

    }


    private List<ModelMyFeed> parseJson(String response) {

        List<ModelMyFeed> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                ModelMyFeed myFeed = new ModelMyFeed();

                //mMovieModel.setId(object.getString("id"));
                myFeed.setId(jsonNode.getString("id"));
                myFeed.setUsuario(jsonNode.getString("usuario"));
                myFeed.setId_usuario(jsonNode.getString("id_usuario"));
                myFeed.setDestino(jsonNode.getString("destino"));
                myFeed.setQueja(jsonNode.getString("queja"));
                myFeed.setFoto(jsonNode.getString("foto"));
                myFeed.setFecha(jsonNode.getString("fecha"));
                myFeed.setCant_likes(jsonNode.getString("cant_likes"));
                myFeed.setDio_like(jsonNode.getString("dio_like"));

                apiResults.add(myFeed);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }


}

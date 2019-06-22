package com.bufeotec.sipcsi.Feed.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

import com.bufeotec.sipcsi.Feed.Models.ModelFeed;
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

public class FeedWebServiceRepository {

    Preferences preferencesUser;
    Application application;
    Context context;
    public FeedWebServiceRepository(Application application){
        this.application = application;
        preferencesUser = new Preferences(context);
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<ModelFeed> webserviceResponseList = new ArrayList<>();

 public LiveData<List<ModelFeed>> providesWebService() {

     final MutableLiveData<List<ModelFeed>> data = new MutableLiveData<>();

     String response = "";
     //String id = preferencesUser.getIdUsuarioPref();
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         APIServiceFeed service = retrofit.create(APIServiceFeed.class);
        //  response = service.makeRequest().execute().body();
         service.savePost(usuarioid).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
                 feedRoomDBRepository.insertPosts(webserviceResponseList);
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


    private List<ModelFeed> parseJson(String response) {

        List<ModelFeed> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                ModelFeed feed = new ModelFeed();

                //mMovieModel.setId(object.getString("id"));
                feed.setId(jsonNode.getString("id"));
                feed.setUsuario(jsonNode.getString("usuario"));
                feed.setId_usuario(jsonNode.getString("id_usuario"));
                feed.setDestino(jsonNode.getString("destino"));
                feed.setQueja(jsonNode.getString("queja"));
                feed.setFoto(jsonNode.getString("foto"));
                feed.setFecha(jsonNode.getString("fecha"));
                feed.setCant_likes(jsonNode.getString("cant_likes"));
                feed.setDio_like(jsonNode.getString("dio_like"));

                apiResults.add(feed);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}

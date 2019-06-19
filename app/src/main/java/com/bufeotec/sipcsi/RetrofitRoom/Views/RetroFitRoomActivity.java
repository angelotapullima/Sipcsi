package com.bufeotec.sipcsi.RetrofitRoom.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import com.andr.mvvm.R;
//import com.andr.mvvm.RetofitMVVM.RetrofitViews.RetroFitPostFragment;
import com.bufeotec.sipcsi.R;

public class RetroFitRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_fit_room);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_retro_room,new com.bufeotec.sipcsi.RetrofitRoom.Views.RetroFitPostFragment()).commit();
    }
}

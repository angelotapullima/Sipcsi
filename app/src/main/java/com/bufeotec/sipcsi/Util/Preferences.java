package com.bufeotec.sipcsi.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String PREFS_NAME = "User";
    Context context;
    SharedPreferences pref;

    public Preferences(Context context){
        this.context = context;
    }

    public String getNombrePref(){
        pref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return pref.getString("nombre","");
    }
    public String getIdUsuarioPref(){
        pref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return pref.getString("idusuario","");
    }
    public String getRolIdPref(){
        pref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return pref.getString("rol_id","");
    }
    public String getNicknamePref(){
        pref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return pref.getString("nickname","");
    }
    public String getDistritoIdPref(){
        pref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return pref.getString("distrito_id","");
    }
    public String getVehiculoPref(){
        pref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return pref.getString("vehiculo","");
    }

}

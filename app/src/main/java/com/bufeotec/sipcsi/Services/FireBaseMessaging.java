package com.bufeotec.sipcsi.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bufeotec.sipcsi.Activitys.MapaAlertas;
import com.bufeotec.sipcsi.Models.Notificaciones;
import com.bufeotec.sipcsi.Principal.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseMessaging extends FirebaseMessagingService {

    public static final String TAG = "FirebaseService";
    private static final String TIPO= "typePush";
    private static final String TITULO= "titulo";
    private static final String DIRECCION= "direccion";
    private static final String CONTENIDO= "Contenido";
    private final static String CHANNEL_ID = "NOTIFICACION";


    String tipo, titulo,direccion, contenido;
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "Token: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        tipo=remoteMessage.getData().get(TIPO);
        titulo =remoteMessage.getData().get(TITULO);
        direccion =remoteMessage.getData().get(DIRECCION);
        contenido=remoteMessage.getData().get(CONTENIDO);

        Notificaciones notificaciones = new Notificaciones();
        //notificaciones.setTitle(remoteMessage.getNotification().getTitle());
        //notificaciones.setDescription(remoteMessage.getNotification().getBody());
        notificaciones.getDescount(remoteMessage.getData().get(TIPO));
        createNotificationChannel();

        //comentada de codigo
        if (tipo.equals("alarmas")){
            alarma(titulo,direccion, tipo);

        }if(tipo.equals("finalizar")){
            finalizar(contenido ,tipo);
            finalizarEnMapaalertas(contenido ,tipo);
        }
    }

    public void alarma (String title ,String direc , String tipo){
        Intent i = null;
        i = new Intent(MainActivity.ALARMA);
        i.putExtra("titulo",title);
        i.putExtra("direccion",direc);
        i.putExtra("tipo",tipo);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

    }
    public void finalizar (String contenido, String tipo){
        Intent i = null;
        i = new Intent(MainActivity.ALARMA);
        i.putExtra("conte",contenido);
        i.putExtra("tipo",tipo);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

    }
    public void finalizarEnMapaalertas (String contenido, String tipo){
        Intent i = null;
        i = new Intent(MapaAlertas.ALARMA);
        i.putExtra("conte",contenido);
        i.putExtra("tipo",tipo);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
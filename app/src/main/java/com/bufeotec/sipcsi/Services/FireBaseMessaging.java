package com.bufeotec.sipcsi.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bufeotec.sipcsi.Activitys.Login;
import com.bufeotec.sipcsi.Activitys.MapaAlertas;
import com.bufeotec.sipcsi.Models.Notificaciones;
import com.bufeotec.sipcsi.Principal.MainActivity;
import com.bufeotec.sipcsi.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseMessaging extends FirebaseMessagingService {

    public static final String TAG = "FirebaseService";
    private static final String TIPO= "typePush";
    private static final String TITULO= "titulo";
    private static final String DIRECCION= "direccion";
    private static final String CONTENIDO= "Contenido";
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;


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
        notificaciones.setTitle(remoteMessage.getNotification().getTitle());
        notificaciones.setDescription(remoteMessage.getNotification().getBody());
        notificaciones.getDescount(remoteMessage.getData().get(TIPO));
        createNotificationChannel();

        //comentada de codigo
        if (tipo==null){
            ShowNotification(notificaciones);
        }else{
            if (tipo.equals("alarmas")){
                alarma(titulo,direccion, tipo);

            }if(tipo.equals("finalizar")){
                finalizar(contenido ,tipo);
                finalizarEnMapaalertas(contenido ,tipo);
            }
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
    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void ShowNotification(Notificaciones notificaciones ){

        Intent i = new Intent(this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,NOTIFICACION_ID,i,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificacionBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.posible)
                .setContentTitle(notificaciones.getTitle())
                .setContentText(notificaciones.getDescription())
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(defaultSound)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICACION_ID,notificacionBuilder.build());
        }


    }

}
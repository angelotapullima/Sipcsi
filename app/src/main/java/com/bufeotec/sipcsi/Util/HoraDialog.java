package com.bufeotec.sipcsi.Util;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HoraDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Obtener hora actual
        final Calendar c= Calendar.getInstance();
        int hora=c.get(Calendar.HOUR_OF_DAY);
        int  minutos=c.get(Calendar.MINUTE);


        // Retornar en nueva instancia del dialogo selector de fecha
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener)getActivity(),hora,minutos,true);
    }


}

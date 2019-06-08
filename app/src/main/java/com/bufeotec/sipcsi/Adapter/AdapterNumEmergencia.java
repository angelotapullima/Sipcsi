package com.bufeotec.sipcsi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bufeotec.sipcsi.Models.NumEmergencia;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;


public class AdapterNumEmergencia extends ArrayAdapter<NumEmergencia> {
    public AdapterNumEmergencia(Context context, ArrayList<NumEmergencia> numEmergencias) {
        super(context, 0, numEmergencias);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_num_emergencia, parent, false);
        }

         // Get the data item for this position
         NumEmergencia numEmergencia = getItem(position);

         // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.txt_entidad);
        final TextView tvHome = (TextView) convertView.findViewById(R.id.txt_numero);
        // Populate the data into the template view using the data object
        tvName.setText(numEmergencia.getEntidad());
        tvHome.setText(numEmergencia.getNumero());
        // Return the completed view to render on screen


         ImageView btButton = (ImageView) convertView.findViewById(R.id.ivUserIcon);
         // Cache row position inside the button using `setTag`
         btButton.setTag(position);
         // Attach the click event handler
         btButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 int position = (Integer) view.getTag();
                 // Access the row position here to get the correct data item
                 NumEmergencia numEmergencia = getItem(position);
                 // Do what you want here...
                 Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ tvHome.getText().toString()));
                 getContext().startActivity(intent);
             }
         });

        return convertView;
    }
}

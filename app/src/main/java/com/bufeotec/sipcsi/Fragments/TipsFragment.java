package com.bufeotec.sipcsi.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bufeotec.sipcsi.Activitys.DetallesTips;
import com.bufeotec.sipcsi.Adapter.AdaptadorTips;
import com.bufeotec.sipcsi.Models.Info;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;


public class TipsFragment extends Fragment {

    Activity activity;
    Context context;
    RecyclerView rcv_tips;
    AdaptadorTips adaptadorTips;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public TipsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TipsFragment newInstance(String param1, String param2) {
        TipsFragment fragment = new TipsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tips, container, false);
        activity = getActivity();
        context = getContext();
        activity.setTitle("Tips");
        rcv_tips=view.findViewById(R.id.rcv_tips);

        ArrayList<Info> lista = new ArrayList<Info>();

        lista.add(new Info("1. ¿Cómo y dónde denunciar casos de violencia familiar y sexual?",
                "El Perú se encuentra entre uno de los países con más índice de violencia familiar y sexual en el mundo. Los feminicidios están en aumento y solo el 29% de mujeres que son víctimas de violencia denuncias a sus agresores.\n" +
                        "El Gobierno del Perú ha puesto a disposición de la comunidad el Programa Nacional Contra la Violencia Familiar y Sexual para brindar apoyo a las personas en situación de violencia, ya sea contra mujeres o integrantes del grupo familiar a nivel nacional.\n" +
                        "Entre todos los servicios se encuentran los Centros de Emergencia Mujer (CEM), Línea 100, Chat 100, Servicio de Atención Urgente (SAU), Centros de Atención Institucional, Hogares Refugio, Estrategia Rural y el Registro de Víctimas de Esterilizaciones Forzadas.\n"));
        lista.add(new Info("2. ¿Cómo y dónde denunciar casos de trata de persona?",
                "La trata de personas es la captación, recepción, traslado, acogida o retención de personas mediante amenazas o violencia, engaños o fraude, privación de la libertad, recepción de pagos o beneficios, abuso de poder o de situación de vulnerabilidad, para utilizarlas en Venta de niñas, niños y adolescentes, Prostitución y cualquier forma de explotación sexual.\n" +
                        "Para denunciar telefónicamente un caso de trata de personas, comunícate a la línea gratuita 1818del MININTER las 24 horas, todos los días. Al representante que te atienda, indícale tus datos personales y los nombres de los involucrados, así como los detalles de los hechos y el lugar donde sucedieron. Si quieres hacer una denuncia anónima, no estás obligado a dar información personal.\n"));
        lista.add(new Info("3. ¿Qué hacer ante abandono de hogar?",
                "Primero debe presentar una denuncia por abandono. Un policía irá a su casa para corroborar la situación y, luego, podrá obtener la constancia.\n" +
                        "1. Plazo Si han pasado dos años consecutivos de la partida, se genera el divorcio por causal de abandono de hogar.\n" +
                        "2. Bienes La persona que deja el hogar puede perder parte de los bienes que adquirió en el matrimonio.\n" +
                        "3. Hijos La patria potestad de los hijos se pierde cuando se abandona al menor por seis meses continuos.\n" +
                        "4. Deuda Para no recibir los avisos de deudas de su cónyuge, comuníqueles a los acreedores su nueva dirección.\n" +
                        "5. Partida No existe abandono de hogar cuando se trata de convivencia, sin importar que esta se haya certificado.\n"));
        lista.add(new Info("4. ¿Qué hacer ante llamadas extorsionadoras?",
                "Si eres víctima de extorsiones telefónicas, podrás denunciarlo telefónicamente a la Policía Nacional del Perú (PNP) para que hagan las investigaciones correspondientes.\n" +
                        "Reporta tu caso en la central de emergencias llamando al 105. Ellos enviarán tu caso a la comisaría de tu sector.\n" +
                        "Los policías te derivarán a la División de Investigación Criminal (Divincri), que son las unidades especializadas en este tipo de crímenes. Deberás llevar todas las pruebas que tengas, ya sean mensajes de texto, grabaciones de la llamada, el número de la llamada, etc. Esto ayudará en las investigaciones correspondientes.\n"));
        lista.add(new Info("5. ¿Qué hacer cuando eres víctima de estafa?",
                "En caso de estafa, los consumidores tienen dos opciones: acudir a la Fiscalía Provincial Penal de la jurisdicción donde se cometió el delito, es decir, desde donde se concretó la venta, o denunciar el hecho ante la División de Investigación de Alta Tecnología (Divindat)·\n" +
                        "La pena no podrá ser menor a 1 año ni mayor a 6 años, siempre y cuando no exista agravantes, informa Linares. \"Es un agravante que el que comete el delito de estafa lo haga para luego acceder a datos de tarjetas de crédito y ahorro, en esos casos la pena es de entre 4 y 8 años\", sostiene.\n"));
        lista.add(new Info("6. ¿Qué hacer ante el reclamo de una mala atención al cliente?",
                "A. Libro de reclamaciones\n" +
                        "Se trata de un documento físico o virtual donde los consumidores pueden registrar sus quejas o reclamos.\n" +
                        "Así, el proveedor responderá al reclamo, dejando la posibilidad de solucionar directamente un conflicto de consumo. El libro de reclamaciones quedará siempre como una evidencia de la situación o problema con un producto o servicio.\n" +
                        "\n" +
                        "B. Reclamo ante Indecopi\n" +
                        "La segunda alternativa es presentar un reclamo ante el Sistema de Atención del Consumidor del Indecopi (SAC), para que el Indecopi, sin pronunciarse sobre el fondo, provoque un ambiente de conciliación. De llegar a un arreglo se levanta un acta formal.\n" +
                        "\n" +
                        "C. Denuncia ante Indecopi\n" +
                        "La tercera posibilidad es una denuncia ante el Indecopi, que se puede interponer en primera instancia, ante una de las comisiones de protección al consumidor o en las oficinas de solución de controversias.\n"));
        lista.add(new Info("7. ¿Qué Hacer ante abuso de autoridad?",
                "Si eres víctima de abuso de autoridad por parte de agentes policiales, testigo de actos de corrupción en la PNP o si en una comisaría no decepcionan tu denuncia, puedes llamar a la línea gratuita 1818 y denunciar el hecho. También puedes denunciar vía web: https://goo.gl/9YUnD6"));
        lista.add(new Info("8. ¿Que hacer cuando mi calle es zona de comercialización de drogas?",
                "Cualquier persona podrá denunciar de forma anónima los focos de venta de drogas a través de la Central Única de Denuncias (CUD) que hoy fue relanzada y que contará con dos nuevas alternativas de atención a la ciudadanía.\n" +
                        "Ambos canales de atención se suman a los tres que actualmente atiende la línea telefónica 1818 de la CUD.\n" +
                        "\n" +
                        "\n"));


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_tips.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        rcv_tips.setLayoutManager(layoutManager1);




        adaptadorTips = new AdaptadorTips(activity, lista, R.layout.rcv_list_tips,
                new AdaptadorTips.OnItemClickListener() {
                    @Override
                    public void onItemClick(Info info, int position) {

                        String titulo = info.getTitulo();
                        String des= String.valueOf(info.getContenido());

                        Intent i =  new Intent(activity, DetallesTips.class);


                        i.putExtra("titulo",titulo);
                        i.putExtra("des",des);
                        startActivity(i);
                    }
                }

        );


        rcv_tips.setAdapter(adaptadorTips);




        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

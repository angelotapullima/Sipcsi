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

import com.bufeotec.sipcsi.Activitys.DetallerPrimeroAuxilios;
import com.bufeotec.sipcsi.Adapter.AdaptadorPrimerosAuxilios;
import com.bufeotec.sipcsi.Models.Info;
import com.bufeotec.sipcsi.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrimerosAuxiliosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrimerosAuxiliosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrimerosAuxiliosFragment extends Fragment {


    Activity activity;
    Context context;
    AdaptadorPrimerosAuxilios adaptadorPrimerosAuxilios;
    RecyclerView rcv_primerosA;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PrimerosAuxiliosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrimerosAuxiliosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrimerosAuxiliosFragment newInstance(String param1, String param2) {
        PrimerosAuxiliosFragment fragment = new PrimerosAuxiliosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_primeros_auxilios, container, false);

        context = getContext();
        activity = getActivity();
        activity.setTitle("Primero Auxilios");
        rcv_primerosA=view.findViewById(R.id.rcv_primerosA);


        ArrayList<Info> lista = new ArrayList<Info>();

        lista.add(new Info("1. EXPLORACIÓN DE LA VICTIMA, EXPLORACIÓN PRIMARIA",
                "¿Qué se tiene que explorar?\n" +
                        "        * CONSCIENCIA\n" +
                        "        * RESPIRACIÓN\n" +
                        "        * PULSO\n" +
                        "        (*Siempre por este orden)\n" +
                        "        * Respuesta a estímulos\n" +
                        "        * Si hay movimientos torácicos\n" +
                        "        * Si hay pulso carotideo"));


        lista.add(new Info("2. EXPLORACIÓN DEL NIVEL DE CONSCIENCIA",
                "La respuesta verbal:\n" +
                        "            * No habla\n" +
                        "            * Los sonidos son incomprensibles\n" +
                        "            *El lenguaje es confuso\n" +
                        "            * Normal\n" +
                        "\n" +
                        "        La apertura ocular:    \n" +
                        "            * No abre los ojos\n" +
                        "            * Lo hace sólo al dolor o al hablarle\n" +
                        "\n" +
                        "        La respuesta Motora:\n" +
                        "            * No hay movimientos\n" +
                        "            * Movimientos de flexión o extensiones anormales\n" +
                        "            * Los movimientos son orientados\n" +
                        "            * Obedece a las órdenes"));

        lista.add(new Info("REFLEJO PUPILAR",
                "REFLEJO PUPILAR:\n" +
                        "            •   Normalmente las pupilas se contraen al estímulo de la luz. Si ambas pupilas están más grandes de lo normal (dilatadas), la lesión o enfermedad puede indicar shock, hemorragia severa, agotamiento por calor, o drogas tales como cocaína o anfetaminas.\n" +
                        "\n" +
                        "            •   Si ambas pupilas están más pequeñas de lo normal (contraídas), la causa puede ser una insolación o el uso de drogas tales como narcóticos.\n" +
                        "\n" +
                        "            •   Si las pupilas no son de igual tamaño, sospechar un traumatismo craneal o una parálisis.\n" +
                        "\n" +
                        "        FORMA DE EXPLORAR EL REFLEJO PUPILAR:\n" +
                        "            •   Si posee una linterna pequeña, alumbre con el haz de luz el ojo y observe como la pupila se contrae\n" +
                        "\n" +
                        "            •   Si no posee el elemento productor de luz, abra intempestivamente el párpado superior y observe la misma reacción.\n" +
                        "\n" +
                        "            •   Si no hay contracción de una o de ninguna de las dos pupilas, sospeche daño neurológico grave"));


        lista.add(new Info("PROCEDIMIENTO PARA CONTROLAR LA RESPIRACION",
                "VER movimientos torácicos.\n" +
                        "OÍR la respiración del lesionado.\n" +
                        "SENTIR el aliento en la mejilla.\n" +
                        "Para controlar la respiración, deben contarse los movimientos\n" +
                        "respiratorios, tomando la inspiración y la espiración como una sola\n" +
                        "respiración.\n" +
                        "\uF0B7 Colocar al lesionado en posición cómoda (acostada) en caso de\n" +
                        "vomito con la cabeza hacia un lado.\n" +
                        "\uF0B7 Aflojar las prendas de vestir. Inicie el control de la respiración\n" +
                        "observando el tórax y el abdomen, de preferencia después de\n" +
                        "haber tomado el pulso, para que el lesionado no se de cuenta y\n" +
                        "evitar así que cambie el ritmo de la respiración.\n" +
                        "\uF0B7 Cuente las respiraciones por minuto utilizando un reloj con\n" +
                        "segundero.\n" +
                        "\uF0B7 Anote la cifra para verificar los cambios y dar estos datos\n" +
                        "cuando lleve el lesionado al centro asistencial.\n" +
                        "Hay factores que hacen variar el número de respiraciones:\n" +
                        "El ejercicio: la actividad muscular produce un aumento temporal de\n" +
                        "la frecuencia.\n" +
                        "El sexo: en la mujer la respiración tiende a ser más rápida que en\n" +
                        "el hombre.\n" +
                        "La hemorragia: aumenta la frecuencia respiratoria\n" +
                        "La edad: a medida que aumenta, la frecuencia respiratoria tiende a\n" +
                        "disminuir."));



        lista.add(new Info("EXPLORACIÓN DEL FUNCIONAMIENTO\n" +
                "\n" +
                "CARDIACO",
                "El pulso se explora siempre en la arteria carótida.\n" +
                        "En los RN y obesos en la arteria humeral.\n" +
                        "Debemos observar si el pulso es rítmico, regular, frecuencia, etc."));



        lista.add(new Info("PUNTOS PARA TOMAR EL PULSO",
                "El pulso se puede tomar en cualquier arteria superficial que\n" +
                        "pueda comprimirse contra un hueso.\n" +
                        "Los puntos donde se puede tomar el pulso son:\n" +
                        "• En la sien (temporal)\n" +
                        "• En el cuello (carotídeo)\n" +
                        "• En hueco clavicular (subclavia)\n" +
                        "• Parte interna del brazo (humeral)\n" +
                        "• En la muñeca (radial)\n" +
                        "• Parte interna del pliegue del codo (cubital)\n" +
                        "• En la ingle (femoral)\n" +
                        "• En el dorso del pie (pedio)\n" +
                        "• En la tetilla izquierda de bebés (apical)\n" +
                        "En primeros auxilios, los puntos en los que se controla el pulso son\n" +
                        "el radial y el carotídeo."));



        lista.add(new Info("Lesiones multipes",
                "Cabeza: buscar heridas en cara y cuero cabelludo, fracturas,\n" +
                        "lesiones oculares.\n" +
                        "Cuello: buscar deformaciones y bultos.\n" +
                        "Tórax: valorar si existe dificultad respiratoria, heridas,\n" +
                        "hemorragias\n" +
                        "Abdomen: si la pared está o no depresible, suponer\n" +
                        "hemorragias internas, heridas\n" +
                        "Extremidades: buscar posibles fracturas, esguinces,\n" +
                        "luxaciones, etc\n" +
                        "\n" +
                        "7.1. Posición lateral de seguridad\n" +
                        "Es la posición de espera en pacientes inconscientes NO\n" +
                        "traumáticos\n" +
                        "TÉCNICA:\n" +
                        "• Con el accidentado boca arriba, extender el brazo más cercano a\n" +
                        "nosotros y colocarlo flexionado en 90º.\n" +
                        "• Flexionar la pierna más alejada.\n" +
                        "\n" +
                        "• Girar al accidentado suavemente empujándolo del hombro y la\n" +
                        "rodilla más alejados a nosotros\n" +
                        "• Recoger el brazo que gira externamente para darle dos puntos de\n" +
                        "soporte (rodilla y brazo)."));


        lista.add(new Info("ASFIXIA",
                "LAS CAUSAS MAS FRECUENTES SON:\n" +
                        "\uF0B7 Obstrucción de las vías respiratorias.\n" +
                        "\uF0B7 Ambiente tóxico y/o falta de oxígeno.\n" +
                        "\uF0B7 Función pulmonar deficiente.\n" +
                        "\uF0B7 Traumatismos torácicos.\n" +
                        "\uF0B7 Lesiones cerebrales.\n" +
                        "ACTUACIÓN:\n" +
                        "\uF0B7 a) Si existe un obstáculo externo,\n" +
                        "\uF0B7 suprimirlo.\n" +
                        "\uF0B7 b) Colocar al accidentado en un\n" +
                        "\uF0B7 ambiente puro.\n" +
                        "\uF0B7 c) Asegurar la libertad de las vías\n" +
                        "\uF0B7 respiratorias.\n" +
                        "8.1. Situaciones en las que el oxígeno no llega, o llega mal\n" +
                        "a las células del organismo.\n" +
                        "\n" +
                        "Para ello:\n" +
                        "\uF0B7 Aflojar la ropa alrededor del cuello y cintura.\n" +
                        "\uF0B7 Si está inconsciente (aunque respire), colocar dos dedos en\n" +
                        "la barbilla y una mano en la frente basculando la cabeza\n" +
                        "hacia atrás suavemente; con esta maniobra se libera la\n" +
                        "garganta obstruida por la caída de la lengua hacia atrás.\n" +
                        "\n" +
                        "\uF0B7 Abrir la boca y liberar de aquello que la obstruya (vómito,\n" +
                        "secreciones, dentadura postiza móvil, etc.).\n" +
                        "\uF0B7 Colocar en posición lateral de seguridad a fin de permitir la\n" +
                        "salida de sangre o vómito.\n" +
                        "\n"));


        lista.add(new Info("Obstrucción de la vía oral",
                "LIGERA:\n" +
                        "VÍCTIMA AGITADA CON TOS EFECTIVA\n" +
                        "ACTUACIÓN:\n" +
                        "DEJAR QUE TOSA Y VIGILARLA (ANIMAR A QUE TOSA)\n" +
                        "NO DAR GOLPES EN LA ESPALDA\n" +
                        "\n" +
                        "9.1. Obstrucción de vía oral\n" +
                        "\n" +
                        "SEVERA:\n" +
                        "VÍCTIMA QUE NO HABLA\n" +
                        "TOS INEFECTIVA\n" +
                        "CIANOSIS\n" +
                        "ALTERACIÓN\n" +
                        "PROGRESIVA DE\n" +
                        "CONSCIENCIA\n" +
                        "PUEDE ESTAR\n" +
                        "CONSCIENTE O\n" +
                        "INCONSCIENTE\n" +
                        "SI ESTA CONSCIENTE:\n" +
                        "5 GOLPES INTERESCAPULARES\n" +
                        "5 COMPRESIONES ABDOMINALES\n" +
                        "SI ESTA INCONSCIENTE:\n" +
                        "Iniciar RCP\n" +
                        "\n" +
                        "9.2. LACTANTES\n" +
                        "5 palmadas en la espalda\n" +
                        "5 compresiones torácicas\n" +
                        "\n" +
                        "OTRAS CAUSAS DE ASFIXIA\n" +
                        "\n" +
                        "\uF0B7 AHOGAMIENTO: Drenaje postural (agua del estómago),\n" +
                        "RCP, PLS, mantener abrigado, traslado hospitalario, aunque\n" +
                        "se recupere (segundo ahogamiento)\n" +
                        "\uF0B7 AHORCAMIENTO: Retirar objetos del cuello, sujetar el\n" +
                        "cuerpo, abrir vías respiratorias, RCP. Si respira: PLS\n" +
                        "\uF0B7 HIPERVENTILACIÓN: respiración rápida y profunda, mareos,\n" +
                        "temblor, hormigueo, calambres, tranquilizar, alejar del\n" +
                        "\n" +
                        "conflicto, 10 ciclos respiratorios dentro de bolsa, 15 segundos\n" +
                        "fuera y repetir hasta que remita.\n" +
                        "\uF0B7 CRISIS ASMÁTICA: tranquilizar, no tumba, ayudar a\n" +
                        "administrar medicación y si pierde consciencia: abrir vías y\n" +
                        "prepararse para RCP\n" +
                        "\uF0B7 INTOXICACIÓN:\n" +
                        "Si se advierte la presencia de un gas tóxico o inflamable, se\n" +
                        "deben tomar por parte del socorrista las siguientes\n" +
                        "precauciones:\n" +
                        "\uF0A7 Protegerse o contener la respiración antes de la\n" +
                        "evacuación del accidentado.\n" +
                        "\uF0A7 No encender cerillas ni tocar interruptores.\n" +
                        "\uF0A7 Emplear una cuerda guía para el rescate\n" +
                        "\uF0A7 Abrir vías y prepararse para RCP"));

        lista.add(new Info("LIPOTIMIA",
                "Puede estar causada por fatiga, dolor, hambre, emoción\n" +
                        "repentina, lugar poco ventilado, calor, etc.\n" +
                        "SINTOMAS:\n" +
                        "PALIDEZ\n" +
                        "PIEL FRÍA Y SUDOROSA\n" +
                        "PULSO DÉBIL Y LENTO\n" +
                        "SENSACIÓN DE MAREO\n" +
                        "DEBILIDAD\n" +
                        "Víctima pálida, fría y sudorosa, disminuye la frecuencia\n" +
                        "cardiaca y generalmente, la víctima nota que se desmaya.\n" +
                        "\n" +
                        "10.1. LIPOTIMIA\n" +
                        "\n" +
                        "Actuación:\n" +
                        "• Aflojar la ropa alrededor del cuello, pecho y cintura.\n" +
                        "• Traslado a un ambiente de aire puro.\n" +
                        "• Tumbarlo en posición horizontal con las piernas elevadas unos 45º\n" +
                        "• Mantener la permeabilidad de la vía aérea y asegurarse de que\n" +
                        "respira y tiene pulso."));

        lista.add(new Info("EPILEPSIA",
                "Afección crónica de diversa etiología caracterizada por\n" +
                        "crisis convulsivas recurrentes debidas a una descarga\n" +
                        "excesiva de las neuronas cerebrales.\n" +
                        "ACTUACIÓN:\n" +
                        "\uF0B7 Despejar el entorno de cualquier objeto que pueda herir al\n" +
                        "enfermo.\n" +
                        "\uF0B7 No intentar sujetar o inmovilizar al paciente\n" +
                        "\uF0B7 Deslizar una manta o ropa debajo del afectado para\n" +
                        "amortiguar los golpes.\n" +
                        "\uF0B7 No forzar la introducción de objetos en la boca de un paciente\n" +
                        "que se encuentra\n" +
                        "convulsionando.\n" +
                        "\uF0B7 Aflojar las ropas cuando cesa la crisis.\n" +
                        "\uF0B7 PLS cuando cesa la crisis"));

        lista.add(new Info("HEMORRAGIAS",
                "Clasificación\n" +
                        "Según el vaso sanguíneo lesionado se clasifican en:\n" +
                        "ARTERIALES: color rojo vivo (sangre oxigenada), sale a gran\n" +
                        "presión, como borbotones a impulsos rítmicos.\n" +
                        "VENOSAS: color rojo violáceo (sangre de retorno), sale lenta\n" +
                        "y continuamente a menor presión.\n" +
                        "CAPILARES: color rojo, sale desde pequeños puntitos\n" +
                        "continuamente. Es la llamada “hemorragia en sábana”.\n" +
                        "12.1. HEMORRAGIAS EXTERNAS\n" +
                        "\n" +
                        "ACTUACIÓN\n" +
                        "• Compresión directa sobre el punto sangrante con apósitos,\n" +
                        "durante 10 minutos.\n" +
                        "NO RETIRAR NUNCA EL PRIMER APÓSITO\n" +
                        "• Elevar el miembro afectado, si las lesiones lo permiten.\n" +
                        "• Si no cede, compresión arterial a distancia\n" +
                        "Técnica que puede resultar dolorosa (informar a la víctima)\n" +
                        "La sangre sale al exterior a través de una herida\n" +
                        "• Unicamente en casos muy especiales:TORNIQUETE\n" +
                        "¡¡ EXCEPCIÓN !! NO PODREMOS HACER PRESIÓN\n" +
                        "DIRECTA EN LA HERIDA SI EXISTE FRACTURA ABIERTA\n" +
                        "\n" +
                        "12.2. HEMORRAGIAS INTERNAS\n" +
                        "\n" +
                        "OTORRAGIA\n" +
                        "La sangre fluye por el oído\n" +
                        "• Poner en PLS, con el oído sangrante hacia el suelo.\n" +
                        "• Si ha habido traumatismo, puede haber fractura.\n" +
                        "• NO MOVER, pero facilitar la salida de sangre.\n" +
                        "• No intentar nunca parar la hemorragia.\n" +
                        "EPISTAXIS\n" +
                        "La sangre fluye por la nariz\n" +
                        "ACTUACIÓN:\n" +
                        "• Presión directa sobre el orificio sangrante, contra el tabique\n" +
                        "nasal durante 5 min.\n" +
                        "• Inclinar la cabeza hacia delante.\n" +
                        "• Si no se detiene la hemorragia, taponar con gasa mojada\n" +
                        "en agua oxigenada, dejando un trozo de gasa fuera.\n" +
                        "HEMOPTISIS\n" +
                        "La sangre fluye por la boca\n" +
                        "\uF0B7 Control de signos vitales\n" +
                        "\uF0B7 Dieta absoluta\n" +
                        "\uF0B7 Colocar a la víctima en posición semisentada.\n" +
                        "\uF0B7 Traslado a un centro sanitario\n" +
                        "HEMATEMESIS\n" +
                        "La sangre fluye por la boca\n" +
                        "\uF0B7 Control de signos vitales\n" +
                        "\uF0B7 Dieta absoluta\n" +
                        "\uF0B7 Colocar a la víctima en PLS.\n" +
                        "\uF0B7 Traslado a un centro sanitario"));




        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcv_primerosA.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        rcv_primerosA.setLayoutManager(layoutManager1);




        adaptadorPrimerosAuxilios = new AdaptadorPrimerosAuxilios(activity, lista, R.layout.rcv_list_primeros_auxilios  ,
                new AdaptadorPrimerosAuxilios.OnItemClickListener() {
                    @Override
                    public void onItemClick(Info info, int position) {

                        String titulo = info.getTitulo();
                        String des= String.valueOf(info.getContenido());

                        Intent i =  new Intent(activity, DetallerPrimeroAuxilios.class);


                        i.putExtra("titulo",titulo);
                        i.putExtra("des",des);
                        startActivity(i);
                    }
                }

        );


        rcv_primerosA.setAdapter(adaptadorPrimerosAuxilios);


        return view;
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

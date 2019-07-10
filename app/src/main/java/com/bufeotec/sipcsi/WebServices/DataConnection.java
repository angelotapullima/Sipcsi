package com.bufeotec.sipcsi.WebServices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bufeotec.sipcsi.Models.Accidentes;
import com.bufeotec.sipcsi.Models.Alertas;
import com.bufeotec.sipcsi.Models.Apoyo;
import com.bufeotec.sipcsi.Models.Areas;
import com.bufeotec.sipcsi.Models.Delito;
import com.bufeotec.sipcsi.Models.Puntos;
import com.bufeotec.sipcsi.Models.Usuario;
import com.bufeotec.sipcsi.Models.Vehiculos;
import com.bufeotec.sipcsi.Principal.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DataConnection extends AppCompatActivity {

    private static final String TAG = "DataConnection" ;
    public String funcion,idusuario;
    public String parametros,respuesta = "false",cargarDatos;
    boolean mensajeprogres;


    SharedPreferences sharedPreferences;

    public ArrayList<Puntos> listpuntos = new ArrayList();
    public ArrayList<Puntos> listturisticos = new ArrayList();
    public ArrayList<Vehiculos> listaVehiculos= new ArrayList();
    public ArrayList<Vehiculos> listaUbicacion= new ArrayList();
    public ArrayList<Alertas> listaAlertas= new ArrayList();
    public ArrayList<Alertas> listaAlertasHechas= new ArrayList();
    public ArrayList<Alertas> listaAtenciones= new ArrayList();
    public ArrayList<Vehiculos> listaVehiculosAlerta= new ArrayList();
    public ArrayList<Vehiculos> listaBasureros= new ArrayList();
    public ArrayList<Vehiculos> listaBasurerosActivos= new ArrayList();
    public ArrayList<Puntos> listaRutasBasureros= new ArrayList();
    public ArrayList<Areas> listaAreas= new ArrayList();
    public ArrayList<Accidentes> listaCausas= new ArrayList();
    public ArrayList<Usuario> listaPerfil= new ArrayList();
    public ArrayList<Accidentes> listaAccidentes= new ArrayList();
    public ArrayList<Delito> listaDelito= new ArrayList();
    public ArrayList<Apoyo> listaApoyo= new ArrayList();
    Activity context;

    Runnable run;
    Usuario obj;
    Puntos puntos;
    Vehiculos vehiculos;
    Alertas alertas;
    Areas areas;

    Accidentes accidentes;
    JSONObject json_data;
    public static  final String IP= "www.guabba.com/accidentestransito";

    public DataConnection(){

    }
    public DataConnection(Activity context, String funcion, Usuario obj, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.obj = obj;
        this.mensajeprogres = mensajeprogres;


        new GetAndSet().execute();
    }

    public DataConnection(Activity context, String funcion, String idusuario, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.idusuario = idusuario;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }






    public DataConnection(Activity context, String funcion, Vehiculos vehiculos, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.vehiculos=vehiculos;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }

    public DataConnection(Activity context, String funcion, Accidentes accidentes, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.accidentes=accidentes;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }

    public DataConnection(Activity context, String funcion, Puntos puntos, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.puntos=puntos;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }


    public DataConnection( Runnable run,String funcion, Vehiculos vehiculos, boolean mensajeprogres) {
        this.run=run;
        this.funcion=funcion;
        this.vehiculos=vehiculos;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }

    public DataConnection(Activity context, String funcion, Alertas alertas, boolean mensajeprogres) {
        this.context=context;
        this.funcion=funcion;
        this.alertas=alertas;
        this.mensajeprogres = mensajeprogres;
        new GetAndSet().execute();
    }

    public DataConnection(Activity context, String funcion, boolean mensajeprogres) {
        this.context=context;
        this.funcion=funcion;
        this.mensajeprogres=mensajeprogres;
        new GetAndSet().execute();
    }

    public DataConnection(Runnable run, String funcion, String idusuario, boolean mensajeprogres) {
        this.run=run;
        this.funcion=funcion;
        this.mensajeprogres=mensajeprogres;
        new GetAndSet().execute();
    }


    //Enviamos los datos al webservice y devuelve un valor string-respuesta
    private String obtenerDatos(){
        StringBuffer response = null;
        try {

            URL url = new URL("https://"+IP+"/index.php?c=Cliente&a=registrar");


            if(funcion.equals("loginUsuario")){
                parametros = "usuario=" + URLEncoder.encode(obj.getUsuario_nickname(),"UTF-8")
                        + "&contrasenha=" + URLEncoder.encode(obj.getUsuario_contrasenha(),"UTF-8");

                url = new URL("https://"+IP+"/index.php?c=Admin&a=loguearse&key_mobile=123456asdfgh");
            }if (funcion.equals("mostrarPuntos")){

                parametros = "accidentes=" + URLEncoder.encode(puntos.getAccidentes(),"UTF-8")
                        + "&delitos=" + URLEncoder.encode(puntos.getDelitos(),"UTF-8")
                        + "&basura=" + URLEncoder.encode(puntos.getBasura(),"UTF-8")
                        + "&vial=" + URLEncoder.encode(puntos.getVial(),"UTF-8")
                        + "&camaras=" + URLEncoder.encode(puntos.getCamaras(),"UTF-8")
                        + "&fechainicio=" + URLEncoder.encode(puntos.getFinicio(),"UTF-8")
                        + "&fechafinal=" + URLEncoder.encode(puntos.getFeinal(),"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Mapa&a=incidencias&key_mobile=123456asdfgh");
            }


            if (funcion.equals("listarVehiculos")){
                parametros = "id=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Vehiculo&a=mostrar_ws&key_mobile=123456asdfgh");

            }if (funcion.equals("ubicacionVehiculo")){
                parametros = "id=" + URLEncoder.encode(vehiculos.getId_vehiculo(),"UTF-8")
                        + "&token=" + URLEncoder.encode(vehiculos.getTok(),"UTF-8");

                url = new URL("https://"+IP+"/index.php?c=Vehiculo&a=ubicacion_vehiculo&key_mobile=123456asdfgh");

            }if (funcion.equals("listarAlertas")){
                parametros = "id_distrito=" + URLEncoder.encode(alertas.getDistrito_id(),"UTF-8")+
                        "&id_usuario=" + URLEncoder.encode(alertas.getUsuario_id(),"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Alerta&a=listar_alertas_pendientes&key_mobile=123456asdfgh");

            }
            if (funcion.equals("listarVehiculosAlerta")){
                parametros = "id_alerta=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Alerta&a=usuarios_atencion_alertas_lista&key_mobile=123456asdfgh");

            }if (funcion.equals("listarAtenciones")){
                parametros = "id_usuario=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Alerta&a=listar_mis_atenciones&key_mobile=123456asdfgh");

            }
            if (funcion.equals("listarAreas")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Pueblo&a=listar_areas_ws&key_mobile=123456asdfgh");

            }if (funcion.equals("listarAlertasHechas")){
                parametros = "id_usuario=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Alerta&a=listar_mis_alertas&key_mobile=123456asdfgh");

            }
            if (funcion.equals("listarPerfil")){
                parametros = "id_usuario=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Usuario&a=perfil_ws&key_mobile=123456asdfgh");

            }if (funcion.equals("listarAccidentes")){
                parametros = //"id_distrito=" + URLEncoder.encode(accidentes.getDistrito(),"UTF-8")+
                        "&id_usuario=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Accidente&a=mostrar_ws&key_mobile=123456asdfgh");

            }if (funcion.equals("listarDelitos")){
                parametros = //"id_distrito=" + URLEncoder.encode(accidentes.getDistrito(),"UTF-8")+
                        "&id_usuario=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Robo&a=mostrar_ws&key_mobile=123456asdfgh");

            }if (funcion.equals("listarApoyo")){
                parametros = //"id_distrito=" + URLEncoder.encode(accidentes.getDistrito(),"UTF-8")+
                        "&id_usuario=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Apoyo&a=mostrar_ws&key_mobile=123456asdfgh");

            }if (funcion.equals("listarCausas")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");
                url = new URL("https://"+IP+"/index.php?c=CausaAccidente&a=listar_causas&key_mobile=123456asdfgh");

            }if (funcion.equals("listarRutasBasureros")){
                parametros = "id_vehiculo=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Rutas&a=listar_ruta_basurero&key_mobile=123456asdfgh");
            }if (funcion.equals("listarBasurerosActivos")){
                parametros = "id_distrito=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Vehiculo&a=ubicacion_basureros&key_mobile=123456asdfgh");
            }
            if (funcion.equals("listarBasureros")){
                parametros = "id_usuario=" + URLEncoder.encode(idusuario,"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Rutas&a=listar_vehiculos_basureros&key_mobile=123456asdfgh");
            }
            if (funcion.equals("listarTuristicos")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Mapa&a=puntos_turisticos&key_mobile=123456asdfgh");

            }
             if (funcion.equals("editarPerfil")){
                 parametros = "usuario_nombre=" + URLEncoder.encode(obj.getUsuario_nombre(),"UTF-8")
                         + "&usuario_apellido=" + URLEncoder.encode(obj.getUsuario_apellido(),"UTF-8")
                         + "&distrito_id=" + URLEncoder.encode(obj.getDistrito_id(),"UTF-8")
                         + "&usuario_nickname=" + URLEncoder.encode(obj.getUsuario_nickname(),"UTF-8")
                         + "&usuario_id=" + URLEncoder.encode(obj.getUsuario_id(),"UTF-8");
                url = new URL("https://"+IP+"/index.php?c=Usuario&a=editar_perfil&key_mobile=123456asdfgh");

            }

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language","en-US,en;q=0.5");
            con.setDoOutput(true);

            con.setFixedLengthStreamingMode(parametros.getBytes().length);

            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(parametros.getBytes());
            out.flush();
            out.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine=in.readLine()) !=null){
                response.append(inputLine);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();

    }

    String valorcodigo,rol;

    private  boolean filtrardDatos(){

        cargarDatos = obtenerDatos();
        try {

            if(!(cargarDatos.equalsIgnoreCase("")  )){


                json_data = new JSONObject(cargarDatos);

                if(funcion.equals("loginUsuario")){

                    //if(cargarDatos.equalsIgnoreCase("{\"results\":[]}")){


                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");
                    rol = jsonNodev.optString("rol");

                    if(!valorcodigo.equals("1")){
                        respuesta = "false";
                        obj=null;
                    }else{
                        respuesta = "true";

                    /*if(valorcodigo.equalsIgnoreCase("2") || valorcodigo.equalsIgnoreCase("3")){
                        respuesta = "false";

                        obj = null;
                    }else if(rol.equalsIgnoreCase("3")) {
                        respuesta = "false";
                        obj = null;
                    }else{

                        respuesta = "true";*/


                        //resultJSON = json_data.getJSONArray("results");
                        int count = resultJSON.length();



                        for (int i = 0; i < count;i++){

                            JSONObject jsonNode = resultJSON.getJSONObject(i);

                            obj.setUsuario_id(jsonNode.optString("idusuario"));
                            obj.setRol_id(jsonNode.optString("rol"));
                            obj.setToken(jsonNode.optString("token"));
                            obj.setUsuario_contrasenha(jsonNode.optString("clave"));
                            obj.setUsuario_nickname(jsonNode.optString("nickname"));
                            obj.setDistrito_id(jsonNode.optString("distrito_id"));
                            obj.setDistrito_nombre(jsonNode.optString("distrito"));
                            obj.setUsuario_nombre(jsonNode.optString("nombre"));
                            obj.setVehiculo(jsonNode.optString("vehiculo"));

                            sharedPreferences= context.getSharedPreferences("User",Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("idusuario",obj.getUsuario_id());
                            editor.putString("rol",obj.getRol_id());
                            editor.putString("token",obj.getToken());
                            editor.putString("clave",obj.getUsuario_contrasenha());
                            editor.putString("nickname",obj.getUsuario_nickname());
                            editor.putString("distrito_id",obj.getDistrito_id());
                            editor.putString("distrito",obj.getDistrito_nombre());
                            editor.putString("nombre",obj.getUsuario_nombre());
                            editor.putString("vehiculo",obj.getVehiculo());
                            editor.apply();
                            /*obj.setToken(jsonNode.optString("token"));
                            obj.setChofer(jsonNode.optString("chofer"));*/

                            //Nuevo
                            //obj = new Usuario(obj.getUsuario_id(),obj.getUsuario_usuario(),obj.getUsuario_nombre(),obj.getUsuario_foto(),obj.getUbigeo_id(),obj.getRol_id(),obj.getToken(),obj.getChofer());
                            obj = new Usuario(obj.getUsuario_id(),obj.getRol_id(),obj.getToken(),obj.getUsuario_contrasenha(),obj.getUsuario_nickname(),obj.getDistrito_id(),obj.getDistrito_nombre(),obj.getUsuario_nombre(),obj.getVehiculo());
                        }

                    }

                }if(funcion.equals("mostrarPuntos")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Puntos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Puntos();
                        obj.setFecha(jsonNode.optString("fecha"));
                        obj.setDescripcion(jsonNode.optString("detalle"));
                        obj.setNombre(jsonNode.optString("tipo"));
                        obj.setLat(jsonNode.optString("lat"));
                        obj.setLongitud(jsonNode.optString("long"));


                        //Llenamos los datos al Array
                        listpuntos.add(obj);
                    }
                }

                if(funcion.equals("listarPoints")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Puntos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Puntos();
                        obj.setLat(jsonNode.optString("lat"));
                        obj.setLongitud(jsonNode.optString("lng"));


                        //Llenamos los datos al Array
                        listpuntos.add(obj);
                    }
                }if (funcion.equals("listarVehiculos")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Vehiculos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Vehiculos();
                        obj.setId_vehiculo(jsonNode.optString("id_vehiculo"));
                        obj.setMarca(jsonNode.optString("marca"));
                        obj.setModelo(jsonNode.optString("modelo"));
                        obj.setPlaca(jsonNode.optString("placa"));
                        obj.setColor(jsonNode.optString("color"));
                        obj.setLatitud(jsonNode.optString("vehiculo_coord_x"));
                        obj.setLongitud(jsonNode.optString("vehiculo_coord_y"));
                        obj.setTok(jsonNode.optString("token"));
                        obj.setFecha(jsonNode.optString("fecha"));
                        obj.setTipo(jsonNode.optString("vehiculo_tipo"));
                        obj.setId_vehiculo(jsonNode.optString("id_vehiculo"));
                        obj.setUsuario(jsonNode.optString("usuario"));

                        //Llenamos los datos al Array
                        listaVehiculos.add(obj);
                    }

                }if (funcion.equals("ubicacionVehiculo")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Vehiculos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Vehiculos();
                        obj.setMarca(jsonNode.optString("marca"));
                        obj.setModelo(jsonNode.optString("modelo"));
                        obj.setPlaca(jsonNode.optString("placa"));
                        obj.setColor(jsonNode.optString("color"));
                        obj.setLatitud(jsonNode.optString("vehiculo_coord_x"));
                        obj.setLongitud(jsonNode.optString("vehiculo_coord_y"));
                        obj.setFecha(jsonNode.optString("fecha"));

                        //Llenamos los datos al Array
                        listaUbicacion.add(obj);
                    }

                }if (funcion.equals("listarAlertas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Alertas obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Alertas();
                        obj.setAlerta_id(jsonNode.optString("alerta_id"));
                        obj.setAlerta_tipo(jsonNode.optString("tipo"));
                        obj.setAlerta_tipo_delito(jsonNode.optString("tipo_delito"));
                        obj.setAlerta_descripcion(jsonNode.optString("descripcion"));
                        obj.setAlerta_fecha(jsonNode.optString("fecha"));
                        obj.setCalle_nombre(jsonNode.optString("calle_nombre"));
                        obj.setCalle_x(jsonNode.optString("calle_x"));
                        obj.setCalle_y(jsonNode.optString("calle_y"));
                        obj.setCantidad(jsonNode.optString("cant"));

                        //Llenamos los datos al Array
                        listaAlertas.add(obj);
                    }

                }
                if (funcion.equals("listarVehiculosAlerta")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Vehiculos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Vehiculos();
                        obj.setMarca(jsonNode.optString("marca"));
                        obj.setModelo(jsonNode.optString("modelo"));
                        obj.setPlaca(jsonNode.optString("placa"));
                        obj.setColor(jsonNode.optString("color"));
                        obj.setLatitud(jsonNode.optString("vehiculo_coord_x"));
                        obj.setLongitud(jsonNode.optString("vehiculo_coord_y"));
                        obj.setId_vehiculo(jsonNode.optString("id_vehiculo"));
                        obj.setTok(jsonNode.optString("token"));
                        obj.setUsuario(jsonNode.optString("usuario"));
                        obj.setFechora(jsonNode.optString("alerta_fchahora"));
                        obj.setHacetiempo(jsonNode.optString("alerta_fecha_hace"));
                        obj.setFoto(jsonNode.optString("alerta_imagen"));

                        //Llenamos los datos al Array
                        listaVehiculosAlerta.add(obj);
                    }

                }

                if (funcion.equals("listarAreas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Areas obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Areas();
                        obj.setArea_nombre(jsonNode.optString("area"));
                        //Llenamos los datos al Array
                        listaAreas.add(obj);
                    }

                }
                if (funcion.equals("listarAtenciones")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Alertas obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Alertas();

                        obj.setAlerta_id(jsonNode.optString("alerta_id"));
                        obj.setAlerta_tipo(jsonNode.optString("tipo"));
                        obj.setAlerta_tipo_delito(jsonNode.optString("tipo_delito"));
                        obj.setAlerta_descripcion(jsonNode.optString("descripcion"));
                        obj.setAlerta_fecha(jsonNode.optString("fecha"));
                        obj.setCalle_nombre(jsonNode.optString("calle_nombre"));
                        obj.setCalle_x(jsonNode.optString("calle_x"));
                        obj.setCalle_y(jsonNode.optString("calle_y"));
                        obj.setCantidad(jsonNode.optString("cant"));
                        obj.setAlerta_estado(jsonNode.optString("estado"));
                        listaAtenciones.add(obj);
                    }

                }if (funcion.equals("listarAlertasHechas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Alertas obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Alertas();

                        obj.setAlerta_id(jsonNode.optString("alerta_id"));
                        obj.setAlerta_tipo(jsonNode.optString("tipo"));
                        obj.setAlerta_tipo_delito(jsonNode.optString("tipo_delito"));
                        obj.setAlerta_descripcion(jsonNode.optString("descripcion"));
                        obj.setAlerta_fecha(jsonNode.optString("fecha"));
                        obj.setCalle_nombre(jsonNode.optString("calle_nombre"));
                        obj.setAlerta_estado(jsonNode.optString("estado"));
                        obj.setCalle_x(jsonNode.optString("calle_x"));
                        obj.setCalle_y(jsonNode.optString("calle_y"));
                        obj.setCantidad(jsonNode.optString("cant"));

                        listaAlertasHechas.add(obj);
                    }

                }if (funcion.equals("listarPerfil")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Usuario obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Usuario();

                        obj.setUsuario_id(jsonNode.optString("id"));
                        obj.setDistrito_id(jsonNode.optString("distrito"));
                        obj.setUsuario_nombre(jsonNode.optString("nombre"));
                        obj.setUsuario_apellido(jsonNode.optString("apellido"));
                        obj.setUsuario_dni(jsonNode.optString("dni"));
                        obj.setUsuario_nickname(jsonNode.optString("nickname"));
                        obj.setUsuario_contrasenha(jsonNode.optString("contrasenha"));
                        obj.setRol_id(jsonNode.optString("rol_nombre"));
                        obj.setDistrito_nombre(jsonNode.optString("distrito_nombre"));



                        listaPerfil.add(obj);
                    }

                }if (funcion.equals("listarAccidentes")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Accidentes obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Accidentes();

                        obj.setId(jsonNode.optString("id"));
                        obj.setCausa(jsonNode.optString("causa"));
                        obj.setNumero_parte(jsonNode.optString("nparte"));
                        obj.setAsunto(jsonNode.optString("asunto"));
                        obj.setFecha(jsonNode.optString("fecha"));
                        obj.setDestinarario(jsonNode.optString("destinatario"));
                        obj.setDescripcion(jsonNode.optString("descripcion"));
                        obj.setFatal(jsonNode.optString("fatal"));
                        obj.setDireccion(jsonNode.optString("calle_nombre"));
                        obj.setLatitud(jsonNode.optString("calle_x"));
                        obj.setLongitud(jsonNode.optString("rol"));



                        listaAccidentes.add(obj);
                    }

                }if (funcion.equals("listarDelitos")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Delito obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Delito();

                        obj.setId(jsonNode.optString("id"));
                        obj.setNumero_parte(jsonNode.optString("nparte"));
                        obj.setAsunto(jsonNode.optString("asunto"));
                        obj.setFecha(jsonNode.optString("fecha"));
                        obj.setDestinarario(jsonNode.optString("destinatario"));
                        obj.setDescripcion(jsonNode.optString("descripcion"));
                        obj.setDireccion(jsonNode.optString("calle_nombre"));
                        obj.setLatitud(jsonNode.optString("calle_x"));
                        obj.setLongitud(jsonNode.optString("rol"));



                        listaDelito.add(obj);
                    }

                }if (funcion.equals("listarApoyo")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Apoyo obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Apoyo();

                        obj.setId(jsonNode.optString("id"));
                        obj.setNumero_parte(jsonNode.optString("nparte"));
                        obj.setAsunto(jsonNode.optString("asunto"));
                        obj.setFecha(jsonNode.optString("fecha"));
                        obj.setDestinarario(jsonNode.optString("destinatario"));
                        obj.setDescripcion(jsonNode.optString("descripcion"));
                        obj.setDireccion(jsonNode.optString("calle_nombre"));
                        obj.setLatitud(jsonNode.optString("calle_x"));
                        obj.setLongitud(jsonNode.optString("rol"));



                        listaApoyo.add(obj);
                    }

                }if (funcion.equals("listarCausas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Accidentes obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Accidentes();
                        obj.setCausa(jsonNode.optString("causa"));
                        //Llenamos los datos al Array
                        listaCausas.add(obj);
                    }

                }if (funcion.equals("listarBasureros")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Vehiculos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Vehiculos();
                        obj.setId_vehiculo(jsonNode.optString("id_vehiculo"));
                        obj.setMarca(jsonNode.optString("marca"));
                        obj.setModelo(jsonNode.optString("modelo"));
                        obj.setPlaca(jsonNode.optString("placa"));
                        obj.setColor(jsonNode.optString("color"));
                        //Llenamos los datos al Array
                        listaBasureros.add(obj);
                    }

                }
                if (funcion.equals("listarRutasBasureros")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Puntos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Puntos();
                        obj.setLat(jsonNode.optString("x"));
                        obj.setLongitud(jsonNode.optString("y"));
                        //Llenamos los datos al Array
                        listaRutasBasureros.add(obj);
                    }

                } if (funcion.equals("listarBasurerosActivos")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Vehiculos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Vehiculos();
                        obj.setId_vehiculo(jsonNode.optString("id_vehiculo"));
                        obj.setMarca(jsonNode.optString("marca"));
                        obj.setModelo(jsonNode.optString("modelo"));
                        obj.setPlaca(jsonNode.optString("placa"));
                        obj.setColor(jsonNode.optString("color"));
                        obj.setLatitud(jsonNode.optString("vehiculo_coord_x"));
                        obj.setLongitud(jsonNode.optString("vehiculo_coord_y"));
                        obj.setFecha(jsonNode.optString("fecha"));

                        //Llenamos los datos al Array
                        listaBasurerosActivos.add(obj);
                    }

                }if(funcion.equals("listarTuristicos")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Puntos obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Puntos();
                        obj.setFecha(jsonNode.optString("fecha"));
                        obj.setDireccion(jsonNode.optString("calle"));
                        obj.setDescripcion(jsonNode.optString("descripcion"));
                        obj.setNombre(jsonNode.optString("nombre"));
                        obj.setImagen(jsonNode.optString("imagen"));
                        obj.setNombre(jsonNode.optString("nombre"));
                        obj.setLat(jsonNode.optString("coord_x"));
                        obj.setLongitud(jsonNode.optString("coord_y"));


                        //Llenamos los datos al Array
                        listturisticos.add(obj);
                    }
                }

                return true;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }


        return false;
    }

    public boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return  true;
        }else {
            return false;
        }

    }

    class GetAndSet extends AsyncTask<String,String,String > {


        String valor = "";
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(mensajeprogres){
                loading = ProgressDialog.show(context, "Sipsi", "Por favor espere...", false, false);
            }

        }

        @Override
        protected String doInBackground(String... params) {

            try{
                if(filtrardDatos()){

                    //
                    actividad();

                }
            }catch (NullPointerException e){
                valor = "Problema con la conexion a internet";
            }catch (RuntimeException e){
                valor = "Problema con la conexion a internet";
            }
            return valor;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(mensajeprogres){

                loading.dismiss();
            }
            if(!valor.equals("")){
                //Toast.makeText(context, valor, Toast.LENGTH_LONG).show();
            }

        }
    }


    private  void actividad(){

        if(funcion.equals("loginUsuario")){

            if(respuesta.equals("true")){

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("rol_id",obj.getRol_id());
                        intent.putExtra("idusuario",obj.getUsuario_id());
                        intent.putExtra("token",obj.getToken());
                        intent.putExtra("nickname",obj.getUsuario_nickname());
                        intent.putExtra("clave",obj.getUsuario_contrasenha());
                        intent.putExtra("distrito_id",obj.getDistrito_id());
                        intent.putExtra("nombre",obj.getUsuario_nombre());
                        intent.putExtra("vehiculo",obj.getVehiculo());


                        context.startActivity(intent);

                    }
                });
            }else {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(valorcodigo.equalsIgnoreCase("2")){
                            Toast.makeText(context, "Datos incorrectos" + valorcodigo, Toast.LENGTH_SHORT).show();

                        }
                        else if(valorcodigo.equalsIgnoreCase("3")){
                            Toast.makeText(context, "Cuenta desactivada" + valorcodigo, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "No tiene permisos" + valorcodigo, Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }


        }


    }

    public Usuario getUsuario(){
        return obj;
    }

    public ArrayList<Puntos> getPuntos(){
        return listpuntos;
    }
    public ArrayList<Usuario> getListaperfil(){
        return listaPerfil;
    }

    public ArrayList<Vehiculos> getListaVehiculos(){
        return listaVehiculos;
    }

    public ArrayList<Vehiculos> getListaUbicacion(){
        return listaUbicacion;
    }

    public ArrayList<Alertas> getListaAlertas(){
        return listaAlertas;
    }
    public ArrayList<Alertas> getListaAlertasHechas(){
        return listaAlertasHechas;
    }


    public ArrayList<Vehiculos> getListaVehiculosAlerta(){
        return listaVehiculosAlerta;
    }
    public ArrayList<Areas> getListaAreas(){
        return listaAreas;
    }

    public ArrayList<Alertas> getListaAtenciones(){
        return listaAtenciones;
    }



    public ArrayList<Accidentes> getListaAccidentes(){
        return listaAccidentes;
    }
    public ArrayList<Delito> getListaDelito(){
        return listaDelito;
    }
    public ArrayList<Apoyo> getListaApoyo(){
        return listaApoyo;
    }
    public ArrayList<Accidentes> getListaCausas(){
        return listaCausas;
    }
    public ArrayList<Vehiculos> getListaBasureros(){
        return listaBasureros;
    }
    public ArrayList<Puntos> getListaRutasBasureros(){
        return listaRutasBasureros;
    }
    public ArrayList<Puntos> getListturisticos(){
        return listturisticos;
    }
    public ArrayList<Vehiculos> getListaBasurerosActivos(){
        return listaBasurerosActivos;
    }




}

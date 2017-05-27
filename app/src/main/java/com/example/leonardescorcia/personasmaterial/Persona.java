package com.example.leonardescorcia.personasmaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by Lab Software 1 on 20/05/2017.
 */

public class Persona {
    private String uuid,urlFoto,cedula,nombre,apellido,idFoto;

    public Persona(){

    }

    public Persona(String urlFoto, String cedula, String nombre, String apellido, String idFoto) {
        this.uuid= UUID.randomUUID().toString();
        this.urlFoto = urlFoto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idFoto=idFoto;
    }

    public Persona(String uuid, String urlFoto, String cedula, String nombre, String apellido, String idFoto) {
        this.uuid = uuid;
        this.urlFoto = urlFoto;
        this.idFoto = idFoto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }


    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlfoto) {
        this.urlFoto = urlfoto;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idfoto) {
        this.idFoto = idFoto;
    }

    public  void guardar(Context contexto){
        guardarRemoto(contexto);
    }
    public void guardarRemoto(final Context contexto){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection conexion = null;

                try {
                    URL url = new URL("http://34.224.168.6/guardar.php");
                    conexion =(HttpURLConnection)url.openConnection();
                    conexion.setConnectTimeout(30000);
                    conexion.setReadTimeout(30000);

                    //Configuracion de envío de datos via POST
                    conexion.setRequestMethod("POST");
                    conexion.setDoOutput(true);
                    conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                    //Crear consulta con los datos

                    StringBuilder builder = new StringBuilder();
                    builder.append("id");
                    builder.append("=");
                    builder.append(URLEncoder.encode(uuid,"UTF-8"));
                    builder.append("&");
                    builder.append("foto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(getUrlFoto(),"UTF-8"));
                    builder.append("&");
                    builder.append("cedula");
                    builder.append("=");
                    builder.append(URLEncoder.encode(cedula,"UTF-8"));
                    builder.append("&");
                    builder.append("nombre");
                    builder.append("=");
                    builder.append(URLEncoder.encode(nombre,"UTF-8"));
                    builder.append("&");
                    builder.append("apellido");
                    builder.append("=");
                    builder.append(URLEncoder.encode(apellido,"UTF-8"));
                    builder.append("&");
                    builder.append("idfoto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(idFoto,"UTF-8"));

                    String query = builder.toString();

                    conexion.setFixedLengthStreamingMode(query.getBytes("UTF-8").length);

                    OutputStream outputStream = conexion.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write(query);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    //Conectar
                    conexion.connect();

                    //Leer Respuesta del servidor

                    InputStream inputStream = conexion.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder datos = new StringBuilder();
                    String linea;
                    while((linea =bufferedReader.readLine())!=null){
                        datos.append(linea);
                    }

                    bufferedReader.close();
                    return datos.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return null;

            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        urlFoto = jsonObject.getString("foto");
                        guardarLocal(contexto);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }


    public void guardarLocal(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        PersonasSQLiteOpenHelper  aux = new PersonasSQLiteOpenHelper(contexto,"DBPersonas",null);
        db = aux.getWritableDatabase();

        //insertar forma 1
        sql = "INSERT INTO Personas values('"
                +this.getUuid()+"','"
                +this.getUrlFoto()+"','"
                +this.getCedula()+"','"
                +this.getNombre()+"','"
                +this.getApellido()+"','"
                +this.getIdFoto()+"')";

        db.execSQL(sql);

        //insert forma 2

      /*  ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("foto",this.getFoto());
        nuevoRegistro.put("cedula",this.getCedula());
        nuevoRegistro.put("nombre",this.getNombre());
        nuevoRegistro.put("apellido",this.getApellido());
        nuevoRegistro.put("sexo",this.getSexo());
        nuevoRegistro.put("pasatiempo",this.getPasatiempo());

        db.insert("Personas",null,nuevoRegistro);
*/
        //cerrar conexion
        db.close();

    }

    public void eliminar(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        PersonasSQLiteOpenHelper  aux = new PersonasSQLiteOpenHelper(contexto,"DBPersonas",null);
        db = aux.getWritableDatabase();

        sql = "DELETE FROM Personas where cedula='"+this.getCedula()+"'";
        db.execSQL(sql);
        db.close();

    }

    public void modificar(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        PersonasSQLiteOpenHelper  aux = new PersonasSQLiteOpenHelper(contexto,"DBPersonas",null);
        db = aux.getWritableDatabase();

        //insertar forma 1
        sql = "UPDATE Personas SET nombre='"+this.getNombre()+"', apellido='"+this.getApellido()+"' where cedula ='"+this.getCedula()+"'";

        db.execSQL(sql);

        //cerrar conexion
        db.close();

    }



}
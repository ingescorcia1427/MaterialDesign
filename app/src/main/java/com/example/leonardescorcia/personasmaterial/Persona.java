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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by LabSoftware on 12/05/2017.
 */

public class Persona {
    private String uuid, urlFoto, cedula, nombre, apellido, idFoto;

    public Persona(){

    }


    public Persona(String urlFoto, String cedula, String nombre, String apellido, String idFoto) {
        this.uuid = UUID.randomUUID().toString();
        this.urlFoto = urlFoto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idFoto = idFoto;
    }

    //Constructor
    public Persona(String uuid, String urlFoto, String cedula, String nombre, String apellido, String idFoto) {
        this.uuid = uuid;
        this.urlFoto = urlFoto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idFoto = idFoto;
    }

    //GET
    public String getUuid() {
        return uuid;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getIdFoto() {
        return idFoto;
    }


    //SET
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }


    public void guardar(Context contexto){
        guardarRemoto(contexto);
    }


    public void guardarRemoto(final Context contexto){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection conexion = null;

                try {
                    URL url = new URL("http://34.224.168.6/guardar.php");
                    conexion = (HttpURLConnection) url.openConnection();
                    conexion.setConnectTimeout(30000);
                    conexion.setReadTimeout(30000);

                    //Configuración de envío de datos vía POST
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
                    builder.append(URLEncoder.encode(urlFoto,"UTF-8"));
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
                    builder.append("idFoto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(idFoto,"UTF-8"));

                    String query = builder.toString();

                    conexion.setFixedLengthStreamingMode(query.getBytes("UTF-8").length);

                    OutputStream outputStream = conexion.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
                    bufferWriter.write(query);
                    bufferWriter.flush();
                    bufferWriter.close();

                    //Conectar
                    conexion.connect();

                    //Leer Respuesta del Servidor
                    InputStream inputStream = conexion.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferReader = new BufferedReader(inputStreamReader);
                    StringBuilder datos = new StringBuilder();
                    String linea;
                    while((linea = bufferReader.readLine())!=null){
                        datos.append(linea);
                    }

                    bufferReader.close();
                    return datos.toString();


                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean succes = jsonObject.getBoolean("success");
                    if (succes) {
                        urlFoto = jsonObject.getString("urlFoto");
                        guardarLocal(contexto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }



    public void guardarLocal(Context contexto) {
        //Declarar variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexión de la base de datos en modo escritura
        PersonasSQLiteOpenHelper aux = new PersonasSQLiteOpenHelper(contexto, "DBPersonas", null);
        db = aux.getWritableDatabase();

        //Insertar forma 1
        sql = "INSERT INTO Personas values('"
                +this.getUuid()+"','"
                +this.getUrlFoto()+"','"
                +this.getCedula()+"','"
                +this.getNombre()+"','"
                +this.getApellido()+"','"
                +this.getIdFoto()+"')";

        db.execSQL(sql);

        //Insertar forma 2
        /*ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("foto", this.getFoto());
        nuevoRegistro.put("cedula", this.getCedula());
        nuevoRegistro.put("nombre", this.getNombre());
        nuevoRegistro.put("apellido", this.getApellido());
        nuevoRegistro.put("sexo", this.getApellido());
        nuevoRegistro.put("pasatiempo", this.getPasatiempo());

        db.insert("Personas", null, nuevoRegistro);*/

        //Cerrar conexión
        db.close();
    }

    public void eliminar(Context contexto) {
        //Declarar variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexión de la base de datos en modo escritura
        PersonasSQLiteOpenHelper aux = new PersonasSQLiteOpenHelper(contexto, "DBPersonas", null);
        db = aux.getWritableDatabase();

        //Eliminar
        sql = "DELETE FROM Personas where cedula='" + this.getCedula()+"'";
        db.execSQL(sql);
        db.close();
    }

    public void modificar(Context contexto) {
        //Declarar variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexión de la base de datos en modo escritura
        PersonasSQLiteOpenHelper aux = new PersonasSQLiteOpenHelper(contexto, "DBPersonas", null);
        db = aux.getWritableDatabase();

        //Insertar forma 1
        sql = "UPDATE Personas SET nombre='" + this.getNombre() + "', apellido='" + this.getApellido() + "' WHERE cedula='"+this.getCedula()+"'";
        db.execSQL(sql);
        db.close();
    }



}

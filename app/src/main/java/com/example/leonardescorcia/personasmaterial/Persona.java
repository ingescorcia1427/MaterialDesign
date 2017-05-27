package com.example.leonardescorcia.personasmaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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


    public void guardar(Context contexto) {
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

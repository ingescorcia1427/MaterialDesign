package com.example.leonardescorcia.personasmaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LabSoftware on 12/05/2017.
 */

public class Persona {
    String foto, cedula, nombre, apellido;

    public Persona(String foto, String cedula, String nombre, String apellido) {
        this.foto = foto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getFoto() {
        return foto;
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


    public void setFoto(String foto) {
        this.foto = foto;
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

    public void guardar(Context contexto) {
        //Declarar variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexión de la base de datos en modo escritura
        PersonasSQLiteOpenHelper aux = new PersonasSQLiteOpenHelper(contexto, "DBPersonas", null);
        db = aux.getWritableDatabase();

        //Insertar forma 1
        sql = "INSERT INTO Personas values('"
                +this.getFoto()+"','"
                +this.getCedula()+"','"
                +this.getNombre()+"','"
                +this.getApellido()+"')";

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

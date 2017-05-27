package com.example.leonardescorcia.personasmaterial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by LabSoftware on 13/05/2017.
 */

public class Datos {

    public static ArrayList<Persona> traerpersonas(Context contexto) {


    ArrayList<Persona> personas = new ArrayList<>();

    //Declarar variables
    SQLiteDatabase db;
    Persona p;
    String sql, uuid, urlFoto, cedula, nombre, apellido, idFoto;

    //Abrir conexión de lectura
    PersonasSQLiteOpenHelper aux = new PersonasSQLiteOpenHelper(contexto, "dbPersonas", null);
    db = aux.getReadableDatabase();

    //Cursor
    sql = "select * from Personas";
    Cursor c = db.rawQuery(sql, null);

        //Recorrido de cursor
        if(c.moveToFirst()){
            do{
                uuid=c.getString(0);
                urlFoto=c.getString(1);
                cedula=c.getString(2);
                nombre=c.getString(3);
                apellido=c.getString(4);
                idFoto=c.getString(5);
                p = new Persona(uuid, urlFoto, cedula, nombre, apellido, idFoto);
                personas.add(p);
            }
            while (c.moveToNext());
        }
        db.close();

        return personas;
    }

    public static Persona buscarPersona(Context contexto, String ced_buscar){ //ced_buscar = Cedula a Buscar

        //Declarar variables
        SQLiteDatabase db;
        Persona p = null;
        String sql, uuid, urlFoto, cedula, nombre, apellido, idFoto;

        //Abrir conexión de lectura
        PersonasSQLiteOpenHelper aux = new PersonasSQLiteOpenHelper(contexto, "DBPersonas", null);
        db = aux.getReadableDatabase();

        //Cursor
        sql = "select * from Personas where cedula ='"+ ced_buscar+"'";
        Cursor c = db.rawQuery(sql, null);

        //Recorrido de cursor
        if(c.moveToFirst()){
            do{
                uuid=c.getString(0);
                urlFoto=c.getString(1);
                cedula=c.getString(2);
                nombre=c.getString(3);
                apellido=c.getString(4);
                idFoto=c.getString(5);
                p = new Persona(uuid, urlFoto, cedula, nombre, apellido, idFoto);
            }
            while (c.moveToNext());
        }
        db.close();

        return p;
    }


}

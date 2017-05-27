package com.example.leonardescorcia.personasmaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LabSoftware on 13/05/2017.
 */

public class PersonasSQLiteOpenHelper extends SQLiteOpenHelper {
    private String sql = "CREATE TABLE Personas(uuid text, urlfoto text, cedula text, nombre text, apellido text, idFoto text)";
    private static int version=2;

    public PersonasSQLiteOpenHelper(Context contexto, String name, SQLiteDatabase.CursorFactory factory){
        super(contexto, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Personas");
        db.execSQL(sql);
    }
}

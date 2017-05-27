package com.example.leonardescorcia.personasmaterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements AdaptadorPersona.OnPersonaClickListener {
    private RecyclerView listado;
    private ArrayList<Persona> personas;
    private AdaptadorPersona adapter;
    private LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listado = (RecyclerView) findViewById(R.id.lstOpciones);

        personas = Datos.traerPersonas(getApplicationContext());
        adapter = new AdaptadorPersona(personas,this);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listado.setLayoutManager(llm);
        listado.setAdapter(adapter);

    }

    public void agregar(View v){
        finish();
        Intent i = new Intent(Principal.this, AgregarPersona.class);
        startActivity(i);
    }

    @Override
    public void onPersonaClick(Persona p) {
        //finish();
        Intent i = new Intent(Principal.this,DetallePersona.class);
        Bundle b = new Bundle();
        b.putString("nombre",p.getNombre());
        b.putString("apellido",p.getApellido());
        b.putString("urlfoto",p.getUrlFoto());

        i.putExtra("datos",b);
        startActivity(i);
    }
}

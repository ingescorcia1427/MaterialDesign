package com.example.leonardescorcia.personasmaterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetallePersona extends AppCompatActivity {
   private CollapsingToolbarLayout collapsingToolbarLayout;
    private Persona p;
    private String nombre,apellido,urlfoto;
    private Bundle b;
    private Intent i;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_persona);

        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        i = getIntent();
        b=i.getBundleExtra("datos");
        nombre = b.getString("nombre");
        apellido = b.getString("apellido");
        urlfoto = b.getString("urlfoto");

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        foto = (ImageView)findViewById(R.id.fotoPersona);

        Picasso.with(getApplicationContext()).load(urlfoto).into(foto);
        collapsingToolbarLayout.setTitle(nombre+" "+apellido);

    }
}

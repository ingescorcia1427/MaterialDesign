package com.example.leonardescorcia.personasmaterial;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class AgregarPersona extends AppCompatActivity {
    private EditText cajaCedula, cajaNombre, cajaApellido;
    private TextInputLayout icajaCedula, icajaNombre, icajaApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_persona);

        cajaCedula = (EditText) findViewById(R.id.txtCedula);
        cajaNombre = (EditText) findViewById(R.id.txtNombre);
        cajaApellido = (EditText) findViewById(R.id.txtApellido);

        icajaCedula = (TextInputLayout)findViewById(R.id.cedulaPersona);
        icajaNombre = (TextInputLayout)findViewById(R.id.nombrePersona);
        icajaApellido = (TextInputLayout)findViewById(R.id.apellidoPersona);
    }

    public int fotoAleatoria(){
        int fotod[] = {R.drawable.images, R.drawable.images2, R.drawable.images3};
        int numero = (int)(Math.random()*3);
        return fotod[numero];
    }

    public boolean validarTodo() {
        if (cajaCedula.getText().toString().isEmpty()) {
            cajaCedula.setError("Digite cédula");
            cajaCedula.requestFocus();
            return false;
        }
        if (cajaNombre.getText().toString().isEmpty()) {
            cajaNombre.setError("Digite Nombre");
            cajaNombre.requestFocus();
            return false;
        }
        if (cajaApellido.getText().toString().isEmpty()) {
            cajaApellido.setError("Digite Apellido");
            cajaApellido.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validarCedula() {
        if (cajaCedula.getText().toString().isEmpty()) {
            cajaCedula.setError("Digite cédula");
            cajaCedula.requestFocus();
            return false;
        }
        return true;
    }

    public void limpiar(){
        cajaCedula.setText("");
        cajaNombre.setText("");
        cajaApellido.setText("");
        cajaCedula.requestFocus();
    }

    public void borrar(View v){
        limpiar();
    }

    public void guardar(View v){
        String urlFoto, cedula, nombre, apellido, idFoto;;
        Persona p;

        if (validarTodo()){
            urlFoto = String.valueOf(fotoAleatoria());
            cedula = cajaCedula.getText().toString();
            nombre = cajaNombre.getText().toString();
            apellido = cajaApellido.getText().toString();
            idFoto = "prueba";

            p = new Persona(urlFoto, cedula, nombre, apellido, idFoto);
            p.guardar(getApplicationContext());

            InputMethodManager imp = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imp.hideSoftInputFromWindow(cajaCedula.getWindowToken(),0);
            Snackbar.make(v, "Persona guardada Exitosamente", Snackbar.LENGTH_SHORT).show();
            limpiar();
        }
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AgregarPersona.this, Principal.class);
        startActivity(i);
    }

}

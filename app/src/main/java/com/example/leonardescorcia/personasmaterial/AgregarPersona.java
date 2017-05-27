package com.example.leonardescorcia.personasmaterial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

public class AgregarPersona extends AppCompatActivity {
    private EditText cajaCedula;
    private EditText cajaNombre;
    private EditText cajaApellido;
    private boolean guardado;
    private TextInputLayout icajaCedula;
    private TextInputLayout icajaNombre;
    private TextInputLayout icajaApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_persona);

        cajaCedula = (EditText)findViewById(R.id.txtCedula);
        cajaNombre= (EditText)findViewById(R.id.txtNombre);
        cajaApellido = (EditText)findViewById(R.id.txtApellido);

        icajaCedula = (TextInputLayout) findViewById(R.id.cedulaPersona);
        icajaNombre = (TextInputLayout)findViewById(R.id.nombrePersona);
        icajaApellido = (TextInputLayout)findViewById(R.id.apellidoPersona);
        guardado = false;

        cajaCedula.addTextChangedListener(new TextWatcherPersonalizado(icajaCedula,getResources().getString(R.string.mensaje_error_cedula)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s) && !guardado) return true;
                else return false;
            }
        });


        cajaNombre.addTextChangedListener(new TextWatcherPersonalizado(icajaNombre,getResources().getString(R.string.mensaje_error_nombre)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s)&& !guardado) return true;
                else return false;
            }
        });


        cajaApellido.addTextChangedListener(new TextWatcherPersonalizado(icajaApellido,getResources().getString(R.string.mensaje_error_apellido)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s)&& !guardado) return true;
                else return false;
            }
        });

    }




    public int fotoAleatoria(){
        int fotos[] = {R.drawable.images,R.drawable.images2,R.drawable.images3};
        int numero = (int)(Math.random() * 3);
        return fotos[numero];
    }
    public void guardar(View v)  {
        String urlfoto,cedula,nombre,apellido,idfoto;
        Persona p;
        int foto;

        if(validarTodo()){
            cedula = cajaCedula.getText().toString();

            nombre = cajaNombre.getText().toString();
            apellido=cajaApellido.getText().toString();

            foto = fotoAleatoria();
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),foto);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] imagenBytes = baos.toByteArray();
            urlfoto = Base64.encodeToString(imagenBytes, Base64.DEFAULT);
            try {
                baos.close();
            }catch (Exception e){

            }
                idfoto= String.valueOf(foto);
            p = new Persona(urlfoto,cedula,nombre,apellido,idfoto);
            p.guardar(getApplicationContext());

            InputMethodManager imp =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imp.hideSoftInputFromWindow(cajaCedula.getWindowToken(),0);
            Snackbar.make(v,getResources().getString(R.string.mensaje_exitoso_guardar), Snackbar.LENGTH_SHORT).show();
            guardado= true;
            limpiar();


        }
    }

    public void limpiar(){
        cajaCedula.setText("");
        cajaNombre.setText("");
        cajaApellido.setText("");

        cajaCedula.requestFocus();

        guardado = false;
    }
    public boolean validarTodo(){
        if(cajaCedula.getText().toString().isEmpty()){
            icajaCedula.setError(getResources().getString(R.string.mensaje_error_cedula));
            cajaCedula.requestFocus();
            return false;
        }
        if(cajaNombre.getText().toString().isEmpty()){
            icajaNombre.setError(getResources().getString(R.string.mensaje_error_nombre));
            cajaNombre.requestFocus();
            return false;
        }
        if(cajaApellido.getText().toString().isEmpty()){
            icajaApellido.setError(getResources().getString(R.string.mensaje_error_apellido));
            cajaApellido.requestFocus();
            return false;
        }


        return true;
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AgregarPersona.this,Principal.class);
        startActivity(i);
    }
}

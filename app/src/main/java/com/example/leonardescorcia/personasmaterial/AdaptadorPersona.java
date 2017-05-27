package com.example.leonardescorcia.personasmaterial;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lab Software 1 on 20/05/2017.
 */

public class AdaptadorPersona extends RecyclerView.Adapter<AdaptadorPersona.PersonaViewHolder> {

    private ArrayList<Persona> personas;
    private OnPersonaClickListener clickListener;

    public AdaptadorPersona(ArrayList<Persona> personas, OnPersonaClickListener clickListener){
        this.personas=personas;
        this.clickListener=clickListener;
    }

    @Override
    public AdaptadorPersona.PersonaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_personas,parent,false);
        return new PersonaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorPersona.PersonaViewHolder holder, int position) {
            final Persona p = personas.get(position);
            //holder.foto.setImageResource(Integer.parseInt(p.getUrlfoto()));
        Picasso.with(holder.view.getContext()).load(p.getUrlFoto()).into(holder.foto);

        holder.cedula.setText(p.getCedula());
            holder.nombre.setText(p.getNombre());
            holder.apellido.setText(p.getApellido());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onPersonaClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personas.size();
    }

    public static class PersonaViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView cedula;
        private TextView nombre;
        private TextView apellido;
        private View view;

        public PersonaViewHolder(View itemView) {
            super(itemView);
            view= itemView;
            foto = (ImageView)itemView.findViewById(R.id.foto);
            cedula = (TextView)itemView.findViewById(R.id.txtCedulaP);
            nombre = (TextView) itemView.findViewById(R.id.txtNombreP);
            apellido = (TextView)itemView.findViewById(R.id.txtApellidoP);
        }
    }

    public interface OnPersonaClickListener{
        void onPersonaClick(Persona p);
    }

}

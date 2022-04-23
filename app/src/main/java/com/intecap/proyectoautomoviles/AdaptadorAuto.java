package com.intecap.proyectoautomoviles;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorAuto extends RecyclerView.Adapter<AdaptadorAuto.AutoViewHolder> {

    ArrayList<Automovil> listaAutos;

    public AdaptadorAuto (ArrayList<Automovil> listaAutos) {
        this.listaAutos = listaAutos;
    }

    @NonNull
    @Override
    public AdaptadorAuto.AutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listado_autos,null,false);
        return new AutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAuto.AutoViewHolder holder, int position) {
        holder.txtMarca.setText(listaAutos.get(position).getMarca());
        holder.txtLinea.setText(listaAutos.get(position).getLinea());
        holder.txtModelo.setText(listaAutos.get(position).getModelo());
    }

    @Override
    public int getItemCount() {
        return listaAutos.size();
    }

    public class AutoViewHolder extends RecyclerView.ViewHolder {
        TextView txtMarca,txtLinea,txtModelo;
        public AutoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMarca = itemView.findViewById(R.id.txtMarca);
            txtLinea = itemView.findViewById(R.id.txtLinea);
            txtModelo = itemView.findViewById(R.id.txtModelo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context pantVisualizar = v.getContext();
                    Intent i = new Intent(pantVisualizar,VerAuto.class);
                    i.putExtra("id",listaAutos.get(getAdapterPosition()).getCorrelativo());
                    pantVisualizar.startActivity(i);
                }
            });
        }
    }
}

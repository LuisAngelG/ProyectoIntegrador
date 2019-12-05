package com.lgastelu.proyectointegrador.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lgastelu.proyectointegrador.DetailActivity;
import com.lgastelu.proyectointegrador.R;
import com.lgastelu.proyectointegrador.models.Publicaciones;

import java.util.ArrayList;
import java.util.List;

public class PublicacionesAdapter extends RecyclerView.Adapter<PublicacionesAdapter.ViewHolder> {

    private List<Publicaciones> publicaciones;

    public PublicacionesAdapter(){
        this.publicaciones = new ArrayList<>();
    }

    public void setPublicaciones (List<Publicaciones> publicaciones){
        this.publicaciones = publicaciones;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView contenidoText;

        ViewHolder(View itemView){
            super(itemView);
            contenidoText = itemView.findViewById(R.id.txt_content);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publicaciones, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final Context context = viewHolder.itemView.getContext();

        final Publicaciones publicaciones = this.publicaciones.get(position);

        viewHolder.contenidoText.setText(publicaciones.getPub_contenido());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ID", publicaciones.getPub_id());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.publicaciones.size();
    }

}

package com.example.laboratorio2.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorio2.R;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorio2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {
    private List<Alumno> mItems;
    private Activity contexto;

    public AlumnoAdapter(Activity context, List<Alumno> alumnos){
        mItems = alumnos;
        contexto = context;
    }

    @Override
    public int getItemCount(){
        return this.mItems.size();
    }

    @Override
    public AlumnoAdapter.AlumnoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_alumno,
                parent, false);
        AlumnoAdapter.AlumnoViewHolder viewHolder = new AlumnoAdapter.AlumnoViewHolder(view);
        return viewHolder;
    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre, apellidop, apellidom, carrera, sexo;
        public AlumnoViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.txvNombreAlumno);
            apellidop = itemView.findViewById(R.id.txvApellidopAlumno);
            apellidom = itemView.findViewById(R.id.txvApellidomAlumno);
            carrera = itemView.findViewById(R.id.txvCarreraAlumno);
            sexo = itemView.findViewById(R.id.txvSexoAlumno);
        }

    }

    @Override
    public void onBindViewHolder(AlumnoAdapter.AlumnoViewHolder holder, int position){
        final Alumno p = mItems.get(position);
        holder.nombre.setText(p.getNombre());
        holder.apellidop.setText(p.getApellidop());
        holder.apellidom.setText(p.getApellidom());
        holder.carrera.setText(p.getCarrera());
        holder.sexo.setText(p.getSexo());
    }

    public interface OnItemClickListener{
        void onItemClick(Alumno p);
    }

    private AlumnoAdapter.OnItemClickListener mClickListener;
    public void setOnItemClickListener(AlumnoAdapter.OnItemClickListener listener){
        mClickListener = listener;
    }

}

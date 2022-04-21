package com.example.recyclerviewweb.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewweb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// TODO 13: Crea el adaptador de alumno para el RecyclerView
public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>{
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
    public AlumnoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_alumno,
                parent, false);
        AlumnoViewHolder viewHolder = new AlumnoViewHolder(view);
        return viewHolder;
    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre, apellido, codigo, carrera, estado;
        public CircleImageView foto;
        public AlumnoViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.txvNombreAlumno);
            apellido = itemView.findViewById(R.id.txvApellidosAlumno);
            codigo = itemView.findViewById(R.id.txvCodigoAlumno);
            carrera = itemView.findViewById(R.id.txvCarreraAlumno);
            estado = itemView.findViewById(R.id.txvEstadoAlumno);
            foto = itemView.findViewById(R.id.imgFotoAlumno);
        }

    }

    @Override
    public void onBindViewHolder(AlumnoViewHolder holder, int position){
        final Alumno p = mItems.get(position);
        holder.nombre.setText(p.getNombre());
        holder.apellido.setText(p.getApellido());
        holder.codigo.setText(p.getCodigo());
        holder.carrera.setText(p.getCarrera());
        holder.estado.setText(p.getEstado());

        // Para cargar la url de la foto en el circleImage view necesitamos PICASSO
        if (p.getUrlfoto().equals("ninguna"))
            Picasso.with(contexto.getBaseContext()).load(R.drawable.upc).into(holder.foto);
        else
            Picasso.with(contexto.getBaseContext()).load(p.getUrlfoto()).error(R.drawable.upc).placeholder(R.drawable.upc).into(holder.foto);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (mClickListener!=null)
                    mClickListener.onItemClick(p);
            }
        });
    }

    public interface OnItemClickListener{
        void onItemClick(Alumno p);
    }

    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener = listener;
    }

}

package com.upc.ingenieroselectronicos.estudiantessotr.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.upc.ingenieroselectronicos.estudiantessotr.R;

import java.util.List;

//TODO 23: Crear el adaptador de Alumno para el RecyclerView
public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {
    private List<Alumno> mItems;
    private Activity contexto;

    public AlumnoAdapter(Activity context,List<Alumno> alumnos)
    {
        this.mItems = alumnos;
        contexto = context;

    }

    @Override
    public int getItemCount(){
        return mItems.size();
    }

    @Override
    public AlumnoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_alumnos,parent,false);
        AlumnoViewHolder viewHolder = new AlumnoViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(AlumnoViewHolder holder,int position){
        final Alumno p = mItems.get(position);
        holder.nombre.setText(p.getNombre());
        holder.apellido.setText(p.getApellido());
        holder.codigo.setText(p.getCodigo());
        holder.carrera.setText(p.getCarrera());
        holder.estado.setText(p.getEstado());

        if(p.getUrlfoto().length()>30)
            Picasso.with(contexto.getBaseContext()).load(p.getUrlfoto()).error(R.drawable.upc)
                    .placeholder(R.drawable.upc).into(holder.foto);
        else
            Picasso.with(contexto.getBaseContext()).load(R.drawable.upc).into(holder.foto);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mClickListener!=null)
                    mClickListener.onItemClick(p);
            }
        });
    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public TextView apellido;
        public TextView codigo;
        public TextView carrera;
        public ImageView foto;
        public TextView estado;
        public AlumnoViewHolder(View itemView){
            super(itemView);
            //Obtenemos las referencias de los componentes:
            nombre = itemView.findViewById(R.id.txv_nombre_alumno);
            apellido = itemView.findViewById(R.id.txv_apellido_alumno);
            codigo = itemView.findViewById(R.id.txv_codigo_alumno);
            carrera = itemView.findViewById(R.id.txv_carrera_alumno);
            foto  = itemView.findViewById(R.id.img_foto_alumno);
            estado = itemView.findViewById(R.id.txv_estado_alumno);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Alumno p);
    }
    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener = listener;
    }

}

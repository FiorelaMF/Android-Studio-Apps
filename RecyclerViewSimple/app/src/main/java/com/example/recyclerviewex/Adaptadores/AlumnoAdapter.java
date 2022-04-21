package com.example.recyclerviewex.Adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewex.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO 4: Crear el adaptador de alumno heredade RecyclerView y de una clase anidad
    // ViewHolder que hay que crearla dentro de la clase AlumnoAdapter
    // List<String> obj = new ArrayList<>()

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {
    //Refrencia a la lista de datos que se van a cargar en el RecyclerView
    private List<Alumno> lista;

    //Constructor del AlumnoAdapter
    public AlumnoAdapter(List<Alumno> alumnos){
        this.lista = alumnos;
    }

    //Sobre-escribir el metodo getItemCount
    @Override
    public int getItemCount(){
        return this.lista.size();

    }

    //El metodo onCreateViewHolder permite introducir modelo.xml en el RecyclerView
    @Override
    public AlumnoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo, parent, false);
        AlumnoViewHolder viewHolder = new AlumnoViewHolder(vista);
        return viewHolder;
    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder{
        //Definir los componentes que se van a manipular en modelo.xml
        public TextView txvNombre, txvCodigo, txvCarrera, txvPonderado;
        public CircleImageView imgAlumno;
        public AlumnoViewHolder(View itemView){
            super(itemView);
            txvNombre = itemView.findViewById(R.id.txvNombre);
            txvCarrera = itemView.findViewById(R.id.txvCarrera);
            txvCodigo = itemView.findViewById(R.id.txvCodigo);
            txvPonderado = itemView.findViewById(R.id.txvPonderado);
            imgAlumno = itemView.findViewById(R.id.imgAlumno);
        }
    }
    //Este metodo define como vamos a mostar la informacion en modelo.xml
    @Override
    public void onBindViewHolder(AlumnoViewHolder holder, int position){
        Alumno p = this.lista.get(position);

        holder.txvNombre.setText(p.getNombre());
        holder.txvCodigo.setText("Codigo: "+p.getCodigo());
        holder.txvCarrera.setText(p.getCarrera());
        holder.txvPonderado.setText(String.format("%.2f",p.getPonderado()));
        if(p.getPonderado()<13){
            holder.txvPonderado.setTextColor(Color.RED);
        } else{
            holder.txvPonderado.setTextColor(Color.GREEN);
        }

        //if de Imagen hombre/mujer
        if(p.isSexof()){
            holder.imgAlumno.setImageResource(R.drawable.woman_icon);
        } else{
            holder.imgAlumno.setImageResource(R.drawable.man_icon);
        }

        //Tenemos que manejar los clicks sobre el RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(onItemClickListener!=null){
                    onItemClickListener.onClickRecyclerView(p);
                }
                //
            }
        });
    }

    //Creamos una interfaz para la deteccion de clicks en el RecyclerView
    public interface OnItemClickListener{
        void onClickRecyclerView(Alumno p);  //Metodo abstracto que se tendra que implementar para atender los clicks
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }


}






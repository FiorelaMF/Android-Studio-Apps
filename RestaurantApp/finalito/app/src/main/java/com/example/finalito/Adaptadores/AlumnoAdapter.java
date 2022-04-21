package com.example.finalito.Adaptadores;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalito.MisClases.Alumno;
import com.example.finalito.R;

import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {
    private List<Alumno> mItems;
    private Activity contexto;

    public AlumnoAdapter(Activity context,List<Alumno>ListaAlumno){
        mItems=ListaAlumno;
        contexto=context;
    }
    @Override
    public int getItemCount(){
        return this.mItems.size();
    }
    @Override
    public AlumnoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        AlumnoViewHolder viewHolder =new AlumnoViewHolder(view);
        return viewHolder;
    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder{
        //ENLAZAR TODOS LOS WIDGETS DEL CARDVIEW
        TextView txv1;

        public AlumnoViewHolder(View itemView){
            super(itemView);
            txv1=itemView.findViewById(R.id.txv1CardUno);
        }
    }
    //COMO QUIERO Q SE MUESTREN EN EL CARD VIEW
    @Override
    public void onBindViewHolder(AlumnoViewHolder holder, int position){
        final Alumno p=mItems.get(position);
        holder.txv1.setText(p.getNombre()+"\nPrecio: S/."+p.getPrecio()+"\nDisponibles: "+p.getDisponibles());

        //PARA DETECTAR LOS CLICKS DEL CARDVIEW
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null)
                    mClickListener.onItemClick(p);
            }
        });
        ///////////////////////////////////////
    }
    //PARA DETECTAR LOS CLICKS DEL CARDVIEW
    public interface OnItemClickListener{
        void onItemClick(Alumno p);
    }
    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener=listener;
    }
    /////////////////////////////////////////

}

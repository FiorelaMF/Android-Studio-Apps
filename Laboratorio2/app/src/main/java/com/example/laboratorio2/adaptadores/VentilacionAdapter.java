package com.example.laboratorio2.adaptadores;

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

// Crea el adaptador de alumno para el RecyclerView
public class VentilacionAdapter extends RecyclerView.Adapter<VentilacionAdapter.VentilacionViewHolder>{
    private List<Ventilacion> mItems;
    private Activity contexto;

    public VentilacionAdapter(Activity context, List<Ventilacion> ventilaciones){
        mItems = ventilaciones;
        contexto = context;

    }

    @Override
    public int getItemCount(){
        return this.mItems.size();
    }

    @Override
    public VentilacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_ventilacion,
                parent, false);
        VentilacionViewHolder viewHolder = new VentilacionViewHolder(view);
        return viewHolder;
    }

    public class VentilacionViewHolder extends RecyclerView.ViewHolder{
        public TextView fecha, asistentes;
        public VentilacionViewHolder(View itemView){
            super(itemView);
            fecha = itemView.findViewById(R.id.txvFechaVent);
            asistentes = itemView.findViewById(R.id.txvAsistentesVent);
        }

    }

    @Override
    public void onBindViewHolder(VentilacionViewHolder holder, int position){
        final Ventilacion p = mItems.get(position);
        holder.fecha.setText(p.getFecha());
        holder.asistentes.setText(p.getAsistentes());
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (mClickListener!=null) {
                    mClickListener.onItemClick(p);
                }
            }
        }); */
    }

    public interface OnItemClickListener{
        void onItemClick(Ventilacion p);
    }

    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener = listener;
    }

}


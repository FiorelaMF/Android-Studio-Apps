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
public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.AsistenciaViewHolder>{
    private List<Asistencia> mItems;
    private Activity contexto;

    public AsistenciaAdapter(Activity context, List<Asistencia> asistencias){
        mItems = asistencias;
        contexto = context;

    }

    @Override
    public int getItemCount(){
        return this.mItems.size();
    }

    @Override
    public AsistenciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_misasistencias,
                parent, false);
        AsistenciaViewHolder viewHolder = new AsistenciaViewHolder(view);
        return viewHolder;
    }

    public class AsistenciaViewHolder extends RecyclerView.ViewHolder{
        public TextView fecha, hIngreso, hSalida, estado, temp;
        public AsistenciaViewHolder(View itemView){
            super(itemView);
            fecha = itemView.findViewById(R.id.txvFechaAsist);
            hIngreso = itemView.findViewById(R.id.txvIngresoAsist);
            hSalida = itemView.findViewById(R.id.txvSalidaAsist);
            estado = itemView.findViewById(R.id.txvEstadoAsist);
            temp = itemView.findViewById(R.id.txvTempAsist);
        }

    }

    @Override
    public void onBindViewHolder(AsistenciaViewHolder holder, int position){
        final Asistencia p = mItems.get(position);
        holder.fecha.setText(p.getFecha());
        holder.hIngreso.setText(p.gethIngreso());
        holder.hSalida.setText(p.gethSalida());
        holder.estado.setText(p.getEstado());
        holder.temp.setText(p.getTemp());
    }

    public interface OnItemClickListener{
        void onItemClick(Asistencia p);
    }

    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener = listener;
    }

}

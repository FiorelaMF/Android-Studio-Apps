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

// Crea el adaptador de Fiebre para el RecyclerView
public class FiebreAdapter extends RecyclerView.Adapter<FiebreAdapter.FiebreViewHolder>{
    private List<Fiebre> mItems;
    private Activity contexto;

    public FiebreAdapter(Activity context, List<Fiebre> fiebres){
        mItems = fiebres;
        contexto = context;
    }

    @Override
    public int getItemCount(){
        return this.mItems.size();
    }

    @Override
    public FiebreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_casos_fiebre,
                parent, false);
        FiebreViewHolder viewHolder = new FiebreViewHolder(view);
        return viewHolder;
    }

    public class FiebreViewHolder extends RecyclerView.ViewHolder{
        public TextView curso, fecha, nombre_comp, codigo, temp;
        public FiebreViewHolder(View itemView){
            super(itemView);
            curso = itemView.findViewById(R.id.txvCursoFiebre);
            fecha = itemView.findViewById(R.id.txvFechaFiebre);
            nombre_comp = itemView.findViewById(R.id.txvNombreFiebre);
            codigo = itemView.findViewById(R.id.txvCodigoFiebre);
            temp = itemView.findViewById(R.id.txvTempFiebre);
        }

    }

    @Override
    public void onBindViewHolder(FiebreViewHolder holder, int position){
        final Fiebre p = mItems.get(position);
        holder.curso.setText(p.getCurso());
        holder.fecha.setText(p.getFecha());
        holder.nombre_comp.setText(p.getNombre_comp());
        holder.codigo.setText(p.getCodigo());
        holder.temp.setText(p.getTemperatura());
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
        void onItemClick(Fiebre p);
    }

    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener = listener;
    }

}

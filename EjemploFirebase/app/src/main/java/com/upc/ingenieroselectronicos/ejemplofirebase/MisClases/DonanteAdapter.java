package com.upc.ingenieroselectronicos.ejemplofirebase.MisClases;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.upc.ingenieroselectronicos.ejemplofirebase.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//TODO 11: Creamos el adaptador del RecyclerVIew
public class DonanteAdapter extends RecyclerView.Adapter<DonanteAdapter.DonanteViewHolder> {

    private List<Donante> mItems;
    private Activity contexto;

    public DonanteAdapter(Activity contexto, List<Donante> donantes){
        this.contexto = contexto;
        this.mItems = donantes;
    }

    @Override
    public int getItemCount(){
        return this.mItems.size();
    }

    @Override
    public DonanteViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_lista_donantes,
                parent,false);
        DonanteViewHolder viewHolder = new DonanteViewHolder(vista);
        return viewHolder;
    }

    public class DonanteViewHolder extends RecyclerView.ViewHolder{
        public TextView txvNombre, txvCorreo, txvFecha,txvDonacion;
        public CircleImageView imgDonante;
        public DonanteViewHolder(View itemView){
            super(itemView);
            txvNombre = itemView.findViewById(R.id.txvNombre);
            txvCorreo = itemView.findViewById(R.id.txvCorreo);
            txvFecha = itemView.findViewById(R.id.txvFecha);
            txvDonacion = itemView.findViewById(R.id.txvDonacion);
            imgDonante = itemView.findViewById(R.id.imgDonante);


        }
    }

    @Override
    public void onBindViewHolder(DonanteViewHolder holder, int position){
        Donante p = mItems.get(position);
        holder.txvNombre.setText(p.getNombre());
        holder.txvCorreo.setText(p.getCorreo());
        holder.txvFecha.setText(p.getFecha());
        holder.txvDonacion.setText("S/."+String.format("%.2f",p.getDonacion()));
        if(p.getUrlfoto().equals("ninguna")){
            holder.imgDonante.setImageResource(R.drawable.user);
        }else{
            Picasso.with(contexto.getBaseContext()).load(p.getUrlfoto()).error(R.drawable.user).into(holder.imgDonante);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null){
                    mClickListener.onItemClick(p);
                }
            }
        });
    }

    public interface OnItemClickListener{
        void onItemClick(Donante p);


    }

    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener=listener;
    }
}

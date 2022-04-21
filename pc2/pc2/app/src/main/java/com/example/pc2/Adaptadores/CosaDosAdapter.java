package com.example.pc2.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pc2.MisClases.CosaDos;
import com.example.pc2.MisClases.CosaUno;
import com.example.pc2.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//PALABRAS CLAVE SON ADAPTER Y VIEWHOLDER
public class CosaDosAdapter extends RecyclerView.Adapter<CosaDosAdapter.CosaDosViewHolder> {
    private List<CosaDos> mItems;
    private Activity contexto;

    public CosaDosAdapter(Activity context,List<CosaDos>ListaCosaDos){
        mItems=ListaCosaDos;
        contexto=context;
    }
    @Override
    public int getItemCount(){
        return this.mItems.size();
    }
    @Override
    public CosaDosAdapter.CosaDosViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardcosados,parent,false);
        CosaDosAdapter.CosaDosViewHolder viewHolder =new CosaDosAdapter.CosaDosViewHolder(view);
        return viewHolder;
    }

    public class CosaDosViewHolder extends RecyclerView.ViewHolder{
        //ENLAZAR TODOS LOS WIDGETS DEL CARDVIEW
        TextView txv1,txv2;
        CircleImageView img1;

        public CosaDosViewHolder(View itemView){
            super(itemView);
            txv1=itemView.findViewById(R.id.txv1CardDos);
            txv2=itemView.findViewById(R.id.txv2CardDos);
            img1=itemView.findViewById(R.id.imgCardDos);
        }
    }
    //COMO QUIERO Q SE MUESTREN EN EL CARD VIEW
    @Override
    public void onBindViewHolder(CosaDosAdapter.CosaDosViewHolder holder, int position){
        final CosaDos p=mItems.get(position);

        holder.txv1.setText(p.getCampus());
//        holder.txv2.setText(p.getCampus());

//        if(p.getPhotourl().equals("ninguna")){
//            Picasso.with(contexto.getBaseContext()).load(R.drawable.upc).into(holder.imagen);
//        }
//        else{
//            Picasso.with(contexto.getBaseContext()).load(p.getPhotourl()).error(R.drawable.upc).placeholder(R.drawable.upc).into(holder.imagen);
//        }

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
        void onItemClick(CosaDos p);
    }
    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener=listener;
    }
    /////////////////////////////////////////
}

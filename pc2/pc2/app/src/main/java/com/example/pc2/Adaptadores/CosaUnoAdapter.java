package com.example.pc2.Adaptadores;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pc2.MisClases.CosaUno;
import com.example.pc2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//PALABRAS CLAVE ADAPTER VIEWHOLDER
public class CosaUnoAdapter extends RecyclerView.Adapter<CosaUnoAdapter.CosaUnoViewHolder> {
    private List<CosaUno> mItems;
    private Activity contexto;

    public CosaUnoAdapter(Activity context,List<CosaUno>ListaCosaUno){
        mItems=ListaCosaUno;
        contexto=context;
    }
    @Override
    public int getItemCount(){
        return this.mItems.size();
    }
    @Override
    public CosaUnoViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardcosauno,parent,false);
        CosaUnoViewHolder viewHolder =new CosaUnoViewHolder(view);
        return viewHolder;
    }

    public class CosaUnoViewHolder extends RecyclerView.ViewHolder{
        //ENLAZAR TODOS LOS WIDGETS DEL CARDVIEW
        TextView txv1,txv2;

        public CosaUnoViewHolder(View itemView){
            super(itemView);
            txv1=itemView.findViewById(R.id.txv1CardUno);
            txv2=itemView.findViewById(R.id.txv2CardUno);
        }
    }
    //COMO QUIERO Q SE MUESTREN EN EL CARD VIEW
    @Override
    public void onBindViewHolder(CosaUnoViewHolder holder, int position){
        final CosaUno p=mItems.get(position);

        holder.txv1.setText(p.getCodigo());

        String notas="";

        if (!p.getPc1().equals("88.00") && !p.getPc1().equals("-1.00")){
            notas=notas+"PC1:"+p.getPc1()+"\n";
        }else if(p.getPc1().equals("-1.00")){
            notas=notas+"PC1:NR\n";
        }
        if (!p.getLb1().equals("88.00")&& !p.getLb1().equals("-1.00")){
            notas=notas+"LB1:"+p.getLb1()+"\n";
        }else if(p.getLb1().equals("-1.00")){
            notas=notas+"LB1:NR\n";
        }
        if (!p.getEa().equals("88.00")&& !p.getEa().equals("-1.00")){
            notas=notas+"EA:"+p.getEa()+"\n";
        }else if(p.getEa().equals("-1.00")){
            notas=notas+"EA:NR\n";
        }
        if (!p.getPc2().equals("88.00")&& !p.getPc2().equals("-1.00")){
            notas=notas+"PC2:"+p.getPc2()+"\n";
        }else if(p.getPc2().equals("-1.00")){
            notas=notas+"PC2:NR\n";
        }
        if (!p.getLb2().equals("88.00")&& !p.getLb2().equals("-1.00")){
            notas=notas+"LB2:"+p.getLb2()+"\n";
        }else if(p.getLb2().equals("-1.00")){
            notas=notas+"LB2:NR\n";
        }
        if (!p.getTb().equals("88.00")&& !p.getTb().equals("-1.00")){
            notas=notas+"TB:"+p.getTb()+"\n";
        }else if(p.getTb().equals("-1")){
            notas=notas+"TB:NR\n";
        }
        if (!p.getDd().equals("88.00")&& !p.getDd().equals("-1.00")){
            notas=notas+"DD:"+p.getDd()+"\n";
        }else if(p.getDd().equals("-1.00")){
            notas=notas+"DD:NR\n";
        }
        Log.e(">>>",notas);
        holder.txv2.setText(notas);

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
        void onItemClick(CosaUno p);
    }
    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener=listener;
    }
    /////////////////////////////////////////

}

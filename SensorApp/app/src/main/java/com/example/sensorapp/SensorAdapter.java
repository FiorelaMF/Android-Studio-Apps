package com.example.sensorapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder>
public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private List<Sensor> mItems;
    private Activity contexto;

    public SensorAdapter(Activity contexto, List<Sensor> sensores) {
        this.contexto = contexto;
        this.mItems = sensores;
    }

    @Override
    public int getItemCount(){
        return this.mItems.size();
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_lista_datos,
                parent, false);
        SensorViewHolder viewHolder = new SensorViewHolder(vista);
        return viewHolder;
    }

    public class SensorViewHolder extends RecyclerView.ViewHolder{
        public TextView txvTemp, txvHum, txvCO2, txvAforo, txvFecha, txvHora;
        public SensorViewHolder(View itemView){
            super(itemView);
            txvTemp = itemView.findViewById(R.id.txvTemp);
            txvHum = itemView.findViewById(R.id.txvHum);
            txvCO2 = itemView.findViewById(R.id.txvCO2);
            txvAforo = itemView.findViewById(R.id.txvAforo);
            txvFecha = itemView.findViewById(R.id.txvFecha);
            txvHora = itemView.findViewById(R.id.txvHora);



        }
    }

    @Override
    public void onBindViewHolder(SensorViewHolder holder, int position){
        Sensor p = mItems.get(position);
        holder.txvTemp.setText("Temperatura: "+p.getTemperatura()+"°C");
        holder.txvHum.setText("Humedad: "+p.getHumedad()+"%");
        holder.txvCO2.setText("CO2: "+p.getCo2()+" ppm");
        holder.txvAforo.setText("Aforo: "+p.getAforo());
        holder.txvFecha.setText("Fecha: "+p.getFecha());
        holder.txvHora.setText("Hora: "+p.getHora());


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
        void onItemClick(Sensor p);


    }

    private OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener=listener;
    }


}

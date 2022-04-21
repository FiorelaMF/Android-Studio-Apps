package app.acsolutions.ventisysupc.LISTAS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.acsolutions.ventisysupc.R;

public class VerVentilacionAdaptador extends RecyclerView.Adapter<VerVentilacionAdaptador.VerVentilacionHolder>{
    public List<VerVentilacion> ventilaciones;
    public VerVentilacionAdaptador(List<VerVentilacion> ventilaciones) {
        this.ventilaciones = ventilaciones;
    }
    @NonNull
    @Override
    public VerVentilacionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_misventilaciones,parent,false);
        VerVentilacionAdaptador.VerVentilacionHolder holder = new VerVentilacionAdaptador.VerVentilacionHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull VerVentilacionHolder holder, int position) {
        VerVentilacion ventilacion = ventilaciones.get(position);
        holder.CURSO.setText(ventilacion.getCURSO());
        holder.FECHA.setText(ventilacion.getFECHA());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onItemClick(ventilacion);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return ventilaciones.size();
    }
    public static class VerVentilacionHolder extends RecyclerView.ViewHolder{
        TextView FECHA,CURSO;
        public VerVentilacionHolder(@NonNull View itemView) {
            super(itemView);
            FECHA = itemView.findViewById(R.id.mi_ventilacion_fecha);
            CURSO = itemView.findViewById(R.id.mi_ventilacion_curso);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(VerVentilacion ventilaciones);
    }
    private VerVentilacionAdaptador.OnItemClickListener mClickListener;
    public void setOnItemClickListener(VerVentilacionAdaptador.OnItemClickListener listener){
        mClickListener = listener;
    }
}

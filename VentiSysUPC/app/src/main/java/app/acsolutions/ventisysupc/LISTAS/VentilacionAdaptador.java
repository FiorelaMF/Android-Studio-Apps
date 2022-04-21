package app.acsolutions.ventisysupc.LISTAS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.acsolutions.ventisysupc.R;

public class VentilacionAdaptador extends RecyclerView.Adapter<VentilacionAdaptador.VentilacionHolver>{
    public List<Ventilacion> aulas;
    public VentilacionAdaptador(List<Ventilacion> aulas) {
        this.aulas = aulas;
    }
    @NonNull
    @Override
    public VentilacionHolver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_ventilacion,parent,false);
        VentilacionAdaptador.VentilacionHolver holder = new VentilacionAdaptador.VentilacionHolver(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull VentilacionHolver holder, int position) {
        Ventilacion aula = aulas.get(position);
        holder.CURSO.setText(aula.getCURSO());
        holder.FECHA.setText(aula.getFECHA());
        holder.ASIST.setText(aula.getASIST());
    }
    @Override
    public int getItemCount() {
        return aulas.size();
    }
    public static class VentilacionHolver extends RecyclerView.ViewHolder{
        TextView CURSO,FECHA,ASIST;
        public VentilacionHolver(@NonNull View itemView) {
            super(itemView);
            CURSO = itemView.findViewById(R.id.ventilacion_curso);
            FECHA = itemView.findViewById(R.id.ventilacion_fecha);
            ASIST = itemView.findViewById(R.id.ventilacion_asiste);
        }
    }

}

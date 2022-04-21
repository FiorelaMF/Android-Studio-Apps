package app.acsolutions.ventisysupc.LISTAS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import app.acsolutions.ventisysupc.R;

public class MisAsistenciasAdaptador extends RecyclerView.Adapter<MisAsistenciasAdaptador.MisAsistenciasHolder>{
    public List<MisAsistencias> asistencias;
    public MisAsistenciasAdaptador(List<MisAsistencias> asistencias) {
        this.asistencias = asistencias;
    }
    @NonNull
    @Override
    public MisAsistenciasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_misasistencias,parent,false);
        MisAsistenciasAdaptador.MisAsistenciasHolder holder = new MisAsistenciasAdaptador.MisAsistenciasHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull MisAsistenciasHolder holder, int position) {
        MisAsistencias asistencia = asistencias.get(position);
        holder.ESTADO.setText(asistencia.getESTADO());
        holder.FECHA.setText(asistencia.getFECHA());
        holder.INGRESO.setText(asistencia.getINGRESO());
        holder.SALIDA.setText(asistencia.getSALIDA());
        holder.TEMPERATURA.setText(asistencia.getTEMPERATURA());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onItemClick(asistencia);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return asistencias.size();
    }
    public static class MisAsistenciasHolder extends RecyclerView.ViewHolder{
        TextView ESTADO,FECHA,INGRESO,SALIDA,TEMPERATURA;
        public MisAsistenciasHolder(@NonNull View itemView) {
            super(itemView);
            ESTADO = itemView.findViewById(R.id.mis_asistencia_estado);
            FECHA = itemView.findViewById(R.id.mis_asistencias_fecha);
            INGRESO = itemView.findViewById(R.id.mis_asistencia_ingreso);
            SALIDA = itemView.findViewById(R.id.mis_asistencia_salida);
            TEMPERATURA = itemView.findViewById(R.id.mis_asistencia_tempe);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(MisAsistencias asistencias);
    }
    private MisAsistenciasAdaptador.OnItemClickListener mClickListener;
    public void setOnItemClickListener(MisAsistenciasAdaptador.OnItemClickListener listener){
        mClickListener = listener;
    }
}

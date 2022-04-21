package app.acsolutions.ventisysupc.LISTAS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.acsolutions.ventisysupc.R;

public class ClasesDocenteAdaptador extends RecyclerView.Adapter<ClasesDocenteAdaptador.ClasesDocenteHolder>{
    public List<ClasesDocente> clases;
    public ClasesDocenteAdaptador(List<ClasesDocente> clases) {
        this.clases = clases;
    }
    @NonNull
    @Override
    public ClasesDocenteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_docente,parent,false);
        ClasesDocenteAdaptador.ClasesDocenteHolder holder = new ClasesDocenteAdaptador.ClasesDocenteHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ClasesDocenteHolder holder, int position) {
        ClasesDocente clase = clases.get(position);
        holder.CURSO.setText(clase.getCURSO());
        holder.FECHA.setText(clase.getFECHA());
        holder.SECCION.setText(clase.getSECCION());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onItemClick(clase);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return clases.size();
    }
    public interface OnItemClickListener{
        void onItemClick(ClasesDocente clases);
    }
    public static class ClasesDocenteHolder extends RecyclerView.ViewHolder{
        TextView FECHA,CURSO,SECCION;
        public ClasesDocenteHolder(@NonNull View itemView) {
            super(itemView);
            FECHA = itemView.findViewById(R.id.docente_fecha);
            CURSO = itemView.findViewById(R.id.docente_curso);
            SECCION = itemView.findViewById(R.id.docente_seccion);
        }
    }
    private ClasesDocenteAdaptador.OnItemClickListener mClickListener;
    public void setOnItemClickListener(ClasesDocenteAdaptador.OnItemClickListener listener){
        mClickListener = listener;
    }
}

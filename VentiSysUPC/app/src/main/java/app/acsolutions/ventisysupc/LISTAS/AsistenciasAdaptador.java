package app.acsolutions.ventisysupc.LISTAS;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.acsolutions.ventisysupc.R;

public class AsistenciasAdaptador extends RecyclerView.Adapter<AsistenciasAdaptador.AsistenciasHolder> {
    public List<Asistencias> asistencias;
    public AsistenciasAdaptador(List<Asistencias> asistencias) {
        this.asistencias = asistencias;
    }
    @NonNull
    @Override
    public AsistenciasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_asistencias,parent,false);
        AsistenciasAdaptador.AsistenciasHolder holder = new AsistenciasAdaptador.AsistenciasHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull AsistenciasHolder holder, int position) {
        Asistencias asistencia = asistencias.get(position);
        holder.NOMBRE.setText(asistencia.getNOMBRE());
        holder.INGRESO.setText(asistencia.getINGRESO());
        holder.SALIDA.setText(asistencia.getSALIDA());
        holder.ESTADO.setText(asistencia.getESTADO());
        try{
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            mStorageRef.child("IMAGENES/"+asistencia.getFOTO()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).error(R.drawable.logito).placeholder(R.drawable.cargando).into(holder.FOTO);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    holder.FOTO.setBackgroundResource(R.drawable.logito);
                }
            });
        }
        catch (Exception e){
            Log.e("errorddd:",e.toString());
        }
    }
    @Override
    public int getItemCount() {
        return asistencias.size();
    }
    public static class AsistenciasHolder extends RecyclerView.ViewHolder{
        TextView NOMBRE,INGRESO,SALIDA,ESTADO;
        ImageView FOTO;
        public AsistenciasHolder(@NonNull View itemView) {
            super(itemView);
            NOMBRE = itemView.findViewById(R.id.asistencias_nombre);
            INGRESO = itemView.findViewById(R.id.asistencia_ingreso);
            SALIDA = itemView.findViewById(R.id.asistencia_salida);
            FOTO = itemView.findViewById(R.id.asistencias_foto);
            ESTADO = itemView.findViewById(R.id.asistencia_estado);
        }
    }
}

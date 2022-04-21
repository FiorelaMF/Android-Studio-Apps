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

public class DetalleAsistenciaDocenteAdaptador extends RecyclerView.Adapter<DetalleAsistenciaDocenteAdaptador.DetalleAsistenciaDocenteHolder>{
    public List<DetalleAsistenciaDocente> alumnos;
    public DetalleAsistenciaDocenteAdaptador(List<DetalleAsistenciaDocente> alumnos) {
        this.alumnos = alumnos;
    }

    @NonNull
    @Override
    public DetalleAsistenciaDocenteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_detalle_alumno,parent,false);
        DetalleAsistenciaDocenteAdaptador.DetalleAsistenciaDocenteHolder holder = new DetalleAsistenciaDocenteAdaptador.DetalleAsistenciaDocenteHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleAsistenciaDocenteHolder holder, int position) {

        DetalleAsistenciaDocente alumno = alumnos.get(position);
        holder.CODIGO.setText(alumno.getCODIGO());
        holder.NOMBRE.setText(alumno.getNOMBRE());
        holder.INGRESO.setText(alumno.getINGRESO());
        holder.FECHA.setText(alumno.getFECHA());
        holder.SALIDA.setText(alumno.getSALIDA());
        holder.ESTADO.setText(alumno.getESTADO());
        holder.TEMPE.setText(alumno.getTEMPE());
        try{
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            mStorageRef.child("IMAGENES/"+alumno.getFOTO()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        return alumnos.size();
    }
    public static class DetalleAsistenciaDocenteHolder extends RecyclerView.ViewHolder{
        TextView CODIGO,NOMBRE,INGRESO,FECHA,SALIDA,ESTADO,TEMPE;
        ImageView FOTO;
        public DetalleAsistenciaDocenteHolder(@NonNull View itemView) {
            super(itemView);
            CODIGO = itemView.findViewById(R.id.detalle_codigo);
            NOMBRE = itemView.findViewById(R.id.detalle_nombre);
            INGRESO = itemView.findViewById(R.id.detalle_ingreso);
            FECHA = itemView.findViewById(R.id.detalle_fecha);
            SALIDA = itemView.findViewById(R.id.detalle_salida);
            ESTADO = itemView.findViewById(R.id.detalle_estado);
            TEMPE = itemView.findViewById(R.id.detalle_tempe);
            FOTO = itemView.findViewById(R.id.detalle_foto);
        }
    }


}

package app.acsolutions.ventisysupc.LISTAS;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import app.acsolutions.ventisysupc.R;

public class FiebreAdaptador extends RecyclerView.Adapter<FiebreAdaptador.FiebreHolder>{
    public List<Fiebre> casos;
    public FiebreAdaptador(List<Fiebre> casos) {
        this.casos = casos;
    }
    @NonNull
    @Override
    public FiebreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_fiebre,parent,false);
        FiebreHolder holder = new FiebreHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull FiebreHolder holder, int position) {
        Fiebre caso = casos.get(position);
        holder.CURSO.setText(caso.getCURSO());
        holder.FECHA.setText(caso.getFECHA());
        holder.ALUMNO.setText(caso.getALUMNO());
        holder.CORREO.setText(caso.getCORREO());
        holder.TEMPERATURA.setText(caso.getTEMPE());
        try{
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            mStorageRef.child("IMAGENES/"+caso.getFOTO()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        return casos.size();
    }
    public static class FiebreHolder extends RecyclerView.ViewHolder{
        TextView CURSO,FECHA,ALUMNO,CORREO,TEMPERATURA;
        ImageView FOTO;
        public FiebreHolder(@NonNull View itemView) {
            super(itemView);
            CURSO = itemView.findViewById(R.id.fiebre_curso);
            FECHA = itemView.findViewById(R.id.fiebre_fecha);
            ALUMNO = itemView.findViewById(R.id.fiebre_alumno);
            CORREO = itemView.findViewById(R.id.fiebre_correo);
            TEMPERATURA = itemView.findViewById(R.id.fiebre_temperatura);
            FOTO = itemView.findViewById(R.id.fiebre_foto);
        }
    }
}

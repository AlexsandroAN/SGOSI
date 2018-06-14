package br.com.dae.sgosi.adapter;


import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;
import br.com.dae.sgosi.R;
import br.com.dae.sgosi.entidade.Anexo;

public class AdapterAnexo extends RecyclerView.Adapter<AdapterAnexo.MyViewHolder> {
    private List<Anexo> listaAnexo;

    public AdapterAnexo(List<Anexo> lista) {
        this.listaAnexo = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_anexo, parent, false);

        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Anexo anexo = listaAnexo.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.idAnexo.setText("Nr. :" + anexo.getId());
        holder.dtAnexo.setText("Data: " + dateFormat.format(anexo.getData()));

        Drawable image = Drawable.createFromPath(anexo.getUriFoto());
        holder.imagemAnexo.setImageDrawable(image);
    }

    @Override
    public int getItemCount() {
        return listaAnexo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idAnexo, dtAnexo;
        ImageView imagemAnexo;

        public MyViewHolder(View itemView) {
            super(itemView);
            idAnexo = itemView.findViewById(R.id.textIdAnexo);
            dtAnexo = itemView.findViewById(R.id.textDtAnexo);
            imagemAnexo = (ImageView) itemView.findViewById(R.id.imageViewAnexo);
        }
    }
}

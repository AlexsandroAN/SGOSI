package br.com.dae.sgosi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;

public class AdapterOrdemServico extends RecyclerView.Adapter<AdapterOrdemServico.MyViewHolder>{
    private List<OrdemServico> listaOrdemServico;

    public AdapterOrdemServico(List<OrdemServico> lista) {
        this.listaOrdemServico = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        OrdemServico os = listaOrdemServico.get( position );
        holder.titulo.setText( os.getCliente().getNome());
        holder.genero.setText( os.getDescricaoInicio() );
        holder.ano.setText( os.getDescricaoFim() );

    }

    @Override
    public int getItemCount() {
        return listaOrdemServico.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView ano;
        TextView genero;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            ano = itemView.findViewById(R.id.textAno);
            genero = itemView.findViewById(R.id.textGenero);

        }
    }

}

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

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.MyViewHolder>{
    private List<Cliente> listaCliente;

    public AdapterCliente(List<Cliente> lista) {
        this.listaCliente = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cliente, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cliente cliente = listaCliente.get( position );
        holder.nome.setText( cliente.getNome());
        holder.telefone.setText( cliente.getTelefone());
    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView telefone;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNomeCliente);
            telefone = itemView.findViewById(R.id.textTelefoneCliente);
        }
    }
}

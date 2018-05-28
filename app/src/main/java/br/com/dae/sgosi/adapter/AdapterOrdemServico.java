package br.com.dae.sgosi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.entidade.OrdemServico;

public class AdapterOrdemServico extends RecyclerView.Adapter<AdapterOrdemServico.MyViewHolder> {
    private List<OrdemServico> listaOrdemServico;

    public AdapterOrdemServico(List<OrdemServico> lista) {
        this.listaOrdemServico = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ordem_servico, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrdemServico os = listaOrdemServico.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.nrOs.setText("Nr OS: " + os.getId());
        holder.clienteNome.setText("Cliente: " + os.getCliente().getNome());
        holder.tipoServico.setText("Tipo: " + os.getTipoServico().getNome());
        holder.status.setText("Status: " +os.getStatus().getDescrisao());
        holder.dataInicio.setText(dateFormat.format(os.getDataInicio()));
        holder.descInicio.setText(os.getDescricaoInicio());
        holder.dataFim.setText(dateFormat.format(os.getDataFim()));
        holder.descFim.setText(os.getDescricaoFim());
    }

    @Override
    public int getItemCount() {
        return listaOrdemServico.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nrOs;
        TextView clienteNome;
        TextView tipoServico;
        TextView status;
        TextView dataInicio;
        TextView descInicio;
        TextView dataFim;
        TextView descFim;

        public MyViewHolder(View itemView) {
            super(itemView);
            nrOs = itemView.findViewById(R.id.textNrOS);
            clienteNome = itemView.findViewById(R.id.textClienteNome);
            tipoServico = itemView.findViewById(R.id.textTipoServico);
            status = itemView.findViewById(R.id.textStatus);
            dataInicio = itemView.findViewById(R.id.textDataInicio);
            descInicio = itemView.findViewById(R.id.textDescInicio);
            dataFim = itemView.findViewById(R.id.textDataFim);
            descFim = itemView.findViewById(R.id.textDescFim);
        }
    }

}

package br.com.dae.sgosi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.entidade.OrdemServico;

public class AdapterOrdemServicoPorStatus extends RecyclerView.Adapter<AdapterOrdemServicoPorStatus.MyViewHolder> {
    private List<OrdemServico> listaOrdemServico;

    public AdapterOrdemServicoPorStatus(List<OrdemServico> lista) {
        this.listaOrdemServico = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ordem_servico_por_status, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrdemServico os = listaOrdemServico.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.nrOs.setText("Nr OS: " + os.getId());
        holder.clienteNome.setText("Cliente: " + os.getCliente().getNome());
        holder.tipoServico.setText("Tipo: " + os.getTipoServico().getNome());
        //holder.status.setText("Status: " + os.getStatus().getDescrisao());
        holder.dataInicio.setText("Data Início: " + dateFormat.format(os.getDataInicio()));
        holder.dataFim.setText("Data Fim: " + dateFormat.format(os.getDataFim()));
    }

    @Override
    public int getItemCount() {
        return listaOrdemServico.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nrOs;
        TextView clienteNome;
        TextView tipoServico;
        //TextView status;
        TextView dataInicio;
        TextView dataFim;

        public MyViewHolder(View itemView) {
            super(itemView);
            nrOs = itemView.findViewById(R.id.textNrOS);
            clienteNome = itemView.findViewById(R.id.textClienteNome);
            tipoServico = itemView.findViewById(R.id.textTipoServico);
            //status = itemView.findViewById(R.id.textStatus);
            dataInicio = itemView.findViewById(R.id.textDataInicio);
            dataFim = itemView.findViewById(R.id.textDataFim);
        }
    }

}

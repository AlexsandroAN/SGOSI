package br.com.dae.sgosi.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.activity.CadastroClienteActivity;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdemServicoFragment extends Fragment {

    private EditText edtNome, edtDescricao;
    private ListView listaViewOrdemServico;
    private List<OrdemServico> listaOrdemServico;
    private OrdemServicoDAO ordemServicoDAO;
    private ArrayList<OrdemServico> listViewOrdemServico;
    private OrdemServico ordemServico;
    private ArrayAdapter adapter;
    private int posicaoSelecionada;
    private View view;
    private Context context;


    public OrdemServicoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ordem_servico, container, false);
        ordemServicoDAO = new OrdemServicoDAO(getContext());

        //retirar
        //listaViewOrdemServico = (ListView) view.findViewById(R.id.listViewOrdemServico);

        // testeSalvarOrdemServico();

        //retirar
        //carregarOrdemServico();

        //retirar
//        listaViewOrdemServico.setOnItemClickListener(clickListenerOrdemServio);
//        listaViewOrdemServico.setOnCreateContextMenuListener(contextMenuListener);
//        listaViewOrdemServico.setOnItemLongClickListener(longClickListener);

        // Chamar tela de cadastro Tipo de Serviço
        //retirar
      /*  FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addOrdemServico);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testeSalvarOrdemServico();
                Snackbar.make(view, "Ordem de serviço nº " + ordemServico.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        return view;
    }

    public void testeSalvarOrdemServico() {
        //  ordemServicoDAO = new OrdemServicoDAO(getContext());

        // Cliente cliente = clienteDAO.consultarClientePorId(listaCliente.get(position).getId());

        ClienteDAO clienteDAO = new ClienteDAO(getContext());
        Cliente cliente = clienteDAO.consultarClientePorId(10);

        TipoServicoDAO tipoServicoDAO = new TipoServicoDAO(getContext());
        TipoServico tipoServico = tipoServicoDAO.consultarTipoServicoPorId(0);

//        tipoServico.setNome("nome");
//        tipoServico.setDescricao("descricao");
//        tipoServicoDAO.salvarTipoServico(tipoServico);
        ///tipoServico = tipoServicoDAO.consultarTipoServicoPorId(1);

        // salavra uma ordem de servico
        ordemServico = new OrdemServico();
        ordemServico.setCliente(cliente.getId());
        ordemServico.setTipoServico(tipoServico.getId());
        ordemServico.setStatus(StatusOrdemServico.FAZER_ORCAMENTO.ordinal());
        ordemServico.setDataInicio(new Date());
        ordemServico.setDataFim(new Date());
        ordemServico.setDescricaoInicio("descricao inicio");
        ordemServico.setDescricaoFim("descricao fim");

        // ordemServicoDAO = new OrdemServicoDAO(getContext());
        ordemServicoDAO.salvarOrdemServico(ordemServico);
        ordemServicoDAO.close();
        // ordemServico.toString();
    }

    public void carregarOrdemServico() {
        listViewOrdemServico = ordemServicoDAO.getLista();
        ordemServicoDAO.close();

        if (listViewOrdemServico != null) {
            adapter = new ArrayAdapter<OrdemServico>(getContext(), android.R.layout.simple_list_item_1, listViewOrdemServico);
            listaViewOrdemServico.setAdapter(adapter);
        }
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
        setArrayAdapterOrdemServico();
    }

    private void setArrayAdapterOrdemServico() {
        listaOrdemServico = ordemServicoDAO.getLista();
        List<String> valores = new ArrayList<String>();
        for (OrdemServico os : listaOrdemServico) {
            valores.add(os.getDescricaoInicio());
        }
        adapter.clear();
        adapter.addAll(valores);
        listaViewOrdemServico.setAdapter(adapter);
    }

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            posicaoSelecionada = position;
            return false;
        }
    };

    private AdapterView.OnItemClickListener clickListenerOrdemServio = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            OrdemServico ordemServico = ordemServicoDAO.consultarOrdemServicoPorId(listaOrdemServico.get(position).getId());
            StringBuilder info = new StringBuilder();
            info.append("Id: " + ordemServico.getId());
            info.append("\nCliente: " + ordemServico.getCliente());
            info.append("\nTipo de Serviço: " + ordemServico.getTipoServico());
            info.append("\nStatus: " + ordemServico.getStatus());
            info.append("\nData Inicio: " + ordemServico.getDataInicio());
            info.append("\nData Fim: " + ordemServico.getDataFim());
            info.append("\nDescrição Inicio: " + ordemServico.getDescricaoInicio());
            info.append("\nDescrição Fim: " + ordemServico.getDescricaoFim());
            Util.showMsgAlertOK(getActivity(), "Info", info.toString(), TipoMsg.INFO);
        }
    };

    private View.OnCreateContextMenuListener contextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opções").setHeaderIcon(R.drawable.edit);
            menu.add(1, 10, 1, "Editar");
            menu.add(1, 20, 2, "Deletar");
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 10:
                OrdemServico ordemServico = ordemServicoDAO.consultarOrdemServicoPorId(listaOrdemServico.get(posicaoSelecionada).getId());
                Intent i = new Intent(getContext(), CadastroClienteActivity.class);
                i.putExtra("cliente-escolhido", ordemServico);
                startActivity(i);
                break;
            case 20:
                Util.showMsgConfirm(getActivity(), "Remover Cliente", "Deseja remover realmente o(a) " + listaOrdemServico.get(posicaoSelecionada).getId() + "?",
                        TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ordemServicoDAO.deletarOrdemServico(listaOrdemServico.get(posicaoSelecionada));
                                setArrayAdapterOrdemServico();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Cliente deletado com sucesso!", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
        }
        return true;
    }
}

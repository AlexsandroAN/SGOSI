package br.com.dae.sgosi.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.activity.CadastroClienteActivity;
import br.com.dae.sgosi.activity.CadastroOrdemServicoActivity;
import br.com.dae.sgosi.adapter.AdapterOrdemServico;
import br.com.dae.sgosi.adapter.RecyclerItemClickListener;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;

public class OrdemServicoFragment extends Fragment {

    private ListView listaViewOrdemServico;
    private List<OrdemServico> listaOrdemServico;
    private OrdemServicoDAO ordemServicoDAO;
    private ArrayList<OrdemServico> listViewOrdemServico;
    private OrdemServico ordemServico;
    private ArrayAdapter adapter;
    private int posicaoSelecionada;
    private View view;
    private Context context;
    private Cliente cliente;
    private TipoServico tipoServico;
    private ClienteDAO clienteDAO;
    private TipoServicoDAO tipoServicoDAO;
    private RecyclerView recyclerView;
    private AdapterOrdemServico adapterOrdemServico;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public OrdemServicoFragment() {
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
        view = inflater.inflate(R.layout.fragment_main_adapter, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        carregarOrdemServico();

        //Configurar Recycleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration( new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        recyclerView.setOnCreateContextMenuListener(contextMenuListener);

        //evento de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                        getContext(), recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                OrdemServico ordemServico = listViewOrdemServico.get(position);
                                StringBuilder info = new StringBuilder();
                                info.append("Cliente: " + ordemServico.getCliente().getNome());
                                info.append("\nTipo de Serviço: " + ordemServico.getTipoServico().getNome());
                                info.append("\nStatus: " + ordemServico.getStatus().getDescrisao());
                                info.append("\nData Inicio: " +  dateFormat.format(ordemServico.getDataInicio()));
                                info.append("\nData Fim: " + dateFormat.format(ordemServico.getDataFim()));
                                info.append("\nDescrição Inicio: " + ordemServico.getDescricaoInicio());
                                info.append("\nDescrição Fim: " + ordemServico.getDescricaoFim());
                                Util.showMsgAlertOK(getActivity(), "Info", info.toString(), TipoMsg.INFO);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                ordemServicoDAO = new OrdemServicoDAO(getContext());
                                OrdemServico ordemServico = listViewOrdemServico.get(position);
                                Intent i = new Intent(getContext(), CadastroOrdemServicoActivity.class);
                                i.putExtra("ordem_servico-escolhido", ordemServico);
                                startActivity(i);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.novo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CadastroOrdemServicoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }



    public void carregarOrdemServico() {
        ordemServicoDAO = new OrdemServicoDAO(getContext());
        listViewOrdemServico = ordemServicoDAO.getLista();
        ordemServicoDAO.close();

        if (listViewOrdemServico != null) {
            //adapter = new ArrayAdapter<OrdemServico>(getContext(), android.R.layout.simple_list_item_1, listViewOrdemServico);
            //listaViewOrdemServico.setAdapter(adapter);
            //Configurar adpater
            adapterOrdemServico = new AdapterOrdemServico( listViewOrdemServico );
            recyclerView.setAdapter( adapterOrdemServico );
       }
    }

    private void setArrayAdapterOrdemServico() {
        listaOrdemServico = ordemServicoDAO.getLista();

        listaOrdemServico.size();
        List<String> valores = new ArrayList<String>();
        for (OrdemServico os : listaOrdemServico) {
            valores.add(os.toString());
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
            info.append("Cliente: " + ordemServico.getCliente());
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

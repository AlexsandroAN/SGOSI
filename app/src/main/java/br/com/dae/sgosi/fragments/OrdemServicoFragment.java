package br.com.dae.sgosi.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    private OrdemServicoDAO ordemServicoDAO;
    private ArrayList<OrdemServico> listViewOrdemServico;
    private View view;
    private Context context;
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_adapter, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        carregarOrdemServico();

        //Configurar Recycleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

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
                                info.append("\nData Inicio: " + dateFormat.format(ordemServico.getDataInicio()));
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

                tipoServicoDAO = new TipoServicoDAO(getContext());
                List<TipoServico> listaTipoCliente = tipoServicoDAO.getLista();

                clienteDAO = new ClienteDAO(context);
                List<Cliente> listaCliente = clienteDAO.getLista();

                if (listaTipoCliente.isEmpty() || listaCliente.isEmpty()) {

                    if (listaTipoCliente.isEmpty() && !listaCliente.isEmpty()) {
                        Toast.makeText(context, "O App não existe Tipo de Serviço! Cadastre antes.", Toast.LENGTH_LONG).show();
                    } else if (listaCliente.isEmpty() && !listaTipoCliente.isEmpty()) {
                        Toast.makeText(context, "O App não existe Cliente! Cadastre antes.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "O App não existe Tipo de Serviço e nem Cliente! Cadastre antes.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Intent intent = new Intent(getActivity(), CadastroOrdemServicoActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public void carregarOrdemServico() {
        ordemServicoDAO = new OrdemServicoDAO(getContext());
        listViewOrdemServico = ordemServicoDAO.getLista();

        if (listViewOrdemServico != null) {
            adapterOrdemServico = new AdapterOrdemServico(listViewOrdemServico);
            recyclerView.setAdapter(adapterOrdemServico);
        }
    }
}

package br.com.dae.sgosi.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.activity.CadastroTipoServicoActivity;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.TipoServico;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipoServicoFragment extends android.support.v4.app.Fragment {

    private EditText edtNome, edtDescricao;
    private ListView listaViewTipoServico;
    private List<TipoServico> listaTipoServicos;
    private TipoServicoDAO tipoServicoDAO;
    private ArrayList<TipoServico> listViewTipoServico;
    private TipoServico tipoServico;
    private ArrayAdapter adapter;
    private int posicaoSelecionada;
    private View view;
    private TextView textTipoServico;
    private Context context;

    public TipoServicoFragment() {
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
        view = inflater.inflate(R.layout.fragment_tipo_servico, container, false);

        listaViewTipoServico = (ListView) view.findViewById(R.id.listViewTipoServico);
        carregarTipoServico();

        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
        setArrayAdapterTipoServico();

        listaViewTipoServico.setOnItemClickListener(clickListenerTipoServico);
        listaViewTipoServico.setOnCreateContextMenuListener(contextMenuListener);
        listaViewTipoServico.setOnItemLongClickListener(longClickListener);

        // Chamar tela de cadastro Tipo de Serviço
        FloatingActionButton fab =  (FloatingActionButton) view.findViewById(R.id.addTipoServico);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CadastroTipoServicoActivity.class);
                startActivity(intent);
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        return view;
    }

    public void carregarTipoServico() {
        tipoServicoDAO = new TipoServicoDAO(getContext());
        listViewTipoServico = tipoServicoDAO.getLista();
        tipoServicoDAO.close();

        if (listViewTipoServico != null) {
            adapter = new ArrayAdapter<TipoServico>(getContext(), android.R.layout.simple_list_item_1, listViewTipoServico);
            listaViewTipoServico.setAdapter(adapter);
        }
    }

    private void setArrayAdapterTipoServico() {
        listaTipoServicos = tipoServicoDAO.getLista();

        List<String> valores = new ArrayList<String>();
        for (TipoServico ts : listaTipoServicos) {
            valores.add(ts.getNome());
        }
        adapter.clear();
        adapter.addAll(valores);
        listaViewTipoServico.setAdapter(adapter);
    }

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            posicaoSelecionada = position;
            return false;
        }
    };

    private AdapterView.OnItemClickListener clickListenerTipoServico = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TipoServico tipoServico = tipoServicoDAO.consultarTipoServicoPorId(listaTipoServicos.get(position).getId());
            StringBuilder info = new StringBuilder();
            info.append("Nome: " + tipoServico.getNome());
            info.append("\nDescrição: " + tipoServico.getDescricao());
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
                TipoServico tipoServico = tipoServicoDAO.consultarTipoServicoPorId(listaTipoServicos.get(posicaoSelecionada).getId());
                Intent i = new Intent(getContext(), CadastroTipoServicoActivity.class);
                i.putExtra("tipo_servico-escolhido", tipoServico);
                startActivity(i);
                break;
            case 20:
                Util.showMsgConfirm(getActivity(), "Remover Tipo Serviço", "Deseja remover realmente o(a) " + listaTipoServicos.get(posicaoSelecionada).getNome() + "?",
                        TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tipoServicoDAO.deletarTipoServico(listaTipoServicos.get(posicaoSelecionada));
                                setArrayAdapterTipoServico();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Tipo Serviço deletado com sucesso!", Toast.LENGTH_LONG ).show();
                            }
                        });
                break;
        }
        return true;
    }


}

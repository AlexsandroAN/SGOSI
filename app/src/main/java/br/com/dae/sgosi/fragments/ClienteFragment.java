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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.adapter.RecyclerItemClickListener;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.activity.CadastroClienteActivity;
import br.com.dae.sgosi.adapter.AdapterCliente;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.Contatos;
import br.com.dae.sgosi.entidade.EntidadeContatos;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClienteFragment extends Fragment {

    private EditText edtNome, edtDescricao, edtEndereco, edtEmail, edtTelefone;
    private ListView listaViewCliente;
    private ClienteDAO clienteDAO;
    private List<Cliente> listaCliente = new ArrayList<>();
    private List<EntidadeContatos> listaContato;
    private ArrayList<Cliente> listViewCliente;
    private Cliente cliente;
    public ArrayAdapter adapter;
    private int posicaoSelecionada;
    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private AdapterCliente adapterCliente;

    public ClienteFragment() {
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

        carregarCliente();

        //Configurar Recycleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        recyclerView.setOnCreateContextMenuListener(contextMenuListener);

        //evento de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                        getContext(), recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Cliente cliente = listViewCliente.get(position);
                                StringBuilder info = new StringBuilder();
                                info.append("Nome: " + cliente.getNome());
                                info.append("\nDescrição: " + cliente.getDescricao());
                                info.append("\nEndereço: " + cliente.getEndereco());
                                info.append("\nEmail: " + cliente.getEmail());
                                info.append("\nTelefone: " + cliente.getTelefone());
                                Util.showMsgAlertOK(getActivity(), "Info", info.toString(), TipoMsg.INFO);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                clienteDAO = new ClienteDAO(getContext());
                                Cliente cliente = listViewCliente.get(position);
                                Intent i = new Intent(getContext(), CadastroClienteActivity.class);
                                i.putExtra("cliente-escolhido", cliente);
                                startActivity(i);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );



        // Chamar tela de cadastro Cliente
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.novo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pegar todos contatos do dispositivo
                Contatos Contato = new Contatos(getContext());
                // Pegar todos os contatos
                //  listaCliente = Contato.getContatos(listViewCliente);

                listaContato = Contato.getContatos();
                // Atualizar os clientes
                clienteDAO.salvarListaCliente(listaContato);
                carregarCliente();

                Toast.makeText(getContext(), "Inseridos " + listaContato.size() + " Clientes", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    public void carregarCliente() {
        clienteDAO = new ClienteDAO(getContext());
        listViewCliente = clienteDAO.getLista();
        clienteDAO.close();

        if (listViewCliente != null) {
            adapterCliente = new AdapterCliente(listViewCliente);
            recyclerView.setAdapter(adapterCliente);
        }
    }

    public void setArrayAdapterCliente() {
        listaCliente = clienteDAO.getLista();
        List<String> valores = new ArrayList<String>();
        // adapter dos contatos do dispositivo
//        if(listaContato != null){
//            for (EntidadeContatos lista : listaContato) {
//                valores.add(lista.getNome());
//            }
//        }
        for (Cliente c : listaCliente) {
            valores.add(c.getNome());
        }
        adapter.clear();
        adapter.addAll(valores);
        listaViewCliente.setAdapter(adapter);
    }

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            posicaoSelecionada = position;
            return false;
        }
    };

    private AdapterView.OnItemClickListener clickListenerCliente = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cliente cliente = clienteDAO.consultarClientePorId(listaCliente.get(position).getId());
            StringBuilder info = new StringBuilder();
            info.append("Nome: " + cliente.getNome());
            info.append("\nDescrição: " + cliente.getDescricao());
            info.append("\nEndereço: " + cliente.getEndereco());
            info.append("\nEmail: " + cliente.getEmail());
            info.append("\nTelefone: " + cliente.getTelefone());
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
                Cliente cliente = clienteDAO.consultarClientePorId(listaCliente.get(posicaoSelecionada).getId());
                Intent i = new Intent(getContext(), CadastroClienteActivity.class);
                i.putExtra("cliente-escolhido", cliente);
                startActivity(i);
                break;
            case 20:
                Util.showMsgConfirm(getActivity(), "Remover Cliente", "Deseja remover realmente o(a) " + listaCliente.get(posicaoSelecionada).getNome() + "?",
                        TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clienteDAO.deletarCliente(listaCliente.get(posicaoSelecionada));
                                setArrayAdapterCliente();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Cliente deletado com sucesso!", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
        }
        return true;
    }
}

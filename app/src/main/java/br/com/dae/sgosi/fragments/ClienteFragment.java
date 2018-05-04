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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.activity.CadastroTipoServicoActivity;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.Contatos;
import br.com.dae.sgosi.entidade.EntidadeContatos;
import br.com.dae.sgosi.entidade.TipoServico;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClienteFragment extends Fragment {

    private EditText edtNome, edtDescricao, edtEndereco, edtEmail, edtTelefone;
    private ListView listaViewCliente;
    private ClienteDAO clienteDAO;
    private List<Cliente> listaCliente;
    private List<EntidadeContatos> listaContato;
    private ArrayList<Cliente> listViewCliente;
    private Cliente cliente;
    private ArrayAdapter adapter;
    private int posicaoSelecionada;
    private View view;
    private Context context;


    public ClienteFragment() {
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
        view = inflater.inflate(R.layout.fragment_cliente, container, false);

        listaViewCliente = (ListView) view.findViewById(R.id.listViewCliente);

        List ListaContatos = new ArrayList();

        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
        //setArrayAdapterCliente();

        clienteDAO = new ClienteDAO(getContext());


        listaViewCliente.setOnItemClickListener(clickListenerCliente);
        listaViewCliente.setOnCreateContextMenuListener(contextMenuListener);
        listaViewCliente.setOnItemLongClickListener(longClickListener);


        // Chamar tela de cadastro Cliente
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.atualizarClientes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pegar todos contatos do dispositivo
                Contatos Contato = new Contatos(getContext());
                listaCliente = Contato.getContatos();
                listaCliente.size();
                clienteDAO.salvarListaCliente(listaCliente);
                carregarCliente();
                setArrayAdapterCliente();
                Snackbar.make(view, "Atualizando " + listaCliente.size() + " Contatos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }


    public void carregarCliente() {
       // clienteDAO = new ClienteDAO(getContext());
        listViewCliente = clienteDAO.getLista();
        clienteDAO.close();

        if (listViewCliente != null) {
            adapter = new ArrayAdapter<Cliente>(getContext(), android.R.layout.simple_list_item_1, listViewCliente);
            listaViewCliente.setAdapter(adapter);
        }
    }

    private void setArrayAdapterCliente() {
        // adapter dos clientes
        listaCliente = clienteDAO.getLista();
        List<String> valores = new ArrayList<String>();

        // adapter dos contatos do dispositivo
//        if(listaContato != null){
//            for (EntidadeContatos lista : listaContato) {
//                valores.add(lista.getNome());
//            }
//        }

        // adapter dos clientes
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
           // info.append("\nTelefone: " + cliente.getTelefone());
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
                Intent i = new Intent(getContext(), CadastroTipoServicoActivity.class);
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
                                Toast.makeText(getContext(), "Cliente deletado com sucesso!", Toast.LENGTH_LONG ).show();
                            }
                        });
                break;
        }
        return true;
    }

}

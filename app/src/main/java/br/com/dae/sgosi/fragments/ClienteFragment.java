package br.com.dae.sgosi.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.activity.CadastroClienteActivity;
import br.com.dae.sgosi.activity.CadastroTipoServicoActivity;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.dao.TipoServicoDAO;
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
    private TextView textTipoServico;
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
        // carregarCliente();

        List ListaContatos = new ArrayList();

        // pegar todos contatos do dispositivo
        //Contatos Contato = new Contatos(getContext());
        //listaContato = Contato.getContatos();

        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
       //


        // Chamar tela de cadastro Cliente
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.atualizarClientes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pegar todos contatos do dispositivo
                Contatos Contato = new Contatos(getContext());
                listaContato = Contato.getContatos();
                setArrayAdapterCliente();
                Snackbar.make(view, "Atualizando " + listaContato.size() + " Contatos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    public void carregarCliente() {
        clienteDAO = new ClienteDAO(getContext());
        listViewCliente = clienteDAO.getLista();
        clienteDAO.close();

        if (listViewCliente != null) {
            adapter = new ArrayAdapter<Cliente>(getContext(), android.R.layout.simple_list_item_1, listViewCliente);
            listaViewCliente.setAdapter(adapter);
        }
    }

    private void setArrayAdapterCliente() {
        // adapter dos clientes
        //listaCliente = clienteDAO.getLista();
        List<String> valores = new ArrayList<String>();

        // adapter dos contatos do dispositivo
        if(listaContato != null){
            for (EntidadeContatos lista : listaContato) {
                valores.add(lista.getNome());
            }
        }

        // adapter dos clientes
//        for (Cliente c : listaCliente) {
//            valores.add(c.getNome());
//        }
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

}

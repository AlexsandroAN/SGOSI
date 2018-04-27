package br.com.dae.sgosi.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.CadastroTipoServicoActivity;
import br.com.dae.sgosi.PrincipalActivity;
import br.com.dae.sgosi.R;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.TipoServico;

public class ListaTipoServicoFragment extends Fragment {

    private EditText edtNome, edtDescricao;

    private Button btnCadastrarTipoServico;
    private ListView listaViewTipoServico;
    private List<TipoServico> listaTipoServicos;
    private TipoServicoDAO tipoServicoDAO;
    private ArrayList<TipoServico> listViewTipoServico;
    private TipoServico tipoServico;
    private ArrayAdapter adapter;
    private int posicaoSelecionada;
    private RecyclerView rv;
    //private MaterialSearchView searchView;
    private View view;

    Context context;

    public ListaTipoServicoFragment() {
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
        view = inflater.inflate(R.layout.fragment_lista_tipo_servico, container, false);

        tipoServicoDAO = new TipoServicoDAO(getContext());
        listaViewTipoServico = (ListView) view.findViewById(R.id.listViewTipoServico);

        listViewTipoServico = tipoServicoDAO.getLista();

        if (listViewTipoServico != null) {
            adapter = new ArrayAdapter<TipoServico>(getContext(), android.R.layout.simple_list_item_1, listViewTipoServico);
            listaViewTipoServico.setAdapter(adapter);
        }

        btnCadastrarTipoServico = view.findViewById(R.id.btnCadastrarTipoServico);

        //carregarTipoServico();

        btnCadastrarTipoServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CadastroTipoServicoActivity.class);
                startActivity(intent);
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


}

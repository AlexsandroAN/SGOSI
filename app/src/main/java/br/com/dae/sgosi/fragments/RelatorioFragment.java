package br.com.dae.sgosi.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.activity.CadastroTipoServicoActivity;
import br.com.dae.sgosi.activity.ListaClienteActivity;
import br.com.dae.sgosi.activity.ListaStatusActivity;
import br.com.dae.sgosi.activity.ListaTipoServicoActivity;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.entidade.OrdemServico;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelatorioFragment extends android.support.v4.app.Fragment {

    private EditText edtNome, edtDescricao;
    private ListView listaViewRelatorioOpcoes;
    private ArrayAdapter adapter;
    private View view;
    private Context context;
    private List<String> opcoes;

    public RelatorioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_relatorio, container, false);

        listaViewRelatorioOpcoes = (ListView) view.findViewById(R.id.listViewRelatorio);

        opcoes = new ArrayList<String>();
        opcoes.add("Por Cliente");
        opcoes.add("Por Tipo de Serviço");
        opcoes.add("Por Status");

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, opcoes);
        listaViewRelatorioOpcoes.setAdapter(adapter);

        listaViewRelatorioOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO(context);
                List<OrdemServico> listaOrdemServico = ordemServicoDAO.getLista();

                if (listaOrdemServico.isEmpty()) {
                    Toast.makeText(context, "Não contém Ordem de Serviço cadastrada!", Toast.LENGTH_LONG).show();
                } else {

                    if (position == 0) {
                        Intent i = new Intent(getContext(), ListaClienteActivity.class);
                        startActivity(i);
                    }
                    if (position == 1) {
                        Intent i = new Intent(getContext(), ListaTipoServicoActivity.class);
                        startActivity(i);
                    }
                    if (position == 2) {
                        Intent i = new Intent(getContext(), ListaStatusActivity.class);
                        startActivity(i);
                    }
                }

            }
        });

        return view;
    }

}

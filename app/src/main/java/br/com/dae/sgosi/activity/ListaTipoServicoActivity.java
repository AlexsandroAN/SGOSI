package br.com.dae.sgosi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;

public class ListaTipoServicoActivity extends AppCompatActivity {

    private List<TipoServico> listaTipoServico = new ArrayList<>();
    private TipoServicoDAO tipoServicoDAO;
    private ListView listaViewTiposervico;
    private OrdemServicoDAO ordemServicoDAO;
    private List<OrdemServico> listaOrdemservico = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipo_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tipo de Serviço - qtd de OS");

        listaViewTiposervico = (ListView) findViewById(R.id.listaTipoServico);

        tipoServicoDAO = new TipoServicoDAO(this);
        listaTipoServico = tipoServicoDAO.getLista();

        ordemServicoDAO = new OrdemServicoDAO(this);
        listaOrdemservico = ordemServicoDAO.getLista();

        for (int i = 0; i < listaOrdemservico.size(); i++) {
            for (int y = 0; y < listaTipoServico.size(); y++) {
                if (listaTipoServico.get(y).getId() == listaOrdemservico.get(i).getTipoServico().getId()) {
                    listaTipoServico.get(y).setQtdOS(listaTipoServico.get(y).getQtdOS() + 1);
                }
            }
        }

        final List<TipoServico> tipoServicoComOS = new ArrayList<TipoServico>();
        for (int i = 0; i < listaTipoServico.size(); i++) {
            if (listaTipoServico.get(i).getQtdOS() != 0) {
                tipoServicoComOS.add(listaTipoServico.get(i));
            }
        }

        ArrayAdapter<TipoServico> adapter = new ArrayAdapter<TipoServico>(this,
                android.R.layout.simple_list_item_1, tipoServicoComOS);

        listaViewTiposervico.setAdapter(adapter);

        listaViewTiposervico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TipoServico tipoServico = tipoServicoComOS.get(position);

                Intent i = new Intent(getApplicationContext(), ListaTipoServicoOSActivity.class);
                i.putExtra("tipo_servico-escolhido", tipoServico);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                intent = new Intent(ListaTipoServicoActivity.this, PrincipalActivity.class);
                intent.putExtra("tela", "RelatorioFragment");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
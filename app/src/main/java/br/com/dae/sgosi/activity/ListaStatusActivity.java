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
import java.util.Arrays;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;

public class ListaStatusActivity extends AppCompatActivity {

    private List<StatusOrdemServico> listaStatus = Arrays.asList(StatusOrdemServico.values());
    private ListView listaViewStatus;
    private OrdemServicoDAO ordemServicoDAO;
    private List<OrdemServico> listaOrdemservico = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Status - qtd de OS");

        listaViewStatus = (ListView) findViewById(R.id.listaStatus);

        ordemServicoDAO = new OrdemServicoDAO(this);
        listaOrdemservico = ordemServicoDAO.getLista();

        for (int i = 0; i < listaStatus.size(); i++) {
            listaStatus.get(i).setQtdOS(0);
        }

        for (int i = 0; i < listaOrdemservico.size(); i++) {
            for (int y = 0; y < listaStatus.size(); y++) {
                if (listaStatus.get(y).ordinal() == listaOrdemservico.get(i).getStatus().ordinal()) {
                    listaStatus.get(y).setQtdOS(listaStatus.get(y).getQtdOS() + 1);
                }
            }
        }

        final List<StatusOrdemServico> listaStatusComOS = new ArrayList<StatusOrdemServico>();
        for (int i = 0; i < listaStatus.size(); i++) {
            if (listaStatus.get(i).getQtdOS() != 0) {
                listaStatusComOS.add(listaStatus.get(i));
            }
        }

        ArrayAdapter<StatusOrdemServico> adapter = new ArrayAdapter<StatusOrdemServico>(this,
                android.R.layout.simple_list_item_1, listaStatusComOS);
        listaViewStatus.setAdapter(adapter);

        listaViewStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StatusOrdemServico statusOrdemServico = listaStatusComOS.get(position);

                Intent i = new Intent(getApplicationContext(), ListaStatusOSActivity.class);
                i.putExtra("status-escolhido", statusOrdemServico);
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
                intent = new Intent(ListaStatusActivity.this, PrincipalActivity.class);
                intent.putExtra("tela", "RelatorioFragment");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
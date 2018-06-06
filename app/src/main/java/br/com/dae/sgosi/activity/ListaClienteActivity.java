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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;

public class ListaClienteActivity extends AppCompatActivity {

    private List<Cliente> listaCliente = new ArrayList<>();
    private List<OrdemServico> listaOrdemservico = new ArrayList<>();
    private ClienteDAO clienteDAO;
    private OrdemServicoDAO ordemServicoDAO;
    private ListView listaViewCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cliente - qtd de OS");

        listaViewCliente = (ListView) findViewById(R.id.listaCliente);

        clienteDAO = new ClienteDAO(this);
        listaCliente = clienteDAO.getLista();

        ordemServicoDAO = new OrdemServicoDAO(this);
        listaOrdemservico = ordemServicoDAO.getLista();

        for (int i = 0; i < listaOrdemservico.size(); i++) {
            for (int y = 0; y < listaCliente.size(); y++) {
                if (listaCliente.get(y).getId() == listaOrdemservico.get(i).getCliente().getId()) {
                    listaCliente.get(y).setQtdOS(listaCliente.get(y).getQtdOS() + 1);
                }
            }
        }

        final List<Cliente> clientesComOS = new ArrayList<Cliente>();
        for (int i = 0; i < listaCliente.size(); i++) {
            if (listaCliente.get(i).getQtdOS() != 0) {
                clientesComOS.add(listaCliente.get(i));
            }
        }

        final ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this,
                android.R.layout.simple_list_item_1, clientesComOS);

        listaViewCliente.setAdapter(adapter);


        listaViewCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = clientesComOS.get(position);

                Intent i = new Intent(getApplicationContext(), ListaClienteOSActivity.class);
                i.putExtra("cliente-escolhido", cliente);
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
                intent = new Intent(ListaClienteActivity.this, PrincipalActivity.class);
                intent.putExtra("tela", "RelatorioFragment");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package br.com.dae.sgosi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.adapter.AdapterOrdemServicoPorStatus;
import br.com.dae.sgosi.adapter.AdapterOrdemServicoPorTipoServico;
import br.com.dae.sgosi.adapter.RecyclerItemClickListener;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;

public class ListaStatusOSActivity extends AppCompatActivity {

    private StatusOrdemServico stsatusEscolhido;
    private OrdemServicoDAO ordemServicoDAO;
    private ArrayList<OrdemServico> listViewOrdemServico;
    private RecyclerView recyclerView;
    private AdapterOrdemServicoPorStatus adapterOrdemServicoPorStatus;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_adapter_os);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        stsatusEscolhido = (StatusOrdemServico) intent.getSerializableExtra("status-escolhido");
        getSupportActionBar().setTitle(stsatusEscolhido.getDescrisao());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOS);

        carregarOrdemServico();

        //Configurar Recycleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        //evento de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                        getApplicationContext(), recyclerView,
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
                                Util.showMsgAlertOK(ListaStatusOSActivity.this, "Info", info.toString(), TipoMsg.INFO);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            }
                        }
                )
        );

    }

    public void carregarOrdemServico() {
        ordemServicoDAO = new OrdemServicoDAO(getApplicationContext());
        listViewOrdemServico = ordemServicoDAO.consultarOrdemServicoPorStatus(stsatusEscolhido.ordinal());

        if (listViewOrdemServico != null) {
            adapterOrdemServicoPorStatus= new AdapterOrdemServicoPorStatus(listViewOrdemServico);
            recyclerView.setAdapter(adapterOrdemServicoPorStatus);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                intent = new Intent(ListaStatusOSActivity.this, ListaStatusActivity.class);
                intent.putExtra("tela", "RelatorioFragment");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package br.com.dae.sgosi.activity;

import android.content.Context;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;
import br.com.dae.sgosi.fragments.DatePickerFragment;

public class CadastroOrdemServicoActivity extends AppCompatActivity {

    private Date dataInicio, dataFim;
    private Button btnPoliform;
    private OrdemServico editarOrdemServico, ordemServico;
    private OrdemServicoDAO ordemServicoDAO;
    private Spinner spnStatus, spnClientes, spnTipoServco;
    private List<Cliente> listaCliente = new ArrayList<>();
    private List<TipoServico> listaTipoServico;
    private EditText edtDescInicio, edtDescFim, edtDataInicio, edtDataFim;
    private Cliente cliente;
    private TipoServico tipoServico;
    private ClienteDAO clienteDAO;
    private TipoServicoDAO tipoServicoDAO;
    private String nome;
    private int idPosicao;
    private String[] id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ordem_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spnClientes = (Spinner) findViewById(R.id.spnClientes);
        spnTipoServco = (Spinner) findViewById(R.id.spnTipoServico);
        spnStatus = (Spinner) findViewById(R.id.spnStatus);
        edtDataInicio = (EditText) findViewById(R.id.edtDataInicio);
        edtDataFim = (EditText) findViewById(R.id.edtDataFim);
        edtDescInicio = (EditText) findViewById(R.id.edtDescInicio);
        edtDescFim = (EditText) findViewById(R.id.edtDescFim);
        btnPoliform = (Button) findViewById(R.id.btn_Poliform_OrdemServico);

//        edtDataInicio.setText("01/01/2018");
//        edtDataFim.setText("20/02/2018");
//        edtDescInicio.setText("descrição inicio");
//        edtDescFim.setText("descrição fim");

        if (editarOrdemServico != null) {
            getSupportActionBar().setTitle("Editar Ordem Servico");
            ordemServico.setId(editarOrdemServico.getId());
        } else {
            getSupportActionBar().setTitle("Adicionar Ordem de Serviço");
        }

        this.initClientes();
        this.initStatus();
        this.initTipoServico();

        btnPoliform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordemServicoDAO = new OrdemServicoDAO(CadastroOrdemServicoActivity.this);
                ordemServico = montarOrdemServico();
                ordemServicoDAO.salvarOrdemServico(ordemServico);
                ordemServicoDAO.close();

                Toast.makeText(CadastroOrdemServicoActivity.this, "Ordem de servico salvo com sucesso", Toast.LENGTH_LONG).show();

                Intent i = new Intent(CadastroOrdemServicoActivity.this, PrincipalActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public Cliente getCliente(int pos) {
        cliente = new Cliente();
        clienteDAO = new ClienteDAO(CadastroOrdemServicoActivity.this);
        listaCliente = clienteDAO.getLista();

        for (Cliente cliente : listaCliente) {
            if (cliente.getId() == pos) {
                return cliente;
            }
        }
        return null;
    }



    private OrdemServico montarOrdemServico() {
        try {
            ordemServico = new OrdemServico();

            cliente = new Cliente();
            clienteDAO = new ClienteDAO(CadastroOrdemServicoActivity.this);
            cliente = clienteDAO.consultarClientePorId(spnClientes.getSelectedItemPosition()+1);
            ordemServico.setCliente(cliente);

            tipoServico = new TipoServico();
            tipoServicoDAO = new TipoServicoDAO(CadastroOrdemServicoActivity.this);
            tipoServico = tipoServicoDAO.consultarTipoServicoPorId(spnTipoServco.getSelectedItemPosition() + 1);
            ordemServico.setTipoServico(tipoServico);

            StatusOrdemServico status = StatusOrdemServico.getStatusOS((spnStatus.getSelectedItemPosition()));
            ordemServico.setStatus(status);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date dtInicio = dateFormat.parse(edtDataInicio.getText().toString());
            ordemServico.setDataInicio(dtInicio);

            Date dtFim = dateFormat.parse(edtDataFim.getText().toString());
            ordemServico.setDataFim(dtFim);

            ordemServico.setDescricaoInicio(edtDescInicio.getText().toString());
            ordemServico.setDescricaoFim(edtDescFim.getText().toString());

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return ordemServico;
    }

    private void initClientes() {
        ClienteDAO clienteDAO = new ClienteDAO(CadastroOrdemServicoActivity.this);
        listaCliente = clienteDAO.getLista();

        ArrayList<String> clientes = new ArrayList<>();
        for (Cliente cliente : listaCliente) {
            clientes.add(cliente.getNome());
        }
        ArrayAdapter adapter = new ArrayAdapter(CadastroOrdemServicoActivity.this, android.R.layout.simple_spinner_item, clientes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnClientes.setAdapter(adapter);
    }

    private void initTipoServico() {
        TipoServicoDAO tipoServicoDAO = new TipoServicoDAO(CadastroOrdemServicoActivity.this);
        listaTipoServico = tipoServicoDAO.getLista();

        ArrayList<String> tipoServicos = new ArrayList<>();
        for (TipoServico tipoServico : listaTipoServico) {
            tipoServicos.add(tipoServico.getNome());
        }
        ArrayAdapter adapter = new ArrayAdapter(CadastroOrdemServicoActivity.this, android.R.layout.simple_spinner_item, tipoServicos);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnTipoServco.setAdapter(adapter);
    }

    private void initStatus() {
        ArrayList<String> status = new ArrayList<>();
        for (StatusOrdemServico statusOS : StatusOrdemServico.values()) {
            status.add(statusOS.getDescrisao());
        }
        ArrayAdapter adapter = new ArrayAdapter(CadastroOrdemServicoActivity.this, android.R.layout.simple_spinner_item, status);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnStatus.setAdapter(adapter);
    }

    public void setDataInicio(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        Calendar calendar = Calendar.getInstance();

        Bundle bundle = new Bundle();
        bundle.putInt("dia", calendar.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("mes", calendar.get(Calendar.MONTH));
        bundle.putInt("ano", calendar.get(Calendar.YEAR));

        datePickerFragment.setArguments(bundle);

        datePickerFragment.setDateListener(dateIniciolistener);
        datePickerFragment.show(getFragmentManager(), "Data NAsc.");
    }

    private DatePickerDialog.OnDateSetListener dateIniciolistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edtDataInicio.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };

    public void setDataFim(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        Calendar calendar = Calendar.getInstance();

        Bundle bundle = new Bundle();
        bundle.putInt("dia", calendar.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("mes", calendar.get(Calendar.MONTH));
        bundle.putInt("ano", calendar.get(Calendar.YEAR));

        datePickerFragment.setArguments(bundle);

        datePickerFragment.setDateListener(dateFimlistener);
        datePickerFragment.show(getFragmentManager(), "Data NAsc.");
    }

    private DatePickerDialog.OnDateSetListener dateFimlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edtDataFim.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };


    private boolean validarOrdemSecrvio(OrdemServico ordemServico) {
        boolean erro = false;
//        if (ordemServico.getNome() == null || "".equals(ordemServico.getNome())) {
//            erro = true;
//            edtNome.setError("Campo Nome é obrigatório!");
//        }
//        if (ordemServico.getDescricao() == null || "".equals(ordemServico.getDescricao())) {
//            erro = true;
//            edtDescricao.setError("Campo Descrição é obrigatório!");
//        }
        return erro;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent = new Intent(CadastroOrdemServicoActivity.this, PrincipalActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
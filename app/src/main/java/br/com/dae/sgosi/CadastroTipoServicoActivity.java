package br.com.dae.sgosi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.TipoServico;
import br.com.dae.sgosi.fragments.ListaTipoServicoFragment;

public class CadastroTipoServicoActivity extends AppCompatActivity {

    private EditText edtNome, edtDescricao;
    private Button btnSalvar;
    private TipoServico editarTipoServico, tipoServico;
    private TipoServicoDAO tipoServicoDAO;
    // Primeiro Passo
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tipo_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Adicionar Tipo Serviço");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Segundo Passo
        fragmentManager = getSupportFragmentManager();

        tipoServico = new TipoServico();
        tipoServicoDAO = new TipoServicoDAO(CadastroTipoServicoActivity.this);

        Intent intent = getIntent();
        // editarTipoServico = (TipoServico) intent.getSerializableExtra("tipo_servico-escolhido");

        edtNome = (EditText) findViewById(R.id.edtNomeTipoServico);
        edtDescricao = (EditText) findViewById(R.id.edtDescricaoTipoServico);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        btnSalvar.setText("Salvar");


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tipoServico.setNome(edtNome.getText().toString());
                tipoServico.setDescricao(edtDescricao.getText().toString());

                tipoServicoDAO.salvarTipoServico(tipoServico);
                tipoServicoDAO.close();
                Util.showMsgToast(CadastroTipoServicoActivity.this, "Tipo de Serviço salvo com sucesso!");

               // fragmentManager.beginTransaction().replace(R.id.content_fragment, new ListaTipoServicoFragment()).commit();

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private boolean validarTipoServico(TipoServico tipoServico) {
        boolean erro = false;
        if (tipoServico.getNome() == null || "".equals(tipoServico.getNome())) {
            erro = true;
            edtNome.setError("Campo Nome é obrigatório!");
        }
        if (tipoServico.getDescricao() == null || "".equals(tipoServico.getDescricao())) {
            erro = true;
            edtDescricao.setError("Campo Descrição é obrigatório!");
        }
        return erro;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent = new Intent(CadastroTipoServicoActivity.this, PrincipalActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}


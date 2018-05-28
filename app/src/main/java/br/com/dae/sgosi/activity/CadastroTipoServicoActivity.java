package br.com.dae.sgosi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.dao.TipoServicoDAO;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;
import br.com.dae.sgosi.fragments.TipoServicoFragment;

public class CadastroTipoServicoActivity extends AppCompatActivity {

    private EditText edtNome, edtDescricao;
    private Button btnPoliform;
    private TipoServico editarTipoServico, tipoServico;
    private TipoServicoDAO tipoServicoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tipo_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tipoServico = new TipoServico();
        tipoServicoDAO = new TipoServicoDAO(CadastroTipoServicoActivity.this);

        Intent intent = getIntent();
        editarTipoServico = (TipoServico) intent.getSerializableExtra("tipo_servico-escolhido");

        edtNome = (EditText) findViewById(R.id.edtNomeTipoServico);
        edtDescricao = (EditText) findViewById(R.id.edtDescricaoTipoServico);

        btnPoliform = (Button) findViewById(R.id.btn_Poliform_TipoServico);

        if (editarTipoServico != null) {
            getSupportActionBar().setTitle("Editar Tipo Serviço");

            edtNome.setText(editarTipoServico.getNome());
            edtDescricao.setText(editarTipoServico.getDescricao());
            tipoServico.setId(editarTipoServico.getId());

        } else {
            getSupportActionBar().setTitle("Adicionar Tipo Serviço");
        }

        btnPoliform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tipoServico.setNome(edtNome.getText().toString());
                tipoServico.setDescricao(edtDescricao.getText().toString());

                if (!validarTipoServico(tipoServico)) {
                    if (getSupportActionBar().getTitle().equals("Adicionar Tipo Serviço")) {
                        tipoServicoDAO.salvarTipoServico(tipoServico);
                        tipoServicoDAO.close();
                        Toast.makeText(CadastroTipoServicoActivity.this, "Tipo de Serviço salvo com sucesso", Toast.LENGTH_LONG ).show();
                    } else {
                        tipoServicoDAO.alterarTipoServico(tipoServico);
                        tipoServicoDAO.close();
                        Toast.makeText(CadastroTipoServicoActivity.this, "Tipo de Serviço editado com sucesso!", Toast.LENGTH_LONG ).show();
                    }
                    Intent i = new Intent(CadastroTipoServicoActivity.this, PrincipalActivity.class);
                    i.putExtra("tela", "cadastroTipoServicoActivity");
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private boolean validarTipoServico(TipoServico tipoServico) {
        boolean erro = false;
        if (tipoServico.getNome() == null || "".equals(tipoServico.getNome())) {
            erro = true;
            edtNome.setError("Campo Nome é obrigatório!");
        }
        return erro;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent = new Intent(CadastroTipoServicoActivity.this, PrincipalActivity.class);
                intent.putExtra("tela", "cadastroTipoServicoActivity");
                startActivity(intent);
                break;
        }
        return true;
    }
}


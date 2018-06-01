package br.com.dae.sgosi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.dao.OrdemServicoDAO;
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

        getSupportActionBar().setTitle("Tipo de Serviço");

        if (editarTipoServico != null) {
            tipoServico.setId(editarTipoServico.getId());

            edtNome.setText(editarTipoServico.getNome());
            edtDescricao.setText(editarTipoServico.getDescricao());
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        MenuItem item = menu.findItem(R.id.menu_remove);

        if (editarTipoServico != null) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            // disabled
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                intent = new Intent(CadastroTipoServicoActivity.this, PrincipalActivity.class);
                intent.putExtra("tela", "cadastroTipoServicoActivity");
                startActivity(intent);
                return true;
            case R.id.menu_remove:
                if(editarTipoServico != null){

                    boolean excluirTipoServico = true;
                    OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO(CadastroTipoServicoActivity.this);
                    List<OrdemServico> lista;
                    lista = ordemServicoDAO.getLista();

                    for(OrdemServico os: lista){
                        if(os.getTipoServico().getId() == editarTipoServico.getId()){
                            excluirTipoServico = false;
                        }
                    }

                    if(excluirTipoServico){
                        tipoServicoDAO.deletarTipoServico(editarTipoServico);
                        Toast.makeText(CadastroTipoServicoActivity.this, "Tipo Serviço deletado com sucesso!", Toast.LENGTH_LONG).show();
                        intent = new Intent(CadastroTipoServicoActivity.this, PrincipalActivity.class);
                        intent.putExtra("tela", "cadastroTipoServicoActivity");
                        startActivity(intent);
                    } else {
                        Toast.makeText(CadastroTipoServicoActivity.this, "Tipo Serviço não pode ser deletado!", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }

            case R.id.menu_salvar:
                tipoServico.setNome(edtNome.getText().toString());
                tipoServico.setDescricao(edtDescricao.getText().toString());

                if (!validarTipoServico(tipoServico)) {
                    if (editarTipoServico == null) {
                        tipoServicoDAO.salvarTipoServico(tipoServico);
                    } else {
                        tipoServicoDAO.alterarTipoServico(tipoServico);
                    }
                    Toast.makeText(CadastroTipoServicoActivity.this, "Tipo de Serviço salvo com sucesso!", Toast.LENGTH_LONG).show();

                    intent = new Intent(CadastroTipoServicoActivity.this, PrincipalActivity.class);
                    intent.putExtra("tela", "cadastroTipoServicoActivity");
                    startActivity(intent);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


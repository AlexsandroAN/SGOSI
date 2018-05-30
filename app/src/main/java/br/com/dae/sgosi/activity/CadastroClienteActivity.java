package br.com.dae.sgosi.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.Util.TipoMsg;
import br.com.dae.sgosi.Util.Util;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.TipoServico;
import br.com.dae.sgosi.fragments.ClienteFragment;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText edtNome, edtDescricao, edtEndereco, edtEmail, edtTelefone;
    private Button btnPoliform;
    private Cliente cliente, editarCliente;
    private ClienteDAO clienteDAO;
    private ClienteFragment clienteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cliente = new Cliente();
        clienteDAO = new ClienteDAO(CadastroClienteActivity.this);

        Intent intent = getIntent();
        editarCliente = (Cliente) intent.getSerializableExtra("cliente-escolhido");

        edtNome = (EditText) findViewById(R.id.edtNomeCliente);
        edtDescricao = (EditText) findViewById(R.id.edtDescricaoCliente);
        edtEndereco = (EditText) findViewById(R.id.edtEnderecoCliente);
        edtEmail = (EditText) findViewById(R.id.edtEmailCliente);
        edtTelefone = (EditText) findViewById(R.id.edtTelefoneCliente);

        getSupportActionBar().setTitle("Cliente");

        if (editarCliente != null) {
            cliente.setId(editarCliente.getId());

            edtNome.setText(editarCliente.getNome());
            edtDescricao.setText(editarCliente.getDescricao());
            edtEndereco.setText(editarCliente.getEndereco());
            edtEmail.setText(editarCliente.getEmail());
            edtTelefone.setText(editarCliente.getTelefone());
        }

//        btnPoliform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                cliente.setNome(edtNome.getText().toString());
//                cliente.setDescricao(edtDescricao.getText().toString());
//                cliente.setEndereco(edtEndereco.getText().toString());
//                cliente.setEmail(edtEmail.getText().toString());
//                cliente.setTelefone(edtTelefone.getText().toString());
//
//                // if (!validarCliente(cliente)) {
//                //   if (getSupportActionBar().getTitle().equals("Adicionar Cliente")) {
//                clienteDAO.salvarCliente(cliente);
//                clienteDAO.close();
//                Toast.makeText(CadastroClienteActivity.this, "Cliente salvo com sucesso", Toast.LENGTH_LONG).show();
//                //  } else {
//                //       clienteDAO.alterarCliente(cliente);
//                //       clienteDAO.close();
//                //       Toast.makeText(CadastroClienteActivity.this, "Cliente editado com sucesso!", Toast.LENGTH_LONG).show();
//                //   }
//                Intent i = new Intent(CadastroClienteActivity.this, MainActivity.class);
//                i.putExtra("tela", "cadastroClienteActivity");
//                startActivity(i);
//                finish();
//                // }
//            }
//        });
    }

    private boolean validarCliente(Cliente cliente) {
        boolean erro = false;
        if (cliente.getNome() == null || "".equals(cliente.getNome())) {
            erro = true;
            edtNome.setError("Campo Nome é obrigatório!");
        }
//        if (cliente.getDescricao() == null || "".equals(cliente.getDescricao())) {
//            erro = true;
//            edtDescricao.setError("Campo Descrição é obrigatório!");
//        }
        return erro;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(CadastroClienteActivity.this, PrincipalActivity.class);
                intent.putExtra("tela", "cadastroClienteActivity");
                startActivity(intent);
                return true;
            case R.id.menu_remove:
                if (editarCliente != null) {
                    clienteDAO.deletarCliente(editarCliente);
                    clienteDAO.close();
                    Toast.makeText(CadastroClienteActivity.this, "Cliente deletado com sucesso!", Toast.LENGTH_LONG).show();
                    intent = new Intent(CadastroClienteActivity.this, PrincipalActivity.class);
                    intent.putExtra("tela", "cadastroClienteActivity");
                    startActivity(intent);
                    return true;
                }
            case R.id.menu_salvar:

                cliente.setNome(edtNome.getText().toString());
                cliente.setDescricao(edtDescricao.getText().toString());
                cliente.setEndereco(edtEndereco.getText().toString());
                cliente.setEmail(edtEmail.getText().toString());
                cliente.setTelefone(edtTelefone.getText().toString());

                if (!validarCliente(cliente)) {

                    if (editarCliente == null) {
                        clienteDAO.salvarCliente(cliente);
                    } else {
                        clienteDAO.alterarCliente(cliente);
                    }
                    clienteDAO.close();
                    Toast.makeText(CadastroClienteActivity.this, "Cliente salvo com sucesso", Toast.LENGTH_LONG).show();

                    intent = new Intent(CadastroClienteActivity.this, PrincipalActivity.class);
                    intent.putExtra("tela", "cadastroClienteActivity");
                    startActivity(intent);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
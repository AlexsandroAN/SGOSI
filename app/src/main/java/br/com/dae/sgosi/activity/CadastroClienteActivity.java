package br.com.dae.sgosi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dae.sgosi.R;
import br.com.dae.sgosi.dao.ClienteDAO;
import br.com.dae.sgosi.entidade.Cliente;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText edtNome, edtDescricao, edtEndereco, edtEmail, edtTelefone;
    private Button btnPoliform;
    private Cliente editarCliente, cliente;
    private ClienteDAO clienteDAO;


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

        btnPoliform = (Button) findViewById(R.id.btn_Poliform_Cliente);

        if (editarCliente != null) {
            getSupportActionBar().setTitle("Editar Cliente");
            cliente.setId(editarCliente.getId());
            edtNome.setText(editarCliente.getNome());
            edtDescricao.setText(editarCliente.getDescricao());
            edtEndereco.setText(editarCliente.getEndereco());
            edtEmail.setText(editarCliente.getEmail());
            edtTelefone.setText(editarCliente.getTelefone());
        } else {
            getSupportActionBar().setTitle("Adicionar Cliente");
        }

        btnPoliform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cliente.setNome(edtNome.getText().toString());
                cliente.setDescricao(edtDescricao.getText().toString());
                cliente.setEndereco(edtEndereco.getText().toString());
                cliente.setEmail(edtEmail.getText().toString());
                cliente.setTelefone(edtTelefone.getText().toString());

                if (!validarCliente(cliente)) {
                    if (getSupportActionBar().getTitle().equals("Adicionar Cliente")) {
                        clienteDAO.salvarCliente(cliente);
                        clienteDAO.close();
                        Toast.makeText(CadastroClienteActivity.this, "Cliente salvo com sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        clienteDAO.alterarCliente(cliente);
                        clienteDAO.close();
                        Toast.makeText(CadastroClienteActivity.this, "Cliente editado com sucesso!", Toast.LENGTH_LONG).show();
                    }

//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frameConteudo, new ClienteFragment());
//                    transaction.commit();

                    Intent i = new Intent(CadastroClienteActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private boolean validarCliente(Cliente cliente) {
        boolean erro = false;
        if (cliente.getNome() == null || "".equals(cliente.getNome())) {
            erro = true;
            edtNome.setError("Campo Nome é obrigatório!");
        }
        if (cliente.getDescricao() == null || "".equals(cliente.getDescricao())) {
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
                Intent intent = new Intent(CadastroClienteActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
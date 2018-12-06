package br.com.dae.sgosi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import br.com.dae.sgosi.Util.Constantes;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.EntidadeContatos;
import br.com.dae.sgosi.helper.DbHelper;

/**
 * Created by 39091 on 23/04/2018.
 */

public class ClienteDAO{

    private SQLiteDatabase escreve;

    private SQLiteDatabase le;


    public ClienteDAO(Context context) {
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }



    // Método para salvar cliente
    public void salvarCliente(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("descricao", cliente.getDescricao());
        values.put("endereco", cliente.getEndereco());
        values.put("email", cliente.getEmail());
        values.put("telefone", cliente.getTelefone());

        escreve.insert("cliente", null, values);
    }

    // Método para salvar uma List de cliente
    public void salvarListaCliente(List<EntidadeContatos> contatos) {
        for (EntidadeContatos contato: contatos){
            ContentValues values = new ContentValues();
            values.put("nome", contato.getNome());
            values.put("descricao", "");
            values.put("endereco", "");
            values.put("email", "");
            values.put("telefone", contato.getTelefones().get(0).toString());

            escreve.insert("cliente", null, values);
        }
    }

    public void alterarCliente(Cliente cliente) {
        ContentValues values = new ContentValues();

        values.put("nome", cliente.getNome());
        values.put("descricao", cliente.getDescricao());
        values.put("endereco", cliente.getEndereco());
        values.put("email", cliente.getEmail());
        values.put("telefone", cliente.getTelefone());

        escreve.update("cliente", values, "id = ?", new String[]{String.valueOf(cliente.getId())});
    }

    public void deletarCliente(Cliente cliente) {
       escreve.delete("cliente", "id = ?", new String[]{String.valueOf(cliente.getId())});
    }

    public void close() {
        escreve.close();
        le.close();
    }


    // Método para listar todos os clientes
    public ArrayList<Cliente> getLista() {
        Cursor cursor = le.query("cliente", null, null, null, null, null, null);

        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();

        while (cursor.moveToNext()) {
            Cliente cliente = new Cliente();
            setPessoaFromCursor(cursor, cliente);
            listaClientes.add(cliente);
        }

        return listaClientes;
    }

    private void setPessoaFromCursor(Cursor cursor, Cliente cliente) {
        cliente.setId(cursor.getInt(cursor.getColumnIndex("id")));
        cliente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        cliente.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        cliente.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
        cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        cliente.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
    }

    public Cliente consultarClientePorId(int idCliente) {
        Cliente cliente = new Cliente();

        Cursor cursor = le.query("cliente", null, "ID = ?", new String[]{String.valueOf(idCliente)}, null, null, "nome");

        if (cursor.moveToNext()) {
            setPessoaFromCursor(cursor, cliente);
        }

        return cliente;
    }
}

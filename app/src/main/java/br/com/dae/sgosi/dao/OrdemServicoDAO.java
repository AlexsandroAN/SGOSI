package br.com.dae.sgosi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.dae.sgosi.Util.Constantes;
import br.com.dae.sgosi.activity.CadastroOrdemServicoActivity;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;

/**
 * Created by 39091 on 23/04/2018.
 */

public class OrdemServicoDAO extends SQLiteOpenHelper {

    private Context context;

    public OrdemServicoDAO(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tipoServicoSQL = "CREATE TABLE tipo_servico (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT(50) NOT NULL," +
                " descricao TEXT(100));";

        String clienteSQL = "CREATE TABLE cliente (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT(50) NOT NULL," +
                " descricao TEXT(100)," +
                " endereco TEXT(100)," +
                " email TEXT(20)," +
                " telefone TEXT(20));";

        String ordemServicoSQL = "CREATE TABLE ordem_servico (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " cliente  INTEGER," +
                " tipoServico INTEGER," +
                " status INTEGER," +
                " dataInicio INTEGER," +
                " dataFim INTEGER," +
                " descricaoInicio TEXT," +
                " descricaoFim TEXT);";

        db.execSQL(tipoServicoSQL);
        db.execSQL(clienteSQL);
        db.execSQL(ordemServicoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String tipo_servico = "DROP TABLE IF EXISTS tipo_servico";
        String cliente = "DROP TABLE IF EXISTS cliente";
        String ordem_servico = "DROP TABLE IF EXISTS ordem_servico";
        db.execSQL(tipo_servico);
        db.execSQL(cliente);
        db.execSQL(ordem_servico);
    }

    public void salvarOrdemServico(OrdemServico ordemServico) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cliente", ordemServico.getCliente().getId());
        values.put("tipoServico", ordemServico.getTipoServico().getId());
        values.put("status", ordemServico.getStatus().ordinal());
        values.put("dataInicio", ordemServico.getDataInicio().getTime());
        values.put("dataFim", ordemServico.getDataFim().getTime());
        values.put("descricaoInicio", ordemServico.getDescricaoInicio());
        values.put("descricaoFim", ordemServico.getDescricaoFim());

        db.insert("ordem_servico", null, values);
    }

    public void alterarOrdemServico(OrdemServico ordemServico) {
        ContentValues values = new ContentValues();
        values.put("cliente", ordemServico.getCliente().getId());
        values.put("tipoServico", ordemServico.getTipoServico().getId());
        values.put("status", ordemServico.getStatus().ordinal());
        values.put("dataInicio", ordemServico.getDataInicio().getTime());
        values.put("dataFim", ordemServico.getDataFim().getTime());
        values.put("descricaoInicio", ordemServico.getDescricaoInicio());
        values.put("descricaoFim", ordemServico.getDescricaoFim());
        SQLiteDatabase db = this.getWritableDatabase();

        getWritableDatabase().update("ordem_servico", values, "id = ?", new String[]{String.valueOf(ordemServico.getId())});
    }

    public void deletarOrdemServico(OrdemServico ordemServico) {
        getWritableDatabase().delete("ordem_servico", "id = ?", new String[]{String.valueOf(ordemServico.getId())});
    }

    public ArrayList<OrdemServico> getLista() {
        SQLiteDatabase db = this.getReadableDatabase();

      //Cursor cursor = db.rawQuery("select * from ordem_servico inner join cliente", null);
        Cursor cursor = db.query("ordem_servico", null, null, null, null, null, null);

        ArrayList<OrdemServico> listaOrdemServico = new ArrayList<OrdemServico>();

        while (cursor.moveToNext()) {
            OrdemServico ordemServico = new OrdemServico();

            setOrdemServicoFromCursor(cursor, ordemServico);
            listaOrdemServico.add(ordemServico);
        }
        return listaOrdemServico;
    }

    private void setOrdemServicoFromCursor(Cursor cursor, OrdemServico ordemServico) {
        ordemServico.setId(cursor.getInt(cursor.getColumnIndex("id")));

        int idCliente = cursor.getInt(cursor.getColumnIndex("cliente"));
        Cliente cliente;
        cliente = consultarClientePorId(idCliente);
        ordemServico.setCliente(cliente);

        int idTipoServico = cursor.getInt(cursor.getColumnIndex("tipoServico"));
        TipoServico tipoServico;
        tipoServico = consultarTipoServicoPorId(idTipoServico);
        ordemServico.setTipoServico(tipoServico);

        int status = cursor.getInt(cursor.getColumnIndex("status"));
        ordemServico.setStatus(StatusOrdemServico.getStatusOS(status));

        long timeDataInicio = cursor.getLong(cursor.getColumnIndex("dataInicio"));
        Date dtInicio = new Date();
        dtInicio.setTime(timeDataInicio);
        ordemServico.setDataInicio(dtInicio);

        long timeDataFim = cursor.getLong(cursor.getColumnIndex("dataFim"));
        Date dtFim = new Date();
        dtFim.setTime(timeDataFim);
        ordemServico.setDataFim(dtFim);

        ordemServico.setDescricaoInicio(cursor.getString(cursor.getColumnIndex("descricaoInicio")));
        ordemServico.setDescricaoFim(cursor.getString(cursor.getColumnIndex("descricaoFim")));
    }

    public Cliente consultarClientePorId(int idCliente) {
        Cliente cliente = new Cliente();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("cliente", null, "ID = ?", new String[]{String.valueOf(idCliente)}, null, null, "nome");

        if (cursor.moveToNext()) {
            setClienteFromCursor(cursor, cliente);
        }
        return cliente;
    }

    private void setClienteFromCursor(Cursor cursor, Cliente cliente) {
        cliente.setId(cursor.getInt(cursor.getColumnIndex("id")));
        cliente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        cliente.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        cliente.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
        cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        cliente.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
    }

    public TipoServico consultarTipoServicoPorId(int idTipoServico) {
        TipoServico tipoServico = new TipoServico();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tipo_servico", null, "ID = ?", new String[]{String.valueOf(idTipoServico)}, null, null, "nome");

        if (cursor.moveToNext()) {
            setTipoServicoFromCursor(cursor, tipoServico);
        }

        return tipoServico;
    }

    private void setTipoServicoFromCursor(Cursor cursor, TipoServico tipoServico) {
        tipoServico.setId(cursor.getInt(cursor.getColumnIndex("id")));
        tipoServico.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        tipoServico.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
    }

    public OrdemServico consultarOrdemServicoPorId(int idOrdemServico) {
        OrdemServico ordemServico = new OrdemServico();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("ordem_servico", null, "ID = ?", new String[]{String.valueOf(idOrdemServico)}, null, null, null);

        if (cursor.moveToNext()) {
            setOrdemServicoFromCursor(cursor, ordemServico);
        }
        return ordemServico;
    }
}

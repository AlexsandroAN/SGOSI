package br.com.dae.sgosi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import br.com.dae.sgosi.Util.Constantes;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;
import br.com.dae.sgosi.helper.DbHelper;

/**
 * Created by 39091 on 23/04/2018.
 */

public class OrdemServicoDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public OrdemServicoDAO(Context context) {
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }


    public void salvarOrdemServico(OrdemServico ordemServico) {
        ContentValues values = new ContentValues();
        values.put("cliente", ordemServico.getCliente().getId());
        values.put("tipoServico", ordemServico.getTipoServico().getId());
        values.put("status", ordemServico.getStatus().ordinal());
        values.put("dataInicio", ordemServico.getDataInicio().getTime());
        values.put("dataFim", ordemServico.getDataFim().getTime());
        values.put("descricaoInicio", ordemServico.getDescricaoInicio());
        values.put("descricaoFim", ordemServico.getDescricaoFim());

        escreve.insert("ordem_servico", null, values);
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

        escreve.update("ordem_servico", values, "id = ?", new String[]{String.valueOf(ordemServico.getId())});
    }

    public void deletarOrdemServico(OrdemServico ordemServico) {
        escreve.delete("ordem_servico", "id = ?", new String[]{String.valueOf(ordemServico.getId())});
    }

    public ArrayList<OrdemServico> getLista() {
        Cursor cursor = le.query("ordem_servico", null, null, null, null, null, null);

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

        Cursor cursor = le.query("cliente", null, "ID = ?", new String[]{String.valueOf(idCliente)}, null, null, "nome");

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

        Cursor cursor = le.query("tipo_servico", null, "ID = ?", new String[]{String.valueOf(idTipoServico)}, null, null, "nome");

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

        Cursor cursor = le.query("ordem_servico", null, "ID = ?", new String[]{String.valueOf(idOrdemServico)}, null, null, null);

        if (cursor.moveToNext()) {
            setOrdemServicoFromCursor(cursor, ordemServico);
        }
        return ordemServico;
    }

    public  ArrayList<OrdemServico> consultarOrdemServicoPorCliente(int idCliente) {

        Cursor cursor = le.query("ordem_servico", null, "cliente = ?", new String[]{String.valueOf(idCliente)}, null, null, null);

        ArrayList<OrdemServico> listaOrdemServico = new ArrayList<OrdemServico>();

        while (cursor.moveToNext()) {
            OrdemServico ordemServico = new OrdemServico();

            setOrdemServicoFromCursor(cursor, ordemServico);
            listaOrdemServico.add(ordemServico);
        }
        return listaOrdemServico;
    }

    public  ArrayList<OrdemServico> consultarOrdemServicoPorTipoServico(int idTipoServico) {

        Cursor cursor = le.query("ordem_servico", null, "tipoServico = ?", new String[]{String.valueOf(idTipoServico)}, null, null, null);

        ArrayList<OrdemServico> listaOrdemServico = new ArrayList<OrdemServico>();

        while (cursor.moveToNext()) {
            OrdemServico ordemServico = new OrdemServico();

            setOrdemServicoFromCursor(cursor, ordemServico);
            listaOrdemServico.add(ordemServico);
        }
        return listaOrdemServico;
    }

    public  ArrayList<OrdemServico> consultarOrdemServicoPorStatus(int idStatus) {

        Cursor cursor = le.query("ordem_servico", null, "status = ?", new String[]{String.valueOf(idStatus)}, null, null, null);

        ArrayList<OrdemServico> listaOrdemServico = new ArrayList<OrdemServico>();

        while (cursor.moveToNext()) {
            OrdemServico ordemServico = new OrdemServico();

            setOrdemServicoFromCursor(cursor, ordemServico);
            listaOrdemServico.add(ordemServico);
        }
        return listaOrdemServico;
    }
}

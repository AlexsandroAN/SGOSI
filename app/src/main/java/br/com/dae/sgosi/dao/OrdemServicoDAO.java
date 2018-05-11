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

        StringBuilder queryTbOrdemServico = new StringBuilder();
        queryTbOrdemServico.append("CREATE TABLE ordem_servico (");
        queryTbOrdemServico.append(" id INTEGER PRIMARY KEY AUTOINCREMENT,");
        queryTbOrdemServico.append(" cliente  INTERGER,");
        queryTbOrdemServico.append(" tipoServico INTERGER,");
        queryTbOrdemServico.append(" status INTERGER,");
        queryTbOrdemServico.append(" dataInicio INTERGER,");
        queryTbOrdemServico.append(" dataFim INTERGER,");
        queryTbOrdemServico.append(" descricaoInicio TEXT,");
        queryTbOrdemServico.append(" descricaoFim TEXT)");

        db.execSQL(queryTbOrdemServico.toString());

        StringBuilder queryTbTipoServico = new StringBuilder();
        queryTbTipoServico.append("CREATE TABLE tipo_servico (");
        queryTbTipoServico.append(" id INTEGER PRIMARY KEY AUTOINCREMENT,");
        queryTbTipoServico.append(" nome TEXT(50) NOT NULL,");
        queryTbTipoServico.append(" descricao TEXT(100))");

        db.execSQL(queryTbTipoServico.toString());

        StringBuilder queryTbCliente = new StringBuilder();
        queryTbCliente.append("CREATE TABLE cliente (");
        queryTbCliente.append(" id INTEGER PRIMARY KEY AUTOINCREMENT,");
        queryTbCliente.append(" nome TEXT(50) NOT NULL,");
        queryTbCliente.append(" descricao TEXT(100),");
        queryTbCliente.append(" endereco TEXT(100),");
        queryTbCliente.append(" email TEXT(20),");
        queryTbCliente.append(" telefone TEXT(20))");

        db.execSQL(queryTbCliente.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ordem_servico = "DROP TABLE IF EXISTS ordem_servico";
        db.execSQL(ordem_servico);
    }

    public void salvarOrdemServico(OrdemServico ordemServico){
        ContentValues values = new ContentValues();
        values.put("cliente", ordemServico.getCliente());
        values.put("tipoServico", ordemServico.getTipoServico());
        values.put("status", ordemServico.getStatus());
        values.put("dataInicio", ordemServico.getDataInicio().getTime());
        values.put("dataFim", ordemServico.getDataFim().getTime());
        values.put("descricaoInicio", ordemServico.getDescricaoInicio());
        values.put("descricaoFim", ordemServico.getDescricaoFim());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("ordem_servico", null, values);
    }

    public void alterarOrdemServico(OrdemServico ordemServico){
        ContentValues values = new ContentValues();
        values.put("cliente", ordemServico.getCliente());
        values.put("tipoServico", ordemServico.getTipoServico());
        values.put("status", ordemServico.getStatus());
        values.put("dataInicio", ordemServico.getDataInicio().getTime());
        values.put("dataFim", ordemServico.getDataFim().getTime());
        values.put("descricaoInicio", ordemServico.getDescricaoInicio());
        values.put("descricaoFim", ordemServico.getDescricaoFim());
        SQLiteDatabase db = this.getWritableDatabase();

        getWritableDatabase().update("ordem_servico", values, "id = ?", new String[]{String.valueOf(ordemServico.getId())});
    }

    public void deletarOrdemServico(OrdemServico ordemServico){
        getWritableDatabase().delete("ordem_servico", "id = ?", new String[]{String.valueOf(ordemServico.getId())});
    }

    public ArrayList<OrdemServico> getLista() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("ordem_servico", null, null, null, null, null, null);

        ArrayList<OrdemServico> listaOrdemServico = new ArrayList<OrdemServico>();

        while (cursor.moveToNext()) {
            OrdemServico ordemServico  = new OrdemServico();

            setOrdemServicoFromCursor(cursor, ordemServico);
            listaOrdemServico.add(ordemServico);
        }
        return listaOrdemServico;
    }

    private void setOrdemServicoFromCursor(Cursor cursor, OrdemServico ordemServico) {
        ordemServico.setId(cursor.getInt(cursor.getColumnIndex("id")));
        //int idCliente = cursor.getColumnIndex("cliente");
        ordemServico.setCliente(cursor.getColumnIndex("cliente"));
        ordemServico.setTipoServico(cursor.getColumnIndex("tipoServico"));
        ordemServico.setStatus(cursor.getColumnIndex("status"));

        long time = cursor.getLong(cursor.getColumnIndex("dataInicio"));
        Date dtInicio = new Date();
        dtInicio.setTime(time);
        ordemServico.setDataInicio(dtInicio);

        long time1 = cursor.getLong(cursor.getColumnIndex("dataFim"));
        Date dtFim = new Date();
        dtInicio.setTime(time);
        ordemServico.setDataInicio(dtInicio);

        ordemServico.setDescricaoInicio(cursor.getString(cursor.getColumnIndex("descricaoInicio")));
        ordemServico.setDescricaoFim(cursor.getString(cursor.getColumnIndex("descricaoFim")));
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

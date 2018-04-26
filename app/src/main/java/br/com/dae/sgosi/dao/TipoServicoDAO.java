package br.com.dae.sgosi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import br.com.dae.sgosi.Util.Constantes;
import br.com.dae.sgosi.entidade.TipoServico;

/**
 * Created by 39091 on 23/04/2018.
 */

public class TipoServicoDAO extends SQLiteOpenHelper {

    public TipoServicoDAO(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE tipo_servico (");
        query.append(" id INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" nome TEXT(50) NOT NULL,");
        query.append(" descricao TEXT(100))");

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String tipoServico = "DROP TABLE IF EXISTS tipo_servico";
        db.execSQL(tipoServico);
    }

    public void salvarTipoServico(TipoServico tipoServico){
        ContentValues values = new ContentValues();

        values.put("nome", tipoServico.getNome());
        values.put("descricao", tipoServico.getDescricao());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("tipo_servico", null, values);
    }

    public void alterarTipoServico(TipoServico tipoServico){
        ContentValues values = new ContentValues();

        values.put("nome", tipoServico.getNome());
        values.put("descricao", tipoServico.getDescricao());

        getWritableDatabase().update("tipo_servico", values, "id = ?", new String[]{String.valueOf(tipoServico.getId())});
    }

    public void deletarTipoServico(TipoServico tipoServico){
        getWritableDatabase().delete("tipo_servico", "id = ?", new String[]{String.valueOf(tipoServico.getId())});
    }

    public ArrayList<TipoServico> getLista() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tipo_servico", null, null, null, null, null, null);

        ArrayList<TipoServico> listaTipoServico = new ArrayList<TipoServico>();

        while (cursor.moveToNext()) {
            TipoServico tipoServico = new TipoServico();

            setTipoServicoFromCursor(cursor, tipoServico);
            listaTipoServico.add(tipoServico);
        }
        return listaTipoServico;
    }

    private void setTipoServicoFromCursor(Cursor cursor, TipoServico tipoServico) {
        tipoServico.setId(cursor.getInt(cursor.getColumnIndex("id")));
        tipoServico.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        tipoServico.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
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

}

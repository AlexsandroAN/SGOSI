package br.com.dae.sgosi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import br.com.dae.sgosi.Util.Constantes;
import br.com.dae.sgosi.entidade.TipoServico;
import br.com.dae.sgosi.helper.DbHelper;

/**
 * Created by 39091 on 23/04/2018.
 */

public class TipoServicoDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TipoServicoDAO(Context context) {
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    public void salvarTipoServico(TipoServico tipoServico){
        ContentValues values = new ContentValues();
        values.put("nome", tipoServico.getNome());
        values.put("descricao", tipoServico.getDescricao());

        escreve.insert("tipo_servico", null, values);
    }

    public void alterarTipoServico(TipoServico tipoServico){
        ContentValues values = new ContentValues();

        values.put("nome", tipoServico.getNome());
        values.put("descricao", tipoServico.getDescricao());

        escreve.update("tipo_servico", values, "id = ?", new String[]{String.valueOf(tipoServico.getId())});
    }

    public void deletarTipoServico(TipoServico tipoServico){
        escreve.delete("tipo_servico", "id = ?", new String[]{String.valueOf(tipoServico.getId())});
    }

    public ArrayList<TipoServico> getLista() {
        Cursor cursor = escreve.query("tipo_servico", null, null, null, null, null, null);

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

        Cursor cursor = le.query("tipo_servico", null, "ID = ?", new String[]{String.valueOf(idTipoServico)}, null, null, "nome");

        if (cursor.moveToNext()) {
            setTipoServicoFromCursor(cursor, tipoServico);
        }

        return tipoServico;
    }

}

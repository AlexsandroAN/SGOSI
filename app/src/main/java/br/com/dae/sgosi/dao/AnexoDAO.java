package br.com.dae.sgosi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Date;

import br.com.dae.sgosi.entidade.Anexo;
import br.com.dae.sgosi.entidade.Cliente;
import br.com.dae.sgosi.entidade.OrdemServico;
import br.com.dae.sgosi.entidade.StatusOrdemServico;
import br.com.dae.sgosi.entidade.TipoServico;
import br.com.dae.sgosi.helper.DbHelper;

/**
 * Created by 39091 on 23/04/2018.
 */

public class AnexoDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public AnexoDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }


    public void salvarAnexo(Anexo anexo) {
        ContentValues values = new ContentValues();
        values.put("os", anexo.getOs().getId());
        values.put("data", anexo.getData().getTime());
        values.put("uriFoto", anexo.getUriFoto());

        escreve.insert("anexo", null, values);
    }

    public void alterarAnexo(Anexo anexo) {
        ContentValues values = new ContentValues();
        values.put("os", anexo.getOs().getId());
        values.put("data", anexo.getData().getTime());
        values.put("uriFoto", anexo.getUriFoto());

        escreve.update("anexo", values, "id = ?", new String[]{String.valueOf(anexo.getId())});
    }

    public void deletarAnexo(Anexo anexo) {
        escreve.delete("anexo", "id = ?", new String[]{String.valueOf(anexo.getId())});
    }

    public ArrayList<Anexo> getLista() {
        Cursor cursor = le.query("anexo", null, null, null, null, null, null);

        ArrayList<Anexo> listaAnexo = new ArrayList<Anexo>();

        while (cursor.moveToNext()) {
            Anexo anexo = new Anexo();
            setAnexoFromCursor(cursor, anexo);
            listaAnexo.add(anexo);
        }
        return listaAnexo;
    }

    public ArrayList<Anexo> consultarAnexoPorOrdemServico(int idOrdemServico) {
        Cursor cursor = le.query("anexo", null, "os = ?", new String[]{String.valueOf(idOrdemServico)}, null, null, null);

        ArrayList<Anexo> listaAnexo = new ArrayList<Anexo>();

        while (cursor.moveToNext()) {
            Anexo anexo  = new Anexo();

            setAnexoFromCursor(cursor, anexo);
            listaAnexo.add(anexo);
        }
        return listaAnexo;
    }


    private void setAnexoFromCursor(Cursor cursor, Anexo anexo) {
        anexo.setId(cursor.getInt(cursor.getColumnIndex("id")));

        int idOS = cursor.getInt(cursor.getColumnIndex("os"));
        OrdemServico ordemServico;
        ordemServico = consultarOrdemServicoPorId(idOS);
        anexo.setOs(ordemServico);

        long timeData = cursor.getLong(cursor.getColumnIndex("data"));
        Date data = new Date();
        data.setTime(timeData);
        anexo.setData(data);

        anexo.setUriFoto(cursor.getString(cursor.getColumnIndex("uriFoto")));
    }


    public OrdemServico consultarOrdemServicoPorId(int idOrdemServico) {
        OrdemServico ordemServico = new OrdemServico();

        Cursor cursor = le.query("ordem_servico", null, "ID = ?", new String[]{String.valueOf(idOrdemServico)}, null, null, null);

        if (cursor.moveToNext()) {
            setOrdemServicoFromCursor(cursor, ordemServico);
        }
        return ordemServico;
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
}

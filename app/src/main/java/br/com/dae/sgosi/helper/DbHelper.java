package br.com.dae.sgosi.helper;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.dae.sgosi.Util.Constantes;

/**
 * Created by jamiltondamasceno
 */

public class DbHelper extends SQLiteOpenHelper {

    private Context context;

    public DbHelper(Context context) {

     //  super (ontext.deleteDatabase(Constantes.BD_NOME));

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
                " descricaoFim TEXT)";

        String anexoSQL = "CREATE TABLE anexo (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " os  INTEGER," +
                " data INTEGER," +
                " uriFoto TEXT);";

        db.execSQL(tipoServicoSQL);
        db.execSQL(clienteSQL);
        db.execSQL(ordemServicoSQL);
        db.execSQL(anexoSQL);

        try {
            db.execSQL(tipoServicoSQL);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String tipo_servico = "DROP TABLE IF EXISTS tipo_servico";
        String cliente = "DROP TABLE IF EXISTS cliente";
        String ordem_servico = "DROP TABLE IF EXISTS ordem_servico";
        String anexo = "DROP TABLE IF EXISTS anexo";
        db.execSQL(tipo_servico);
        db.execSQL(cliente);
        db.execSQL(ordem_servico);
        db.execSQL(anexo);
        try {
            db.execSQL(tipo_servico);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar App");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao atualizar App" + e.getMessage());
        }

    }

}

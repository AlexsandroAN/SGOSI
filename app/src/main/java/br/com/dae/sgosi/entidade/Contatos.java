package br.com.dae.sgosi.entidade;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 39091 on 03/05/2018.
 */

public class Contatos {

    private Context ctx;

    public Contatos(Context contexto) {
        this.ctx = contexto;
    }

    public List getContatos() {

        Cursor C_Contatos = this.ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        //pega os index das colunas
        int IndexID = C_Contatos.getColumnIndex(ContactsContract.Contacts._ID);
        int IndexTemTelefone =
                C_Contatos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

        int IndexName =
                C_Contatos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        List Contatos = new ArrayList();
        EntidadeContatos Contato;
        while (C_Contatos.moveToNext()) {
            Contato = new EntidadeContatos();
            Contato.setID(C_Contatos.getString(IndexID));
            Contato.setNome(C_Contatos.getString(IndexName));
            //verifica se o contato tem telefone
            if (Integer.parseInt(C_Contatos.getString(IndexTemTelefone)) > 0) {
                Telefone _Telefone = new Telefone(Contato.getID(), this.ctx);
                Contato.setTelefones(_Telefone.getTelefones());
            }
            Contatos.add(Contato);
        }
        C_Contatos.close();
        return Contatos;
    }
}
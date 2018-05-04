package br.com.dae.sgosi.entidade;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

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
        int IndexName = C_Contatos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int IndexTemTelefone = C_Contatos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

        List Cliente = new ArrayList();
        Cliente cliente;
        while (C_Contatos.moveToNext()) {
            cliente = new Cliente();
//            cliente.setID(C_Contatos.getString(IndexID));
            cliente.setNome(C_Contatos.getString(IndexName));
            //verifica se o contato tem telefone
            if (Integer.parseInt(C_Contatos.getString(IndexTemTelefone)) > 0) {
                Telefone _Telefone = new Telefone(cliente.getId(), this.ctx);
               // cliente.setTelefones(_Telefone.getTelefones());
            }
            Cliente.add(cliente);
        }
        C_Contatos.close();
        return Cliente;
    }
}
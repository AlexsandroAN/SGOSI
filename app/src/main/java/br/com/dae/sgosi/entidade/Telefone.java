package br.com.dae.sgosi.entidade;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 39091 on 03/05/2018.
 */

public class Telefone {
    private int _IDContato;

    private Context _ctx;

    public Telefone(int idCliente, Context Contexto)

    {

        this._IDContato = idCliente;

        this._ctx = Contexto;

    }



    public List getTelefones()

    {

        Cursor C_Telefones =this._ctx.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + _IDContato, null, null);

        int IndexTelefone;

        List Telefones = new ArrayList();

        while(C_Telefones.moveToNext())

        {

            EntidadeTelefone Telefone = new EntidadeTelefone();

            IndexTelefone = C_Telefones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            Telefone.setTelefone(C_Telefones.getString(IndexTelefone));



            Telefones.add(Telefone);

        }

        C_Telefones.close();

        return Telefones;

    }

}

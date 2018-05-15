package br.com.dae.sgosi.entidade;

/**
 * Created by 39091 on 03/05/2018.
 */

public class EntidadeTelefone {
    private String Telefone;
    public String getTelefone() {
        return Telefone;

    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Telefone: " + Telefone;
    }
}



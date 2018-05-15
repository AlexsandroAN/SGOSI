package br.com.dae.sgosi.entidade;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 39091 on 11/07/2016.
 */
public class Cliente implements Serializable {

    private int id;

    private String nome;

    private String descricao;

    private String endereco;

    private String email;

    private String telefone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //Metodo sobreescrito para que não aparece o nome do componente
    //na listView
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return nome + "-" + telefone;
    }
}



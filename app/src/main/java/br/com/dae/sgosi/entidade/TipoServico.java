package br.com.dae.sgosi.entidade;

import java.io.Serializable;

/**
 * Created by 39091 on 11/07/2016.
 */
public class TipoServico implements Serializable {

    private int id;

    private String nome;

    private String descricao;

    transient int posicaoAtual;

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

    public int getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(int posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public TipoServico() {

    }

    @Override
    public String toString() {
        return nome;
    }
}



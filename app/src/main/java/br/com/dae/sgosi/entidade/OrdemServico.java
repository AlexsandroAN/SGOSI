package br.com.dae.sgosi.entidade;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 39091 on 11/07/2016.
 */
public class OrdemServico implements Serializable {

    private int id;

    private int cliente;

    private int tipoServico;

    private int status;

    private Date dataInicio;

    private Date dataFim;

    private String descricaoInicio;

    private String descricaoFim;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(int tipoServico) {
        this.tipoServico = tipoServico;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricaoInicio() {
        return descricaoInicio;
    }

    public void setDescricaoInicio(String descricaoInicio) {
        this.descricaoInicio = descricaoInicio;
    }

    public String getDescricaoFim() {
        return descricaoFim;
    }

    public void setDescricaoFim(String descricaoFim) {
        this.descricaoFim = descricaoFim;
    }



    @Override
    public String toString() {
        return descricaoInicio;
    }
}



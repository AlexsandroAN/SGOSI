package br.com.dae.sgosi.entidade;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 39091 on 11/07/2016.
 */
public class OrdemServico implements Serializable {

    private int id;

    private Cliente cliente;

    private TipoServico tipoServico;

    private StatusOrdemServico status;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
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
        return "{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", tipoServico=" + tipoServico +
                ", status=" + status +
                '}';
    }
}



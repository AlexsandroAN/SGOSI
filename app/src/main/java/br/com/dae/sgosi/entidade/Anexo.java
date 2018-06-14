package br.com.dae.sgosi.entidade;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 39091 on 11/07/2016.
 */
public class Anexo implements Serializable {

    private int id;

    private OrdemServico os;

    private Date data;

    private String uriFoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrdemServico getOs() {
        return os;
    }

    public void setOs(OrdemServico os) {
        this.os = os;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
    }

    @Override
    public String toString() {
        return "Anexo{" +
                "uriFoto=" + uriFoto +
                '}';
    }
}



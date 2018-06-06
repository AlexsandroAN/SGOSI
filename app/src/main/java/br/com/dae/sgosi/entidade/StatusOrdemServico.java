package br.com.dae.sgosi.entidade;

public enum StatusOrdemServico {

    FAZER_ORCAMENTO("Fazer Orçamento"), // Para o processo inicial;
    REALIZAR_SERVICO("Realizar serviço"), // Quando este já foi fechado e o serviço será realizado
    EM_ANDAMENTO("Em andamento"), // Quando o processo está em andamento
    CONCLUIDO("Concluído"); // Quando o serviço já foi realizado e a ordem será finalizada

    transient int qtdOS;

    private StatusOrdemServico(String descricao) {
        this.descrisao = descricao;
    }

    private String descrisao;

    public String getDescrisao() {
        return descrisao;
    }

    public static StatusOrdemServico getStatusOS(int pos) {
        for (StatusOrdemServico statusOS : StatusOrdemServico.values()) {
            if (statusOS.ordinal() == pos) {
                return statusOS;
            }
        }
        return null;
    }

    public int getQtdOS() {
        return qtdOS;
    }

    public void setQtdOS(int qtdOS) {
        this.qtdOS = qtdOS;
    }

    public void setDescrisao(String descrisao) {
        this.descrisao = descrisao;
    }

    @Override
    public String toString() {
            return descrisao + " - " + qtdOS + " OS";
    }
}

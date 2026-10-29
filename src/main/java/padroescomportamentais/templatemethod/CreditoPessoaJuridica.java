package padroescomportamentais.templatemethod;

public class CreditoPessoaJuridica extends SolicitacaoCredito {

    public static final double LIMITE_COMPROMETIMENTO = 0.40;
    public static final int ANOS_MINIMOS_ATIVIDADE = 2;

    private int anosAtividade;

    public int getAnosAtividade() {
        return anosAtividade;
    }

    public void setAnosAtividade(int anosAtividade) {
        this.anosAtividade = anosAtividade;
    }

    @Override
    protected boolean atendeRequisitos() {
        return this.anosAtividade >= ANOS_MINIMOS_ATIVIDADE;
    }

    @Override
    protected double getLimiteComprometimento() {
        return LIMITE_COMPROMETIMENTO;
    }

    @Override
    public String getTipo() {
        return "CreditoEmpresarial";
    }
}

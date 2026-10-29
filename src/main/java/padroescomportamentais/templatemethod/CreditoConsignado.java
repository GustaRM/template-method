package padroescomportamentais.templatemethod;

public class CreditoConsignado extends SolicitacaoCredito {

    public static final double LIMITE_COMPROMETIMENTO = 0.35;

    private boolean beneficiarioAtivo;

    public boolean isBeneficiarioAtivo() {
        return beneficiarioAtivo;
    }

    public void setBeneficiarioAtivo(boolean beneficiarioAtivo) {
        this.beneficiarioAtivo = beneficiarioAtivo;
    }

    @Override
    protected boolean atendeRequisitos() {
        return this.beneficiarioAtivo;
    }

    @Override
    protected double getLimiteComprometimento() {
        return LIMITE_COMPROMETIMENTO;
    }

    @Override
    public String getTipo() {
        return "CreditoConsignado";
    }
}

package padroescomportamentais.templatemethod;

public class CreditoPessoaFisica extends SolicitacaoCredito {

    public static final double LIMITE_COMPROMETIMENTO = 0.30;
    public static final int IDADE_MINIMA = 18;

    private int idade;

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    protected boolean atendeRequisitos() {
        return this.idade >= IDADE_MINIMA;
    }

    @Override
    protected double getLimiteComprometimento() {
        return LIMITE_COMPROMETIMENTO;
    }
}

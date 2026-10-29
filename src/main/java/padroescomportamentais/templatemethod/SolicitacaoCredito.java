package padroescomportamentais.templatemethod;

/**
 * Classe abstrata do Template Method.
 *
 * Métodos-template (final): verificarAprovacao() e getInfo().
 * Passos concretos (comuns a todos): calcularParcela() e calcularComprometimento().
 * Passos abstratos (variam por subclasse): atendeRequisitos() e getLimiteComprometimento().
 * Gancho (hook) opcional: getTipo().
 */
public abstract class SolicitacaoCredito {

    private int numero;
    private String solicitante;
    private double valorSolicitado;
    private double rendaMensal;
    private int numeroParcelas;

    // ---------- Template Methods ----------

    public final String verificarAprovacao() {
        if (!this.atendeRequisitos()) {
            return "Reprovado";
        }
        if (this.calcularComprometimento() <= this.getLimiteComprometimento()) {
            return "Aprovado";
        }
        return "Reprovado";
    }

    public final String getInfo() {
        return getTipo() + "{" +
                "numero=" + this.numero +
                ", solicitante='" + this.solicitante + '\'' +
                ", resultado=" + this.verificarAprovacao() +
                '}';
    }

    // ---------- Passos concretos ----------

    public double calcularParcela() {
        if (this.valorSolicitado <= 0 || this.numeroParcelas <= 0) {
            throw new IllegalStateException("Valor e número de parcelas devem ser informados");
        }
        return this.valorSolicitado / this.numeroParcelas;
    }

    public double calcularComprometimento() {
        if (this.rendaMensal <= 0) {
            throw new IllegalStateException("A renda mensal deve ser informada");
        }
        return this.calcularParcela() / this.rendaMensal;
    }

    // ---------- Passos abstratos ----------

    protected abstract boolean atendeRequisitos();

    protected abstract double getLimiteComprometimento();

    // ---------- Gancho ----------

    public String getTipo() {
        return "Credito";
    }

    // ---------- Getters e setters ----------

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public double getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(double valorSolicitado) {
        if (valorSolicitado <= 0) {
            throw new IllegalArgumentException("O valor solicitado deve ser positivo");
        }
        this.valorSolicitado = valorSolicitado;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(double rendaMensal) {
        if (rendaMensal <= 0) {
            throw new IllegalArgumentException("A renda mensal deve ser positiva");
        }
        this.rendaMensal = rendaMensal;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        if (numeroParcelas <= 0) {
            throw new IllegalArgumentException("O número de parcelas deve ser positivo");
        }
        this.numeroParcelas = numeroParcelas;
    }
}

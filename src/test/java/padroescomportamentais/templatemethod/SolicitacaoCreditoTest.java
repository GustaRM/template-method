package padroescomportamentais.templatemethod;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa os passos concretos e a ordem de execução do método-template. */
class SolicitacaoCreditoTest {

    private static final double DELTA = 0.0001;

    /** Subclasse de teste que registra a ordem em que os passos são chamados. */
    private static class SolicitacaoEspia extends SolicitacaoCredito {
        final List<String> chamadas = new ArrayList<>();
        private final boolean requisitos;

        SolicitacaoEspia(boolean requisitos) {
            this.requisitos = requisitos;
        }

        @Override
        protected boolean atendeRequisitos() {
            chamadas.add("requisitos");
            return requisitos;
        }

        @Override
        protected double getLimiteComprometimento() {
            chamadas.add("limite");
            return 0.5;
        }
    }

    private SolicitacaoEspia criarEspia(boolean requisitos) {
        SolicitacaoEspia s = new SolicitacaoEspia(requisitos);
        s.setValorSolicitado(1000.0);
        s.setNumeroParcelas(10);
        s.setRendaMensal(1000.0);
        return s;
    }

    // ---------- passos concretos ----------

    @Test
    void deveCalcularParcela() {
        assertEquals(100.0, criarEspia(true).calcularParcela(), DELTA);
    }

    @Test
    void deveCalcularComprometimento() {
        assertEquals(0.1, criarEspia(true).calcularComprometimento(), DELTA);
    }

    @Test
    void tipoPadraoEhCredito() {
        assertEquals("Credito", criarEspia(true).getTipo());
    }

    // ---------- ordem do template ----------

    @Test
    void templateChamaRequisitosDepoisLimite() {
        SolicitacaoEspia s = criarEspia(true);
        assertEquals("Aprovado", s.verificarAprovacao());
        assertEquals(List.of("requisitos", "limite"), s.chamadas);
    }

    @Test
    void templateNaoChamaLimiteQuandoRequisitosFalham() {
        SolicitacaoEspia s = criarEspia(false);
        assertEquals("Reprovado", s.verificarAprovacao());
        assertEquals(List.of("requisitos"), s.chamadas);
    }

    @Test
    void getInfoUsaOGanchoEOResultadoDoTemplate() {
        SolicitacaoEspia s = criarEspia(true);
        s.setNumero(9);
        s.setSolicitante("Teste");
        assertEquals("Credito{numero=9, solicitante='Teste', resultado=Aprovado}", s.getInfo());
    }

    // ---------- validações ----------

    @Test
    void settersRejeitamValoresInvalidos() {
        SolicitacaoEspia s = new SolicitacaoEspia(true);
        assertThrows(IllegalArgumentException.class, () -> s.setValorSolicitado(0));
        assertThrows(IllegalArgumentException.class, () -> s.setValorSolicitado(-1));
        assertThrows(IllegalArgumentException.class, () -> s.setRendaMensal(0));
        assertThrows(IllegalArgumentException.class, () -> s.setNumeroParcelas(0));
    }

    @Test
    void calcularSemDadosLancaIllegalState() {
        SolicitacaoEspia s = new SolicitacaoEspia(true);
        assertThrows(IllegalStateException.class, s::calcularParcela);
        s.setValorSolicitado(1000.0);
        s.setNumeroParcelas(10);
        assertThrows(IllegalStateException.class, s::calcularComprometimento);
    }

    // ---------- polimorfismo ----------

    @Test
    void tiposDiferentesSeComportamDeFormaDiferenteComOsMesmosDados() {
        // parcela de 350 sobre renda de 1000 => 35% de comprometimento
        CreditoPessoaFisica pf = new CreditoPessoaFisica();
        pf.setIdade(30);
        CreditoConsignado consignado = new CreditoConsignado();
        consignado.setBeneficiarioAtivo(true);
        CreditoPessoaJuridica pj = new CreditoPessoaJuridica();
        pj.setAnosAtividade(5);

        SolicitacaoCredito[] todos = {pf, consignado, pj};
        for (SolicitacaoCredito s : todos) {
            s.setValorSolicitado(3500.0);
            s.setNumeroParcelas(10);
            s.setRendaMensal(1000.0);
        }

        assertEquals("Reprovado", pf.verificarAprovacao());         // limite 30%
        assertEquals("Aprovado", consignado.verificarAprovacao());  // limite 35%
        assertEquals("Aprovado", pj.verificarAprovacao());          // limite 40%
    }
}

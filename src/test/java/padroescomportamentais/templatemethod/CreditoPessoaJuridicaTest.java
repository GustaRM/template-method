package padroescomportamentais.templatemethod;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreditoPessoaJuridicaTest {

    private CreditoPessoaJuridica criar(int anosAtividade, double valor) {
        CreditoPessoaJuridica credito = new CreditoPessoaJuridica();
        credito.setAnosAtividade(anosAtividade);
        credito.setRendaMensal(2500.0);
        credito.setNumeroParcelas(12);
        credito.setValorSolicitado(valor);
        return credito;
    }

    @Test
    void deveRetornarAprovado() {
        assertEquals("Aprovado", criar(2, 12000.0).verificarAprovacao()); // exatamente 40%
    }

    @Test
    void deveRetornarReprovadoPorComprometimento() {
        assertEquals("Reprovado", criar(5, 12120.0).verificarAprovacao()); // 40,4%
    }

    @Test
    void deveRetornarReprovadoPorTempoDeAtividade() {
        assertEquals("Reprovado", criar(1, 6000.0).verificarAprovacao());
    }

    @Test
    void deveRetornarInformacoes() {
        CreditoPessoaJuridica credito = criar(3, 12000.0);
        credito.setSolicitante("Padaria Pão Quente");
        credito.setNumero(2);
        assertEquals("CreditoEmpresarial{numero=2, solicitante='Padaria Pão Quente', resultado=Aprovado}",
                credito.getInfo());
    }
}

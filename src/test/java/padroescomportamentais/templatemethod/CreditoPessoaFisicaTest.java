package padroescomportamentais.templatemethod;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreditoPessoaFisicaTest {

    private CreditoPessoaFisica criar(int idade, double valor) {
        CreditoPessoaFisica credito = new CreditoPessoaFisica();
        credito.setIdade(idade);
        credito.setRendaMensal(1000.0);
        credito.setNumeroParcelas(10);
        credito.setValorSolicitado(valor);
        return credito;
    }

    @Test
    void deveRetornarAprovado() {
        assertEquals("Aprovado", criar(30, 3000.0).verificarAprovacao()); // exatamente 30%
    }

    @Test
    void deveRetornarReprovadoPorComprometimento() {
        assertEquals("Reprovado", criar(30, 3010.0).verificarAprovacao()); // 30,1%
    }

    @Test
    void deveRetornarReprovadoPorIdadeMinima() {
        assertEquals("Reprovado", criar(17, 1000.0).verificarAprovacao());
    }

    @Test
    void deveAprovarComIdadeMinimaExata() {
        assertEquals("Aprovado", criar(18, 1000.0).verificarAprovacao());
    }

    @Test
    void deveRetornarInformacoes() {
        CreditoPessoaFisica credito = criar(30, 3000.0);
        credito.setSolicitante("Maria");
        credito.setNumero(1);
        assertEquals("Credito{numero=1, solicitante='Maria', resultado=Aprovado}", credito.getInfo());
    }
}

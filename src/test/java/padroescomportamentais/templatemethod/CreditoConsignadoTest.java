package padroescomportamentais.templatemethod;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreditoConsignadoTest {

    private CreditoConsignado criar(boolean beneficiarioAtivo, double valor) {
        CreditoConsignado credito = new CreditoConsignado();
        credito.setBeneficiarioAtivo(beneficiarioAtivo);
        credito.setRendaMensal(1000.0);
        credito.setNumeroParcelas(10);
        credito.setValorSolicitado(valor);
        return credito;
    }

    @Test
    void deveRetornarAprovado() {
        assertEquals("Aprovado", criar(true, 3500.0).verificarAprovacao()); // exatamente 35%
    }

    @Test
    void deveRetornarReprovadoPorComprometimento() {
        assertEquals("Reprovado", criar(true, 3510.0).verificarAprovacao());
    }

    @Test
    void deveRetornarReprovadoSeBeneficiarioInativo() {
        assertEquals("Reprovado", criar(false, 1000.0).verificarAprovacao());
    }

    @Test
    void deveRetornarInformacoes() {
        CreditoConsignado credito = criar(true, 3000.0);
        credito.setSolicitante("José");
        credito.setNumero(3);
        assertEquals("CreditoConsignado{numero=3, solicitante='José', resultado=Aprovado}", credito.getInfo());
    }
}

package padroescomportamentais.templatemethod;

public class Main {

    public static void main(String[] args) {
        CreditoPessoaFisica pf = new CreditoPessoaFisica();
        pf.setNumero(1);
        pf.setSolicitante("Maria Souza");
        pf.setIdade(30);
        pf.setRendaMensal(5000.0);
        pf.setValorSolicitado(12000.0);
        pf.setNumeroParcelas(10);

        CreditoPessoaJuridica pj = new CreditoPessoaJuridica();
        pj.setNumero(2);
        pj.setSolicitante("Padaria Pão Quente Ltda");
        pj.setAnosAtividade(1);
        pj.setRendaMensal(20000.0);
        pj.setValorSolicitado(60000.0);
        pj.setNumeroParcelas(12);

        CreditoConsignado consignado = new CreditoConsignado();
        consignado.setNumero(3);
        consignado.setSolicitante("José Almeida");
        consignado.setBeneficiarioAtivo(true);
        consignado.setRendaMensal(2500.0);
        consignado.setValorSolicitado(8000.0);
        consignado.setNumeroParcelas(12);

        SolicitacaoCredito[] solicitacoes = {pf, pj, consignado};
        for (SolicitacaoCredito s : solicitacoes) {
            System.out.println(s.getInfo());
        }
    }
}

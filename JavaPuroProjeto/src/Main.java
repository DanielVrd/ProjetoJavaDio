
class ContaBancaria {
    private static ContaBancaria instancia = new ContaBancaria();
    private double saldo;

    private ContaBancaria() {
        saldo = 0.0;
    }

    public static ContaBancaria getInstancia() {
        return instancia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public void sacar(double valor) {
        saldo -= valor;
    }
}

interface TaxaJurosStrategy {
    double calcularTaxaJuros(double saldo);
}

// Implementações das estratégias de taxas de juros.
class TaxaJurosSimples implements TaxaJurosStrategy {
    public double calcularTaxaJuros(double saldo) {
        return saldo * 0.05;
    }
}

class TaxaJurosCompostos implements TaxaJurosStrategy {
    public double calcularTaxaJuros(double saldo) {
        return saldo * 0.08;
    }
}

class ContaBancariaFacade {
    private ContaBancaria conta;
    private TaxaJurosStrategy taxaJuros;

    public ContaBancariaFacade() {
        conta = ContaBancaria.getInstancia();
        taxaJuros = new TaxaJurosSimples();
    }

    public void setTaxaJurosStrategy(TaxaJurosStrategy strategy) {
        taxaJuros = strategy;
    }

    public double getSaldo() {
        return conta.getSaldo();
    }

    public void depositar(double valor) {
        conta.depositar(valor);
    }

    public void sacar(double valor) {
        conta.sacar(valor);
    }

    public double calcularTaxaJuros() {
        return taxaJuros.calcularTaxaJuros(conta.getSaldo());
    }
}

public class Main {
    public static void main(String[] args) {
        ContaBancariaFacade contaFacade = new ContaBancariaFacade();
        contaFacade.depositar(1000.0);
        contaFacade.sacar(500.0);

        System.out.println("Saldo da conta: " + contaFacade.getSaldo());
        System.out.println("Taxa de juros: " + contaFacade.calcularTaxaJuros());

        contaFacade.setTaxaJurosStrategy(new TaxaJurosCompostos());
        System.out.println("Taxa de juros (compostos): " + contaFacade.calcularTaxaJuros());
    }
}

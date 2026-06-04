package excecoes;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("O saldo da conta é insuficiente");
    }
}
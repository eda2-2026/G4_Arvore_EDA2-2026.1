package entidades;

public record Dinheiro(int reais, int centavos) {

    public Dinheiro {
        if (centavos < 0 || centavos > 99) {
            throw new IllegalArgumentException("Os centavos devem estar entre 0 e 99");
        }
    }

    @Override
    public String toString() {
        if (reais < 0) {
            return String.format("-R$ %d,%02d", -reais, centavos);
        }
        return String.format("R$ %d,%02d", reais, centavos);
    }

    public boolean maiorQue(Dinheiro preco) {
        if (this.reais < preco.reais) return false;
        if (this.reais > preco.reais) return true;
        return (this.centavos > preco.centavos);
    }

    public boolean maiorIgual(Dinheiro preco) {
        if (this.equals(preco)) return true;
        return this.maiorQue(preco);
    }

    public Dinheiro somar(Dinheiro valor) {
        // 1. Converte ambos os valores totalmente para centavos
        int centavosAtuais = (this.reais * 100) + this.centavos;
        int centavosSomar = (valor.reais * 100) + valor.centavos;

        // 2. Faz a soma
        int resultadoCentavos = centavosAtuais + centavosSomar;

        // 3. Transforma de volta em reais e centavos
        int novosReais = resultadoCentavos / 100;
        int novosCentavos = resultadoCentavos % 100;

        // Garante que os centavos fiquem positivos
        Math.abs(novosCentavos);

        return new Dinheiro(novosReais, novosCentavos);
    }

    public Dinheiro subtrair(Dinheiro valor) {
        // 1. Converte ambos os valores totalmente para centavos
        int centavosAtuais = (this.reais * 100) + this.centavos;
        int centavosSubtrair = (valor.reais * 100) + valor.centavos;

        // 2. Faz a subtração
        int resultadoCentavos = centavosAtuais - centavosSubtrair;

        // 3. Transformade volta em reais e centavos
        int novosReais = resultadoCentavos / 100;
        int novosCentavos = resultadoCentavos % 100;

        // Garante que os centavos fiquem positivos
        novosCentavos = Math.abs(novosCentavos);

        return new Dinheiro(novosReais, novosCentavos);
    }
    
    public int getReais() {
        return reais;
    }

    public int getCentavos() {
        return centavos;
    }
}
package excecoes;

public class UsuarioNaoCadastradoException extends RuntimeException {
    public UsuarioNaoCadastradoException() {
        super("O usuário não está cadastrado");
    }
}
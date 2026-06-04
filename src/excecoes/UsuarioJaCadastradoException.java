package excecoes;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException() {
        super("O usuário já está cadastrado");
    }
}
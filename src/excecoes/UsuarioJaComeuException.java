package excecoes;

public class UsuarioJaComeuException extends RuntimeException {
    public UsuarioJaComeuException() {
        super("O usuário já fez essa refeição");
    }
}
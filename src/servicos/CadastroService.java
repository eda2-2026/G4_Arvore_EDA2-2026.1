package servicos;

import Estrutura.Avl;
import entidades.Usuario;
import excecoes.UsuarioJaCadastradoException;
import excecoes.UsuarioNaoCadastradoException;

public class CadastroService {

    private static Avl arvoreUsuarios = new Avl();

    public static void cadastrarUsuario(Usuario usuario){
        if(arvoreUsuarios.buscarAvl(usuario.getMatricula()) == null){
            arvoreUsuarios.inserirAvl(usuario);
        }else{
            throw new UsuarioJaCadastradoException();       
        }
    }
    public static void removerUsuario(long matricula){
        if(arvoreUsuarios.buscarAvl(matricula) != null){
            arvoreUsuarios.removerAvl(matricula);
        }else{
            throw new UsuarioNaoCadastradoException();
        }
    }

    public static java.util.List<Usuario> obterTodosUsuarios() {
        return arvoreUsuarios.obterTodos();
    }

    public static Usuario buscarUsuario(long matricula){
        return arvoreUsuarios.buscarAvl(matricula);
    }

}

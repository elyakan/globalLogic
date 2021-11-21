package cl.globallogic.usuario.services;

import cl.globallogic.usuario.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> getMail(String i_email);

    List<Usuario> getUsuarios();

    Usuario getUsuarioById(String id);

    Usuario insert(Usuario usuario);

    void updateUsuario(String id, Usuario usuario);

    void deleteUsuario(String id);
}

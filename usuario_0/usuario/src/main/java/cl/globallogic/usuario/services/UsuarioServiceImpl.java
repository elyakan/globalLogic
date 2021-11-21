package cl.globallogic.usuario.services;

import cl.globallogic.usuario.model.Usuario;
import cl.globallogic.usuario.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl  implements UsuarioService {
    UsuarioRepository usuarioRepository;
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> getMail(String i_email) {
        List<Usuario> usuario = new ArrayList<>();
        usuarioRepository.findByEmailContaining(i_email).forEach(usuario::add);
        return usuario;
    }

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuario = new ArrayList<>();
        usuarioRepository.findAll().forEach(usuario::add);
        return usuario;
    }

    @Override
    public Usuario getUsuarioById(String id) {
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Usuario insert(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    @Override
    public void updateUsuario(String id, Usuario usuario) {
        Usuario usuarioFromDb = usuarioRepository.findById(id).get();
        System.out.println(usuarioFromDb.toString());
        usuarioFromDb.setName(usuario.getName());
        usuarioFromDb.setEmail(usuario.getEmail());
        usuarioFromDb.setIsactive(usuario.getIsactive());

        usuarioRepository.save(usuarioFromDb);
    }

    @Override
    public void deleteUsuario(String id) {
        usuarioRepository.deleteById(id);
    }
}

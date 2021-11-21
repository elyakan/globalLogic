package cl.globallogic.usuario.repositories;

import cl.globallogic.usuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

    List<Usuario> findByEmailContaining(String email);
}
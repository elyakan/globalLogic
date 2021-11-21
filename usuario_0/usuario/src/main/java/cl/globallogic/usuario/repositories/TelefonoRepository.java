package cl.globallogic.usuario.repositories;

import cl.globallogic.usuario.model.Telefono;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefonoRepository extends CrudRepository<Telefono, String> {

}

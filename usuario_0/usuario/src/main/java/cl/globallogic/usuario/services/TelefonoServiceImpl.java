package cl.globallogic.usuario.services;

import cl.globallogic.usuario.model.Telefono;
import cl.globallogic.usuario.repositories.TelefonoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelefonoServiceImpl  implements TelefonoService {
    TelefonoRepository telefonoRepository;
    public TelefonoServiceImpl(TelefonoRepository telefonoRepository) {
        this.telefonoRepository = telefonoRepository;
    }

    @Override
    public List<Telefono> getTelefonos() {
        List<Telefono> telefono = new ArrayList<>();
        telefonoRepository.findAll().forEach(telefono::add);
        return telefono;
    }

    @Override
    public Telefono getTelefonoById(String id) {
        return telefonoRepository.findById(id).get();
    }

    @Override
    public Telefono insert(Telefono telefono) {
        return telefonoRepository.save(telefono);
    }


    @Override
    public Telefono updateTelefono(String id, Telefono telefono) {
        Telefono telefonoFromDb = telefonoRepository.findById(id).get();
        System.out.println(telefonoFromDb.toString());
        telefonoFromDb.setCitycode(telefono.getCitycode());
        telefonoFromDb.setContrycode(telefono.getContrycode());
        telefonoFromDb.setNumber(telefono.getNumber());

        telefonoRepository.save(telefonoFromDb);
        return telefonoFromDb;
    }

    @Override
    public void deleteTelefono(String id) {
        telefonoRepository.deleteById(id);
    }
}

package cl.globallogic.usuario.services;

import cl.globallogic.usuario.model.Telefono;

import java.util.List;

public interface TelefonoService {

    List<Telefono> getTelefonos();

    Telefono getTelefonoById(String id_telefono);

    Telefono insert(Telefono telefono);

    Telefono updateTelefono(String id_telefono, Telefono telefono);

    void deleteTelefono(String id_telefono);
}


package com.agendaContactos.agendaContactos.service;

import com.agendaContactos.agendaContactos.model.Contacto;
import com.agendaContactos.agendaContactos.repository.ContactoRepository;
import com.agendaContactos.agendaContactos.repository.IContactoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactoService implements IContactoService {

    @Autowired
    private IContactoRepository contactoRepository;

    @Override
    public List<Contacto> listar() {
        return contactoRepository.findAll();
    }

    @Override
    public boolean guardar(Contacto contacto) {
        boolean rta = false;
        if (contactoRepository.save(contacto) != null) {
            rta = true;
        }
        return rta;
    }

    @Override
    public Contacto buscarPorId(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

    @Override
    public boolean eliminar(Long id) {
        boolean rta = false;
        if (contactoRepository.existsById(id)) {
            contactoRepository.deleteById(id);
            rta = true;
        }
        return rta;
    }

    public boolean modificar (Long id, Contacto contacto){
        boolean rta = false;
        Contacto aModificar = buscarPorId(id);
        if(aModificar!=null){ 
            aModificar.setNombre(contacto.getNombre());
            aModificar.setApellido(contacto.getApellido());
            aModificar.setTelefono(contacto.getTelefono());
            aModificar.setEmail(contacto.getEmail());
            this.guardar(aModificar);
            rta = true;
        }
        return rta;
    }

}

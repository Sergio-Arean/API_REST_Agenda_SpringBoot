
package com.agendaContactos.agendaContactos.service;

import com.agendaContactos.agendaContactos.model.Contacto;
import java.util.List;


public interface IContactoService {
    public List <Contacto> listar();
    public boolean guardar(Contacto contacto);
    public boolean modificar(Long id,Contacto contacto);
    public Contacto buscarPorId(Long id);
    public boolean eliminar(Long id);
}

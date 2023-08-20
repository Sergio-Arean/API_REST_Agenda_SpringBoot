
package com.agendaContactos.agendaContactos.repository;

import com.agendaContactos.agendaContactos.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactoRepository extends JpaRepository<Contacto, Long> {
    
}

package com.agendaContactos.agendaContactos.controller;

import com.agendaContactos.agendaContactos.model.Contacto;
import com.agendaContactos.agendaContactos.service.ContactoService;
import com.agendaContactos.agendaContactos.service.IContactoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

@Controller
public class ContactoController {

    @Autowired
    IContactoService contactoService;

    /* 
    Backend 
     */
    //Listar
    @GetMapping("/contactos/")
    @ResponseBody
    public List<Contacto> listarContactos() {
        return contactoService.listar();
    }

    //Agregar
    @PostMapping("/contactos/agregar")
    @ResponseBody
    public ResponseEntity<String> agregarContacto(@RequestBody Contacto contacto) {
        if (contactoService.guardar(contacto)) {
            return new ResponseEntity<>("El contacto fue agregado exitosamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("El contacto no pudo ser agregado", HttpStatus.BAD_REQUEST);
        }

    }

    //Modificar
    @PutMapping("/contactos/modificar/{id}")
    @ResponseBody
    public ResponseEntity<String> modificarContacto(@PathVariable Long id, @RequestBody Contacto contacto) {
        if (contactoService.modificar(id, contacto)) {
            return new ResponseEntity<>("El contacto fue modificado exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El contacto no fue encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar
    @DeleteMapping("contactos/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarContacto(@PathVariable Long id) {
        if (contactoService.eliminar(id)) {
            return new ResponseEntity<>("El contacto fue eliminado exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El contacto no fue encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    /*
    Vistas 
     */
    @GetMapping("/views/contactos/")
    public String listar(Model model) {
        List<Contacto> contactos = contactoService.listar();
        model.addAttribute("contactos", contactos);
        model.addAttribute("titulo", "Contactos");
        return "/views/contactos/listar";
    }

    @GetMapping("/views/contactos/agregar")
    public String agregar(Model model) {
        Contacto contacto = new Contacto();
        model.addAttribute("titulo", "Nuevo Contacto");
        model.addAttribute("contacto", contacto);
        return "views/contactos/agregar";
    }

    @PostMapping("/views/contactos/guardar")
    public String guardar(@Valid @ModelAttribute Contacto contacto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo Contacto");
            model.addAttribute("contacto", contacto);
            System.out.println("Hubo errores en el formulario");
            return "views/contactos/agregar";
        }
        contactoService.guardar(contacto);
        System.out.println("Cliente guardado!");
        return "redirect:/views/contactos/";
    }

    @GetMapping("/views/contactos/modificar/{id}")
    public String modificar(@PathVariable("id") Long id, Model model) {
        Contacto contacto = contactoService.buscarPorId(id);
        model.addAttribute("titulo", "Modificar Contacto");
        model.addAttribute("contacto", contacto);
        return "views/contactos/agregar";
    }

    @GetMapping("/views/contactos/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        contactoService.eliminar(id);
        return "redirect:/views/contactos/";
    }

}

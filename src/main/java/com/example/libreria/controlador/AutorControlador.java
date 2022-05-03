/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.libreria.controlador;
import com.example.libreria.entidades.Autor;
import com.example.libreria.excepciones.ExcepcionServicio;
import com.example.libreria.servicios.AutorServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/AutorForm")
    public String cargaAutor() {
        return "AutorForm.html";
    }

    @PostMapping("/registroAutor")
    public String registroAutor(ModelMap modelo, @RequestParam(required = false) String nombre) {
        try {
            autorServicio.registrar(nombre);
        } catch (ExcepcionServicio ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autorForm.html";
        }
        modelo.put("mensaje", "Autor cargado con éxito");
        return "index.html";
    }

    @GetMapping("/tablasAutor")
    public String listadoAutores(ModelMap modelo) {
        modelo.addAttribute("lista", autorServicio.listadoAutor());
        return "tablasAutor.html";
    }

    @GetMapping("/autor/{id}")
    public String editarAutor(RedirectAttributes redirectAttributes, @PathVariable("id") String id, ModelMap modelo) {
        Autor autor = autorServicio.buscarPorId(id).get();
        modelo.put("tipo", autor);
        modelo.put("nombre", autor.getNombre());
        return "AutorForm.html";
    }

    @PostMapping("/edicionAutor")
    public String editarAutor(RedirectAttributes redirectAttributes, ModelMap modelo, String id, @RequestParam(required = false) String nombreAutor) {
        Autor autor = autorServicio.buscarPorId(id).get();
        try {
            modelo.put("nombreAutor", autor.getNombre());
            autorServicio.modificar(id, nombreAutor);
        } catch (ExcepcionServicio ex) {
            modelo.put("tipo", autor);
            modelo.put("error", ex.getMessage());
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/autor/{id}";
        }
        modelo.put("mensaje", "Autor modificado con éxito");
        return "index.html";
    }

    @GetMapping("autor/baja/{id}")
    public String bajaAutor(@PathVariable("id") String id, ModelMap modelo) throws ExcepcionServicio {
        try {
            autorServicio.darDeBaja(id);
        } catch (ExcepcionServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }
        return "redirect:/tablasAutor";
    }
    
        @GetMapping("autor/alta/{id}")
    public String altaAutor(@PathVariable("id") String id, ModelMap modelo) throws ExcepcionServicio {
        try {
            autorServicio.darDeAlta(id);
        } catch (ExcepcionServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }
        return "redirect:/tablasAutor";
    }
}
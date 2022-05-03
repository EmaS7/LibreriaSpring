/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.libreria.controlador;

import com.example.libreria.entidades.Editorial;
import com.example.libreria.excepciones.ExcepcionServicio;
import com.example.libreria.servicios.EditorialServicio;
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

/**
 *
 * @author HP
 */
@Controller
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/EditorialForm")
    public String cargaEditorial(){
        return "EditorialForm.html";
    }
    
    @PostMapping("/registroEditorial")
    public String registroEditorial(ModelMap modelo, @RequestParam String nombreEditorial){
        System.out.println("Nombre de la editorial: " +nombreEditorial);
        try{
            editorialServicio.registrar(nombreEditorial);
        }catch(ExcepcionServicio ex){
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "EditorialForm.html";
        }
        modelo.put("mensaje", "Editorial cargada con exito!");
        return "index.html";
    }
    
    @GetMapping("/tablasEditorial")
    public String listadoEditorial(ModelMap modelo){
        modelo.addAttribute("lista", editorialServicio.listadoEditorial());
        return "tablasEditorial.html";
    }
    
    @GetMapping("/editorial/{id}")
    public String editarEditorial(RedirectAttributes redirectAttributes, @PathVariable("id") String id, ModelMap modelo){
        Editorial editorial = editorialServicio.buscarPorId(id).get();
        modelo.put("tipo", editorial);
        modelo.put("nombreEditorial", editorial.getNombre());
        return "editorialForm.html";
    }
    
    @PostMapping("/edicionEditorial")
    public String editarEditorial(RedirectAttributes redirectAttributes, ModelMap modelo, String id, @RequestParam(required = false) String nombreEditorial){
        Editorial editorial = editorialServicio.buscarPorId(id).get();
        try{
            modelo.put("nombreEditorial", editorial);
            editorialServicio.modificar(id, nombreEditorial);
        }catch(ExcepcionServicio ex){
            modelo.put("tipo", editorial);
            modelo.put("error", ex.getMessage());
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/editorial/{id}";
        }
        modelo.put("mensaje", "Editorial modificada con éxito!");
        return "index.html";
    }
    
    @GetMapping("editorial/baja/{id}")
    public String bajaEditorial(@PathVariable("id") String id, ModelMap modelo) throws ExcepcionServicio {
        try {
            editorialServicio.darDeBaja(id);
        }catch(ExcepcionServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }
        return "redirect:/tablasEditorial";
    }
    
        @GetMapping("editorial/alta/{id}")
    public String altaEditorial(@PathVariable("id") String id, ModelMap modelo) throws ExcepcionServicio {
        try {
            editorialServicio.darDeAlta(id);
        } catch (ExcepcionServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }
        return "redirect:/tablasEditorial";
    }
}
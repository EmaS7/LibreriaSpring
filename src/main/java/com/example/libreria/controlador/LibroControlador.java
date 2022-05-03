package com.example.libreria.controlador;
import com.example.libreria.entidades.Autor;
import com.example.libreria.entidades.Editorial;
import com.example.libreria.entidades.Libro;
import com.example.libreria.excepciones.ExcepcionServicio;
import com.example.libreria.servicios.AutorServicio;
import com.example.libreria.servicios.EditorialServicio;
import com.example.libreria.servicios.LibroServicio;
import java.util.List;
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
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/LibroForm")
    public String formularioLibro(ModelMap modelo){
        List<Autor> listadoAutores = autorServicio.listadoAutor();
        List<Editorial> listadoEditorial = editorialServicio.listadoEditorial();
        modelo.addAttribute("autores", listadoAutores);
        modelo.addAttribute("editoriales", listadoEditorial);
        return "libroForm.html";
    }
    
    @PostMapping("/registroLibro")
    public String registroLibro(ModelMap modelo, @RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejempPrest,@RequestParam(required = false) Integer ejempRest,@RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String autor, @RequestParam(required = false) String editorial){
        Autor autorObjeto = autorServicio.buscarPorNombre(autor);
        Editorial editorialObjeto = editorialServicio.buscarPorNombre(editorial);
        try{
            libroServicio.registrar(titulo, anio, isbn, ejemplares, ejempPrest, ejempRest, autorObjeto, editorialObjeto);
        }catch(ExcepcionServicio ex){
            modelo.put("error", ex.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.addAttribute("autores", autorServicio.listadoAutor());
            modelo.addAttribute("editoriales", editorialServicio.listadoEditorial());
            return "libroForm.html"; 
        }
        modelo.put("mensaje", "Libro cargado con exito!");
        return "index.html";
    }
    @GetMapping("/tablasLibros")
    public String listadoLibros(ModelMap modelo){
        modelo.addAttribute("lista", libroServicio.listadoLibros());
        return "tablasLibros.html";
    }    
    @GetMapping("/libro/{id}")
    public String editarLibro(@PathVariable("id") String id,RedirectAttributes redirectAttributes, ModelMap modelo){
        Libro libro = libroServicio.buscarLibros(id).get();
        modelo.put("tipo", libro);
        modelo.put("isbn", libro.getIsbn());
        modelo.put("titulo", libro.getTitulo());
        modelo.put("anio", libro.getAnio());
        modelo.put("autores", autorServicio.listadoAutor());
        modelo.put("editoriales", editorialServicio.listadoEditorial());
        return "libroForm.html";
    }
    @PostMapping("/editarLibro")
    public String CambioLibro(RedirectAttributes redirectAttributes, String id, ModelMap modelo, @RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) Integer ejempPrest, @RequestParam(required = false) Integer ejempRest, @RequestParam(required = false) String autor, @RequestParam(required = false) String editorial){
        Libro libro = libroServicio.buscarLibros(id).get();
        Autor autorObjeto = autorServicio.buscarPorNombre(autor);
        Editorial editorialObjeto = editorialServicio.buscarPorNombre(editorial);
        try{
            libroServicio.modificar(id, titulo, anio, isbn, ejemplares, ejempPrest, ejempRest, autorObjeto, editorialObjeto);
        }catch(ExcepcionServicio ex){
            modelo.put("tipo", libro);
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/libro/{id}";
        }
        modelo.put("mensaje", "Libro modificado con éxito!");
        return "index.html";
        }
    
    @GetMapping("libro/baja/{id}")
    public String bajaLibro(@PathVariable("id") String id, ModelMap modelo) throws ExcepcionServicio {
        try {
            libroServicio.darDeBaja(id);
        } catch (ExcepcionServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }
        return "redirect:/tablasLibros";
    }
    
    @GetMapping("libro/alta/{id}")
    public String altaLibro(@PathVariable("id") String id, ModelMap modelo) throws ExcepcionServicio {
        try {
            libroServicio.darDeAlta(id);
        } catch (ExcepcionServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }
        return "redirect:/tablasLibros";
    }
    
}


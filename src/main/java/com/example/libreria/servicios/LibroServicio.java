/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.entidades.Editorial;
import com.example.libreria.entidades.Libro;
import com.example.libreria.excepciones.ExcepcionServicio;
import com.example.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void registrar(String titulo, Integer anio, Long isbn, Integer ejemp, Integer ejempPrest, Integer ejempRest, Autor autor, Editorial editorial) throws ExcepcionServicio {
        validar(titulo, anio, isbn, ejemp, ejempPrest, ejempRest);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemp);

        libroRepositorio.save(libro);
    }

    @Transactional
    public void modificar(String id, String titulo, Integer anio, Long isbn, Integer ejemp, Integer ejempPrest, Integer ejempRest, Autor autor, Editorial editorial) throws ExcepcionServicio {
        validar(titulo, anio, isbn, ejemp, ejempPrest, ejempRest);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemp);

            libroRepositorio.save(libro);
        } else {
            throw new ExcepcionServicio("No se encuentra el libro mostro");
        }
    }

    @Transactional
    public void darDeBaja(String id) throws ExcepcionServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);
        } else {
            throw new ExcepcionServicio("El libro no se encuentra registrado");
        }
    }
    @Transactional
    public void darDeAlta(String id) throws ExcepcionServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);
        } else {
            throw new ExcepcionServicio("El libro no se encuentra registrado");
        }
    }

    public void validar(String titulo, Integer anio, Long isbn, Integer ejemp, Integer ejempPrest, Integer ejempRest) throws ExcepcionServicio {
        if (titulo == null) {
            throw new ExcepcionServicio("El titulo no puede ser nulo");
        }
        if (titulo.isEmpty()) {
            throw new ExcepcionServicio("El titulo no puede estar vacio");
        }
        if (anio == null) {
            throw new ExcepcionServicio("Ingrese un año valido");
        }
        if (isbn == null) {
            throw new ExcepcionServicio("Ingrese un año valido");
        }
        if (ejemp == null) {
            throw new ExcepcionServicio("Cantidad de ejemplares no valido");
        }
        if (ejempPrest == null) {
            throw new ExcepcionServicio("Error");
        }
        if (ejempRest == null) {
            throw new ExcepcionServicio("Error");
        }

    }

    public List<Libro> listadoLibros() {
        return (List<Libro>)libroRepositorio.findAll();
    }

    public Optional<Libro> buscarLibros(String id) {
        return (Optional<Libro>)libroRepositorio.findById(id);
    }

    public Libro buscarPorTitulo(String titulo) {
        return libroRepositorio.buscarPorTitulo(titulo);
    }
}

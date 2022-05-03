/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.excepciones.ExcepcionServicio;
import com.example.libreria.repositorios.AutorRepositorio;
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
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void registrar(String nombre) throws ExcepcionServicio {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        autorRepositorio.save(autor);
    }

    @Transactional
    public void modificar(String id, String nombre) throws ExcepcionServicio {
        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        } else {
            throw new ExcepcionServicio("No se encuentra el autor");
        }
    }

    @Transactional
    public void darDeBaja(String id) throws ExcepcionServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = autorRepositorio.findById(id).get();
            autor.setAlta(Boolean.FALSE);
            autorRepositorio.save(autor);
        } else {
            throw new ExcepcionServicio("No se encontró el autor pedida.");
        }
    }
    
    @Transactional
    public void darDeAlta(String id) throws ExcepcionServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = autorRepositorio.findById(id).get();
            autor.setAlta(Boolean.TRUE);
            autorRepositorio.save(autor);
        } else {
            throw new ExcepcionServicio("No se encontró el autor pedida.");
        }
    }

    public void validar(String nombre) throws ExcepcionServicio {
        if (nombre == null) {
            throw new ExcepcionServicio("El nombre no puede ser nulo");
        }
        if (nombre.isEmpty()) {
            throw new ExcepcionServicio("El nombre no puede estar vacio");
        }
    }
    public List<Autor> listadoAutor() {
        return (List<Autor>) autorRepositorio.findAll();
    }

     public Autor buscarPorNombre(String nombre) {
        return autorRepositorio.buscarPorNombreAutor(nombre);
    }
     public Optional<Autor> buscarPorId(String id) {
        return autorRepositorio.findById(id);
    }
}

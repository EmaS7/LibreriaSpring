/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.libreria.servicios;

import com.example.libreria.entidades.Editorial;
import com.example.libreria.excepciones.ExcepcionServicio;
import com.example.libreria.repositorios.EditorialRepositorio;
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
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void registrar(String nombre) throws ExcepcionServicio {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificar(String id, String nombre) throws ExcepcionServicio {
        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        } else {
            throw new ExcepcionServicio("No se encuentra el autor");
        }
    }

    @Transactional
    public void darDeBaja(String id) throws ExcepcionServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = editorialRepositorio.findById(id).get();
            editorial.setAlta(Boolean.FALSE);
            editorialRepositorio.save(editorial);
        } else {
            throw new ExcepcionServicio("La editorial no se encuentra registrada");
        }
    }
    
    @Transactional
    public void darDeAlta(String id) throws ExcepcionServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = editorialRepositorio.findById(id).get();
            editorial.setAlta(Boolean.TRUE);
            editorialRepositorio.save(editorial);
        } else {
            throw new ExcepcionServicio("La editorial no se encuentra registrada");
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

    public List<Editorial> listadoEditorial() {
        return (List<Editorial>) editorialRepositorio.findAll();
    }

    public Editorial buscarPorNombre(String nombre) {
        return editorialRepositorio.buscarPorEditorial(nombre);
    }

    public Optional<Editorial> buscarPorId(String id) {
        return editorialRepositorio.findById(id);
    }
}

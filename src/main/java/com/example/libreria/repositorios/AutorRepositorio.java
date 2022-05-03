
package com.example.libreria.repositorios;

import com.example.libreria.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Query("SELECT l FROM Autor l WHERE l.nombre = :nombre")
    public Autor buscarPorNombreAutor(@Param("nombre") String nombre);
    
}


package com.example.libreria.repositorios;

import com.example.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
    @Query("SELECT l FROM Editorial l WHERE l.nombre = :nombre")
    public Editorial buscarPorEditorial(@Param("nombre") String nombre);
    
}

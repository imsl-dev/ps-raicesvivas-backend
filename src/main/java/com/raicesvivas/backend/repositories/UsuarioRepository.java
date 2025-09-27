package com.raicesvivas.backend.models.repositories;

import com.raicesvivas.backend.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
}

package com.raicesvivas.backend.models.repositories;

import com.raicesvivas.backend.models.entities.PagoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoUsuarioRepository extends JpaRepository<PagoUsuario, Long> {
}

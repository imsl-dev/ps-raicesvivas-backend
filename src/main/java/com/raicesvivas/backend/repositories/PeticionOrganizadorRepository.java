package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.PeticionOrganizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeticionOrganizadorRepository extends JpaRepository<PeticionOrganizador, Integer> {

    PeticionOrganizador findByUsuarioId(Integer idUsuario);
}

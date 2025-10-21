package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.PeticionOrganizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeticionOrganizadorRepository extends JpaRepository<PeticionOrganizador, Integer> {
}

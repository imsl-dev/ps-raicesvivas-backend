package com.raicesvivas.backend.repositories.auxiliar;

import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {

}

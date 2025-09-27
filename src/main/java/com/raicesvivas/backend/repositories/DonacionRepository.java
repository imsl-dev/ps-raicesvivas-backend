package com.raicesvivas.backend.models.repositories;

import com.raicesvivas.backend.models.entities.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonacionRepository  extends JpaRepository<Donacion, Integer> {
}

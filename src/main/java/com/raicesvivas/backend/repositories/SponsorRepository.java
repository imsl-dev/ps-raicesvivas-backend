package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
    List<Sponsor> findByActivo(Boolean activo);
}

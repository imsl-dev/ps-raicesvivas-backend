package com.raicesvivas.backend.models.repositories;

import com.raicesvivas.backend.models.entities.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
}

package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.Canjeable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanjeableRepository extends JpaRepository<Canjeable, Integer> {
}

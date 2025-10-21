package com.raicesvivas.backend.repositories.auxiliar;

import com.raicesvivas.backend.models.entities.auxiliar.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Integer> {
}

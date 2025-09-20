package com.raicesvivas.backend.models.entities.auxiliar;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuentas_bancarias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cbu", nullable = false)
    private Long cbu;

    // Evitar referencia circular - solo almacenar ID
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
}
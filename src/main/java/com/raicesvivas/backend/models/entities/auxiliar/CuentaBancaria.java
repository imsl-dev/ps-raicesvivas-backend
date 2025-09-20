package com.raicesvivas.backend.models.entities.auxiliar;

import com.raicesvivas.backend.models.entities.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cuentas_bancarias")
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int CBU;

    @Column(name = "id_usuario")
    private int idUsuario;
}

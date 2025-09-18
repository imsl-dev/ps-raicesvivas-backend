package com.raicesvivas.backend.models.entities.auxiliar;

import com.raicesvivas.backend.models.entities.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="cuentas_bancarias")
@Data
public class CuentaBancaria {

    //TODO estamos con estos campos? o hara falta mas?
    @Id
    private int id;

    private int CBU;

    private Usuario titular;
}

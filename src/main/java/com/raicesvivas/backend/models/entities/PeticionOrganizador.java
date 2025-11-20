package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.enums.EstadoPeticion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "peticiones_organizadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeticionOrganizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Para evitar referencia circular - solo almacenar ID
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_peticion", nullable = false)
    private EstadoPeticion estadoPeticion;

    @Column(name = "mensaje_usuario", columnDefinition = "TEXT")
    private String mensajeUsuario;

    @Column(name = "image64", columnDefinition = "TEXT")
    private String image64;

    @Column(name = "nombreUsuario", columnDefinition = "TEXT")
    private String nombreUsuario;

    @Column(name = "apellidoUsuario", columnDefinition = "TEXT")
    private String apellidoUsuario;

    @Column(name = "email", columnDefinition = "TEXT")
    private String email;

    @Column(name="userImage", columnDefinition = "TEXT")
    private String userImage;
}
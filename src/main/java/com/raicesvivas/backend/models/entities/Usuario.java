package com.raicesvivas.backend.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.models.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="email",nullable = false, length = 255)
    private String email;

    @Column(name="password",nullable = false,length = 255)
    private String password;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 255)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "nro_doc", nullable = false, length = 20)
    private String nroDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provincia_id", referencedColumnName = "id", nullable = false)
    private Provincia provincia;

    @Column(name = "puntos", columnDefinition = "INTEGER DEFAULT 0")
    private Integer puntos = 0;

    @Column(name = "ruta_img", columnDefinition = "TEXT")
    private String rutaImg;

    // 🔥 Many-to-Many with Canjeable
    @ManyToMany
    @JoinTable(
            name = "usuario_canjeables",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "canjeable_id")
    )
    private List<Canjeable> canjeables = new ArrayList<>();
}
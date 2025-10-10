package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.EventoDto;
import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Sponsor;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.entities.auxiliar.CuentaBancaria;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.repositories.EventoRepository;
import com.raicesvivas.backend.repositories.SponsorRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.auxiliar.CuentaBancariaRepository;
import com.raicesvivas.backend.repositories.auxiliar.ProvinciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final ProvinciaRepository provinciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SponsorRepository sponsorRepository;
    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final ModelMapper mapper;

    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }

    public Evento getEventoById(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con ID: " + id + " no encontrado"));
    }

    public Evento saveEvento(EventoDto dto) {
        Evento nuevoEvento = mapper.map(dto, Evento.class);
        nuevoEvento.setId(null);

        // Asignar provincia
        Provincia provincia = provinciaRepository.findById(dto.getProvinciaId())
                .orElseThrow(() -> new EntityNotFoundException("Provincia con ID: " + dto.getProvinciaId() + " no encontrada"));
        nuevoEvento.setProvincia(provincia);

        // Asignar organizador
        Usuario organizador = usuarioRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + dto.getOrganizadorId() + " no encontrado"));
        nuevoEvento.setOrganizador(organizador);

        // Asignar sponsor si existe
        if (dto.getSponsorId() != null) {
            Sponsor sponsor = sponsorRepository.findById(dto.getSponsorId())
                    .orElseThrow(() -> new EntityNotFoundException("Sponsor con ID: " + dto.getSponsorId() + " no encontrado"));
            nuevoEvento.setSponsor(sponsor);
        }

        // Asignar cuenta bancaria si existe
        if (dto.getCuentaBancariaId() != null) {
            CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(dto.getCuentaBancariaId())
                    .orElseThrow(() -> new EntityNotFoundException("Cuenta bancaria con ID: " + dto.getCuentaBancariaId() + " no encontrada"));
            nuevoEvento.setCuentaBancaria(cuentaBancaria);
        }

        return eventoRepository.save(nuevoEvento);
    }

    public Evento updateEvento(EventoDto dto) {
        Evento eventoExistente = eventoRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Evento con ID: " + dto.getId() + " no encontrado"));

        // Actualizar campos básicos
        eventoExistente.setTipo(dto.getTipo());
        eventoExistente.setEstado(dto.getEstado());
        eventoExistente.setNombre(dto.getNombre());
        eventoExistente.setDescripcion(dto.getDescripcion());
        eventoExistente.setRutaImg(dto.getRutaImg());
        eventoExistente.setDireccion(dto.getDireccion());
        eventoExistente.setHoraInicio(dto.getHoraInicio());
        eventoExistente.setHoraFin(dto.getHoraFin());
        eventoExistente.setPuntosAsistencia(dto.getPuntosAsistencia());
        eventoExistente.setCostoInterno(dto.getCostoInterno());
        eventoExistente.setCostoInscripcion(dto.getCostoInscripcion());

        // Actualizar provincia
        Provincia provincia = provinciaRepository.findById(dto.getProvinciaId())
                .orElseThrow(() -> new EntityNotFoundException("Provincia con ID: " + dto.getProvinciaId() + " no encontrada"));
        eventoExistente.setProvincia(provincia);

        // Actualizar organizador
        Usuario organizador = usuarioRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + dto.getOrganizadorId() + " no encontrado"));
        eventoExistente.setOrganizador(organizador);

        // Actualizar sponsor si existe
        if (dto.getSponsorId() != null) {
            Sponsor sponsor = sponsorRepository.findById(dto.getSponsorId())
                    .orElseThrow(() -> new EntityNotFoundException("Sponsor con ID: " + dto.getSponsorId() + " no encontrado"));
            eventoExistente.setSponsor(sponsor);
        } else {
            eventoExistente.setSponsor(null);
        }

        // Actualizar cuenta bancaria si existe
        if (dto.getCuentaBancariaId() != null) {
            CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(dto.getCuentaBancariaId())
                    .orElseThrow(() -> new EntityNotFoundException("Cuenta bancaria con ID: " + dto.getCuentaBancariaId() + " no encontrada"));
            eventoExistente.setCuentaBancaria(cuentaBancaria);
        } else {
            eventoExistente.setCuentaBancaria(null);
        }

        return eventoRepository.save(eventoExistente);
    }

    public void deleteEventoById(Integer id) {
        if (!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("Evento con ID: " + id + " no encontrado");
        }
        eventoRepository.deleteById(id);
    }
}
package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.SponsorDto;
import com.raicesvivas.backend.models.entities.Sponsor;
import com.raicesvivas.backend.repositories.SponsorRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SponsorService {
    private final SponsorRepository sponsorRepository;
    private final ModelMapper modelMapper;

    public List<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    public Sponsor getSponsorById(int id) {
        return sponsorRepository.findById(id).get();
    }
    public Sponsor saveSponsor(SponsorDto sponsorDto) {
        return sponsorRepository.save(modelMapper.map(sponsorDto, Sponsor.class));
    }
    public Sponsor updateSponsor(SponsorDto sponsorDto){
        Sponsor sponsor = modelMapper.map(sponsorDto, Sponsor.class);
        return sponsorRepository.save(sponsor);
    }
    public int deleteSponsorById(int id) {
        sponsorRepository.deleteById(id);
        return id;
    }
}

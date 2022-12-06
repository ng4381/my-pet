package com.nservices.mypet.service;

import com.nservices.mypet.dto.PetStatesMementoDTO;
import com.nservices.mypet.entity.PetStatesMementoEntity;
import com.nservices.mypet.exception.PetStatesMementoNotFoundException;
import com.nservices.mypet.mapper.PetStatesMementoMapper;
import com.nservices.mypet.repository.PetStatesMementoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetStatesMementoService {

    private final PetStatesMementoMapper petStatesMementoMapper;
    private final PetStatesMementoRepository petStatesMementoRepository;

    public PetStatesMementoDTO getCurrentStatesDto(Long petId) {
        PetStatesMementoEntity mementoEntity = petStatesMementoRepository.findByPetId(petId);
        if (mementoEntity == null) {
            throw new PetStatesMementoNotFoundException("State for pet(id: " + petId + ") not found!");
        }
        return petStatesMementoMapper.EntityToObject(mementoEntity);
    }

    public PetStatesMementoEntity savePetStatesMemento(PetStatesMementoEntity petStatesMemento) {
        return petStatesMementoRepository.save(petStatesMemento);
    }
}

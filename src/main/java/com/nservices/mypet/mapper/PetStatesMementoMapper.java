package com.nservices.mypet.mapper;

import com.nservices.mypet.dto.PetStatesMementoDTO;
import com.nservices.mypet.entity.PetStatesMementoEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PetStatesMementoMapper implements IDtoMapper<PetStatesMementoDTO, PetStatesMementoEntity>{
    @Override
    public PetStatesMementoEntity ObjectToEntity(PetStatesMementoDTO object) {
        return null;
    }

    @Override
    public PetStatesMementoDTO EntityToObject(PetStatesMementoEntity entity) {
        return new PetStatesMementoDTO(
                entity.getId(),
                new ArrayList<>(entity.getPetStates()),
                entity.getDateTimeStamp()
        );
    }
}

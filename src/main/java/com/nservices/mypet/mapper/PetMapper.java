package com.nservices.mypet.mapper;

import com.nservices.mypet.dto.PetDTO;
import com.nservices.mypet.entity.PetEntity;
import org.springframework.stereotype.Component;


@Component
public class PetMapper implements IDtoMapper<PetDTO, PetEntity> {
    @Override
    public PetEntity ObjectToEntity(PetDTO object) {
        return null;
    }

    @Override
    public PetDTO EntityToObject(PetEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PetDTO(
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getBirthDate()
        );
    }
}

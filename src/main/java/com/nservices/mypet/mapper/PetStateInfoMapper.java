package com.nservices.mypet.mapper;

import com.nservices.mypet.dto.PetStateInfoDTO;
import com.nservices.mypet.entity.PetStateInfoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class PetStateInfoMapper implements IDtoMapper<PetStateInfoDTO, PetStateInfoEntity>{
    @Override
    public PetStateInfoEntity ObjectToEntity(PetStateInfoDTO object) {
        return null;
    }

    @Override
    public PetStateInfoDTO EntityToObject(PetStateInfoEntity entity) {
        long petAgeInDays = ChronoUnit.DAYS.between(entity.getPet().getBirthDate(), LocalDateTime.now());
        return new PetStateInfoDTO(entity.getPet().getName(), (int) petAgeInDays, entity.getState().name(), entity.getMinutes());
    }
}

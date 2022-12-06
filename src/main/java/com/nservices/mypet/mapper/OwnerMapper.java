package com.nservices.mypet.mapper;

import com.nservices.mypet.dto.OwnerDTO;
import com.nservices.mypet.entity.OwnerEntity;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper implements IDtoMapper<OwnerDTO, OwnerEntity>{
    @Override
    public OwnerEntity ObjectToEntity(OwnerDTO object) {
        return null;
    }

    @Override
    public OwnerDTO EntityToObject(OwnerEntity entity) {
        return new OwnerDTO(
                entity.getId(),
                entity.getName(),
                entity.getName()
        );
    }
}

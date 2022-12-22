package com.nservices.mypet.mapper;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.entity.FriendEntity;
import org.springframework.stereotype.Component;

@Component
public class FriendMapper implements IDtoMapper<FriendDto, FriendEntity>{
    @Override
    public FriendEntity ObjectToEntity(FriendDto object) {
        return null;
    }

    @Override
    public FriendDto EntityToObject(FriendEntity entity) {
        throw new UnsupportedOperationException("This operation doesn't supported anymore. Entity to object");
    }
}

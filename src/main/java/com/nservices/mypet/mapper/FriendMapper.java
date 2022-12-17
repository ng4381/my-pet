package com.nservices.mypet.mapper;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.entity.FriendEntity;

public class FriendMapper implements IDtoMapper<FriendDto, FriendEntity>{
    @Override
    public FriendEntity ObjectToEntity(FriendDto object) {
        return null;
    }

    @Override
    public FriendDto EntityToObject(FriendEntity entity) {
        return new FriendDto(
                entity.getUser().getUsername(),
                entity.getFriend().getUsername(),
                entity.getConfirmed()
        );
    }
}

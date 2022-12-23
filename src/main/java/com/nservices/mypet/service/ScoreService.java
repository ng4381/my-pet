package com.nservices.mypet.service;

import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final OwnerService ownerService;
    public void incrementFriendshipScore(int amount, String username) {
        OwnerEntity owner = ownerService.getOwner(username);
        Score score = owner.getScore();
        score.setFriendshipScore(score.getFriendshipScore() + amount);
        score.updateTotalScore();
        ownerService.save(owner);
    }

    public void incrementPersonalScore(int amount, String username) {
        OwnerEntity owner = ownerService.getOwner(username);
        Score score = owner.getScore();
        score.setPersonalScore(score.getPersonalScore() + amount);
        score.updateTotalScore();
        ownerService.save(owner);
    }
}

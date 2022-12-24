package com.nservices.mypet.service;

import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.Score;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nservices.mypet.util.TestConstants.OWNER1_NAME;
import static com.nservices.mypet.util.TestConstants.USER1_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScoreServiceTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private ScoreService scoreService;

    @Test
    public void shouldIncrementFriendshipScore() {
        OwnerEntity owner = OwnerEntity.builder()
                .name(OWNER1_NAME)
                .score(new Score(100, 100, 200))
                .build();
        given(ownerService.getOwner(any())).willReturn(owner);
        scoreService.incrementFriendshipScore(100, USER1_NAME);
        Assertions.assertThat(owner.getScore())
                .hasFieldOrPropertyWithValue("friendshipScore", 200)
                .hasFieldOrPropertyWithValue("totalScore", 300);
        verify(ownerService).save(any());
    }

    @Test
    public void shouldIncrementPersonalScore() {
        OwnerEntity owner = OwnerEntity.builder()
                .name(OWNER1_NAME)
                .score(new Score(100, 100, 200))
                .build();
        given(ownerService.getOwner(any())).willReturn(owner);
        scoreService.incrementPersonalScore(100, USER1_NAME);
        Assertions.assertThat(owner.getScore())
                .hasFieldOrPropertyWithValue("personalScore", 200)
                .hasFieldOrPropertyWithValue("totalScore", 300);
        verify(ownerService).save(any());
    }
}

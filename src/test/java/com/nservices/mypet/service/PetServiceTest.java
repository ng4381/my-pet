package com.nservices.mypet.service;

import com.nservices.mypet.dto.PetDTO;
import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.exception.OnlyFriendCanChangeStateException;
import com.nservices.mypet.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static com.nservices.mypet.util.TestConstants.USER1_NAME;
import static com.nservices.mypet.util.TestConstants.USER2_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class PetServiceTest {

    @InjectMocks
    private PetService petService;
    @Mock
    private PetStatesMementoService petStatesMementoService;
    @Mock
    private OwnerService ownerService;
    @Mock
    private PetRepository petRepository;

    @Mock
    private PetStateInfoService petStateInfoService;

    @Test
    public void shouldThrowExceptionWhenUserResetsFriendOnlyState() {
        given(petRepository.findByUsername(anyString())).willReturn(new PetEntity());
        PetStateInfoEntity petStateInfo = new PetStateInfoEntity();
        petStateInfo.setFriendOnly(1);
        given(petStateInfoService.getPetStateInfo(any(), any())).willReturn(petStateInfo);

        Assertions.assertThatThrownBy(() -> petService.resetState(USER1_NAME, "DIRTY"));
    }

    private PetStateInfoEntity preResetMock(int friendOnly) {
        given(petRepository.findByUsername(anyString())).willReturn(new PetEntity());
        PetStateInfoEntity petStateInfo = new PetStateInfoEntity();
        petStateInfo.setActive(1);
        petStateInfo.setLastModification(LocalDateTime.now());
        petStateInfo.setStart(LocalDateTime.now());
        petStateInfo.setMinutes(80);
        petStateInfo.setFriendOnly(friendOnly);
        petStateInfo.setStart(LocalDateTime.of(0,1,1,0,0));
        given(petStateInfoService.getPetStateInfo(any(), any())).willReturn(petStateInfo);
        return petStateInfo;
    }
    @Test
    public void shouldResetPedState() {
        PetStateInfoEntity petStateInfo = preResetMock(0);
        petService.resetState(USER1_NAME, "DIRTY");
        Assertions.assertThat(petStateInfo)
                .hasFieldOrPropertyWithValue("active", 0)
                .hasFieldOrPropertyWithValue("minutes", 0L);
    }

    @Test
    public void shouldResetFriendPedState() {
        PetStateInfoEntity petStateInfo = preResetMock(1);
        petService.resetFriendsState("", USER1_NAME, "DIRTY");
        Assertions.assertThat(petStateInfo)
                .hasFieldOrPropertyWithValue("active", 0)
                .hasFieldOrPropertyWithValue("minutes", 0L);
    }

    @Test
    public void shouldThrowExceptionWhenUserResetsFriendsNormalState() {
        given(petRepository.findByUsername(anyString())).willReturn(new PetEntity());
        PetStateInfoEntity petStateInfo = new PetStateInfoEntity();
        petStateInfo.setFriendOnly(0);
        given(petStateInfoService.getPetStateInfo(any(), any())).willReturn(petStateInfo);

        Assertions.assertThatThrownBy(() -> petService.resetFriendsState("", USER1_NAME, "DIRTY"));
    }
}
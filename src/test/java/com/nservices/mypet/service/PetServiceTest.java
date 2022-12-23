package com.nservices.mypet.service;

import com.nservices.mypet.dto.PetDTO;
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

    @Test
    @DisplayName("when pet states memento repository fails then transaction rolls back")
    public void testSavePetTransactionRollBack() {
        //Mockito.when(ownerService.getOwner(Mockito.anyString())).thenThrow(RuntimeException.class);
        Assertions.assertThatThrownBy(() ->
                petService.savePet(new PetDTO(1L, "owner", 15, LocalDateTime.now()), "nik"));
    }

    @Test
    public void shouldThrowExceptionWhenUserResetsFriendOnlyState() {
        Assertions.fail("not implemented");
    }

    @Test
    public void shouldResetFriendsPedState() {
        petService.resetFriendsState(USER2_NAME, "DIRTY");
        Assertions.fail("not implemented");
    }

    @Test
    public void shouldThrowExceptionWhenUserResetsFriendsNormalState() {
        Assertions.fail("not implemented");
    }
}
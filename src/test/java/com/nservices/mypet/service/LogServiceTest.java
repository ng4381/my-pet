package com.nservices.mypet.service;

import com.nservices.mypet.dto.ILogDto;
import com.nservices.mypet.dto.LogDto;
import com.nservices.mypet.entity.LogEntity;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.repository.LogRepository;
import com.nservices.mypet.repository.OwnerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.nservices.mypet.util.TestConstants.USER1_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LogServiceTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private OwnerService ownerService;
    @InjectMocks
    private PetLogService petLogService;

    @Test
    public void shouldSaveLog() {
        given(ownerService.getOwner(anyString())).willReturn(new OwnerEntity());
        petLogService.saveLog(USER1_NAME, "test message");
        Mockito.verify(logRepository).save(any());
    }

    @Test
    public void shouldReturnAllLogs() {
        given(logRepository.findAllLogsByUsername(anyString())).willReturn(List.of(
                new LogDto("message_1"),
                new LogDto("message_2")
        ));
        List<ILogDto> logDtoList = petLogService.getAllLogsByUsername(USER1_NAME);
        Assertions.assertThat(logDtoList).hasSize(2);
    }
}

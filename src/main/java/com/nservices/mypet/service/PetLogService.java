package com.nservices.mypet.service;

import com.nservices.mypet.dto.ILogDto;
import com.nservices.mypet.dto.LogDto;
import com.nservices.mypet.entity.LogEntity;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetLogService {
    private final OwnerService ownerService;
    private final LogRepository logRepository;

    public LogEntity saveLog(String username, String message) {
        OwnerEntity owner = ownerService.getOwner(username);
        return logRepository.save(new LogEntity(owner, message));
    }

    public List<ILogDto> getAllLogsByUsername(String username) {
        return logRepository.findAllLogsByUsername(username);
    }
}
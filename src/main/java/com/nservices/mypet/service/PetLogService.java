package com.nservices.mypet.service;

import com.nservices.mypet.dto.ILogDto;
import com.nservices.mypet.dto.LogDto;
import com.nservices.mypet.entity.LogEntity;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetLogService {
    private final OwnerService ownerService;
    private final LogRepository logRepository;

    public LogEntity saveLog(String username, String message) {
        OwnerEntity owner = ownerService.getOwner(username);
        return logRepository.save(new LogEntity(owner, message, LocalDateTime.now()));
    }

    public LogEntity generateAndSavePetStateLog(String username, int friendOnly, String state, List<String> possibleMessages) {
        if (friendOnly == 0) {
            return saveLog(username, "[PetState] Your pet became " + state);
        }
        int maxIdx = possibleMessages.size()-1;
        int rndPosition = (int)(Math.random() * maxIdx);
        return saveLog(username, possibleMessages.get(rndPosition));
    }

    public List<ILogDto> getAllLogsByUsername(String username) {
//        return logRepository.findAllLogsByUsername(username).stream()
//                .map(iLogDto -> new LogDto(iLogDto.getMessage())).limit(20).collect(Collectors.toList());

        return logRepository.findAllLogsByUsername(username, PageRequest.of(0, 20)).stream()
                .map(iLogDto -> new LogDto(iLogDto.getMessage(), iLogDto.getDateTime())).collect(Collectors.toList());
    }
}

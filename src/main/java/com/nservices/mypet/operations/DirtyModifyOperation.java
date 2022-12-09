package com.nservices.mypet.operations;

import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.service.PetStateInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class DirtyModifyOperation implements IModifyOperation {
    private final PetStateInfoService petStateInfoService;
    public static final Integer DEATH_TIME_MINUTES = 1000;
    public static final Integer DIRTY_TIME_MINUTES = 5;

    @Override
    public void execute(PetEntity pet) {

        PetStateInfoEntity petStateInfo = petStateInfoService.getPetStateInfo(pet.getId(), PetState.DIRTY);
        long diffFromStart = ChronoUnit.MINUTES.between(petStateInfo.getStart(), LocalDateTime.now());
        long diffFromLastModification = ChronoUnit.MINUTES.between(petStateInfo.getStart(), LocalDateTime.now());
        if (diffFromStart >= DEATH_TIME_MINUTES && petStateInfo.getActive() == 1) {
            log.info(String.format("[%s] DEAD", pet.getName()));
        }

        if (petStateInfo.getActive() == 1) {
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setMinutes(diffFromStart);
            log.info(String.format("[%s] Still dirty", pet.getName()));
        }

        if (diffFromLastModification >= DIRTY_TIME_MINUTES && petStateInfo.getActive() == 0) {
            petStateInfo.setActive(1);
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setStart(LocalDateTime.now());
            petStateInfo.setMinutes(0);
            log.info(String.format("[%s] Become dirty", pet.getName()));
        }

        petStateInfoService.savePetStateInfo(petStateInfo);
    }
}

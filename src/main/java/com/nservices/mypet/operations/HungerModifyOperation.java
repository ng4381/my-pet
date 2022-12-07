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
public class HungerModifyOperation implements IModifyOperation {
    private final PetStateInfoService petStateInfoService;
    public static final Integer DEATH_TIME_MINUTES = 1000;
    public static final Integer HUNGRY_TIME_MINUTES = 3;
    /**
     * eat every (4-6) hours
     * if more than 24 hours, then die
     * 0 min if not hungry
     */
    @Override
    public void execute(PetEntity pet) {

        PetStateInfoEntity petStateInfo = petStateInfoService.getPetStateInfo(pet.getId(), PetState.HUNGRY);
        long diffFromStart = ChronoUnit.MINUTES.between(petStateInfo.getStart(), LocalDateTime.now());
        long diffFromLastModification = ChronoUnit.MINUTES.between(petStateInfo.getStart(), LocalDateTime.now());
        if (diffFromStart >= DEATH_TIME_MINUTES) {
            log.info(String.format("[%s] DEAD", pet.getName()));
        }

        if (diffFromLastModification >= HUNGRY_TIME_MINUTES && petStateInfo.getActive() == 0) {
            petStateInfo.setActive(1);
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setStart(LocalDateTime.now());
            petStateInfo.setMinutes(0);
            log.info(String.format("[%s] Become hungry", pet.getName()));
        }

        if (petStateInfo.getActive() == 1) {
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setMinutes(diffFromStart);
            log.info(String.format("[%s] Still hungry", pet.getName()));
        }

        petStateInfoService.savePetStateInfo(petStateInfo);

    }
}

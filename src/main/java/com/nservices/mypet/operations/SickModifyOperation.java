package com.nservices.mypet.operations;

import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.service.PetLogService;
import com.nservices.mypet.service.PetStateInfoService;
import com.nservices.mypet.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SickModifyOperation implements IModifyOperation {
    private final PetStateInfoService petStateInfoService;
    private final PetLogService petLogService;

    private final ScoreService scoreService;
    public static final Integer DEATH_TIME_MINUTES = 1000;
    public static final Integer CHANCE_TO_SICK = 30;

    @Override
    public void execute(PetEntity pet) {
        String username = pet.getOwner().getUser().getUsername();

        PetStateInfoEntity petStateInfo = petStateInfoService.getPetStateInfo(pet.getId(), PetState.SICK);
        long diffFromStart = ChronoUnit.MINUTES.between(petStateInfo.getStart(), LocalDateTime.now());
        long diffFromLastModification = ChronoUnit.MINUTES.between(petStateInfo.getStart(), LocalDateTime.now());
        if (diffFromStart >= DEATH_TIME_MINUTES && petStateInfo.getActive() == 1) {
            log.info(String.format("[%s] DEAD", pet.getName()));
        }

        if (petStateInfo.getActive() == 1) {
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setMinutes(diffFromStart);
//            int score = -3;
//            scoreService.incrementPersonalScore(score, username);
//            petLogService.saveLog(username, "[PetState] Your pet still sick [Score] (-" + score + ")");
            log.info(String.format("[%s] Still sick", pet.getName()));
        }

        boolean isSick = Math.random() * 100 > 100 - CHANCE_TO_SICK;

        if (isSick && petStateInfo.getActive() == 0) {
            petStateInfo.setActive(1);
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setStart(LocalDateTime.now());
            petStateInfo.setMinutes(0);
            petStateInfo.setFriendOnly(Math.random() < 0.5 ? 0 : 1);
            String petName = pet.getName();
            petLogService.generateAndSavePetStateLog(
                    username,
                    petStateInfo.getFriendOnly(),
                    "SICK",
                    List.of(
                            String.format("[PetState] You went to a cinema. [%s] is sick. Ask your friend to heal [%s]", petName, petName),
                            String.format("[PetState] You went to a zoo. [%s] is sick. Ask your friend to heal [%s]", petName, petName)
                    )
            );
            log.info(String.format("[%s] Become sick", pet.getName()));
        }

        petStateInfoService.savePetStateInfo(petStateInfo);
    }
}

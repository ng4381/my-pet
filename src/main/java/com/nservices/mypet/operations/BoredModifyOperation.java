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
public class BoredModifyOperation implements IModifyOperation {
    private final PetStateInfoService petStateInfoService;
    private final PetLogService petLogService;

    private final ScoreService scoreService;
    public static final Integer DEATH_TIME_MINUTES = 1000;
    public static final Integer BORED_TIME_MINUTES = 3;

    @Override
    public void execute(PetEntity pet) {

        String username = pet.getOwner().getUser().getUsername();

        PetStateInfoEntity petStateInfo = petStateInfoService.getPetStateInfo(pet.getId(), PetState.BORED);
        long diffFromStart = ChronoUnit.MINUTES.between(petStateInfo.getStart(), LocalDateTime.now());
        long diffFromLastModification = ChronoUnit.MINUTES.between(petStateInfo.getLastModification(), LocalDateTime.now());
        if (diffFromStart >= DEATH_TIME_MINUTES && petStateInfo.getActive() == 1) {
            log.info(String.format("[%s] DEAD", pet.getName()));
        }

        if (petStateInfo.getActive() == 1) {
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setMinutes(diffFromStart);
//            int score = -1;
//            scoreService.incrementPersonalScore(score, username);
//            petLogService.saveLog(username, "[PetState] Your pet still bored [Score] (-" + score + ")");
            log.info(String.format("[%s] Still bored", pet.getName()));
        }

        if (diffFromLastModification >= BORED_TIME_MINUTES && petStateInfo.getActive() == 0) {
            petStateInfo.setActive(1);
            petStateInfo.setLastModification(LocalDateTime.now());
            petStateInfo.setStart(LocalDateTime.now());
            petStateInfo.setMinutes(0);
            petStateInfo.setFriendOnly(Math.random() < 0.5 ? 0 : 1);
            log.info(String.format("[%s] Become bored", pet.getName()));
            String petName = pet.getName();
            petLogService.generateAndSavePetStateLog(
                    username,
                    petStateInfo.getFriendOnly(),
                    "BORED",
                    List.of(
                            String.format("[PetState] Your pet wants to play a ball. [%s] is bored. Ask your friend to play with [%s]", petName, petName),
                            String.format("[PetState] Your pet is all alone at home. [%s] is bored. Ask your friend to visit [%s]", petName, petName),
                            String.format("[PetState] Your pet wants to go for a walk. [%s] is bored. Ask your friend to walk with [%s]", petName, petName)
                    )
            );
        }


        petStateInfoService.savePetStateInfo(petStateInfo);
    }
}

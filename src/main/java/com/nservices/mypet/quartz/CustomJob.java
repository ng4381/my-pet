package com.nservices.mypet.quartz;

import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.operations.IModifyOperation;
import com.nservices.mypet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
@RequiredArgsConstructor
@Slf4j
public class CustomJob implements Job {
    private final List<IModifyOperation> operations;

    private final PetService petService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info(operations.toString());
        List<PetEntity> pets = petService.getAllAlivePets();
        for (PetEntity pet : pets) {
            operations.forEach(iModifyOperation -> {
                iModifyOperation.execute(pet);
            });
        }
    }
}

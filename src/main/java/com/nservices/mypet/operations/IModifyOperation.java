package com.nservices.mypet.operations;

import com.nservices.mypet.dto.PetDTO;
import com.nservices.mypet.entity.PetEntity;

public interface IModifyOperation {
    void execute(PetEntity pet);
}

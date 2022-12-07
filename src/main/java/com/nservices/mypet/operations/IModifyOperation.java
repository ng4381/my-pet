package com.nservices.mypet.operations;

import com.nservices.mypet.entity.PetEntity;

public interface IModifyOperation {
    void execute(PetEntity pet);
}

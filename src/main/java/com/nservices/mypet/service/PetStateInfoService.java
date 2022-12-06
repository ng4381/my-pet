package com.nservices.mypet.service;

import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.repository.PetStateInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetStateInfoService {

    private final PetStateInfoRepository petStateInfoRepository;

    public PetStateInfoEntity savePetStateInfo(PetStateInfoEntity petStateInfo) {
        return petStateInfoRepository.save(petStateInfo);
    }

    public PetStateInfoEntity getPetStateInfo(Long petId, PetState state) {
        return petStateInfoRepository.findByPetIdAndState(petId, state);
    }
}

package com.nservices.mypet.service;

import com.nservices.mypet.dto.PetStateInfoDTO;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.mapper.PetStateInfoMapper;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.repository.PetStateInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetStateInfoService {

    private final PetStateInfoRepository petStateInfoRepository;

    private final PetStateInfoMapper petStateInfoMapper;
    public PetStateInfoEntity savePetStateInfo(PetStateInfoEntity petStateInfo) {
        return petStateInfoRepository.save(petStateInfo);
    }

    public PetStateInfoEntity getPetStateInfo(Long petId, PetState state) {
        return petStateInfoRepository.findByPetIdAndState(petId, state);
    }

    public List<PetStateInfoDTO> getUserPetStateInfo(String username) {
        List<PetStateInfoEntity> petStateInfoEntities = petStateInfoRepository.findAllByUsername(username);
        return petStateInfoEntities.stream().map(petStateInfoEntity ->
            petStateInfoMapper.EntityToObject(petStateInfoEntity)
        ).collect(Collectors.toList());
    }
}

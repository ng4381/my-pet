package com.nservices.mypet.repository;

import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.model.PetState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetStateInfoRepository extends JpaRepository<PetStateInfoEntity, Long> {
    PetStateInfoEntity findByPetIdAndState(Long id, PetState state);
}

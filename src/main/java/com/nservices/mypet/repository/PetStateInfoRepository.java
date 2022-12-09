package com.nservices.mypet.repository;

import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.model.PetState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetStateInfoRepository extends JpaRepository<PetStateInfoEntity, Long> {
    PetStateInfoEntity findByPetIdAndState(Long id, PetState state);

    @Query("SELECT psi FROM PetStateInfoEntity psi INNER JOIN PetEntity p ON p.id = psi.pet.id INNER JOIN OwnerEntity o ON o.id = p.owner INNER JOIN User u ON u.id = o.user.id WHERE u.username = :username ")
    List<PetStateInfoEntity> findAllByUsername(String username);
}

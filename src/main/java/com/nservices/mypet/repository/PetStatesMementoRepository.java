package com.nservices.mypet.repository;

import com.nservices.mypet.entity.PetStatesMementoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetStatesMementoRepository extends JpaRepository<PetStatesMementoEntity, Long> {
    PetStatesMementoEntity findByPetId(Long id);
}

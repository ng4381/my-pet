package com.nservices.mypet.repository;

import com.nservices.mypet.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    @Query("select p from PetEntity p LEFT JOIN p.owner o LEFT JOIN o.user u WHERE u.username = :username")
    public PetEntity findByUsername(String username);
}

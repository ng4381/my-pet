package com.nservices.mypet.repository;

import com.nservices.mypet.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
    @Query(value = "SELECT o FROM OwnerEntity o INNER JOIN FETCH o.user u WHERE u.username = :username")
    OwnerEntity findByUsername(@Param("username") String username);
}

package com.nservices.mypet.repository;

import com.nservices.mypet.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    List<FriendEntity> findAllByUserUsername(String username);
}

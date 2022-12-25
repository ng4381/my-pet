package com.nservices.mypet.repository;

import com.nservices.mypet.dto.ILogDto;
import com.nservices.mypet.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

    @Query(value = "SELECT l.message as message FROM LogEntity l LEFT JOIN OwnerEntity o ON l.owner.id = o.id LEFT JOIN User u ON u.id = o.user.id WHERE u.username = :username ORDER BY l.id DESC")
    List<ILogDto> findAllLogsByUsername(String username);

}

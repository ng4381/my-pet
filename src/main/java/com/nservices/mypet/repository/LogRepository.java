package com.nservices.mypet.repository;

import com.nservices.mypet.dto.ILogDto;
import com.nservices.mypet.entity.LogEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends PagingAndSortingRepository<LogEntity, Long> {

    @Query(value = "SELECT l.message as message, l.dateTime as dateTime FROM LogEntity l LEFT JOIN OwnerEntity o ON l.owner.id = o.id LEFT JOIN User u ON u.id = o.user.id WHERE u.username = :username ORDER BY l.dateTime DESC")
    List<ILogDto> findAllLogsByUsername(String username, Pageable pageable);

}

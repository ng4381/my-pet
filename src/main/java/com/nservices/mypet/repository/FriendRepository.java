package com.nservices.mypet.repository;

import com.nservices.mypet.dto.IFriendDto;
import com.nservices.mypet.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    String USER_FRIENDS_TO_IFRIEND_DTO =
            "f.confirmed as status, " +
            "p.name as petName, " +
            "p.age as petAge, " +
            "0 as friendshipScore, " +
            "0 as personalScore, " +
            "0 as totalScore " +
            "FROM FriendEntity f " +
            "LEFT JOIN OwnerEntity o ON f.friend.id = o.user.id " +
            "LEFT JOIN PetEntity p ON p.owner.id = o.id";

    String USER_FRIENDS_TO_IFRIEND_DTO_CONFIRMED_AND_WAITING_CONFIRMATION = "SELECT " +
            "f.friend.username as friendUsername, " +
            USER_FRIENDS_TO_IFRIEND_DTO;

    String USER_FRIENDS_TO_IFRIEND_DTO_UNCONFIRMED_FRIEND_REQUEST = "SELECT " +
            "f.user.username as friendUsername, " +
            USER_FRIENDS_TO_IFRIEND_DTO;

    List<FriendEntity> findAllByUserUsername(String username);

    FriendEntity findFriendByUserUsernameAndFriendUsername(String username, String friend_username);

    @Query(value = USER_FRIENDS_TO_IFRIEND_DTO_CONFIRMED_AND_WAITING_CONFIRMATION + " WHERE f.user.username = :username")
    List<IFriendDto> findAllFriendsWithStatusConfirmedAndWaitingConfirmation(String username);

    @Query(value = USER_FRIENDS_TO_IFRIEND_DTO_UNCONFIRMED_FRIEND_REQUEST + " WHERE f.friend.username = :username AND f.confirmed = 0")
    List<IFriendDto> findAllFriendsWithStatusUnconfirmedFriendRequest(String username);
}

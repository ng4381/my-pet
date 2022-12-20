package com.nservices.mypet.repository;

import com.nservices.mypet.dto.IFriendDto;
import com.nservices.mypet.entity.FriendEntity;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.nservices.mypet.repository.security.RoleConstants.ROLE_USER;
import static com.nservices.mypet.util.Constants.*;


@DataJpaTest
@Slf4j
class FriendRepositoryTest {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private User user1, user2, user3;
    private FriendEntity friend1, friend2;

    @BeforeEach
    private void init() {
        user1 = userRepository.save(new User(USER1_NAME, USER1_PASSWORD, 1, ROLE_USER));
        user2 = userRepository.save(new User(USER2_NAME, USER2_PASSWORD, 1, ROLE_USER));
        user3 = userRepository.save(new User(USER3_NAME, USER3_PASSWORD, 1, ROLE_USER));
        friend1 = friendRepository.save(new FriendEntity(user1, user2, 0));
        friend2 = friendRepository.save(new FriendEntity(user1, user3, 0));

        OwnerEntity owner1 = new OwnerEntity(OWNER1_NAME, EMAIL1, user1);
        ownerRepository.save(owner1);
        PetEntity pet1 = petRepository.save(new PetEntity(PET1_NAME, PET1_AGE, LocalDateTime.now(), owner1));
        petRepository.save(pet1);

        OwnerEntity owner2 = new OwnerEntity(OWNER2_NAME, EMAIL2, user2);
        ownerRepository.save(owner2);
        PetEntity pet2 = petRepository.save(new PetEntity(PET2_NAME, PET2_AGE, LocalDateTime.now(), owner2));
        petRepository.save(pet2);

        OwnerEntity owner3 = new OwnerEntity(OWNER3_NAME, EMAIL3, user3);
        ownerRepository.save(owner3);
        PetEntity pet3 = petRepository.save(new PetEntity(PET3_NAME, PET3_AGE, LocalDateTime.now(), owner3));
        petRepository.save(pet3);
    }

    @Test
    public void shouldSaveFriend() {
        Assertions.assertThat(friend1).extracting("user.username", "friend.username", "confirmed")
                .contains(USER1_NAME, USER2_NAME, 0);
        Assertions.assertThat(friend2).extracting("user.username", "friend.username", "confirmed")
                .contains(USER1_NAME, USER3_NAME, 0);
    }

    @Test
    public void shouldReturnAllFriends() {
        List<FriendEntity> friends = friendRepository.findAllByUserUsername(USER1_NAME);
        friends.forEach(friend -> System.out.println("User: " + friend.getUser() + " Friend: " + friend.getFriend()));
        Assertions.assertThat(friends).isNotEmpty();
    }

    @Test
    public void shouldReturnFriendByUserUsernameAndFriendUsername() {
        FriendEntity friendEntity = friendRepository.findFriendByUserUsernameAndFriendUsername(USER1_NAME, USER2_NAME);
        Assertions.assertThat(friendEntity).extracting("user.username", "friend.username")
                .contains(USER1_NAME, USER2_NAME);
    }

    @Test
    public void shouldReturnFriendsWithStatusConfirmedAndWaitingConfirmation() {
        List<IFriendDto> friends = friendRepository.findAllFriendsWithStatusConfirmedAndWaitingConfirmation(USER1_NAME);
        Assertions.assertThat(friends).filteredOn("friendUsername", USER2_NAME).isNotEmpty();
        friends.forEach(iFriendDto -> System.out.println(getFriendDtoDescription(iFriendDto)));
    }

    @Test
    public void shouldReturnFriendsWithStatusUnconfirmedFriendRequest() {
        List<IFriendDto> friends = friendRepository.findAllFriendsWithStatusUnconfirmedFriendRequest(USER1_NAME);
        Assertions.assertThat(friends).filteredOn("friendUsername", USER2_NAME).isNotEmpty();
        friends.forEach(iFriendDto -> System.out.println(getFriendDtoDescription(iFriendDto)));
    }

    private String getFriendDtoDescription(IFriendDto friendDto) {
        return "Friend username: " +
        friendDto.getFriendUsername() +
                "; Status: " +
        friendDto.getStatus() +
                "; Pet name: " +
        friendDto.getPetName() +
                "; Pet age: " +
        friendDto.getPetAge() +
                "; Friendship score: " +
        friendDto.getFriendshipScore() +
                "; personal score: " +
        friendDto.getPersonalScore() +
                "; total score: " +
        friendDto.getTotalScore();
    }

}
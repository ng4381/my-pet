package com.nservices.mypet.jpa;

import com.nservices.mypet.entity.*;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.repository.*;
import com.nservices.mypet.repository.security.RoleConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class JpaTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetStatesMementoRepository petStatesMementoRepository;

    @Autowired
    private PetStateInfoRepository petStateInfoRepository;

    private User user, user2;

    @BeforeEach
    public void init() {
        user = new User("nik", "1", 1, RoleConstants.ROLE_USER);
        user2 = new User("rita", "1", 1, RoleConstants.ROLE_USER);
    }

    @Test
    public void testOwnerRepository() {
        OwnerEntity owner = testEntityManager.persist(new OwnerEntity("nik", "nik@gmail.com", user));
        Assertions.assertThat(ownerRepository.findById(owner.getId()).get())
                .isNotNull()
                .extracting("name", "email")
                .containsExactly("nik", "nik@gmail.com");
    }

    @Test
    public void whenWrongEmailThenThrowException() {
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> testEntityManager.persist(new OwnerEntity("nik", "nikgmail.com", user)));
    }

    @Test
    public void testPetPersistence() {
        OwnerEntity owner = new OwnerEntity("nik", "nik@gmail.com", user);
        PetEntity pet = petRepository.save(new PetEntity("pups", 0, LocalDateTime.of(2022, 1, 1, 0, 0), owner));

        PetEntity savedPet = petRepository.save(pet);

        Assertions.assertThat(petRepository.findById(savedPet.getId()).get())
                .isNotNull()
                .extracting("name", "owner.name")
                .containsExactly("pups", "nik");
    }

    @Test
    public void testMementoPersistence() {
        OwnerEntity owner = new OwnerEntity("nik", "nik@gmail.com", user);
        PetEntity pet = new PetEntity("pups", 0, LocalDateTime.of(2022, 1, 1, 0, 0), owner);
        PetStatesMementoEntity memento = new PetStatesMementoEntity(pet, List.of(PetState.HUNGRY, PetState.SICK), LocalDateTime.now());
        PetStatesMementoEntity savedMemento = petStatesMementoRepository.save(memento);
        Assertions.assertThat(petStatesMementoRepository.findById(savedMemento.getId()).get())
                .isNotNull()
                .extracting("pet.name")
                .isEqualTo("pups");


    }

    @Test
    public void testMementoPersistenceWithFetch() {
        OwnerEntity owner = new OwnerEntity("nik", "nik@gmail.com", user);
        PetEntity pet = new PetEntity("pups", 0, LocalDateTime.of(2022, 1, 1, 0, 0), owner);
        PetStatesMementoEntity memento = new PetStatesMementoEntity(pet, List.of(PetState.HUNGRY, PetState.SICK), LocalDateTime.now());
        PetStatesMementoEntity savedMemento = petStatesMementoRepository.save(memento);
        Query query = testEntityManager.getEntityManager().createQuery("SELECT psm FROM PetStatesMementoEntity psm JOIN FETCH psm.petStates", PetStatesMementoEntity.class);
        PetStatesMementoEntity mementoFromDb = (PetStatesMementoEntity) query.getSingleResult();

        Assertions.assertThat(mementoFromDb.getPetStates())
                .contains(PetState.HUNGRY, PetState.SICK);
    }

    @Test
    public void testMementoPersistenceFindByPetId() {
        OwnerEntity owner = new OwnerEntity("nik", "nik@gmail.com", user);
        PetEntity pet = new PetEntity("pups", 0, LocalDateTime.of(2022, 1, 1, 0, 0), owner);
        LocalDateTime dateTime_1 = LocalDateTime.of(2022, 1, 1, 0, 0);
        PetStatesMementoEntity memento = new PetStatesMementoEntity(pet, List.of(PetState.HUNGRY, PetState.SICK), dateTime_1);
        System.out.println("Id = " + pet.getId());
        PetStatesMementoEntity savedMemento = petStatesMementoRepository.save(memento);

        System.out.println("Id = " + pet.getId());

        Assertions.assertThat(petStatesMementoRepository.findByPetId(pet.getId()))
                .extracting("dateTimeStamp").isEqualTo(dateTime_1);
    }

    @Test
    public void testJPQLforUserAndOwner() {
        userRepository.save(user);
        userRepository.save(user2);

        OwnerEntity owner2 = new OwnerEntity("rita_owner", "rita@gmail.com", user2);
        OwnerEntity owner = new OwnerEntity("nik_owner", "nik@gmail.com", user);
        ownerRepository.save(owner2);
        ownerRepository.save(owner);

        OwnerEntity ownerJpql = ownerRepository.findByUsername("nik");
        Assertions.assertThat(ownerJpql)
                .isNotNull()
                .extracting("name")
                .isEqualTo("nik_owner");


    }

    @Test
    public void testfindByUsernameAndPassword() {
        userRepository.save(user);
        userRepository.save(user2);

        Assertions.assertThat(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())).isNotNull();
    }
}

package com.nservices.mypet.entity;

import com.nservices.mypet.model.PetState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "petsmemento")
public class PetStatesMementoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pet_id")
    private PetEntity pet;
    @ElementCollection(targetClass = PetState.class)
    @JoinTable(name = "petstates", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private List<PetState> petStates;
    @Column(name = "dateTimeStamp")
    private LocalDateTime dateTimeStamp;

    public PetStatesMementoEntity(PetEntity pet, List<PetState> petStates, LocalDateTime dateTimeStamp) {
        this.pet = pet;
        this.petStates = petStates;
        this.dateTimeStamp = dateTimeStamp;
    }
}

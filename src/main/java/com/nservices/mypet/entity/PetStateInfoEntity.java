package com.nservices.mypet.entity;

import com.nservices.mypet.model.PetState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "petstateinfo")
@NoArgsConstructor
public class PetStateInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private PetState state;

    @Column(name = "last_modification")
    private LocalDateTime lastModification;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "minutes")
    private long minutes;

    @Column(name = "active")
    private int active;

    public PetStateInfoEntity(PetEntity pet, PetState state, LocalDateTime lastModification, LocalDateTime start, int minutes, int active) {
        this.pet = pet;
        this.state = state;
        this.lastModification = lastModification;
        this.minutes = minutes;
        this.start = start;
        this.active = active;
    }
}

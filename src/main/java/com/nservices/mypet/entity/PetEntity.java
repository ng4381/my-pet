package com.nservices.mypet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pets")
@NoArgsConstructor
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private int age;
    @Column(name = "birthDate")
    private LocalDateTime birthDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private OwnerEntity owner;

    @OneToMany(mappedBy = "pet")
    private Set<PetStateInfoEntity> petStatesInfo;


    public PetEntity(String name, int age, LocalDateTime birthDate, OwnerEntity owner) {
        this.name = name;
        this.age = age;
        this.birthDate = birthDate;
        this.owner = owner;
    }
}

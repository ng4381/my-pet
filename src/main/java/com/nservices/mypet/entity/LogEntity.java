package com.nservices.mypet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "logs")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    private String message;

    public OwnerEntity getOwner() {
        return owner;
    }

    public LogEntity(OwnerEntity owner, String message) {
        this.owner = owner;
        this.message = message;
    }
}

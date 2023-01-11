package com.nservices.mypet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "message")
    private String message;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public OwnerEntity getOwner() {
        return owner;
    }

    public LogEntity(OwnerEntity owner, String message, LocalDateTime dateTime) {
        this.owner = owner;
        this.message = message;
        this.dateTime = dateTime;
    }
}

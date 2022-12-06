package com.nservices.mypet.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity
@Table(name = "owners")
@NoArgsConstructor
public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // name = user_name
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Email
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public OwnerEntity(String name, String email, User user) {
        this.name = name;
        this.email = email;
        this.user = user;
    }
}

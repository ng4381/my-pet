package com.nservices.mypet.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password;
    private int active;
    private String roles = "";

    @OneToOne(mappedBy = "user")
    private OwnerEntity owner;

    public User(String username, String password, int active, String roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public List<String> getRoles() {
        if(roles.isEmpty() || roles == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(roles.split(", "));
    }
}

package com.nservices.mypet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "friends")
@Getter
@Setter
@NoArgsConstructor
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    /**
     * 0 - unconfirmed
     * 1 - confirmed
     */
    private int confirmed;

    public FriendEntity(User user, User friend, int confirmed) {
        this.user = user;
        this.friend = friend;
        this.confirmed = confirmed;
    }
}

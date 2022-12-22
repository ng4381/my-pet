package com.nservices.mypet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Score {
    @Column(name = "")
    private Integer FriendshipScore;
    private Integer PersonalScore;
    private Integer TotalScore;
}

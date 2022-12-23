package com.nservices.mypet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Column(name = "friendship_score")
    private int friendshipScore;

    @Column(name = "personal_score")
    private int personalScore;

    @Column(name = "total_score")
    private int totalScore;

    public void updateTotalScore() {
        totalScore = friendshipScore + personalScore;
    }
}

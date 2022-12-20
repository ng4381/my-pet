package com.nservices.mypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FriendDto {
    private String friendUsername;
    private Integer status;
    private String petName;
    private Integer petAge;
    private Integer friendshipScore;
    private Integer personalScore;
    private Integer totalScore;
    //List<String> petStates;

    public FriendDto(String friendUsername, Integer status, String petName, Integer petAge, Integer friendshipScore, Integer personalScore, Integer totalScore) {
        this.friendUsername = friendUsername;
        this.status = status;
        this.petName = petName;
        this.petAge = petAge;
        this.friendshipScore = friendshipScore;
        this.personalScore = personalScore;
        this.totalScore = totalScore;
    }
}

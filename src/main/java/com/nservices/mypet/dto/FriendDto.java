package com.nservices.mypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FriendDto {
    private String friendUsername;
    private String status;
    private String petName;
    private Integer petAge;
    private Integer friendshipScore;
    private Integer personalScore;
    private Integer totalScore;
    private List<String> petStates;

}

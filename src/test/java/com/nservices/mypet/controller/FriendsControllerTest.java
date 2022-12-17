package com.nservices.mypet.controller;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.service.FriendService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FriendsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendService friendsService;


    @Test
    public void shouldReturnCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/{username}", "Test User")).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void shouldInvokeSaveMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/{username}", "Test User")).andDo(print()).andExpect(status().isCreated());
        verify(friendsService).addUnconfirmedFriend(anyString(), anyString());
    }

    /*
    @Test
    public void shouldReturnAllFriends() throws Exception {

        when(principal.getName()).thenReturn("USER_1");

        when(friendsService.getUserFriendsDto(any())).thenReturn(List.of(
                new FriendDto("USER_1", "USER_2", 0),
                new FriendDto("USER_1", "USER_3", 0)
        ));
        mockMvc.perform(MockMvcRequestBuilders.get("/friends")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{'user':''}"));
    }

    @Test
    public void shouldConfirmAFriend() {

    }
     */

}

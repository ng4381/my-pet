package com.nservices.mypet.controller;

import com.nservices.mypet.service.FriendService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.nservices.mypet.util.Constants.USER1_NAME;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class FriendsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendService friendsService;

    @Test
    public void shouldAddUnconfirmedFriend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/{username}", USER1_NAME)).andDo(print()).andExpect(status().isCreated());
        verify(friendsService).addUnconfirmedFriend(anyString(), anyString());
    }


    @Test
    public void shouldReturnAllFriends() throws Exception {

        /*
        when(friendsService.getUserFriendsDto(any())).thenReturn(List.of(
                new FriendDto("USER_1", "USER_2", 0),
                new FriendDto("USER_1", "USER_3", 0)
        ));
        mockMvc.perform(MockMvcRequestBuilders.get("/friends")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"username\":\"USER_1\",\"friend_username\":\"USER_2\",\"confirmed\":0},{\"username\":\"USER_1\",\"friend_username\":\"USER_3\",\"confirmed\":0}]"));

         */

        Assertions.fail("TO DO");
    }


    @Test
    public void shouldConfirmAFriend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/confirm/{username}", USER1_NAME))
                .andDo(print())
                .andExpect(status().isOk());
        verify(friendsService).confirmFriend(anyString(), anyString());
    }


    @Test
    public void shouldDeleteFriend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/friends/{username}", USER1_NAME))
                .andDo(print())
                .andExpect(status().isOk());
        verify(friendsService).deleteFriendByUserUsernameFriendUsername(anyString(), anyString());
    }





}

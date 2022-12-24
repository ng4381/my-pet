package com.nservices.mypet.controller;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.repository.PetRepository;
import com.nservices.mypet.repository.PetStateInfoRepository;
import com.nservices.mypet.service.FriendService;
import com.nservices.mypet.service.PetService;
import com.nservices.mypet.service.PetStateInfoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.nservices.mypet.util.Constants.*;
import static com.nservices.mypet.util.TestConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
public class FriendsControllerTest {


    //@Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PetService petService;

    @MockBean
    private PetRepository petRepository;

    @InjectMocks
    private PetStateInfoService petStateInfoService;

    @MockBean
    private PetStateInfoRepository petStateInfoRepository;

    @MockBean
    private FriendService friendsService;

    private Principal mockPrincipal;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    public void shouldAddUnconfirmedFriend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/{username}", USER1_NAME)
                        .accept(MediaType.APPLICATION_JSON).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("shouldAddUnconfirmedFriend"));
        verify(friendsService).addUnconfirmedFriend(anyString(), anyString());
    }


    @Test
    public void shouldReturnAllFriends() throws Exception {

        when(friendsService.getUserFriendsDto(any())).thenReturn(List.of(
                new FriendDto(USER1_NAME, WAITING_FRIEND_CONFIRMATION, PET1_NAME, PET1_AGE, 0, 0, 0, new ArrayList<>()),
                new FriendDto(USER2_NAME, CONFIRMED, PET2_NAME, PET2_AGE, 0, 0, 0, new ArrayList<>()),
                new FriendDto(USER3_NAME, UNCONFIRMED_FRIEND_REQUEST, PET3_NAME, PET3_AGE, 0, 0, 0, new ArrayList<>())
        ));
        mockMvc.perform(MockMvcRequestBuilders.get("/friends")
                        .accept(MediaType.APPLICATION_JSON).principal(mockPrincipal))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"friendUsername\":\"USER_1\",\"status\":\"WAITING_FRIEND_CONFIRMATION\",\"petName\":\"PET_1\",\"petAge\":2,\"friendshipScore\":0,\"personalScore\":0,\"totalScore\":0,\"petStates\":[]},{\"friendUsername\":\"USER_2\",\"status\":\"CONFIRMED\",\"petName\":\"PET_2\",\"petAge\":4,\"friendshipScore\":0,\"personalScore\":0,\"totalScore\":0,\"petStates\":[]},{\"friendUsername\":\"USER_3\",\"status\":\"UNCONFIRMED_FRIEND_REQUEST\",\"petName\":\"PET_3\",\"petAge\":6,\"friendshipScore\":0,\"personalScore\":0,\"totalScore\":0,\"petStates\":[]}]"))
                .andDo(document("shouldReturnAllFriends"));

    }


    @Test
    public void shouldConfirmAFriend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/confirm/{username}", USER1_NAME)
                        .accept(MediaType.APPLICATION_JSON).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("shouldConfirmAFriend"));
        verify(friendsService).confirmFriend(anyString(), anyString());
    }


    @Test
    public void shouldDeleteFriend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/friends/{username}", USER1_NAME)
                .accept(MediaType.APPLICATION_JSON).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("shouldDeleteFriend"));;
        verify(friendsService).deleteFriendByUserUsernameFriendUsername(anyString(), anyString());
    }

    @Test
    public void shouldResetFriendsState() throws Exception {
        PetEntity pet = new PetEntity();
        pet.setId(1L);
        given(petRepository.findByUsername(anyString())).willReturn(pet);

        PetStateInfoEntity petStateInfo = new PetStateInfoEntity();
        petStateInfo.setFriendOnly(1);
        given(petStateInfoRepository.findByPetIdAndState(anyLong(), any())).willReturn(petStateInfo);
        //given(petStateInfoService.getPetStateInfo(anyLong(), any(PetState.class))).willReturn(new PetStateInfoEntity());
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/states/reset/{username}/{state}", USER1_NAME, "SICK")
                .accept(MediaType.APPLICATION_JSON).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("shouldResetFriendsState"));
    }

}

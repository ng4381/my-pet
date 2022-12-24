package com.nservices.mypet.controller;

import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.repository.PetRepository;
import com.nservices.mypet.repository.PetStateInfoRepository;
import com.nservices.mypet.service.FriendService;
import com.nservices.mypet.service.PetService;
import com.nservices.mypet.service.PetStateInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static com.nservices.mypet.util.TestConstants.USER1_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
class PetControllerTest {
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
    void shouldResetPetState() throws Exception {
        PetEntity pet = new PetEntity();
        pet.setId(1L);
        given(petRepository.findByUsername(anyString())).willReturn(pet);

        PetStateInfoEntity petStateInfo = new PetStateInfoEntity();
        petStateInfo.setFriendOnly(0);
        given(petStateInfoRepository.findByPetIdAndState(anyLong(), any())).willReturn(petStateInfo);
        mockMvc.perform(MockMvcRequestBuilders.post("/pets/states/reset/{state}", "SICK")
                        .accept(MediaType.APPLICATION_JSON).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("shouldResetUsersState"));
    }
}
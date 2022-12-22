package com.nservices.mypet.controller;

import com.nservices.mypet.dto.OwnerDTO;
import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.service.OwnerService;
import com.nservices.mypet.service.PetLogService;
import com.nservices.mypet.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static com.nservices.mypet.util.TestConstants.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class LogControllerTest {
    private MockMvc mockMvc;

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
    public void shouldReturnAllLogsByUsername() throws Exception {

        // TODO  refactor the code. Avoid cascadeType from User and Owner and overs

        this.mockMvc.perform(MockMvcRequestBuilders.get("/logs")
                .accept(MediaType.APPLICATION_JSON).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andDo(document("shouldReturnAllLogsByUsername"));


    }
}

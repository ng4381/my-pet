package com.nservices.mypet.controller;

import com.nservices.mypet.dto.PetStateInfoDTO;
import com.nservices.mypet.dto.PetStatesMementoDTO;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.service.PetStateInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/pets/states")
@RequiredArgsConstructor
public class PetStatesController {
    private final PetStateInfoService petStateInfoService;

    @GetMapping
    public ResponseEntity<List<PetStateInfoDTO>> getPetStateInfo(Principal principal) {
        List<PetStateInfoDTO> petStateInfoDTOS = petStateInfoService.getUserPetStateInfo(principal.getName());
        return ResponseEntity.ok(petStateInfoDTOS);
    }
}

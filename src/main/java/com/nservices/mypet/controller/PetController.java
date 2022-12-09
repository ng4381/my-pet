package com.nservices.mypet.controller;

import com.nservices.mypet.dto.PetDTO;
import com.nservices.mypet.mapper.PetMapper;
import com.nservices.mypet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/pets")
@RequiredArgsConstructor
@Slf4j
public class PetController {
    private final PetService petService;

    private final PetMapper petMapper;


    @GetMapping
    public ResponseEntity<PetDTO> getPet(Principal principal) {
        //TODO ned to return petDTO
        return ResponseEntity.ok(petMapper.EntityToObject(petService.getPet(principal.getName())));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> postPetAndOwner(@RequestBody PetDTO petDTO, Principal principal) {
        petService.savePet(petDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/states/reset/{state}")
    public ResponseEntity<HttpStatus> resetPetState(@PathVariable String state, Principal principal) {
        log.info(String.format("/states/reset/%s ... username: %s", state, principal.getName()));
        petService.resetState(principal.getName(), state);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

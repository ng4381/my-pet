package com.nservices.mypet.controller;

import com.nservices.mypet.dto.RegistryInfoDTO;
import com.nservices.mypet.service.RegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registry")
@RequiredArgsConstructor
@CrossOrigin
public class RegistryController {
    private final RegistryService registryService;

    @PostMapping
    public ResponseEntity<HttpStatus> registerPetAndUser(@RequestBody RegistryInfoDTO registryInfoDTO) {
        registryService.registerPetAndUser(registryInfoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

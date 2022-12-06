package com.nservices.mypet.controller;

import com.nservices.mypet.dto.PetStatesMementoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets/states")
public class PetStatesController {
    @GetMapping("/last/{user}")
    public ResponseEntity<PetStatesMementoDTO> getCurrentStates() {
        // TODO
        return ResponseEntity.ok(null);
    }
}

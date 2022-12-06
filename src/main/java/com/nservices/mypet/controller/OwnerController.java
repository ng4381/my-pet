package com.nservices.mypet.controller;

import com.nservices.mypet.dto.OwnerDTO;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.mapper.OwnerMapper;
import com.nservices.mypet.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @GetMapping
    public ResponseEntity<OwnerDTO> getOwner(Principal principal) {
        OwnerEntity owner = ownerService.getOwner(principal.getName());
        return ResponseEntity.ok(ownerMapper.EntityToObject(owner));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> postOwner(@RequestBody OwnerDTO ownerDTO,Principal principal) {
        ownerService.saveOwner(ownerDTO, principal.getName());
        return ResponseEntity.ok().build();
    }
}

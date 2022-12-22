package com.nservices.mypet.controller;

import com.nservices.mypet.dto.ILogDto;
import com.nservices.mypet.service.PetLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogsController {

    private final PetLogService logService;

    @GetMapping
    public ResponseEntity<List<ILogDto>> getAllLogsByUsername(Principal principal) {
        return ResponseEntity.ok(logService.getAllLogsByUsername(principal.getName()));
    }
}

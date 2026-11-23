package com.azim.carsales.controller;

import com.azim.carsales.service.AiQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    AiQueryService aiService;

    public AiController(AiQueryService aiQueryService) {
        this.aiService = aiQueryService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody String question) {
        return ResponseEntity.ok(aiService.process(question));
    }
}
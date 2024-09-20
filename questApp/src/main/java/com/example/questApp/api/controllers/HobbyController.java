package com.example.questApp.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.questApp.business.abstracts.HobbyService;
import com.example.questApp.business.requests.HobbyCreateRequest;
import com.example.questApp.business.responses.HobbyResponse;
import com.example.questApp.core.utilities.results.DataResult;

@RestController
@RequestMapping("/hobbies")
public class HobbyController {
    private final HobbyService hobbyService;

    @Autowired
    public HobbyController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @GetMapping("/getByUserId/{userId}")
    public DataResult<List<HobbyResponse>> getByUserId(@PathVariable("userId") Long userId) {
        return hobbyService.getByUserId(userId);
    }

    // Endpoint to add a new hobby
    @PostMapping("/add")
    public DataResult<List<HobbyResponse>> add(@RequestBody List<HobbyCreateRequest> hobbyCreateRequest) {
        return hobbyService.add(hobbyCreateRequest);
    }
}

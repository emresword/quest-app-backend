package com.example.questApp.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.questApp.business.abstracts.LikeService;
import com.example.questApp.business.requests.LikeCreateRequest;
import com.example.questApp.business.responses.LikeResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.Like;

@RestController
@RequestMapping("/likes")
@CrossOrigin
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public DataResult<List<LikeResponse>> getAll(
            @RequestParam Optional<Long> userId,
            @RequestParam Optional<Long> postId) {
        return likeService.getAll(userId, postId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody LikeCreateRequest likeCreateRequest) {
        return likeService.add(likeCreateRequest);
    }

    @GetMapping("/{id}")
    public DataResult<Like> getById(@PathVariable Long id) {
        return likeService.getById(id);
    }

    @DeleteMapping
    public Result delete(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId) {
        return likeService.delete(userId, postId);
    }
}

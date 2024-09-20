package com.example.questApp.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.LikeService;
import com.example.questApp.business.requests.LikeCreateRequest;
import com.example.questApp.business.responses.LikeResponse;
import com.example.questApp.entities.concretes.Like;
@RestController
@RequestMapping("/api/likes")
@CrossOrigin
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAll(
            @RequestParam Optional<Long> userId,
            @RequestParam Optional<Long> postId) {
        return likeService.getAll(userId, postId);
    }

    @PostMapping("/add")
    public Like add(@RequestBody LikeCreateRequest likeCreateRequest) {
        return likeService.add(likeCreateRequest);
    }

    @GetMapping("/{id}")
    public Like getById(@PathVariable Long id) {
        return likeService.getById(id);
    }

    @PutMapping("/{id}")
    public Like update(@PathVariable Long id, @RequestBody Like like) {
        return likeService.update(id, like);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        likeService.delete(id);
    }
}

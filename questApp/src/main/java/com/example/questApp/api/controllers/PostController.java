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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.PostService;
import com.example.questApp.business.requests.PostCreateRequest;
import com.example.questApp.business.requests.PostUpdateRequest;
import com.example.questApp.business.responses.PostResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.Post;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public DataResult<List<PostResponse>> getAll(@RequestParam(value = "userId", required = false) Optional<Long> userId) {
      return postService.getAll(userId);
    }

    @PostMapping("/add")
    public DataResult<Post> add(@RequestBody PostCreateRequest postCreateRequest) {
        return postService.add(postCreateRequest);
    }

    @GetMapping("/{postId}")
    public DataResult<Post> getById(@PathVariable("postId") Long id) {
        return postService.getById(id);
    }

    @PutMapping("/{postId}")
    public DataResult<Post> update(@PathVariable("postId") Long id, @RequestBody PostUpdateRequest postUpdateRequest) {
        return postService.update(id, postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public Result delete(@PathVariable("postId") Long id) {
        return postService.delete(id);
    }
}

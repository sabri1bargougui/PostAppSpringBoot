package com.example.postapp.Controller;

import com.example.postapp.Exception.ResourceNotFoundException;
import com.example.postapp.Models.Post;
import com.example.postapp.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping("/posts")
    public Post createPost(@Validated @RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPostById(@PathVariable(value = "id") Long postId) {
        return postRepository.findById(postId);

    }

    @PutMapping("/posts/{id}")
    public Post updateNote(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody Post postDetails) {

        Post post = postRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", noteId));

        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());

        Post updatedPost = postRepository.save(post);
        return updatedPost;
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long postId) {
        Post note = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        postRepository.delete(note);

        return ResponseEntity.ok().build();
    }



}

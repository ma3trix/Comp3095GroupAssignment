package ca.gbc.postservice.controller;

import ca.gbc.postservice.dto.PostRequest;
import ca.gbc.postservice.dto.PostResponse;
import ca.gbc.postservice.service.PostServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceimpl postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") String postId,
                                        @RequestBody PostRequest postRequest) {
        String updatedPostId = postService.updatePost(postId, postRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/post/" + updatedPostId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") String postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

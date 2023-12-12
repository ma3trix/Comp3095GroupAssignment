package ca.gbc.commentservice.controller;

import ca.gbc.commentservice.model.Comment;
import ca.gbc.commentservice.service.CommentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/comment/" + createdComment.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "post", fallbackMethod = "createCommentFallback")
    @TimeLimiter(name = "post")
    @Retry(name = "post")
    public CompletableFuture<Comment> createComment(@RequestBody Comment comment){
        commentService.createComment(comment);
        return CompletableFuture.supplyAsync(() -> commentService.createComment(comment));
    }
    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Comment getComment(@PathVariable("commentId") String commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getCommentsByPost(@PathVariable("postId") String postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable("commentId") String commentId,
                                                 @RequestBody Comment comment) {
        Comment updatedComment = commentService.updateComment(comment);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

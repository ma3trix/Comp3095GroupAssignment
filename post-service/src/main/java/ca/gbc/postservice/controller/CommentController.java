package ca.gbc.postservice.controller;

import ca.gbc.postservice.dto.CommentRequest;
import ca.gbc.postservice.dto.CommentResponse;
import ca.gbc.postservice.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComments() {
        return commentService.getAllComments();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") String commentId,
                                           @RequestBody CommentRequest commentRequest) {
        String updatedCommentId = commentService.updateComment(commentId, commentRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/comment/" + updatedCommentId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

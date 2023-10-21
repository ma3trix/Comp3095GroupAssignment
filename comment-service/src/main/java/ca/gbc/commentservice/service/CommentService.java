package ca.gbc.commentservice.service;

import ca.gbc.commentservice.model.Comment;

import java.util.List;

public interface CommentService {
    // Create a new comment
    Comment createComment(Comment comment);

    // Retrieve a comment by its ID
    Comment getCommentById(String commentId);

    // Retrieve all comments for a specific post
    List<Comment> getCommentsByPostId(String postId);

    // Update an existing comment
    Comment updateComment(Comment comment);

    // Delete a comment by its ID
    void deleteComment(String commentId);
}

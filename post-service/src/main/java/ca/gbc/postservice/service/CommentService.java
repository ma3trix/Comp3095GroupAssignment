package ca.gbc.postservice.service;

import ca.gbc.postservice.dto.CommentResponse;
import ca.gbc.postservice.dto.CommentRequest;

import java.util.List;

public interface CommentService {

    void createComment(CommentRequest commentRequest);

    public String updateComment(String commentId, CommentRequest commentRequest);

    void deleteComment(String commentId);

    List<CommentResponse> getAllComments();
}
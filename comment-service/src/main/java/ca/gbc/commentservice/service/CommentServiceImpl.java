package ca.gbc.commentservice.service;

import ca.gbc.commentservice.model.Comment;
import ca.gbc.commentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Comment comment) {
        // Perform comment creation logic, e.g., setting timestamps
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(String commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public List<Comment> getCommentsByPostId(String postId) {
        // Implement logic to retrieve comments associated with a specific post
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment updateComment(Comment comment) {
        // Perform comment update logic, e.g., updating timestamps
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }
}

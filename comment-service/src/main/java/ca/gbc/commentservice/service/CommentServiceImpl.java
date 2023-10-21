package ca.gbc.commentservice.service;

import ca.gbc.commentservice.model.Comment;
import ca.gbc.commentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        // Set timestamps and perform any additional logic
        LocalDateTime now = LocalDateTime.now();
        comment.setCreatedDate(now);
        comment.setLastModifiedDate(now);

        // Save the comment
        Comment createdComment = commentRepository.save(comment);

        // You can add any additional logic here

        return createdComment;
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
        try {
            // Retrieve the existing comment by its ID
            Comment existingComment = commentRepository.findById(comment.getId())
                    .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

            // Update the content and lastModifiedDate fields
            existingComment.setContent(comment.getContent());
            existingComment.setLastModifiedDate(LocalDateTime.now());

            // Save the updated comment
            Comment updatedComment = commentRepository.save(existingComment);

            return updatedComment;
        } catch (CommentNotFoundException ex) {
            // Handle the exception, e.g., log it or return an appropriate response
            // You can also rethrow or wrap the exception if needed
            throw ex;
        }
    }

    @Override
    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }
}

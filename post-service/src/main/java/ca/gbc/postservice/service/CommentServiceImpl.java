package ca.gbc.postservice.service;

import ca.gbc.postservice.dto.CommentRequest;
import ca.gbc.postservice.dto.CommentResponse;
import ca.gbc.postservice.model.Comment;
import ca.gbc.postservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void createComment(CommentRequest commentRequest) {
        log.info("Creating a new comment: {}", commentRequest.getContent());

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .author(commentRequest.getAuthor())
                .post(commentRequest.getPost())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        log.info("Comment {} is saved", comment.getId());
    }

    @Override
    public String updateComment(String commentId, CommentRequest commentRequest) {
        log.info("Updating a comment with id: {}", commentId);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(commentId));
        Comment comment = mongoTemplate.findOne(query, Comment.class);

        if (comment != null) {
            comment.setContent(commentRequest.getContent());
            comment.setAuthor(commentRequest.getAuthor());
            comment.setPost(commentRequest.getPost());
            comment.setLastModifiedDate(LocalDateTime.now());

            log.info("Comment {} is updated", commentId);

            return commentRepository.save(comment).getId();
        }
        return commentId;
    }

    @Override
    public void deleteComment(String commentId) {
        log.info("Comment {} is deleted", commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponse> getAllComments() {
        log.info("Returning a list of comments");
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::mapToCommentResponse).toList();
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .post(comment.getPost())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .build();
    }
}

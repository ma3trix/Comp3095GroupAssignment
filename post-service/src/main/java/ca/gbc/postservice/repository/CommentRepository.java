package ca.gbc.postservice.repository;

import ca.gbc.postservice.model.Comment;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @DeleteQuery
    void deleteById(String commentId);

}
package ca.gbc.commentservice.dto;

import ca.gbc.commentservice.model.Post;
import ca.gbc.commentservice.model.User;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class CommentResponse {
    @Id
    private String id;
    private String content;
    private User author;
    private Post post;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}

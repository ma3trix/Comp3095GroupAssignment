package ca.gbc.commentservice.dto;

import ca.gbc.commentservice.model.Post;
import ca.gbc.commentservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private String content;
    private User author;
    private Post post;
}

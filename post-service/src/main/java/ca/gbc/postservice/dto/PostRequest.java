package ca.gbc.postservice.dto;

import ca.gbc.postservice.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {

    private String content;
    private String title;
    private User author;
    private List<Comment> comments;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}

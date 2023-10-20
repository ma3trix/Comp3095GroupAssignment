package ca.gbc.postservice.dto;

import ca.gbc.postservice.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {

    private String content;

    private String title;
    private User author;
    private Post post;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}


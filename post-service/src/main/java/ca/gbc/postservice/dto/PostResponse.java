package ca.gbc.postservice.dto;

import ca.gbc.postservice.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.apache.catalina.User;
import org.springframework.data.annotation.Id;
import ca.gbc.postservice.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    @Id
    private String id;

    private String title;
    private String content;
    private User author;
    private List<Comment> comments;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
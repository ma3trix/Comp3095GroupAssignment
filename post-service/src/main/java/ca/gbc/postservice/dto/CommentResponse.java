package ca.gbc.postservice.dto;
import java.time.LocalDateTime;
import ca.gbc.postservice.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    @Id
    private String id;

    private String title;
    private String content;
    private User author;
    private Post post;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
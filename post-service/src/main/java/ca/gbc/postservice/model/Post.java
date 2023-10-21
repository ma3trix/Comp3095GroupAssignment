package ca.gbc.postservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    private String title;
    private String content;
    private User author;
    private List<Comment> comments;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}

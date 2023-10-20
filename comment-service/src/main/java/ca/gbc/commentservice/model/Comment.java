package ca.gbc.commentservice.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String content;
    private User author; // Reference to the user who made the comment
    //private Post post; // Reference to the post the comment belongs to
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // Getters and setters
}

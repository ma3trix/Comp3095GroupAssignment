package ca.gbc.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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

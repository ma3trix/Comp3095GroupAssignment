package ca.gbc.friendshipservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Entity
@Document(collection = "user")
@Data
@AllArgsConstructor
@Builder
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User recipient;

    private LocalDateTime createdDate;

    public Friendship() {
        this.createdDate = LocalDateTime.now();


    }
}

package ca.gbc.friendshipservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FriendshipResponse {
    private String friendshipId; // ID of the friendship
    private String userId; // ID of the user who initiated the friendship
    private String friendId; // ID of the friend who accepted the friendship

}

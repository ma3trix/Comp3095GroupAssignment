package ca.gbc.friendshipservice.service;

import ca.gbc.friendshipservice.model.Friendship;
import java.util.List;

public interface FriendshipService {
    // Create a new friendship
    Friendship createFriendship(Friendship friendship);

    // Get a list of friendships for a specific user by their userId
    List<Friendship> getFriendshipsByUserId(String userId);

    // Accept a friendship request
    Friendship acceptFriendshipRequest(String friendshipId);

    // Remove a friendship
    void removeFriendship(String friendshipId);

    // Check if two users are friends
    boolean areUsersFriends(String userId1, String userId2);

    // Add more methods as needed for your application's requirements
}

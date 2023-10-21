package ca.gbc.friendshipservice.service;
import ca.gbc.friendshipservice.model.Friendship;
import ca.gbc.friendshipservice.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship createFriendship(Friendship friendship) {
        // Set the createdDate to the current timestamp
        friendship.setCreatedDate(LocalDateTime.now());

        // Save the new friendship
        return friendshipRepository.save(friendship);
    }

    @Override
    public List<Friendship> getFriendshipsByUserId(String userId) {
        return friendshipRepository.findByUserId(userId);
    }

    @Override
    public Friendship acceptFriendshipRequest(String friendshipId) {
        Optional<Friendship> optionalFriendship = friendshipRepository.findById(friendshipId);
        if (optionalFriendship.isPresent()) {
            Friendship friendship = optionalFriendship.get();
            // Update the friendship to mark it as accepted
            return friendshipRepository.save(friendship);
        }
        // Handle not found or other error cases
        return null;
    }

    @Override
    public void removeFriendship(String friendshipId) {
        friendshipRepository.deleteById(friendshipId);
    }

    @Override
    public boolean areUsersFriends(String userId1, String userId2) {
        List<Friendship> friendships = friendshipRepository.findFriendshipBetweenUsers(userId1, userId2);
        return !friendships.isEmpty();
    }


}

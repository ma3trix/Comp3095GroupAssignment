package ca.gbc.friendshipservice.repository;

import ca.gbc.friendshipservice.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    List<Friendship> findByUserId(String userId);
    // Custom query method to find friendships where the initiator's user ID matches the provided user ID
    List<Friendship> findByInitiatorUserId(String userId);

    // Custom query method to find friendships where the recipient's user ID matches the provided user ID
    List<Friendship> findByRecipientUserId(String userId);

    List<Friendship> findFriendshipBetweenUsers(String userId1, String userId2);


}

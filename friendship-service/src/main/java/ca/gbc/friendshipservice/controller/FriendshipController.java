package ca.gbc.friendshipservice.controller;

import ca.gbc.friendshipservice.dto.FriendshipRequest;
import ca.gbc.friendshipservice.dto.FriendshipResponse;
import ca.gbc.friendshipservice.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendship")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FriendshipResponse> createFriendship(@RequestBody FriendshipRequest friendshipRequest) {
        FriendshipResponse response = friendshipService.createFriendship(friendshipRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<FriendshipResponse>> getFriendshipsByUserId(@PathVariable("userId") String userId) {
        List<FriendshipResponse> friendships = friendshipService.getFriendshipsByUserId(userId);
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @PutMapping("/{friendshipId}")
    public ResponseEntity<FriendshipResponse> acceptFriendshipRequest(@PathVariable("friendshipId") String friendshipId) {
        FriendshipResponse response = friendshipService.acceptFriendshipRequest(friendshipId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<?> removeFriendship(@PathVariable("friendshipId") String friendshipId) {
        friendshipService.removeFriendship(friendshipId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

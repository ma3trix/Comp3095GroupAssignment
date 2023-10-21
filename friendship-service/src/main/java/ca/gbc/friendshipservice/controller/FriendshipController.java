package ca.gbc.friendshipservice.controller;

import ca.gbc.friendshipservice.dto.FriendshipRequest;
import ca.gbc.friendshipservice.dto.FriendshipResponse;
import ca.gbc.friendshipservice.model.Friendship;
import ca.gbc.friendshipservice.service.FriendshipService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FriendshipResponse> createFriendship(@RequestBody FriendshipRequest friendshipRequest) {
        // Create a new Friendship object and copy properties from FriendshipRequest
        Friendship newFriendship = new Friendship();
        BeanUtils.copyProperties(friendshipRequest, newFriendship);

        // Call the service to create the friendship
        Friendship createdFriendship = friendshipService.createFriendship(newFriendship);

        // Create a response object and copy properties from the created Friendship
        FriendshipResponse response = new FriendshipResponse();
        BeanUtils.copyProperties(createdFriendship, response);

        // Return the response with HTTP status code 201 (CREATED)
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<FriendshipResponse>> getFriendshipsByUserId(@PathVariable String userId) {
        // Retrieve a list of Friendships for the given user ID
        List<Friendship> friendships = friendshipService.getFriendshipsByUserId(userId);

        // Map the list of Friendships to a list of FriendshipResponses
        List<FriendshipResponse> responseList = friendships.stream()
                .map(friendship -> {
                    FriendshipResponse response = new FriendshipResponse();
                    BeanUtils.copyProperties(friendship, response);
                    return response;
                })
                .collect(Collectors.toList());

        // Return the list of FriendshipResponses with HTTP status code 200 (OK)
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PutMapping("/{friendshipId}")
    public ResponseEntity<FriendshipResponse> acceptFriendshipRequest(@PathVariable String friendshipId) {
        // Call the service to accept the friendship request
        Friendship updatedFriendship = friendshipService.acceptFriendshipRequest(friendshipId);

        // If the updatedFriendship is not null, create a response object and copy properties
        if (updatedFriendship != null) {
            FriendshipResponse response = new FriendshipResponse();
            BeanUtils.copyProperties(updatedFriendship, response);

            // Return the response with HTTP status code 200 (OK)
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Return a response with HTTP status code 404 (NOT FOUND) if the friendship is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<?> removeFriendship(@PathVariable String friendshipId) {
        // Call the service to remove the friendship
        friendshipService.removeFriendship(friendshipId);

        // Return a response with HTTP status code 204 (NO CONTENT)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

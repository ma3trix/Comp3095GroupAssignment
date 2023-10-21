package ca.gbc.userservice.service;
import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;
import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public void createUser(UserRequest userRequest) {
        log.info("Creating new user {}", userRequest.getUsername());

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .build();

        userRepository.save(user);

        log.info("User {} is saved", user.getId());
    }

    @Override
    public String updateUser(String userId, UserRequest userRequest) {
        log.info("Updating a user with id {}", userId);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        User user = mongoTemplate.findOne(query, User.class);

        if (user != null) {
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setEmail(userRequest.getEmail());

            log.info("User {} is updated", userId);

            return userRepository.save(user).getId();
        }
        return userId.toString();
    }

    @Override
    public void deleteUser(String userID) {
        log.info("Product {} is deleted", userID);
        userRepository.deleteById(userID);
    }

    @Override
    public List<UserResponse> getUsers() {
        log.info("Returning a list of users");
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }


    @Override
    public User getUserbyId(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
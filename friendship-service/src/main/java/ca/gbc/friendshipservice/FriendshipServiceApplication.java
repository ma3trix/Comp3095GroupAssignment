package ca.gbc.friendshipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication

public class FriendshipServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendshipServiceApplication.class, args);
    }

}

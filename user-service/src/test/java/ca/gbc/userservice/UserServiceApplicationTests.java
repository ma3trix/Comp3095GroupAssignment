package ca.gbc.userservice;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;
import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceApplicationTests extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private UserRequest getUserRequest() {
        return UserRequest.builder()
                .username("Sunshine")
                .password("SunIsShining")
                .email("Sunshine@gmail.com")
                .build();
    }

    private List<User> getUserList() {
        List<User> users = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        User user = User.builder()
                .username("Sunshine")
                .password("SunIsShining")
                .email("Sunshine@gmail.com")
                .build();
        users.add(user);
        return users;
    }

    private String convertObjecetToJsonString(List<UserResponse> userResponseList) throws Exception {
        return objectMapper.writeValueAsString(userResponseList);
    }

    private List<UserResponse> convertJsonToObject(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<List<UserResponse>>() {
        });
    }

    @Test
    void createUser() throws Exception {
        UserRequest userRequest = getUserRequest();
        String userRequestString = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestString))
                .andExpect(MockMvcResultMatchers.status().isCreated());


        Assertions.assertTrue(userRepository.findAll().size() > 0);

        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("Sunshine"));
        List<User> products = mongoTemplate.find(query, User.class);
        Assertions.assertTrue(products.size() > 0);

    }

    @Test
    void getAllUsers() throws Exception {
        //Given
        userRepository.saveAll(getUserList());

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user")
                .accept(MediaType.APPLICATION_JSON));
        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk());
        //print out the response
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        result.getResponse().getContentAsString();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);

        int actualSize = jsonNodes.size();
        int expectedSize = getUserList().size();

        Assertions.assertEquals(expectedSize, actualSize);

    }

    /*@Test
    void updateUser() throws Exception {
        //Given
        User savedUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("Sunshine")
                .password("SunIsShining")
                .email("Sunshine@gmail.com")
                .build();

        //Saved product with original price
        userRepository.save(savedUser);

        //prepare updated product and productRequest
        savedUser.setUsername(String);
        String productRequestString = objectMapper.writeValueAsString(savedUser);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/user/" + savedUser.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedUser.getId()));
        User storedUser = mongoTemplate.findOne(query, User.class);

        assertEquals(savedUser.getUsername(), storedUser.getUsername());
    }*/

    @Test
    void updateUser() throws Exception {
        // Given
        User savedUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("Sunshine")
                .password("SunIsShining")
                .email("Sunshine@gmail.com")
                .build();

        // Save the user in the database
        userRepository.save(savedUser);

        // Prepare an updated user
        savedUser.setUsername("NewUsername");
        String updatedUserJson = objectMapper.writeValueAsString(savedUser);

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/user/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson));

        // Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        // You may need to wait or use asynchronous mechanisms before checking the updated user in the database
        Thread.sleep(1000); // This is a simple sleep; in a real test, consider a more robust approach

        // Query the database to retrieve the updated user
        User storedUser = userRepository.findById(savedUser.getId()).orElse(null);

        // Assert that the username has been updated
        assertNotNull(storedUser);
        assertEquals("NewUsername", storedUser.getUsername());
    }


    @Test
    void deleteUser() throws Exception {
        //Given
        User savedUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("Sunshine")
                .password("SunIsShining")
                .email("Sunshine@gmail.com")
                .build();

        userRepository.save(savedUser);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/user/" + savedUser.getId().toString())
                .contentType(MediaType.APPLICATION_JSON));


        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedUser.getId()));
        Long userCount = mongoTemplate.count(query, User.class);

        assertEquals(0, userCount);


    }
}

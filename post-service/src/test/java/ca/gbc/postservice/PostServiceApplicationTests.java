package ca.gbc.postservice;

import ca.gbc.postservice.dto.PostRequest;
import ca.gbc.postservice.model.Post;
import ca.gbc.postservice.model.User;
import ca.gbc.postservice.repository.PostRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class PostServiceApplicationTests extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private PostRequest getPostRequest() {
        User author = User.builder()
                .id(UUID.randomUUID().toString())
                .username("AuthorUsername")
                .password("AuthorPassword")
                .email("author@example.com")
                .build();

        return PostRequest.builder()
                .title("Post Title")
                .content("Post Content")
                .author(author)
                .build();
    }

    private List<Post> getPostList() {
        List<Post> posts = new ArrayList<>();

        User author = User.builder()
                .id(UUID.randomUUID().toString())
                .username("AuthorUsername")
                .password("AuthorPassword")
                .email("author@example.com")
                .build();

        Post post = Post.builder()
                .title("Post Title")
                .content("Post Content")
                .author(author)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        posts.add(post);
        return posts;
    }

    @Test
    void createPost() throws Exception {
        PostRequest postRequest = getPostRequest();
        String postRequestString = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertTrue(postRepository.findAll().size() > 0);

        Query query = new Query();
        query.addCriteria(Criteria.where("title").is("Post Title"));
        List<Post> posts = mongoTemplate.find(query, Post.class);
        Assertions.assertTrue(posts.size() > 0);
    }

    @Test
    void getAllPosts() throws Exception {
        postRepository.saveAll(getPostList());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/post")
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        result.getResponse().getContentAsString();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);

        int actualSize = jsonNodes.size();
        int expectedSize = getPostList().size();

        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void updatePost() throws Exception {
        Post savedPost = Post.builder()
                .title("Original Title")
                .content("Original Content")
                .author(User.builder()
                        .id(UUID.randomUUID().toString())
                        .username("AuthorUsername")
                        .password("AuthorPassword")
                        .email("author@example.com")
                        .build())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        postRepository.save(savedPost);

        savedPost.setTitle("Updated Title");
        String postRequestString = objectMapper.writeValueAsString(savedPost);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/post/" + savedPost.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(postRequestString));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedPost.getId()));
        Post storedPost = mongoTemplate.findOne(query, Post.class);

        assertEquals(savedPost.getTitle(), storedPost.getTitle());
    }

    @Test
    void deletePost() throws Exception {
        Post savedPost = Post.builder()
                .title("Post Title")
                .content("Post Content")
                .author(User.builder()
                        .id(UUID.randomUUID().toString())
                        .username("AuthorUsername")
                        .password("AuthorPassword")
                        .email("author@example.com")
                        .build())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        postRepository.save(savedPost);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/post/" + savedPost.getId().toString())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedPost.getId()));
        Long postCount = mongoTemplate.count(query, Post.class);

        assertEquals(0, postCount);
    }
}
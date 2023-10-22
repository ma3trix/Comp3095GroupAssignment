package ca.gbc.commentservice;
import ca.gbc.commentservice.model.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createComment() throws Exception {
        // Create a Comment object
        Comment comment = Comment.builder()
                .content("This is a comment")
                // Set author, post, and other fields as needed
                .build();

        // Convert Comment to JSON
        String commentJson = objectMapper.writeValueAsString(comment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Assertions for successful comment creation
        assertNotNull(comment.getId());
        // Add more assertions if needed
    }

    @Test
    void getAllComments() throws Exception {
        // Given: Save some comments to the database
        List<Comment> savedComments = saveCommentsToDatabase();

        // When: Send a GET request to retrieve comments
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/comments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // Then: Parse the JSON response and assert the expected number of comments
        String jsonResponse = result.getResponse().getContentAsString();
        List<Comment> responseComments = deserializeCommentList(jsonResponse);
        assertEquals(savedComments.size(), responseComments.size());
    }

    @Test
    void updateComment() throws Exception {
        // Given: Save a comment to the database
        Comment savedComment = saveCommentToDatabase();

        // Update the comment content
        savedComment.setContent("Updated comment content");

        // Convert updated comment to JSON
        String updatedCommentJson = objectMapper.writeValueAsString(savedComment);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/comments/" + savedComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCommentJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // You may need to wait or use asynchronous mechanisms before checking the updated comment in the database
        Thread.sleep(1000); // This is a simple sleep; in a real test, consider a more robust approach

        // Query the database to retrieve the updated comment
        Comment updatedComment = getCommentByIdFromDatabase(savedComment.getId());

        // Assert that the comment content has been updated
        assertNotNull(updatedComment);
        assertEquals("Updated comment content", updatedComment.getContent());
    }

    @Test
    void deleteComment() throws Exception {
        // Given: Save a comment to the database
        Comment savedComment = saveCommentToDatabase();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/comments/" + savedComment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // You may need to wait or use asynchronous mechanisms before checking if the comment has been deleted from the database
        Thread.sleep(1000); // This is a simple sleep; in a real test, consider a more robust approach

        // Query the database to check if the comment has been deleted
        Comment deletedComment = getCommentByIdFromDatabase(savedComment.getId());

        // Assert that the comment has been deleted (should be null)
        assertNull(deletedComment);
    }

    private List<Comment> saveCommentsToDatabase() {
        // Save some comments to the database and return them
        // You can use the CommentRepository to save comments
        return null;
    }

    private Comment saveCommentToDatabase() {
        // Save a comment to the database and return it
        // You can use the CommentRepository to save a comment
        return null;
    }

    private Comment getCommentByIdFromDatabase(String commentId) {
        // Retrieve a comment from the database by its ID
        // You can use the CommentRepository to find a comment by ID
        return null;
    }

    private List<Comment> deserializeCommentList(String jsonString) throws Exception {
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        CollectionType collectionType = typeFactory.constructCollectionType(List.class, Comment.class);
        return objectMapper.readValue(jsonString, collectionType);
    }
}



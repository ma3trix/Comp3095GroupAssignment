package ca.gbc.postservice.service;

import ca.gbc.postservice.dto.PostRequest;
import ca.gbc.postservice.dto.PostResponse;
import ca.gbc.postservice.model.Post;
import ca.gbc.postservice.model.User;
import ca.gbc.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceimpl implements PostService {

    private final PostRepository postRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void createPost(PostRequest postRequest) {
        log.info("Creating a new post: {}", postRequest.getTitle());

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author((User) postRequest.getAuthor())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        postRepository.save(post);

        log.info("Post {} is saved", post.getId());
    }

    @Override
    public String updatePost(String postId, PostRequest postRequest) {
        log.info("Updating a post with id: {}", postId);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(postId));
        Post post = mongoTemplate.findOne(query, Post.class);

        if (post != null) {
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setAuthor((User) postRequest.getAuthor());
            post.setLastModifiedDate(LocalDateTime.now());

            log.info("Post {} is updated", postId);

            return postRepository.save(post).getId();
        }
        return postId;
    }

    public Post getPostbyId(String postId) {
        return postRepository.findById(postId).orElse(null);
    }


    @Override
    public void deletePost(String postId) {
        log.info("Post {} is deleted", postId);
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        log.info("Returning a list of posts");
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToPostResponse).toList();
    }

    private PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author((User) post.getAuthor())
                .createdDate(post.getCreatedDate())
                .lastModifiedDate(post.getLastModifiedDate())
                .build();
    }
}
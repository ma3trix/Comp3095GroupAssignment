package ca.gbc.postservice.service;

import ca.gbc.postservice.dto.PostResponse;
import ca.gbc.postservice.dto.PostRequest;
import ca.gbc.postservice.model.Post;

import java.util.List;

public interface PostService {

    void createPost(PostRequest postRequest);

    public String updatePost(String postId, PostRequest postRequest);

    void deletePost(String postId);

    List<PostResponse> getAllPosts();

    Post getPostbyId(String postId);

}
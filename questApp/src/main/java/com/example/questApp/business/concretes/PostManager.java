package com.example.questApp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.LikeService;
import com.example.questApp.business.abstracts.PostService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.PostCreateRequest;
import com.example.questApp.business.requests.PostUpdateRequest;
import com.example.questApp.business.responses.LikeResponse;
import com.example.questApp.business.responses.PostResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.core.utilities.results.SuccessResult;
import com.example.questApp.dataAccess.abstracts.PostDao;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

@Service
public class PostManager implements PostService {
    private final PostDao postDao;
    private final UserService userService;

    @Autowired
    @Lazy
    private LikeService likeService;

    @Autowired
    public PostManager(PostDao postDao, UserService userService) {
        this.postDao = postDao;
        this.userService = userService;
    }

    @Override
    public DataResult<List<PostResponse>> getAll(Optional<Long> userId) {
        List<Post> posts;
        if (userId.isPresent()) {
            posts = postDao.findByUserId(userId.get());
        } else {
            posts = postDao.findAll();
        }

        List<PostResponse> response = posts.stream().map(p -> {
         
            DataResult<List<LikeResponse>> likeResult = likeService.getAll(Optional.empty(), Optional.of(p.getId()));
            
            if (!likeResult.isSuccess()) {
               
                return new PostResponse(p, List.of());
            }
            
            List<LikeResponse> likes = likeResult.getData();
            return new PostResponse(p, likes);
        }).collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Posts listed successfully");
    }

    @Override
    public DataResult<Post> add(PostCreateRequest postCreateRequest) {
        DataResult<User> userResult = this.userService.getById(postCreateRequest.getUserId());
        if (!userResult.isSuccess()) {
            return new ErrorDataResult<>("User not found");
        }

        User user = userResult.getData();

        Post toSavePost = new Post();
        toSavePost.setText(postCreateRequest.getText());
        toSavePost.setTitle(postCreateRequest.getTitle());
        toSavePost.setUser(user);

        Post savedPost = postDao.save(toSavePost);
        return new SuccessDataResult<>(savedPost, "Post added successfully");
    }

    @Override
    public DataResult<Post> getById(Long id) {
        Post post = postDao.findById(id).orElse(null);
        if (post == null) {
            return new ErrorDataResult<>("Post not found");
        }

        return new SuccessDataResult<>(post, "Post found successfully");
    }

    @Override
    public DataResult<Post> update(Long id, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postDao.findById(id);
        if (post.isPresent()) {
            Post toUpdate = post.get();
            toUpdate.setText(null);
            toUpdate.setTitle(postUpdateRequest.getTitle());
            Post updatedPost = postDao.save(toUpdate);
            return new SuccessDataResult<>(updatedPost, "Post updated successfully");
        }
        return new ErrorDataResult<>("Post not found");
    }

    @Override
    public Result delete(Long id) {
        Optional<Post> post = postDao.findById(id);
        if (post.isPresent()) {
            postDao.delete(post.get());
            return new SuccessResult("Post deleted successfully");
        }
        return new ErrorResult("Post not found");
    }
//    @Override
//    public DataResult<Post> add(String title, Long userId, MultipartFile image) {
//        DataResult<User> userResult = this.userService.getById(userId);
//        if (!userResult.isSuccess()) {
//            return new ErrorDataResult<>("User not found");
//        }
//
//        User user = userResult.getData();
//        Post toSavePost = new Post();
//        toSavePost.setTitle(title);
//        toSavePost.setUser(user);
//
//        // Save the image and get the image URL or path
//        String imagePath = saveImage(image);
//        toSavePost.setImage(imagePath);
//
//        Post savedPost = postDao.save(toSavePost);
//        return new SuccessDataResult<>(savedPost, "Post added successfully");
//    }
//
//    private String saveImage(MultipartFile image) {
//        if (image == null || image.isEmpty()) {
//            return null;
//        }
//        
//        try {
//            // Get the absolute path of the images directory
//            String uploadDir = System.getProperty("user.dir") + "/images/";
//            File uploadDirFile = new File(uploadDir);
//            if (!uploadDirFile.exists()) {
//                uploadDirFile.mkdirs(); // Create the directory if it doesn't exist
//            }
//
//            // Get the original filename and construct the file path
//            String fileName = image.getOriginalFilename();
//            String filePath = uploadDir + fileName;
//            File file = new File(filePath);
//
//            // Save the file
//            image.transferTo(file);
//            
//            return filePath; // Return the saved image path
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Log the error or handle it appropriately
//            return null;
//        }
//    }

}

package com.example.questApp.business.abstracts;

import java.util.List;

import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

public interface UserService {
    DataResult<List<User>> getAll();
    DataResult<User> add(User user);
    DataResult<User> getById(Long id);
    DataResult<User> update(Long id, User user);
    Result delete(Long id);
    DataResult<User> getByUserName(String userName);
//    DataResult<User> updateAvatar(Long id, int avatarId);
    DataResult<User> updateAvatar(Long id, int avatarId, String currentUsername);
    DataResult<List<Post>> getPostsByUserId(Long userId);//i am gonna solve these because now i am using user instead of user request response
    DataResult<List<User>> getByUserNameContaining(String userName);
    DataResult<User> updateDescription(Long id,String description, String currentUsername);
}

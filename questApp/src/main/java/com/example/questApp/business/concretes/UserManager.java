package com.example.questApp.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.core.utilities.results.SuccessResult;
import com.example.questApp.dataAccess.abstracts.PostDao;
import com.example.questApp.dataAccess.abstracts.UserDao;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

@Service
@Transactional
public class UserManager implements UserService {
    private final UserDao userDao;
    private final PostDao postDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserManager(UserDao userDao, PostDao postDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataResult<List<User>> getAll() {
        List<User> users = this.userDao.findAll();
        return new SuccessDataResult<>(users, "Users retrieved successfully.");
    }

    @Override
    public DataResult<User> add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
        User savedUser = this.userDao.save(user);
        return new SuccessDataResult<>(savedUser, "User added successfully.");
    }

    @Override
    public DataResult<User> getById(Long id) {
        Optional<User> user = userDao.findById(id);
        if (user.isPresent()) {
            return new SuccessDataResult<>(user.get(), "User retrieved successfully.");
        }
        return new ErrorDataResult<>("User not found.");
    }

    @Override
    public DataResult<User> update(Long id, User newUser) {
        Optional<User> user = userDao.findById(id);

        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) { 
                foundUser.setPassword(passwordEncoder.encode(newUser.getPassword())); // Re-encode password
            }
            User updatedUser = userDao.save(foundUser);
            return new SuccessDataResult<>(updatedUser, "User updated successfully.");
        } else {
            return new ErrorDataResult<>("User not found.");
        }
    }

    @Override
    public Result delete(Long id) {
        if (userDao.existsById(id)) {
            this.userDao.deleteById(id);
            return new SuccessResult("User deleted successfully.");
        }
        return new ErrorResult("User not found.");
    }

    @Override
    public DataResult<User> getByUserName(String userName) {
        User user = userDao.findByUserName(userName);
        if (user != null) {
            return new SuccessDataResult<>(user, "User found successfully.");
        }
        return new ErrorDataResult<>("User not found.");
    }

//    @Override
//    public DataResult<User> updateAvatar(Long id, int avatarId) {
//        Optional<User> user = userDao.findById(id);
//
//        if (user.isPresent()) {
//            User foundUser = user.get();
//            foundUser.setAvatarId(avatarId);
//            User updatedUser = userDao.save(foundUser);
//            return new SuccessDataResult<>(updatedUser, "User avatar updated successfully.");
//        } else {
//            return new ErrorDataResult<>("User not found.");
//        }
//    }
    @Override
    public DataResult<User> updateAvatar(Long id, int avatarId, String currentUsername) {
        Optional<User> user = userDao.findById(id);

        if (user.isPresent()) {
            User foundUser = user.get();
            if (!foundUser.getUserName().equals(currentUsername)) {
                return new ErrorDataResult<>("You are not authorized to update this user's avatar.");
            }

            foundUser.setAvatarId(avatarId);
            User updatedUser = userDao.save(foundUser);
            return new SuccessDataResult<>(updatedUser, "User avatar updated successfully.");
        } else {
            return new ErrorDataResult<>("User not found.");
        }
    }

    @Override
    public DataResult<List<Post>> getPostsByUserId(Long userId) {
        List<Post> posts = postDao.findByUserId(userId);
        return new SuccessDataResult<>(posts, "Posts retrieved successfully.");
    }

	@Override
	public DataResult<List<User>> getByUserNameContaining(String userName) {
		List<User> users=this.userDao.findByUserNameContaining(userName);
		if(users!=null && !userName.isEmpty()) {
			 return new SuccessDataResult<>(users, "Posts retrieved successfully.");
		}
		return new ErrorDataResult<>( "Users aren't listed");
	}
	@Override
    public DataResult<User> updateDescription(Long id,String description, String currentUserName) {
        Optional<User> user = userDao.findById(id);

        if (user.isPresent()) {
            User foundUser = user.get();
            if (!foundUser.getUserName().equals(currentUserName)) {
                return new ErrorDataResult<>("You are not authorized to update this user's description.");
            }

            foundUser.setDescription(description);
            User updatedUser = userDao.save(foundUser);
            return new SuccessDataResult<>(updatedUser, "User description updated successfully.");
        } else {
            return new ErrorDataResult<>("User not found.");
        }
    }
}

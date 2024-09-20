package com.example.questApp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.FollowerService;
import com.example.questApp.business.requests.FollowerRequest;
import com.example.questApp.business.responses.FollowerResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.core.utilities.results.SuccessResult;
import com.example.questApp.dataAccess.abstracts.FollowerDao;
import com.example.questApp.dataAccess.abstracts.UserDao;
import com.example.questApp.entities.concretes.Follower;
import com.example.questApp.entities.concretes.User;

@Service
public class FollowerManager implements FollowerService {

    private final FollowerDao followerDao;
    private final UserDao userDao;

    @Autowired
    public FollowerManager(FollowerDao followerDao, UserDao userDao) {
        this.followerDao = followerDao;
        this.userDao = userDao;
    }

    @Override
    public DataResult<List<FollowerResponse>> getAll(Optional<Long> userId) {
        try {
            List<Follower> followers;

            if (userId.isPresent()) {
                // Fetch followers for the given user ID
                followers = followerDao.findByFollower_Id(userId.get());
            } else {
                // Fetch all followers
                followers = followerDao.findAll();
            }

            List<FollowerResponse> responseList = followers.stream()
                .map(FollowerResponse::new) // Convert each Follower to FollowerResponse
                .collect(Collectors.toList());

            return new SuccessDataResult<>(responseList);
        } catch (Exception e) {
            // Handle the exception and return an error result
            return new ErrorDataResult<>("An error occurred while fetching followers: " + e.getMessage());
        }
    }

    @Override
    public FollowerResponse add(FollowerRequest request) {
        Optional<User> followerUser = userDao.findById(request.getFollowerUserId());
        Optional<User> followedUser = userDao.findById(request.getFollowedUserId());

        if (followerUser.isPresent() && followedUser.isPresent()) {
            Follower follower = new Follower();
            follower.setFollower(followerUser.get());
            follower.setFollowed(followedUser.get());

            Follower savedFollower = followerDao.save(follower);
            return new FollowerResponse(savedFollower);
        } else {
            throw new RuntimeException("One or both users not found");
        }
    }

    @Override
    public Result delete(Long followerId) {
        try {
            Optional<Follower> follower = followerDao.findById(followerId);
            if (follower.isPresent()) {
                followerDao.delete(follower.get());
                return new SuccessResult("Follower successfully deleted.");
            } else {
                return new ErrorResult("Follower not found.");
            }
        } catch (Exception e) {
            return new ErrorResult("An error occurred while deleting the follower: " + e.getMessage());
        }
    }

    @Override
    public Result followUser(Long followerUserId, Long followedUserId) {
        Optional<User> followerUser = userDao.findById(followerUserId);
        Optional<User> followedUser = userDao.findById(followedUserId);

        if (followerUser.isPresent() && followedUser.isPresent()) {
            // Check if the follow relationship already exists
            Optional<Follower> existingFollower = followerDao.findByFollowerAndFollowed(followerUser.get(), followedUser.get());
            if (existingFollower.isEmpty()) {
                Follower follower = new Follower();
                follower.setFollower(followerUser.get());
                follower.setFollowed(followedUser.get());

                followerDao.save(follower);
                return new SuccessResult("User followed successfully.");
            } else {
                return new ErrorResult("You are already following this user.");
            }
        } else {
            return new ErrorResult("One or both users not found.");
        }
    }

    @Override
    public Result unfollowUser(Long followerUserId, Long followedUserId) {
        Optional<User> followerUser = userDao.findById(followerUserId);
        Optional<User> followedUser = userDao.findById(followedUserId);

        if (followerUser.isPresent() && followedUser.isPresent()) {
            Optional<Follower> existingFollower = followerDao.findByFollowerAndFollowed(followerUser.get(), followedUser.get());
            if (existingFollower.isPresent()) {
                followerDao.delete(existingFollower.get());
                return new SuccessResult("User unfollowed successfully.");
            } else {
                return new ErrorResult("You are not following this user.");
            }
        } else {
            return new ErrorResult("One or both users not found.");
        }
    }

    @Override
    public DataResult<List<FollowerResponse>> getFollowers(Long userId) {
        List<Follower> followers = followerDao.findByFollowed_Id(userId);
        List<FollowerResponse> responses = followers.stream()
                .map(follower -> new FollowerResponse(
                    follower.getId(),
                    follower.getFollower().getId(),
                    follower.getFollower().getUserName(),
                    follower.getFollowed().getId(),
                    follower.getFollowed().getUserName()))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(responses);
    }

    @Override
    public DataResult<List<FollowerResponse>> getFollowing(Long userId) {
        List<Follower> followings = followerDao.findByFollower_Id(userId);
        List<FollowerResponse> responses = followings.stream()
                .map(follower -> new FollowerResponse(
                    follower.getId(),
                    follower.getFollower().getId(),
                    follower.getFollower().getUserName(),
                    follower.getFollowed().getId(),
                    follower.getFollowed().getUserName()))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(responses);
    }
    @Override
    public Result setFlirtIdIfFollowing(Long userId, Long flirtId) {
        Optional<User> userOpt = userDao.findById(userId);
        Optional<User> flirtUserResult = userDao.findById(flirtId);

        if (userOpt.isEmpty() || flirtUserResult.isEmpty()) {
            return new ErrorResult("User not found.");
        }

        User user = userOpt.get();
        User flirtUser = flirtUserResult.get();
        boolean isFollowing = followerDao.findByFollowerAndFollowed(user, flirtUser).isPresent();

        System.out.println("User ID: " + userId);
        System.out.println("Flirt ID: " + flirtId);
        System.out.println("Is Following: " + isFollowing);

        if (isFollowing) {
            user.setFlirtId(flirtId);
            userDao.save(user);
            return new SuccessResult("Flirt ID set successfully.");
        } else {
            return new ErrorResult("User is not following the flirt user.");
        }
    }


    @Override
    public DataResult<List<User>> findFlirt(Long userId) {
    	List<User> flirtUser = userDao.findByFlirtId(userId);
        if (flirtUser != null) {
        	System.out.println("flirt user "+flirtUser);
            return new SuccessDataResult<>(flirtUser);
        } else {
            return new ErrorDataResult<>("Flirt user not found.");
        }
    }
    @Override
    public DataResult<Boolean> isMatchedFlirt(Long userId) {
        DataResult<List<User>> flirtUsersResult = findFlirt(userId);
        List<User> flirtUsers = flirtUsersResult.getData();

        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isEmpty()) {
            return new ErrorDataResult<>("User not found.");
        }
        
        User currentUser = userOpt.get();
        boolean isMatched = flirtUsers.stream().anyMatch(user -> currentUser.getFlirtId().equals(user.getId()));
        return new SuccessDataResult<>(isMatched);
    }

    

    @Override
    public DataResult<Boolean> findMutualFlirt(Long userId, Long flirtId) {
        boolean isUserFollowingFlirt = followerDao.existsByUserIdAndFlirtId(userId, flirtId);
        boolean isFlirtFollowingUser = followerDao.existsByUserIdAndFlirtId(flirtId, userId);

        if (isUserFollowingFlirt && isFlirtFollowingUser) {
            return new SuccessDataResult<>(true, "Mutual flirt exists");
        } else {
            return new ErrorDataResult<>(false, "No mutual flirt"); 
        }
    }

}

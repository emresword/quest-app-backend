package com.example.questApp.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.HobbyService;
import com.example.questApp.business.requests.HobbyCreateRequest;
import com.example.questApp.business.responses.HobbyResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.dataAccess.abstracts.HobbyDao;
import com.example.questApp.dataAccess.abstracts.UserDao;
import com.example.questApp.entities.concretes.Hobby;
import com.example.questApp.entities.concretes.User;

@Service
public class HobbyManager implements HobbyService {
    private final HobbyDao hobbyDao;
    private final UserDao userDao;

    @Autowired
    public HobbyManager(HobbyDao hobbyDao, UserDao userDao) {
        this.hobbyDao = hobbyDao;
        this.userDao = userDao;
    }

    @Override
    public DataResult<List<HobbyResponse>> getByUserId(Long userId) {
        if (userId == null) {
            return new ErrorDataResult<>("User ID cannot be null");
        }

        List<Hobby> hobbies = hobbyDao.findByUserId(userId);

        if (hobbies.isEmpty()) {
            return new ErrorDataResult<>("No hobbies found for user ID: " + userId);
        }

        List<HobbyResponse> hobbyResponses = hobbies.stream()
                .map(HobbyResponse::new)
                .collect(Collectors.toList());

        return new SuccessDataResult<>(hobbyResponses, "Hobbies retrieved successfully");
    }


    @Override
    public DataResult<List<HobbyResponse>> add(List<HobbyCreateRequest> hobbyCreateRequests) {
        List<Hobby> savedHobbies = new ArrayList<>();

        for (HobbyCreateRequest request : hobbyCreateRequests) {
            // Check if the user exists
            Optional<User> userOptional = userDao.findById(request.getUserId());
            if (!userOptional.isPresent()) {
                throw new IllegalArgumentException("User with ID: " + request.getUserId() + " not found");
            }

            User user = userOptional.get();

            // Check if the hobby already exists for the user
            List<Hobby> existingHobbies = hobbyDao.findByUserId(user.getId());
            boolean hobbyExists = existingHobbies.stream()
                .anyMatch(hobby -> hobby.getHobbyName().equalsIgnoreCase(request.getHobbyName()));

            if (hobbyExists) {
                throw new IllegalArgumentException("Hobby with name '" + request.getHobbyName() + "' already exists for user ID: " + user.getId());
            }

            // Create the new hobby
            Hobby hobby = new Hobby();
            hobby.setHobbyName(request.getHobbyName());
            hobby.setHobbyId(request.getHobbyId());
            hobby.setUser(user);

            // Save the hobby
            savedHobbies.add(hobbyDao.save(hobby));
        }

        // Convert hobbies to response
        List<HobbyResponse> responses = savedHobbies.stream()
            .map(HobbyResponse::new)
            .collect(Collectors.toList());

        return new SuccessDataResult<>(responses, "Hobbies added successfully");
    }

	@Override
	public Result delete(Long hobbyId) {
		
		return null;
	}

}

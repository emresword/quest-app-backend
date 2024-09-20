package com.example.questApp.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.dataAccess.abstracts.UserDao;
import com.example.questApp.entities.concretes.User;

@Service
public class UserManager implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        return this.userDao.findAll();
    }

    @Override
    public User add(User user) {
        // You should handle password encoding in the registration process or another service.
        return this.userDao.save(user);
    }

    @Override
    public User getById(Long id) {
        return this.userDao.findById(id).orElse(null);
    }

    @Override
    public User update(Long id, User newUser) {
        Optional<User> user = userDao.findById(id);

        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) { 
                // Ensure you handle password encoding somewhere else
                foundUser.setPassword(newUser.getPassword()); 
            }
            userDao.save(foundUser);
            return foundUser;
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        this.userDao.deleteById(id);
    }

    @Override
    public User getByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}

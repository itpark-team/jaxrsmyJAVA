package com.example.jaxrsmy.dao;

import com.example.jaxrsmy.model.User;

import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDAO {

    private static UsersDAO instance = null;

    public static UsersDAO getInstance() {
        if (instance == null) {
            instance = new UsersDAO();
        }
        return instance;
    }

    private List<User> users;

    private UsersDAO() {
        users = new ArrayList<>();
        users.add(new User(1, "Иван"));
        users.add(new User(2, "Толик"));
        users.add(new User(3, "Фёдор"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(long id) {
        Optional<User> foundUser = users.stream().filter(u -> u.getId() == id).findFirst();

        if (!foundUser.isPresent()) {
            throw new RuntimeException("user not found");
        }

        return foundUser.get();
    }

    public void addNewUser(User user)  {
        Optional<User> foundUser = users.stream().filter(u -> u.getId() == user.getId()).findFirst();

        if (foundUser.isPresent()) {
            throw new WebApplicationException("user already exist");
        }

        users.add(user);
    }

    public void updateUser(User user, long id) {
        Optional<User> foundUser = users.stream().filter(u -> u.getId() == id).findFirst();

        if (!foundUser.isPresent()) {
            throw new WebApplicationException("user not found");
        }

        foundUser.get().setName(user.getName());
    }

    public void deleteUser(long id)  {
        Optional<User> foundUser = users.stream().filter(u -> u.getId() == id).findFirst();

        if (!foundUser.isPresent()) {
            throw new WebApplicationException("user not found");
        }

        users.remove(foundUser.get());
    }


}

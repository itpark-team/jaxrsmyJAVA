package com.example.jaxrsmy.dao;

import com.example.jaxrsmy.model.User;

import javax.ws.rs.WebApplicationException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UsersDAO {

    private static UsersDAO instance = null;

    private long lastId;
    private Map<Long, User> users;

    public static UsersDAO getInstance() {
        if (instance == null) {
            synchronized (UsersDAO.class) {
                if (instance == null) {
                    instance = new UsersDAO();
                }
            }
        }
        return instance;
    }

    private UsersDAO() {
        users = new ConcurrentHashMap<>();
        users.put(1l, new User(1, "Иван"));
        users.put(2l, new User(2, "Толик"));
        users.put(3l, new User(3, "Фёдор"));

        lastId = 3;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(long id) {
        if (!users.containsKey(id)) {
            throw new WebApplicationException("user not found");
        }

        return users.get(id);
    }

    public void addNewUser(User user) {
        lastId++;
        user.setId(lastId);

        users.put(lastId, user);
    }

    public void updateUser(User user, long id) {
        if (!users.containsKey(id)) {
            throw new WebApplicationException("user not found");
        }

        User foundUser = users.get(id);
        foundUser.setName(user.getName());
    }

    public void deleteUser(long id) {
        if (!users.containsKey(id)) {
            throw new WebApplicationException("user not found");
        }

        users.remove(id);
    }


}

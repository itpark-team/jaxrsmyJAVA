package com.example.jaxrsmy.dao;

import com.example.jaxrsmy.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDAO {
    private List<User> users;

    public UsersDAO() {
        users = new ArrayList<>();
        users.add(new User(1, "Иван"));
        users.add(new User(2, "Толик"));
        users.add(new User(3, "Фёдор"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(long id) throws Exception {
        Optional<User> foundUser = users.stream().filter(user -> user.getId() == id).findFirst();

        if (!foundUser.isPresent()) {
            throw new Exception("not found");
        }

        return foundUser.get();
    }

    /*public void updateEmployee(Employee employee, int id) {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                emp.setId(employee.getId());
                emp.setFirstName(employee.getFirstName());
                return;
            }
        }
        throw new EmployeeNotFound();
    }

    public void deleteEmployee(int id) {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                employeeList.remove(emp);
                return;
            }
        }
        throw new EmployeeNotFound();
    }

    public void addEmployee(Employee employee) {
        for (Employee emp : employeeList) {
            if (emp.getId() == employee.getId()) {
                throw new EmployeeAlreadyExists();
            }
        }
        employeeList.add(employee);
    }*/
}

package com.example.jaxrsmy.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
    private long id;

    @NotNull(message = "field NAME cant be null")
    @Size(min = 1, max = 128, message = "field NAME size must be between 1 and 128 symbols" )
    private String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

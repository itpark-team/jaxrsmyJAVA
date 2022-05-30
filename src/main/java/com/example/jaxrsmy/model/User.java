package com.example.jaxrsmy.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @DecimalMin(value = "1", message = "field ID cant be null and more than 1")
    private long id;
    @NotNull(message = "field NAME cant be null")
    private String name;
}

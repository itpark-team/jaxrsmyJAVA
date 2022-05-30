package com.example.jaxrsmy.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @DecimalMin(value = "1", message = "field ID cant be null and more than 1")
    private long id;

    @NotNull(message = "field NAME cant be null")
    @Size(min = 1, max = 128, message = "field NAME size must be between 1 and 128 symbols" )
    private String name;
}

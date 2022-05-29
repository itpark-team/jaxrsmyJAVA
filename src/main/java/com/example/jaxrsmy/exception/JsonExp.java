package com.example.jaxrsmy.exception;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonRootName(value = "error")
@Getter
@Setter
@AllArgsConstructor
public class JsonExp {
    private String message;
}

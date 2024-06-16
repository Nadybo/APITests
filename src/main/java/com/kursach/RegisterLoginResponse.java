package com.kursach;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RegisterLoginResponse {
    private int id;
    private String token;
    private String error;
}
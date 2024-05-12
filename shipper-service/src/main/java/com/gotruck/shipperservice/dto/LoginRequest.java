package com.gotruck.shipperservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @Override
    public String toString() {
        return String.format("LoginRequest{email='%s', password='******'}", email);
    }
}

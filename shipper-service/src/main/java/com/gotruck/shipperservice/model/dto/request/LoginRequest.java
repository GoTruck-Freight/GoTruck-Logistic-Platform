package com.gotruck.shipperservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    @ToString.Exclude
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}

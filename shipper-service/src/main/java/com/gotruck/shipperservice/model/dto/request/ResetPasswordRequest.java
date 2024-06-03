package com.gotruck.shipperservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Data
@ToString(exclude = "newPassword")
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must include at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String newPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResetPasswordRequest that = (ResetPasswordRequest) o;
        return Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newPassword);
    }
}

package com.gotruck.shipperservice.dto;

import com.gotruck.shipperservice.model.enums.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 50,message = "Company name must not exceed 50 characters")
    private String companyName;

    @NotBlank(message = "Contact name is required")
    @Size(max = 50, message = "Contact name must not exceed 50 characters")
    private String contactName;

    @NotBlank(message = "Email is required")
    @Email(message ="Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must include at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Pattern(
            regexp = "^[0-9]*$",
            message = "Invalid phone number format. Use only digits."
    )
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AccountStatus accountStatus = AccountStatus.ENABLED;

    private String image;

    @Override
    public String toString() {
        return new StringBuilder("RegisterRequest{")
                .append("companyName='").append(companyName).append('\'')
                .append(", contactName='").append(contactName).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", password='").append("*****").append('\'')
                .append(", phoneNumber='").append(phoneNumber).append('\'')
                .append(", image='").append(image).append('\'')
                .append(", accountStatus=").append(accountStatus).append('\'')
                .append('}').toString();

    }

}

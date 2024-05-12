package com.gotruck.shipperservice.dto;

import com.gotruck.shipperservice.model.enums.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Size(max = 50, message = "Company name must not exceed 50 characters")
    private String companyName;

    @Size(max = 50, message = "Contact name must not exceed 50 characters")
    private String contactName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Pattern(
            regexp = "^[0-9]*$",
            message = "Invalid phone number format. Use only digits.")
    private String phoneNumber;

    private String image;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}

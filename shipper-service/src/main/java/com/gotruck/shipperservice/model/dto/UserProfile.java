package com.gotruck.shipperservice.model.dto;

import com.gotruck.shipperservice.model.enums.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(companyName, that.companyName) &&
                Objects.equals(contactName, that.contactName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(image, that.image) &&
                accountStatus == that.accountStatus;
    }
    @Override
    public int hashCode() {
        return Objects.hash(companyName, contactName, email, phoneNumber, accountStatus, image);
    }
}

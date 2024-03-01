package com.gotruck.shipperservice.model;

import com.gotruck.shipperservice.Enum.AccountStatus;
import com.gotruck.shipperservice.service.ImageService;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Shipper-user")

public class User implements UserDetails {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotBlank(message = "Company name is required")
    @Size(max = 50, message = "Company name must not exceed 50 characters")
    private String companyName;

    @NotBlank(message = "Contact name is required")
    @Size(max = 50, message = "Contact name must not exceed 50 characters")
    private String contactName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must include at least one uppercase letter, one lowercase letter, and one digit")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Pattern(
            regexp = "^[0-9]*$",
            message = "Invalid phone number format. Use only digits.")
    private String phoneNumber;

    @Transient // Bu alanın veritabanında saklanmamasını belirtir
    private static final String DEFAULT_IMAGE_URL = "/images/driverrr-image.jpeg";

    @Column(length = 1000)
    private String image;


//    @Enumerated(EnumType.STRING)

//    @Column(nullable = false)

//    private Role role;
    private AccountStatus accountStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
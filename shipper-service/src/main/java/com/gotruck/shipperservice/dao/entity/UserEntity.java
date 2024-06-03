package com.gotruck.shipperservice.dao.entity;

import com.gotruck.shipperservice.model.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
@Table(name = "shipper-users")

public class UserEntity extends BaseEntity implements UserDetails {

    @NotBlank
    @Column(name = "company_name")
    private String companyName;

    @NotBlank
    @Column(name = "contact_name")
    private String contactName;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image", length = 1000)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    @Builder.Default
    private AccountStatus accountStatus = AccountStatus.ENABLED;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(companyName, that.companyName) &&
                Objects.equals(contactName, that.contactName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(image, that.image) &&
                accountStatus == that.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, contactName, email, password, phoneNumber, image, accountStatus);
    }
}
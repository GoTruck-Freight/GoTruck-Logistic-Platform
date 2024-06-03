package com.gotruck.shipperservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefreshTokenRequest that = (RefreshTokenRequest) o;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken);
    }
}

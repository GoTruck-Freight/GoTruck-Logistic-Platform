package com.gotruck.shipperservice.model.dto.response;

import lombok.*;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"accessToken", "refreshToken"})
public class JwtAuthResponse {

    private String accessToken;
    private String refreshToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JwtAuthResponse that = (JwtAuthResponse) o;
        return Objects.equals(accessToken, that.accessToken)
                && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken);
    }
}

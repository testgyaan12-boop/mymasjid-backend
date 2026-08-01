package com.noormasjid.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long userId;
    private String email;
    private String name;
    private String systemRole;
    private Long currentMasjidId;

    public AuthResponse() {
        this.tokenType = "Bearer";
    }
}

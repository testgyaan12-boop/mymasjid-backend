package com.noormasjid.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String systemRole;
    private Long currentMasjidId;
    private String currentMasjidName;
}

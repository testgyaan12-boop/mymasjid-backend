package com.noormasjid.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MasjidResponse {

    private Long id;
    private String name;
    private String address;
    private String pincode;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String email;
    private String logo;
    private String website;
    private String userRole;
}
